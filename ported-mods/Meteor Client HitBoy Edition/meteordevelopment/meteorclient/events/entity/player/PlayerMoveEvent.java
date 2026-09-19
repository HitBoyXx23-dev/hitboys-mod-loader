/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import net.minecraft.class_1313;
/*    */ import net.minecraft.class_243;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerMoveEvent
/*    */ {
/* 12 */   private static final PlayerMoveEvent INSTANCE = new PlayerMoveEvent();
/*    */   
/*    */   public class_1313 type;
/*    */   public class_243 movement;
/*    */   
/*    */   public static PlayerMoveEvent get(class_1313 type, class_243 movement) {
/* 18 */     INSTANCE.type = type;
/* 19 */     INSTANCE.movement = movement;
/* 20 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\PlayerMoveEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */