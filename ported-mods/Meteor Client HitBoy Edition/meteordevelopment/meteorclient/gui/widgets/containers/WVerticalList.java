/*    */ package meteordevelopment.meteorclient.gui.widgets.containers;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WVerticalList
/*    */   extends WContainer
/*    */ {
/* 11 */   public double spacing = 3.0D;
/*    */   
/*    */   protected double widthRemove;
/*    */   
/*    */   protected double spacing() {
/* 16 */     return this.theme.scale(this.spacing);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 21 */     this.width = 0.0D;
/* 22 */     this.height = 0.0D;
/*    */     
/* 24 */     for (int i = 0; i < this.cells.size(); i++) {
/* 25 */       Cell<?> cell = this.cells.get(i);
/*    */       
/* 27 */       if (i > 0) this.height += spacing();
/*    */       
/* 29 */       this.width = Math.max(this.width, cell.padLeft() + (cell.widget()).width + cell.padRight());
/* 30 */       this.height += cell.padTop() + (cell.widget()).height + cell.padBottom();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateWidgetPositions() {
/* 36 */     double y = this.y;
/*    */     
/* 38 */     for (int i = 0; i < this.cells.size(); i++) {
/* 39 */       Cell<?> cell = this.cells.get(i);
/*    */       
/* 41 */       if (i > 0) y += spacing(); 
/* 42 */       y += cell.padTop();
/*    */       
/* 44 */       cell.x = this.x + cell.padLeft();
/* 45 */       cell.y = y;
/*    */       
/* 47 */       cell.width = this.width - this.widthRemove - cell.padLeft() - cell.padRight();
/* 48 */       cell.height = (cell.widget()).height;
/*    */       
/* 50 */       cell.alignWidget();
/*    */       
/* 52 */       y += cell.height + cell.padBottom();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\containers\WVerticalList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */