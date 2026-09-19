/*    */ package meteordevelopment.meteorclient.gui.renderer.operations;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderOperation;
/*    */ import meteordevelopment.meteorclient.renderer.text.TextRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextOperation
/*    */   extends GuiRenderOperation<TextOperation>
/*    */ {
/*    */   private String text;
/*    */   private TextRenderer renderer;
/*    */   public boolean title;
/*    */   
/*    */   public TextOperation set(String text, TextRenderer renderer, boolean title) {
/* 18 */     this.text = text;
/* 19 */     this.renderer = renderer;
/* 20 */     this.title = title;
/*    */     
/* 22 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRun() {
/* 27 */     this.renderer.render(this.text, this.x, this.y, this.color);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\renderer\operations\TextOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */