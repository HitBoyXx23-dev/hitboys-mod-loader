/*    */ package meteordevelopment.meteorclient.systems.modules.movement;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.entity.player.JumpVelocityMultiplierEvent;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HighJump
/*    */   extends Module
/*    */ {
/* 17 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 19 */   private final Setting<Double> multiplier = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 20 */       .name("jump-multiplier"))
/* 21 */       .description("Jump height multiplier."))
/* 22 */       .defaultValue(1.0D)
/* 23 */       .min(0.0D)
/* 24 */       .build());
/*    */ 
/*    */   
/*    */   public HighJump() {
/* 28 */     super(Categories.Movement, "high-jump", "Makes you jump higher than normal.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onJumpVelocityMultiplier(JumpVelocityMultiplierEvent event) {
/* 33 */     event.multiplier = (float)(event.multiplier * ((Double)this.multiplier.get()).doubleValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\HighJump.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */