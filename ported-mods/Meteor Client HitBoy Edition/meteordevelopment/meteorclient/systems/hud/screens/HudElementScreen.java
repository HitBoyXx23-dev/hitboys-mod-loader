/*     */ package meteordevelopment.meteorclient.systems.hud.screens;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WMinus;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.settings.Settings;
/*     */ import meteordevelopment.meteorclient.systems.hud.HudElement;
/*     */ import meteordevelopment.meteorclient.systems.hud.XAnchor;
/*     */ import meteordevelopment.meteorclient.systems.hud.YAnchor;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*     */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*     */ import net.minecraft.class_332;
/*     */ 
/*     */ 
/*     */ public class HudElementScreen
/*     */   extends WindowScreen
/*     */ {
/*     */   private final HudElement element;
/*     */   private WContainer settingsC1;
/*     */   private WContainer settingsC2;
/*     */   private final Settings settings;
/*     */   
/*     */   public HudElementScreen(GuiTheme theme, HudElement element) {
/*  35 */     super(theme, element.info.title);
/*     */     
/*  37 */     this.element = element;
/*     */     
/*  39 */     this.settings = new Settings();
/*  40 */     SettingGroup sg = this.settings.createGroup("Anchors");
/*  41 */     sg.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  42 */         .name("auto-anchors"))
/*  43 */         .description("Automatically assigns anchors based on the position."))
/*  44 */         .defaultValue(Boolean.valueOf(true)))
/*  45 */         .onModuleActivated(booleanSetting -> booleanSetting.set(Boolean.valueOf(element.autoAnchors))))
/*  46 */         .onChanged(aBoolean -> {
/*     */             if (aBoolean.booleanValue())
/*     */               element.box.updateAnchors(); 
/*     */             element.autoAnchors = aBoolean.booleanValue();
/*  50 */           })).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     Objects.requireNonNull(element.box); sg.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("x-anchor")).description("Horizontal anchor.")).defaultValue(XAnchor.Left)).visible(() -> !element.autoAnchors)).onModuleActivated(xAnchorSetting -> xAnchorSetting.set(element.box.xAnchor))).onChanged(element.box::setXAnchor))
/*  59 */         .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     Objects.requireNonNull(element.box); sg.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("y-anchor")).description("Vertical anchor.")).defaultValue(YAnchor.Top)).visible(() -> !element.autoAnchors)).onModuleActivated(yAnchorSetting -> yAnchorSetting.set(element.box.yAnchor))).onChanged(element.box::setYAnchor))
/*  68 */         .build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/*  75 */     add((WWidget)this.theme.label(this.element.info.description, Utils.getWindowWidth() / 2.0D));
/*     */ 
/*     */     
/*  78 */     if (this.element.settings.sizeGroups() > 0) {
/*  79 */       this.element.settings.onActivated();
/*     */       
/*  81 */       this.settingsC1 = (WContainer)add((WWidget)this.theme.verticalList()).expandX().widget();
/*  82 */       this.settingsC1.add(this.theme.settings(this.element.settings)).expandX();
/*     */     } 
/*     */ 
/*     */     
/*  86 */     this.settings.onActivated();
/*     */     
/*  88 */     this.settingsC2 = (WContainer)add((WWidget)this.theme.verticalList()).expandX().widget();
/*  89 */     this.settingsC2.add(this.theme.settings(this.settings)).expandX();
/*     */     
/*  91 */     add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */ 
/*     */     
/*  94 */     WWidget widget = this.element.getWidget(this.theme);
/*     */     
/*  96 */     if (widget != null) {
/*  97 */       Cell<WWidget> cell = add(widget);
/*  98 */       if (widget instanceof WContainer) cell.expandX(); 
/*  99 */       add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */     } 
/*     */ 
/*     */     
/* 103 */     WHorizontalList bottomList = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */ 
/*     */     
/* 106 */     bottomList.add((WWidget)this.theme.label("Active:"));
/* 107 */     WCheckbox active = (WCheckbox)bottomList.add((WWidget)this.theme.checkbox(this.element.isActive())).widget();
/* 108 */     active.action = (() -> {
/*     */         if (this.element.isActive() != active.checked) {
/*     */           this.element.toggle();
/*     */         }
/*     */       });
/* 113 */     WMinus remove = (WMinus)bottomList.add((WWidget)this.theme.minus()).expandCellX().right().widget();
/* 114 */     remove.action = (() -> {
/*     */         this.element.remove();
/*     */         method_25419();
/*     */       });
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25393() {
/* 122 */     super.method_25393();
/*     */     
/* 124 */     if (this.settingsC1 != null) {
/* 125 */       this.element.settings.tick(this.settingsC1, this.theme);
/*     */     }
/*     */     
/* 128 */     this.settings.tick(this.settingsC2, this.theme);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onRenderBefore(class_332 drawContext, float delta) {
/* 133 */     HudEditorScreen.renderElements(drawContext);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean toClipboard() {
/* 138 */     return NbtUtils.toClipboard((ISerializable)this.element);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fromClipboard() {
/* 143 */     return NbtUtils.fromClipboard((ISerializable)this.element);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\screens\HudElementScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */