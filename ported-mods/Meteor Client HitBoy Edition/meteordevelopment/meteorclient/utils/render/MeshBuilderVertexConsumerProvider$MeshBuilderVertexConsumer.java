/*     */ package meteordevelopment.meteorclient.utils.render;
/*     */ 
/*     */ import meteordevelopment.meteorclient.renderer.MeshBuilder;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_4588;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeshBuilderVertexConsumer
/*     */   implements class_4588
/*     */ {
/*     */   private final MeshBuilder mesh;
/*     */   private int offsetX;
/*     */   private int offsetY;
/*     */   private int offsetZ;
/*  74 */   private final double[] xs = new double[4];
/*  75 */   private final double[] ys = new double[4];
/*  76 */   private final double[] zs = new double[4];
/*  77 */   private final Color color = new Color();
/*     */   
/*     */   private int i;
/*     */   
/*     */   public MeshBuilderVertexConsumer(MeshBuilder mesh) {
/*  82 */     this.mesh = mesh;
/*     */   }
/*     */   
/*     */   public void setOffset(int offsetX, int offsetY, int offsetZ) {
/*  86 */     this.offsetX = offsetX;
/*  87 */     this.offsetY = offsetY;
/*  88 */     this.offsetZ = offsetZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22912(float x, float y, float z) {
/*  93 */     this.xs[this.i] = this.offsetX + x;
/*  94 */     this.ys[this.i] = this.offsetY + y;
/*  95 */     this.zs[this.i] = this.offsetZ + z;
/*     */     
/*  97 */     if (++this.i >= 4) {
/*  98 */       this.mesh.ensureQuadCapacity();
/*     */       
/* 100 */       this.mesh.quad(this.mesh
/* 101 */           .vec3(this.xs[0], this.ys[0], this.zs[0]).color(this.color).next(), this.mesh
/* 102 */           .vec3(this.xs[1], this.ys[1], this.zs[1]).color(this.color).next(), this.mesh
/* 103 */           .vec3(this.xs[2], this.ys[2], this.zs[2]).color(this.color).next(), this.mesh
/* 104 */           .vec3(this.xs[3], this.ys[3], this.zs[3]).color(this.color).next());
/*     */ 
/*     */       
/* 107 */       this.i = 0;
/*     */     } 
/*     */     
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_1336(int red, int green, int blue, int alpha) {
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_39415(int argb) {
/* 120 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22913(float u, float v) {
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_60796(int u, int v) {
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22921(int u, int v) {
/* 135 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22914(float x, float y, float z) {
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_75298(float width) {
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   public void fixedColor(int red, int green, int blue, int alpha) {
/* 149 */     this.color.set(red, green, blue, alpha);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\MeshBuilderVertexConsumerProvider$MeshBuilderVertexConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */