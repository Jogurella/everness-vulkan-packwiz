#!/usr/bin/env python3
import argparse,hashlib,json,pathlib,tomllib,urllib.parse,zipfile
p=argparse.ArgumentParser();p.add_argument('--root',type=pathlib.Path,default=pathlib.Path(__file__).resolve().parents[1]);a=p.parse_args();r=a.root
pack=tomllib.loads((r/'pack.toml').read_text());idx=r/pack['index']['file']
assert hashlib.new(pack['index']['hash-format'],idx.read_bytes()).hexdigest()==pack['index']['hash']
index=tomllib.loads(idx.read_text());count=0
for item in index['files']:
    rel=pathlib.PurePosixPath(item['file']);assert not rel.is_absolute() and '..' not in rel.parts
    f=r.joinpath(*rel.parts);assert f.is_file(),rel
    assert hashlib.new(item.get('hash-format',index['hash-format']),f.read_bytes()).hexdigest()==item['hash'],rel
    if item.get('metafile'):
        m=tomllib.loads(f.read_text());assert m['side']=='client';assert m['pin'] is True
        assert urllib.parse.urlparse(m['download']['url']).scheme=='https'
        assert len(m['download']['hash'])==128 and m['download']['hash-format']=='sha512'
        count+=1
manual=json.loads((r/'manual-files.json').read_text());all_paths=[x['path'] for x in manual['files']]
assert len(all_paths)==len(set(all_paths))
manifest=json.loads((r/'settings-manifest.json').read_text(encoding='utf-8'))
entries={item['file']:item for item in index['files']}
for item in manifest['files']:
    f=r/item['path'];assert hashlib.sha256(f.read_bytes()).hexdigest()==item['sha256'],item['path']
    assert entries[item['path']].get('preserve') is True,item['path']
assert 'config/punchy/profiles/.active_profile' in entries
assert pack['versions']=={'fabric':'0.20.1','minecraft':'26.3'}
assert pack['options']['everness']['settings-sha256']==hashlib.sha256((r/'settings-manifest.json').read_bytes()).hexdigest()
mods=[tomllib.loads(f.read_text()) for f in (r/'mods').glob('*.pw.toml')]
assert len(mods)==65
assert not any('distanthorizons' in m['filename'].lower() or 'vulkanmod' in m['filename'].lower() for m in mods)
assert {'viris-0.3.6+mc26.3.jar','voxy-0.2.20-beta.jar','sodium-fabric-0.9.2+mc26.3.jar','vitrail-0.13.0-beta+mc26.3.jar'}.issubset({m['filename'] for m in mods})
assert 'config/voxy-config.json' in entries
assert 'config/DistantHorizons.toml' not in entries
assert (r/'.github/assets/dh-voxy-beispielbild.png').is_file()
with zipfile.ZipFile(r/'downloads/Everness-vulkan-Prism-Starter.zip') as starter:
    mmc=json.loads(starter.read('mmc-pack.json'))
    assert next(c['version'] for c in mmc['components'] if c['uid']=='net.fabricmc.fabric-loader')=='0.20.1'
    assert not any(n.startswith('minecraft/mods/') for n in starter.namelist())
    for item in manifest['files']:assert hashlib.sha256(starter.read('minecraft/'+item['path'])).hexdigest()==item['sha256']
for line in (r/'downloads/SHA256SUMS.txt').read_text().splitlines():
    expected,name=line.split('  ',1);assert hashlib.sha256((r/'downloads'/name).read_bytes()).hexdigest()==expected
print(json.dumps({'pack':pack['name'],'index_files':len(index['files']),'download_metadata':count,'local_additions':len(all_paths),'result':'PASS'}))
