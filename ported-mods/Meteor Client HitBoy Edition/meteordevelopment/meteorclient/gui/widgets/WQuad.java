/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WQuad
/*    */   extends WWidget
/*    */ {
/*    */   public Color color;
/*    */   
/*    */   public WQuad(Color color) {
/* 14 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 19 */     double s = this.theme.scale(32.0D);
/*    */     
/* 21 */     this.width = s;
/* 22 */     this.height = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WQuad.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */