/*     */ package meteordevelopment.meteorclient.gui.screens;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*     */ import meteordevelopment.meteorclient.gui.tabs.Tabs;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WWindow;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.modules.Category;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*     */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_3545;
/*     */ import net.minecraft.class_6417;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModulesScreen
/*     */   extends TabScreen
/*     */ {
/*     */   private WCategoryController controller;
/*     */   private WWindow searchWindow;
/*     */   private WTextBox searchTextBox;
/*     */   
/*     */   public ModulesScreen(GuiTheme theme) {
/*  41 */     super(theme, Tabs.get().getFirst());
/*     */   }
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/*  46 */     this.controller = (WCategoryController)add((WWidget)new WCategoryController()).widget();
/*     */ 
/*     */     
/*  49 */     WVerticalList help = (WVerticalList)add((WWidget)this.theme.verticalList()).pad(4.0D).bottom().widget();
/*  50 */     help.add((WWidget)this.theme.label("Left click - Toggle module"));
/*  51 */     help.add((WWidget)this.theme.label("Right click - Open module settings"));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void method_25426() {
/*  56 */     super.method_25426();
/*  57 */     this.controller.refresh();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WWindow createCategory(WContainer c, Category category, List<Module> moduleList) {
/*  63 */     WWindow w = this.theme.window(category.name);
/*  64 */     w.id = category.name;
/*  65 */     w.padding = 0.0D;
/*  66 */     w.spacing = 0.0D;
/*     */     
/*  68 */     if (this.theme.categoryIcons()) {
/*  69 */       w.beforeHeaderInit = (wContainer -> wContainer.add((WWidget)this.theme.item(category.icon)).pad(2.0D));
/*     */     }
/*     */     
/*  72 */     c.add((WWidget)w);
/*  73 */     w.view.scrollOnlyWhenMouseOver = true;
/*  74 */     w.view.hasScrollBar = false;
/*  75 */     w.view.spacing = 0.0D;
/*     */     
/*  77 */     for (Module module : moduleList) {
/*  78 */       w.add(this.theme.module(module)).expandX();
/*     */     }
/*     */     
/*  81 */     return w;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createSearchW(WContainer w, String text) {
/*  87 */     if (!text.isEmpty()) {
/*     */       
/*  89 */       List<class_3545<Module, String>> modules = Modules.get().searchTitles(text);
/*     */       
/*  91 */       if (!modules.isEmpty()) {
/*  92 */         WSection section = (WSection)w.add((WWidget)this.theme.section("Modules")).expandX().widget();
/*  93 */         section.spacing = 0.0D;
/*     */         
/*  95 */         int count = 0;
/*  96 */         for (class_3545<Module, String> p : modules) {
/*  97 */           if (count >= ((Integer)(Config.get()).moduleSearchCount.get()).intValue() || count >= modules.size())
/*  98 */             break;  section.add(this.theme.module((Module)p.method_15442(), (String)p.method_15441())).expandX();
/*  99 */           count++;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 104 */       Set<Module> settings = Modules.get().searchSettingTitles(text);
/*     */       
/* 106 */       if (!settings.isEmpty()) {
/* 107 */         WSection section = (WSection)w.add((WWidget)this.theme.section("Settings")).expandX().widget();
/* 108 */         section.spacing = 0.0D;
/*     */         
/* 110 */         int count = 0;
/* 111 */         for (Module module : settings) {
/* 112 */           if (count >= ((Integer)(Config.get()).moduleSearchCount.get()).intValue() || count >= settings.size())
/* 113 */             break;  section.add(this.theme.module(module)).expandX();
/* 114 */           count++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected WWindow createSearch(WContainer c) {
/* 121 */     WWindow w = this.theme.window("Search");
/* 122 */     w.id = "search";
/* 123 */     this.searchWindow = w;
/*     */     
/* 125 */     if (this.theme.categoryIcons()) {
/* 126 */       w.beforeHeaderInit = (wContainer -> wContainer.add((WWidget)this.theme.item(class_1802.field_8251.method_7854())).pad(2.0D));
/*     */     }
/*     */     
/* 129 */     c.add((WWidget)w);
/* 130 */     w.view.scrollOnlyWhenMouseOver = true;
/* 131 */     w.view.hasScrollBar = false;
/* 132 */     w.view.maxHeight -= 20.0D;
/*     */     
/* 134 */     WVerticalList l = this.theme.verticalList();
/*     */     
/* 136 */     WTextBox text = (WTextBox)w.add((WWidget)this.theme.textBox("")).minWidth(140.0D).expandX().widget();
/* 137 */     text.setFocused(true);
/* 138 */     this.searchTextBox = text;
/* 139 */     text.action = (() -> {
/*     */         l.clear();
/*     */         
/*     */         createSearchW((WContainer)l, text.get());
/*     */       });
/* 144 */     w.add((WWidget)l).expandX();
/* 145 */     createSearchW((WContainer)l, text.get());
/*     */     
/* 147 */     return w;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25404(class_11908 value) {
/* 152 */     if (this.locked) return false;
/*     */     
/* 154 */     boolean cntrl = class_6417.field_52734 ? ((value.comp_4797() == 8)) : ((value.comp_4797() == 2));
/*     */     
/* 156 */     if (cntrl && value.comp_4795() == 70) {
/* 157 */       if (this.searchWindow != null) this.searchWindow.setExpanded(true); 
/* 158 */       if (this.searchTextBox != null) {
/* 159 */         this.searchTextBox.setFocused(true);
/* 160 */         this.searchTextBox.setCursorMax();
/*     */       } 
/*     */       
/* 163 */       return true;
/*     */     } 
/*     */     
/* 166 */     return super.method_25404(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Cell<WWindow> createFavorites(WContainer c) {
/* 172 */     boolean hasFavorites = Modules.get().getAll().stream().anyMatch(module -> module.favorite);
/* 173 */     if (!hasFavorites) return null;
/*     */     
/* 175 */     WWindow w = this.theme.window("Favorites");
/* 176 */     w.id = "favorites";
/* 177 */     w.padding = 0.0D;
/* 178 */     w.spacing = 0.0D;
/*     */     
/* 180 */     if (this.theme.categoryIcons()) {
/* 181 */       w.beforeHeaderInit = (wContainer -> wContainer.add((WWidget)this.theme.item(class_1802.field_8137.method_7854())).pad(2.0D));
/*     */     }
/*     */     
/* 184 */     Cell<WWindow> cell = c.add((WWidget)w);
/* 185 */     w.view.scrollOnlyWhenMouseOver = true;
/* 186 */     w.view.hasScrollBar = false;
/* 187 */     w.view.spacing = 0.0D;
/*     */     
/* 189 */     createFavoritesW(w);
/* 190 */     return cell;
/*     */   }
/*     */   
/*     */   protected boolean createFavoritesW(WWindow w) {
/* 194 */     List<Module> modules = new ArrayList<>();
/*     */     
/* 196 */     for (Module module : Modules.get().getAll()) {
/* 197 */       if (module.favorite) {
/* 198 */         modules.add(module);
/*     */       }
/*     */     } 
/*     */     
/* 202 */     modules.sort((o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.name, o2.name));
/*     */     
/* 204 */     for (Module module : modules) {
/* 205 */       w.add(this.theme.module(module)).expandX();
/*     */     }
/*     */     
/* 208 */     return !modules.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean toClipboard() {
/* 213 */     return NbtUtils.toClipboard((ISerializable)Modules.get());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fromClipboard() {
/* 218 */     return NbtUtils.fromClipboard((ISerializable)Modules.get());
/*     */   }
/*     */ 
/*     */   
/*     */   public void reload() {}
/*     */ 
/*     */   
/*     */   protected class WCategoryController
/*     */     extends WContainer
/*     */   {
/* 228 */     public final List<WWindow> windows = new ArrayList<>();
/*     */     
/*     */     private Cell<WWindow> favorites;
/*     */     
/*     */     public void init() {
/* 233 */       List<Module> moduleList = new ArrayList<>();
/* 234 */       for (Category category : Modules.loopCategories()) {
/* 235 */         for (Module module : Modules.get().getGroup(category)) {
/* 236 */           if (!((List)(Config.get()).hiddenModules.get()).contains(module)) {
/* 237 */             moduleList.add(module);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 242 */         if (!moduleList.isEmpty()) {
/* 243 */           this.windows.add(ModulesScreen.this.createCategory(this, category, moduleList));
/* 244 */           moduleList.clear();
/*     */         } 
/*     */       } 
/*     */       
/* 248 */       this.windows.add(ModulesScreen.this.createSearch(this));
/*     */       
/* 250 */       refresh();
/*     */     }
/*     */     
/*     */     protected void refresh() {
/* 254 */       if (this.favorites == null) {
/* 255 */         this.favorites = ModulesScreen.this.createFavorites(this);
/* 256 */         if (this.favorites != null) this.windows.add((WWindow)this.favorites.widget()); 
/*     */       } else {
/* 258 */         ((WWindow)this.favorites.widget()).clear();
/*     */         
/* 260 */         if (!ModulesScreen.this.createFavoritesW((WWindow)this.favorites.widget())) {
/* 261 */           remove(this.favorites);
/* 262 */           this.windows.remove(this.favorites.widget());
/* 263 */           this.favorites = null;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onCalculateWidgetPositions() {
/* 270 */       double pad = this.theme.scale(4.0D);
/* 271 */       double h = this.theme.scale(40.0D);
/*     */       
/* 273 */       double x = this.x + pad;
/* 274 */       double y = this.y;
/*     */       
/* 276 */       for (Cell<?> cell : (Iterable<Cell<?>>)this.cells) {
/* 277 */         double windowWidth = Utils.getWindowWidth();
/* 278 */         double windowHeight = Utils.getWindowHeight();
/*     */         
/* 280 */         if (x + cell.width > windowWidth) {
/* 281 */           x += pad;
/* 282 */           y += h;
/*     */         } 
/*     */         
/* 285 */         if (x > windowWidth) {
/* 286 */           x = windowWidth / 2.0D - cell.width / 2.0D;
/* 287 */           if (x < 0.0D) x = 0.0D; 
/*     */         } 
/* 289 */         if (y > windowHeight) {
/* 290 */           y = windowHeight / 2.0D - cell.height / 2.0D;
/* 291 */           if (y < 0.0D) y = 0.0D;
/*     */         
/*     */         } 
/* 294 */         cell.x = x;
/* 295 */         cell.y = y;
/*     */         
/* 297 */         cell.width = (cell.widget()).width;
/* 298 */         cell.height = (cell.widget()).height;
/*     */         
/* 300 */         cell.alignWidget();
/*     */         
/* 302 */         x += cell.width + pad;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\ModulesScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */