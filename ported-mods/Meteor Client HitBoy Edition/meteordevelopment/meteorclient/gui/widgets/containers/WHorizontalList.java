/*    */ package meteordevelopment.meteorclient.gui.widgets.containers;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WHorizontalList
/*    */   extends WContainer
/*    */ {
/* 11 */   public double spacing = 3.0D;
/*    */   
/*    */   protected double calculatedWidth;
/*    */   protected int fillXCount;
/*    */   
/*    */   protected double spacing() {
/* 17 */     return this.theme.scale(this.spacing);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 22 */     this.width = 0.0D;
/* 23 */     this.height = 0.0D;
/*    */     
/* 25 */     this.fillXCount = 0;
/*    */     
/* 27 */     for (int i = 0; i < this.cells.size(); i++) {
/* 28 */       Cell<?> cell = this.cells.get(i);
/*    */       
/* 30 */       if (i > 0) this.width += spacing();
/*    */       
/* 32 */       this.width += cell.padLeft() + (cell.widget()).width + cell.padRight();
/* 33 */       this.height = Math.max(this.height, cell.padTop() + (cell.widget()).height + cell.padBottom());
/*    */       
/* 35 */       if (cell.expandCellX) this.fillXCount++;
/*    */     
/*    */     } 
/* 38 */     this.calculatedWidth = this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateWidgetPositions() {
/* 43 */     double x = this.x;
/* 44 */     double fillXWidth = (this.width - this.calculatedWidth) / this.fillXCount;
/*    */     
/* 46 */     for (int i = 0; i < this.cells.size(); i++) {
/* 47 */       Cell<?> cell = this.cells.get(i);
/*    */       
/* 49 */       if (i > 0) x += spacing(); 
/* 50 */       x += cell.padLeft();
/*    */       
/* 52 */       cell.x = x;
/* 53 */       cell.y = this.y + cell.padTop();
/*    */       
/* 55 */       cell.width = (cell.widget()).width;
/* 56 */       cell.height = this.height - cell.padTop() - cell.padTop();
/*    */       
/* 58 */       if (cell.expandCellX) cell.width += fillXWidth; 
/* 59 */       cell.alignWidget();
/*    */       
/* 61 */       x += cell.width + cell.padRight();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\containers\WHorizontalList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */