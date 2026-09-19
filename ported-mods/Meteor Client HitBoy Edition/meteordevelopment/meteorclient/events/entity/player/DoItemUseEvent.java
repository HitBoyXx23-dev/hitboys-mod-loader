/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoItemUseEvent
/*    */   extends Cancellable
/*    */ {
/* 16 */   private static final DoItemUseEvent INSTANCE = new DoItemUseEvent();
/*    */   
/*    */   public static DoItemUseEvent get() {
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\DoItemUseEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */