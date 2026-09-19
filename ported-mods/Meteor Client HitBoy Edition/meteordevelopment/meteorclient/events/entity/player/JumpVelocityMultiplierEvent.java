/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JumpVelocityMultiplierEvent
/*    */ {
/*  9 */   private static final JumpVelocityMultiplierEvent INSTANCE = new JumpVelocityMultiplierEvent();
/*    */   
/* 11 */   public float multiplier = 1.0F;
/*    */   
/*    */   public static JumpVelocityMultiplierEvent get() {
/* 14 */     INSTANCE.multiplier = 1.0F;
/* 15 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\JumpVelocityMultiplierEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */