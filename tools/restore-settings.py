#!/usr/bin/env python3
"""Restore the published Everness preference snapshot once, with a local backup."""
import argparse, datetime, hashlib, json, os, pathlib, shutil, sys, uuid

def digest(data): return hashlib.sha256(data).hexdigest()

def restore(bundle, game, check=False, again=False):
    bundle=bundle.resolve(); game=game.resolve()
    manifest_bytes=(bundle/'settings-manifest.json').read_bytes()
    manifest=json.loads(manifest_bytes); revision=digest(manifest_bytes)
    if not game.is_dir() or not (game/'mods').is_dir():
        raise ValueError('Select the Minecraft game folder containing mods and config.')
    records=[]
    for item in manifest['files']:
        rel=pathlib.PurePosixPath(item['path'])
        if rel.is_absolute() or '..' in rel.parts or '\\' in str(rel) or ':' in str(rel):
            raise ValueError('Unsafe snapshot path')
        if not (rel.parts[0] in {'config','oneconfig','shaderpacks','upscalepacks','vitrail'} or str(rel) in {'options.txt','servers.dat'}):
            raise ValueError('Not a preference path: '+str(rel))
        src=bundle.joinpath(*rel.parts); dst=game.joinpath(*rel.parts)
        if not src.resolve().is_relative_to(bundle) or not dst.resolve().is_relative_to(game):
            raise ValueError('Linked path escapes the selected folder')
        data=src.read_bytes()
        if digest(data)!=item['sha256']: raise ValueError('Snapshot checksum failed: '+str(rel))
        records.append((str(rel),src,dst,data))
    differences=[r for r in records if not r[2].is_file() or digest(r[2].read_bytes())!=digest(r[3])]
    result={'profile':manifest['profile'],'version':manifest['version'],'files':len(records),'different':len(differences),'game':str(game)}
    if check: return {**result,'result':'MATCH' if not differences else 'DIFFERENT','paths':[r[0] for r in differences]}
    marker=game/'.everness-settings'/('restored-'+revision+'.json')
    if marker.exists() and not again: return {**result,'result':'ALREADY_RESTORED'}
    stamp=datetime.datetime.now().strftime('%Y%m%d-%H%M%S')+'-'+uuid.uuid4().hex[:8]
    backup=game/'.everness-settings'/'backups'/stamp
    backup.mkdir(parents=True)
    journal=[]
    # Finish validation and backups before replacing any preference.
    for rel,src,dst,data in differences:
        if dst.exists() and not dst.is_file(): raise ValueError('Destination is not a file: '+rel)
        old=backup.joinpath(*pathlib.PurePosixPath(rel).parts)
        if dst.exists(): old.parent.mkdir(parents=True,exist_ok=True); shutil.copy2(dst,old)
        journal.append({'path':rel,'existed':dst.exists()})
    (backup/'restore-journal.json').write_text(json.dumps(journal,indent=2),encoding='utf-8')
    written=[]
    try:
        for rel,src,dst,data in differences:
            dst.parent.mkdir(parents=True,exist_ok=True)
            temp=dst.with_name(dst.name+'.'+uuid.uuid4().hex+'.tmp')
            try: temp.write_bytes(data); os.replace(temp,dst)
            finally:
                if temp.exists(): temp.unlink()
            written.append(rel)
        for rel,src,dst,data in records:
            if digest(dst.read_bytes())!=digest(data): raise ValueError('Restore verification failed: '+rel)
    except Exception:
        for item in journal:
            dst=game.joinpath(*pathlib.PurePosixPath(item['path']).parts)
            if item['existed']: shutil.copy2(backup.joinpath(*pathlib.PurePosixPath(item['path']).parts),dst)
            elif dst.exists(): dst.unlink()
        raise
    marker.parent.mkdir(parents=True,exist_ok=True)
    marker.write_text(json.dumps({**result,'backup':str(backup),'manifestSha256':revision},indent=2),encoding='utf-8')
    return {**result,'result':'RESTORED','backup':str(backup)}

if __name__=='__main__':
    p=argparse.ArgumentParser(description=__doc__)
    p.add_argument('--game-dir',type=pathlib.Path,required=True)
    p.add_argument('--bundle',type=pathlib.Path,default=pathlib.Path(__file__).resolve().parents[1])
    p.add_argument('--check-only',action='store_true'); p.add_argument('--again',action='store_true')
    a=p.parse_args()
    print(json.dumps(restore(a.bundle,a.game,a.check_only,a.again),ensure_ascii=False,indent=2))
