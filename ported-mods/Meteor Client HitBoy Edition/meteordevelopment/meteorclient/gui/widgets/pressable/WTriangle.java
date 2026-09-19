/*    */ package meteordevelopment.meteorclient.gui.widgets.pressable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WTriangle
/*    */   extends WPressable
/*    */ {
/*    */   public double rotation;
/*    */   
/*    */   protected void onCalculateSize() {
/* 13 */     double s = this.theme.textHeight();
/*    */     
/* 15 */     this.width = s;
/* 16 */     this.height = s;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\pressable\WTriangle.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */