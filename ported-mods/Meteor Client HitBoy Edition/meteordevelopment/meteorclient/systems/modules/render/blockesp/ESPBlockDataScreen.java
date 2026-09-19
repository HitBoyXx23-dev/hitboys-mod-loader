/*     */ package meteordevelopment.meteorclient.systems.modules.render.blockesp;
/*     */ import java.util.Map;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BlockDataSetting;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.GenericSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.settings.Settings;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_2248;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public class ESPBlockDataScreen extends WindowScreen {
/*     */   private final ESPBlockData blockData;
/*     */   
/*     */   public ESPBlockDataScreen(GuiTheme theme, ESPBlockData blockData, class_2248 block, BlockDataSetting<ESPBlockData> setting) {
/*  22 */     this(theme, blockData, (Setting<?>)setting, () -> ((Map<class_2248, ESPBlockData>)setting.get()).put(block, blockData));
/*     */   } private final Setting<?> setting; @Nullable
/*     */   private final Runnable firstChangeConsumer;
/*     */   public ESPBlockDataScreen(GuiTheme theme, ESPBlockData blockData, GenericSetting<ESPBlockData> setting) {
/*  26 */     this(theme, blockData, (Setting<?>)setting, (Runnable)null);
/*     */   }
/*     */   
/*     */   private ESPBlockDataScreen(GuiTheme theme, ESPBlockData blockData, Setting<?> setting, @Nullable Runnable firstChangeConsumer) {
/*  30 */     super(theme, "Configure Block");
/*     */     
/*  32 */     this.blockData = blockData;
/*  33 */     this.setting = setting;
/*  34 */     this.firstChangeConsumer = firstChangeConsumer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/*  39 */     Settings settings = new Settings();
/*  40 */     SettingGroup sgGeneral = settings.getDefaultGroup();
/*  41 */     SettingGroup sgTracer = settings.createGroup("Tracer");
/*     */     
/*  43 */     sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  44 */         .name("shape-mode"))
/*  45 */         .description("How the shape is rendered."))
/*  46 */         .defaultValue(ShapeMode.Lines))
/*  47 */         .onModuleActivated(shapeModeSetting -> shapeModeSetting.set(this.blockData.shapeMode)))
/*  48 */         .onChanged(shapeMode -> {
/*     */             if (this.blockData.shapeMode != shapeMode) {
/*     */               this.blockData.shapeMode = shapeMode;
/*     */               
/*     */               onChanged();
/*     */             } 
/*  54 */           })).build());
/*     */ 
/*     */     
/*  57 */     sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  58 */         .name("line-color"))
/*  59 */         .description("Color of lines."))
/*  60 */         .defaultValue(new SettingColor(0, 255, 200))
/*  61 */         .onModuleActivated(settingColorSetting -> ((SettingColor)settingColorSetting.get()).set((Color)this.blockData.lineColor)))
/*  62 */         .onChanged(settingColor -> {
/*     */             if (!this.blockData.lineColor.equals(settingColor)) {
/*     */               this.blockData.lineColor.set((Color)settingColor);
/*     */               
/*     */               onChanged();
/*     */             } 
/*  68 */           })).build());
/*     */ 
/*     */     
/*  71 */     sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  72 */         .name("side-color"))
/*  73 */         .description("Color of sides."))
/*  74 */         .defaultValue(new SettingColor(0, 255, 200, 25))
/*  75 */         .onModuleActivated(settingColorSetting -> ((SettingColor)settingColorSetting.get()).set((Color)this.blockData.sideColor)))
/*  76 */         .onChanged(settingColor -> {
/*     */             if (!this.blockData.sideColor.equals(settingColor)) {
/*     */               this.blockData.sideColor.set((Color)settingColor);
/*     */               
/*     */               onChanged();
/*     */             } 
/*  82 */           })).build());
/*     */ 
/*     */     
/*  85 */     sgTracer.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  86 */         .name("tracer"))
/*  87 */         .description("If tracer line is allowed to this block."))
/*  88 */         .defaultValue(Boolean.valueOf(true)))
/*  89 */         .onModuleActivated(booleanSetting -> booleanSetting.set(Boolean.valueOf(this.blockData.tracer))))
/*  90 */         .onChanged(aBoolean -> {
/*     */             if (this.blockData.tracer != aBoolean.booleanValue()) {
/*     */               this.blockData.tracer = aBoolean.booleanValue();
/*     */               
/*     */               onChanged();
/*     */             } 
/*  96 */           })).build());
/*     */ 
/*     */     
/*  99 */     sgTracer.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/* 100 */         .name("tracer-color"))
/* 101 */         .description("Color of tracer line."))
/* 102 */         .defaultValue(new SettingColor(0, 255, 200, 125))
/* 103 */         .onModuleActivated(settingColorSetting -> ((SettingColor)settingColorSetting.get()).set((Color)this.blockData.tracerColor)))
/* 104 */         .onChanged(settingColor -> {
/*     */             if (!this.blockData.tracerColor.equals(settingColor)) {
/*     */               this.blockData.tracerColor.set((Color)settingColor);
/*     */               
/*     */               onChanged();
/*     */             } 
/* 110 */           })).build());
/*     */ 
/*     */     
/* 113 */     settings.onActivated();
/* 114 */     add(this.theme.settings(settings)).expandX();
/*     */   }
/*     */   
/*     */   private void onChanged() {
/* 118 */     if (!this.blockData.isChanged() && this.firstChangeConsumer != null) {
/* 119 */       this.firstChangeConsumer.run();
/*     */     }
/*     */     
/* 122 */     this.setting.onChanged();
/* 123 */     this.blockData.changed();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\blockesp\ESPBlockDataScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */