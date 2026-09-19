/*    */ package meteordevelopment.meteorclient.events.world;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkOcclusionEvent
/*    */   extends Cancellable
/*    */ {
/* 11 */   private static final ChunkOcclusionEvent INSTANCE = new ChunkOcclusionEvent();
/*    */   
/*    */   public static ChunkOcclusionEvent get() {
/* 14 */     INSTANCE.setCancelled(false);
/* 15 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\world\ChunkOcclusionEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */