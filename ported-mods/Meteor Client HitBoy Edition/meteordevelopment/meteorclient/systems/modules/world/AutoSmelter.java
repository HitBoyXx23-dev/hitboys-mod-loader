/*     */ package meteordevelopment.meteorclient.systems.modules.world;
/*     */ 
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.mixininterface.IAbstractFurnaceScreenHandler;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ItemListSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import net.minecraft.class_10290;
/*     */ import net.minecraft.class_1720;
/*     */ import net.minecraft.class_1735;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoSmelter
/*     */   extends Module
/*     */ {
/*  26 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  28 */   private final Setting<List<class_1792>> fuelItems = this.sgGeneral.add((Setting)((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder())
/*  29 */       .name("fuel-items"))
/*  30 */       .description("Items to use as fuel"))
/*  31 */       .defaultValue(new class_1792[] { class_1802.field_8713, class_1802.field_8665
/*  32 */         }).filter(this::fuelItemFilter)
/*  33 */       .bypassFilterWhenSavingAndLoading()
/*  34 */       .build());
/*     */ 
/*     */   
/*  37 */   private final Setting<List<class_1792>> smeltableItems = this.sgGeneral.add((Setting)((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder())
/*  38 */       .name("smeltable-items"))
/*  39 */       .description("Items to smelt"))
/*  40 */       .defaultValue(new class_1792[] { class_1802.field_8599, class_1802.field_8775, class_1802.field_27018, class_1802.field_33400, class_1802.field_33401, class_1802.field_33402
/*  41 */         }).filter(this::smeltableItemFilter)
/*  42 */       .bypassFilterWhenSavingAndLoading()
/*  43 */       .build());
/*     */ 
/*     */   
/*  46 */   private final Setting<Boolean> disableWhenOutOfItems = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  47 */       .name("disable-when-out-of-items"))
/*  48 */       .description("Disable the module when you run out of items"))
/*  49 */       .defaultValue(Boolean.valueOf(true)))
/*  50 */       .build());
/*     */ 
/*     */   
/*     */   public AutoSmelter() {
/*  54 */     super(Categories.World, "auto-smelter", "Automatically smelts items from your inventory");
/*     */   }
/*     */   
/*     */   private boolean fuelItemFilter(class_1792 item) {
/*  58 */     if (!Utils.canUpdate()) return false;
/*     */     
/*  60 */     return this.mc.method_1562().method_62147().method_61751().contains(item);
/*     */   }
/*     */   
/*     */   private boolean smeltableItemFilter(class_1792 item) {
/*  64 */     return (this.mc.field_1687 != null && this.mc.field_1687.method_8433().method_64678(class_10290.field_54650).method_64701(item.method_7854()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(class_1720 c) {
/*  69 */     if (this.mc.field_1724.field_6012 % 10 == 0) {
/*     */       return;
/*     */     }
/*  72 */     checkFuel(c);
/*     */ 
/*     */     
/*  75 */     takeResults(c);
/*     */ 
/*     */     
/*  78 */     insertItems(c);
/*     */   }
/*     */   
/*     */   private void insertItems(class_1720 c) {
/*  82 */     class_1799 inputItemStack = ((class_1735)c.field_7761.getFirst()).method_7677();
/*  83 */     if (!inputItemStack.method_7960())
/*     */       return; 
/*  85 */     int slot = -1;
/*     */     
/*  87 */     for (int i = 3; i < c.field_7761.size(); ) {
/*  88 */       class_1799 item = ((class_1735)c.field_7761.get(i)).method_7677();
/*  89 */       if (!((IAbstractFurnaceScreenHandler)c).meteor$isItemSmeltable(item) || 
/*  90 */         !((List)this.smeltableItems.get()).contains(item.method_7909()) || 
/*  91 */         !smeltableItemFilter(item.method_7909())) {
/*     */         i++; continue;
/*  93 */       }  slot = i;
/*     */     } 
/*     */ 
/*     */     
/*  97 */     if (((Boolean)this.disableWhenOutOfItems.get()).booleanValue() && slot == -1) {
/*  98 */       error("You do not have any items in your inventory that can be smelted. Disabling.", new Object[0]);
/*  99 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/* 103 */     InvUtils.move().fromId(slot).toId(0);
/*     */   }
/*     */   
/*     */   private void checkFuel(class_1720 c) {
/* 107 */     class_1799 fuelStack = ((class_1735)c.field_7761.get(1)).method_7677();
/*     */     
/* 109 */     if (c.method_17364() > 0.0F)
/* 110 */       return;  if (!fuelStack.method_7960())
/*     */       return; 
/* 112 */     int slot = -1;
/* 113 */     for (int i = 3; i < c.field_7761.size(); ) {
/* 114 */       class_1799 item = ((class_1735)c.field_7761.get(i)).method_7677();
/* 115 */       if (!((List)this.fuelItems.get()).contains(item.method_7909()) || 
/* 116 */         !fuelItemFilter(item.method_7909())) {
/*     */         i++; continue;
/* 118 */       }  slot = i;
/*     */     } 
/*     */ 
/*     */     
/* 122 */     if (((Boolean)this.disableWhenOutOfItems.get()).booleanValue() && slot == -1) {
/* 123 */       error("You do not have any fuel in your inventory. Disabling.", new Object[0]);
/* 124 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/* 128 */     InvUtils.move().fromId(slot).toId(1);
/*     */   }
/*     */   
/*     */   private void takeResults(class_1720 c) {
/* 132 */     class_1799 resultStack = ((class_1735)c.field_7761.get(2)).method_7677();
/* 133 */     if (resultStack.method_7960())
/*     */       return; 
/* 135 */     InvUtils.shiftClick().slotId(2);
/*     */     
/* 137 */     if (!resultStack.method_7960()) {
/* 138 */       error("Your inventory is full. Disabling.", new Object[0]);
/* 139 */       toggle();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\AutoSmelter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */