/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_1297;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InteractEntityEvent
/*    */   extends Cancellable
/*    */ {
/* 13 */   private static final InteractEntityEvent INSTANCE = new InteractEntityEvent();
/*    */   
/*    */   public class_1297 entity;
/*    */   public class_1268 hand;
/*    */   
/*    */   public static InteractEntityEvent get(class_1297 entity, class_1268 hand) {
/* 19 */     INSTANCE.setCancelled(false);
/* 20 */     INSTANCE.entity = entity;
/* 21 */     INSTANCE.hand = hand;
/* 22 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\InteractEntityEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */