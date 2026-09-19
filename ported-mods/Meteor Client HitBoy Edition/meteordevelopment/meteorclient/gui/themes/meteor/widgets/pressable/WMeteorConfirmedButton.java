/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedButton;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*    */ 
/*    */ 
/*    */ public class WMeteorConfirmedButton
/*    */   extends WConfirmedButton
/*    */   implements MeteorWidget
/*    */ {
/*    */   public WMeteorConfirmedButton(String text, String confirmText, GuiTexture texture) {
/* 18 */     super(text, confirmText, texture);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 23 */     MeteorGuiTheme theme = theme();
/* 24 */     double pad = pad();
/*    */     
/* 26 */     SettingColor settingColor = theme.outlineColor.get(this.pressed, this.mouseOver);
/* 27 */     Color fg = this.pressedOnce ? (Color)theme.backgroundColor.get(this.pressed, this.mouseOver) : (Color)theme.textColor.get();
/* 28 */     Color bg = this.pressedOnce ? (Color)theme.textColor.get() : (Color)theme.backgroundColor.get(this.pressed, this.mouseOver);
/*    */     
/* 30 */     renderBackground(renderer, (WWidget)this, (Color)settingColor, bg);
/*    */     
/* 32 */     String text = getText();
/*    */     
/* 34 */     if (text != null) {
/* 35 */       renderer.text(text, this.x + this.width / 2.0D - this.textWidth / 2.0D, this.y + pad, fg, false);
/*    */     } else {
/*    */       
/* 38 */       double ts = theme.textHeight();
/* 39 */       renderer.quad(this.x + this.width / 2.0D - ts / 2.0D, this.y + pad, ts, ts, this.texture, fg);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\pressable\WMeteorConfirmedButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */