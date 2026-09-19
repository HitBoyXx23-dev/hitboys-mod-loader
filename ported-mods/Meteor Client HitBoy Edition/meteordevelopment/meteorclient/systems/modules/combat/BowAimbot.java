/*     */ package meteordevelopment.meteorclient.systems.modules.combat;
/*     */ 
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.pathing.PathManagers;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EntityTypeListSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*     */ import meteordevelopment.meteorclient.utils.entity.SortPriority;
/*     */ import meteordevelopment.meteorclient.utils.entity.TargetUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1429;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1753;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_243;
/*     */ 
/*     */ public class BowAimbot extends Module {
/*  34 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  36 */   private final Setting<Double> range = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  37 */       .name("range"))
/*  38 */       .description("The maximum range the entity can be to aim at it."))
/*  39 */       .defaultValue(20.0D)
/*  40 */       .range(0.0D, 100.0D)
/*  41 */       .sliderMax(100.0D)
/*  42 */       .build());
/*     */ 
/*     */   
/*  45 */   private final Setting<Set<class_1299<?>>> entities = this.sgGeneral.add((Setting)((EntityTypeListSetting.Builder)((EntityTypeListSetting.Builder)(new EntityTypeListSetting.Builder())
/*  46 */       .name("entities"))
/*  47 */       .description("Entities to attack."))
/*  48 */       .onlyAttackable()
/*  49 */       .build());
/*     */ 
/*     */   
/*  52 */   private final Setting<SortPriority> priority = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  53 */       .name("priority"))
/*  54 */       .description("What type of entities to target."))
/*  55 */       .defaultValue(SortPriority.LowestHealth))
/*  56 */       .build());
/*     */ 
/*     */   
/*  59 */   private final Setting<Boolean> babies = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  60 */       .name("babies"))
/*  61 */       .description("Whether or not to attack baby variants of the entity."))
/*  62 */       .defaultValue(Boolean.valueOf(true)))
/*  63 */       .build());
/*     */ 
/*     */   
/*  66 */   private final Setting<Boolean> nametagged = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  67 */       .name("nametagged"))
/*  68 */       .description("Whether or not to attack mobs with a name tag."))
/*  69 */       .defaultValue(Boolean.valueOf(false)))
/*  70 */       .build());
/*     */ 
/*     */ 
/*     */   
/*  74 */   private final Setting<Boolean> pauseOnCombat = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  75 */       .name("pause-on-combat"))
/*  76 */       .description("Freezes Baritone temporarily until you released the bow."))
/*  77 */       .defaultValue(Boolean.valueOf(false)))
/*  78 */       .build());
/*     */   
/*     */   private boolean wasPathing;
/*     */   
/*     */   private class_1297 target;
/*     */   
/*     */   public BowAimbot() {
/*  85 */     super(Categories.Combat, "bow-aimbot", "Automatically aims your bow for you.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/*  90 */     this.target = null;
/*  91 */     this.wasPathing = false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/*  96 */     if (!PlayerUtils.isAlive() || !itemInHand())
/*  97 */       return;  if (!(this.mc.field_1724.method_31549()).field_7477 && !InvUtils.find(itemStack -> itemStack.method_7909() instanceof net.minecraft.class_1744).found())
/*     */       return; 
/*  99 */     this.target = TargetUtils.get(entity -> { if (entity == this.mc.field_1724 || entity == this.mc.method_1560()) return false;  if (entity instanceof class_1309) { class_1309 livingEntity = (class_1309)entity; if (livingEntity.method_29504()) return false;  }  if (!entity.method_5805()) return false;  if (!PlayerUtils.isWithin(entity, ((Double)this.range.get()).doubleValue())) return false;  if (!((Set)this.entities.get()).contains(entity.method_5864())) return false;  if (!((Boolean)this.nametagged.get()).booleanValue() && entity.method_16914()) return false;  if (!PlayerUtils.canSeeEntity(entity)) return false;  if (entity instanceof class_1657) { class_1657 player = (class_1657)entity; if (player.method_68878()) return false;  if (!Friends.get().shouldAttack(player)) return false;  }  if (entity instanceof class_1429) { class_1429 animal = (class_1429)entity; if (((Boolean)this.babies.get()).booleanValue() || !animal.method_6109()); return false; }  }(SortPriority)this.priority
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 111 */         .get());
/*     */     
/* 113 */     if (this.target == null) {
/* 114 */       if (this.wasPathing) {
/* 115 */         PathManagers.get().resume();
/* 116 */         this.wasPathing = false;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 121 */     if (this.mc.field_1690.field_1904.method_1434() && itemInHand()) {
/* 122 */       if (((Boolean)this.pauseOnCombat.get()).booleanValue() && PathManagers.get().isPathing() && !this.wasPathing) {
/* 123 */         PathManagers.get().pause();
/* 124 */         this.wasPathing = true;
/*     */       } 
/*     */       
/* 127 */       aim();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean itemInHand() {
/* 132 */     return InvUtils.testInMainHand(new class_1792[] { class_1802.field_8102, class_1802.field_8399 });
/*     */   }
/*     */ 
/*     */   
/*     */   private void aim() {
/* 137 */     float velocity = class_1753.method_7722(this.mc.field_1724.method_6048());
/*     */ 
/*     */     
/* 140 */     class_243 pos = this.target.method_73189();
/*     */     
/* 142 */     double relativeX = pos.field_1352 - this.mc.field_1724.method_23317();
/* 143 */     double relativeY = pos.field_1351 + (this.target.method_17682() / 2.0F) - this.mc.field_1724.method_23320();
/* 144 */     double relativeZ = pos.field_1350 - this.mc.field_1724.method_23321();
/*     */ 
/*     */     
/* 147 */     double hDistance = Math.sqrt(relativeX * relativeX + relativeZ * relativeZ);
/* 148 */     double hDistanceSq = hDistance * hDistance;
/* 149 */     float g = 0.006F;
/* 150 */     float velocitySq = velocity * velocity;
/* 151 */     float pitch = (float)-Math.toDegrees(Math.atan((velocitySq - Math.sqrt((velocitySq * velocitySq) - g * (g * hDistanceSq + 2.0D * relativeY * velocitySq))) / g * hDistance));
/*     */ 
/*     */     
/* 154 */     if (Float.isNaN(pitch)) {
/* 155 */       Rotations.rotate(Rotations.getYaw(this.target), Rotations.getPitch(this.target));
/*     */     } else {
/* 157 */       Rotations.rotate(Rotations.getYaw(new class_243(pos.field_1352, pos.field_1351, pos.field_1350)), pitch);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInfoString() {
/* 163 */     return EntityUtils.getName(this.target);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\BowAimbot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */