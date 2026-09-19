/*    */ package meteordevelopment.meteorclient.gui.tabs;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WidgetScreen;
/*    */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TabScreen
/*    */   extends WidgetScreen
/*    */ {
/*    */   public final Tab tab;
/*    */   
/*    */   public TabScreen(GuiTheme theme, Tab tab) {
/* 17 */     super(theme, tab.name);
/*    */     
/* 19 */     this.tab = tab;
/*    */   }
/*    */   
/*    */   public <T extends WWidget> Cell<T> addDirect(T widget) {
/* 23 */     return add((WWidget)widget);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\TabScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */