/*     */ package meteordevelopment.meteorclient.systems.modules.combat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BlockListSetting;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.KeybindSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockIterator;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.meteorclient.utils.world.Dir;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class HoleFiller extends Module {
/*  41 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  42 */   private final SettingGroup sgSmart = this.settings.createGroup("Smart");
/*  43 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*     */   
/*  45 */   private final Setting<List<class_2248>> blocks = this.sgGeneral.add((Setting)((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder())
/*  46 */       .name("blocks"))
/*  47 */       .description("Which blocks can be used to fill holes."))
/*  48 */       .defaultValue(new class_2248[] {
/*     */ 
/*     */           
/*     */           class_2246.field_10540, class_2246.field_22423, class_2246.field_22108, class_2246.field_23152, class_2246.field_10343
/*     */ 
/*     */ 
/*     */         
/*  55 */         }).build());
/*     */ 
/*     */   
/*  58 */   private final Setting<Integer> searchRadius = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  59 */       .name("search-radius"))
/*  60 */       .description("Horizontal radius in which to search for holes."))
/*  61 */       .defaultValue(Integer.valueOf(5)))
/*  62 */       .min(0)
/*  63 */       .sliderMax(6)
/*  64 */       .build());
/*     */ 
/*     */   
/*  67 */   private final Setting<Double> placeRange = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  68 */       .name("place-range"))
/*  69 */       .description("How far away from the player you can place a block."))
/*  70 */       .defaultValue(4.5D)
/*  71 */       .min(0.0D)
/*  72 */       .sliderMax(6.0D)
/*  73 */       .build());
/*     */ 
/*     */   
/*  76 */   private final Setting<Double> placeWallsRange = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  77 */       .name("walls-range"))
/*  78 */       .description("How far away from the player you can place a block behind walls."))
/*  79 */       .defaultValue(4.5D)
/*  80 */       .min(0.0D)
/*  81 */       .sliderMax(6.0D)
/*  82 */       .build());
/*     */ 
/*     */   
/*  85 */   private final Setting<Boolean> doubles = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  86 */       .name("doubles"))
/*  87 */       .description("Fills double holes."))
/*  88 */       .defaultValue(Boolean.valueOf(true)))
/*  89 */       .build());
/*     */ 
/*     */   
/*  92 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  93 */       .name("rotate"))
/*  94 */       .description("Automatically rotates towards the holes being filled."))
/*  95 */       .defaultValue(Boolean.valueOf(false)))
/*  96 */       .build());
/*     */ 
/*     */   
/*  99 */   private final Setting<Integer> placeDelay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/* 100 */       .name("place-delay"))
/* 101 */       .description("The ticks delay between placement."))
/* 102 */       .defaultValue(Integer.valueOf(1)))
/* 103 */       .min(0)
/* 104 */       .build());
/*     */ 
/*     */   
/* 107 */   private final Setting<Integer> blocksPerTick = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/* 108 */       .name("blocks-per-tick"))
/* 109 */       .description("How many blocks to place in one tick."))
/* 110 */       .defaultValue(Integer.valueOf(3)))
/* 111 */       .min(1)
/* 112 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   private final Setting<Boolean> smart = this.sgSmart.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 118 */       .name("smart"))
/* 119 */       .description("Take more factors into account before filling a hole."))
/* 120 */       .defaultValue(Boolean.valueOf(true)))
/* 121 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Setting<Keybind> forceFill;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> predictMovement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> ticksToPredict;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> ignoreSafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> onlyMoving;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> targetRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> feetRange;
/*     */ 
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
/*     */   
/*     */   private final Setting<Boolean> render;
/*     */ 
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
/*     */   
/*     */   private final Setting<SettingColor> sideColor;
/*     */ 
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
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> nextSideColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> nextLineColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<class_1657> targets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<Hole> holes;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int timer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HoleFiller() {
/* 248 */     super(Categories.Combat, "hole-filler", "Fills holes with specified blocks."); Objects.requireNonNull(this.smart); this.forceFill = this.sgSmart.add((Setting)((KeybindSetting.Builder)((KeybindSetting.Builder)((KeybindSetting.Builder)((KeybindSetting.Builder)(new KeybindSetting.Builder()).name("force-fill")).description("Fills all holes around you regardless of target checks.")).defaultValue(Keybind.none())).visible(this.smart::get)).build()); Objects.requireNonNull(this.smart); this.predictMovement = this.sgSmart.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("predict-movement")).description("Predict target movement to account for ping.")).defaultValue(Boolean.valueOf(true))).visible(this.smart::get)).build()); this.ticksToPredict = this.sgSmart.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("ticks-to-predict")).description("How many ticks ahead we should predict for.")).defaultValue(10.0D).min(1.0D).sliderMax(30.0D).visible(() -> (((Boolean)this.smart.get()).booleanValue() && ((Boolean)this.predictMovement.get()).booleanValue()))).build()); Objects.requireNonNull(this.smart); this.ignoreSafe = this.sgSmart.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("ignore-safe")).description("Ignore players in safe holes.")).defaultValue(Boolean.valueOf(true))).visible(this.smart::get)).build()); Objects.requireNonNull(this.smart); this.onlyMoving = this.sgSmart.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("only-moving")).description("Ignore players if they're standing still.")).defaultValue(Boolean.valueOf(true))).visible(this.smart::get)).build()); Objects.requireNonNull(this.smart); this.targetRange = this.sgSmart.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("target-range")).description("How far away to target players.")).defaultValue(7.0D).min(0.0D).sliderMin(1.0D).sliderMax(10.0D).visible(this.smart::get)).build()); Objects.requireNonNull(this.smart); this.feetRange = this.sgSmart.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("feet-range")).description("How far from a hole a player's feet must be to fill it.")).defaultValue(1.5D).min(0.0D).sliderMax(4.0D).visible(this.smart::get)).build()); this.swing = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("swing")).description("Swing the player's hand when placing.")).defaultValue(Boolean.valueOf(true))).build()); this.render = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("render")).description("Renders an overlay where blocks will be placed.")).defaultValue(Boolean.valueOf(true))).build()); Objects.requireNonNull(this.render); this.shapeMode = this.sgRender.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("shape-mode")).description("How the shapes are rendered.")).defaultValue(ShapeMode.Both)).visible(this.render::get)).build()); this.sideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("side-color")).description("The side color of the target block rendering.")).defaultValue(new SettingColor(197, 137, 232, 10)).visible(() -> (((Boolean)this.render.get()).booleanValue() && ((ShapeMode)this.shapeMode.get()).sides()))).build());
/*     */     this.lineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("line-color")).description("The line color of the target block rendering.")).defaultValue(new SettingColor(197, 137, 232)).visible(() -> (((Boolean)this.render.get()).booleanValue() && ((ShapeMode)this.shapeMode.get()).lines()))).build());
/*     */     this.nextSideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("next-side-color")).description("The side color of the next block to be placed.")).defaultValue(new SettingColor(227, 196, 245, 10)).visible(() -> (((Boolean)this.render.get()).booleanValue() && ((ShapeMode)this.shapeMode.get()).sides()))).build());
/*     */     this.nextLineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("next-line-color")).description("The line color of the next block to be placed.")).defaultValue(new SettingColor(5, 139, 221)).visible(() -> (((Boolean)this.render.get()).booleanValue() && ((ShapeMode)this.shapeMode.get()).lines()))).build());
/*     */     this.targets = new ArrayList<>();
/* 253 */     this.holes = new ArrayList<>(); } public void onActivate() { this.timer = 0; }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 258 */     if (((Boolean)this.smart.get()).booleanValue()) setTargets(); 
/* 259 */     this.holes.clear();
/*     */ 
/*     */     
/* 262 */     FindItemResult block = InvUtils.findInHotbar(itemStack -> ((List)this.blocks.get()).contains(class_2248.method_9503(itemStack.method_7909())));
/* 263 */     if (!block.found()) {
/*     */       return;
/*     */     }
/* 266 */     BlockIterator.register(((Integer)this.searchRadius.get()).intValue(), ((Integer)this.searchRadius.get()).intValue(), (blockPos, blockState) -> {
/*     */           if (!validHole(blockPos))
/*     */             return;  int surroundBlocks = 0; class_2350 air = null;
/*     */           for (class_2350 direction : class_2350.values()) {
/*     */             if (direction != class_2350.field_11036) {
/*     */               class_2680 state = this.mc.field_1687.method_8320(blockPos.method_10093(direction));
/*     */               if (state.method_26204().method_9520() >= 600.0F) {
/*     */                 surroundBlocks++;
/*     */               } else {
/*     */                 if (direction == class_2350.field_11033)
/*     */                   return; 
/*     */                 if (validHole(blockPos.method_10093(direction)) && air == null) {
/*     */                   for (class_2350 dir : class_2350.values()) {
/*     */                     if (dir != direction.method_10153() && dir != class_2350.field_11036) {
/*     */                       class_2680 state1 = this.mc.field_1687.method_8320(blockPos.method_10093(direction).method_10093(dir));
/*     */                       if (state1.method_26204().method_9520() >= 600.0F) {
/*     */                         surroundBlocks++;
/*     */                       } else {
/*     */                         return;
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                   air = direction;
/*     */                 } 
/*     */               } 
/*     */               if (surroundBlocks == 5 && air == null) {
/*     */                 this.holes.add(new Hole(blockPos, (byte)0));
/*     */               } else if (surroundBlocks == 8 && ((Boolean)this.doubles.get()).booleanValue() && air != null) {
/*     */                 this.holes.add(new Hole(blockPos, Dir.get(air)));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         });
/* 299 */     BlockIterator.after(() -> {
/*     */           if (this.timer > 0 || this.holes.isEmpty()) {
/*     */             return;
/*     */           }
/*     */           
/*     */           int placedCount = 0;
/*     */           for (Hole hole : this.holes) {
/*     */             if (placedCount < ((Integer)this.blocksPerTick.get()).intValue() && BlockUtils.place((class_2338)hole.blockPos, block, ((Boolean)this.rotate.get()).booleanValue(), 10, ((Boolean)this.swing.get()).booleanValue(), true)) {
/*     */               placedCount++;
/*     */             }
/*     */           } 
/*     */           this.timer = ((Integer)this.placeDelay.get()).intValue();
/*     */         });
/* 312 */     this.timer--;
/*     */   }
/*     */   
/*     */   @EventHandler(priority = 100)
/*     */   private void onRender(Render3DEvent event) {
/* 317 */     if (!((Boolean)this.render.get()).booleanValue() || this.holes.isEmpty())
/*     */       return; 
/* 319 */     for (int i = 0; i < this.holes.size(); i++) {
/* 320 */       boolean isNext = (i < ((Integer)this.blocksPerTick.get()).intValue());
/* 321 */       Color side = isNext ? (Color)this.nextSideColor.get() : (Color)this.sideColor.get();
/* 322 */       Color line = isNext ? (Color)this.nextLineColor.get() : (Color)this.lineColor.get();
/*     */       
/* 324 */       Hole hole = this.holes.get(i);
/* 325 */       event.renderer.box((class_2338)hole.blockPos, side, line, (ShapeMode)this.shapeMode.get(), hole.exclude);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean validHole(class_2338 blockPos) {
/* 331 */     if (!BlockUtils.canPlace(blockPos)) return false;
/*     */ 
/*     */     
/* 334 */     if (!this.mc.field_1687.method_8320(blockPos.method_10084()).method_45474()) return false;
/*     */ 
/*     */     
/* 337 */     if (isOutOfRange(blockPos)) return false;
/*     */ 
/*     */     
/* 340 */     if (!((Boolean)this.smart.get()).booleanValue() || ((Keybind)this.forceFill.get()).isPressed()) return true;
/*     */ 
/*     */     
/* 343 */     return this.targets.stream().anyMatch(target -> 
/* 344 */         (target.method_23318() > blockPos.method_10264() && isCloseToHolePos(target, blockPos)));
/*     */   }
/*     */ 
/*     */   
/*     */   private void setTargets() {
/* 349 */     this.targets.clear();
/*     */     
/* 351 */     for (class_1657 player : this.mc.field_1687.method_18456()) {
/* 352 */       if (player.method_5858((class_1297)this.mc.field_1724) > Math.pow(((Double)this.targetRange.get()).doubleValue(), 2.0D) || player
/* 353 */         .method_68878() || player == this.mc.field_1724 || player
/*     */         
/* 355 */         .method_29504() || 
/* 356 */         !Friends.get().shouldAttack(player) || (((Boolean)this.ignoreSafe
/* 357 */         .get()).booleanValue() && isSurrounded(player)) || (((Boolean)this.onlyMoving
/* 358 */         .get()).booleanValue() && (player.method_23317() - player.field_6014 != 0.0D || player.method_23318() - player.field_6036 != 0.0D || player.method_23321() - player.field_5969 != 0.0D))) {
/*     */         continue;
/*     */       }
/* 361 */       this.targets.add(player);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isSurrounded(class_1657 target) {
/* 366 */     for (class_2350 dir : DirectionAccessor.meteor$getHorizontal()) {
/* 367 */       class_2338 blockPos = target.method_24515().method_10093(dir);
/* 368 */       class_2248 block = this.mc.field_1687.method_8320(blockPos).method_26204();
/* 369 */       if (block.method_9520() < 600.0F) return false;
/*     */     
/*     */     } 
/* 372 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isOutOfRange(class_2338 blockPos) {
/* 376 */     class_243 pos = blockPos.method_46558().method_1031(0.0D, 0.499D, 0.0D);
/* 377 */     if (!PlayerUtils.isWithin(pos, ((Double)this.placeRange.get()).doubleValue())) return true;
/*     */     
/* 379 */     class_3959 raycastContext = new class_3959(this.mc.field_1724.method_33571(), pos, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
/* 380 */     class_3965 result = this.mc.field_1687.method_17742(raycastContext);
/* 381 */     if (result == null || !result.method_17777().equals(blockPos)) {
/* 382 */       return !PlayerUtils.isWithin(pos, ((Double)this.placeWallsRange.get()).doubleValue());
/*     */     }
/* 384 */     return false;
/*     */   }
/*     */   
/*     */   private boolean isCloseToHolePos(class_1657 target, class_2338 blockPos) {
/* 388 */     class_243 pos = target.method_73189();
/*     */ 
/*     */     
/* 391 */     if (((Boolean)this.predictMovement.get()).booleanValue()) {
/* 392 */       double dx = target.method_23317() - target.field_6014;
/* 393 */       double dy = target.method_23318() - target.field_6036;
/* 394 */       double dz = target.method_23321() - target.field_5969;
/* 395 */       pos = pos.method_1031(dx * ((Double)this.ticksToPredict.get()).doubleValue(), dy * ((Double)this.ticksToPredict.get()).doubleValue(), dz * ((Double)this.ticksToPredict.get()).doubleValue());
/*     */     } 
/*     */     
/* 398 */     double i = pos.field_1352 - blockPos.method_10263() + 0.5D;
/* 399 */     double j = pos.field_1351 - blockPos.method_10264() + 1.0D;
/* 400 */     double k = pos.field_1350 - blockPos.method_10260() + 0.5D;
/* 401 */     double distance = Math.sqrt(i * i + j * j + k * k);
/*     */     
/* 403 */     return (distance < ((Double)this.feetRange.get()).doubleValue());
/*     */   }
/*     */   
/*     */   private static class Hole {
/* 407 */     private final class_2338.class_2339 blockPos = new class_2338.class_2339();
/*     */     private final byte exclude;
/*     */     
/*     */     public Hole(class_2338 blockPos, byte exclude) {
/* 411 */       this.blockPos.method_10101((class_2382)blockPos);
/* 412 */       this.exclude = exclude;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\HoleFiller.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */