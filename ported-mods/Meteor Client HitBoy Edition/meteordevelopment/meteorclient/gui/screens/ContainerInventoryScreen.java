/*     */ package meteordevelopment.meteorclient.gui.screens;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.BetterTooltips;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_10799;
/*     */ import net.minecraft.class_11907;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_1661;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_9276;
/*     */ import net.minecraft.class_9334;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerInventoryScreen
/*     */   extends class_437
/*     */ {
/*  35 */   private static final class_2960 SLOT_TEXTURE = class_2960.method_60656("container/slot");
/*     */   private static final int SLOT_SIZE = 18;
/*     */   private static final int SCREEN_WIDTH = 176;
/*     */   private final List<class_1799> containerItems;
/*     */   private final class_1661 playerInventory;
/*     */   private final int containerRows;
/*     */   private int x;
/*     */   private int y;
/*     */   private int baseX;
/*     */   private int baseY;
/*     */   private int playerY;
/*     */   
/*     */   public ContainerInventoryScreen(class_1799 containerItem) {
/*  48 */     super(containerItem.method_7964());
/*  49 */     this.playerInventory = MeteorClient.mc.field_1724.method_31548();
/*     */     
/*  51 */     this.containerItems = new ArrayList<>();
/*  52 */     if (containerItem.method_7909() instanceof net.minecraft.class_5537) {
/*  53 */       class_9276 bundleContents = (class_9276)containerItem.method_58694(class_9334.field_49650);
/*  54 */       if (bundleContents != null) {
/*  55 */         Objects.requireNonNull(this.containerItems); bundleContents.method_57421().forEach(this.containerItems::add);
/*     */       } 
/*     */     } else {
/*  58 */       class_1799[] tempItems = new class_1799[64];
/*  59 */       Utils.getItemsInContainerItem(containerItem, tempItems);
/*  60 */       Collections.addAll(this.containerItems, tempItems);
/*     */     } 
/*     */     
/*  63 */     this.containerRows = Math.max(1, class_3532.method_38788(this.containerItems.size(), 9));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void method_25426() {
/*  68 */     super.method_25426();
/*  69 */     this.x = (this.field_22789 - 176) / 2;
/*  70 */     this.y = (this.field_22790 - 114 + this.containerRows * 18 + 20) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25394(class_332 context, int mouseX, int mouseY, float delta) {
/*  75 */     super.method_25394(context, mouseX, mouseY, delta);
/*     */     
/*  77 */     this.baseX = this.x + 8;
/*  78 */     this.baseY = this.y + 18;
/*  79 */     this.playerY = this.baseY + this.containerRows * 18 + 20;
/*     */ 
/*     */     
/*  82 */     for (int j = 0; j < this.containerRows + 4; j++) {
/*  83 */       for (int col = 0; col < 9; col++) {
/*  84 */         int slotY = (j < this.containerRows) ? (this.baseY + j * 18) : (this.playerY + (j - this.containerRows) * 18);
/*  85 */         context.method_52706(class_10799.field_56883, SLOT_TEXTURE, this.baseX + col * 18, slotY, 18, 18);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  90 */     for (int i = 0; i < this.containerItems.size(); i++) {
/*  91 */       class_1799 class_1799 = this.containerItems.get(i);
/*  92 */       if (!class_1799.method_7960()) {
/*  93 */         int itemX = this.baseX + i % 9 * 18 + 1;
/*  94 */         int itemY = this.baseY + i / 9 * 18 + 1;
/*  95 */         context.method_51427(class_1799, itemX, itemY);
/*  96 */         context.method_51431(this.field_22793, class_1799, itemX, itemY);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 101 */     for (int row = 0; row < 4; row++) {
/* 102 */       for (int col = 0; col < 9; col++) {
/* 103 */         int slotIndex = (row < 3) ? (9 + row * 9 + col) : col;
/* 104 */         class_1799 class_1799 = this.playerInventory.method_5438(slotIndex);
/* 105 */         if (!class_1799.method_7960()) {
/* 106 */           int itemX = this.baseX + col * 18 + 1;
/* 107 */           int itemY = this.playerY + row * 18 + 1;
/* 108 */           context.method_51427(class_1799, itemX, itemY);
/* 109 */           context.method_51431(this.field_22793, class_1799, itemX, itemY);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 115 */     context.method_51448().pushMatrix();
/* 116 */     context.method_51448().translate(this.x, this.y);
/* 117 */     if (this.field_22793 != null) {
/* 118 */       context.method_51439(this.field_22793, this.field_22785, 8, 6, -12566464, false);
/* 119 */       context.method_51439(this.field_22793, this.playerInventory.method_5476(), 8, 18 + this.containerRows * 18 + 10, -12566464, false);
/*     */     } 
/* 121 */     context.method_51448().popMatrix();
/*     */ 
/*     */     
/* 124 */     class_1799 item = getSelectedItem(mouseX, mouseY);
/* 125 */     if (!item.method_7960()) {
/* 126 */       context.method_64038(this.field_22793, method_25408(MeteorClient.mc, item), item.method_32347(), mouseX, mouseY);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25402(class_11909 click, boolean doubled) {
/* 132 */     BetterTooltips tooltips = (BetterTooltips)Modules.get().get(BetterTooltips.class);
/*     */     
/* 134 */     class_1799 stack = getSelectedItem((int)click.comp_4798(), (int)click.comp_4799());
/* 135 */     if (tooltips.shouldOpenContents((class_11907)click)) {
/* 136 */       return tooltips.openContent(stack);
/*     */     }
/*     */     
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25404(class_11908 input) {
/* 144 */     BetterTooltips tooltips = (BetterTooltips)Modules.get().get(BetterTooltips.class);
/*     */     
/* 146 */     class_1799 stack = getSelectedItem((int)MeteorClient.mc.field_1729.method_68879(MeteorClient.mc.method_22683()), (int)MeteorClient.mc.field_1729.method_68883(MeteorClient.mc.method_22683()));
/* 147 */     if (tooltips.shouldOpenContents((class_11907)input)) {
/* 148 */       return tooltips.openContent(stack);
/*     */     }
/*     */     
/* 151 */     if (input.comp_4795() == 256 || MeteorClient.mc.field_1690.field_1822.method_1417(input)) {
/* 152 */       method_25419();
/* 153 */       return true;
/*     */     } 
/*     */     
/* 156 */     return false;
/*     */   }
/*     */   
/*     */   private class_1799 getSelectedItem(int mouseX, int mouseY) {
/* 160 */     if (mouseX < this.baseX || mouseX > this.baseX + 162) return class_1799.field_8037;
/*     */     
/* 162 */     int col = (mouseX - this.baseX) / 18;
/* 163 */     if (col > 8) return class_1799.field_8037;
/*     */     
/* 165 */     if (mouseY >= this.baseY && mouseY < this.baseY + this.containerRows * 18) {
/* 166 */       int index = (mouseY - this.baseY) / 18 * 9 + col;
/* 167 */       return (index < this.containerItems.size()) ? this.containerItems.get(index) : class_1799.field_8037;
/*     */     } 
/*     */     
/* 170 */     if (mouseY >= this.playerY && mouseY < this.playerY + 72) {
/* 171 */       int row = (mouseY - this.playerY) / 18;
/* 172 */       int slotIndex = (row < 3) ? (9 + row * 9 + col) : col;
/* 173 */       return this.playerInventory.method_5438(slotIndex);
/*     */     } 
/*     */     
/* 176 */     return class_1799.field_8037;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\ContainerInventoryScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */