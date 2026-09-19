/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WTopBar;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WMeteorTopBar
/*    */   extends WTopBar
/*    */   implements MeteorWidget
/*    */ {
/*    */   protected Color getButtonColor(boolean pressed, boolean hovered) {
/* 15 */     return (Color)(theme()).backgroundColor.get(pressed, hovered);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Color getNameColor() {
/* 20 */     return (Color)(theme()).textColor.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\WMeteorTopBar.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */