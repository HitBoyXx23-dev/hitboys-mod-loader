/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BreakBlockEvent
/*    */   extends Cancellable
/*    */ {
/* 12 */   private static final BreakBlockEvent INSTANCE = new BreakBlockEvent();
/*    */   
/*    */   public class_2338 blockPos;
/*    */   
/*    */   public static BreakBlockEvent get(class_2338 blockPos) {
/* 17 */     INSTANCE.setCancelled(false);
/* 18 */     INSTANCE.blockPos = blockPos;
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\BreakBlockEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */