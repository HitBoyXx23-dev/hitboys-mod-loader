/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import net.minecraft.class_239;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemUseCrosshairTargetEvent
/*    */ {
/* 11 */   private static final ItemUseCrosshairTargetEvent INSTANCE = new ItemUseCrosshairTargetEvent();
/*    */   
/*    */   public class_239 target;
/*    */   
/*    */   public static ItemUseCrosshairTargetEvent get(class_239 target) {
/* 16 */     INSTANCE.target = target;
/* 17 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\ItemUseCrosshairTargetEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */