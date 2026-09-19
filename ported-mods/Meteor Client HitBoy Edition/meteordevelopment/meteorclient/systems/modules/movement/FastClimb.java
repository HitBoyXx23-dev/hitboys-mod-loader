/*    */ package meteordevelopment.meteorclient.systems.modules.movement;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.mixin.LivingEntityAccessor;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.Timer;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_5635;
/*    */ 
/*    */ 
/*    */ public class FastClimb
/*    */   extends Module
/*    */ {
/* 24 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 26 */   private final Setting<Boolean> timerMode = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 27 */       .name("timer-mode"))
/* 28 */       .description("Use timer."))
/* 29 */       .defaultValue(Boolean.valueOf(false)))
/* 30 */       .build());
/*    */ 
/*    */   
/* 33 */   private final Setting<Double> speed = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 34 */       .name("climb-speed"))
/* 35 */       .description("Your climb speed."))
/* 36 */       .defaultValue(0.2872D)
/* 37 */       .min(0.0D)
/* 38 */       .visible(() -> !((Boolean)this.timerMode.get()).booleanValue()))
/* 39 */       .build());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Setting<Double> timer;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean resetTimer;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FastClimb() {
/* 55 */     super(Categories.Movement, "fast-climb", "Allows you to climb faster.");
/*    */     Objects.requireNonNull(this.timerMode);
/*    */     this.timer = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("timer")).description("The timer value for Timer.")).defaultValue(1.436D).min(1.0D).sliderMin(1.0D).visible(this.timerMode::get)).build());
/*    */   }
/*    */   public void onActivate() {
/* 60 */     this.resetTimer = false;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onPreTick(TickEvent.Pre event) {
/* 65 */     if (((Boolean)this.timerMode.get()).booleanValue()) {
/* 66 */       if (climbing()) {
/* 67 */         this.resetTimer = false;
/* 68 */         ((Timer)Modules.get().get(Timer.class)).setOverride(((Double)this.timer.get()).doubleValue());
/* 69 */       } else if (!this.resetTimer) {
/* 70 */         ((Timer)Modules.get().get(Timer.class)).setOverride(1.0D);
/* 71 */         this.resetTimer = true;
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 78 */     if (!((Boolean)this.timerMode.get()).booleanValue() && climbing()) {
/* 79 */       class_243 velocity = this.mc.field_1724.method_18798();
/* 80 */       this.mc.field_1724.method_18800(velocity.field_1352, ((Double)this.speed.get()).doubleValue(), velocity.field_1350);
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean climbing() {
/* 85 */     return ((this.mc.field_1724.field_5976 || ((LivingEntityAccessor)this.mc.field_1724).meteor$isJumping()) && (this.mc.field_1724.method_6101() || (this.mc.field_1724.method_55667().method_27852(class_2246.field_27879) && class_5635.method_32355((class_1297)this.mc.field_1724))));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\FastClimb.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */