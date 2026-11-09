"""Build only the five Everness compatibility classes on the pinned lifecycle.1 JAR."""
from pathlib import Path
import argparse,hashlib,json,subprocess,tempfile,zipfile

BASE_SHA256='5b0cbc7974983ce52f13ec47ce2e84f4736c9d5d66090307424b78395936638a'
NOTICE=b'Everness: map enchanted equipment to its normal alpha-tested texture only in Vitrail shadow submission. Normal view glint, textures and resource packs unchanged. No Voxy embedded. Offline verification required.\n'

def main():
    p=argparse.ArgumentParser(description=__doc__)
    p.add_argument('--base-jar',type=Path,required=True)
    p.add_argument('--classpath',required=True,help='Semicolon-separated Minecraft 26.3/Fabric/Vitrail/VIRIS/MixinExtras classpath, or libs/*')
    p.add_argument('--javac',type=Path,required=True)
    p.add_argument('--output',type=Path,required=True)
    p.add_argument('--source',type=Path,default=Path(__file__).resolve().parent/'src')
    a=p.parse_args()
    assert hashlib.sha256(a.base_jar.read_bytes()).hexdigest()==BASE_SHA256,'Unexpected lifecycle.1 base JAR'
    sources=sorted(a.source.rglob('*.java'));assert len(sources)==5,'Expected exactly five source files'
    with tempfile.TemporaryDirectory(prefix='everness-equipment-build-')as tmp:
        classes=Path(tmp)/'classes';classes.mkdir()
        args=['--release','25','-proc:none','-encoding','UTF-8','-classpath',a.classpath,'-d',str(classes)]+list(map(str,sources))
        argfile=Path(tmp)/'javac.args'
        argfile.write_text('\n'.join('"'+s.replace('\\','/')+'"'for s in args),encoding='utf-8')
        subprocess.run([str(a.javac),'@'+str(argfile)],check=True)
        a.output.parent.mkdir(parents=True,exist_ok=True)
        with zipfile.ZipFile(a.base_jar)as src,zipfile.ZipFile(a.output,'w',zipfile.ZIP_DEFLATED)as dst:
            for info in src.infolist():
                data=src.read(info.filename)
                if info.filename=='fabric.mod.json':
                    m=json.loads(data);m['version']='0.3.6+mc26.3.everness-lifecycle.2'
                    m.setdefault('entrypoints',{}).setdefault('preLaunch',[]).append('com.viris.compat.everness.ChestCompatPreLaunch')
                    data=(json.dumps(m,indent=2)+'\n').encode()
                if info.filename=='viris.mixins.json':
                    m=json.loads(data);m.setdefault('client',[]).extend(['compat.everness.VitrailEquipmentShadowScopeMixin','compat.everness.EnchantedEquipmentShadowMixin'])
                    data=(json.dumps(m,indent=2)+'\n').encode()
                dst.writestr(info,data)
            for f in sorted(classes.rglob('*.class')):dst.write(f,f.relative_to(classes).as_posix())
            dst.writestr('EVERNESS-EQUIPMENT-NOTICE.txt',NOTICE)
    with zipfile.ZipFile(a.base_jar)as old,zipfile.ZipFile(a.output)as new:
        changed=[n for n in old.namelist()if old.read(n)!=new.read(n)]
        assert set(changed)=={'fabric.mod.json','viris.mixins.json'},changed
        additions=set(new.namelist())-set(old.namelist())
        assert len(additions)==6 and 'EVERNESS-EQUIPMENT-NOTICE.txt'in additions,additions
        assert not any(n.startswith(('me/cortex/voxy/','dev/vitrail/','traben/'))for n in additions)
    print(json.dumps({'sha256':hashlib.sha256(a.output.read_bytes()).hexdigest(),'output':str(a.output),'changedExistingEntries':changed,'addedEntries':sorted(additions),'VoxyEmbedded':False}))

if __name__=='__main__':main()
