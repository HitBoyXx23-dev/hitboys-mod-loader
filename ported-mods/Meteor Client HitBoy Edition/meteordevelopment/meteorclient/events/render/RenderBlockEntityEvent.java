/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_11954;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderBlockEntityEvent
/*    */   extends Cancellable
/*    */ {
/* 12 */   private static final RenderBlockEntityEvent INSTANCE = new RenderBlockEntityEvent();
/*    */   
/*    */   public class_11954 blockEntityState;
/*    */   
/*    */   public static RenderBlockEntityEvent get(class_11954 blockEntityState) {
/* 17 */     INSTANCE.setCancelled(false);
/* 18 */     INSTANCE.blockEntityState = blockEntityState;
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\RenderBlockEntityEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */