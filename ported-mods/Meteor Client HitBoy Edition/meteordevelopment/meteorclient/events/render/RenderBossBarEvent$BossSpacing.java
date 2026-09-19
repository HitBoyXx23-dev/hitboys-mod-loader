/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BossSpacing
/*    */ {
/* 28 */   private static final BossSpacing INSTANCE = new BossSpacing();
/*    */   
/*    */   public int spacing;
/*    */   
/*    */   public static BossSpacing get(int spacing) {
/* 33 */     INSTANCE.spacing = spacing;
/* 34 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\RenderBossBarEvent$BossSpacing.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */