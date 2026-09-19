/*    */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*    */ import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedButton;
/*    */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*    */ import meteordevelopment.meteorclient.systems.hud.screens.HudEditorScreen;
/*    */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*    */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ public class HudTab
/*    */   extends Tab
/*    */ {
/*    */   public HudTab() {
/* 27 */     super("HUD");
/*    */   }
/*    */ 
/*    */   
/*    */   public TabScreen createScreen(GuiTheme theme) {
/* 32 */     return (TabScreen)new HudScreen(theme, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isScreen(class_437 screen) {
/* 37 */     return screen instanceof HudScreen;
/*    */   }
/*    */   
/*    */   public static class HudScreen extends WindowTabScreen {
/*    */     private WContainer settingsContainer;
/*    */     private final Hud hud;
/*    */     
/*    */     public HudScreen(GuiTheme theme, Tab tab) {
/* 45 */       super(theme, tab);
/*    */       
/* 47 */       this.hud = Hud.get();
/* 48 */       this.hud.settings.onActivated();
/*    */     }
/*    */ 
/*    */     
/*    */     public void initWidgets() {
/* 53 */       this.settingsContainer = (WContainer)add((WWidget)this.theme.verticalList()).expandX().widget();
/* 54 */       this.settingsContainer.add(this.theme.settings(this.hud.settings)).expandX().widget();
/*    */       
/* 56 */       add((WWidget)this.theme.horizontalSeparator()).expandX();
/*    */       
/* 58 */       WButton openEditor = (WButton)add((WWidget)this.theme.button("Edit")).expandX().widget();
/* 59 */       openEditor.action = (() -> MeteorClient.mc.method_1507((class_437)new HudEditorScreen(this.theme)));
/*    */       
/* 61 */       WHorizontalList buttons = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/* 62 */       Objects.requireNonNull(this.hud); ((WConfirmedButton)buttons.add((WWidget)this.theme.confirmedButton("Clear", "Confirm")).expandX().widget()).action = this.hud::clear;
/* 63 */       Objects.requireNonNull(this.hud); ((WConfirmedButton)buttons.add((WWidget)this.theme.confirmedButton("Reset to default elements", "Confirm")).expandX().widget()).action = this.hud::resetToDefaultElements;
/*    */       
/* 65 */       add((WWidget)this.theme.horizontalSeparator()).expandX();
/*    */       
/* 67 */       WHorizontalList bottom = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*    */       
/* 69 */       bottom.add((WWidget)this.theme.label("Active: "));
/* 70 */       WCheckbox active = (WCheckbox)bottom.add((WWidget)this.theme.checkbox(this.hud.active)).expandCellX().widget();
/* 71 */       active.action = (() -> this.hud.active = active.checked);
/*    */       
/* 73 */       WButton resetSettings = (WButton)bottom.add((WWidget)this.theme.button(GuiRenderer.RESET)).widget();
/* 74 */       Objects.requireNonNull(this.hud.settings); resetSettings.action = this.hud.settings::reset;
/* 75 */       resetSettings.tooltip = "Reset";
/*    */     }
/*    */ 
/*    */     
/*    */     protected void onRenderBefore(class_332 drawContext, float delta) {
/* 80 */       HudEditorScreen.renderElements(drawContext);
/*    */     }
/*    */ 
/*    */     
/*    */     public void method_25393() {
/* 85 */       super.method_25393();
/*    */       
/* 87 */       this.hud.settings.tick(this.settingsContainer, this.theme);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean toClipboard() {
/* 92 */       return NbtUtils.toClipboard((ISerializable)this.hud);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean fromClipboard() {
/* 97 */       return NbtUtils.fromClipboard((ISerializable)this.hud);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\HudTab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */