/*     */ package meteordevelopment.meteorclient.systems.modules.combat;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.utils.entity.DamageUtils;
/*     */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*     */ import meteordevelopment.meteorclient.utils.entity.SortPriority;
/*     */ import meteordevelopment.meteorclient.utils.entity.TargetUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockIterator;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class AnchorAura extends Module {
/*  41 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  42 */   private final SettingGroup sgPlace = this.settings.createGroup("Place");
/*  43 */   private final SettingGroup sgBreak = this.settings.createGroup("Break");
/*  44 */   private final SettingGroup sgPause = this.settings.createGroup("Pause");
/*  45 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*     */ 
/*     */ 
/*     */   
/*  49 */   private final Setting<Double> targetRange = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  50 */       .name("target-range"))
/*  51 */       .description("Range in which to target players."))
/*  52 */       .defaultValue(10.0D)
/*  53 */       .min(0.0D)
/*  54 */       .sliderMax(16.0D)
/*  55 */       .build());
/*     */ 
/*     */   
/*  58 */   private final Setting<SortPriority> targetPriority = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  59 */       .name("target-priority"))
/*  60 */       .description("How to select the player to target."))
/*  61 */       .defaultValue(SortPriority.LowestHealth))
/*  62 */       .build());
/*     */ 
/*     */   
/*  65 */   private final Setting<Double> minDamage = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  66 */       .name("min-damage"))
/*  67 */       .description("The minimum damage to inflict on your target."))
/*  68 */       .defaultValue(7.0D)
/*  69 */       .min(0.0D)
/*  70 */       .sliderMax(36.0D)
/*  71 */       .build());
/*     */ 
/*     */   
/*  74 */   private final Setting<Double> maxSelfDamage = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  75 */       .name("max-self-damage"))
/*  76 */       .description("The maximum damage to inflict on yourself."))
/*  77 */       .defaultValue(7.0D)
/*  78 */       .min(0.0D)
/*  79 */       .sliderMax(36.0D)
/*  80 */       .build());
/*     */ 
/*     */   
/*  83 */   private final Setting<Boolean> antiSuicide = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  84 */       .name("anti-suicide"))
/*  85 */       .description("Will not place and break anchors if they will kill you."))
/*  86 */       .defaultValue(Boolean.valueOf(true)))
/*  87 */       .build());
/*     */ 
/*     */   
/*  90 */   private final Setting<Boolean> swapBack = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  91 */       .name("swap-back"))
/*  92 */       .description("Switches to your previous slot after using anchors."))
/*  93 */       .defaultValue(Boolean.valueOf(true)))
/*  94 */       .build());
/*     */ 
/*     */   
/*  97 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  98 */       .name("rotate"))
/*  99 */       .description("Rotates server-side towards the anchors being placed/broken."))
/* 100 */       .defaultValue(Boolean.valueOf(true)))
/* 101 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 106 */   private final Setting<Boolean> place = this.sgPlace.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 107 */       .name("place"))
/* 108 */       .description("Allows Anchor Aura to place anchors."))
/* 109 */       .defaultValue(Boolean.valueOf(true)))
/* 110 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> placeDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> placeRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> placeWallsRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> airPlace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> chargeDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> breakDelay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> breakRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> breakWallsRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> pauseOnUse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> pauseOnMine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> pauseOnCA;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> swing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> render;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<ShapeMode> shapeMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> sideColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> lineColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double bestPlaceDamage;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class_2338.class_2339 bestPlacePos;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double bestBreakDamage;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class_2338.class_2339 bestBreakPos;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_2338 renderBlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int placeDelayLeft;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int chargeDelayLeft;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int breakDelayLeft;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_1657 target;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnchorAura() {
/* 258 */     super(Categories.Combat, "anchor-aura", "Automatically places and breaks Respawn Anchors to harm entities."); Objects.requireNonNull(this.place); this.placeDelay = this.sgPlace.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("place-delay")).description("The tick delay between placing anchors.")).defaultValue(Integer.valueOf(5))).range(0, 10).visible(this.place::get)).build()); Objects.requireNonNull(this.place); this.placeRange = this.sgPlace.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("place-range")).description("The range at which anchors can be placed.")).defaultValue(4.0D).range(0.0D, 6.0D).visible(this.place::get)).build()); Objects.requireNonNull(this.place); this.placeWallsRange = this.sgPlace.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("walls-range")).description("Range in which to place anchors when behind blocks.")).defaultValue(4.0D).range(0.0D, 6.0D).visible(this.place::get)).build()); Objects.requireNonNull(this.place); this.airPlace = this.sgPlace.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("air-place")).description("Allows Anchor Aura to place anchors in the air.")).defaultValue(Boolean.valueOf(true))).visible(this.place::get)).build()); this.chargeDelay = this.sgBreak.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("charge-delay")).description("The tick delay it takes to charge anchors.")).defaultValue(Integer.valueOf(1))).range(0, 10).build()); this.breakDelay = this.sgBreak.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("break-delay")).description("The tick delay it takes to break anchors.")).defaultValue(Integer.valueOf(1))).range(0, 10).build()); this.breakRange = this.sgBreak.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("break-range")).description("Range in which to break anchors.")).defaultValue(4.5D).min(0.0D).sliderMax(6.0D).build()); this.breakWallsRange = this.sgBreak.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("walls-range")).description("Range in which to break anchors when behind blocks.")).defaultValue(4.5D).min(0.0D).sliderMax(6.0D).build()); this.pauseOnUse = this.sgPause.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("pause-on-use")).description("Pauses while using an item.")).defaultValue(Boolean.valueOf(true))).build()); this.pauseOnMine = this.sgPause.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("pause-on-mine")).description("Pauses while mining blocks.")).defaultValue(Boolean.valueOf(true))).build()); this.pauseOnCA = this.sgPause.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("pause-on-CA")).description("Pauses while Crystal Aura is placing.")).defaultValue(Boolean.valueOf(true))).build()); this.swing = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("swing")).description("Whether to swing your hand client-side.")).defaultValue(Boolean.valueOf(true))).build()); this.render = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("render")).description("Renders the block where it is placing an anchor.")).defaultValue(Boolean.valueOf(true))).build()); Objects.requireNonNull(this.render);
/*     */     this.shapeMode = this.sgRender.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("shape-mode")).description("How the shapes are rendered.")).defaultValue(ShapeMode.Both)).visible(this.render::get)).build());
/*     */     this.sideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("side-color")).description("The side color for positions to be placed.")).defaultValue(new SettingColor(15, 255, 211, 41)).visible(() -> (((Boolean)this.render.get()).booleanValue() && ((ShapeMode)this.shapeMode.get()).sides()))).build());
/*     */     this.lineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("line-color")).description("The line color for positions to be placed.")).defaultValue(new SettingColor(15, 255, 211)).visible(() -> (((Boolean)this.render.get()).booleanValue() && ((ShapeMode)this.shapeMode.get()).lines()))).build());
/*     */     this.bestPlacePos = new class_2338.class_2339();
/* 263 */     this.bestBreakPos = new class_2338.class_2339(); } public void onActivate() { this.renderBlockPos = null;
/* 264 */     this.placeDelayLeft = ((Integer)this.placeDelay.get()).intValue();
/* 265 */     this.chargeDelayLeft = 0;
/* 266 */     this.breakDelayLeft = 0;
/* 267 */     this.target = null; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 272 */     this.renderBlockPos = null;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 277 */     if (this.mc.field_1687.method_27983() == class_1937.field_25180) {
/* 278 */       error("You can't blow up respawn anchors in this dimension, disabling.", new Object[0]);
/* 279 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 284 */     if (shouldPause()) {
/* 285 */       this.renderBlockPos = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 290 */     if (TargetUtils.isBadTarget(this.target, ((Double)this.targetRange.get()).doubleValue())) {
/* 291 */       this.renderBlockPos = null;
/* 292 */       this.target = TargetUtils.getPlayerTarget(((Double)this.targetRange.get()).doubleValue(), (SortPriority)this.targetPriority.get());
/* 293 */       if (TargetUtils.isBadTarget(this.target, ((Double)this.targetRange.get()).doubleValue()))
/*     */         return; 
/*     */     } 
/* 296 */     doAnchorAura();
/*     */   }
/*     */   
/*     */   private void doAnchorAura() {
/* 300 */     this.bestPlaceDamage = 0.0D;
/* 301 */     this.bestBreakDamage = 0.0D;
/*     */ 
/*     */     
/* 304 */     int iteratorRange = (int)Math.ceil(Math.max(((Double)this.placeRange.get()).doubleValue(), ((Double)this.breakRange.get()).doubleValue()));
/* 305 */     BlockIterator.register(iteratorRange, iteratorRange, (blockPos, blockState) -> {
/*     */           boolean isPlacing = (blockState.method_26204() != class_2246.field_23152);
/*     */           
/*     */           double baseRange = (isPlacing ? (Double)this.placeRange.get() : (Double)this.breakRange.get()).doubleValue();
/*     */           
/*     */           double wallsRange = (isPlacing ? (Double)this.placeWallsRange.get() : (Double)this.breakWallsRange.get()).doubleValue();
/*     */           
/*     */           if (isOutOfRange(blockPos, baseRange, wallsRange)) {
/*     */             return;
/*     */           }
/*     */           
/*     */           if (isPlacing) {
/*     */             if (!BlockUtils.canPlace(blockPos)) {
/*     */               return;
/*     */             }
/*     */             
/*     */             if (!((Boolean)this.airPlace.get()).booleanValue() && isAirPlace(blockPos)) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */           
/*     */           float bestDamage = isPlacing ? (float)this.bestPlaceDamage : (float)this.bestBreakDamage;
/*     */           float selfDamage = DamageUtils.anchorDamage((class_1309)this.mc.field_1724, blockPos.method_46558());
/*     */           float targetDamage = DamageUtils.anchorDamage((class_1309)this.target, blockPos.method_46558());
/*     */           if (targetDamage >= ((Double)this.minDamage.get()).doubleValue() && targetDamage > bestDamage && (!((Boolean)this.antiSuicide.get()).booleanValue() || selfDamage <= ((Double)this.maxSelfDamage.get()).doubleValue()) && (!((Boolean)this.antiSuicide.get()).booleanValue() || PlayerUtils.getTotalHealth() - selfDamage > 0.0F)) {
/*     */             if (isPlacing) {
/*     */               this.bestPlaceDamage = targetDamage;
/*     */               this.bestPlacePos.method_10101((class_2382)blockPos);
/*     */             } else {
/*     */               this.bestBreakDamage = targetDamage;
/*     */               this.bestBreakPos.method_10101((class_2382)blockPos);
/*     */             } 
/*     */           }
/*     */         });
/* 339 */     BlockIterator.after(() -> {
/*     */           this.renderBlockPos = null;
/*     */           FindItemResult anchor = InvUtils.findInHotbar(new class_1792[] { class_1802.field_23141 });
/*     */           FindItemResult glowStone = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8801 });
/*     */           if (this.bestBreakDamage > 0.0D) {
/*     */             doBreak(glowStone);
/*     */           } else if (this.bestPlaceDamage > 0.0D && ((Boolean)this.place.get()).booleanValue() && anchor.found() && glowStone.found()) {
/*     */             doPlace(anchor);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void doPlace(FindItemResult anchor) {
/* 355 */     this.renderBlockPos = (class_2338)this.bestPlacePos;
/*     */     
/* 357 */     if (this.placeDelayLeft++ < ((Integer)this.placeDelay.get()).intValue()) {
/*     */       return;
/*     */     }
/* 360 */     BlockUtils.place((class_2338)this.bestPlacePos, anchor, ((Boolean)this.rotate.get()).booleanValue(), 50, ((Boolean)this.swing.get()).booleanValue(), false, ((Boolean)this.swapBack.get()).booleanValue());
/*     */     
/* 362 */     this.placeDelayLeft = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private void doBreak(FindItemResult glowStone) {
/* 367 */     this.renderBlockPos = (class_2338)this.bestBreakPos;
/*     */     
/* 369 */     if (((Boolean)this.rotate.get()).booleanValue()) {
/* 370 */       Rotations.rotate(Rotations.getYaw((class_2338)this.bestBreakPos), Rotations.getPitch((class_2338)this.bestBreakPos), 40, () -> doInteract(glowStone));
/*     */     } else {
/* 372 */       doInteract(glowStone);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doInteract(FindItemResult glowStone) {
/* 377 */     class_2680 blockState = this.mc.field_1687.method_8320((class_2338)this.bestBreakPos);
/* 378 */     if (blockState.method_26204() != class_2246.field_23152)
/*     */       return; 
/* 380 */     class_243 center = this.bestBreakPos.method_46558();
/* 381 */     int charges = ((Integer)blockState.method_11654((class_2769)class_2741.field_23187)).intValue();
/*     */ 
/*     */     
/* 384 */     if (charges == 0 && this.chargeDelayLeft++ >= ((Integer)this.chargeDelay.get()).intValue()) {
/* 385 */       if (!glowStone.found())
/*     */         return; 
/* 387 */       InvUtils.swap(glowStone.slot(), ((Boolean)this.swapBack.get()).booleanValue());
/* 388 */       BlockUtils.interact(new class_3965(center, BlockUtils.getDirection((class_2338)this.bestBreakPos), (class_2338)this.bestBreakPos, true), class_1268.field_5808, ((Boolean)this.swing.get()).booleanValue());
/* 389 */       this.chargeDelayLeft = 0;
/* 390 */       charges++;
/*     */     } 
/*     */ 
/*     */     
/* 394 */     if (charges > 0 && this.breakDelayLeft++ >= ((Integer)this.breakDelay.get()).intValue()) {
/* 395 */       FindItemResult fir = InvUtils.findInHotbar(item -> !item.method_7909().equals(class_1802.field_8801));
/* 396 */       if (!fir.found())
/*     */         return; 
/* 398 */       InvUtils.swap(fir.slot(), ((Boolean)this.swapBack.get()).booleanValue());
/* 399 */       BlockUtils.interact(new class_3965(center, BlockUtils.getDirection((class_2338)this.bestBreakPos), (class_2338)this.bestBreakPos, true), class_1268.field_5808, ((Boolean)this.swing.get()).booleanValue());
/* 400 */       this.breakDelayLeft = 0;
/*     */ 
/*     */       
/* 403 */       this.mc.field_1687.method_8652((class_2338)this.bestBreakPos, this.mc.field_1687.method_8316((class_2338)this.bestBreakPos).method_15759(), 0);
/*     */     } 
/*     */     
/* 406 */     if (((Boolean)this.swapBack.get()).booleanValue()) InvUtils.swapBack(); 
/*     */   }
/*     */   
/*     */   private boolean isOutOfRange(class_2338 blockPos, double baseRange, double wallsRange) {
/* 410 */     class_243 pos = blockPos.method_46558();
/* 411 */     if (!PlayerUtils.isWithin(pos, baseRange)) return true;
/*     */     
/* 413 */     class_3959 raycastContext = new class_3959(this.mc.field_1724.method_33571(), pos, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
/* 414 */     class_3965 result = this.mc.field_1687.method_17742(raycastContext);
/* 415 */     if (result == null || !result.method_17777().equals(blockPos)) {
/* 416 */       return !PlayerUtils.isWithin(pos, wallsRange);
/*     */     }
/* 418 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isAirPlace(class_2338 blockPos) {
/* 422 */     for (class_2350 direction : class_2350.values()) {
/* 423 */       if (!this.mc.field_1687.method_8320(blockPos.method_10093(direction)).method_45474()) return false;
/*     */     
/*     */     } 
/* 426 */     return true;
/*     */   }
/*     */   
/*     */   private boolean shouldPause() {
/* 430 */     if (((Boolean)this.pauseOnUse.get()).booleanValue() && this.mc.field_1724.method_6115()) return true;
/*     */     
/* 432 */     if (((Boolean)this.pauseOnMine.get()).booleanValue() && this.mc.field_1761.method_2923()) return true;
/*     */     
/* 434 */     CrystalAura CA = (CrystalAura)Modules.get().get(CrystalAura.class);
/* 435 */     return (((Boolean)this.pauseOnCA.get()).booleanValue() && CA.isActive() && CA.kaTimer > 0);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/* 440 */     if (!((Boolean)this.render.get()).booleanValue() || this.renderBlockPos == null)
/*     */       return; 
/* 442 */     event.renderer.box(this.renderBlockPos, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInfoString() {
/* 447 */     return EntityUtils.getName((class_1297)this.target);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\AnchorAura.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */