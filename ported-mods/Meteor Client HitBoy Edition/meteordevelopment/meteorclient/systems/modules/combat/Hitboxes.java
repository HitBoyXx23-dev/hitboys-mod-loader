/*     */ package meteordevelopment.meteorclient.systems.modules.combat;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EntityTypeListSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_3489;
/*     */ 
/*     */ public class Hitboxes
/*     */   extends Module
/*     */ {
/*  23 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  24 */   private final SettingGroup sgWeapon = this.settings.createGroup("Weapon Options");
/*     */   
/*  26 */   private final Setting<Set<class_1299<?>>> entities = this.sgGeneral.add((Setting)((EntityTypeListSetting.Builder)((EntityTypeListSetting.Builder)(new EntityTypeListSetting.Builder())
/*  27 */       .name("entities"))
/*  28 */       .description("Which entities to target."))
/*  29 */       .defaultValue(new class_1299[] { class_1299.field_6097
/*  30 */         }).build());
/*     */ 
/*     */   
/*  33 */   private final Setting<Double> value = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  34 */       .name("expand"))
/*  35 */       .description("How much to expand the hitbox of the entity."))
/*  36 */       .defaultValue(0.5D)
/*  37 */       .build());
/*     */ 
/*     */   
/*  40 */   private final Setting<Boolean> ignoreFriends = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  41 */       .name("ignore-friends"))
/*  42 */       .description("Doesn't expand the hitboxes of friends."))
/*  43 */       .defaultValue(Boolean.valueOf(true)))
/*  44 */       .build());
/*     */ 
/*     */   
/*  47 */   private final Setting<Boolean> onlyOnWeapon = this.sgWeapon.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  48 */       .name("only-on-weapon"))
/*  49 */       .description("Only modifies hitbox when holding a weapon in hand."))
/*  50 */       .defaultValue(Boolean.valueOf(false)))
/*  51 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> sword;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> axe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> pickaxe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> shovel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> hoe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> mace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> spear;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> trident;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Hitboxes() {
/* 119 */     super(Categories.Combat, "hitboxes", "Expands an entity's hitboxes."); Objects.requireNonNull(this.onlyOnWeapon); this.sword = this.sgWeapon.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("sword")).description("Enable when holding a sword.")).defaultValue(Boolean.valueOf(true))).visible(this.onlyOnWeapon::get)).build()); Objects.requireNonNull(this.onlyOnWeapon); this.axe = this.sgWeapon.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("axe")).description("Enable when holding an axe.")).defaultValue(Boolean.valueOf(true))).visible(this.onlyOnWeapon::get)).build()); Objects.requireNonNull(this.onlyOnWeapon); this.pickaxe = this.sgWeapon.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("pickaxe")).description("Enable when holding a pickaxe.")).defaultValue(Boolean.valueOf(true))).visible(this.onlyOnWeapon::get)).build()); Objects.requireNonNull(this.onlyOnWeapon); this.shovel = this.sgWeapon.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("shovel")).description("Enable when holding a shovel.")).defaultValue(Boolean.valueOf(true))).visible(this.onlyOnWeapon::get)).build()); Objects.requireNonNull(this.onlyOnWeapon); this.hoe = this.sgWeapon.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("hoe")).description("Enable when holding a hoe.")).defaultValue(Boolean.valueOf(true))).visible(this.onlyOnWeapon::get)).build()); Objects.requireNonNull(this.onlyOnWeapon); this.mace = this.sgWeapon.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("mace")).description("Enable when holding a mace.")).defaultValue(Boolean.valueOf(true))).visible(this.onlyOnWeapon::get)).build());
/*     */     Objects.requireNonNull(this.onlyOnWeapon);
/*     */     this.spear = this.sgWeapon.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("spear")).description("Enable when holding a spear.")).defaultValue(Boolean.valueOf(true))).visible(this.onlyOnWeapon::get)).build());
/*     */     Objects.requireNonNull(this.onlyOnWeapon);
/* 123 */     this.trident = this.sgWeapon.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("trident")).description("Enable when holding a trident.")).defaultValue(Boolean.valueOf(true))).visible(this.onlyOnWeapon::get)).build()); } public double getEntityValue(class_1297 entity) { if (isActive() && testWeapon()) { if (((Boolean)this.ignoreFriends.get()).booleanValue() && entity instanceof class_1657) { class_1657 playerEntity = (class_1657)entity; if (Friends.get().isFriend(playerEntity)) return 0.0D;  }  } else { return 0.0D; }
/* 124 */      if (((Set)this.entities.get()).contains(entity.method_5864())) return ((Double)this.value.get()).doubleValue(); 
/* 125 */     return 0.0D; }
/*     */ 
/*     */   
/*     */   private boolean testWeapon() {
/* 129 */     if (!((Boolean)this.onlyOnWeapon.get()).booleanValue()) return true; 
/* 130 */     return InvUtils.testInMainHand(itemStack -> 
/* 131 */         (((Boolean)this.sword.get()).booleanValue() && itemStack.method_31573(class_3489.field_42611)) ? true : (
/* 132 */         (((Boolean)this.axe.get()).booleanValue() && itemStack.method_31573(class_3489.field_42612)) ? true : (
/* 133 */         (((Boolean)this.pickaxe.get()).booleanValue() && itemStack.method_31573(class_3489.field_42614)) ? true : (
/* 134 */         (((Boolean)this.shovel.get()).booleanValue() && itemStack.method_31573(class_3489.field_42615)) ? true : (
/* 135 */         (((Boolean)this.hoe.get()).booleanValue() && itemStack.method_31573(class_3489.field_42613)) ? true : (
/* 136 */         (((Boolean)this.mace.get()).booleanValue() && itemStack.method_7909() instanceof net.minecraft.class_9362) ? true : (
/* 137 */         (((Boolean)this.spear.get()).booleanValue() && itemStack.method_31573(class_3489.field_63257)) ? true : (
/* 138 */         (((Boolean)this.trident.get()).booleanValue() && itemStack.method_7909() instanceof net.minecraft.class_1835)))))))));
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\Hitboxes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */