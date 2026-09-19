/*    */ package meteordevelopment.meteorclient.gui.widgets.pressable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WCheckbox
/*    */   extends WPressable
/*    */ {
/*    */   public boolean checked;
/*    */   
/*    */   public WCheckbox(boolean checked) {
/* 12 */     this.checked = checked;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 17 */     double pad = pad();
/* 18 */     double s = this.theme.textHeight();
/*    */     
/* 20 */     this.width = pad + s + pad;
/* 21 */     this.height = pad + s + pad;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onPressed(int button) {
/* 26 */     this.checked = !this.checked;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\pressable\WCheckbox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */