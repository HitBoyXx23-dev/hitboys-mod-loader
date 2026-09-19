/*     */ package meteordevelopment.meteorclient.systems.modules.combat;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
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
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.meteorclient.utils.world.CardinalDirection;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2586;
/*     */ 
/*     */ public class BedAura extends Module {
/*  40 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  41 */   private final SettingGroup sgTargeting = this.settings.createGroup("Targeting");
/*  42 */   private final SettingGroup sgAutoMove = this.settings.createGroup("Inventory");
/*  43 */   private final SettingGroup sgPause = this.settings.createGroup("Pause");
/*  44 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*     */ 
/*     */ 
/*     */   
/*  48 */   private final Setting<Integer> delay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  49 */       .name("delay"))
/*  50 */       .description("The delay between placing beds in ticks."))
/*  51 */       .defaultValue(Integer.valueOf(9)))
/*  52 */       .min(0)
/*  53 */       .sliderMax(20)
/*  54 */       .build());
/*     */ 
/*     */   
/*  57 */   private final Setting<Boolean> strictDirection = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  58 */       .name("strict-direction"))
/*  59 */       .description("Only places beds in the direction you are facing."))
/*  60 */       .defaultValue(Boolean.valueOf(false)))
/*  61 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private final Setting<Double> targetRange = this.sgTargeting.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  67 */       .name("target-range"))
/*  68 */       .description("The range at which players can be targeted."))
/*  69 */       .defaultValue(4.0D)
/*  70 */       .min(0.0D)
/*  71 */       .sliderMax(5.0D)
/*  72 */       .build());
/*     */ 
/*     */   
/*  75 */   private final Setting<SortPriority> priority = this.sgTargeting.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  76 */       .name("target-priority"))
/*  77 */       .description("How to filter targets within range."))
/*  78 */       .defaultValue(SortPriority.LowestHealth))
/*  79 */       .build());
/*     */ 
/*     */   
/*  82 */   private final Setting<Double> minDamage = this.sgTargeting.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  83 */       .name("min-damage"))
/*  84 */       .description("The minimum damage to inflict on your target."))
/*  85 */       .defaultValue(7.0D)
/*  86 */       .range(0.0D, 36.0D)
/*  87 */       .sliderMax(36.0D)
/*  88 */       .build());
/*     */ 
/*     */   
/*  91 */   private final Setting<Double> maxSelfDamage = this.sgTargeting.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  92 */       .name("max-self-damage"))
/*  93 */       .description("The maximum damage to inflict on yourself."))
/*  94 */       .defaultValue(7.0D)
/*  95 */       .range(0.0D, 36.0D)
/*  96 */       .sliderMax(36.0D)
/*  97 */       .build());
/*     */ 
/*     */   
/* 100 */   private final Setting<Boolean> antiSuicide = this.sgTargeting.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 101 */       .name("anti-suicide"))
/* 102 */       .description("Will not place and break beds if they will kill you."))
/* 103 */       .defaultValue(Boolean.valueOf(true)))
/* 104 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   private final Setting<Boolean> autoMove = this.sgAutoMove.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 110 */       .name("auto-move"))
/* 111 */       .description("Moves beds into a selected hotbar slot."))
/* 112 */       .defaultValue(Boolean.valueOf(false)))
/* 113 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> autoMoveSlot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> autoSwitch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> pauseOnEat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> pauseOnDrink;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> pauseOnMine;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> swing;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> render;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<ShapeMode> shapeMode;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> sideColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> lineColor;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CardinalDirection direction;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_1657 target;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_2338 placePos;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_2338 breakPos;
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
/*     */   public BedAura() {
/* 199 */     super(Categories.Combat, "bed-aura", "Automatically places and explodes beds in the Nether and End."); Objects.requireNonNull(this.autoMove); this.autoMoveSlot = this.sgAutoMove.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("auto-move-slot")).description("The slot auto move moves beds to.")).defaultValue(Integer.valueOf(9))).range(1, 9).sliderRange(1, 9).visible(this.autoMove::get)).build()); this.autoSwitch = this.sgAutoMove.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("auto-switch")).description("Switches to and from beds automatically.")).defaultValue(Boolean.valueOf(true))).build()); this.pauseOnEat = this.sgPause.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("pause-on-eat")).description("Pauses while eating.")).defaultValue(Boolean.valueOf(true))).build()); this.pauseOnDrink = this.sgPause.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("pause-on-drink")).description("Pauses while drinking.")).defaultValue(Boolean.valueOf(true))).build()); this.pauseOnMine = this.sgPause.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("pause-on-mine")).description("Pauses while mining.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     this.swing = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("swing")).description("Whether to swing hand client-side.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     this.render = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("render")).description("Renders the block where it is placing a bed.")).defaultValue(Boolean.valueOf(true))).build());
/*     */     this.shapeMode = this.sgRender.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("shape-mode")).description("How the shapes are rendered.")).defaultValue(ShapeMode.Both)).build());
/*     */     this.sideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("side-color")).description("The side color for positions to be placed.")).defaultValue(new SettingColor(15, 255, 211, 75)).build());
/* 204 */     this.lineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("line-color")).description("The line color for positions to be placed.")).defaultValue(new SettingColor(15, 255, 211)).build()); } public void onActivate() { this.timer = ((Integer)this.delay.get()).intValue();
/* 205 */     this.direction = CardinalDirection.North; }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 211 */     if (this.mc.field_1687.method_27983() == class_1937.field_25179) {
/* 212 */       error("You can't blow up beds in this dimension, disabling.", new Object[0]);
/* 213 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 218 */     if (PlayerUtils.shouldPause(((Boolean)this.pauseOnMine.get()).booleanValue(), ((Boolean)this.pauseOnEat.get()).booleanValue(), ((Boolean)this.pauseOnDrink.get()).booleanValue())) {
/*     */       return;
/*     */     }
/* 221 */     this.target = TargetUtils.getPlayerTarget(((Double)this.targetRange.get()).doubleValue(), (SortPriority)this.priority.get());
/* 222 */     if (this.target == null) {
/* 223 */       this.placePos = null;
/* 224 */       this.breakPos = null;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 229 */     if (((Boolean)this.autoMove.get()).booleanValue()) {
/* 230 */       FindItemResult bed = InvUtils.find(itemStack -> itemStack.method_7909() instanceof net.minecraft.class_1748);
/*     */       
/* 232 */       if (bed.found() && bed.slot() != ((Integer)this.autoMoveSlot.get()).intValue() - 1) {
/* 233 */         InvUtils.move().from(bed.slot()).toHotbar(((Integer)this.autoMoveSlot.get()).intValue() - 1);
/*     */       }
/*     */     } 
/*     */     
/* 237 */     if (this.breakPos == null) {
/* 238 */       this.placePos = findPlace(this.target);
/*     */     }
/*     */ 
/*     */     
/* 242 */     if (this.timer <= 0 && placeBed(this.placePos)) {
/* 243 */       this.timer = ((Integer)this.delay.get()).intValue();
/*     */     } else {
/*     */       
/* 246 */       this.timer--;
/*     */     } 
/*     */     
/* 249 */     if (this.breakPos == null) this.breakPos = findBreak(); 
/* 250 */     breakBed(this.breakPos);
/*     */   }
/*     */   
/*     */   private class_2338 findPlace(class_1657 target) {
/* 254 */     if (!InvUtils.find(itemStack -> itemStack.method_7909() instanceof net.minecraft.class_1748).found()) return null;
/*     */     
/* 256 */     for (int index = 0; index < 3; index++) {
/* 257 */       int i = (index == 0) ? 1 : ((index == 1) ? 0 : 2);
/*     */       
/* 259 */       for (CardinalDirection dir : CardinalDirection.values()) {
/* 260 */         if (!((Boolean)this.strictDirection.get()).booleanValue() || dir
/* 261 */           .toDirection() == this.mc.field_1724.method_5735() || dir
/* 262 */           .toDirection().method_10153() == this.mc.field_1724.method_5735()) {
/*     */           
/* 264 */           class_2338 centerPos = target.method_24515().method_10086(i);
/*     */           
/* 266 */           float headSelfDamage = DamageUtils.bedDamage((class_1309)this.mc.field_1724, Utils.vec3d(centerPos));
/* 267 */           float offsetSelfDamage = DamageUtils.bedDamage((class_1309)this.mc.field_1724, Utils.vec3d(centerPos.method_10093(dir.toDirection())));
/*     */           
/* 269 */           if (this.mc.field_1687.method_8320(centerPos).method_45474() && 
/* 270 */             BlockUtils.canPlace(centerPos.method_10093(dir.toDirection())) && 
/* 271 */             DamageUtils.bedDamage((class_1309)target, Utils.vec3d(centerPos)) >= ((Double)this.minDamage.get()).doubleValue() && offsetSelfDamage < ((Double)this.maxSelfDamage
/* 272 */             .get()).doubleValue() && headSelfDamage < ((Double)this.maxSelfDamage
/* 273 */             .get()).doubleValue() && (
/* 274 */             !((Boolean)this.antiSuicide.get()).booleanValue() || PlayerUtils.getTotalHealth() - headSelfDamage > 0.0F) && (
/* 275 */             !((Boolean)this.antiSuicide.get()).booleanValue() || PlayerUtils.getTotalHealth() - offsetSelfDamage > 0.0F)) {
/* 276 */             return centerPos.method_10093((this.direction = dir).toDirection());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 281 */     return null;
/*     */   }
/*     */   
/*     */   private class_2338 findBreak() {
/* 285 */     for (class_2586 blockEntity : Utils.blockEntities()) {
/* 286 */       if (!(blockEntity instanceof net.minecraft.class_2587))
/*     */         continue; 
/* 288 */       class_2338 bedPos = blockEntity.method_11016();
/* 289 */       class_243 bedVec = Utils.vec3d(bedPos);
/*     */       
/* 291 */       if (PlayerUtils.isWithinReach(bedVec) && 
/* 292 */         DamageUtils.bedDamage((class_1309)this.target, bedVec) >= ((Double)this.minDamage.get()).doubleValue() && 
/* 293 */         DamageUtils.bedDamage((class_1309)this.mc.field_1724, bedVec) < ((Double)this.maxSelfDamage.get()).doubleValue() && (
/* 294 */         !((Boolean)this.antiSuicide.get()).booleanValue() || PlayerUtils.getTotalHealth() - DamageUtils.bedDamage((class_1309)this.mc.field_1724, bedVec) > 0.0F)) {
/* 295 */         return bedPos;
/*     */       }
/*     */     } 
/*     */     
/* 299 */     return null;
/*     */   }
/*     */   
/*     */   private boolean placeBed(class_2338 pos) {
/* 303 */     if (pos == null) return false;
/*     */     
/* 305 */     FindItemResult bed = InvUtils.findInHotbar(itemStack -> itemStack.method_7909() instanceof net.minecraft.class_1748);
/* 306 */     if (bed.getHand() == null && !((Boolean)this.autoSwitch.get()).booleanValue()) return false;
/*     */     
/* 308 */     switch (this.direction) { case East: 
/*     */       case South:
/*     */       
/*     */       case West:
/* 312 */        }  double yaw = 0.0D;
/*     */ 
/*     */     
/* 315 */     Rotations.rotate(yaw, Rotations.getPitch(pos), () -> {
/*     */           BlockUtils.place(pos, bed, false, 0, ((Boolean)this.swing.get()).booleanValue(), true);
/*     */           
/*     */           this.breakPos = pos;
/*     */         });
/* 320 */     return true;
/*     */   }
/*     */   
/*     */   private void breakBed(class_2338 pos) {
/* 324 */     if (pos == null)
/* 325 */       return;  this.breakPos = null;
/*     */     
/* 327 */     if (!(this.mc.field_1687.method_8320(pos).method_26204() instanceof net.minecraft.class_2244))
/*     */       return; 
/* 329 */     boolean wasSneaking = this.mc.field_1724.method_5715();
/* 330 */     if (wasSneaking) this.mc.field_1724.method_5660(false);
/*     */     
/* 332 */     this.mc.field_1761.method_2896(this.mc.field_1724, class_1268.field_5810, new class_3965(class_243.method_24953((class_2382)pos), class_2350.field_11036, pos, false));
/*     */     
/* 334 */     this.mc.field_1724.method_5660(wasSneaking);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/* 339 */     if (((Boolean)this.render.get()).booleanValue() && this.placePos != null && this.breakPos == null) {
/* 340 */       int x = this.placePos.method_10263();
/* 341 */       int y = this.placePos.method_10264();
/* 342 */       int z = this.placePos.method_10260();
/*     */       
/* 344 */       switch (this.direction) { case North:
/* 345 */           event.renderer.box(x, y, z, (x + 1), y + 0.6D, (z + 2), (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0); break;
/* 346 */         case South: event.renderer.box(x, y, (z - 1), (x + 1), y + 0.6D, (z + 1), (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0); break;
/* 347 */         case East: event.renderer.box((x - 1), y, z, (x + 1), y + 0.6D, (z + 1), (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0); break;
/* 348 */         case West: event.renderer.box(x, y, z, (x + 2), y + 0.6D, (z + 1), (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0);
/*     */           break; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getInfoString() {
/* 355 */     return EntityUtils.getName((class_1297)this.target);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\BedAura.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */