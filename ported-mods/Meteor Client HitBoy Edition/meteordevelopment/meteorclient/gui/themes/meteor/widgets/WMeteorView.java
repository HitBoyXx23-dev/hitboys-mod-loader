/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WView;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WMeteorView
/*    */   extends WView
/*    */   implements MeteorWidget
/*    */ {
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 15 */     if (this.canScroll && this.hasScrollBar)
/* 16 */       renderer.quad(handleX(), handleY(), handleWidth(), handleHeight(), (Color)(theme()).scrollbarColor.get(this.focused, this.handleMouseOver)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\WMeteorView.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */