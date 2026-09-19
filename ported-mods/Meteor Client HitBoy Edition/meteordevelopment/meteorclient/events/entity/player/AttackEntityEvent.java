/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_1297;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttackEntityEvent
/*    */   extends Cancellable
/*    */ {
/* 12 */   private static final AttackEntityEvent INSTANCE = new AttackEntityEvent();
/*    */   
/*    */   public class_1297 entity;
/*    */   
/*    */   public static AttackEntityEvent get(class_1297 entity) {
/* 17 */     INSTANCE.setCancelled(false);
/* 18 */     INSTANCE.entity = entity;
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\AttackEntityEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */