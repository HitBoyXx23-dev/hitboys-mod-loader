/*     */ package meteordevelopment.meteorclient.gui.themes.meteor;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.WidgetScreen;
/*     */ import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
/*     */ import meteordevelopment.meteorclient.gui.utils.AlignmentX;
/*     */ import meteordevelopment.meteorclient.gui.utils.CharFilter;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WAccount;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WQuad;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WTooltip;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WTopBar;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WVerticalSeparator;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WWindow;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WDropdown;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WSlider;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedMinus;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WFavorite;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WMinus;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WTriangle;
/*     */ import meteordevelopment.meteorclient.renderer.text.TextRenderer;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.accounts.Account;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ 
/*     */ public class MeteorGuiTheme extends GuiTheme {
/*  38 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  39 */   private final SettingGroup sgColors = this.settings.createGroup("Colors");
/*  40 */   private final SettingGroup sgTextColors = this.settings.createGroup("Text");
/*  41 */   private final SettingGroup sgBackgroundColors = this.settings.createGroup("Background");
/*  42 */   private final SettingGroup sgOutline = this.settings.createGroup("Outline");
/*  43 */   private final SettingGroup sgSeparator = this.settings.createGroup("Separator");
/*  44 */   private final SettingGroup sgScrollbar = this.settings.createGroup("Scrollbar");
/*  45 */   private final SettingGroup sgSlider = this.settings.createGroup("Slider");
/*  46 */   private final SettingGroup sgStarscript = this.settings.createGroup("Starscript");
/*     */ 
/*     */ 
/*     */   
/*  50 */   public final Setting<Double> scale = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  51 */       .name("scale"))
/*  52 */       .description("Scale of the GUI."))
/*  53 */       .defaultValue(1.0D)
/*  54 */       .min(0.75D)
/*  55 */       .sliderRange(0.75D, 4.0D)
/*  56 */       .onSliderRelease()
/*  57 */       .onChanged(aDouble -> {
/*     */           if (MeteorClient.mc.field_1755 instanceof WidgetScreen)
/*     */             ((WidgetScreen)MeteorClient.mc.field_1755).invalidate(); 
/*  60 */         })).build());
/*     */ 
/*     */   
/*  63 */   public final Setting<AlignmentX> moduleAlignment = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  64 */       .name("module-alignment"))
/*  65 */       .description("How module titles are aligned."))
/*  66 */       .defaultValue(AlignmentX.Center))
/*  67 */       .build());
/*     */ 
/*     */   
/*  70 */   public final Setting<Boolean> categoryIcons = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  71 */       .name("category-icons"))
/*  72 */       .description("Adds item icons to module categories."))
/*  73 */       .defaultValue(Boolean.valueOf(false)))
/*  74 */       .build());
/*     */ 
/*     */   
/*     */   public final Setting<Boolean> hideHUD;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> accentColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> checkboxColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> plusColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> minusColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> favoriteColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> textColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> textSecondaryColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> textHighlightColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> titleTextColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> loggedInColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> placeholderColor;
/*     */ 
/*     */   
/*     */   public final ThreeStateColorSetting backgroundColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> moduleBackground;
/*     */ 
/*     */   
/*     */   public final ThreeStateColorSetting outlineColor;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> separatorText;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> separatorCenter;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> separatorEdges;
/*     */ 
/*     */   
/*     */   public final ThreeStateColorSetting scrollbarColor;
/*     */ 
/*     */   
/*     */   public final ThreeStateColorSetting sliderHandle;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> sliderLeft;
/*     */ 
/*     */   
/*     */   public final Setting<SettingColor> sliderRight;
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> starscriptText;
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> starscriptBraces;
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> starscriptParenthesis;
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> starscriptDots;
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> starscriptCommas;
/*     */   
/*     */   private final Setting<SettingColor> starscriptOperators;
/*     */   
/*     */   private final Setting<SettingColor> starscriptStrings;
/*     */   
/*     */   private final Setting<SettingColor> starscriptNumbers;
/*     */   
/*     */   private final Setting<SettingColor> starscriptKeywords;
/*     */   
/*     */   private final Setting<SettingColor> starscriptAccessedObjects;
/*     */ 
/*     */   
/*     */   public MeteorGuiTheme() {
/* 169 */     super("Meteor"); this.hideHUD = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("hide-HUD")).description("Hide HUD when in GUI.")).defaultValue(Boolean.valueOf(false))).onChanged(v -> { if (MeteorClient.mc.field_1755 instanceof WidgetScreen)
/*     */               MeteorClient.mc.field_1690.field_1842 = v.booleanValue(); 
/* 171 */           })).build()); this.accentColor = color("accent", "Main color of the GUI.", new SettingColor(145, 61, 226)); this.checkboxColor = color("checkbox", "Color of checkbox.", new SettingColor(145, 61, 226)); this.plusColor = color("plus", "Color of plus button.", new SettingColor(50, 255, 50)); this.minusColor = color("minus", "Color of minus button.", new SettingColor(255, 50, 50)); this.favoriteColor = color("favorite", "Color of checked favorite button.", new SettingColor(250, 215, 0)); this.textColor = color(this.sgTextColors, "text", "Color of text.", new SettingColor(255, 255, 255)); this.textSecondaryColor = color(this.sgTextColors, "text-secondary-text", "Color of secondary text.", new SettingColor(150, 150, 150)); this.textHighlightColor = color(this.sgTextColors, "text-highlight", "Color of text highlighting.", new SettingColor(45, 125, 245, 100)); this.titleTextColor = color(this.sgTextColors, "title-text", "Color of title text.", new SettingColor(255, 255, 255)); this.loggedInColor = color(this.sgTextColors, "logged-in-text", "Color of logged in account name.", new SettingColor(45, 225, 45)); this.placeholderColor = color(this.sgTextColors, "placeholder", "Color of placeholder text.", new SettingColor(255, 255, 255, 20)); this.backgroundColor = new ThreeStateColorSetting(this.sgBackgroundColors, "background", new SettingColor(20, 20, 20, 200), new SettingColor(30, 30, 30, 200), new SettingColor(40, 40, 40, 200)); this.moduleBackground = color(this.sgBackgroundColors, "module-background", "Color of module background when active.", new SettingColor(50, 50, 50)); this.outlineColor = new ThreeStateColorSetting(this.sgOutline, "outline", new SettingColor(0, 0, 0), new SettingColor(10, 10, 10), new SettingColor(20, 20, 20)); this.separatorText = color(this.sgSeparator, "separator-text", "Color of separator text", new SettingColor(255, 255, 255)); this.separatorCenter = color(this.sgSeparator, "separator-center", "Center color of separators.", new SettingColor(255, 255, 255)); this.separatorEdges = color(this.sgSeparator, "separator-edges", "Color of separator edges.", new SettingColor(225, 225, 225, 150)); this.scrollbarColor = new ThreeStateColorSetting(this.sgScrollbar, "Scrollbar", new SettingColor(30, 30, 30, 200), new SettingColor(40, 40, 40, 200), new SettingColor(50, 50, 50, 200)); this.sliderHandle = new ThreeStateColorSetting(this.sgSlider, "slider-handle", new SettingColor(130, 0, 255), new SettingColor(140, 30, 255), new SettingColor(150, 60, 255)); this.sliderLeft = color(this.sgSlider, "slider-left", "Color of slider left part.", new SettingColor(100, 35, 170)); this.sliderRight = color(this.sgSlider, "slider-right", "Color of slider right part.", new SettingColor(50, 50, 50)); this.starscriptText = color(this.sgStarscript, "starscript-text", "Color of text in Starscript code.", new SettingColor(169, 183, 198)); this.starscriptBraces = color(this.sgStarscript, "starscript-braces", "Color of braces in Starscript code.", new SettingColor(150, 150, 150)); this.starscriptParenthesis = color(this.sgStarscript, "starscript-parenthesis", "Color of parenthesis in Starscript code.", new SettingColor(169, 183, 198)); this.starscriptDots = color(this.sgStarscript, "starscript-dots", "Color of dots in starscript code.", new SettingColor(169, 183, 198)); this.starscriptCommas = color(this.sgStarscript, "starscript-commas", "Color of commas in starscript code.", new SettingColor(169, 183, 198)); this.starscriptOperators = color(this.sgStarscript, "starscript-operators", "Color of operators in Starscript code.", new SettingColor(169, 183, 198)); this.starscriptStrings = color(this.sgStarscript, "starscript-strings", "Color of strings in Starscript code.", new SettingColor(106, 135, 89)); this.starscriptNumbers = color(this.sgStarscript, "starscript-numbers", "Color of numbers in Starscript code.", new SettingColor(104, 141, 187)); this.starscriptKeywords = color(this.sgStarscript, "starscript-keywords", "Color of keywords in Starscript code.", new SettingColor(204, 120, 50)); this.starscriptAccessedObjects = color(this.sgStarscript, "starscript-accessed-objects", "Color of accessed objects (before a dot) in Starscript code.", new SettingColor(152, 118, 170)); this.settingsFactory = (SettingsWidgetFactory)new DefaultSettingsWidgetFactory(this);
/*     */   }
/*     */   
/*     */   private Setting<SettingColor> color(SettingGroup group, String name, String description, SettingColor color) {
/* 175 */     return group.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/* 176 */         .name(name + "-color"))
/* 177 */         .description(description))
/* 178 */         .defaultValue(color)
/* 179 */         .build());
/*     */   }
/*     */   private Setting<SettingColor> color(String name, String description, SettingColor color) {
/* 182 */     return color(this.sgColors, name, description, color);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WWindow window(WWidget icon, String title) {
/* 189 */     return (WWindow)w((WWidget)new WMeteorWindow(icon, title));
/*     */   }
/*     */ 
/*     */   
/*     */   public WLabel label(String text, boolean title, double maxWidth) {
/* 194 */     if (maxWidth == 0.0D && !text.contains("\n")) return (WLabel)w((WWidget)new WMeteorLabel(text, title)); 
/* 195 */     return (WLabel)w((WWidget)new WMeteorMultiLabel(text, title, maxWidth));
/*     */   }
/*     */ 
/*     */   
/*     */   public WHorizontalSeparator horizontalSeparator(String text) {
/* 200 */     return (WHorizontalSeparator)w((WWidget)new WMeteorHorizontalSeparator(text));
/*     */   }
/*     */ 
/*     */   
/*     */   public WVerticalSeparator verticalSeparator() {
/* 205 */     return (WVerticalSeparator)w((WWidget)new WMeteorVerticalSeparator());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WButton button(String text, GuiTexture texture) {
/* 210 */     return (WButton)w((WWidget)new WMeteorButton(text, texture));
/*     */   }
/*     */ 
/*     */   
/*     */   protected WConfirmedButton confirmedButton(String text, String confirmText, GuiTexture texture) {
/* 215 */     return (WConfirmedButton)w((WWidget)new WMeteorConfirmedButton(text, confirmText, texture));
/*     */   }
/*     */ 
/*     */   
/*     */   public WMinus minus() {
/* 220 */     return (WMinus)w((WWidget)new WMeteorMinus());
/*     */   }
/*     */ 
/*     */   
/*     */   public WConfirmedMinus confirmedMinus() {
/* 225 */     return (WConfirmedMinus)w((WWidget)new WMeteorConfirmedMinus());
/*     */   }
/*     */ 
/*     */   
/*     */   public WPlus plus() {
/* 230 */     return (WPlus)w((WWidget)new WMeteorPlus());
/*     */   }
/*     */ 
/*     */   
/*     */   public WCheckbox checkbox(boolean checked) {
/* 235 */     return (WCheckbox)w((WWidget)new WMeteorCheckbox(checked));
/*     */   }
/*     */ 
/*     */   
/*     */   public WSlider slider(double value, double min, double max) {
/* 240 */     return (WSlider)w((WWidget)new WMeteorSlider(value, min, max));
/*     */   }
/*     */ 
/*     */   
/*     */   public WTextBox textBox(String text, String placeholder, CharFilter filter, Class<? extends WTextBox.Renderer> renderer) {
/* 245 */     return (WTextBox)w((WWidget)new WMeteorTextBox(text, placeholder, filter, renderer));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> WDropdown<T> dropdown(T[] values, T value) {
/* 250 */     return (WDropdown<T>)w((WWidget)new WMeteorDropdown((Object[])values, value));
/*     */   }
/*     */ 
/*     */   
/*     */   public WTriangle triangle() {
/* 255 */     return (WTriangle)w((WWidget)new WMeteorTriangle());
/*     */   }
/*     */ 
/*     */   
/*     */   public WTooltip tooltip(String text) {
/* 260 */     return (WTooltip)w((WWidget)new WMeteorTooltip(text));
/*     */   }
/*     */ 
/*     */   
/*     */   public WView view() {
/* 265 */     return (WView)w((WWidget)new WMeteorView());
/*     */   }
/*     */ 
/*     */   
/*     */   public WSection section(String title, boolean expanded, WWidget headerWidget) {
/* 270 */     return (WSection)w((WWidget)new WMeteorSection(title, expanded, headerWidget));
/*     */   }
/*     */ 
/*     */   
/*     */   public WAccount account(WidgetScreen screen, Account<?> account) {
/* 275 */     return (WAccount)w((WWidget)new WMeteorAccount(screen, account));
/*     */   }
/*     */ 
/*     */   
/*     */   public WWidget module(Module module, String title) {
/* 280 */     return w((WWidget)new WMeteorModule(module, title));
/*     */   }
/*     */ 
/*     */   
/*     */   public WQuad quad(Color color) {
/* 285 */     return (WQuad)w((WWidget)new WMeteorQuad(color));
/*     */   }
/*     */ 
/*     */   
/*     */   public WTopBar topBar() {
/* 290 */     return (WTopBar)w((WWidget)new WMeteorTopBar());
/*     */   }
/*     */ 
/*     */   
/*     */   public WFavorite favorite(boolean checked) {
/* 295 */     return (WFavorite)w((WWidget)new WMeteorFavorite(checked));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color textColor() {
/* 302 */     return (Color)this.textColor.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color textSecondaryColor() {
/* 307 */     return (Color)this.textSecondaryColor.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Color starscriptTextColor() {
/* 314 */     return (Color)this.starscriptText.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color starscriptBraceColor() {
/* 319 */     return (Color)this.starscriptBraces.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color starscriptParenthesisColor() {
/* 324 */     return (Color)this.starscriptParenthesis.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color starscriptDotColor() {
/* 329 */     return (Color)this.starscriptDots.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color starscriptCommaColor() {
/* 334 */     return (Color)this.starscriptCommas.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color starscriptOperatorColor() {
/* 339 */     return (Color)this.starscriptOperators.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color starscriptStringColor() {
/* 344 */     return (Color)this.starscriptStrings.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color starscriptNumberColor() {
/* 349 */     return (Color)this.starscriptNumbers.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color starscriptKeywordColor() {
/* 354 */     return (Color)this.starscriptKeywords.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public Color starscriptAccessedObjectColor() {
/* 359 */     return (Color)this.starscriptAccessedObjects.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextRenderer textRenderer() {
/* 366 */     return TextRenderer.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public double scale(double value) {
/* 371 */     double scaled = value * ((Double)this.scale.get()).doubleValue();
/*     */     
/* 373 */     if (class_6417.field_52734) {
/* 374 */       scaled /= MeteorClient.mc.method_22683().method_4480() / MeteorClient.mc.method_22683().method_4489();
/*     */     }
/*     */     
/* 377 */     return scaled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean categoryIcons() {
/* 382 */     return ((Boolean)this.categoryIcons.get()).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hideHUD() {
/* 387 */     return ((Boolean)this.hideHUD.get()).booleanValue();
/*     */   }
/*     */   public class ThreeStateColorSetting { private final Setting<SettingColor> normal;
/*     */     private final Setting<SettingColor> hovered;
/*     */     private final Setting<SettingColor> pressed;
/*     */     
/*     */     public ThreeStateColorSetting(SettingGroup group, String name, SettingColor c1, SettingColor c2, SettingColor c3) {
/* 394 */       this.normal = MeteorGuiTheme.this.color(group, name, "Color of " + name + ".", c1);
/* 395 */       this.hovered = MeteorGuiTheme.this.color(group, "hovered-" + name, "Color of " + name + " when hovered.", c2);
/* 396 */       this.pressed = MeteorGuiTheme.this.color(group, "pressed-" + name, "Color of " + name + " when pressed.", c3);
/*     */     }
/*     */     
/*     */     public SettingColor get() {
/* 400 */       return (SettingColor)this.normal.get();
/*     */     }
/*     */     
/*     */     public SettingColor get(boolean pressed, boolean hovered, boolean bypassDisableHoverColor) {
/* 404 */       if (pressed) return (SettingColor)this.pressed.get(); 
/* 405 */       return (hovered && (bypassDisableHoverColor || !MeteorGuiTheme.this.disableHoverColor)) ? (SettingColor)this.hovered.get() : (SettingColor)this.normal.get();
/*     */     }
/*     */     
/*     */     public SettingColor get(boolean pressed, boolean hovered) {
/* 409 */       return get(pressed, hovered, false);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\MeteorGuiTheme.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */