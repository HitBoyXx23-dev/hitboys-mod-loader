/*    */ package meteordevelopment.meteorclient.gui.screens;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.settings.Settings;
/*    */ 
/*    */ public abstract class EditSystemScreen<T>
/*    */   extends WindowScreen {
/*    */   private WContainer settingsContainer;
/*    */   protected final T value;
/*    */   
/*    */   public EditSystemScreen(GuiTheme theme, T value, Runnable reload) {
/* 16 */     super(theme, (value == null) ? "New" : "Edit");
/*    */     
/* 18 */     this.isNew = (value == null);
/* 19 */     this.value = this.isNew ? create() : value;
/* 20 */     this.reload = reload;
/*    */   }
/*    */   protected final boolean isNew; private final Runnable reload;
/*    */   
/*    */   public void initWidgets() {
/* 25 */     this.settingsContainer = (WContainer)add((WWidget)this.theme.verticalList()).expandX().minWidth(400.0D).widget();
/* 26 */     this.settingsContainer.add(this.theme.settings(getSettings())).expandX();
/*    */     
/* 28 */     add((WWidget)this.theme.horizontalSeparator()).expandX();
/*    */     
/* 30 */     WButton done = (WButton)add((WWidget)this.theme.button(this.isNew ? "Create" : "Save")).expandX().widget();
/* 31 */     done.action = (() -> {
/*    */         if (save())
/*    */           method_25419(); 
/*    */       });
/* 35 */     this.enterAction = done.action;
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_25393() {
/* 40 */     getSettings().tick(this.settingsContainer, this.theme);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onClosed() {
/* 45 */     if (this.reload != null) this.reload.run(); 
/*    */   }
/*    */   
/*    */   public abstract T create();
/*    */   
/*    */   public abstract boolean save();
/*    */   
/*    */   public abstract Settings getSettings();
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\EditSystemScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */