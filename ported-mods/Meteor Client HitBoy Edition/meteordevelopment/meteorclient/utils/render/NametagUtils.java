/*     */ package meteordevelopment.meteorclient.utils.render;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.Zoom;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import org.joml.Matrix3x2fStack;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fStack;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Vector3d;
/*     */ import org.joml.Vector3dc;
/*     */ import org.joml.Vector4f;
/*     */ 
/*     */ public class NametagUtils {
/*  19 */   private static final Vector4f vec4 = new Vector4f();
/*  20 */   private static final Vector4f mmMat4 = new Vector4f();
/*  21 */   private static final Vector4f pmMat4 = new Vector4f();
/*  22 */   private static final Vector3d camera = new Vector3d();
/*  23 */   private static final Vector3d cameraNegated = new Vector3d();
/*  24 */   private static final Matrix4f model = new Matrix4f();
/*  25 */   private static final Matrix4f projection = new Matrix4f();
/*     */ 
/*     */   
/*     */   private static double windowScale;
/*     */   
/*     */   public static double scale;
/*     */ 
/*     */   
/*     */   public static void onRender(Matrix4f modelView) {
/*  34 */     model.set((Matrix4fc)modelView);
/*  35 */     projection.set((Matrix4fc)RenderUtils.projection);
/*     */     
/*  37 */     Utils.set(camera, MeteorClient.mc.field_1773.method_19418().method_71156());
/*  38 */     cameraNegated.set((Vector3dc)camera);
/*  39 */     cameraNegated.negate();
/*     */     
/*  41 */     windowScale = MeteorClient.mc.method_22683().method_4476(1, false);
/*     */   }
/*     */   
/*     */   public static boolean to2D(Vector3d pos, double scale) {
/*  45 */     return to2D(pos, scale, true);
/*     */   }
/*     */   
/*     */   public static boolean to2D(Vector3d pos, double scale, boolean distanceScaling) {
/*  49 */     return to2D(pos, scale, distanceScaling, false);
/*     */   }
/*     */   
/*     */   public static boolean to2D(Vector3d pos, double scale, boolean distanceScaling, boolean allowBehind) {
/*  53 */     Zoom zoom = (Zoom)Modules.get().get(Zoom.class);
/*  54 */     NametagUtils.scale = scale * zoom.getScaling();
/*  55 */     if (distanceScaling) {
/*  56 */       NametagUtils.scale *= getScale(pos);
/*     */     }
/*     */     
/*  59 */     vec4.set(cameraNegated.x + pos.x, cameraNegated.y + pos.y, cameraNegated.z + pos.z, 1.0D);
/*     */     
/*  61 */     vec4.mul((Matrix4fc)model, mmMat4);
/*  62 */     mmMat4.mul((Matrix4fc)projection, pmMat4);
/*     */     
/*  64 */     boolean behind = (pmMat4.w <= 0.0F);
/*     */     
/*  66 */     if (behind && !allowBehind) return false;
/*     */     
/*  68 */     toScreen(pmMat4);
/*  69 */     double x = (pmMat4.x * MeteorClient.mc.method_22683().method_4489());
/*  70 */     double y = (pmMat4.y * MeteorClient.mc.method_22683().method_4506());
/*     */     
/*  72 */     if (behind) {
/*  73 */       x = MeteorClient.mc.method_22683().method_4489() - x;
/*  74 */       y = MeteorClient.mc.method_22683().method_4506() - y;
/*     */     } 
/*     */     
/*  77 */     if (Double.isInfinite(x) || Double.isInfinite(y)) return false;
/*     */     
/*  79 */     pos.set(x / windowScale, MeteorClient.mc.method_22683().method_4506() - y / windowScale, allowBehind ? pmMat4.w : pmMat4.z);
/*  80 */     return true;
/*     */   }
/*     */   
/*     */   public static void begin(Vector3d pos) {
/*  84 */     Matrix4fStack matrices = RenderSystem.getModelViewStack();
/*  85 */     begin(matrices, pos);
/*     */   }
/*     */   
/*     */   public static void begin(Vector3d pos, class_332 drawContext) {
/*  89 */     begin(pos);
/*     */     
/*  91 */     Matrix3x2fStack matrices = drawContext.method_51448();
/*  92 */     matrices.pushMatrix();
/*  93 */     matrices.scale(1.0F / MeteorClient.mc.method_22683().method_4495());
/*  94 */     matrices.translate((float)pos.x, (float)pos.y);
/*  95 */     matrices.scale((float)scale, (float)scale);
/*     */   }
/*     */   
/*     */   private static void begin(Matrix4fStack matrices, Vector3d pos) {
/*  99 */     matrices.pushMatrix();
/* 100 */     matrices.translate((float)pos.x, (float)pos.y, 0.0F);
/* 101 */     matrices.scale((float)scale, (float)scale, 1.0F);
/*     */   }
/*     */   
/*     */   public static void end() {
/* 105 */     RenderSystem.getModelViewStack().popMatrix();
/*     */   }
/*     */   
/*     */   public static void end(class_332 drawContext) {
/* 109 */     end();
/* 110 */     drawContext.method_51448().popMatrix();
/*     */   }
/*     */   
/*     */   private static double getScale(Vector3d pos) {
/* 114 */     double dist = camera.distance((Vector3dc)pos);
/* 115 */     return class_3532.method_15350(1.0D - dist * 0.01D, 0.5D, 2.147483647E9D);
/*     */   }
/*     */   
/*     */   private static void toScreen(Vector4f vec) {
/* 119 */     float newW = 1.0F / vec.w * 0.5F;
/*     */     
/* 121 */     vec.x = vec.x * newW + 0.5F;
/* 122 */     vec.y = vec.y * newW + 0.5F;
/* 123 */     vec.z = vec.z * newW + 0.5F;
/* 124 */     vec.w = newW;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\NametagUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */