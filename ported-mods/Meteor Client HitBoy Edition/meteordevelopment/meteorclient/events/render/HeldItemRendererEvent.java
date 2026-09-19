/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_4587;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HeldItemRendererEvent
/*    */ {
/* 12 */   private static final HeldItemRendererEvent INSTANCE = new HeldItemRendererEvent();
/*    */   
/*    */   public class_1268 hand;
/*    */   public class_4587 matrix;
/*    */   
/*    */   public static HeldItemRendererEvent get(class_1268 hand, class_4587 matrices) {
/* 18 */     INSTANCE.hand = hand;
/* 19 */     INSTANCE.matrix = matrices;
/* 20 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\HeldItemRendererEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */