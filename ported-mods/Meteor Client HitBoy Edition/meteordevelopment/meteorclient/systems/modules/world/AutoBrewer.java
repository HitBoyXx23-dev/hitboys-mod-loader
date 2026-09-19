/*     */ package meteordevelopment.meteorclient.systems.modules.world;
/*     */ 
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.PotionSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.misc.MyPotion;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_1708;
/*     */ import net.minecraft.class_1735;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1842;
/*     */ import net.minecraft.class_1844;
/*     */ import net.minecraft.class_1847;
/*     */ import net.minecraft.class_6880;
/*     */ import net.minecraft.class_9334;
/*     */ 
/*     */ public class AutoBrewer
/*     */   extends Module {
/*  24 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  26 */   private final Setting<MyPotion> potion = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new PotionSetting.Builder())
/*  27 */       .name("potion"))
/*  28 */       .description("The type of potion to brew."))
/*  29 */       .defaultValue(MyPotion.Strength))
/*  30 */       .build());
/*     */   
/*     */   private int ingredientI;
/*     */   
/*     */   private boolean first;
/*     */   private int timer;
/*     */   
/*     */   public AutoBrewer() {
/*  38 */     super(Categories.World, "auto-brewer", "Automatically brews the specified potion.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  43 */     this.first = false;
/*     */   }
/*     */   
/*     */   public void onBrewingStandClose() {
/*  47 */     this.first = false;
/*     */   }
/*     */   
/*     */   public void tick(class_1708 c) {
/*  51 */     this.timer++;
/*     */ 
/*     */     
/*  54 */     if (!this.first) {
/*  55 */       this.first = true;
/*     */       
/*  57 */       this.ingredientI = -2;
/*  58 */       this.timer = 0;
/*     */     } 
/*     */ 
/*     */     
/*  62 */     if (c.method_17378() != 0 || this.timer < 5)
/*     */       return; 
/*  64 */     if (this.ingredientI == -2) {
/*     */       
/*  66 */       if (takePotions(c))
/*  67 */         return;  this.ingredientI++;
/*  68 */       this.timer = 0;
/*  69 */     } else if (this.ingredientI == -1) {
/*     */       
/*  71 */       if (insertWaterBottles(c))
/*  72 */         return;  this.ingredientI++;
/*  73 */       this.timer = 0;
/*  74 */     } else if (this.ingredientI < ((MyPotion)this.potion.get()).ingredients.length) {
/*     */       
/*  76 */       if (checkFuel(c))
/*  77 */         return;  if (insertIngredient(c, ((MyPotion)this.potion.get()).ingredients[this.ingredientI]))
/*  78 */         return;  this.ingredientI++;
/*  79 */       this.timer = 0;
/*     */     } else {
/*     */       
/*  82 */       this.ingredientI = -2;
/*  83 */       this.timer = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean insertIngredient(class_1708 c, class_1792 ingredient) {
/*  88 */     int slot = -1;
/*     */     
/*  90 */     for (int slotI = 5; slotI < c.field_7761.size(); slotI++) {
/*  91 */       if (((class_1735)c.field_7761.get(slotI)).method_7677().method_7909() == ingredient) {
/*  92 */         slot = slotI;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  97 */     if (slot == -1) {
/*  98 */       error("You do not have any %s left in your inventory... disabling.", new Object[] { class_1074.method_4662(ingredient.method_7876(), new Object[0]) });
/*  99 */       toggle();
/* 100 */       return true;
/*     */     } 
/*     */     
/* 103 */     moveOneItem(c, slot, 3);
/*     */     
/* 105 */     return false;
/*     */   }
/*     */   
/*     */   private boolean checkFuel(class_1708 c) {
/* 109 */     if (c.method_17377() == 0) {
/* 110 */       int slot = -1;
/*     */       
/* 112 */       for (int slotI = 5; slotI < c.field_7761.size(); slotI++) {
/* 113 */         if (((class_1735)c.field_7761.get(slotI)).method_7677().method_7909() == class_1802.field_8183) {
/* 114 */           slot = slotI;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 119 */       if (slot == -1) {
/* 120 */         error("You do not have a sufficient amount of blaze powder to use as fuel for the brew... disabling.", new Object[0]);
/* 121 */         toggle();
/* 122 */         return true;
/*     */       } 
/*     */       
/* 125 */       moveOneItem(c, slot, 4);
/*     */     } 
/*     */     
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   private void moveOneItem(class_1708 c, int from, int to) {
/* 132 */     InvUtils.move().fromId(from).toId(to);
/*     */   }
/*     */   
/*     */   private boolean insertWaterBottles(class_1708 c) {
/* 136 */     for (int i = 0; i < 3; i++) {
/* 137 */       int slot = -1;
/*     */       
/* 139 */       for (int slotI = 5; slotI < c.field_7761.size(); slotI++) {
/* 140 */         if (((class_1735)c.field_7761.get(slotI)).method_7677().method_7909() == class_1802.field_8574) {
/* 141 */           class_1842 potion = (class_1842)((class_6880)((class_1844)((class_1735)c.field_7761.get(slotI)).method_7677().method_58694(class_9334.field_49651)).comp_2378().get()).comp_349();
/* 142 */           if (potion == class_1847.field_8991.comp_349()) {
/* 143 */             slot = slotI;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/* 149 */       if (slot == -1) {
/* 150 */         error("You do not have a sufficient amount of water bottles to complete this brew... disabling.", new Object[0]);
/* 151 */         toggle();
/* 152 */         return true;
/*     */       } 
/*     */       
/* 155 */       InvUtils.move().fromId(slot).toId(i);
/*     */     } 
/*     */     
/* 158 */     return false;
/*     */   }
/*     */   
/*     */   private boolean takePotions(class_1708 c) {
/* 162 */     for (int i = 0; i < 3; i++) {
/* 163 */       InvUtils.shiftClick().slotId(i);
/*     */       
/* 165 */       if (!((class_1735)c.field_7761.get(i)).method_7677().method_7960()) {
/* 166 */         error("You do not have a sufficient amount of inventory space... disabling.", new Object[0]);
/* 167 */         toggle();
/* 168 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 172 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\AutoBrewer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */