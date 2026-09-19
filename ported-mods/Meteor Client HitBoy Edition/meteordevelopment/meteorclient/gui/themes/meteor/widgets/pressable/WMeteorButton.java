/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ public class WMeteorButton
/*    */   extends WButton
/*    */   implements MeteorWidget
/*    */ {
/*    */   public WMeteorButton(String text, GuiTexture texture) {
/* 16 */     super(text, texture);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 21 */     MeteorGuiTheme theme = theme();
/* 22 */     double pad = pad();
/*    */     
/* 24 */     renderBackground(renderer, (WWidget)this, this.pressed, this.mouseOver);
/*    */     
/* 26 */     if (this.text != null) {
/* 27 */       renderer.text(this.text, this.x + this.width / 2.0D - this.textWidth / 2.0D, this.y + pad, (Color)theme.textColor.get(), false);
/*    */     } else {
/*    */       
/* 30 */       double ts = theme.textHeight();
/* 31 */       renderer.quad(this.x + this.width / 2.0D - ts / 2.0D, this.y + pad, ts, ts, this.texture, (Color)theme.textColor.get());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\pressable\WMeteorButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */