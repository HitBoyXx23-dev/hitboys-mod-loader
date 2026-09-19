/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.input;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.widgets.WMeteorLabel;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class CompletionItem
/*    */   extends WMeteorLabel
/*    */   implements WTextBox.ICompletionItem
/*    */ {
/* 60 */   private static final Color SELECTED_COLOR = new Color(255, 255, 255, 15);
/*    */   
/*    */   private boolean selected;
/*    */   
/*    */   public CompletionItem(String text, boolean title, boolean selected) {
/* 65 */     super(text, title);
/* 66 */     this.selected = selected;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 71 */     super.onRender(renderer, mouseX, mouseY, delta);
/*    */     
/* 73 */     if (this.selected) renderer.quad((WWidget)this, SELECTED_COLOR);
/*    */   
/*    */   }
/*    */   
/*    */   public boolean isSelected() {
/* 78 */     return this.selected;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSelected(boolean selected) {
/* 83 */     this.selected = selected;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCompletion() {
/* 88 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\input\WMeteorTextBox$CompletionItem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */