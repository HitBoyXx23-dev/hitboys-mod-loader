/*    */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*    */ import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WDropdown;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*    */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ 
/*    */ public class GuiTab
/*    */   extends Tab
/*    */ {
/*    */   public GuiTab() {
/* 24 */     super("GUI");
/*    */   }
/*    */ 
/*    */   
/*    */   public TabScreen createScreen(GuiTheme theme) {
/* 29 */     return (TabScreen)new GuiScreen(theme, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isScreen(class_437 screen) {
/* 34 */     return screen instanceof GuiScreen;
/*    */   }
/*    */   
/*    */   private static class GuiScreen extends WindowTabScreen {
/*    */     public GuiScreen(GuiTheme theme, Tab tab) {
/* 39 */       super(theme, tab);
/*    */       
/* 41 */       theme.settings.onActivated();
/*    */     }
/*    */ 
/*    */     
/*    */     public void initWidgets() {
/* 46 */       WHorizontalList opts = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*    */       
/* 48 */       opts.add((WWidget)this.theme.label("Theme:"));
/* 49 */       WDropdown<String> themeW = (WDropdown<String>)opts.add((WWidget)this.theme.dropdown((Object[])GuiThemes.getNames(), (GuiThemes.get()).name)).widget();
/* 50 */       themeW.action = (() -> {
/*    */           GuiThemes.select((String)themeW.get());
/*    */           
/*    */           MeteorClient.mc.method_1507(null);
/*    */           
/*    */           this.tab.openScreen(GuiThemes.get());
/*    */         });
/* 57 */       WButton resetLayout = (WButton)opts.add((WWidget)this.theme.button("Reset Layout")).expandX().widget();
/* 58 */       Objects.requireNonNull(this.theme); resetLayout.action = this.theme::clearWindowConfigs;
/*    */       
/* 60 */       WButton reset = (WButton)opts.add((WWidget)this.theme.button("Reset Colors")).right().widget();
/* 61 */       reset.action = (() -> {
/*    */           this.theme.settings.reset();
/*    */           
/*    */           MeteorClient.mc.method_1507(null);
/*    */           this.tab.openScreen(GuiThemes.get());
/*    */         });
/* 67 */       WButton copyButton = (WButton)opts.add((WWidget)this.theme.button(GuiRenderer.COPY)).widget();
/* 68 */       copyButton.action = this::toClipboard;
/* 69 */       copyButton.tooltip = "Copy config";
/*    */       
/* 71 */       WButton pasteButton = (WButton)opts.add((WWidget)this.theme.button(GuiRenderer.PASTE)).right().widget();
/* 72 */       pasteButton.action = this::fromClipboard;
/* 73 */       pasteButton.tooltip = "Paste config";
/*    */       
/* 75 */       add(this.theme.settings(this.theme.settings)).expandX();
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean toClipboard() {
/* 80 */       return NbtUtils.toClipboard((ISerializable)this.theme);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean fromClipboard() {
/* 85 */       return NbtUtils.fromClipboard((ISerializable)this.theme);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\GuiTab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */