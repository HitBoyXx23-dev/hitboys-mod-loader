/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WQuad;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WMeteorQuad
/*    */   extends WQuad
/*    */ {
/*    */   public WMeteorQuad(Color color) {
/* 14 */     super(color);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 19 */     renderer.quad(this.x, this.y, this.width, this.height, this.color);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\WMeteorQuad.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */