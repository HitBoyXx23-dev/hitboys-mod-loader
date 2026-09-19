/*     */ package meteordevelopment.meteorclient.systems.hud.elements;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.ModuleListSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.systems.hud.Alignment;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudRenderer;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.combat.KillAura;
/*     */ import meteordevelopment.meteorclient.systems.modules.combat.Surround;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ 
/*     */ public class ModuleInfosHud extends HudElement {
/*  18 */   public static final HudElementInfo<ModuleInfosHud> INFO = new HudElementInfo(Hud.GROUP, "module-infos", "Displays if selected modules are enabled or disabled.", ModuleInfosHud::new);
/*     */   
/*  20 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  22 */   private final Setting<List<Module>> modules = this.sgGeneral.add((Setting)((ModuleListSetting.Builder)((ModuleListSetting.Builder)(new ModuleListSetting.Builder())
/*  23 */       .name("modules"))
/*  24 */       .description("Which modules to display"))
/*  25 */       .defaultValue(new Class[] { KillAura.class, CrystalAura.class, AnchorAura.class, BedAura.class, Surround.class
/*  26 */         }).build());
/*     */ 
/*     */   
/*  29 */   private final Setting<Boolean> additionalInfo = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  30 */       .name("additional-info"))
/*  31 */       .description("Shows additional info from the module next to the name in the module info list."))
/*  32 */       .defaultValue(Boolean.valueOf(true)))
/*  33 */       .build());
/*     */ 
/*     */   
/*  36 */   private final Setting<Boolean> textShadow = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  37 */       .name("text-shadow"))
/*  38 */       .description("Renders shadow behind text."))
/*  39 */       .defaultValue(Boolean.valueOf(true)))
/*  40 */       .build());
/*     */ 
/*     */   
/*  43 */   private final Setting<SettingColor> moduleColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  44 */       .name("module-color"))
/*  45 */       .description("Module color."))
/*  46 */       .defaultValue(new SettingColor())
/*  47 */       .build());
/*     */ 
/*     */   
/*  50 */   private final Setting<SettingColor> onColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  51 */       .name("on-color"))
/*  52 */       .description("Color when module is on."))
/*  53 */       .defaultValue(new SettingColor(25, 225, 25))
/*  54 */       .build());
/*     */ 
/*     */   
/*  57 */   private final Setting<SettingColor> offColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  58 */       .name("off-color"))
/*  59 */       .description("Color when module is off."))
/*  60 */       .defaultValue(new SettingColor(225, 25, 25))
/*  61 */       .build());
/*     */ 
/*     */   
/*  64 */   private final Setting<Alignment> alignment = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  65 */       .name("alignment"))
/*  66 */       .description("Horizontal alignment."))
/*  67 */       .defaultValue(Alignment.Auto))
/*  68 */       .build());
/*     */ 
/*     */   
/*     */   public ModuleInfosHud() {
/*  72 */     super(INFO);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(HudRenderer renderer) {
/*  77 */     if (Modules.get() == null || ((List)this.modules.get()).isEmpty()) {
/*  78 */       renderer.text("Module Info", this.x, this.y, (Color)this.moduleColor.get(), ((Boolean)this.textShadow.get()).booleanValue());
/*  79 */       setSize(renderer.textWidth("Module Info"), renderer.textHeight());
/*     */       
/*     */       return;
/*     */     } 
/*  83 */     double y = this.y;
/*     */     
/*  85 */     double width = 0.0D;
/*  86 */     double height = 0.0D;
/*     */     
/*  88 */     int i = 0;
/*  89 */     for (Module module : this.modules.get()) {
/*  90 */       double moduleWidth = renderer.textWidth(module.title) + renderer.textWidth(" ");
/*  91 */       String text = null;
/*     */       
/*  93 */       if (module.isActive()) {
/*  94 */         if (((Boolean)this.additionalInfo.get()).booleanValue()) {
/*  95 */           String info = module.getInfoString();
/*  96 */           if (info != null) text = info;
/*     */         
/*     */         } 
/*  99 */         if (text == null) text = "ON"; 
/*     */       } else {
/* 101 */         text = "OFF";
/* 102 */       }  moduleWidth += renderer.textWidth(text);
/*     */       
/* 104 */       double x = this.x + alignX(moduleWidth, (Alignment)this.alignment.get());
/* 105 */       x = renderer.text(module.title, x, y, (Color)this.moduleColor.get(), ((Boolean)this.textShadow.get()).booleanValue());
/* 106 */       renderer.text(text, x + renderer.textWidth(" "), y, module.isActive() ? (Color)this.onColor.get() : (Color)this.offColor.get(), ((Boolean)this.textShadow.get()).booleanValue());
/* 107 */       y += renderer.textHeight() + 2.0D;
/*     */       
/* 109 */       width = Math.max(width, moduleWidth);
/* 110 */       height += renderer.textHeight();
/* 111 */       if (i > 0) height += 2.0D;
/*     */       
/* 113 */       i++;
/*     */     } 
/*     */     
/* 116 */     setSize(width, height);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\elements\ModuleInfosHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */