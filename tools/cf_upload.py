"""Uploads The Deep Void release jars (NeoForge + Fabric) of the given MC versions to CurseForge project 1775380.
Jars and changelog come from the GitHub release v<VER>+<mc>. Dry run unless --go. Order: old MC first, Fabric before NeoForge.
usage: python tools/cf_upload.py <mc>... [--go]  (VER is mod_version in gradle.properties; the key is curseforgeToken= in ~/.gradle/gradle.properties)"""
import json, os, re, subprocess, sys, tempfile, urllib.request, uuid

PROJECT = 1775380
REPO = 'Vladeezyy/the-deep-void-1.21'
# the release version: mod_version of this checkout (every branch has the same)
VER = re.search(r'^mod_version=(\S+)', open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'gradle.properties'), encoding='utf-8').read(), re.M).group(1)
MC = {'1.21.1': 11779, '1.21.11': 14406, '26.1.2': 16082, '26.2': 16498, '26.3': 17045}
JAVA = {'1.21.1': 11135, '1.21.11': 11135}  # Java 21; 26.x: Java 25
LOADERS = {'fabric': ('Fabric', 7499), 'neoforge': ('NeoForge', 10150)}
CLIENT, SERVER, JAVA25 = 9638, 9639, 14454

go = '--go' in sys.argv
token = re.search(r'^curseforgeToken=(.*)$', open(os.path.expanduser('~/.gradle/gradle.properties')).read(), re.M).group(1).strip()

for mc in [a for a in sys.argv[1:] if a != '--go']:
    tag = 'v%s+%s' % (VER, mc)
    d = tempfile.mkdtemp()
    subprocess.run(['gh', 'release', 'download', tag, '-R', REPO, '-D', d, '-p', '*.jar'], check=True)
    notes = json.loads(subprocess.check_output(['gh', 'release', 'view', tag, '-R', REPO, '--json', 'body'], encoding='utf-8'))['body']
    for loader, (name, loader_id) in LOADERS.items():
        jar = os.path.join(d, 'the-deep-void-%s-%s+%s.jar' % (loader, VER, mc))
        relations = [{'slug': 'geckolib', 'type': 'requiredDependency'}, {'slug': 'jei', 'type': 'optionalDependency'}]
        if loader == 'fabric':
            relations.insert(1, {'slug': 'fabric-api', 'type': 'requiredDependency'})
        meta = {'changelog': notes, 'changelogType': 'markdown', 'releaseType': 'release',
                'displayName': 'The Deep Void %s (%s %s)' % (VER, name, mc),
                'gameVersions': [MC[mc], loader_id, JAVA.get(mc, JAVA25), CLIENT, SERVER],
                'relations': {'projects': relations}}
        print(mc, os.path.basename(jar), os.path.getsize(jar), meta['displayName'], meta['gameVersions'], [r['slug'] for r in relations])
        if not go:
            continue
        b = uuid.uuid4().hex
        body = ('--%s\r\nContent-Disposition: form-data; name="metadata"\r\n\r\n%s\r\n' % (b, json.dumps(meta))).encode() + \
               ('--%s\r\nContent-Disposition: form-data; name="file"; filename="%s"\r\nContent-Type: application/java-archive\r\n\r\n' % (b, os.path.basename(jar))).encode() + \
               open(jar, 'rb').read() + ('\r\n--%s--\r\n' % b).encode()
        req = urllib.request.Request('https://minecraft.curseforge.com/api/projects/%d/upload-file' % PROJECT, data=body,
                                     headers={'X-Api-Token': token, 'Content-Type': 'multipart/form-data; boundary=' + b})
        print('  ->', urllib.request.urlopen(req).read().decode())
