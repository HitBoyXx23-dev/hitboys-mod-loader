/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockBreakingCooldownEvent
/*    */ {
/*  9 */   private static final BlockBreakingCooldownEvent INSTANCE = new BlockBreakingCooldownEvent();
/*    */   
/*    */   public int cooldown;
/*    */   
/*    */   public static BlockBreakingCooldownEvent get(int cooldown) {
/* 14 */     INSTANCE.cooldown = cooldown;
/* 15 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\BlockBreakingCooldownEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */