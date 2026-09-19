/*     */ package meteordevelopment.meteorclient.gui.screens.settings.base;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WPressable;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CollectionListSettingScreen<T>
/*     */   extends WindowScreen
/*     */ {
/*     */   protected final Setting<?> setting;
/*     */   protected final Collection<T> collection;
/*     */   private final Iterable<T> registry;
/*     */   private WTable table;
/*  28 */   private String filterText = "";
/*     */   
/*     */   public CollectionListSettingScreen(GuiTheme theme, String title, Setting<?> setting, Collection<T> collection, Iterable<T> registry) {
/*  31 */     super(theme, title);
/*     */     
/*  33 */     this.registry = registry;
/*  34 */     this.setting = setting;
/*  35 */     this.collection = collection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/*  41 */     WTextBox filter = (WTextBox)add((WWidget)this.theme.textBox("")).minWidth(400.0D).expandX().widget();
/*  42 */     filter.setFocused(true);
/*  43 */     filter.action = (() -> {
/*     */         this.filterText = filter.get().trim();
/*     */         
/*     */         this.table.clear();
/*     */         
/*     */         initTable();
/*     */       });
/*  50 */     this.table = (WTable)add((WWidget)this.theme.table()).expandX().widget();
/*     */     
/*  52 */     initTable();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initTable() {
/*  57 */     WTable left = abc(this.registry, true, t -> {
/*     */           addValue((T)t);
/*     */           T v = getAdditionalValue((T)t);
/*     */           if (v != null) {
/*     */             addValue(v);
/*     */           }
/*     */         });
/*  64 */     if (((Boolean)(Config.get()).syncListSettingWidths.get()).booleanValue() || !left.cells.isEmpty()) {
/*  65 */       this.table.add((WWidget)this.theme.verticalSeparator()).expandWidgetY();
/*     */     }
/*     */ 
/*     */     
/*  69 */     WTable right = abc(this.collection, false, t -> {
/*     */           removeValue((T)t);
/*     */           T v = getAdditionalValue((T)t);
/*     */           if (v != null) {
/*     */             removeValue(v);
/*     */           }
/*     */         });
/*  76 */     postWidgets(left, right);
/*     */   }
/*     */ 
/*     */   
/*     */   private WTable abc(Iterable<T> iterable, boolean isLeft, Consumer<T> buttonAction) {
/*  81 */     Cell<WTable> cell = this.table.add((WWidget)this.theme.table()).top();
/*  82 */     if (((Boolean)(Config.get()).syncListSettingWidths.get()).booleanValue()) cell.group("sync-width"); 
/*  83 */     WTable table = (WTable)cell.widget();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     Predicate<T> predicate = isLeft ? (value -> (includeValue((T)value) && !this.collection.contains(value))) : this::includeValue;
/*     */     
/*  90 */     Iterable<T> sorted = SortingHelper.sort(iterable, predicate, this::getValueNames, this.filterText);
/*     */     
/*  92 */     sorted.forEach(t -> {
/*     */           table.add(getValueWidget((T)t));
/*     */           
/*     */           WPressable button = (WPressable)table.add(isLeft ? (WWidget)this.theme.plus() : (WWidget)this.theme.minus()).expandCellX().right().widget();
/*     */           
/*     */           button.action = (());
/*     */           
/*     */           table.row();
/*     */         });
/* 101 */     if (!table.cells.isEmpty()) cell.expandX();
/*     */     
/* 103 */     return table;
/*     */   }
/*     */   
/*     */   protected void invalidateTable() {
/* 107 */     this.table.clear();
/* 108 */     initTable();
/*     */   }
/*     */   
/*     */   protected void addValue(T value) {
/* 112 */     if (!this.collection.contains(value)) {
/* 113 */       this.collection.add(value);
/* 114 */       this.setting.onChanged();
/* 115 */       invalidateTable();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void removeValue(T value) {
/* 120 */     if (this.collection.remove(value)) {
/* 121 */       this.setting.onChanged();
/* 122 */       invalidateTable();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void postWidgets(WTable left, WTable right) {}
/*     */   
/*     */   protected boolean includeValue(T value) {
/* 129 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected T getAdditionalValue(T value) {
/* 137 */     return null;
/*     */   }
/*     */   
/*     */   protected abstract WWidget getValueWidget(T paramT);
/*     */   
/*     */   protected abstract String[] getValueNames(T paramT);
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\settings\base\CollectionListSettingScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */