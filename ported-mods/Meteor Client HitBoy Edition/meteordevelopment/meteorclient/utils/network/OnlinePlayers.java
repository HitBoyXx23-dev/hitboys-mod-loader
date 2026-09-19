/*    */ package meteordevelopment.meteorclient.utils.network;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnlinePlayers
/*    */ {
/*    */   private static long lastPingTime;
/*    */   
/*    */   public static void update() {
/* 15 */     long time = System.currentTimeMillis();
/*    */     
/* 17 */     if (time - lastPingTime > 300000L) {
/* 18 */       MeteorExecutor.execute(() -> Http.post("https://meteorclient.com/api/online/ping").ignoreExceptions().send());
/*    */       
/* 20 */       lastPingTime = time;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void leave() {
/* 25 */     MeteorExecutor.execute(() -> Http.post("https://meteorclient.com/api/online/leave").ignoreExceptions().send());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\network\OnlinePlayers.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */