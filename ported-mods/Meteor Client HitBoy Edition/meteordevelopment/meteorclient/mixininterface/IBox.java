/*    */ package meteordevelopment.meteorclient.mixininterface;
/*    */ 
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IBox
/*    */ {
/*    */   void meteor$expand(double paramDouble);
/*    */   
/*    */   void meteor$set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*    */   
/*    */   default void meteor$set(class_2338 pos) {
/* 16 */     meteor$set(pos.method_10263(), pos.method_10264(), pos.method_10260(), (pos.method_10263() + 1), (pos.method_10264() + 1), (pos.method_10260() + 1));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixininterface\IBox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */