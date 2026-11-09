"""Build only the documented VIRIS outer-class patch; requires JDK 25 and a development classpath."""
from pathlib import Path
import argparse, hashlib, json, os, subprocess, tempfile, zipfile

VERSION = '0.3.6+mc26.3.everness-lifecycle.1'
UPSTREAM_SHA512 = '79e1588ce802fda208a6dbb09528a75e26c4d4587a2bcd1e06e62aec0aef91f800a91bcf3f40c5e218edc4e93280085efe117ad017e4e34ab74d3e6f26273ca9'
p = argparse.ArgumentParser(description=__doc__)
p.add_argument('--upstream-jar', required=True, type=Path)
p.add_argument('--classpath', required=True, help='Minecraft/Fabric/Voxy/Vitrail development dependencies; OS path separator')
p.add_argument('--javac', required=True)
p.add_argument('--output', required=True, type=Path)
args = p.parse_args()
source = Path(__file__).resolve().with_name('VoxyFrame.java')
if hashlib.sha512(args.upstream_jar.read_bytes()).hexdigest() != UPSTREAM_SHA512:
    raise SystemExit('Expected the unmodified official VIRIS 0.3.6 JAR; SHA-512 mismatch.')
if args.output.resolve() == args.upstream_jar.resolve():
    raise SystemExit('Use a separate output file; never overwrite the input JAR.')
with tempfile.TemporaryDirectory(prefix='viris-lifecycle-build-') as tmp:
    classes = Path(tmp) / 'classes'; classes.mkdir()
    cp = str(args.upstream_jar.resolve()) + os.pathsep + args.classpath
    subprocess.run([args.javac, '--release', '25', '-proc:none', '-encoding', 'UTF-8', '-classpath', cp, '-d', str(classes), str(source)], check=True)
    replacement = (classes / 'com/viris/VoxyFrame.class').read_bytes()
    args.output.parent.mkdir(parents=True, exist_ok=True)
    with zipfile.ZipFile(args.upstream_jar) as upstream, zipfile.ZipFile(args.output, 'w', zipfile.ZIP_DEFLATED) as output:
        for info in upstream.infolist():
            data = upstream.read(info.filename)
            if info.filename == 'com/viris/VoxyFrame.class':
                data = replacement
            elif info.filename == 'fabric.mod.json':
                metadata = json.loads(data); metadata['version'] = VERSION
                data = (json.dumps(metadata, ensure_ascii=False, indent=2) + '\n').encode('utf-8')
            output.writestr(info, data)
        output.writestr('EVERNESS-LIFECYCLE-NOTICE.txt', '''VIRIS 0.3.6, upstream laurensanp, MIT.
Upstream source: https://github.com/laurensanp/viris/tree/2d3061bcb73751a6c70cef50eb3b592b6a4659f3
Everness change: invalidate the Voxy shader pipeline when the current Vitrail PackValues object changes,
even if pack name, profile, options, dimension and render size are equal. The pipeline otherwise
keeps uniform suppliers referring to the retired lighting state. No Voxy classes are embedded.
This build is a compatibility candidate; in-game regression verification is required.
''')
    with zipfile.ZipFile(args.upstream_jar) as a, zipfile.ZipFile(args.output) as b:
        changed = {n for n in a.namelist() if a.read(n) != b.read(n)}
        assert changed == {'com/viris/VoxyFrame.class', 'fabric.mod.json'}, changed
        assert set(b.namelist()) - set(a.namelist()) == {'EVERNESS-LIFECYCLE-NOTICE.txt'}
        assert not any(n.startswith(('me/cortex/voxy/', 'dev/vitrail/', 'traben/')) for n in b.namelist())
        assert b.testzip() is None
print(json.dumps({'version': VERSION, 'sha256': hashlib.sha256(args.output.read_bytes()).hexdigest(), 'VoxyEmbedded': False}))
