/*    */ package meteordevelopment.meteorclient.gui.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
/*    */ import net.minecraft.class_11909;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WConfirmedButton
/*    */   extends WButton
/*    */ {
/*    */   protected boolean pressedOnce = false;
/*    */   protected String confirmText;
/*    */   
/*    */   public WConfirmedButton(String text, String confirmText, GuiTexture texture) {
/* 17 */     super(text, texture);
/* 18 */     this.confirmText = confirmText;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onMouseClicked(class_11909 click, boolean doubled) {
/* 23 */     boolean pressed = super.onMouseClicked(click, doubled);
/* 24 */     if (!pressed) {
/* 25 */       this.pressedOnce = false;
/* 26 */       invalidate();
/*    */     } 
/* 28 */     return pressed;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onMouseReleased(class_11909 click) {
/* 33 */     if (this.pressed && this.pressedOnce) super.onMouseReleased(click); 
/* 34 */     this.pressedOnce = this.pressed;
/* 35 */     invalidate();
/* 36 */     return this.pressed = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText() {
/* 41 */     return this.pressedOnce ? this.confirmText : this.text;
/*    */   }
/*    */   
/*    */   public void set(String text, String confirmText) {
/* 45 */     set(text);
/* 46 */     this.confirmText = confirmText;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\pressable\WConfirmedButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */