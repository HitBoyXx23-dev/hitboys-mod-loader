/*     */ package meteordevelopment.meteorclient.renderer.text;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_327;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_9799;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VanillaTextRenderer
/*     */   implements TextRenderer
/*     */ {
/*  21 */   public static final VanillaTextRenderer INSTANCE = new VanillaTextRenderer();
/*     */   
/*  23 */   private final class_9799 buffer = new class_9799(2048);
/*  24 */   private final class_4597.class_4598 immediate = class_4597.method_22991(this.buffer);
/*     */   
/*  26 */   private final class_4587 matrices = new class_4587();
/*  27 */   private final Matrix4f emptyMatrix = new Matrix4f();
/*     */   
/*  29 */   public double scale = 2.0D;
/*     */   
/*     */   public boolean scaleIndividually;
/*     */   private boolean building;
/*  33 */   private double alpha = 1.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlpha(double a) {
/*  41 */     this.alpha = a;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getWidth(String text, int length, boolean shadow) {
/*  46 */     if (text.isEmpty()) return 0.0D;
/*     */     
/*  48 */     if (length != text.length()) text = text.substring(0, length); 
/*  49 */     return (MeteorClient.mc.field_1772.method_1727(text) + (shadow ? 1 : 0)) * this.scale;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getHeight(boolean shadow) {
/*  54 */     Objects.requireNonNull(MeteorClient.mc.field_1772); return (9 + (shadow ? 1 : 0)) * this.scale;
/*     */   }
/*     */ 
/*     */   
/*     */   public void begin(double scale, boolean scaleOnly, boolean big) {
/*  59 */     if (this.building) throw new RuntimeException("VanillaTextRenderer.begin() called twice");
/*     */     
/*  61 */     this.scale = scale * 2.0D;
/*  62 */     this.building = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public double render(String text, double x, double y, Color color, boolean shadow) {
/*  67 */     boolean wasBuilding = this.building;
/*  68 */     if (!wasBuilding) begin();
/*     */     
/*  70 */     x += 0.5D * this.scale;
/*  71 */     y += 0.5D * this.scale;
/*     */     
/*  73 */     int preA = color.a;
/*  74 */     color.a = (int)(color.a / 255.0D * this.alpha * 255.0D);
/*     */     
/*  76 */     Matrix4f matrix = this.emptyMatrix;
/*  77 */     if (this.scaleIndividually) {
/*  78 */       this.matrices.method_22903();
/*  79 */       this.matrices.method_22905((float)this.scale, (float)this.scale, 1.0F);
/*  80 */       matrix = this.matrices.method_23760().method_23761();
/*     */     } 
/*     */     
/*  83 */     MeteorClient.mc.field_1772.method_27521(text, (float)(x / this.scale), (float)(y / this.scale), color.getPacked(), shadow, matrix, (class_4597)this.immediate, class_327.class_6415.field_33993, 0, 15728880);
/*  84 */     double x2 = x / this.scale + MeteorClient.mc.field_1772.method_1727(text);
/*     */     
/*  86 */     if (this.scaleIndividually) this.matrices.method_22909();
/*     */     
/*  88 */     color.a = preA;
/*     */     
/*  90 */     if (!wasBuilding) end(); 
/*  91 */     return (x2 - 1.0D) * this.scale;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBuilding() {
/*  96 */     return this.building;
/*     */   }
/*     */ 
/*     */   
/*     */   public void end() {
/* 101 */     if (!this.building) throw new RuntimeException("VanillaTextRenderer.end() called without calling begin()");
/*     */     
/* 103 */     Matrix4fStack matrixStack = RenderSystem.getModelViewStack();
/*     */     
/* 105 */     matrixStack.pushMatrix();
/* 106 */     if (!this.scaleIndividually) matrixStack.scale((float)this.scale, (float)this.scale, 1.0F);
/*     */     
/* 108 */     this.immediate.method_22993();
/*     */     
/* 110 */     matrixStack.popMatrix();
/*     */     
/* 112 */     this.scale = 2.0D;
/* 113 */     this.building = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\text\VanillaTextRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */