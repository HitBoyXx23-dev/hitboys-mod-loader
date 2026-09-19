/*    */ package meteordevelopment.meteorclient.gui.widgets.pressable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WPlus
/*    */   extends WPressable
/*    */ {
/*    */   protected void onCalculateSize() {
/* 11 */     double pad = pad();
/* 12 */     double s = this.theme.textHeight();
/*    */     
/* 14 */     this.width = pad + s + pad;
/* 15 */     this.height = pad + s + pad;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\pressable\WPlus.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */