/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClipAtLedgeEvent
/*    */ {
/*  9 */   private static final ClipAtLedgeEvent INSTANCE = new ClipAtLedgeEvent();
/*    */   private boolean set;
/*    */   private boolean clip;
/*    */   
/*    */   public void reset() {
/* 14 */     this.set = false;
/*    */   }
/*    */   
/*    */   public void setClip(boolean clip) {
/* 18 */     this.set = true;
/* 19 */     this.clip = clip;
/*    */   }
/*    */   
/*    */   public boolean isSet() {
/* 23 */     return this.set;
/*    */   }
/*    */   public boolean isClip() {
/* 26 */     return this.clip;
/*    */   }
/*    */   
/*    */   public static ClipAtLedgeEvent get() {
/* 30 */     INSTANCE.reset();
/* 31 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\ClipAtLedgeEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */