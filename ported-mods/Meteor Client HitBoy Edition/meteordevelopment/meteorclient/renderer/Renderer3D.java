/*     */ package meteordevelopment.meteorclient.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.world.Dir;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_4587;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Renderer3D
/*     */ {
/*     */   public final MeshBuilder lines;
/*     */   public final MeshBuilder triangles;
/*     */   private final RenderPipeline linesPipeline;
/*     */   private final RenderPipeline trianglesPipeline;
/*     */   
/*     */   public Renderer3D(RenderPipeline lines, RenderPipeline triangles) {
/*  23 */     this.lines = new MeshBuilder(lines);
/*  24 */     this.triangles = new MeshBuilder(triangles);
/*  25 */     this.linesPipeline = lines;
/*  26 */     this.trianglesPipeline = triangles;
/*     */   }
/*     */   
/*     */   public void begin() {
/*  30 */     this.lines.begin();
/*  31 */     this.triangles.begin();
/*     */   }
/*     */   
/*     */   public void render(class_4587 matrices) {
/*  35 */     MeshRenderer.begin()
/*  36 */       .attachments(class_310.method_1551().method_1522())
/*  37 */       .pipeline(this.linesPipeline)
/*  38 */       .mesh(this.lines, matrices)
/*  39 */       .end();
/*     */     
/*  41 */     MeshRenderer.begin()
/*  42 */       .attachments(class_310.method_1551().method_1522())
/*  43 */       .pipeline(this.trianglesPipeline)
/*  44 */       .mesh(this.triangles, matrices)
/*  45 */       .end();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void line(double x1, double y1, double z1, double x2, double y2, double z2, Color color1, Color color2) {
/*  51 */     this.lines.ensureLineCapacity();
/*     */     
/*  53 */     this.lines.line(this.lines
/*  54 */         .vec3(x1, y1, z1).color(color1).next(), this.lines
/*  55 */         .vec3(x2, y2, z2).color(color2).next());
/*     */   }
/*     */ 
/*     */   
/*     */   public void line(double x1, double y1, double z1, double x2, double y2, double z2, Color color) {
/*  60 */     line(x1, y1, z1, x2, y2, z2, color, color);
/*     */   }
/*     */ 
/*     */   
/*     */   public void boxLines(double x1, double y1, double z1, double x2, double y2, double z2, Color color, int excludeDir) {
/*  65 */     this.lines.ensureCapacity(8, 24);
/*     */     
/*  67 */     int blb = this.lines.vec3(x1, y1, z1).color(color).next();
/*  68 */     int blf = this.lines.vec3(x1, y1, z2).color(color).next();
/*  69 */     int brb = this.lines.vec3(x2, y1, z1).color(color).next();
/*  70 */     int brf = this.lines.vec3(x2, y1, z2).color(color).next();
/*  71 */     int tlb = this.lines.vec3(x1, y2, z1).color(color).next();
/*  72 */     int tlf = this.lines.vec3(x1, y2, z2).color(color).next();
/*  73 */     int trb = this.lines.vec3(x2, y2, z1).color(color).next();
/*  74 */     int trf = this.lines.vec3(x2, y2, z2).color(color).next();
/*     */     
/*  76 */     if (excludeDir == 0) {
/*     */       
/*  78 */       this.lines.line(blb, tlb);
/*  79 */       this.lines.line(blf, tlf);
/*  80 */       this.lines.line(brb, trb);
/*  81 */       this.lines.line(brf, trf);
/*     */ 
/*     */       
/*  84 */       this.lines.line(blb, blf);
/*  85 */       this.lines.line(brb, brf);
/*  86 */       this.lines.line(blb, brb);
/*  87 */       this.lines.line(blf, brf);
/*     */ 
/*     */       
/*  90 */       this.lines.line(tlb, tlf);
/*  91 */       this.lines.line(trb, trf);
/*  92 */       this.lines.line(tlb, trb);
/*  93 */       this.lines.line(tlf, trf);
/*     */     }
/*     */     else {
/*     */       
/*  97 */       if (Dir.isNot(excludeDir, (byte)32) && Dir.isNot(excludeDir, (byte)8)) this.lines.line(blb, tlb); 
/*  98 */       if (Dir.isNot(excludeDir, (byte)32) && Dir.isNot(excludeDir, (byte)16)) this.lines.line(blf, tlf); 
/*  99 */       if (Dir.isNot(excludeDir, (byte)64) && Dir.isNot(excludeDir, (byte)8)) this.lines.line(brb, trb); 
/* 100 */       if (Dir.isNot(excludeDir, (byte)64) && Dir.isNot(excludeDir, (byte)16)) this.lines.line(brf, trf);
/*     */ 
/*     */       
/* 103 */       if (Dir.isNot(excludeDir, (byte)32) && Dir.isNot(excludeDir, (byte)4)) this.lines.line(blb, blf); 
/* 104 */       if (Dir.isNot(excludeDir, (byte)64) && Dir.isNot(excludeDir, (byte)4)) this.lines.line(brb, brf); 
/* 105 */       if (Dir.isNot(excludeDir, (byte)8) && Dir.isNot(excludeDir, (byte)4)) this.lines.line(blb, brb); 
/* 106 */       if (Dir.isNot(excludeDir, (byte)16) && Dir.isNot(excludeDir, (byte)4)) this.lines.line(blf, brf);
/*     */ 
/*     */       
/* 109 */       if (Dir.isNot(excludeDir, (byte)32) && Dir.isNot(excludeDir, (byte)2)) this.lines.line(tlb, tlf); 
/* 110 */       if (Dir.isNot(excludeDir, (byte)64) && Dir.isNot(excludeDir, (byte)2)) this.lines.line(trb, trf); 
/* 111 */       if (Dir.isNot(excludeDir, (byte)8) && Dir.isNot(excludeDir, (byte)2)) this.lines.line(tlb, trb); 
/* 112 */       if (Dir.isNot(excludeDir, (byte)16) && Dir.isNot(excludeDir, (byte)2)) this.lines.line(tlf, trf); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void blockLines(int x, int y, int z, Color color, int excludeDir) {
/* 117 */     boxLines(x, y, z, (x + 1), (y + 1), (z + 1), color, excludeDir);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void quad(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, Color topLeft, Color topRight, Color bottomRight, Color bottomLeft) {
/* 123 */     this.triangles.ensureQuadCapacity();
/*     */     
/* 125 */     this.triangles.quad(this.triangles
/* 126 */         .vec3(x1, y1, z1).color(bottomLeft).next(), this.triangles
/* 127 */         .vec3(x2, y2, z2).color(topLeft).next(), this.triangles
/* 128 */         .vec3(x3, y3, z3).color(topRight).next(), this.triangles
/* 129 */         .vec3(x4, y4, z4).color(bottomRight).next());
/*     */   }
/*     */ 
/*     */   
/*     */   public void quad(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, Color color) {
/* 134 */     quad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, color, color, color, color);
/*     */   }
/*     */   
/*     */   public void quadVertical(double x1, double y1, double z1, double x2, double y2, double z2, Color color) {
/* 138 */     quad(x1, y1, z1, x1, y2, z1, x2, y2, z2, x2, y1, z2, color);
/*     */   }
/*     */   
/*     */   public void quadHorizontal(double x1, double y, double z1, double x2, double z2, Color color) {
/* 142 */     quad(x1, y, z1, x1, y, z2, x2, y, z2, x2, y, z1, color);
/*     */   }
/*     */   
/*     */   public void gradientQuadVertical(double x1, double y1, double z1, double x2, double y2, double z2, Color topColor, Color bottomColor) {
/* 146 */     quad(x1, y1, z1, x1, y2, z1, x2, y2, z2, x2, y1, z2, topColor, topColor, bottomColor, bottomColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void side(double x1, double y1, double z1, double x2, double y2, double z2, double x3, double y3, double z3, double x4, double y4, double z4, Color sideColor, Color lineColor, ShapeMode mode) {
/* 153 */     if (mode.lines()) {
/* 154 */       this.lines.ensureCapacity(4, 8);
/*     */       
/* 156 */       int i1 = this.lines.vec3(x1, y1, z1).color(lineColor).next();
/* 157 */       int i2 = this.lines.vec3(x2, y2, z2).color(lineColor).next();
/* 158 */       int i3 = this.lines.vec3(x3, y3, z3).color(lineColor).next();
/* 159 */       int i4 = this.lines.vec3(x4, y4, z4).color(lineColor).next();
/*     */       
/* 161 */       this.lines.line(i1, i2);
/* 162 */       this.lines.line(i2, i3);
/* 163 */       this.lines.line(i3, i4);
/* 164 */       this.lines.line(i4, i1);
/*     */     } 
/*     */     
/* 167 */     if (mode.sides()) {
/* 168 */       quad(x1, y1, z1, x2, y2, z2, x3, y3, z3, x4, y4, z4, sideColor);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sideVertical(double x1, double y1, double z1, double x2, double y2, double z2, Color sideColor, Color lineColor, ShapeMode mode) {
/* 173 */     side(x1, y1, z1, x1, y2, z1, x2, y2, z2, x2, y1, z2, sideColor, lineColor, mode);
/*     */   }
/*     */   
/*     */   public void sideHorizontal(double x1, double y, double z1, double x2, double z2, Color sideColor, Color lineColor, ShapeMode mode) {
/* 177 */     side(x1, y, z1, x1, y, z2, x2, y, z2, x2, y, z1, sideColor, lineColor, mode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void boxSides(double x1, double y1, double z1, double x2, double y2, double z2, Color color, int excludeDir) {
/* 184 */     this.triangles.ensureCapacity(8, 36);
/*     */     
/* 186 */     int blb = this.triangles.vec3(x1, y1, z1).color(color).next();
/* 187 */     int blf = this.triangles.vec3(x1, y1, z2).color(color).next();
/* 188 */     int brb = this.triangles.vec3(x2, y1, z1).color(color).next();
/* 189 */     int brf = this.triangles.vec3(x2, y1, z2).color(color).next();
/* 190 */     int tlb = this.triangles.vec3(x1, y2, z1).color(color).next();
/* 191 */     int tlf = this.triangles.vec3(x1, y2, z2).color(color).next();
/* 192 */     int trb = this.triangles.vec3(x2, y2, z1).color(color).next();
/* 193 */     int trf = this.triangles.vec3(x2, y2, z2).color(color).next();
/*     */     
/* 195 */     if (excludeDir == 0) {
/*     */       
/* 197 */       this.triangles.quad(blb, blf, tlf, tlb);
/* 198 */       this.triangles.quad(brb, trb, trf, brf);
/* 199 */       this.triangles.quad(blb, tlb, trb, brb);
/* 200 */       this.triangles.quad(blf, brf, trf, tlf);
/*     */ 
/*     */       
/* 203 */       this.triangles.quad(blb, brb, brf, blf);
/*     */ 
/*     */       
/* 206 */       this.triangles.quad(tlb, tlf, trf, trb);
/*     */     }
/*     */     else {
/*     */       
/* 210 */       if (Dir.isNot(excludeDir, (byte)32)) this.triangles.quad(blb, blf, tlf, tlb); 
/* 211 */       if (Dir.isNot(excludeDir, (byte)64)) this.triangles.quad(brb, trb, trf, brf); 
/* 212 */       if (Dir.isNot(excludeDir, (byte)8)) this.triangles.quad(blb, tlb, trb, brb); 
/* 213 */       if (Dir.isNot(excludeDir, (byte)16)) this.triangles.quad(blf, brf, trf, tlf);
/*     */ 
/*     */       
/* 216 */       if (Dir.isNot(excludeDir, (byte)4)) this.triangles.quad(blb, brb, brf, blf);
/*     */ 
/*     */       
/* 219 */       if (Dir.isNot(excludeDir, (byte)2)) this.triangles.quad(tlb, tlf, trf, trb); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void blockSides(int x, int y, int z, Color color, int excludeDir) {
/* 224 */     boxSides(x, y, z, (x + 1), (y + 1), (z + 1), color, excludeDir);
/*     */   }
/*     */   
/*     */   public void box(double x1, double y1, double z1, double x2, double y2, double z2, Color sideColor, Color lineColor, ShapeMode mode, int excludeDir) {
/* 228 */     if (mode.lines()) boxLines(x1, y1, z1, x2, y2, z2, lineColor, excludeDir); 
/* 229 */     if (mode.sides()) boxSides(x1, y1, z1, x2, y2, z2, sideColor, excludeDir); 
/*     */   }
/*     */   
/*     */   public void box(class_2338 pos, Color sideColor, Color lineColor, ShapeMode mode, int excludeDir) {
/* 233 */     if (mode.lines()) boxLines(pos.method_10263(), pos.method_10264(), pos.method_10260(), (pos.method_10263() + 1), (pos.method_10264() + 1), (pos.method_10260() + 1), lineColor, excludeDir); 
/* 234 */     if (mode.sides()) boxSides(pos.method_10263(), pos.method_10264(), pos.method_10260(), (pos.method_10263() + 1), (pos.method_10264() + 1), (pos.method_10260() + 1), sideColor, excludeDir); 
/*     */   }
/*     */   
/*     */   public void box(class_238 box, Color sideColor, Color lineColor, ShapeMode mode, int excludeDir) {
/* 238 */     if (mode.lines()) boxLines(box.field_1323, box.field_1322, box.field_1321, box.field_1320, box.field_1325, box.field_1324, lineColor, excludeDir); 
/* 239 */     if (mode.sides()) boxSides(box.field_1323, box.field_1322, box.field_1321, box.field_1320, box.field_1325, box.field_1324, sideColor, excludeDir); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\Renderer3D.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */