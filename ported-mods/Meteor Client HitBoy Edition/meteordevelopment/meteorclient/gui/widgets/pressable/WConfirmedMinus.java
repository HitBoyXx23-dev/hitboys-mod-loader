/*    */ package meteordevelopment.meteorclient.gui.widgets.pressable;
/*    */ 
/*    */ import net.minecraft.class_11909;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WConfirmedMinus
/*    */   extends WMinus
/*    */ {
/*    */   protected boolean pressedOnce = false;
/*    */   
/*    */   public boolean onMouseClicked(class_11909 click, boolean doubled) {
/* 15 */     boolean pressed = super.onMouseClicked(click, doubled);
/* 16 */     if (!pressed) {
/* 17 */       this.pressedOnce = false;
/*    */     }
/* 19 */     return pressed;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onMouseReleased(class_11909 click) {
/* 24 */     if (this.pressed && this.pressedOnce) super.onMouseReleased(click); 
/* 25 */     this.pressedOnce = this.pressed;
/* 26 */     return this.pressed = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\pressable\WConfirmedMinus.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */