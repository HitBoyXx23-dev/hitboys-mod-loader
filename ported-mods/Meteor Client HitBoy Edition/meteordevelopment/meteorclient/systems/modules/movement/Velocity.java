/*     */ package meteordevelopment.meteorclient.systems.modules.movement;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.mixin.EntityVelocityUpdateS2CPacketAccessor;
/*     */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2743;
/*     */ 
/*     */ 
/*     */ public class Velocity
/*     */   extends Module
/*     */ {
/*  23 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  25 */   public final Setting<Boolean> knockback = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  26 */       .name("knockback"))
/*  27 */       .description("Modifies the amount of knockback you take from attacks."))
/*  28 */       .defaultValue(Boolean.valueOf(true)))
/*  29 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> knockbackHorizontal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> knockbackVertical;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> explosions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> explosionsHorizontal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> explosionsVertical;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> liquids;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> liquidsHorizontal;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> liquidsVertical;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> entityPush;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Double> entityPushAmount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> blocks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> sinking;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> fishing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Velocity() {
/* 138 */     super(Categories.Movement, "velocity", "Prevents you from being moved by external forces."); Objects.requireNonNull(this.knockback); this.knockbackHorizontal = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("knockback-horizontal")).description("How much horizontal knockback you will take.")).defaultValue(0.0D).sliderMax(1.0D).visible(this.knockback::get)).build()); Objects.requireNonNull(this.knockback); this.knockbackVertical = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("knockback-vertical")).description("How much vertical knockback you will take.")).defaultValue(0.0D).sliderMax(1.0D).visible(this.knockback::get)).build()); this.explosions = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("explosions")).description("Modifies your knockback from explosions.")).defaultValue(Boolean.valueOf(true))).build()); Objects.requireNonNull(this.explosions); this.explosionsHorizontal = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("explosions-horizontal")).description("How much velocity you will take from explosions horizontally.")).defaultValue(0.0D).sliderMax(1.0D).visible(this.explosions::get)).build()); Objects.requireNonNull(this.explosions); this.explosionsVertical = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("explosions-vertical")).description("How much velocity you will take from explosions vertically.")).defaultValue(0.0D).sliderMax(1.0D).visible(this.explosions::get)).build()); this.liquids = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("liquids")).description("Modifies the amount you are pushed by flowing liquids.")).defaultValue(Boolean.valueOf(true))).build()); Objects.requireNonNull(this.liquids); this.liquidsHorizontal = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("liquids-horizontal")).description("How much velocity you will take from liquids horizontally.")).defaultValue(0.0D).sliderMax(1.0D).visible(this.liquids::get)).build()); Objects.requireNonNull(this.liquids); this.liquidsVertical = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("liquids-vertical")).description("How much velocity you will take from liquids vertically.")).defaultValue(0.0D).sliderMax(1.0D).visible(this.liquids::get)).build()); this.entityPush = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("entity-push")).description("Modifies the amount you are pushed by entities.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     Objects.requireNonNull(this.entityPush);
/*     */     this.entityPushAmount = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("entity-push-amount")).description("How much you will be pushed.")).defaultValue(0.0D).sliderMax(1.0D).visible(this.entityPush::get)).build());
/*     */     this.blocks = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("blocks")).description("Prevents you from being pushed out of blocks.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     this.sinking = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("sinking")).description("Prevents you from sinking in liquids.")).defaultValue(Boolean.valueOf(false))).build());
/* 143 */     this.fishing = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("fishing")).description("Prevents you from being pulled by fishing rods.")).defaultValue(Boolean.valueOf(false))).build()); } @EventHandler private void onTick(TickEvent.Post event) { if (!((Boolean)this.sinking.get()).booleanValue())
/* 144 */       return;  if (this.mc.field_1690.field_1903.method_1434() || this.mc.field_1690.field_1832.method_1434())
/*     */       return; 
/* 146 */     if ((this.mc.field_1724.method_5799() || this.mc.field_1724.method_5771()) && (this.mc.field_1724.method_18798()).field_1351 < 0.0D) {
/* 147 */       ((IVec3d)this.mc.field_1724.method_18798()).meteor$setY(0.0D);
/*     */     } }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onPacketReceive(PacketEvent.Receive event) {
/* 153 */     if (((Boolean)this.knockback.get()).booleanValue()) { class_2596 class_2596 = event.packet; if (class_2596 instanceof class_2743) { class_2743 packet = (class_2743)class_2596; if (packet
/* 154 */           .method_11818() == this.mc.field_1724.method_5628()) {
/* 155 */           double velX = (packet.method_73085().method_10216() - (this.mc.field_1724.method_18798()).field_1352) * ((Double)this.knockbackHorizontal.get()).doubleValue();
/* 156 */           double velY = (packet.method_73085().method_10214() - (this.mc.field_1724.method_18798()).field_1351) * ((Double)this.knockbackVertical.get()).doubleValue();
/* 157 */           double velZ = (packet.method_73085().method_10215() - (this.mc.field_1724.method_18798()).field_1350) * ((Double)this.knockbackHorizontal.get()).doubleValue();
/* 158 */           ((EntityVelocityUpdateS2CPacketAccessor)packet).meteor$setVelocity(new class_243(velX + 
/* 159 */                 (this.mc.field_1724.method_18798()).field_1352, velY + (this.mc.field_1724.method_18798()).field_1351, velZ + (this.mc.field_1724.method_18798()).field_1350));
/*     */         }  }
/*     */        }
/*     */   
/*     */   }
/*     */   public double getHorizontal(Setting<Double> setting) {
/* 165 */     return isActive() ? ((Double)setting.get()).doubleValue() : 1.0D;
/*     */   }
/*     */   
/*     */   public double getVertical(Setting<Double> setting) {
/* 169 */     return isActive() ? ((Double)setting.get()).doubleValue() : 1.0D;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\Velocity.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */