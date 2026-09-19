/*     */ package meteordevelopment.meteorclient.systems.hud.elements;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Supplier;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudElement;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudRenderer;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.meteorclient.utils.world.TickRate;
/*     */ 
/*     */ public class LagNotifierHud extends HudElement {
/*  18 */   public static final HudElementInfo<LagNotifierHud> INFO = new HudElementInfo(Hud.GROUP, "lag-notifier", "Displays if the server is lagging in ticks.", LagNotifierHud::new);
/*     */   
/*  20 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  21 */   private final SettingGroup sgScale = this.settings.createGroup("Scale");
/*  22 */   private final SettingGroup sgBackground = this.settings.createGroup("Background");
/*     */ 
/*     */ 
/*     */   
/*  26 */   private final Setting<Boolean> shadow = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  27 */       .name("shadow"))
/*  28 */       .description("Text shadow."))
/*  29 */       .defaultValue(Boolean.valueOf(true)))
/*  30 */       .build());
/*     */ 
/*     */   
/*  33 */   private final Setting<SettingColor> textColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  34 */       .name("text-color"))
/*  35 */       .description("A."))
/*  36 */       .defaultValue(new SettingColor())
/*  37 */       .build());
/*     */ 
/*     */   
/*  40 */   private final Setting<SettingColor> color1 = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  41 */       .name("color-1"))
/*  42 */       .description("First color."))
/*  43 */       .defaultValue(new SettingColor(255, 255, 5))
/*  44 */       .build());
/*     */ 
/*     */   
/*  47 */   private final Setting<SettingColor> color2 = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  48 */       .name("color-2"))
/*  49 */       .description("Second color."))
/*  50 */       .defaultValue(new SettingColor(235, 158, 52))
/*  51 */       .build());
/*     */ 
/*     */   
/*  54 */   private final Setting<SettingColor> color3 = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  55 */       .name("color-3"))
/*  56 */       .description("Third color."))
/*  57 */       .defaultValue(new SettingColor(225, 45, 45))
/*  58 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private final Setting<Boolean> customScale = this.sgScale.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  64 */       .name("custom-scale"))
/*  65 */       .description("Applies a custom scale to this hud element."))
/*  66 */       .defaultValue(Boolean.valueOf(false)))
/*  67 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> scale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> background;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> backgroundColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LagNotifierHud() {
/*  98 */     super(INFO);
/*     */     Objects.requireNonNull(this.customScale);
/*     */     this.scale = this.sgScale.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("scale")).description("Custom scale.")).visible(this.customScale::get)).defaultValue(1.0D).min(0.5D).sliderRange(0.5D, 3.0D).build());
/*     */     this.background = this.sgBackground.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("background")).description("Displays background.")).defaultValue(Boolean.valueOf(false))).build());
/*     */     Objects.requireNonNull(this.background);
/* 103 */     this.backgroundColor = this.sgBackground.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("background-color")).description("Color used for the background.")).visible(this.background::get)).defaultValue(new SettingColor(25, 25, 25, 50)).build()); } public void render(HudRenderer renderer) { float timeSinceLastTick = TickRate.INSTANCE.getTimeSinceLastTick();
/* 104 */     if (timeSinceLastTick < 1.1F && !isInEditor()) {
/*     */       return;
/*     */     }
/*     */     
/* 108 */     Color color = (isInEditor() || timeSinceLastTick > 10.0F) ? (Color)this.color3.get() : ((timeSinceLastTick > 3.0F) ? (Color)this.color2.get() : (Color)this.color1.get());
/*     */     
/* 110 */     String info = isInEditor() ? "10.2" : String.format("%.1f", new Object[] { Float.valueOf(timeSinceLastTick) });
/*     */     
/* 112 */     render(renderer, info, color);
/* 113 */     if (((Boolean)this.background.get()).booleanValue()) {
/* 114 */       renderer.quad(this.x, this.y, getWidth(), getHeight(), (Color)this.backgroundColor.get());
/*     */     } }
/*     */ 
/*     */   
/*     */   private void render(HudRenderer renderer, String right, Color rightColor) {
/* 119 */     double x2 = renderer.text("Time since last tick ", this.x, this.y, (Color)this.textColor.get(), ((Boolean)this.shadow.get()).booleanValue(), getScale());
/* 120 */     x2 = renderer.text(right, x2, this.y, rightColor, ((Boolean)this.shadow.get()).booleanValue(), getScale());
/*     */     
/* 122 */     setSize(x2 - this.x, renderer.textHeight(((Boolean)this.shadow.get()).booleanValue(), getScale()));
/*     */   }
/*     */   
/*     */   private double getScale() {
/* 126 */     return ((Boolean)this.customScale.get()).booleanValue() ? ((Double)this.scale.get()).doubleValue() : Hud.get().getTextScale();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\elements\LagNotifierHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */