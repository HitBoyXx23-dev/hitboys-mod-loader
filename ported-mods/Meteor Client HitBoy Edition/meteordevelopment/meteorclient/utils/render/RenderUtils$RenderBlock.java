/*     */ package meteordevelopment.meteorclient.utils.render;
/*     */ 
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2382;
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
/*     */ public class RenderBlock
/*     */ {
/* 122 */   public class_2338.class_2339 pos = new class_2338.class_2339();
/*     */   
/*     */   public Color sideColor;
/*     */   
/*     */   public Color lineColor;
/*     */   
/*     */   public ShapeMode shapeMode;
/*     */   public int excludeDir;
/*     */   
/*     */   public RenderBlock set(class_2338 blockPos, Color sideColor, Color lineColor, ShapeMode shapeMode, int excludeDir, int duration, boolean fade, boolean shrink) {
/* 132 */     this.pos.method_10101((class_2382)blockPos);
/* 133 */     this.sideColor = sideColor;
/* 134 */     this.lineColor = lineColor;
/* 135 */     this.shapeMode = shapeMode;
/* 136 */     this.excludeDir = excludeDir;
/* 137 */     this.fade = fade;
/* 138 */     this.shrink = shrink;
/* 139 */     this.ticks = duration;
/* 140 */     this.duration = duration;
/*     */     
/* 142 */     return this;
/*     */   }
/*     */   public int ticks; public int duration; public boolean fade; public boolean shrink;
/*     */   public void tick() {
/* 146 */     this.ticks--;
/*     */   }
/*     */   
/*     */   public void render(Render3DEvent event) {
/* 150 */     int preSideA = this.sideColor.a;
/* 151 */     int preLineA = this.lineColor.a;
/* 152 */     double x1 = this.pos.method_10263(), y1 = this.pos.method_10264(), z1 = this.pos.method_10260();
/* 153 */     double x2 = (this.pos.method_10263() + 1), y2 = (this.pos.method_10264() + 1), z2 = (this.pos.method_10260() + 1);
/*     */     
/* 155 */     double d = (this.ticks - event.tickDelta) / this.duration;
/*     */     
/* 157 */     if (this.fade) {
/* 158 */       this.sideColor.a = (int)(this.sideColor.a * d);
/* 159 */       this.lineColor.a = (int)(this.lineColor.a * d);
/*     */     } 
/* 161 */     if (this.shrink) {
/* 162 */       x1 += d; y1 += d; z1 += d;
/* 163 */       x2 -= d; y2 -= d; z2 -= d;
/*     */     } 
/*     */     
/* 166 */     event.renderer.box(x1, y1, z1, x2, y2, z2, this.sideColor, this.lineColor, this.shapeMode, this.excludeDir);
/*     */     
/* 168 */     this.sideColor.a = preSideA;
/* 169 */     this.lineColor.a = preLineA;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\RenderUtils$RenderBlock.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */