/*    */ package meteordevelopment.meteorclient.events.game;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SendMessageEvent
/*    */   extends Cancellable
/*    */ {
/* 11 */   private static final SendMessageEvent INSTANCE = new SendMessageEvent();
/*    */   
/*    */   public String message;
/*    */   
/*    */   public static SendMessageEvent get(String message) {
/* 16 */     INSTANCE.setCancelled(false);
/* 17 */     INSTANCE.message = message;
/* 18 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\game\SendMessageEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */