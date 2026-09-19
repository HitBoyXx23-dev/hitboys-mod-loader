/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.renderer.Texture;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WTexture
/*    */   extends WWidget
/*    */ {
/*    */   private final double width;
/*    */   private final double height;
/*    */   private final double rotation;
/*    */   private final Texture texture;
/*    */   
/*    */   public WTexture(double width, double height, double rotation, Texture texture) {
/* 17 */     this.width = width;
/* 18 */     this.height = height;
/* 19 */     this.rotation = rotation;
/* 20 */     this.texture = texture;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 25 */     super.width = this.theme.scale(this.width);
/* 26 */     super.height = this.theme.scale(this.height);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 31 */     renderer.texture(this.x, this.y, super.width, super.height, this.rotation, this.texture);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */