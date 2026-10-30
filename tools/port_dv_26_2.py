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
    # 26.2 logs reads outside the write zone: a template placed up to the zone's edge reads the neighbours of its edge
    # blocks (shapes, fluids) one block further, in the chunk at distance 2; clip the template one block inside
    s = s.replace('new BoundingBox(chunk.getMinBlockX() - 16, worldGenLevel.getMinY(), chunk.getMinBlockZ() - 16, chunk.getMaxBlockX() + 16,\n\t\t\t\tworldGenLevel.getMaxY(), chunk.getMaxBlockZ() + 16)',
                  'new BoundingBox(chunk.getMinBlockX() - 15, worldGenLevel.getMinY(), chunk.getMinBlockZ() - 15, chunk.getMaxBlockX() + 15,\n\t\t\t\tworldGenLevel.getMaxY(), chunk.getMaxBlockZ() + 15)')
    if '(placing reads' not in s:
        s = s.replace('// A feature may only write into its own chunk and the neighbouring ones; clip the template to that area',
                      '// A feature may only write into its own chunk and the neighbouring ones; clip the template to that area, one\n\t\t// block inside (placing reads the neighbours of the edge blocks)')
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
