/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WHorizontalSeparator;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ public class WMeteorHorizontalSeparator
/*    */   extends WHorizontalSeparator
/*    */   implements MeteorWidget
/*    */ {
/*    */   public WMeteorHorizontalSeparator(String text) {
/* 15 */     super(text);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 20 */     if (this.text == null) { renderWithoutText(renderer); }
/* 21 */     else { renderWithText(renderer); }
/*    */   
/*    */   }
/*    */   private void renderWithoutText(GuiRenderer renderer) {
/* 25 */     MeteorGuiTheme theme = theme();
/* 26 */     double s = theme.scale(1.0D);
/* 27 */     double w = this.width / 2.0D;
/*    */     
/* 29 */     renderer.quad(this.x, this.y + s, w, s, (Color)theme.separatorEdges.get(), (Color)theme.separatorCenter.get());
/* 30 */     renderer.quad(this.x + w, this.y + s, w, s, (Color)theme.separatorCenter.get(), (Color)theme.separatorEdges.get());
/*    */   }
/*    */   
/*    */   private void renderWithText(GuiRenderer renderer) {
/* 34 */     MeteorGuiTheme theme = theme();
/* 35 */     double s = theme.scale(2.0D);
/* 36 */     double h = theme.scale(1.0D);
/*    */     
/* 38 */     double textStart = Math.round(this.width / 2.0D - this.textWidth / 2.0D - s);
/* 39 */     double textEnd = s + textStart + this.textWidth + s;
/*    */     
/* 41 */     double offsetY = Math.round(this.height / 2.0D);
/*    */     
/* 43 */     renderer.quad(this.x, this.y + offsetY, textStart, h, (Color)theme.separatorEdges.get(), (Color)theme.separatorCenter.get());
/* 44 */     renderer.text(this.text, this.x + textStart + s, this.y, (Color)theme.separatorText.get(), false);
/* 45 */     renderer.quad(this.x + textEnd, this.y + offsetY, this.width - textEnd, h, (Color)theme.separatorCenter.get(), (Color)theme.separatorEdges.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\WMeteorHorizontalSeparator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */