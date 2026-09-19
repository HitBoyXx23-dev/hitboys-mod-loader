/*    */ package meteordevelopment.meteorclient.gui.screens;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.marker.BaseMarker;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MarkerScreen
/*    */   extends WindowScreen
/*    */ {
/*    */   private final BaseMarker marker;
/*    */   private WContainer settingsContainer;
/*    */   
/*    */   public MarkerScreen(GuiTheme theme, BaseMarker marker) {
/* 20 */     super(theme, (String)marker.name.get());
/*    */     
/* 22 */     this.marker = marker;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 28 */     if (!this.marker.settings.groups.isEmpty()) {
/* 29 */       this.settingsContainer = (WContainer)add((WWidget)this.theme.verticalList()).expandX().widget();
/* 30 */       this.settingsContainer.add(this.theme.settings(this.marker.settings)).expandX();
/*    */     } 
/*    */ 
/*    */     
/* 34 */     WWidget widget = getWidget(this.theme);
/*    */     
/* 36 */     if (widget != null) {
/* 37 */       add((WWidget)this.theme.horizontalSeparator()).expandX();
/* 38 */       Cell<WWidget> cell = add(widget);
/* 39 */       if (widget instanceof WContainer) cell.expandX();
/*    */     
/*    */     } 
/*    */   }
/*    */   
/*    */   public void method_25393() {
/* 45 */     super.method_25393();
/*    */     
/* 47 */     this.marker.settings.tick(this.settingsContainer, this.theme);
/*    */   }
/*    */   
/*    */   public WWidget getWidget(GuiTheme theme) {
/* 51 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\MarkerScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */