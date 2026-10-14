import os, sys, subprocess, shutil, re
base, neo, head, out = sys.argv[1:5]   # dirs: forge198 pkg root, neo pkg root, forge HEAD pkg root, output pkg root
def norm(t):
    t=t.replace('net.mcreator.thedeepvoid', 'net.sombrius.thedeepvoid').replace('\r\n','\n')
    lines=t.split('\n'); imps=sorted(set(l.strip() for l in lines if l.startswith('import ')))
    body=[l for l in lines if not l.startswith('import ')]
    out=[]; done=False
    for l in body:
        out.append(l)
        if not done and l.startswith('package '):
            out += ['']+imps; done=True
    t='\n'.join(out)
    import re
    t=re.sub(r'\n{3,}','\n\n',t)
    return t
def files(d):
    r=set()
    for dp,_,fs in os.walk(d):
        for f in fs:
            if f.endswith('.java'): r.add(os.path.relpath(os.path.join(dp,f),d))
    return r
B,N,H=files(base),files(neo),files(head)
stats={}
tmp=out+'.tmp'; os.makedirs(tmp,exist_ok=True)
for rel in sorted(H):
    h=norm(open(os.path.join(head,rel),encoding='utf-8').read())
    dst=os.path.join(out,rel); os.makedirs(os.path.dirname(dst),exist_ok=True)
    if rel in N and rel in B:
        b=norm(open(os.path.join(base,rel),encoding='utf-8').read()); n=norm(open(os.path.join(neo,rel),encoding='utf-8').read())
        if b==h: k='neo'; res=n
        elif b==n: k='forge-new'; res=h
        else:
            for nm,t in (('n',n),('b',b),('h',h)): open(os.path.join(tmp,nm),'w',encoding='utf-8').write(t)
            p=subprocess.run(['git','merge-file','-p','-L','neo','-L','forge198','-L','forge199',os.path.join(tmp,'n'),os.path.join(tmp,'b'),os.path.join(tmp,'h')],capture_output=True,text=True)
            res=p.stdout; k='merged' if p.returncode==0 else 'conflict'
    else: k='forge-only'; res=h
    stats.setdefault(k,[]).append(rel)
    open(dst,'w',encoding='utf-8').write(res)
shutil.rmtree(tmp)
for k,v in stats.items(): print(k,len(v))
open(out+'.conflicts.txt','w').write('\n'.join(stats.get('conflict',[])))
open(out+'.forgeonly.txt','w').write('\n'.join(stats.get('forge-only',[])))
open(out+'.forgenew.txt','w').write('\n'.join(stats.get('forge-new',[])+stats.get('merged',[])))
