"""Build the Everness Nether/fog/sky additions on the pinned lifecycle.2 VIRIS JAR."""
from pathlib import Path
import argparse,hashlib,json,subprocess,tempfile,zipfile
BASE_SHA256='aaf84361dc3baabcc3ab7229ac2f2f6195d6b0cd69af23e782f4eae8a94eab04'
NOTICE=b'Everness targeted Nether atmospheric range and one-time natural fog-color migration. Existing lifecycle/chest/equipment fixes retained. Voxy remains separate.\n'
MIXINS=['compat.everness.NetherAtmosphereMixin','compat.everness.SkyFogCaptureMixin','compat.everness.SkyFogScopeMixin']
def main():
    p=argparse.ArgumentParser(description=__doc__)
    p.add_argument('--base-jar',type=Path,required=True);p.add_argument('--classpath',required=True)
    p.add_argument('--javac',type=Path,required=True);p.add_argument('--output',type=Path,required=True)
    p.add_argument('--source',type=Path,default=Path(__file__).resolve().parent/'src');a=p.parse_args()
    assert hashlib.sha256(a.base_jar.read_bytes()).hexdigest()==BASE_SHA256,'Wrong lifecycle.2 base'
    sources=sorted(a.source.rglob('*.java'));assert len(sources)==7
    assert a.output.resolve()!=a.base_jar.resolve()
    with tempfile.TemporaryDirectory(prefix='everness-fog-build-')as temp:
        classes=Path(temp)/'classes';classes.mkdir()
        args=['--release','25','-proc:none','-encoding','UTF-8','-classpath',a.classpath,'-d',str(classes)]+list(map(str,sources))
        argfile=Path(temp)/'javac.args';argfile.write_text('\n'.join('"'+s.replace('\\','/')+'"'for s in args),encoding='utf-8')
        subprocess.run([str(a.javac),'@'+str(argfile)],check=True)
        a.output.parent.mkdir(parents=True,exist_ok=True)
        with zipfile.ZipFile(a.base_jar)as src,zipfile.ZipFile(a.output,'w',zipfile.ZIP_DEFLATED)as dst:
            for info in src.infolist():
                data=src.read(info.filename)
                if info.filename=='fabric.mod.json':
                    m=json.loads(data);m['version']='0.3.6+mc26.3.everness-lifecycle.3'
                    m.setdefault('entrypoints',{}).setdefault('preLaunch',[]).append('com.viris.compat.everness.FogCompatPreLaunch')
                    data=(json.dumps(m,indent=2)+'\n').encode()
                if info.filename=='viris.mixins.json':
                    m=json.loads(data);m.setdefault('client',[]).extend(MIXINS);data=(json.dumps(m,indent=2)+'\n').encode()
                dst.writestr(info,data)
            for f in sorted(classes.rglob('*.class')):dst.write(f,f.relative_to(classes).as_posix())
            dst.writestr('EVERNESS-FOG-NOTICE.txt',NOTICE)
    with zipfile.ZipFile(a.base_jar)as old,zipfile.ZipFile(a.output)as new:
        assert not set(old.namelist())-set(new.namelist())
        changed=[n for n in old.namelist()if old.read(n)!=new.read(n)]
        assert set(changed)=={'fabric.mod.json','viris.mixins.json'}
        added=set(new.namelist())-set(old.namelist());assert len(added)==9
        assert not any(n.startswith(('me/cortex/voxy/','dev/vitrail/','io/github/sjouwer/'))for n in added)
    print(json.dumps({'sha256':hashlib.sha256(a.output.read_bytes()).hexdigest(),'changedExistingEntries':changed,'addedEntries':sorted(added),'VoxyEmbedded':False}))
if __name__=='__main__':main()
