/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WPlus;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ public class WMeteorPlus
/*    */   extends WPlus
/*    */   implements MeteorWidget
/*    */ {
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 16 */     MeteorGuiTheme theme = theme();
/* 17 */     double pad = pad();
/* 18 */     double s = theme.scale(3.0D);
/*    */     
/* 20 */     renderBackground(renderer, (WWidget)this, this.pressed, this.mouseOver);
/* 21 */     renderer.quad(this.x + pad, this.y + this.height / 2.0D - s / 2.0D, this.width - pad * 2.0D, s, (Color)theme.plusColor.get());
/* 22 */     renderer.quad(this.x + this.width / 2.0D - s / 2.0D, this.y + pad, s, this.height - pad * 2.0D, (Color)theme.plusColor.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\pressable\WMeteorPlus.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */