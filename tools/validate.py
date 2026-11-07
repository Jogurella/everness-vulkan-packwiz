#!/usr/bin/env python3
import argparse,hashlib,json,pathlib,tomllib,urllib.parse
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
print(json.dumps({'pack':pack['name'],'index_files':len(index['files']),'download_metadata':count,'local_additions':len(all_paths),'result':'PASS'}))
