/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedMinus;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*    */ 
/*    */ 
/*    */ public class WMeteorConfirmedMinus
/*    */   extends WConfirmedMinus
/*    */   implements MeteorWidget
/*    */ {
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 17 */     MeteorGuiTheme theme = theme();
/* 18 */     double pad = pad();
/* 19 */     double s = theme.scale(3.0D);
/*    */     
/* 21 */     SettingColor settingColor = theme.outlineColor.get(this.pressed, this.mouseOver);
/* 22 */     Color fg = this.pressedOnce ? (Color)theme.backgroundColor.get(this.pressed, this.mouseOver) : (Color)(theme()).minusColor.get();
/* 23 */     Color bg = this.pressedOnce ? (Color)(theme()).minusColor.get() : (Color)theme.backgroundColor.get(this.pressed, this.mouseOver);
/*    */     
/* 25 */     renderBackground(renderer, (WWidget)this, (Color)settingColor, bg);
/* 26 */     renderer.quad(this.x + pad, this.y + this.height / 2.0D - s / 2.0D, this.width - pad * 2.0D, s, fg);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\pressable\WMeteorConfirmedMinus.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */