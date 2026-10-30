#!/usr/bin/env python3
"""The Deep Void 26.1.2 -> 26.2, the mod's own rules after tools/port_262.py (modding-kb; idempotent, repo root)"""
import glob
import re

ROOTS = ['common/src/main/java/', 'neoforge/src/main/java/', 'fabric/src/main/java/']


def port(s):
    # 26.2: the emissive predicate takes the state only
    s = s.replace('.emissiveRendering((bs, br, bp) -> true)', '.emissiveRendering(bs -> true)')
    # 26.2: no MultiBufferSource (submit node collectors since 1.21.9; the import was left over)
    if 'import net.minecraft.client.renderer.MultiBufferSource;' in s and not re.search(r'\bMultiBufferSource\b', s.replace('import net.minecraft.client.renderer.MultiBufferSource;', '')):
        s = s.replace('import net.minecraft.client.renderer.MultiBufferSource;\n', '')
    # NeoForge 26.2: the effect text of the inventory is extracted, with the canvas width and color
    s = s.replace('renderInventoryText(MobEffectInstance instance, AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int x, int y, int blitOffset)',
                  'extractInventoryText(MobEffectInstance instance, AbstractContainerScreen<?> screen, GuiGraphicsExtractor guiGraphics, int x, int y, int canvasWidth, int color)')
    s = s.replace('ours.renderInventoryText(instance, screen, guiGraphics, x, y, blitOffset)', 'ours.extractInventoryText(instance, screen, guiGraphics, x, y, canvasWidth, color)')
    return s


def main():
    changed = 0
    for root in ROOTS:
        for path in glob.glob(root + '**/*.java', recursive=True):
            s = open(path, encoding='utf-8').read()
            o = port(s)
            if o != s:
                open(path, 'w', encoding='utf-8', newline='\n').write(o)
                changed += 1
    print('changed', changed)


if __name__ == '__main__':
    main()
