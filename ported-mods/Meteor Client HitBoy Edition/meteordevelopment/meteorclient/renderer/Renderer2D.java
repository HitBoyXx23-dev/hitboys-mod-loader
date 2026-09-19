/*     */ package meteordevelopment.meteorclient.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.textures.GpuTextureView;
/*     */ import meteordevelopment.meteorclient.gui.renderer.packer.TextureRegion;
/*     */ import meteordevelopment.meteorclient.utils.PreInit;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_12137;
/*     */ import net.minecraft.class_310;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Renderer2D
/*     */ {
/*     */   public static Renderer2D COLOR;
/*     */   public static Renderer2D TEXTURE;
/*     */   private final boolean textured;
/*     */   public final MeshBuilder triangles;
/*     */   public final MeshBuilder lines;
/*     */   
/*     */   public Renderer2D(boolean textured) {
/*  25 */     this.textured = textured;
/*     */     
/*  27 */     this.triangles = new MeshBuilder(textured ? MeteorRenderPipelines.UI_TEXTURED : MeteorRenderPipelines.UI_COLORED);
/*  28 */     this.lines = new MeshBuilder(MeteorRenderPipelines.UI_COLORED_LINES);
/*     */   }
/*     */   
/*     */   @PreInit
/*     */   public static void init() {
/*  33 */     COLOR = new Renderer2D(false);
/*  34 */     TEXTURE = new Renderer2D(true);
/*     */   }
/*     */   
/*     */   public void setAlpha(double alpha) {
/*  38 */     this.triangles.alpha = alpha;
/*     */   }
/*     */   
/*     */   public void begin() {
/*  42 */     this.triangles.begin();
/*  43 */     this.lines.begin();
/*     */   }
/*     */   
/*     */   public void end() {
/*  47 */     this.triangles.end();
/*  48 */     this.lines.end();
/*     */   }
/*     */   
/*     */   public void render() {
/*  52 */     render(null, null, null);
/*     */   }
/*     */   
/*     */   public void render(GpuTextureView textureView, class_12137 sampler) {
/*  56 */     if (!this.textured) {
/*  57 */       throw new IllegalStateException("Tried to render with a texture with a non-textured Renderer2D");
/*     */     }
/*  59 */     render("u_Texture", textureView, sampler);
/*     */   }
/*     */   
/*     */   public void render(String samplerName, GpuTextureView samplerView, class_12137 sampler) {
/*  63 */     if (this.lines.isBuilding()) this.lines.end(); 
/*  64 */     if (this.triangles.isBuilding()) this.triangles.end();
/*     */     
/*  66 */     MeshRenderer.begin()
/*  67 */       .attachments(class_310.method_1551().method_1522())
/*  68 */       .pipeline(MeteorRenderPipelines.UI_COLORED_LINES)
/*  69 */       .mesh(this.lines)
/*  70 */       .end();
/*     */     
/*  72 */     MeshRenderer.begin()
/*  73 */       .attachments(class_310.method_1551().method_1522())
/*  74 */       .pipeline(this.textured ? MeteorRenderPipelines.UI_TEXTURED : MeteorRenderPipelines.UI_COLORED)
/*  75 */       .mesh(this.triangles)
/*  76 */       .sampler(samplerName, samplerView, sampler)
/*  77 */       .end();
/*     */   }
/*     */ 
/*     */   
/*     */   public void triangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {
/*  82 */     this.triangles.ensureTriCapacity();
/*     */     
/*  84 */     this.triangles.triangle(this.triangles
/*  85 */         .vec2(x1, y1).color(color).next(), this.triangles
/*  86 */         .vec2(x2, y2).color(color).next(), this.triangles
/*  87 */         .vec2(x3, y3).color(color).next());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void line(double x1, double y1, double x2, double y2, Color color) {
/*  94 */     this.lines.ensureLineCapacity();
/*     */     
/*  96 */     this.lines.line(this.lines
/*  97 */         .vec2(x1, y1).color(color).next(), this.lines
/*  98 */         .vec2(x2, y2).color(color).next());
/*     */   }
/*     */ 
/*     */   
/*     */   public void boxLines(double x, double y, double width, double height, Color color) {
/* 103 */     this.lines.ensureCapacity(4, 8);
/*     */     
/* 105 */     int i1 = this.lines.vec2(x, y).color(color).next();
/* 106 */     int i2 = this.lines.vec2(x, y + height).color(color).next();
/* 107 */     int i3 = this.lines.vec2(x + width, y + height).color(color).next();
/* 108 */     int i4 = this.lines.vec2(x + width, y).color(color).next();
/*     */     
/* 110 */     this.lines.line(i1, i2);
/* 111 */     this.lines.line(i2, i3);
/* 112 */     this.lines.line(i3, i4);
/* 113 */     this.lines.line(i4, i1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void quad(double x, double y, double width, double height, Color cTopLeft, Color cTopRight, Color cBottomRight, Color cBottomLeft) {
/* 119 */     this.triangles.ensureQuadCapacity();
/*     */     
/* 121 */     this.triangles.quad(this.triangles
/* 122 */         .vec2(x, y).color(cTopLeft).next(), this.triangles
/* 123 */         .vec2(x, y + height).color(cBottomLeft).next(), this.triangles
/* 124 */         .vec2(x + width, y + height).color(cBottomRight).next(), this.triangles
/* 125 */         .vec2(x + width, y).color(cTopRight).next());
/*     */   }
/*     */ 
/*     */   
/*     */   public void quad(double x, double y, double width, double height, Color color) {
/* 130 */     quad(x, y, width, height, color, color, color, color);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void texQuad(double x, double y, double width, double height, Color color) {
/* 136 */     this.triangles.ensureQuadCapacity();
/*     */     
/* 138 */     this.triangles.quad(this.triangles
/* 139 */         .vec2(x, y).vec2(0.0D, 0.0D).color(color).next(), this.triangles
/* 140 */         .vec2(x, y + height).vec2(0.0D, 1.0D).color(color).next(), this.triangles
/* 141 */         .vec2(x + width, y + height).vec2(1.0D, 1.0D).color(color).next(), this.triangles
/* 142 */         .vec2(x + width, y).vec2(1.0D, 0.0D).color(color).next());
/*     */   }
/*     */ 
/*     */   
/*     */   public void texQuad(double x, double y, double width, double height, TextureRegion texture, Color color) {
/* 147 */     this.triangles.ensureQuadCapacity();
/*     */     
/* 149 */     this.triangles.quad(this.triangles
/* 150 */         .vec2(x, y).vec2(texture.x1, texture.y1).color(color).next(), this.triangles
/* 151 */         .vec2(x, y + height).vec2(texture.x1, texture.y2).color(color).next(), this.triangles
/* 152 */         .vec2(x + width, y + height).vec2(texture.x2, texture.y2).color(color).next(), this.triangles
/* 153 */         .vec2(x + width, y).vec2(texture.x2, texture.y1).color(color).next());
/*     */   }
/*     */ 
/*     */   
/*     */   public void texQuad(double x, double y, double width, double height, double rotation, double texX1, double texY1, double texX2, double texY2, Color color) {
/* 158 */     this.triangles.ensureQuadCapacity();
/*     */     
/* 160 */     double rad = Math.toRadians(rotation);
/* 161 */     double cos = Math.cos(rad);
/* 162 */     double sin = Math.sin(rad);
/*     */     
/* 164 */     double oX = x + width / 2.0D;
/* 165 */     double oY = y + height / 2.0D;
/*     */     
/* 167 */     double _x1 = (x - oX) * cos - (y - oY) * sin + oX;
/* 168 */     double _y1 = (y - oY) * cos + (x - oX) * sin + oY;
/* 169 */     int i1 = this.triangles.vec2(_x1, _y1).vec2(texX1, texY1).color(color).next();
/*     */     
/* 171 */     double _x2 = (x - oX) * cos - (y + height - oY) * sin + oX;
/* 172 */     double _y2 = (y + height - oY) * cos + (x - oX) * sin + oY;
/* 173 */     int i2 = this.triangles.vec2(_x2, _y2).vec2(texX1, texY2).color(color).next();
/*     */     
/* 175 */     double _x3 = (x + width - oX) * cos - (y + height - oY) * sin + oX;
/* 176 */     double _y3 = (y + height - oY) * cos + (x + width - oX) * sin + oY;
/* 177 */     int i3 = this.triangles.vec2(_x3, _y3).vec2(texX2, texY2).color(color).next();
/*     */     
/* 179 */     double _x4 = (x + width - oX) * cos - (y - oY) * sin + oX;
/* 180 */     double _y4 = (y - oY) * cos + (x + width - oX) * sin + oY;
/* 181 */     int i4 = this.triangles.vec2(_x4, _y4).vec2(texX2, texY1).color(color).next();
/*     */     
/* 183 */     this.triangles.quad(i1, i2, i3, i4);
/*     */   }
/*     */   
/*     */   public void texQuad(double x, double y, double width, double height, double rotation, TextureRegion region, Color color) {
/* 187 */     texQuad(x, y, width, height, rotation, region.x1, region.y1, region.x2, region.y2, color);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\Renderer2D.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */