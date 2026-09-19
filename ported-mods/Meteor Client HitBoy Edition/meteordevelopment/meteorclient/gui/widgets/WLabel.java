/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WPressable;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_11909;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WLabel
/*    */   extends WPressable
/*    */ {
/*    */   public Color color;
/*    */   protected String text;
/*    */   protected boolean title;
/*    */   
/*    */   public WLabel(String text, boolean title) {
/* 19 */     this.text = text;
/* 20 */     this.title = title;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 25 */     this.width = this.theme.textWidth(this.text, this.text.length(), this.title);
/* 26 */     this.height = this.theme.textHeight(this.title);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onMouseClicked(class_11909 click, boolean doubled) {
/* 31 */     if (this.action != null) return super.onMouseClicked(click, doubled); 
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onMouseReleased(class_11909 click) {
/* 37 */     if (this.action != null) return super.onMouseReleased(click); 
/* 38 */     return false;
/*    */   }
/*    */   
/*    */   public void set(String text) {
/* 42 */     if (Math.round(this.theme.textWidth(text, text.length(), this.title)) != this.width) invalidate();
/*    */     
/* 44 */     this.text = text;
/*    */   }
/*    */   
/*    */   public String get() {
/* 48 */     return this.text;
/*    */   }
/*    */   
/*    */   public WLabel color(Color color) {
/* 52 */     this.color = color;
/* 53 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WLabel.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */