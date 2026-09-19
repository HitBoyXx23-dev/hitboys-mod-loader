/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DoAttackEvent
/*    */   extends Cancellable
/*    */ {
/* 11 */   private static final DoAttackEvent INSTANCE = new DoAttackEvent();
/*    */   
/*    */   public static DoAttackEvent get() {
/* 14 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\DoAttackEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */