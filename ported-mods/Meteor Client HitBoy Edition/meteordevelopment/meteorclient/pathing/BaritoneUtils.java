/*    */ package meteordevelopment.meteorclient.pathing;
/*    */ 
/*    */ import baritone.api.BaritoneAPI;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BaritoneUtils
/*    */ {
/*    */   public static boolean IS_AVAILABLE = false;
/*    */   
/*    */   public static String getPrefix() {
/* 17 */     if (IS_AVAILABLE) {
/* 18 */       return (String)(BaritoneAPI.getSettings()).prefix.value;
/*    */     }
/*    */     
/* 21 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\pathing\BaritoneUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */