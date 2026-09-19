/*    */ package meteordevelopment.meteorclient.utils.render;
/*    */ 
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_4597;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoopImmediateVertexConsumerProvider
/*    */   extends class_4597.class_4598
/*    */ {
/* 13 */   public static final NoopImmediateVertexConsumerProvider INSTANCE = new NoopImmediateVertexConsumerProvider();
/*    */   
/*    */   private NoopImmediateVertexConsumerProvider() {
/* 16 */     super(null, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public class_4588 method_73477(class_1921 layer) {
/* 21 */     return NoopVertexConsumer.INSTANCE;
/*    */   }
/*    */   
/*    */   public void method_22993() {}
/*    */   
/*    */   public void method_22994(class_1921 layer) {}
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\NoopImmediateVertexConsumerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */