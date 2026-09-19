/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_4587;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArmRenderEvent
/*    */ {
/* 13 */   public static ArmRenderEvent INSTANCE = new ArmRenderEvent();
/*    */   
/*    */   public class_4587 matrix;
/*    */   public class_1268 hand;
/*    */   
/*    */   public static ArmRenderEvent get(class_1268 hand, class_4587 matrices) {
/* 19 */     INSTANCE.matrix = matrices;
/* 20 */     INSTANCE.hand = hand;
/*    */     
/* 22 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\ArmRenderEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */