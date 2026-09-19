/*    */ package meteordevelopment.meteorclient.gui.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WFavorite
/*    */   extends WPressable
/*    */ {
/*    */   public boolean checked;
/*    */   
/*    */   public WFavorite(boolean checked) {
/* 15 */     this.checked = checked;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 20 */     double pad = pad();
/* 21 */     double s = this.theme.textHeight();
/*    */     
/* 23 */     this.width = pad + s + pad;
/* 24 */     this.height = pad + s + pad;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onPressed(int button) {
/* 29 */     this.checked = !this.checked;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 34 */     double pad = pad();
/* 35 */     double s = this.theme.textHeight();
/*    */     
/* 37 */     renderer.quad(this.x + pad, this.y + pad, s, s, this.checked ? GuiRenderer.FAVORITE_YES : GuiRenderer.FAVORITE_NO, getColor());
/*    */   }
/*    */   
/*    */   protected abstract Color getColor();
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\pressable\WFavorite.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */