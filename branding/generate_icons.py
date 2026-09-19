"""
Regenerates HitBoy's flat monogram logo at all needed sizes/formats from a
single source-of-truth drawing routine, so every asset (PNG icons, launcher
icon, .ico multi-res bundles) stays pixel-consistent with branding/HitBoy.svg.
"""
from PIL import Image, ImageDraw

BACKGROUND = (0x12, 0x16, 0x1f, 255)
ACCENT = (0x4c, 0x8d, 0xff, 255)


def draw_logo(size: int) -> Image.Image:
    # Render at 4x supersampling then downscale for crisp edges at small sizes.
    scale = 4
    canvas_size = size * scale
    img = Image.new("RGBA", (canvas_size, canvas_size), (0, 0, 0, 0))
    draw = ImageDraw.Draw(img)

    radius = int(canvas_size * (96 / 512))
    draw.rounded_rectangle([0, 0, canvas_size - 1, canvas_size - 1], radius=radius, fill=BACKGROUND)

    def s(v: float) -> int:
        return round(v / 512 * canvas_size)

    # Left vertical bar of the "H"
    draw.rectangle([s(144), s(120), s(144 + 64), s(120 + 272)], fill=ACCENT)
    # Right vertical bar
    draw.rectangle([s(304), s(120), s(304 + 64), s(120 + 272)], fill=ACCENT)
    # Middle horizontal bar
    draw.rectangle([s(144), s(224), s(144 + 224), s(224 + 64)], fill=ACCENT)

    return img.resize((size, size), Image.LANCZOS)


def main():
    import os

    root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

    targets_png = {
        os.path.join(root, "branding", "HitBoy-1024.png"): 1024,
        os.path.join(root, "launcher", "src", "main", "resources", "icon.png"): 256,
        os.path.join(root, "hitboy-rust-launcher", "HitBoy.png"): 256,
    }
    for path, size in targets_png.items():
        img = draw_logo(size)
        img.save(path)
        print(f"Wrote {path} ({size}x{size})")

    ico_sizes = [16, 32, 48, 64, 128, 256]
    ico_targets = [
        os.path.join(root, "launcher", "HitBoy.ico"),
        os.path.join(root, "hitboy-rust-launcher", "HitBoy.ico"),
    ]
    base = draw_logo(256)
    for path in ico_targets:
        base.save(path, sizes=[(s, s) for s in ico_sizes])
        print(f"Wrote {path} ({ico_sizes})")


if __name__ == "__main__":
    main()
