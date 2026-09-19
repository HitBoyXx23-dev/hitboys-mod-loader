/*     */ package meteordevelopment.meteorclient.systems.modules.player;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.entity.player.ItemUseCrosshairTargetEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.pathing.PathManagers;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.combat.AnchorAura;
/*     */ import meteordevelopment.meteorclient.systems.modules.combat.BedAura;
/*     */ import meteordevelopment.meteorclient.systems.modules.combat.CrystalAura;
/*     */ import meteordevelopment.meteorclient.systems.modules.combat.KillAura;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1291;
/*     */ import net.minecraft.class_1293;
/*     */ import net.minecraft.class_1294;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_6880;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoGap
/*     */   extends Module
/*     */ {
/*  39 */   private static final Class<? extends Module>[] AURAS = new Class[] { KillAura.class, CrystalAura.class, AnchorAura.class, BedAura.class };
/*     */   
/*  41 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  42 */   private final SettingGroup sgPotions = this.settings.createGroup("Potions");
/*  43 */   private final SettingGroup sgHealth = this.settings.createGroup("Health");
/*     */ 
/*     */ 
/*     */   
/*  47 */   private final Setting<Boolean> allowEgap = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  48 */       .name("allow-egap"))
/*  49 */       .description("Allow eating E-Gaps over Gaps if found."))
/*  50 */       .defaultValue(Boolean.valueOf(true)))
/*  51 */       .build());
/*     */ 
/*     */   
/*  54 */   private final Setting<Boolean> always = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  55 */       .name("always"))
/*  56 */       .description("If it should always eat."))
/*  57 */       .defaultValue(Boolean.valueOf(false)))
/*  58 */       .build());
/*     */ 
/*     */   
/*  61 */   private final Setting<Boolean> pauseAuras = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  62 */       .name("pause-auras"))
/*  63 */       .description("Pauses all auras when eating."))
/*  64 */       .defaultValue(Boolean.valueOf(true)))
/*  65 */       .build());
/*     */ 
/*     */   
/*  68 */   private final Setting<Boolean> pauseBaritone = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  69 */       .name("pause-baritone"))
/*  70 */       .description("Pause baritone when eating."))
/*  71 */       .defaultValue(Boolean.valueOf(true)))
/*  72 */       .build());
/*     */ 
/*     */ 
/*     */   
/*  76 */   private final Setting<Boolean> beforeExpiry = this.sgPotions.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  77 */       .name("before-expiry"))
/*  78 */       .description("If it should eat before potion effects expire."))
/*  79 */       .defaultValue(Boolean.valueOf(false)))
/*  80 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> expiryThreshold;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> potionsRegeneration;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> potionsFireResistance;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> potionsAbsorption;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> healthEnabled;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> healthThreshold;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean requiresEGap;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean eating;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int slot;
/*     */ 
/*     */ 
/*     */   
/*     */   private int prevSlot;
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<Class<? extends Module>> wasAura;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean wasBaritone;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AutoGap() {
/* 143 */     super(Categories.Player, "auto-gap", "Automatically eats Gaps or E-Gaps."); Objects.requireNonNull(this.beforeExpiry); this.expiryThreshold = this.sgPotions.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("expiry-threshold")).description("Time in ticks before the potion effect expires to start eating.")).defaultValue(Integer.valueOf(60))).min(0).sliderMax(200).visible(this.beforeExpiry::get)).build()); this.potionsRegeneration = this.sgPotions.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("potions-regeneration")).description("If it should eat when Regeneration runs out.")).defaultValue(Boolean.valueOf(false))).build()); Objects.requireNonNull(this.allowEgap); this.potionsFireResistance = this.sgPotions.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("potions-fire-resistance")).description("If it should eat when Fire Resistance runs out. Requires E-Gaps.")).defaultValue(Boolean.valueOf(true))).visible(this.allowEgap::get)).build());
/*     */     Objects.requireNonNull(this.allowEgap);
/*     */     this.potionsAbsorption = this.sgPotions.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("potions-absorption")).description("If it should eat when Absorption runs out. Requires E-Gaps.")).defaultValue(Boolean.valueOf(false))).visible(this.allowEgap::get)).build());
/*     */     this.healthEnabled = this.sgHealth.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("health-enabled")).description("If it should eat when health drops below threshold.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     this.healthThreshold = this.sgHealth.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("health-threshold")).description("Health threshold to eat at. Includes absorption.")).defaultValue(Integer.valueOf(20))).min(0).sliderMax(40).build());
/* 148 */     this.wasAura = (List<Class<? extends Module>>)new ReferenceArrayList(); } public void onDeactivate() { if (this.eating) stopEating();  }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 153 */     if (this.eating) {
/*     */       
/* 155 */       if (shouldEat())
/*     */       {
/* 157 */         if (isNotGapOrEGap(this.mc.field_1724.method_31548().method_5438(this.slot))) {
/*     */           
/* 159 */           int slot = findSlot();
/*     */ 
/*     */           
/* 162 */           if (slot == -1) {
/* 163 */             stopEating();
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/* 168 */           changeSlot(slot);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 173 */         eat();
/*     */       }
/*     */       else
/*     */       {
/* 177 */         stopEating();
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 182 */     else if (shouldEat()) {
/*     */       
/* 184 */       this.slot = findSlot();
/*     */ 
/*     */       
/* 187 */       if (this.slot != -1) startEating();
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onItemUseCrosshairTarget(ItemUseCrosshairTargetEvent event) {
/* 194 */     if (this.eating) event.target = null; 
/*     */   }
/*     */   
/*     */   private void startEating() {
/* 198 */     this.prevSlot = this.mc.field_1724.method_31548().method_67532();
/* 199 */     eat();
/*     */ 
/*     */     
/* 202 */     this.wasAura.clear();
/* 203 */     if (((Boolean)this.pauseAuras.get()).booleanValue()) {
/* 204 */       for (Class<? extends Module> klass : AURAS) {
/* 205 */         Module module = Modules.get().get(klass);
/*     */         
/* 207 */         if (module.isActive()) {
/* 208 */           this.wasAura.add(klass);
/* 209 */           module.toggle();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 215 */     this.wasBaritone = false;
/* 216 */     if (((Boolean)this.pauseBaritone.get()).booleanValue() && PathManagers.get().isPathing()) {
/* 217 */       this.wasBaritone = true;
/* 218 */       PathManagers.get().pause();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void eat() {
/* 223 */     changeSlot(this.slot);
/* 224 */     setPressed(true);
/* 225 */     if (!this.mc.field_1724.method_6115()) Utils.rightClick();
/*     */     
/* 227 */     this.eating = true;
/*     */   }
/*     */   
/*     */   private void stopEating() {
/* 231 */     changeSlot(this.prevSlot);
/* 232 */     setPressed(false);
/*     */     
/* 234 */     this.eating = false;
/*     */ 
/*     */     
/* 237 */     if (((Boolean)this.pauseAuras.get()).booleanValue()) {
/* 238 */       for (Class<? extends Module> klass : AURAS) {
/* 239 */         if (this.wasAura.contains(klass)) {
/* 240 */           Modules.get().get(klass).enable();
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 246 */     if (((Boolean)this.pauseBaritone.get()).booleanValue() && this.wasBaritone) {
/* 247 */       PathManagers.get().resume();
/*     */     }
/*     */   }
/*     */   
/*     */   private void setPressed(boolean pressed) {
/* 252 */     this.mc.field_1690.field_1904.method_23481(pressed);
/*     */   }
/*     */   
/*     */   private void changeSlot(int slot) {
/* 256 */     InvUtils.swap(slot, false);
/* 257 */     this.slot = slot;
/*     */   }
/*     */   
/*     */   private boolean shouldEat() {
/* 261 */     this.requiresEGap = false;
/*     */     
/* 263 */     if (((Boolean)this.always.get()).booleanValue()) return true; 
/* 264 */     if (shouldEatPotions()) return true; 
/* 265 */     return shouldEatHealth();
/*     */   }
/*     */   
/*     */   private boolean shouldEatPotions() {
/* 269 */     Map<class_6880<class_1291>, class_1293> effects = this.mc.field_1724.method_6088();
/*     */ 
/*     */     
/* 272 */     if (((Boolean)this.potionsRegeneration.get()).booleanValue()) {
/* 273 */       class_1293 effect = effects.get(class_1294.field_5924);
/* 274 */       if (effect == null || (((Boolean)this.beforeExpiry.get()).booleanValue() && effect.method_5584() <= ((Integer)this.expiryThreshold.get()).intValue())) return true;
/*     */     
/*     */     } 
/*     */     
/* 278 */     if (((Boolean)this.potionsFireResistance.get()).booleanValue()) {
/* 279 */       class_1293 effect = effects.get(class_1294.field_5918);
/* 280 */       if (effect == null || (((Boolean)this.beforeExpiry.get()).booleanValue() && effect.method_5584() <= ((Integer)this.expiryThreshold.get()).intValue())) {
/* 281 */         this.requiresEGap = true;
/* 282 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 287 */     if (((Boolean)this.potionsAbsorption.get()).booleanValue()) {
/* 288 */       class_1293 effect = effects.get(class_1294.field_5898);
/* 289 */       if (effect == null || (((Boolean)this.beforeExpiry.get()).booleanValue() && effect.method_5584() <= ((Integer)this.expiryThreshold.get()).intValue())) {
/* 290 */         this.requiresEGap = true;
/* 291 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     return false;
/*     */   }
/*     */   
/*     */   private boolean shouldEatHealth() {
/* 299 */     if (!((Boolean)this.healthEnabled.get()).booleanValue()) return false;
/*     */     
/* 301 */     int health = Math.round(this.mc.field_1724.method_6032() + this.mc.field_1724.method_6067());
/* 302 */     return (health < ((Integer)this.healthThreshold.get()).intValue());
/*     */   }
/*     */   
/*     */   private int findSlot() {
/* 306 */     for (int i = 0; i < 9; i++) {
/* 307 */       class_1799 stack = this.mc.field_1724.method_31548().method_5438(i);
/*     */ 
/*     */       
/* 310 */       if (!stack.method_7960())
/*     */       {
/*     */         
/* 313 */         if (!isNotGapOrEGap(stack)) {
/*     */           
/* 315 */           class_1792 item = stack.method_7909();
/*     */ 
/*     */           
/* 318 */           if (item == class_1802.field_8367 && ((Boolean)this.allowEgap.get()).booleanValue()) return i;
/*     */ 
/*     */           
/* 321 */           if (item == class_1802.field_8463 && !this.requiresEGap) return i; 
/*     */         } 
/*     */       }
/*     */     } 
/* 325 */     return -1;
/*     */   }
/*     */   
/*     */   private boolean isNotGapOrEGap(class_1799 stack) {
/* 329 */     class_1792 item = stack.method_7909();
/* 330 */     return (item != class_1802.field_8463 && item != class_1802.field_8367);
/*     */   }
/*     */   
/*     */   public boolean isEating() {
/* 334 */     return (isActive() && this.eating);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\AutoGap.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */