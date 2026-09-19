/*     */ package meteordevelopment.meteorclient.utils.render;
/*     */ 
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
/*     */ class MyVertexConsumer
/*     */   implements class_4588
/*     */ {
/* 123 */   private final float[] xs = new float[4];
/* 124 */   private final float[] ys = new float[4];
/* 125 */   private final float[] zs = new float[4];
/*     */   
/* 127 */   private int i = 0;
/*     */ 
/*     */   
/*     */   public class_4588 method_22912(float x, float y, float z) {
/* 131 */     this.xs[this.i] = x;
/* 132 */     this.ys[this.i] = y;
/* 133 */     this.zs[this.i] = z;
/*     */     
/* 135 */     this.i++;
/*     */     
/* 137 */     if (this.i == 4) {
/* 138 */       WireframeEntityRenderer.renderer.side(WireframeEntityRenderer.offsetX + this.xs[0], WireframeEntityRenderer.offsetY + this.ys[0], WireframeEntityRenderer.offsetZ + this.zs[0], WireframeEntityRenderer.offsetX + this.xs[1], WireframeEntityRenderer.offsetY + this.ys[1], WireframeEntityRenderer.offsetZ + this.zs[1], WireframeEntityRenderer.offsetX + this.xs[2], WireframeEntityRenderer.offsetY + this.ys[2], WireframeEntityRenderer.offsetZ + this.zs[2], WireframeEntityRenderer.offsetX + this.xs[3], WireframeEntityRenderer.offsetY + this.ys[3], WireframeEntityRenderer.offsetZ + this.zs[3], WireframeEntityRenderer.sideColor, WireframeEntityRenderer.lineColor, WireframeEntityRenderer.shapeMode);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 148 */       this.i = 0;
/*     */     } 
/*     */     
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_1336(int red, int green, int blue, int alpha) {
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_39415(int argb) {
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22913(float u, float v) {
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_60796(int u, int v) {
/* 171 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22921(int u, int v) {
/* 176 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_22914(float x, float y, float z) {
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_75298(float width) {
/* 186 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\WireframeEntityRenderer$MyVertexConsumer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */