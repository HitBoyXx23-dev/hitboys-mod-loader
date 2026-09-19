/*    */ package meteordevelopment.meteorclient.events;
/*    */ 
/*    */ import meteordevelopment.orbit.ICancellable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Cancellable
/*    */   implements ICancellable
/*    */ {
/*    */   private boolean cancelled = false;
/*    */   
/*    */   public void setCancelled(boolean cancelled) {
/* 15 */     this.cancelled = cancelled;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 20 */     return this.cancelled;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\Cancellable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */