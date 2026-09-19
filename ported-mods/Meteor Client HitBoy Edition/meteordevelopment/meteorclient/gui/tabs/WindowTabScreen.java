/*    */ package meteordevelopment.meteorclient.gui.tabs;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WWindow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WindowTabScreen
/*    */   extends TabScreen
/*    */ {
/*    */   protected final WWindow window;
/*    */   
/*    */   public WindowTabScreen(GuiTheme theme, Tab tab) {
/* 17 */     super(theme, tab);
/*    */     
/* 19 */     this.window = (WWindow)super.add((WWidget)theme.window(tab.name)).center().widget();
/*    */   }
/*    */ 
/*    */   
/*    */   public <W extends WWidget> Cell<W> add(W widget) {
/* 24 */     return this.window.add((WWidget)widget);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 29 */     this.window.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\WindowTabScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */