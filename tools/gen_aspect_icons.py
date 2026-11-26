#!/usr/bin/env python3
"""Icons of the mod's Thaumaturge aspects: python tools/gen_aspect_icons.py (needs Pillow)

Writes neoforge/src/main/resources/assets/the_deep_void/textures/aspects/<aspect>.png: 32x32, white and grey with alpha
like Thaumaturge's own icons (Thaumaturge tints them with the aspect color). Drawn at 8x and scaled down.
"""
import math
from pathlib import Path

from PIL import Image, ImageDraw

ROOT = Path(__file__).resolve().parent.parent
OUT = ROOT / "neoforge/src/main/resources/assets/the_deep_void/textures/aspects"
S = 256  # drawing size, scaled down to 32
WHITE = (255, 255, 255, 255)
GREY = (150, 150, 150, 255)
CLEAR = (0, 0, 0, 0)


def canvas():
    im = Image.new("RGBA", (S, S), CLEAR)
    return im, ImageDraw.Draw(im)


def abyssus():
    """A vortex: a spiral that narrows into a dark center"""
    im, d = canvas()
    c = S / 2
    turns, steps = 2.6, 600
    pts = []
    for i in range(steps + 1):
        t = i / steps
        a = t * turns * 2 * math.pi
        r = 118 * (1 - t) + 10 * t
        pts.append((c + r * math.cos(a), c + r * math.sin(a)))
    for i in range(len(pts) - 1):
        t = i / steps
        w = int(26 * (1 - t) + 6 * t)
        shade = int(255 - 105 * t)
        d.line([pts[i], pts[i + 1]], fill=(shade, shade, shade, 255), width=w)
        d.ellipse([pts[i][0] - w / 2, pts[i][1] - w / 2, pts[i][0] + w / 2, pts[i][1] + w / 2], fill=(shade, shade, shade, 255))
    d.ellipse([c - 14, c - 14, c + 14, c + 14], fill=CLEAR)
    return im


def caro():
    """A slab of raw flesh with sinew"""
    im, d = canvas()
    c = S / 2
    pts = []
    for i in range(72):
        a = i / 72 * 2 * math.pi
        r = 104 + 10 * math.sin(3 * a + 0.6) + 6 * math.sin(7 * a)
        pts.append((c + r * math.cos(a) * 1.08, c + r * math.sin(a) * 0.86))
    d.polygon(pts, fill=WHITE)
    for k, (dy, amp) in enumerate(((-46, 16), (2, 20), (48, 14))):
        line = [(c - 92 + x, c + dy + amp * math.sin(x / 26 + k)) for x in range(0, 185, 4)]
        d.line(line, fill=GREY, width=14, joint="curve")
    d.ellipse([c + 30, c - 30, c + 66, c + 4], fill=(200, 200, 200, 255))
    return im


def os_():
    """A bone, corner to corner"""
    im, d = canvas()
    shaft = Image.new("RGBA", (S, S), CLEAR)
    sd = ImageDraw.Draw(shaft)
    c = S / 2
    sd.rectangle([c - 84, c - 20, c + 84, c + 20], fill=WHITE)
    for x in (c - 92, c + 92):
        for y in (c - 26, c + 26):
            sd.ellipse([x - 32, y - 32, x + 32, y + 32], fill=WHITE)
    sd.line([(c - 70, c), (c + 70, c)], fill=(205, 205, 205, 255), width=8)
    return shaft.rotate(45, resample=Image.BICUBIC)


def oculus():
    """A watching eye with a slit pupil"""
    im, d = canvas()
    c = S / 2
    upper = [(c + 120 * math.cos(a), c - 76 * math.sin(a)) for a in (i / 60 * math.pi for i in range(61))]
    lower = [(c - 120 * math.cos(a), c + 76 * math.sin(a)) for a in (i / 60 * math.pi for i in range(61))]
    d.polygon(upper + lower, fill=WHITE)
    d.ellipse([c - 58, c - 58, c + 58, c + 58], fill=GREY)
    d.ellipse([c - 44, c - 44, c + 44, c + 44], fill=(200, 200, 200, 255))
    d.ellipse([c - 14, c - 42, c + 14, c + 42], fill=CLEAR)
    for i in range(5):
        a = math.pi * (0.2 + 0.15 * i)
        x, y = c + 128 * math.cos(a), c - 84 * math.sin(a)
        d.line([(x, y), (c + 152 * math.cos(a), c - 116 * math.sin(a))], fill=WHITE, width=12)
    return im


def main():
    OUT.mkdir(parents=True, exist_ok=True)
    for name, draw in (("abyssus", abyssus), ("caro", caro), ("os", os_), ("oculus", oculus)):
        draw().resize((32, 32), Image.LANCZOS).save(OUT / f"{name}.png", optimize=True)
        print(OUT.relative_to(ROOT) / f"{name}.png")


if __name__ == "__main__":
    main()
