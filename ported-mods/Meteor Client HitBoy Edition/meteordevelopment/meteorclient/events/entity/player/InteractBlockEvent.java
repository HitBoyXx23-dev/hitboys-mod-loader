/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_3965;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InteractBlockEvent
/*    */   extends Cancellable
/*    */ {
/* 13 */   private static final InteractBlockEvent INSTANCE = new InteractBlockEvent();
/*    */   
/*    */   public class_1268 hand;
/*    */   public class_3965 result;
/*    */   
/*    */   public static InteractBlockEvent get(class_1268 hand, class_3965 result) {
/* 19 */     INSTANCE.setCancelled(false);
/* 20 */     INSTANCE.hand = hand;
/* 21 */     INSTANCE.result = result;
/* 22 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\InteractBlockEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */