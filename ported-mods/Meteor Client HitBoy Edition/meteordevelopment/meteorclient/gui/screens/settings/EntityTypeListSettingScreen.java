/*     */ package meteordevelopment.meteorclient.gui.screens.settings;
/*     */ 
/*     */ import com.mojang.blaze3d.textures.FilterMode;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.Consumer;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*     */ import meteordevelopment.meteorclient.renderer.Texture;
/*     */ import meteordevelopment.meteorclient.settings.EntityTypeListSetting;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.Names;
/*     */ import net.minecraft.class_11580;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1311;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_3545;
/*     */ import net.minecraft.class_7923;
/*     */ import net.minecraft.class_9334;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTypeListSettingScreen
/*     */   extends WindowScreen
/*     */ {
/*     */   private static Texture EMPTY_SPAWN_EGG_TEXTURE;
/*     */   private final EntityTypeListSetting setting;
/*     */   private WVerticalList list;
/*     */   private final WTextBox filter;
/*  42 */   private String filterText = ""; private WSection animals; private WSection waterAnimals; private WSection monsters; private WSection ambient; private WSection misc; private WTable animalsT; private WTable waterAnimalsT;
/*     */   private WTable monstersT;
/*     */   private WTable ambientT;
/*     */   private WTable miscT;
/*  46 */   int hasAnimal = 0, hasWaterAnimal = 0, hasMonster = 0, hasAmbient = 0, hasMisc = 0;
/*     */   
/*     */   public EntityTypeListSettingScreen(GuiTheme theme, EntityTypeListSetting setting) {
/*  49 */     super(theme, "Select entities");
/*  50 */     this.setting = setting;
/*     */ 
/*     */     
/*  53 */     this.filter = (WTextBox)super.add((WWidget)theme.textBox("")).minWidth(400.0D).expandX().widget();
/*  54 */     this.filter.setFocused(true);
/*  55 */     this.filter.action = (() -> {
/*     */         this.filterText = this.filter.get().trim();
/*     */         
/*     */         this.list.clear();
/*     */         
/*     */         initWidgets();
/*     */       });
/*  62 */     this.list = (WVerticalList)super.add((WWidget)theme.verticalList()).expandX().widget();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <W extends WWidget> Cell<W> add(W widget) {
/*  68 */     return this.list.add((WWidget)widget);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/*  73 */     this.hasAnimal = this.hasWaterAnimal = this.hasMonster = this.hasAmbient = this.hasMisc = 0;
/*     */     
/*  75 */     for (class_1299<?> entityType : (Iterable<class_1299<?>>)this.setting.get()) {
/*  76 */       if (this.setting.filter == null || this.setting.filter.test(entityType)) {
/*  77 */         switch (entityType.method_5891()) { case field_6294:
/*  78 */             this.hasAnimal++;
/*  79 */           case field_24460: case field_6300: case field_30092: case field_34447: this.hasWaterAnimal++;
/*  80 */           case field_6302: this.hasMonster++;
/*  81 */           case field_6303: this.hasAmbient++;
/*  82 */           case field_17715: this.hasMisc++; }
/*     */ 
/*     */       
/*     */       }
/*     */     } 
/*  87 */     boolean first = (this.animals == null);
/*     */ 
/*     */     
/*  90 */     List<class_1299<?>> animalsE = new ArrayList<>();
/*  91 */     WCheckbox animalsC = this.theme.checkbox((this.hasAnimal > 0));
/*     */     
/*  93 */     this.animals = this.theme.section("Animals", (this.animals != null && this.animals.isExpanded()), (WWidget)animalsC);
/*  94 */     animalsC.action = (() -> tableChecked(animalsE, animalsC.checked));
/*     */     
/*  96 */     Cell<WSection> animalsCell = add(this.animals).expandX();
/*  97 */     this.animalsT = (WTable)this.animals.add((WWidget)this.theme.table()).expandX().widget();
/*     */ 
/*     */     
/* 100 */     List<class_1299<?>> waterAnimalsE = new ArrayList<>();
/* 101 */     WCheckbox waterAnimalsC = this.theme.checkbox((this.hasWaterAnimal > 0));
/*     */     
/* 103 */     this.waterAnimals = this.theme.section("Water Animals", (this.waterAnimals != null && this.waterAnimals.isExpanded()), (WWidget)waterAnimalsC);
/* 104 */     waterAnimalsC.action = (() -> tableChecked(waterAnimalsE, waterAnimalsC.checked));
/*     */     
/* 106 */     Cell<WSection> waterAnimalsCell = add(this.waterAnimals).expandX();
/* 107 */     this.waterAnimalsT = (WTable)this.waterAnimals.add((WWidget)this.theme.table()).expandX().widget();
/*     */ 
/*     */     
/* 110 */     List<class_1299<?>> monstersE = new ArrayList<>();
/* 111 */     WCheckbox monstersC = this.theme.checkbox((this.hasMonster > 0));
/*     */     
/* 113 */     this.monsters = this.theme.section("Monsters", (this.monsters != null && this.monsters.isExpanded()), (WWidget)monstersC);
/* 114 */     monstersC.action = (() -> tableChecked(monstersE, monstersC.checked));
/*     */     
/* 116 */     Cell<WSection> monstersCell = add(this.monsters).expandX();
/* 117 */     this.monstersT = (WTable)this.monsters.add((WWidget)this.theme.table()).expandX().widget();
/*     */ 
/*     */     
/* 120 */     List<class_1299<?>> ambientE = new ArrayList<>();
/* 121 */     WCheckbox ambientC = this.theme.checkbox((this.hasAmbient > 0));
/*     */     
/* 123 */     this.ambient = this.theme.section("Ambient", (this.ambient != null && this.ambient.isExpanded()), (WWidget)ambientC);
/* 124 */     ambientC.action = (() -> tableChecked(ambientE, ambientC.checked));
/*     */     
/* 126 */     Cell<WSection> ambientCell = add(this.ambient).expandX();
/* 127 */     this.ambientT = (WTable)this.ambient.add((WWidget)this.theme.table()).expandX().widget();
/*     */ 
/*     */     
/* 130 */     List<class_1299<?>> miscE = new ArrayList<>();
/* 131 */     WCheckbox miscC = this.theme.checkbox((this.hasMisc > 0));
/*     */     
/* 133 */     this.misc = this.theme.section("Misc", (this.misc != null && this.misc.isExpanded()), (WWidget)miscC);
/* 134 */     miscC.action = (() -> tableChecked(miscE, miscC.checked));
/*     */     
/* 136 */     Cell<WSection> miscCell = add(this.misc).expandX();
/* 137 */     this.miscT = (WTable)this.misc.add((WWidget)this.theme.table()).expandX().widget();
/*     */ 
/*     */ 
/*     */     
/* 141 */     List<class_1792> spawnEggItems = class_7923.field_41178.method_10220().filter(item -> item.method_57347().method_57832(class_9334.field_49609)).toList();
/*     */     
/* 143 */     Consumer<class_1299<?>> entityTypeForEach = entityType -> {
/*     */         if (this.setting.filter == null || this.setting.filter.test(entityType))
/*     */           switch (entityType.method_5891()) {
/*     */             case field_6294:
/*     */               animalsE.add(entityType);
/*     */               addEntityType(this.animalsT, animalsC, entityType, spawnEggItems);
/*     */               break;
/*     */             case field_24460:
/*     */             case field_6300:
/*     */             case field_30092:
/*     */             case field_34447:
/*     */               waterAnimalsE.add(entityType);
/*     */               addEntityType(this.waterAnimalsT, waterAnimalsC, entityType, spawnEggItems);
/*     */               break;
/*     */             case field_6302:
/*     */               monstersE.add(entityType);
/*     */               addEntityType(this.monstersT, monstersC, entityType, spawnEggItems);
/*     */               break;
/*     */             case field_6303:
/*     */               ambientE.add(entityType);
/*     */               addEntityType(this.ambientT, ambientC, entityType, spawnEggItems);
/*     */               break;
/*     */             case field_17715:
/*     */               miscE.add(entityType);
/*     */               addEntityType(this.miscT, miscC, entityType, spawnEggItems);
/*     */               break;
/*     */           }  
/*     */       };
/* 171 */     if (this.filterText.isEmpty()) {
/* 172 */       class_7923.field_41177.forEach(entityTypeForEach);
/*     */     } else {
/* 174 */       List<class_3545<class_1299<?>, Integer>> entities = new ArrayList<>();
/* 175 */       class_7923.field_41177.forEach(entity -> {
/*     */             int words = Utils.searchInWords(Names.get(entity), this.filterText);
/*     */             int diff = Utils.searchLevenshteinDefault(Names.get(entity), this.filterText, false);
/*     */             if (words > 0 || diff < Names.get(entity).length() / 2)
/*     */               entities.add(new class_3545(entity, Integer.valueOf(-diff))); 
/*     */           });
/* 181 */       entities.sort(Comparator.comparingInt(value -> -((Integer)value.method_15441()).intValue()));
/* 182 */       for (class_3545<class_1299<?>, Integer> pair : entities) entityTypeForEach.accept((class_1299)pair.method_15442());
/*     */     
/*     */     } 
/* 185 */     if (this.animalsT.cells.isEmpty()) this.list.cells.remove(animalsCell); 
/* 186 */     if (this.waterAnimalsT.cells.isEmpty()) this.list.cells.remove(waterAnimalsCell); 
/* 187 */     if (this.monstersT.cells.isEmpty()) this.list.cells.remove(monstersCell); 
/* 188 */     if (this.ambientT.cells.isEmpty()) this.list.cells.remove(ambientCell); 
/* 189 */     if (this.miscT.cells.isEmpty()) this.list.cells.remove(miscCell);
/*     */     
/* 191 */     if (first) {
/* 192 */       int totalCount = (this.hasWaterAnimal + this.waterAnimals.cells.size() + this.monsters.cells.size() + this.ambient.cells.size() + this.misc.cells.size()) / 2;
/*     */       
/* 194 */       if (totalCount <= 20) {
/* 195 */         if (!this.animalsT.cells.isEmpty()) this.animals.setExpanded(true); 
/* 196 */         if (!this.waterAnimalsT.cells.isEmpty()) this.waterAnimals.setExpanded(true); 
/* 197 */         if (!this.monstersT.cells.isEmpty()) this.monsters.setExpanded(true); 
/* 198 */         if (!this.ambientT.cells.isEmpty()) this.ambient.setExpanded(true); 
/* 199 */         if (!this.miscT.cells.isEmpty()) this.misc.setExpanded(true);
/*     */       
/*     */       } else {
/* 202 */         if (!this.animalsT.cells.isEmpty()) this.animals.setExpanded(false); 
/* 203 */         if (!this.waterAnimalsT.cells.isEmpty()) this.waterAnimals.setExpanded(false); 
/* 204 */         if (!this.monstersT.cells.isEmpty()) this.monsters.setExpanded(false); 
/* 205 */         if (!this.ambientT.cells.isEmpty()) this.ambient.setExpanded(false); 
/* 206 */         if (!this.miscT.cells.isEmpty()) this.misc.setExpanded(false); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void tableChecked(List<class_1299<?>> entityTypes, boolean checked) {
/* 212 */     boolean changed = false;
/*     */     
/* 214 */     for (class_1299<?> entityType : entityTypes) {
/* 215 */       if (checked) {
/* 216 */         ((Set<class_1299<?>>)this.setting.get()).add(entityType);
/* 217 */         changed = true; continue;
/*     */       } 
/* 219 */       if (((Set)this.setting.get()).remove(entityType)) {
/* 220 */         changed = true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 225 */     if (changed) {
/* 226 */       this.list.clear();
/* 227 */       initWidgets();
/* 228 */       this.setting.onChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEntityType(WTable table, WCheckbox tableCheckbox, class_1299<?> entityType, List<class_1792> spawnEggItems) {
/* 235 */     class_1799 stack = null;
/*     */     
/* 237 */     for (class_1792 item : spawnEggItems) {
/* 238 */       class_11580<class_1299<?>> component = (class_11580<class_1299<?>>)item.method_57347().method_58694(class_9334.field_49609);
/*     */ 
/*     */       
/* 241 */       if (component.method_72530() == entityType) {
/* 242 */         stack = item.method_7854();
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 247 */     if (stack != null) { table.add((WWidget)this.theme.item(stack)); }
/*     */     else
/* 249 */     { if (EMPTY_SPAWN_EGG_TEXTURE == null) {
/* 250 */         EMPTY_SPAWN_EGG_TEXTURE = Texture.readResource("/assets/meteor-client/textures/empty_spawn_egg.png", false, FilterMode.NEAREST);
/*     */       }
/*     */       
/* 253 */       table.add((WWidget)this.theme.texture(32.0D, 32.0D, 0.0D, EMPTY_SPAWN_EGG_TEXTURE)); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 258 */     table.add((WWidget)this.theme.label(Names.get(entityType)));
/*     */ 
/*     */ 
/*     */     
/* 262 */     WCheckbox a = (WCheckbox)table.add((WWidget)this.theme.checkbox(((Set)this.setting.get()).contains(entityType))).expandCellX().right().widget();
/* 263 */     a.action = (() -> {
/*     */         if (a.checked) {
/*     */           ((Set<class_1299>)this.setting.get()).add(entityType); switch (entityType.method_5891()) {
/*     */             case field_6294:
/*     */               if (this.hasAnimal == 0)
/*     */                 tableCheckbox.checked = true;  this.hasAnimal++; break;
/*     */             case field_24460:
/*     */             case field_6300:
/*     */             case field_30092:
/*     */             case field_34447:
/*     */               if (this.hasWaterAnimal == 0)
/*     */                 tableCheckbox.checked = true;  this.hasWaterAnimal++; break;
/*     */             case field_6302:
/*     */               if (this.hasMonster == 0)
/*     */                 tableCheckbox.checked = true;  this.hasMonster++; break;
/*     */             case field_6303:
/*     */               if (this.hasAmbient == 0)
/*     */                 tableCheckbox.checked = true;  this.hasAmbient++; break;
/*     */             case field_17715:
/*     */               if (this.hasMisc == 0)
/*     */                 tableCheckbox.checked = true;  this.hasMisc++; break;
/*     */           } 
/*     */         } else if (((Set)this.setting.get()).remove(entityType)) {
/*     */           switch (entityType.method_5891()) {
/*     */             case field_6294:
/*     */               this.hasAnimal--; if (this.hasAnimal == 0)
/*     */                 tableCheckbox.checked = false; 
/*     */               break;
/*     */             case field_24460:
/*     */             case field_6300:
/*     */             case field_30092:
/*     */             case field_34447:
/*     */               this.hasWaterAnimal--;
/*     */               if (this.hasWaterAnimal == 0)
/*     */                 tableCheckbox.checked = false; 
/*     */               break;
/*     */             case field_6302:
/*     */               this.hasMonster--;
/*     */               if (this.hasMonster == 0)
/*     */                 tableCheckbox.checked = false; 
/*     */               break;
/*     */             case field_6303:
/*     */               this.hasAmbient--;
/*     */               if (this.hasAmbient == 0)
/*     */                 tableCheckbox.checked = false; 
/*     */               break;
/*     */             case field_17715:
/*     */               this.hasMisc--;
/*     */               if (this.hasMisc == 0)
/*     */                 tableCheckbox.checked = false; 
/*     */               break;
/*     */           } 
/*     */         } 
/*     */         this.setting.onChanged();
/*     */       });
/* 318 */     table.row();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\settings\EntityTypeListSettingScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */