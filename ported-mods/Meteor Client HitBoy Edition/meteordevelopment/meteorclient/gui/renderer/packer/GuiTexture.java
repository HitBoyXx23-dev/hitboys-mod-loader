/*    */ package meteordevelopment.meteorclient.gui.renderer.packer;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiTexture
/*    */ {
/* 12 */   private final List<TextureRegion> regions = new ArrayList<>(2);
/*    */   
/*    */   void add(TextureRegion region) {
/* 15 */     this.regions.add(region);
/*    */   }
/*    */   
/*    */   public TextureRegion get(double width, double height) {
/* 19 */     double targetDiagonal = Math.sqrt(width * width + height * height);
/*    */     
/* 21 */     double closestDifference = Double.MAX_VALUE;
/* 22 */     TextureRegion closestRegion = null;
/*    */     
/* 24 */     for (TextureRegion region : this.regions) {
/* 25 */       double difference = Math.abs(targetDiagonal - region.diagonal);
/*    */       
/* 27 */       if (difference < closestDifference) {
/* 28 */         closestDifference = difference;
/* 29 */         closestRegion = region;
/*    */       } 
/*    */     } 
/*    */     
/* 33 */     return closestRegion;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\renderer\packer\GuiTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */