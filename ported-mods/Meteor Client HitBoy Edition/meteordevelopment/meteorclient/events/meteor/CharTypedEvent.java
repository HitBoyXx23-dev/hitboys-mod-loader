/*    */ package meteordevelopment.meteorclient.events.meteor;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CharTypedEvent
/*    */   extends Cancellable
/*    */ {
/* 11 */   private static final CharTypedEvent INSTANCE = new CharTypedEvent();
/*    */   
/*    */   public char c;
/*    */   
/*    */   public static CharTypedEvent get(char c) {
/* 16 */     INSTANCE.setCancelled(false);
/* 17 */     INSTANCE.c = c;
/* 18 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\meteor\CharTypedEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */