/*    */ package meteordevelopment.meteorclient.utils.render;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_9799;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomOutlineVertexConsumerProvider
/*    */   implements class_4597
/*    */ {
/* 14 */   private final class_4597.class_4598 immediate = class_4597.method_22991(new class_9799(1536));
/*    */ 
/*    */   
/*    */   public class_4588 method_73477(class_1921 layer) {
/* 18 */     if (layer.method_24295()) {
/* 19 */       return new CustomVertexConsumer(this.immediate.method_73477(layer));
/*    */     }
/*    */     
/* 22 */     Optional<class_1921> optional = layer.method_23289();
/* 23 */     if (optional.isPresent()) {
/* 24 */       return new CustomVertexConsumer(this.immediate.method_73477(optional.get()));
/*    */     }
/*    */     
/* 27 */     return NoopVertexConsumer.INSTANCE;
/*    */   }
/*    */   
/*    */   public void draw() {
/* 31 */     this.immediate.method_22993();
/*    */   }
/*    */   private static final class CustomVertexConsumer extends Record implements class_4588 { private final class_4588 consumer;
/* 34 */     private CustomVertexConsumer(class_4588 consumer) { this.consumer = consumer; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/render/CustomOutlineVertexConsumerProvider$CustomVertexConsumer;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #34	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 34 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/CustomOutlineVertexConsumerProvider$CustomVertexConsumer; } public class_4588 consumer() { return this.consumer; }
/*    */     public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/render/CustomOutlineVertexConsumerProvider$CustomVertexConsumer;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #34	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/CustomOutlineVertexConsumerProvider$CustomVertexConsumer; }
/*    */     public final boolean equals(Object o) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/render/CustomOutlineVertexConsumerProvider$CustomVertexConsumer;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #34	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/render/CustomOutlineVertexConsumerProvider$CustomVertexConsumer;
/*    */       //   0	8	1	o	Ljava/lang/Object; } public class_4588 method_22912(float x, float y, float z) {
/* 37 */       this.consumer.method_22912(x, y, z);
/* 38 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public class_4588 method_1336(int red, int green, int blue, int alpha) {
/* 43 */       this.consumer.method_1336(red, green, blue, alpha);
/* 44 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public class_4588 method_39415(int argb) {
/* 49 */       this.consumer.method_39415(argb);
/* 50 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public class_4588 method_22913(float u, float v) {
/* 55 */       this.consumer.method_22913(u, v);
/* 56 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public class_4588 method_60796(int u, int v) {
/* 61 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public class_4588 method_22921(int u, int v) {
/* 66 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public class_4588 method_22914(float x, float y, float z) {
/* 71 */       return this;
/*    */     }
/*    */ 
/*    */     
/*    */     public class_4588 method_75298(float width) {
/* 76 */       return this;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\CustomOutlineVertexConsumerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */