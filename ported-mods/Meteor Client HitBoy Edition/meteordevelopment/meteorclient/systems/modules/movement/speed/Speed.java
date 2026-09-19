/*     */ package meteordevelopment.meteorclient.systems.modules.movement.speed;
/*     */ 
/*     */ import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.speed.modes.Strafe;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.speed.modes.Vanilla;
/*     */ import meteordevelopment.meteorclient.systems.modules.world.Timer;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1313;
/*     */ 
/*     */ public class Speed
/*     */   extends Module
/*     */ {
/*  24 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<SpeedModes> speedMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> vanillaSpeed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> ncpSpeed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> ncpSpeedLimit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> inLiquids;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> whenSneaking;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> vanillaOnGround;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SpeedMode currentMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Speed() {
/*  98 */     super(Categories.Movement, "speed", "Modifies your movement speed when moving on the ground."); this.speedMode = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("mode")).description("The method of applying speed.")).defaultValue(SpeedModes.Vanilla)).onModuleActivated(speedModesSetting -> onSpeedModeChanged((SpeedModes)speedModesSetting.get()))).onChanged(this::onSpeedModeChanged)).build()); this.vanillaSpeed = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("vanilla-speed")).description("The speed in blocks per second.")).defaultValue(5.6D).min(0.0D).sliderMax(20.0D).visible(() -> (this.speedMode.get() == SpeedModes.Vanilla))).build()); this.ncpSpeed = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("strafe-speed")).description("The speed.")).visible(() -> (this.speedMode.get() == SpeedModes.Strafe))).defaultValue(1.6D).min(0.0D).sliderMax(3.0D).build()); this.ncpSpeedLimit = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("speed-limit")).description("Limits your speed on servers with very strict anticheats.")).visible(() -> (this.speedMode.get() == SpeedModes.Strafe))).defaultValue(Boolean.valueOf(false))).build()); this.timer = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("timer")).description("Timer override.")).defaultValue(1.0D).min(0.01D).sliderMin(0.01D).sliderMax(10.0D).build()); this.inLiquids = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("in-liquids")).description("Uses speed when in lava or water.")).defaultValue(Boolean.valueOf(false))).build()); this.whenSneaking = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("when-sneaking")).description("Uses speed when sneaking.")).defaultValue(Boolean.valueOf(false))).build());
/*     */     this.vanillaOnGround = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("only-on-ground")).description("Uses speed only when standing on a block.")).visible(() -> (this.speedMode.get() == SpeedModes.Vanilla))).defaultValue(Boolean.valueOf(false))).build());
/* 100 */     onSpeedModeChanged((SpeedModes)this.speedMode.get());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/* 105 */     this.currentMode.onActivate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 110 */     ((Timer)Modules.get().get(Timer.class)).setOverride(1.0D);
/* 111 */     this.currentMode.onDeactivate();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPlayerMove(PlayerMoveEvent event) {
/* 116 */     if (event.type != class_1313.field_6308 || stopSpeed())
/*     */       return; 
/* 118 */     if (((Double)this.timer.get()).doubleValue() != 1.0D) {
/* 119 */       ((Timer)Modules.get().get(Timer.class)).setOverride(PlayerUtils.isMoving() ? ((Double)this.timer.get()).doubleValue() : 1.0D);
/*     */     }
/*     */     
/* 122 */     this.currentMode.onMove(event);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPreTick(TickEvent.Pre event) {
/* 127 */     if (stopSpeed())
/*     */       return; 
/* 129 */     this.currentMode.onTick();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPacketReceive(PacketEvent.Receive event) {
/* 134 */     if (event.packet instanceof net.minecraft.class_2708) this.currentMode.onRubberband(); 
/*     */   }
/*     */   
/*     */   private void onSpeedModeChanged(SpeedModes mode) {
/* 138 */     switch (mode) { case Vanilla:
/* 139 */         this.currentMode = (SpeedMode)new Vanilla(); break;
/* 140 */       case Strafe: this.currentMode = (SpeedMode)new Strafe();
/*     */         break; }
/*     */   
/*     */   }
/*     */   private boolean stopSpeed() {
/* 145 */     if (this.mc.field_1724.method_6128() || this.mc.field_1724.method_6101() || this.mc.field_1724.method_5854() != null) return true; 
/* 146 */     if (!((Boolean)this.whenSneaking.get()).booleanValue() && this.mc.field_1724.method_5715()) return true; 
/* 147 */     if (((Boolean)this.vanillaOnGround.get()).booleanValue() && !this.mc.field_1724.method_24828() && this.speedMode.get() == SpeedModes.Vanilla) return true; 
/* 148 */     return (!((Boolean)this.inLiquids.get()).booleanValue() && (this.mc.field_1724.method_5799() || this.mc.field_1724.method_5771()));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInfoString() {
/* 153 */     return this.currentMode.getHudString();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\speed\Speed.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */