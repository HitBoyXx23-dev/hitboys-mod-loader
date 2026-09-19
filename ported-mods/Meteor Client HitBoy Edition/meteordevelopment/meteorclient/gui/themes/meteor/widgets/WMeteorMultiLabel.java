/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WMultiLabel;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WMeteorMultiLabel
/*    */   extends WMultiLabel
/*    */   implements MeteorWidget
/*    */ {
/*    */   public WMeteorMultiLabel(String text, boolean title, double maxWidth) {
/* 15 */     super(text, title, maxWidth);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 20 */     double h = this.theme.textHeight(this.title);
/* 21 */     Color defaultColor = (Color)(theme()).textColor.get();
/*    */     
/* 23 */     for (int i = 0; i < this.lines.size(); i++)
/* 24 */       renderer.text(this.lines.get(i), this.x, this.y + h * i, (this.color != null) ? this.color : defaultColor, false); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\WMeteorMultiLabel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */