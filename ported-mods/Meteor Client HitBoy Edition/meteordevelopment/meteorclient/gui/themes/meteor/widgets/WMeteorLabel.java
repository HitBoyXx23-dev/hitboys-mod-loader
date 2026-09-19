/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ public class WMeteorLabel
/*    */   extends WLabel
/*    */   implements MeteorWidget
/*    */ {
/*    */   public WMeteorLabel(String text, boolean title) {
/* 14 */     super(text, title);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 19 */     if (!this.text.isEmpty())
/* 20 */       renderer.text(this.text, this.x, this.y, (this.color != null) ? this.color : (this.title ? (Color)(theme()).titleTextColor.get() : (Color)(theme()).textColor.get()), this.title); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\WMeteorLabel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */