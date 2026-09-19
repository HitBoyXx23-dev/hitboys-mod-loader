/*     */ package meteordevelopment.meteorclient.gui;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class RemoveInfo
/*     */ {
/*     */   private final WSection section;
/*     */   private final WTable table;
/* 138 */   private final IntList rowIds = (IntList)new IntArrayList();
/*     */   
/*     */   public RemoveInfo(WSection section, WTable table) {
/* 141 */     this.section = section;
/* 142 */     this.table = table;
/*     */   }
/*     */   
/*     */   public void markRowForRemoval() {
/* 146 */     this.rowIds.add(this.table.rowI());
/*     */   }
/*     */   
/*     */   public void remove(WVerticalList list) {
/* 150 */     for (int i = 0; i < this.rowIds.size(); i++) {
/* 151 */       this.table.removeRow(this.rowIds.getInt(i) - i);
/*     */     }
/*     */     
/* 154 */     if (this.table.cells.isEmpty()) list.cells.removeIf(cell -> (cell.widget() == this.section)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\DefaultSettingsWidgetFactory$RemoveInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */