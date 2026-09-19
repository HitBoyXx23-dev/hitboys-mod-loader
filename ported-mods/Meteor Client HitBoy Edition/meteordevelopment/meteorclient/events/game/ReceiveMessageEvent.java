/*    */ package meteordevelopment.meteorclient.events.game;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_7591;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReceiveMessageEvent
/*    */   extends Cancellable
/*    */ {
/* 13 */   private static final ReceiveMessageEvent INSTANCE = new ReceiveMessageEvent();
/*    */   
/*    */   private class_2561 message;
/*    */   private class_7591 indicator;
/*    */   private boolean modified;
/*    */   public int id;
/*    */   
/*    */   public static ReceiveMessageEvent get(class_2561 message, class_7591 indicator, int id) {
/* 21 */     INSTANCE.setCancelled(false);
/* 22 */     INSTANCE.message = message;
/* 23 */     INSTANCE.indicator = indicator;
/* 24 */     INSTANCE.modified = false;
/* 25 */     INSTANCE.id = id;
/* 26 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public class_2561 getMessage() {
/* 30 */     return this.message;
/*    */   }
/*    */   
/*    */   public class_7591 getIndicator() {
/* 34 */     return this.indicator;
/*    */   }
/*    */   
/*    */   public void setMessage(class_2561 message) {
/* 38 */     this.message = message;
/* 39 */     this.modified = true;
/*    */   }
/*    */   
/*    */   public void setIndicator(class_7591 indicator) {
/* 43 */     this.indicator = indicator;
/* 44 */     this.modified = true;
/*    */   }
/*    */   
/*    */   public boolean isModified() {
/* 48 */     return this.modified;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\game\ReceiveMessageEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */