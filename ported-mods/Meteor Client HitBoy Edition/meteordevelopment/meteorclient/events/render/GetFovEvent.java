/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GetFovEvent
/*    */ {
/*  9 */   private static final GetFovEvent INSTANCE = new GetFovEvent();
/*    */   
/*    */   public float fov;
/*    */   
/*    */   public static GetFovEvent get(float fov) {
/* 14 */     INSTANCE.fov = fov;
/* 15 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\GetFovEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */