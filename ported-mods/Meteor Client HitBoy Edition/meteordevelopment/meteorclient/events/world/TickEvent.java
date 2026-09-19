/*    */ package meteordevelopment.meteorclient.events.world;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TickEvent
/*    */ {
/*    */   public static class Pre
/*    */     extends TickEvent
/*    */   {
/* 10 */     private static final Pre INSTANCE = new Pre();
/*    */     
/*    */     public static Pre get() {
/* 13 */       return INSTANCE;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Post extends TickEvent {
/* 18 */     private static final Post INSTANCE = new Post();
/*    */     
/*    */     public static Post get() {
/* 21 */       return INSTANCE;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\world\TickEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */