/*    */ package meteordevelopment.meteorclient.gui;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WWindow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WindowScreen
/*    */   extends WidgetScreen
/*    */ {
/*    */   protected final WWindow window;
/*    */   
/*    */   public WindowScreen(GuiTheme theme, WWidget icon, String title) {
/* 16 */     super(theme, title);
/*    */     
/* 18 */     this.window = (WWindow)super.<WWindow>add(theme.window(icon, title)).center().widget();
/* 19 */     this.window.view.scrollOnlyWhenMouseOver = false;
/*    */   }
/*    */   
/*    */   public WindowScreen(GuiTheme theme, String title) {
/* 23 */     this(theme, null, title);
/*    */   }
/*    */ 
/*    */   
/*    */   public <W extends WWidget> Cell<W> add(W widget) {
/* 28 */     return this.window.add((WWidget)widget);
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 33 */     this.window.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\WindowScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */