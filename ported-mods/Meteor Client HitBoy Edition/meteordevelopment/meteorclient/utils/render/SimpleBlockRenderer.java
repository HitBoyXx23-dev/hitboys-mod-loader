/*     */ package meteordevelopment.meteorclient.utils.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import net.minecraft.class_1087;
/*     */ import net.minecraft.class_10889;
/*     */ import net.minecraft.class_11659;
/*     */ import net.minecraft.class_11661;
/*     */ import net.minecraft.class_11684;
/*     */ import net.minecraft.class_11954;
/*     */ import net.minecraft.class_12249;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2464;
/*     */ import net.minecraft.class_2586;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4588;
/*     */ import net.minecraft.class_4597;
/*     */ import net.minecraft.class_5819;
/*     */ import net.minecraft.class_777;
/*     */ import net.minecraft.class_827;
/*     */ import org.joml.Vector3fc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SimpleBlockRenderer
/*     */ {
/*  34 */   private static final class_4587 MATRICES = new class_4587();
/*  35 */   private static final List<class_10889> PARTS = new ArrayList<>();
/*  36 */   private static final class_2350[] DIRECTIONS = class_2350.values();
/*  37 */   private static final class_5819 RANDOM = class_5819.method_43047();
/*     */   
/*  39 */   private static final class_11661 renderCommandQueue = new class_11661();
/*     */   
/*     */   private static class_4597 provider;
/*     */   
/*  43 */   private static final class_11684 renderDispatcher = new class_11684(renderCommandQueue, MeteorClient.mc
/*     */       
/*  45 */       .method_1541(), new WrapperImmediateVertexConsumerProvider(() -> provider), MeteorClient.mc
/*     */       
/*  47 */       .method_72703(), NoopOutlineVertexConsumerProvider.INSTANCE, NoopImmediateVertexConsumerProvider.INSTANCE, MeteorClient.mc.field_1772);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderWithBlockEntity(class_2586 blockEntity, float tickDelta, IVertexConsumerProvider vertexConsumerProvider) {
/*  56 */     vertexConsumerProvider.setOffset(blockEntity.method_11016().method_10263(), blockEntity.method_11016().method_10264(), blockEntity.method_11016().method_10260());
/*  57 */     render(blockEntity.method_11016(), blockEntity.method_11010(), vertexConsumerProvider);
/*     */     
/*  59 */     class_827<class_2586, class_11954> renderer = MeteorClient.mc.method_31975().method_3550(blockEntity);
/*     */     
/*  61 */     if (renderer != null && blockEntity.method_11002() && blockEntity.method_11017().method_20526(blockEntity.method_11010())) {
/*  62 */       provider = vertexConsumerProvider;
/*     */       
/*  64 */       class_11954 state = renderer.method_74335();
/*  65 */       renderer.method_74331(blockEntity, state, tickDelta, MeteorClient.mc.field_1773.method_19418().method_71156(), null);
/*  66 */       renderer.method_3569(state, MATRICES, (class_11659)renderCommandQueue, (MeteorClient.mc.field_1773.method_72912()).field_63082);
/*     */       
/*  68 */       renderDispatcher.method_73002();
/*  69 */       renderCommandQueue.method_72954();
/*     */       
/*  71 */       provider = null;
/*     */     } 
/*     */     
/*  74 */     vertexConsumerProvider.setOffset(0, 0, 0);
/*     */   }
/*     */   
/*     */   public static void render(class_2338 pos, class_2680 state, class_4597 consumerProvider) {
/*  78 */     if (state.method_26217() != class_2464.field_11458)
/*     */       return; 
/*  80 */     class_4588 consumer = consumerProvider.method_73477(class_12249.method_75965());
/*     */     
/*  82 */     class_1087 model = MeteorClient.mc.method_1541().method_3349(state);
/*  83 */     model.method_68513(RANDOM, PARTS);
/*     */     
/*  85 */     class_243 offset = state.method_26226(pos);
/*  86 */     float offsetX = (float)offset.field_1352;
/*  87 */     float offsetY = (float)offset.field_1351;
/*  88 */     float offsetZ = (float)offset.field_1350;
/*     */     
/*  90 */     for (class_10889 part : PARTS) {
/*  91 */       for (class_2350 direction : DIRECTIONS) {
/*  92 */         List<class_777> list = part.method_68509(direction);
/*  93 */         if (!list.isEmpty()) renderQuads(list, offsetX, offsetY, offsetZ, consumer);
/*     */       
/*     */       } 
/*  96 */       List<class_777> quads = part.method_68509(null);
/*  97 */       if (!quads.isEmpty()) renderQuads(quads, offsetX, offsetY, offsetZ, consumer);
/*     */     
/*     */     } 
/* 100 */     PARTS.clear();
/*     */   }
/*     */   
/*     */   private static void renderQuads(List<class_777> quads, float offsetX, float offsetY, float offsetZ, class_4588 consumer) {
/* 104 */     for (class_777 quad : quads) {
/* 105 */       for (int j = 0; j < 4; j++) {
/* 106 */         Vector3fc vec = quad.method_76648(j);
/* 107 */         consumer.method_22912(offsetX + vec.x(), offsetY + vec.y(), offsetZ + vec.z());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\SimpleBlockRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */