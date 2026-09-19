/*    */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.pathing.PathManagers;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PathManagerScreen
/*    */   extends WindowTabScreen
/*    */ {
/*    */   public PathManagerScreen(GuiTheme theme, Tab tab) {
/* 33 */     super(theme, tab);
/*    */     
/* 35 */     PathManagers.get().getSettings().get().onActivated();
/*    */   }
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 40 */     WTextBox filter = (WTextBox)add((WWidget)this.theme.textBox("")).minWidth(400.0D).expandX().widget();
/* 41 */     filter.setFocused(true);
/* 42 */     filter.action = (() -> {
/*    */         clear();
/*    */         
/*    */         add((WWidget)filter);
/*    */         
/*    */         add(this.theme.settings(PathManagers.get().getSettings().get(), filter.get().trim())).expandX();
/*    */       });
/* 49 */     add(this.theme.settings(PathManagers.get().getSettings().get(), filter.get().trim())).expandX();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onClosed() {
/* 54 */     PathManagers.get().getSettings().save();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\PathManagerTab$PathManagerScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */