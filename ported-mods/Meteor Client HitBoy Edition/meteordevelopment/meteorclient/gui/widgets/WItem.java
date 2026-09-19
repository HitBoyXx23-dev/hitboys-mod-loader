/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import net.minecraft.class_1799;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WItem
/*    */   extends WWidget
/*    */ {
/*    */   protected class_1799 itemStack;
/*    */   
/*    */   public WItem(class_1799 itemStack) {
/* 15 */     this.itemStack = itemStack;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 20 */     double s = this.theme.scale(32.0D);
/*    */     
/* 22 */     this.width = s;
/* 23 */     this.height = s;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 28 */     if (!this.itemStack.method_7960()) {
/* 29 */       renderer.post(() -> {
/*    */             double s = this.theme.scale(2.0D);
/*    */             renderer.item(this.itemStack, (int)this.x, (int)this.y, (float)s, true);
/*    */           });
/*    */     }
/*    */   }
/*    */   
/*    */   public void set(class_1799 itemStack) {
/* 37 */     this.itemStack = itemStack;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WItem.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */