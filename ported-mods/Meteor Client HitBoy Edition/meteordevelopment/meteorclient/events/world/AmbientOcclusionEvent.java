/*    */ package meteordevelopment.meteorclient.events.world;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AmbientOcclusionEvent
/*    */ {
/*  9 */   private static final AmbientOcclusionEvent INSTANCE = new AmbientOcclusionEvent();
/*    */   
/* 11 */   public float lightLevel = -1.0F;
/*    */   
/*    */   public static AmbientOcclusionEvent get() {
/* 14 */     INSTANCE.lightLevel = -1.0F;
/* 15 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\world\AmbientOcclusionEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */