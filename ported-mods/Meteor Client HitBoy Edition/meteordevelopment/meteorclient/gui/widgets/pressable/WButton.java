/*    */ package meteordevelopment.meteorclient.gui.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WButton
/*    */   extends WPressable
/*    */ {
/*    */   protected String text;
/*    */   protected double textWidth;
/*    */   protected GuiTexture texture;
/*    */   
/*    */   public WButton(String text, GuiTexture texture) {
/* 17 */     this.text = text;
/* 18 */     this.texture = texture;
/*    */     
/* 20 */     if (text == null) this.instantTooltips = true;
/*    */   
/*    */   }
/*    */   
/*    */   protected void onCalculateSize() {
/* 25 */     double pad = pad();
/*    */     
/* 27 */     String text = getText();
/*    */     
/* 29 */     if (text != null) {
/* 30 */       this.textWidth = this.theme.textWidth(text);
/*    */       
/* 32 */       this.width = pad + this.textWidth + pad;
/* 33 */       this.height = pad + this.theme.textHeight() + pad;
/*    */     } else {
/*    */       
/* 36 */       double s = this.theme.textHeight();
/*    */       
/* 38 */       this.width = pad + s + pad;
/* 39 */       this.height = pad + s + pad;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void set(String text) {
/* 44 */     if (this.text == null || Math.round(this.theme.textWidth(text)) != this.textWidth) invalidate();
/*    */     
/* 46 */     this.text = text;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 50 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\pressable\WButton.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */