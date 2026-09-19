/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.entity.player.BlockBreakingCooldownEvent;
/*    */ import meteordevelopment.meteorclient.events.meteor.MouseClickEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.IntSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BreakDelay
/*    */   extends Module
/*    */ {
/* 20 */   SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 22 */   private final Setting<Integer> cooldown = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/* 23 */       .name("cooldown"))
/* 24 */       .description("Block break cooldown in ticks."))
/* 25 */       .defaultValue(Integer.valueOf(0)))
/* 26 */       .min(0)
/* 27 */       .sliderMax(5)
/* 28 */       .build());
/*    */ 
/*    */   
/* 31 */   private final Setting<Boolean> noInstaBreak = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 32 */       .name("no-insta-break"))
/* 33 */       .description("Prevents you from misbreaking blocks if you can instantly break them."))
/* 34 */       .defaultValue(Boolean.valueOf(false)))
/* 35 */       .build());
/*    */   
/*    */   private boolean breakBlockCooldown = false;
/*    */ 
/*    */   
/*    */   public BreakDelay() {
/* 41 */     super(Categories.Player, "break-delay", "Changes the delay between breaking blocks.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onBlockBreakingCooldown(BlockBreakingCooldownEvent event) {
/* 46 */     if (this.breakBlockCooldown) {
/* 47 */       event.cooldown = 5;
/* 48 */       this.breakBlockCooldown = false;
/*    */     } else {
/* 50 */       event.cooldown = ((Integer)this.cooldown.get()).intValue();
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onClick(MouseClickEvent event) {
/* 56 */     if (event.action == KeyAction.Press && ((Boolean)this.noInstaBreak.get()).booleanValue()) {
/* 57 */       this.breakBlockCooldown = true;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean preventInstaBreak() {
/* 62 */     return (isActive() && ((Boolean)this.noInstaBreak.get()).booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\BreakDelay.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */