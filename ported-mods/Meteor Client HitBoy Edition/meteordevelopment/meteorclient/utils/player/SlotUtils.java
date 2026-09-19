/*     */ package meteordevelopment.meteorclient.utils.player;
/*     */ 
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.mixin.CreativeInventoryScreenAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.ItemGroupsAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.MountScreenHandlerAccessor;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1492;
/*     */ import net.minecraft.class_1501;
/*     */ import net.minecraft.class_1703;
/*     */ import net.minecraft.class_1707;
/*     */ import net.minecraft.class_7923;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SlotUtils
/*     */ {
/*     */   public static final int HOTBAR_START = 0;
/*     */   public static final int HOTBAR_END = 8;
/*     */   public static final int MAIN_START = 9;
/*     */   public static final int MAIN_END = 35;
/*     */   public static final int ARMOR_START = 36;
/*     */   public static final int ARMOR_END = 39;
/*     */   public static final int OFFHAND = 40;
/*     */   
/*     */   public static int indexToId(int i) {
/*  59 */     if (MeteorClient.mc.field_1724 == null) return -1; 
/*  60 */     class_1703 handler = MeteorClient.mc.field_1724.field_7512;
/*     */     
/*  62 */     if (handler instanceof net.minecraft.class_1723) return survivalInventory(i); 
/*  63 */     if (handler instanceof net.minecraft.class_481.class_483) return creativeInventory(i); 
/*  64 */     if (handler instanceof class_1707) { class_1707 genericContainerScreenHandler = (class_1707)handler; return genericContainer(i, genericContainerScreenHandler.method_17388()); }
/*  65 */      if (handler instanceof net.minecraft.class_1714) return craftingTable(i); 
/*  66 */     if (handler instanceof net.minecraft.class_3858) return furnace(i); 
/*  67 */     if (handler instanceof net.minecraft.class_3705) return furnace(i); 
/*  68 */     if (handler instanceof net.minecraft.class_3706) return furnace(i); 
/*  69 */     if (handler instanceof net.minecraft.class_1716) return generic3x3(i); 
/*  70 */     if (handler instanceof net.minecraft.class_1718) return enchantmentTable(i); 
/*  71 */     if (handler instanceof net.minecraft.class_1708) return brewingStand(i); 
/*  72 */     if (handler instanceof net.minecraft.class_1728) return villager(i); 
/*  73 */     if (handler instanceof net.minecraft.class_1704) return beacon(i); 
/*  74 */     if (handler instanceof net.minecraft.class_1706) return anvil(i); 
/*  75 */     if (handler instanceof net.minecraft.class_1722) return hopper(i); 
/*  76 */     if (handler instanceof net.minecraft.class_1733) return genericContainer(i, 3); 
/*  77 */     if (handler instanceof net.minecraft.class_1724) return horse(handler, i); 
/*  78 */     if (handler instanceof net.minecraft.class_3910) return cartographyTable(i); 
/*  79 */     if (handler instanceof net.minecraft.class_3803) return grindstone(i); 
/*  80 */     if (handler instanceof net.minecraft.class_3916) return lectern(); 
/*  81 */     if (handler instanceof net.minecraft.class_1726) return loom(i); 
/*  82 */     if (handler instanceof net.minecraft.class_3971) return stonecutter(i); 
/*  83 */     if (handler instanceof net.minecraft.class_8881) return crafter(i); 
/*  84 */     if (handler instanceof net.minecraft.class_4862) return smithingTable(i);
/*     */     
/*  86 */     return -1;
/*     */   }
/*     */   
/*     */   private static int survivalInventory(int i) {
/*  90 */     if (isHotbar(i)) return 36 + i; 
/*  91 */     if (isArmor(i)) return 5 + i - 36; 
/*  92 */     if (i == 40) return 45; 
/*  93 */     return i;
/*     */   }
/*     */   
/*     */   private static int creativeInventory(int i) {
/*  97 */     if (CreativeInventoryScreenAccessor.meteor$getSelectedTab() != class_7923.field_44687.method_29107(ItemGroupsAccessor.meteor$getInventory()))
/*  98 */       return -1; 
/*  99 */     return survivalInventory(i);
/*     */   }
/*     */   
/*     */   private static int genericContainer(int i, int rows) {
/* 103 */     if (isHotbar(i)) return (rows + 3) * 9 + i; 
/* 104 */     if (isMain(i)) return rows * 9 + i - 9; 
/* 105 */     return -1;
/*     */   }
/*     */   
/*     */   private static int craftingTable(int i) {
/* 109 */     if (isHotbar(i)) return 37 + i; 
/* 110 */     if (isMain(i)) return i + 1; 
/* 111 */     return -1;
/*     */   }
/*     */   
/*     */   private static int furnace(int i) {
/* 115 */     if (isHotbar(i)) return 30 + i; 
/* 116 */     if (isMain(i)) return 3 + i - 9; 
/* 117 */     return -1;
/*     */   }
/*     */   
/*     */   private static int generic3x3(int i) {
/* 121 */     if (isHotbar(i)) return 36 + i; 
/* 122 */     if (isMain(i)) return i; 
/* 123 */     return -1;
/*     */   }
/*     */   
/*     */   private static int enchantmentTable(int i) {
/* 127 */     if (isHotbar(i)) return 29 + i; 
/* 128 */     if (isMain(i)) return 2 + i - 9; 
/* 129 */     return -1;
/*     */   }
/*     */   
/*     */   private static int brewingStand(int i) {
/* 133 */     if (isHotbar(i)) return 32 + i; 
/* 134 */     if (isMain(i)) return 5 + i - 9; 
/* 135 */     return -1;
/*     */   }
/*     */   
/*     */   private static int villager(int i) {
/* 139 */     if (isHotbar(i)) return 30 + i; 
/* 140 */     if (isMain(i)) return 3 + i - 9; 
/* 141 */     return -1;
/*     */   }
/*     */   
/*     */   private static int beacon(int i) {
/* 145 */     if (isHotbar(i)) return 28 + i; 
/* 146 */     if (isMain(i)) return 1 + i - 9; 
/* 147 */     return -1;
/*     */   }
/*     */   
/*     */   private static int anvil(int i) {
/* 151 */     if (isHotbar(i)) return 30 + i; 
/* 152 */     if (isMain(i)) return 3 + i - 9; 
/* 153 */     return -1;
/*     */   }
/*     */   
/*     */   private static int hopper(int i) {
/* 157 */     if (isHotbar(i)) return 32 + i; 
/* 158 */     if (isMain(i)) return 5 + i - 9; 
/* 159 */     return -1;
/*     */   }
/*     */   
/*     */   private static int horse(class_1703 handler, int i) {
/* 163 */     class_1309 entity = ((MountScreenHandlerAccessor)handler).meteor$getMount();
/*     */     
/* 165 */     if (entity instanceof class_1501) { class_1501 llamaEntity = (class_1501)entity;
/* 166 */       int strength = llamaEntity.method_6803();
/* 167 */       if (isHotbar(i)) return 2 + 3 * strength + 28 + i; 
/* 168 */       if (isMain(i)) return 2 + 3 * strength + 1 + i - 9;  }
/* 169 */     else if (entity instanceof net.minecraft.class_1498 || entity instanceof net.minecraft.class_1506 || entity instanceof net.minecraft.class_1507 || entity instanceof net.minecraft.class_7689)
/*     */     
/* 171 */     { if (isHotbar(i)) return 29 + i; 
/* 172 */       if (isMain(i)) return 2 + i - 9;  }
/* 173 */     else if (entity instanceof class_1492) { class_1492 abstractDonkeyEntity = (class_1492)entity;
/* 174 */       boolean chest = abstractDonkeyEntity.method_6703();
/* 175 */       if (isHotbar(i)) return (chest ? 44 : 29) + i; 
/* 176 */       if (isMain(i)) return (chest ? 17 : 2) + i - 9;
/*     */        }
/*     */     
/* 179 */     return -1;
/*     */   }
/*     */   
/*     */   private static int cartographyTable(int i) {
/* 183 */     if (isHotbar(i)) return 30 + i; 
/* 184 */     if (isMain(i)) return 3 + i - 9; 
/* 185 */     return -1;
/*     */   }
/*     */   
/*     */   private static int grindstone(int i) {
/* 189 */     if (isHotbar(i)) return 30 + i; 
/* 190 */     if (isMain(i)) return 3 + i - 9; 
/* 191 */     return -1;
/*     */   }
/*     */   
/*     */   private static int lectern() {
/* 195 */     return -1;
/*     */   }
/*     */   
/*     */   private static int loom(int i) {
/* 199 */     if (isHotbar(i)) return 31 + i; 
/* 200 */     if (isMain(i)) return 4 + i - 9; 
/* 201 */     return -1;
/*     */   }
/*     */   
/*     */   private static int stonecutter(int i) {
/* 205 */     if (isHotbar(i)) return 29 + i; 
/* 206 */     if (isMain(i)) return 2 + i - 9; 
/* 207 */     return -1;
/*     */   }
/*     */   
/*     */   private static int crafter(int i) {
/* 211 */     if (isHotbar(i)) return 36 + i; 
/* 212 */     if (isMain(i)) return i; 
/* 213 */     return -1;
/*     */   }
/*     */   
/*     */   private static int smithingTable(int i) {
/* 217 */     if (isHotbar(i)) return 31 + i; 
/* 218 */     if (isMain(i)) return 4 + i - 9; 
/* 219 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isHotbar(int slotIndex) {
/* 225 */     return (slotIndex >= 0 && slotIndex <= 8);
/*     */   }
/*     */   
/*     */   public static boolean isMain(int slotIndex) {
/* 229 */     return (slotIndex >= 9 && slotIndex <= 35);
/*     */   }
/*     */   
/*     */   public static boolean isArmor(int slotIndex) {
/* 233 */     return (slotIndex >= 36 && slotIndex <= 39);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\SlotUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */