#!/usr/bin/env python3
"""Import locally owned exact-version additions; never downloads or publishes them."""
import argparse,hashlib,json,pathlib,shutil,sys
p=argparse.ArgumentParser(description=__doc__)
p.add_argument('--from-game',type=pathlib.Path,required=True)
p.add_argument('--to-game',type=pathlib.Path,required=True)
p.add_argument('--check-only',action='store_true')
a=p.parse_args();root=pathlib.Path(__file__).resolve().parents[1]
manifest=json.loads((root/'manual-files.json').read_text(encoding='utf-8'))
source=a.from_game.resolve();target=a.to_game.resolve();failures=[];ready=[]
for item in manifest['files']:
    rel=pathlib.PurePosixPath(item['path'])
    if rel.is_absolute() or '..' in rel.parts:raise SystemExit('Unsafe manifest path')
    src=source.joinpath(*rel.parts);dst=target.joinpath(*rel.parts)
    if not src.is_file():failures.append(item['path']+' : missing source');continue
    with src.open('rb') as f:digest=hashlib.file_digest(f,'sha512').hexdigest()
    if digest!=item['sha512']:failures.append(item['path']+' : different version');continue
    ready.append((src,dst))
if failures:
    print('Local additions are incomplete; no files were changed.');print('\n'.join(failures));sys.exit(2)
if a.check_only:print(str(len(ready))+' local additions verified.');sys.exit(0)
for src,dst in ready:
    if src==dst:continue
    dst.parent.mkdir(parents=True,exist_ok=True)
    if dst.exists() and dst.read_bytes()!=src.read_bytes():
        backup=target/'.everness-import-backup'/dst.relative_to(target)
        backup.parent.mkdir(parents=True,exist_ok=True);shutil.copy2(dst,backup)
    shutil.copy2(src,dst)
print(str(len(ready))+' exact-version local additions imported. No caches, accounts or worlds copied.')
