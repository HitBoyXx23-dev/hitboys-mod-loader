/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WVerticalSeparator;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WMeteorVerticalSeparator
/*    */   extends WVerticalSeparator
/*    */   implements MeteorWidget
/*    */ {
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 17 */     MeteorGuiTheme theme = theme();
/* 18 */     Color colorEdges = (Color)theme.separatorEdges.get();
/* 19 */     Color colorCenter = (Color)theme.separatorCenter.get();
/*    */     
/* 21 */     double s = theme.scale(1.0D);
/* 22 */     double offsetX = Math.round(this.width / 2.0D);
/*    */     
/* 24 */     renderer.quad(this.x + offsetX, this.y, s, this.height / 2.0D, colorEdges, colorEdges, colorCenter, colorCenter);
/* 25 */     renderer.quad(this.x + offsetX, this.y + this.height / 2.0D, s, this.height / 2.0D, colorCenter, colorCenter, colorEdges, colorEdges);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\WMeteorVerticalSeparator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */