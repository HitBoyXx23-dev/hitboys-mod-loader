/*     */ package meteordevelopment.meteorclient.utils.render;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import meteordevelopment.meteorclient.mixin.RenderLayerAccessor;
/*     */ import net.minecraft.class_12246;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MyVertexConsumerProvider
/*     */   extends class_4597.class_4598
/*     */ {
/*  88 */   public static final MyVertexConsumerProvider INSTANCE = new MyVertexConsumerProvider();
/*  89 */   private final Object2ObjectOpenHashMap<class_1921, WireframeEntityRenderer.MyVertexConsumer> buffers = new Object2ObjectOpenHashMap();
/*     */   
/*     */   protected MyVertexConsumerProvider() {
/*  92 */     super(null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public class_4588 method_73477(class_1921 layer) {
/*  97 */     if ((((RenderLayerAccessor)layer).getRenderSetup()).field_63989 == class_12246.field_63983) {
/*  98 */       return NoopVertexConsumer.INSTANCE;
/*     */     }
/*     */     
/* 101 */     WireframeEntityRenderer.MyVertexConsumer vertexConsumer = (WireframeEntityRenderer.MyVertexConsumer)this.buffers.get(layer);
/*     */     
/* 103 */     if (vertexConsumer == null) {
/* 104 */       vertexConsumer = new WireframeEntityRenderer.MyVertexConsumer();
/* 105 */       this.buffers.put(layer, vertexConsumer);
/*     */     } 
/*     */     
/* 108 */     return vertexConsumer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_22993() {
/* 113 */     throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_22994(class_1921 layer) {
/* 118 */     throw new RuntimeException();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\WireframeEntityRenderer$MyVertexConsumerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */