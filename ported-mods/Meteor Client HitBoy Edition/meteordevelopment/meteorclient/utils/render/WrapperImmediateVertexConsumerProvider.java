/*    */ package meteordevelopment.meteorclient.utils.render;
/*    */ 
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.class_1921;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_4597;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WrapperImmediateVertexConsumerProvider
/*    */   extends class_4597.class_4598
/*    */ {
/*    */   private final Supplier<class_4597> supplier;
/*    */   
/*    */   public WrapperImmediateVertexConsumerProvider(Supplier<class_4597> supplier) {
/* 18 */     super(null, null);
/* 19 */     this.supplier = supplier;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_4588 method_73477(class_1921 layer) {
/* 24 */     return ((class_4597)this.supplier.get()).method_73477(layer);
/*    */   }
/*    */   
/*    */   public void method_22993() {}
/*    */   
/*    */   public void method_22994(class_1921 layer) {}
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\WrapperImmediateVertexConsumerProvider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */