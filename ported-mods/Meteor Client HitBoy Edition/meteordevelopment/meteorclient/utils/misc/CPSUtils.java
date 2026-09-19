/*    */ package meteordevelopment.meteorclient.utils.misc;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.utils.PreInit;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CPSUtils
/*    */ {
/*    */   private static int clicks;
/*    */   private static int cps;
/*    */   private static int secondsClicking;
/*    */   private static long lastTime;
/*    */   
/*    */   @PreInit
/*    */   public static void init() {
/* 25 */     MeteorClient.EVENT_BUS.subscribe(CPSUtils.class);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private static void onTick(TickEvent.Pre event) {
/* 30 */     long currentTime = System.currentTimeMillis();
/*    */     
/* 32 */     if (currentTime - lastTime >= 1000L) {
/* 33 */       if (cps == 0) {
/* 34 */         clicks = 0;
/* 35 */         secondsClicking = 0;
/*    */       } else {
/* 37 */         lastTime = currentTime;
/* 38 */         secondsClicking++;
/* 39 */         cps = 0;
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public static void onAttack() {
/* 46 */     clicks++;
/* 47 */     cps++;
/*    */   }
/*    */   
/*    */   public static int getCpsAverage() {
/* 51 */     return clicks / ((secondsClicking == 0) ? 1 : secondsClicking);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\CPSUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */