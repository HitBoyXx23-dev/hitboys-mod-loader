/*     */ package meteordevelopment.meteorclient.utils.render;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.mixin.RenderLayerAccessor;
/*     */ import meteordevelopment.meteorclient.renderer.Renderer3D;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_10017;
/*     */ import net.minecraft.class_11659;
/*     */ import net.minecraft.class_11661;
/*     */ import net.minecraft.class_11684;
/*     */ import net.minecraft.class_12246;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_897;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WireframeEntityRenderer
/*     */ {
/*  30 */   private static final class_4587 matrices = new class_4587();
/*     */   
/*     */   private static Renderer3D renderer;
/*     */   
/*  34 */   private static final class_11661 renderCommandQueue = new class_11661();
/*     */   
/*  36 */   private static final class_11684 renderDispatcher = new class_11684(renderCommandQueue, MeteorClient.mc
/*     */       
/*  38 */       .method_1541(), MyVertexConsumerProvider.INSTANCE, MeteorClient.mc
/*     */       
/*  40 */       .method_72703(), NoopOutlineVertexConsumerProvider.INSTANCE, NoopImmediateVertexConsumerProvider.INSTANCE, MeteorClient.mc.field_1772);
/*     */ 
/*     */   
/*     */   private static Color sideColor;
/*     */ 
/*     */   
/*     */   private static Color lineColor;
/*     */ 
/*     */   
/*     */   private static ShapeMode shapeMode;
/*     */   
/*     */   private static double offsetX;
/*     */   
/*     */   private static double offsetY;
/*     */   
/*     */   private static double offsetZ;
/*     */ 
/*     */   
/*     */   public static void render(Render3DEvent event, class_1297 entity, double scale, Color sideColor, Color lineColor, ShapeMode shapeMode) {
/*  59 */     WireframeEntityRenderer.renderer = event.renderer;
/*  60 */     WireframeEntityRenderer.sideColor = sideColor;
/*  61 */     WireframeEntityRenderer.lineColor = lineColor;
/*  62 */     WireframeEntityRenderer.shapeMode = shapeMode;
/*     */     
/*  64 */     float tickDelta = MeteorClient.mc.field_1687.method_54719().method_54754() ? 1.0F : event.tickDelta;
/*     */     
/*  66 */     offsetX = class_3532.method_16436(tickDelta, entity.field_6038, entity.method_23317());
/*  67 */     offsetY = class_3532.method_16436(tickDelta, entity.field_5971, entity.method_23318());
/*  68 */     offsetZ = class_3532.method_16436(tickDelta, entity.field_5989, entity.method_23321());
/*     */     
/*  70 */     class_897<class_1297, class_10017> renderer = MeteorClient.mc.method_1561().method_3953(entity);
/*  71 */     class_10017 state = renderer.method_62425(entity, tickDelta);
/*     */     
/*  73 */     class_243 entityOffset = renderer.method_23169(state);
/*  74 */     offsetX += entityOffset.field_1352;
/*  75 */     offsetY += entityOffset.field_1351;
/*  76 */     offsetZ += entityOffset.field_1350;
/*     */     
/*  78 */     matrices.method_22903();
/*  79 */     matrices.method_22905((float)scale, (float)scale, (float)scale);
/*  80 */     renderer.method_3936(state, matrices, (class_11659)renderCommandQueue, (MeteorClient.mc.field_1773.method_72912()).field_63082);
/*  81 */     matrices.method_22909();
/*     */     
/*  83 */     renderDispatcher.method_73002();
/*  84 */     renderCommandQueue.method_72954();
/*     */   }
/*     */   
/*     */   private static class MyVertexConsumerProvider extends class_4597.class_4598 {
/*  88 */     public static final MyVertexConsumerProvider INSTANCE = new MyVertexConsumerProvider();
/*  89 */     private final Object2ObjectOpenHashMap<class_1921, WireframeEntityRenderer.MyVertexConsumer> buffers = new Object2ObjectOpenHashMap();
/*     */     
/*     */     protected MyVertexConsumerProvider() {
/*  92 */       super(null, null);
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_73477(class_1921 layer) {
/*  97 */       if ((((RenderLayerAccessor)layer).getRenderSetup()).field_63989 == class_12246.field_63983) {
/*  98 */         return NoopVertexConsumer.INSTANCE;
/*     */       }
/*     */       
/* 101 */       WireframeEntityRenderer.MyVertexConsumer vertexConsumer = (WireframeEntityRenderer.MyVertexConsumer)this.buffers.get(layer);
/*     */       
/* 103 */       if (vertexConsumer == null) {
/* 104 */         vertexConsumer = new WireframeEntityRenderer.MyVertexConsumer();
/* 105 */         this.buffers.put(layer, vertexConsumer);
/*     */       } 
/*     */       
/* 108 */       return vertexConsumer;
/*     */     }
/*     */ 
/*     */     
/*     */     public void method_22993() {
/* 113 */       throw new RuntimeException();
/*     */     }
/*     */ 
/*     */     
/*     */     public void method_22994(class_1921 layer) {
/* 118 */       throw new RuntimeException();
/*     */     } }
/*     */   private static class MyVertexConsumer implements class_4588 { private final float[] xs; private final float[] ys;
/*     */     
/*     */     private MyVertexConsumer() {
/* 123 */       this.xs = new float[4];
/* 124 */       this.ys = new float[4];
/* 125 */       this.zs = new float[4];
/*     */       
/* 127 */       this.i = 0;
/*     */     }
/*     */     private final float[] zs; private int i;
/*     */     public class_4588 method_22912(float x, float y, float z) {
/* 131 */       this.xs[this.i] = x;
/* 132 */       this.ys[this.i] = y;
/* 133 */       this.zs[this.i] = z;
/*     */       
/* 135 */       this.i++;
/*     */       
/* 137 */       if (this.i == 4) {
/* 138 */         WireframeEntityRenderer.renderer.side(WireframeEntityRenderer.offsetX + this.xs[0], WireframeEntityRenderer.offsetY + this.ys[0], WireframeEntityRenderer.offsetZ + this.zs[0], WireframeEntityRenderer.offsetX + this.xs[1], WireframeEntityRenderer.offsetY + this.ys[1], WireframeEntityRenderer.offsetZ + this.zs[1], WireframeEntityRenderer.offsetX + this.xs[2], WireframeEntityRenderer.offsetY + this.ys[2], WireframeEntityRenderer.offsetZ + this.zs[2], WireframeEntityRenderer.offsetX + this.xs[3], WireframeEntityRenderer.offsetY + this.ys[3], WireframeEntityRenderer.offsetZ + this.zs[3], WireframeEntityRenderer.sideColor, WireframeEntityRenderer.lineColor, WireframeEntityRenderer.shapeMode);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 148 */         this.i = 0;
/*     */       } 
/*     */       
/* 151 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_1336(int red, int green, int blue, int alpha) {
/* 156 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_39415(int argb) {
/* 161 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_22913(float u, float v) {
/* 166 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_60796(int u, int v) {
/* 171 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_22921(int u, int v) {
/* 176 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_22914(float x, float y, float z) {
/* 181 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public class_4588 method_75298(float width) {
/* 186 */       return this;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\WireframeEntityRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */