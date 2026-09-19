/*     */ package meteordevelopment.meteorclient.systems.modules.render;
/*     */ 
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.meteor.KeyEvent;
/*     */ import meteordevelopment.meteorclient.events.meteor.MouseScrollEvent;
/*     */ import meteordevelopment.meteorclient.events.render.GetFovEvent;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Zoom
/*     */   extends Module
/*     */ {
/*  25 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  27 */   private final Setting<Double> zoom = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  28 */       .name("zoom"))
/*  29 */       .description("How much to zoom."))
/*  30 */       .defaultValue(6.0D)
/*  31 */       .min(1.0D)
/*  32 */       .build());
/*     */ 
/*     */   
/*  35 */   private final Setting<Double> scrollSensitivity = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  36 */       .name("scroll-sensitivity"))
/*  37 */       .description("Allows you to change zoom value using scroll wheel. 0 to disable."))
/*  38 */       .defaultValue(1.0D)
/*  39 */       .min(0.0D)
/*  40 */       .build());
/*     */ 
/*     */   
/*  43 */   private final Setting<Boolean> smooth = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  44 */       .name("smooth"))
/*  45 */       .description("Smooth transition."))
/*  46 */       .defaultValue(Boolean.valueOf(true)))
/*  47 */       .build());
/*     */ 
/*     */   
/*  50 */   private final Setting<Boolean> cinematic = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  51 */       .name("cinematic"))
/*  52 */       .description("Enables cinematic camera."))
/*  53 */       .defaultValue(Boolean.valueOf(false)))
/*  54 */       .build());
/*     */ 
/*     */   
/*  57 */   private final Setting<Boolean> hideHud = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  58 */       .name("hide-HUD"))
/*  59 */       .description("Whether or not to hide the Minecraft HUD."))
/*  60 */       .defaultValue(Boolean.valueOf(false)))
/*  61 */       .build());
/*     */ 
/*     */   
/*  64 */   private final Setting<Boolean> renderHands = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  65 */       .name("show-hands"))
/*  66 */       .description("Whether or not to render your hands."))
/*  67 */       .defaultValue(Boolean.valueOf(false)))
/*  68 */       .visible(() -> !((Boolean)this.hideHud.get()).booleanValue()))
/*  69 */       .build());
/*     */   
/*     */   private boolean enabled;
/*     */   
/*     */   private boolean preCinematic;
/*     */   
/*     */   private double preMouseSensitivity;
/*     */   private double value;
/*     */   private double lastFov;
/*     */   private double time;
/*     */   private boolean hudManualToggled;
/*     */   
/*     */   public Zoom() {
/*  82 */     super(Categories.Render, "zoom", "Zooms your view.");
/*  83 */     this.autoSubscribe = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  88 */     if (!this.enabled) {
/*  89 */       this.preCinematic = this.mc.field_1690.field_1914;
/*  90 */       this.preMouseSensitivity = ((Double)this.mc.field_1690.method_42495().method_41753()).doubleValue();
/*  91 */       this.value = ((Double)this.zoom.get()).doubleValue();
/*  92 */       this.lastFov = ((Integer)this.mc.field_1690.method_41808().method_41753()).intValue();
/*  93 */       this.time = 0.001D;
/*     */       
/*  95 */       MeteorClient.EVENT_BUS.subscribe(this);
/*  96 */       this.enabled = true;
/*     */     } 
/*     */     
/*  99 */     if (((Boolean)this.hideHud.get()).booleanValue() && !this.mc.field_1690.field_1842) {
/* 100 */       this.hudManualToggled = false;
/* 101 */       this.mc.field_1690.field_1842 = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 107 */     if (((Boolean)this.hideHud.get()).booleanValue() && !this.hudManualToggled) {
/* 108 */       this.mc.field_1690.field_1842 = false;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onKeyPressed(KeyEvent event) {
/* 114 */     if (event.key() != 290)
/* 115 */       return;  this.hudManualToggled = true;
/*     */   }
/*     */   
/*     */   public void onStop() {
/* 119 */     this.mc.field_1690.field_1914 = this.preCinematic;
/* 120 */     this.mc.field_1690.method_42495().method_41748(Double.valueOf(this.preMouseSensitivity));
/*     */     
/* 122 */     this.mc.field_1769.method_3292();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 127 */     this.mc.field_1690.field_1914 = ((Boolean)this.cinematic.get()).booleanValue();
/*     */     
/* 129 */     if (!((Boolean)this.cinematic.get()).booleanValue()) {
/* 130 */       this.mc.field_1690.method_42495().method_41748(Double.valueOf(this.preMouseSensitivity / Math.max(getScaling() * 0.5D, 1.0D)));
/*     */     }
/*     */     
/* 133 */     if (this.time == 0.0D) {
/* 134 */       MeteorClient.EVENT_BUS.unsubscribe(this);
/* 135 */       this.enabled = false;
/*     */       
/* 137 */       onStop();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onMouseScroll(MouseScrollEvent event) {
/* 143 */     if (((Double)this.scrollSensitivity.get()).doubleValue() > 0.0D && isActive()) {
/* 144 */       this.value += event.value * 0.25D * ((Double)this.scrollSensitivity.get()).doubleValue() * this.value;
/* 145 */       if (this.value < 1.0D) this.value = 1.0D;
/*     */       
/* 147 */       event.cancel();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender3D(Render3DEvent event) {
/* 153 */     if (!((Boolean)this.smooth.get()).booleanValue()) {
/* 154 */       this.time = isActive() ? 1.0D : 0.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 158 */     if (isActive()) { this.time += event.frameTime * 5.0D; }
/* 159 */     else { this.time -= event.frameTime * 5.0D; }
/*     */     
/* 161 */     this.time = class_3532.method_15350(this.time, 0.0D, 1.0D);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onGetFov(GetFovEvent event) {
/* 166 */     event.fov /= (float)getScaling();
/*     */     
/* 168 */     if (this.lastFov != event.fov) this.mc.field_1769.method_3292(); 
/* 169 */     this.lastFov = event.fov;
/*     */   }
/*     */   
/*     */   public double getScaling() {
/* 173 */     double delta = (this.time < 0.5D) ? (4.0D * this.time * this.time * this.time) : (1.0D - Math.pow(-2.0D * this.time + 2.0D, 3.0D) / 2.0D);
/* 174 */     return class_3532.method_16436(delta, 1.0D, this.value);
/*     */   }
/*     */   
/*     */   public boolean renderHands() {
/* 178 */     return (!isActive() || ((Boolean)this.renderHands.get()).booleanValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\Zoom.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */