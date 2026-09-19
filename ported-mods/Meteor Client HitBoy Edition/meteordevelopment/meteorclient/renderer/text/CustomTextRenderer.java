/*     */ package meteordevelopment.meteorclient.renderer.text;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import meteordevelopment.meteorclient.renderer.MeshBuilder;
/*     */ import meteordevelopment.meteorclient.renderer.MeshRenderer;
/*     */ import meteordevelopment.meteorclient.renderer.MeteorRenderPipelines;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_310;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomTextRenderer
/*     */   implements TextRenderer
/*     */ {
/*  18 */   public static final Color SHADOW_COLOR = new Color(60, 60, 60, 180);
/*     */   
/*  20 */   private final MeshBuilder mesh = new MeshBuilder(MeteorRenderPipelines.UI_TEXT);
/*     */   
/*     */   public final FontFace fontFace;
/*     */   
/*     */   private final Font[] fonts;
/*     */   
/*     */   private Font font;
/*     */   private boolean building;
/*     */   private boolean scaleOnly;
/*  29 */   private double fontScale = 1.0D;
/*  30 */   private double scale = 1.0D;
/*     */   
/*     */   public CustomTextRenderer(FontFace fontFace) throws IOException {
/*  33 */     this.fontFace = fontFace;
/*     */     
/*  35 */     ByteBuffer buffer = fontFace.readToDirectByteBuffer();
/*     */     
/*  37 */     this.fonts = new Font[5];
/*  38 */     for (int i = 0; i < this.fonts.length; i++) {
/*  39 */       this.fonts[i] = new Font(buffer, (int)Math.round(27.0D * (i * 0.5D + 1.0D)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlpha(double a) {
/*  45 */     this.mesh.alpha = a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void begin(double scale, boolean scaleOnly, boolean big) {
/*  50 */     if (this.building) throw new RuntimeException("CustomTextRenderer.begin() called twice");
/*     */     
/*  52 */     if (!scaleOnly) this.mesh.begin();
/*     */     
/*  54 */     if (big) {
/*  55 */       this.font = this.fonts[this.fonts.length - 1];
/*     */     } else {
/*     */       int scaleI;
/*  58 */       double scaleA = Math.floor(scale * 10.0D) / 10.0D;
/*     */ 
/*     */       
/*  61 */       if (scaleA >= 3.0D) { scaleI = 5; }
/*  62 */       else if (scaleA >= 2.5D) { scaleI = 4; }
/*  63 */       else if (scaleA >= 2.0D) { scaleI = 3; }
/*  64 */       else if (scaleA >= 1.5D) { scaleI = 2; }
/*  65 */       else { scaleI = 1; }
/*     */       
/*  67 */       this.font = this.fonts[scaleI - 1];
/*     */     } 
/*     */     
/*  70 */     this.building = true;
/*  71 */     this.scaleOnly = scaleOnly;
/*     */     
/*  73 */     this.fontScale = this.font.getHeight() / 27.0D;
/*  74 */     this.scale = 1.0D + (scale - this.fontScale) / this.fontScale;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getWidth(String text, int length, boolean shadow) {
/*  79 */     if (text.isEmpty()) return 0.0D;
/*     */     
/*  81 */     Font font = this.building ? this.font : this.fonts[0];
/*  82 */     return (font.getWidth(text, length) + (shadow ? true : false)) * this.scale / 1.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getHeight(boolean shadow) {
/*  87 */     Font font = this.building ? this.font : this.fonts[0];
/*  88 */     return (font.getHeight() + 1 + (shadow ? 1 : 0)) * this.scale / 1.5D;
/*     */   }
/*     */   
/*     */   public double render(String text, double x, double y, Color color, boolean shadow) {
/*     */     double width;
/*  93 */     boolean wasBuilding = this.building;
/*  94 */     if (!wasBuilding) begin();
/*     */ 
/*     */     
/*  97 */     if (shadow) {
/*  98 */       int preShadowA = SHADOW_COLOR.a;
/*  99 */       SHADOW_COLOR.a = (int)(color.a / 255.0D * preShadowA);
/*     */       
/* 101 */       width = this.font.render(this.mesh, text, x + this.fontScale * this.scale / 1.5D, y + this.fontScale * this.scale / 1.5D, SHADOW_COLOR, this.scale / 1.5D);
/* 102 */       this.font.render(this.mesh, text, x, y, color, this.scale / 1.5D);
/*     */       
/* 104 */       SHADOW_COLOR.a = preShadowA;
/*     */     } else {
/*     */       
/* 107 */       width = this.font.render(this.mesh, text, x, y, color, this.scale / 1.5D);
/*     */     } 
/*     */     
/* 110 */     if (!wasBuilding) end(); 
/* 111 */     return width;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuilding() {
/* 116 */     return this.building;
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/* 121 */     if (!this.building) throw new RuntimeException("CustomTextRenderer.end() called without calling begin()");
/*     */     
/* 123 */     if (!this.scaleOnly) {
/* 124 */       this.mesh.end();
/*     */       
/* 126 */       MeshRenderer.begin()
/* 127 */         .attachments(class_310.method_1551().method_1522())
/* 128 */         .pipeline(MeteorRenderPipelines.UI_TEXT)
/* 129 */         .mesh(this.mesh)
/* 130 */         .sampler("u_Texture", this.font.texture.method_71659(), this.font.texture.method_75484())
/* 131 */         .end();
/*     */     } 
/*     */     
/* 134 */     this.building = false;
/* 135 */     this.scale = 1.0D;
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 139 */     for (Font font : this.fonts)
/* 140 */       font.texture.close(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\text\CustomTextRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */