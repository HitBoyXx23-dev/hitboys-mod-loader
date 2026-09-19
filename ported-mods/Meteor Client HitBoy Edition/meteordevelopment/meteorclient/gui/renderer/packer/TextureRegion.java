/*    */ package meteordevelopment.meteorclient.gui.renderer.packer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextureRegion
/*    */ {
/*    */   public double x1;
/*    */   public double y1;
/*    */   public double x2;
/*    */   public double y2;
/*    */   public double diagonal;
/*    */   
/*    */   public TextureRegion(double width, double height) {
/* 15 */     this.diagonal = Math.sqrt(width * width + height * height);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\renderer\packer\TextureRegion.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */