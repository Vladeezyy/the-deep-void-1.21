#!/usr/bin/env python3
"""GeckoLib 4 -> 5, The Deep Void's own rules after tools/port_geckolib5.py (idempotent; repo root):
- the controllers' leftovers of GeckoLib 4 in entities and items: getController(), the STOPPED state,
  forceAnimationReset(), getPartialTick() (port.GeoAnims)
- GeoItem / GeoArmor items: the same AnimationTest as the entities"""
import glob
import re

ROOT = 'common/src/main/java/net/sombrius/thedeepvoid/'
PKG = 'net.sombrius.thedeepvoid'


def port(s, cls):
    s = re.sub(r'(\w+)\.getController\(\)\.getAnimationState\(\) == AnimationController\.State\.STOPPED', rf'{PKG}.port.GeoAnims.stopped(\1.controller())', s)
    s = re.sub(r'(\w+)\.getController\(\)\.getAnimationState\(\) != AnimationController\.State\.STOPPED', rf'!{PKG}.port.GeoAnims.stopped(\1.controller())', s)
    s = re.sub(r'\bevent\.getController\(\)', 'event.controller()', s)
    s = s.replace('.forceAnimationReset()', '.reset()')
    s = re.sub(r'\bevent\.getPartialTick\(\)', f'{PKG}.port.GeoAnims.partialTick(event)', s)
    if cls and '(AnimationState event)' in s:
        s = s.replace('(AnimationState event)', f'(AnimationTest<{cls}> event)')
        s = s.replace('import software.bernie.geckolib.animation.AnimationState;', 'import software.bernie.geckolib.animation.state.AnimationTest;')
        s = re.sub(r'new AnimationController<>\(this, ', 'new AnimationController<>(', s)
    s = s.replace('import software.bernie.geckolib.animation.PlayState;', 'import software.bernie.geckolib.animation.object.PlayState;')
    s = s.replace('import software.bernie.geckolib.animation.AnimatableManager;', 'import software.bernie.geckolib.animatable.manager.AnimatableManager;')
    return s


def main():
    changed = 0
    for path in glob.glob(ROOT + '**/*.java', recursive=True):
        s = open(path, encoding='utf-8').read()
        if 'software.bernie.geckolib' not in s:
            continue
        m = re.search(r'^public (?:abstract )?class (\w+)[^{]*implements [^{]*\b(?:GeoItem|GeoEntity)\b', s, re.M)
        o = port(s, m.group(1) if m else None)
        if o != s:
            open(path, 'w', encoding='utf-8', newline='\n').write(o)
            changed += 1
    print('changed', changed)


if __name__ == '__main__':
    main()
