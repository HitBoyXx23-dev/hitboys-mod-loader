/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WTooltip
/*    */   extends WContainer
/*    */   implements WRoot
/*    */ {
/*    */   private boolean valid;
/*    */   protected String text;
/*    */   
/*    */   public WTooltip(String text) {
/* 17 */     this.text = text;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 22 */     add((WWidget)this.theme.label(this.text)).pad(4.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public void invalidate() {
/* 27 */     this.valid = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 32 */     if (!this.valid) {
/* 33 */       calculateSize();
/* 34 */       calculateWidgetPositions();
/*    */       
/* 36 */       this.valid = true;
/*    */     } 
/*    */     
/* 39 */     return super.render(renderer, mouseX, mouseY, delta);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WTooltip.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */