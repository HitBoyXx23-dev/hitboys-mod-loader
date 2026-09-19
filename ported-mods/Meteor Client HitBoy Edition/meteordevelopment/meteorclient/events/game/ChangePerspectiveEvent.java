/*    */ package meteordevelopment.meteorclient.events.game;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_5498;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChangePerspectiveEvent
/*    */   extends Cancellable
/*    */ {
/* 12 */   private static final ChangePerspectiveEvent INSTANCE = new ChangePerspectiveEvent();
/*    */   
/*    */   public class_5498 perspective;
/*    */   
/*    */   public static ChangePerspectiveEvent get(class_5498 perspective) {
/* 17 */     INSTANCE.setCancelled(false);
/* 18 */     INSTANCE.perspective = perspective;
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\game\ChangePerspectiveEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */