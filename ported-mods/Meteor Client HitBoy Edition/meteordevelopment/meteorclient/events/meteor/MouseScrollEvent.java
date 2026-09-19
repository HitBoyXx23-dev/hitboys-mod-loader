/*    */ package meteordevelopment.meteorclient.events.meteor;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseScrollEvent
/*    */   extends Cancellable
/*    */ {
/* 11 */   private static final MouseScrollEvent INSTANCE = new MouseScrollEvent();
/*    */   
/*    */   public double value;
/*    */   
/*    */   public static MouseScrollEvent get(double value) {
/* 16 */     INSTANCE.setCancelled(false);
/* 17 */     INSTANCE.value = value;
/* 18 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\meteor\MouseScrollEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */