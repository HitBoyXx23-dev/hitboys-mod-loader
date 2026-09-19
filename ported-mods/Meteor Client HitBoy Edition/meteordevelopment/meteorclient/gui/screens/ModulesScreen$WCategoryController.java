/*     */ package meteordevelopment.meteorclient.gui.screens;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WWindow;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.modules.Category;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
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
/*     */ public class WCategoryController
/*     */   extends WContainer
/*     */ {
/* 228 */   public final List<WWindow> windows = new ArrayList<>();
/*     */   
/*     */   private Cell<WWindow> favorites;
/*     */   
/*     */   public void init() {
/* 233 */     List<Module> moduleList = new ArrayList<>();
/* 234 */     for (Category category : Modules.loopCategories()) {
/* 235 */       for (Module module : Modules.get().getGroup(category)) {
/* 236 */         if (!((List)(Config.get()).hiddenModules.get()).contains(module)) {
/* 237 */           moduleList.add(module);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 242 */       if (!moduleList.isEmpty()) {
/* 243 */         this.windows.add(ModulesScreen.this.createCategory(this, category, moduleList));
/* 244 */         moduleList.clear();
/*     */       } 
/*     */     } 
/*     */     
/* 248 */     this.windows.add(ModulesScreen.this.createSearch(this));
/*     */     
/* 250 */     refresh();
/*     */   }
/*     */   
/*     */   protected void refresh() {
/* 254 */     if (this.favorites == null) {
/* 255 */       this.favorites = ModulesScreen.this.createFavorites(this);
/* 256 */       if (this.favorites != null) this.windows.add((WWindow)this.favorites.widget()); 
/*     */     } else {
/* 258 */       ((WWindow)this.favorites.widget()).clear();
/*     */       
/* 260 */       if (!ModulesScreen.this.createFavoritesW((WWindow)this.favorites.widget())) {
/* 261 */         remove(this.favorites);
/* 262 */         this.windows.remove(this.favorites.widget());
/* 263 */         this.favorites = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateWidgetPositions() {
/* 270 */     double pad = this.theme.scale(4.0D);
/* 271 */     double h = this.theme.scale(40.0D);
/*     */     
/* 273 */     double x = this.x + pad;
/* 274 */     double y = this.y;
/*     */     
/* 276 */     for (Cell<?> cell : (Iterable<Cell<?>>)this.cells) {
/* 277 */       double windowWidth = Utils.getWindowWidth();
/* 278 */       double windowHeight = Utils.getWindowHeight();
/*     */       
/* 280 */       if (x + cell.width > windowWidth) {
/* 281 */         x += pad;
/* 282 */         y += h;
/*     */       } 
/*     */       
/* 285 */       if (x > windowWidth) {
/* 286 */         x = windowWidth / 2.0D - cell.width / 2.0D;
/* 287 */         if (x < 0.0D) x = 0.0D; 
/*     */       } 
/* 289 */       if (y > windowHeight) {
/* 290 */         y = windowHeight / 2.0D - cell.height / 2.0D;
/* 291 */         if (y < 0.0D) y = 0.0D;
/*     */       
/*     */       } 
/* 294 */       cell.x = x;
/* 295 */       cell.y = y;
/*     */       
/* 297 */       cell.width = (cell.widget()).width;
/* 298 */       cell.height = (cell.widget()).height;
/*     */       
/* 300 */       cell.alignWidget();
/*     */       
/* 302 */       x += cell.width + pad;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\ModulesScreen$WCategoryController.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */