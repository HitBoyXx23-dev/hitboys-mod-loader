/*     */ package meteordevelopment.meteorclient.systems.modules.combat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.entity.SortPriority;
/*     */ import meteordevelopment.meteorclient.utils.entity.TargetUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2374;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class AutoWeb extends Module {
/*  33 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  34 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*     */   
/*  36 */   private final Setting<Double> placeRange = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  37 */       .name("place-range"))
/*  38 */       .description("The range at which webs can be placed."))
/*  39 */       .defaultValue(4.0D)
/*  40 */       .min(0.0D)
/*  41 */       .sliderMax(6.0D)
/*  42 */       .build());
/*     */ 
/*     */   
/*  45 */   private final Setting<Double> placeWallsRange = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  46 */       .name("walls-range"))
/*  47 */       .description("Range in which to place webs when behind blocks."))
/*  48 */       .defaultValue(4.0D)
/*  49 */       .min(0.0D)
/*  50 */       .sliderMax(6.0D)
/*  51 */       .build());
/*     */ 
/*     */   
/*  54 */   private final Setting<SortPriority> priority = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  55 */       .name("target-priority"))
/*  56 */       .description("How to filter targets within range."))
/*  57 */       .defaultValue(SortPriority.LowestDistance))
/*  58 */       .build());
/*     */ 
/*     */   
/*  61 */   private final Setting<Double> targetRange = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  62 */       .name("target-range"))
/*  63 */       .description("The maximum distance to target players."))
/*  64 */       .defaultValue(10.0D)
/*  65 */       .min(0.0D)
/*  66 */       .sliderMax(30.0D)
/*  67 */       .build());
/*     */ 
/*     */   
/*  70 */   private final Setting<Boolean> predictMovement = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  71 */       .name("predict-movement"))
/*  72 */       .description("Predict target movement to account for ping."))
/*  73 */       .defaultValue(Boolean.valueOf(true)))
/*  74 */       .build());
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
/*     */   private final Setting<Boolean> doubles;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> rotate;
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
/*     */   
/*     */   private final List<class_2338> placePositions;
/*     */ 
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
/*     */   
/*     */   public AutoWeb() {
/* 138 */     super(Categories.Combat, "auto-web", "Automatically places webs on other players."); Objects.requireNonNull(this.predictMovement); this.ticksToPredict = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("ticks-to-predict")).description("How many ticks ahead we should predict for.")).defaultValue(10.0D).min(1.0D).sliderMax(30.0D).visible(this.predictMovement::get)).build()); this.doubles = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("doubles")).description("Places webs in the target's upper hitbox as well as the lower hitbox.")).defaultValue(Boolean.valueOf(false))).build()); this.rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("rotate")).description("Rotates towards the webs when placing.")).defaultValue(Boolean.valueOf(true))).build()); this.render = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("render")).description("Renders an overlay where webs are placed.")).defaultValue(Boolean.valueOf(true))).build()); Objects.requireNonNull(this.render);
/*     */     this.shapeMode = this.sgRender.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("shape-mode")).description("How the shapes are rendered.")).defaultValue(ShapeMode.Both)).visible(this.render::get)).build());
/*     */     this.sideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("side-color")).description("The side color of the placed web rendering.")).defaultValue(new SettingColor(239, 231, 244, 31)).visible(() -> (((Boolean)this.render.get()).booleanValue() && ((ShapeMode)this.shapeMode.get()).sides()))).build());
/*     */     this.lineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("line-color")).description("The line color of the placed web rendering.")).defaultValue(new SettingColor(255, 255, 255)).visible(() -> (((Boolean)this.render.get()).booleanValue() && ((ShapeMode)this.shapeMode.get()).lines()))).build());
/*     */     this.placePositions = new ArrayList<>();
/* 143 */     this.target = null; } public void onActivate() { this.target = null; }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 148 */     this.placePositions.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 153 */     this.placePositions.clear();
/*     */     
/* 155 */     if (TargetUtils.isBadTarget(this.target, ((Double)this.targetRange.get()).doubleValue())) {
/* 156 */       this.target = TargetUtils.getPlayerTarget(((Double)this.targetRange.get()).doubleValue(), (SortPriority)this.priority.get());
/* 157 */       if (TargetUtils.isBadTarget(this.target, ((Double)this.targetRange.get()).doubleValue())) {
/*     */         return;
/*     */       }
/*     */     } 
/* 161 */     FindItemResult webs = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8786 });
/* 162 */     if (!webs.found())
/*     */       return; 
/* 164 */     class_243 pos = this.target.method_73189();
/*     */ 
/*     */     
/* 167 */     if (((Boolean)this.predictMovement.get()).booleanValue()) {
/* 168 */       double dx = this.target.method_23317() - this.target.field_6014;
/* 169 */       double dy = this.target.method_23318() - this.target.field_6036;
/* 170 */       double dz = this.target.method_23321() - this.target.field_5969;
/* 171 */       pos = pos.method_1031(dx * ((Double)this.ticksToPredict.get()).doubleValue(), dy * ((Double)this.ticksToPredict.get()).doubleValue(), dz * ((Double)this.ticksToPredict.get()).doubleValue());
/*     */     } 
/*     */     
/* 174 */     class_2338 blockPos = class_2338.method_49638((class_2374)pos);
/*     */     
/* 176 */     if (canPlaceWebAt(blockPos)) {
/* 177 */       BlockUtils.place(blockPos, webs, ((Boolean)this.rotate.get()).booleanValue(), 0, false);
/* 178 */       this.placePositions.add(blockPos);
/*     */     } 
/*     */     
/* 181 */     if (((Boolean)this.doubles.get()).booleanValue() && canPlaceWebAt(blockPos.method_10084())) {
/* 182 */       BlockUtils.place(blockPos.method_10084(), webs, ((Boolean)this.rotate.get()).booleanValue(), 0, false);
/* 183 */       this.placePositions.add(blockPos.method_10084());
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean canPlaceWebAt(class_2338 blockPos) {
/* 188 */     if (!this.mc.field_1687.method_8320(blockPos).method_45474()) return false;
/*     */ 
/*     */     
/* 191 */     return !isOutOfRange(blockPos);
/*     */   }
/*     */   
/*     */   private boolean isOutOfRange(class_2338 blockPos) {
/* 195 */     class_243 pos = blockPos.method_46558();
/* 196 */     if (!PlayerUtils.isWithin(pos, ((Double)this.placeRange.get()).doubleValue())) return true;
/*     */     
/* 198 */     class_3959 raycastContext = new class_3959(this.mc.field_1724.method_33571(), pos, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)this.mc.field_1724);
/* 199 */     class_3965 result = this.mc.field_1687.method_17742(raycastContext);
/* 200 */     if (result == null || !result.method_17777().equals(blockPos)) {
/* 201 */       return !PlayerUtils.isWithin(pos, ((Double)this.placeWallsRange.get()).doubleValue());
/*     */     }
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/* 208 */     if (!((Boolean)this.render.get()).booleanValue() || this.placePositions.isEmpty())
/*     */       return; 
/* 210 */     for (class_2338 placePosition : this.placePositions)
/* 211 */       event.renderer.box(placePosition, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\AutoWeb.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */