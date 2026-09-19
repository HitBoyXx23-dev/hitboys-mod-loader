/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.input;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WSlider;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ public class WMeteorSlider
/*    */   extends WSlider
/*    */   implements MeteorWidget
/*    */ {
/*    */   public WMeteorSlider(double value, double min, double max) {
/* 15 */     super(value, min, max);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 20 */     double valueWidth = valueWidth();
/*    */     
/* 22 */     renderBar(renderer, valueWidth);
/* 23 */     renderHandle(renderer, valueWidth);
/*    */   }
/*    */   
/*    */   private void renderBar(GuiRenderer renderer, double valueWidth) {
/* 27 */     MeteorGuiTheme theme = theme();
/*    */     
/* 29 */     double s = theme.scale(3.0D);
/* 30 */     double handleSize = handleSize();
/*    */     
/* 32 */     double x = this.x + handleSize / 2.0D;
/* 33 */     double y = this.y + this.height / 2.0D - s / 2.0D;
/*    */     
/* 35 */     renderer.quad(x, y, valueWidth, s, (Color)theme.sliderLeft.get());
/* 36 */     renderer.quad(x + valueWidth, y, this.width - valueWidth - handleSize, s, (Color)theme.sliderRight.get());
/*    */   }
/*    */   
/*    */   private void renderHandle(GuiRenderer renderer, double valueWidth) {
/* 40 */     MeteorGuiTheme theme = theme();
/* 41 */     double s = handleSize();
/*    */     
/* 43 */     renderer.quad(this.x + valueWidth, this.y, s, s, GuiRenderer.CIRCLE, (Color)theme.sliderHandle.get(this.dragging, this.handleMouseOver));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\input\WMeteorSlider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */