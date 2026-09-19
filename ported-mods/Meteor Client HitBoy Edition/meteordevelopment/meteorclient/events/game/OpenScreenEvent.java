/*    */ package meteordevelopment.meteorclient.events.game;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OpenScreenEvent
/*    */   extends Cancellable
/*    */ {
/* 12 */   private static final OpenScreenEvent INSTANCE = new OpenScreenEvent();
/*    */   
/*    */   public class_437 screen;
/*    */   
/*    */   public static OpenScreenEvent get(class_437 screen) {
/* 17 */     INSTANCE.setCancelled(false);
/* 18 */     INSTANCE.screen = screen;
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\game\OpenScreenEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */