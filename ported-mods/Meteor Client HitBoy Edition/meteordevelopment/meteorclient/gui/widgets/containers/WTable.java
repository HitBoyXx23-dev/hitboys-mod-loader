/*     */ package meteordevelopment.meteorclient.gui.widgets.containers;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
/*     */ import it.unimi.dsi.fastutil.doubles.DoubleList;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntListIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ 
/*     */ public class WTable
/*     */   extends WContainer
/*     */ {
/*  18 */   public double horizontalSpacing = 3.0D;
/*  19 */   public double verticalSpacing = 3.0D;
/*     */   
/*  21 */   private final List<List<Cell<?>>> rows = new ArrayList<>();
/*     */   
/*     */   private int rowI;
/*  24 */   private final DoubleList rowHeights = (DoubleList)new DoubleArrayList();
/*  25 */   private final DoubleList columnWidths = (DoubleList)new DoubleArrayList();
/*     */   
/*  27 */   private final DoubleList rowWidths = (DoubleList)new DoubleArrayList();
/*  28 */   private final IntList rowExpandCellXCounts = (IntList)new IntArrayList();
/*     */ 
/*     */   
/*     */   public <T extends meteordevelopment.meteorclient.gui.widgets.WWidget> Cell<T> add(T widget) {
/*  32 */     Cell<T> cell = super.add(widget);
/*     */     
/*  34 */     if (this.rows.size() <= this.rowI) {
/*  35 */       List<Cell<?>> row = new ArrayList<>();
/*  36 */       row.add(cell);
/*  37 */       this.rows.add(row);
/*     */     } else {
/*  39 */       ((List<Cell<T>>)this.rows.get(this.rowI)).add(cell);
/*     */     } 
/*  41 */     return cell;
/*     */   }
/*     */   
/*     */   public void row() {
/*  45 */     this.rowI++;
/*     */   }
/*     */   
/*     */   public int rowI() {
/*  49 */     return this.rowI;
/*     */   }
/*     */   
/*     */   public void removeRow(int i) {
/*  53 */     for (Cell<?> cell : this.rows.remove(i)) {
/*  54 */       Iterator<Cell<?>> it; for (it = this.cells.iterator(); it.hasNext();) {
/*  55 */         if (it.next() == cell) {
/*  56 */           it.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  62 */     this.rowI--;
/*     */   }
/*     */   
/*     */   public List<Cell<?>> getRow(int i) {
/*  66 */     if (i < 0 || i >= this.rows.size()) return null; 
/*  67 */     return this.rows.get(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  72 */     super.clear();
/*  73 */     this.rows.clear();
/*  74 */     this.rowI = 0;
/*     */   }
/*     */   
/*     */   protected double horizontalSpacing() {
/*  78 */     return this.theme.scale(this.horizontalSpacing);
/*     */   }
/*     */   
/*     */   protected double verticalSpacing() {
/*  82 */     return this.theme.scale(this.verticalSpacing);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateSize() {
/*  87 */     calculateInfo();
/*     */ 
/*     */     
/*  90 */     this.rowWidths.clear();
/*     */     
/*  92 */     this.width = 0.0D;
/*  93 */     this.height = 0.0D;
/*     */ 
/*     */     
/*  96 */     for (int rowI = 0; rowI < this.rows.size(); rowI++) {
/*  97 */       List<Cell<?>> row = this.rows.get(rowI);
/*     */       
/*  99 */       double rowWidth = 0.0D;
/*     */ 
/*     */       
/* 102 */       for (int cellI = 0; cellI < row.size(); cellI++) {
/*     */         
/* 104 */         if (cellI > 0) rowWidth += horizontalSpacing(); 
/* 105 */         rowWidth += this.columnWidths.getDouble(cellI);
/*     */       } 
/*     */ 
/*     */       
/* 109 */       this.rowWidths.add(rowWidth);
/* 110 */       this.width = Math.max(this.width, rowWidth);
/*     */ 
/*     */       
/* 113 */       if (rowI > 0) this.height += verticalSpacing(); 
/* 114 */       this.height += this.rowHeights.getDouble(rowI);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateWidgetPositions() {
/* 120 */     double y = this.y;
/*     */ 
/*     */     
/* 123 */     for (int rowI = 0; rowI < this.rows.size(); rowI++) {
/* 124 */       List<Cell<?>> row = this.rows.get(rowI);
/*     */       
/* 126 */       if (rowI > 0) y += verticalSpacing();
/*     */       
/* 128 */       double x = this.x;
/* 129 */       double rowHeight = this.rowHeights.getDouble(rowI);
/*     */       
/* 131 */       double expandXAdd = (this.rowExpandCellXCounts.getInt(rowI) > 0) ? ((this.width - this.rowWidths.getDouble(rowI)) / this.rowExpandCellXCounts.getInt(rowI)) : 0.0D;
/*     */ 
/*     */       
/* 134 */       for (int cellI = 0; cellI < row.size(); cellI++) {
/* 135 */         Cell<?> cell = row.get(cellI);
/*     */         
/* 137 */         if (cellI > 0) x += horizontalSpacing(); 
/* 138 */         double columnWidth = this.columnWidths.getDouble(cellI);
/*     */         
/* 140 */         cell.x = x;
/* 141 */         cell.y = y;
/*     */         
/* 143 */         cell.width = columnWidth + (cell.expandCellX ? expandXAdd : 0.0D);
/* 144 */         cell.height = rowHeight;
/*     */         
/* 146 */         cell.alignWidget();
/*     */         
/* 148 */         x += columnWidth + (cell.expandCellX ? expandXAdd : 0.0D);
/*     */       } 
/*     */       
/* 151 */       y += rowHeight;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void calculateInfo() {
/* 157 */     this.rowHeights.clear();
/* 158 */     this.columnWidths.clear();
/* 159 */     this.rowExpandCellXCounts.clear();
/*     */     
/* 161 */     Map<String, IntList> columnGroups = new HashMap<>();
/*     */ 
/*     */     
/* 164 */     for (List<Cell<?>> row : this.rows) {
/* 165 */       double rowHeight = 0.0D;
/* 166 */       int rowExpandXCount = 0;
/*     */ 
/*     */       
/* 169 */       for (int i = 0; i < row.size(); i++) {
/* 170 */         Cell<?> cell = row.get(i);
/*     */ 
/*     */         
/* 173 */         rowHeight = Math.max(rowHeight, cell.padTop() + (cell.widget()).height + cell.padBottom());
/*     */ 
/*     */         
/* 176 */         double cellWidth = cell.padLeft() + (cell.widget()).width + cell.padRight();
/* 177 */         if (this.columnWidths.size() <= i) { this.columnWidths.add(cellWidth); }
/* 178 */         else { this.columnWidths.set(i, Math.max(this.columnWidths.getDouble(i), cellWidth)); }
/*     */         
/* 180 */         if (cell.group != null) ((IntList)columnGroups.computeIfAbsent(cell.group, k -> new IntArrayList())).add(i);
/*     */ 
/*     */         
/* 183 */         if (cell.expandCellX) rowExpandXCount++;
/*     */       
/*     */       } 
/*     */       
/* 187 */       this.rowHeights.add(rowHeight);
/* 188 */       this.rowExpandCellXCounts.add(rowExpandXCount);
/*     */     } 
/*     */ 
/*     */     
/* 192 */     columnGroups.values().forEach(columns -> {
/*     */           double maxWidth = -2.147483648E9D;
/*     */           IntListIterator<Integer> intListIterator = columns.iterator();
/*     */           while (intListIterator.hasNext()) {
/*     */             int i = ((Integer)intListIterator.next()).intValue();
/*     */             maxWidth = Math.max(maxWidth, this.columnWidths.getDouble(i));
/*     */           } 
/*     */           intListIterator = columns.iterator();
/*     */           while (intListIterator.hasNext()) {
/*     */             int i = ((Integer)intListIterator.next()).intValue();
/*     */             this.columnWidths.set(i, maxWidth);
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\containers\WTable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */