/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.input;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WDropdown;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class WRoot
/*    */   extends WDropdown.WDropdownRoot
/*    */   implements MeteorWidget
/*    */ {
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 47 */     MeteorGuiTheme theme = theme();
/* 48 */     double s = theme.scale(2.0D);
/* 49 */     SettingColor settingColor = theme.outlineColor.get();
/*    */     
/* 51 */     renderer.quad(this.x, this.y + this.height - s, this.width, s, (Color)settingColor);
/* 52 */     renderer.quad(this.x, this.y, s, this.height - s, (Color)settingColor);
/* 53 */     renderer.quad(this.x + this.width - s, this.y, s, this.height - s, (Color)settingColor);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\input\WMeteorDropdown$WRoot.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */