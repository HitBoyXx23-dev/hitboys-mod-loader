/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WMinus;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ public class WMeteorMinus
/*    */   extends WMinus
/*    */   implements MeteorWidget
/*    */ {
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 15 */     double pad = pad();
/* 16 */     double s = this.theme.scale(3.0D);
/*    */     
/* 18 */     renderBackground(renderer, (WWidget)this, this.pressed, this.mouseOver);
/* 19 */     renderer.quad(this.x + pad, this.y + this.height / 2.0D - s / 2.0D, this.width - pad * 2.0D, s, (Color)(theme()).minusColor.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\pressable\WMeteorMinus.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */