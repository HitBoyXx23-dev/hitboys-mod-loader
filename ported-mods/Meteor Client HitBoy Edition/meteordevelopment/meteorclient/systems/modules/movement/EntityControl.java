/*     */ package meteordevelopment.meteorclient.systems.modules.movement;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.events.entity.EntityMoveEvent;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EntityTypeListSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*     */ import meteordevelopment.meteorclient.utils.misc.input.Input;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2833;
/*     */ import net.minecraft.class_7923;
/*     */ 
/*     */ public class EntityControl
/*     */   extends Module {
/*  32 */   private final SettingGroup sgControl = this.settings.createGroup("Control");
/*  33 */   private final SettingGroup sgSpeed = this.settings.createGroup("Speed");
/*  34 */   private final SettingGroup sgFlight = this.settings.createGroup("Flight");
/*     */   
/*  36 */   List<class_1299<?>> list = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Set<class_1299<?>>> entities;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> spoofSaddle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> maxJump;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> lockYaw;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> cancelServerPackets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> speed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> horizontalSpeed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> onlyOnGround;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> inWater;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> flight;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> verticalSpeed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> fallSpeed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> antiKick;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> delay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int delayLeft;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double lastPacketY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean sentPacket;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityControl() {
/* 159 */     super(Categories.Movement, "entity-control", "Lets you control rideable entities without a saddle.", new String[] { "entity-speed", "entity-fly", "boat-fly" }); class_7923.field_41177.forEach(entityType -> {
/*     */           if (EntityUtils.isRideable(entityType) && entityType != class_1299.field_6096 && entityType != class_1299.field_6074 && entityType != class_1299.field_17714)
/*     */             this.list.add(entityType); 
/*     */         }); this.entities = this.sgControl.add((Setting)((EntityTypeListSetting.Builder)((EntityTypeListSetting.Builder)(new EntityTypeListSetting.Builder()).name("entities")).description("Target entities.")).filter(entityType -> (EntityUtils.isRideable(entityType) && entityType != class_1299.field_6096 && entityType != class_1299.field_6074 && entityType != class_1299.field_17714)).defaultValue(this.list.<class_1299>toArray(new class_1299[0])).build()); this.spoofSaddle = this.sgControl.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("spoof-saddle*")).description("Lets you control rideable entities without them being saddled. Only works on older server versions.")).defaultValue(Boolean.valueOf(false))).build()); this.maxJump = this.sgControl.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("max-jump")).description("Sets jump power to maximum.")).defaultValue(Boolean.valueOf(true))).build()); this.lockYaw = this.sgControl.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("lock-yaw")).description("Locks the Entity's yaw.")).defaultValue(Boolean.valueOf(true))).build()); this.cancelServerPackets = this.sgControl.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("cancel-server-packets")).description("Cancels incoming vehicle move packets. WILL desync you from the server if you make an invalid movement.")).defaultValue(Boolean.valueOf(false))).build()); this.speed = this.sgSpeed.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("speed")).description("Makes you go faster horizontally when riding entities.")).defaultValue(Boolean.valueOf(false))).build()); Objects.requireNonNull(this.speed); this.horizontalSpeed = this.sgSpeed.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("horizontal-speed")).description("Horizontal speed in blocks per second.")).defaultValue(10.0D).min(0.0D).sliderMax(50.0D).visible(this.speed::get)).build()); Objects.requireNonNull(this.speed); this.onlyOnGround = this.sgSpeed.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("only-on-ground")).description("Use speed only when standing on a block.")).defaultValue(Boolean.valueOf(false))).visible(this.speed::get)).build()); Objects.requireNonNull(this.speed); this.inWater = this.sgSpeed.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("in-water")).description("Use speed when in water.")).defaultValue(Boolean.valueOf(true))).visible(this.speed::get)).build()); this.flight = this.sgFlight.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("fly")).description("Allows you to fly with entities.")).defaultValue(Boolean.valueOf(false))).build()); Objects.requireNonNull(this.flight); this.verticalSpeed = this.sgFlight.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("vertical-speed")).description("Vertical speed in blocks per second.")).defaultValue(6.0D).min(0.0D).sliderMax(20.0D).visible(this.flight::get)).build()); Objects.requireNonNull(this.flight); this.fallSpeed = this.sgFlight.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("fall-speed")).description("How fast you will fall in blocks per second. Set to a small value to prevent fly kicks.")).defaultValue(0.0D).min(0.0D).visible(this.flight::get)).build()); Objects.requireNonNull(this.flight); this.antiKick = this.sgFlight.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("anti-fly-kick")).description("Whether to prevent the server from kicking you for flying.")).defaultValue(Boolean.valueOf(true))).visible(this.flight::get)).build()); this.delay = this.sgFlight.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("delay")).description("The amount of delay, in ticks, between flying down a bit and return to original position")).defaultValue(Integer.valueOf(40))).min(1).sliderMax(80).visible(() -> (((Boolean)this.flight.get()).booleanValue() && ((Boolean)this.antiKick.get()).booleanValue()))).build());
/* 163 */     this.lastPacketY = Double.MAX_VALUE;
/* 164 */     this.sentPacket = false;
/*     */   }
/*     */   
/*     */   public void onActivate() {
/* 168 */     this.delayLeft = ((Integer)this.delay.get()).intValue();
/* 169 */     this.sentPacket = false;
/* 170 */     this.lastPacketY = Double.MAX_VALUE;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPreTick(TickEvent.Pre event) {
/* 175 */     if (this.sentPacket && this.mc.field_1724.method_5854() != null) {
/* 176 */       class_2833 packet = class_2833.method_65307(this.mc.field_1724.method_5854());
/* 177 */       ((IVec3d)packet.comp_3350()).meteor$setY(this.lastPacketY);
/* 178 */       this.mc.method_1562().method_52787((class_2596)packet);
/* 179 */       this.sentPacket = false;
/*     */     } 
/*     */     
/* 182 */     this.delayLeft--;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onEntityMove(EntityMoveEvent event) {
/* 187 */     class_1297 entity = event.entity;
/* 188 */     if (event.entity.method_5642() != this.mc.field_1724 || !((Set)this.entities.get()).contains(entity.method_5864()))
/*     */       return; 
/* 190 */     double velX = (entity.method_18798()).field_1352;
/* 191 */     double velY = (entity.method_18798()).field_1351;
/* 192 */     double velZ = (entity.method_18798()).field_1350;
/*     */ 
/*     */     
/* 195 */     if (((Boolean)this.speed.get()).booleanValue() && (!((Boolean)this.onlyOnGround.get()).booleanValue() || entity.method_24828() || entity.method_70987()) && (((Boolean)this.inWater.get()).booleanValue() || !entity.method_5799())) {
/* 196 */       class_243 vel = PlayerUtils.getHorizontalVelocity(((Double)this.horizontalSpeed.get()).doubleValue());
/* 197 */       velX = vel.field_1352;
/* 198 */       velZ = vel.field_1350;
/*     */     } 
/*     */ 
/*     */     
/* 202 */     if (((Boolean)this.flight.get()).booleanValue()) {
/* 203 */       velY = 0.0D;
/* 204 */       if (Input.isPressed(this.mc.field_1690.field_1903)) velY += ((Double)this.verticalSpeed.get()).doubleValue() / 20.0D; 
/* 205 */       if (Input.isPressed(this.mc.field_1690.field_1867)) { velY -= ((Double)this.verticalSpeed.get()).doubleValue() / 20.0D; }
/* 206 */       else { velY -= ((Double)this.fallSpeed.get()).doubleValue() / 20.0D; }
/*     */     
/*     */     } 
/* 209 */     if (((Boolean)this.lockYaw.get()).booleanValue()) entity.method_36456(this.mc.field_1724.method_36454()); 
/* 210 */     ((IVec3d)event.movement).meteor$set(velX, velY, velZ);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onSendPacket(PacketEvent.Send event) {
/* 215 */     class_2596 class_2596 = event.packet; if (class_2596 instanceof class_2833) { class_2833 packet = (class_2833)class_2596; if (((Boolean)this.antiKick.get()).booleanValue()) {
/*     */         
/* 217 */         double currentY = (packet.comp_3350()).field_1351;
/* 218 */         if (this.delayLeft <= 0 && !this.sentPacket && shouldFlyDown(currentY) && EntityUtils.isOnAir(this.mc.field_1724.method_5854()) && !this.mc.field_1724.method_5854().method_70987()) {
/* 219 */           ((IVec3d)packet.comp_3350()).meteor$setY(this.lastPacketY - 0.0313D);
/* 220 */           this.sentPacket = true;
/* 221 */           this.delayLeft = ((Integer)this.delay.get()).intValue();
/*     */         } 
/*     */         
/* 224 */         this.lastPacketY = currentY;
/*     */         return;
/*     */       }  }
/*     */      } @EventHandler
/*     */   private void onReceivePacket(PacketEvent.Receive event) {
/* 229 */     if (event.packet instanceof net.minecraft.class_2692 && ((Boolean)this.cancelServerPackets.get()).booleanValue()) {
/* 230 */       event.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean shouldFlyDown(double currentY) {
/* 235 */     if (currentY >= this.lastPacketY) return true; 
/* 236 */     return (this.lastPacketY - currentY < 0.0313D);
/*     */   }
/*     */   
/*     */   public boolean spoofSaddle() {
/* 240 */     return (isActive() && ((Boolean)this.spoofSaddle.get()).booleanValue());
/*     */   }
/*     */   
/*     */   public boolean maxJump() {
/* 244 */     return (isActive() && ((Boolean)this.maxJump.get()).booleanValue());
/*     */   }
/*     */   
/*     */   public boolean cancelJump() {
/* 248 */     if (!(this.mc.field_1724.method_5854() instanceof net.minecraft.class_1316)) return false; 
/* 249 */     return (isActive() && ((Set)this.entities.get()).contains(this.mc.field_1724.method_5854().method_5864()) && ((Boolean)this.flight.get()).booleanValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\EntityControl.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */