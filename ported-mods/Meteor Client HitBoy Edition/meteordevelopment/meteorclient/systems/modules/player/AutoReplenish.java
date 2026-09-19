/*     */ package meteordevelopment.meteorclient.systems.modules.player;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.ItemListSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.combat.AutoTotem;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ 
/*     */ public class AutoReplenish
/*     */   extends Module
/*     */ {
/*  25 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  27 */   private final Setting<Integer> minCount = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  28 */       .name("min-count"))
/*  29 */       .description("Replenish a slot when it reaches this item count."))
/*  30 */       .defaultValue(Integer.valueOf(8)))
/*  31 */       .min(1)
/*  32 */       .sliderRange(1, 63)
/*  33 */       .build());
/*     */ 
/*     */   
/*  36 */   private final Setting<Integer> tickDelay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  37 */       .name("delay"))
/*  38 */       .description("How long in ticks to wait between replenishing your hotbar."))
/*  39 */       .defaultValue(Integer.valueOf(1)))
/*  40 */       .min(0)
/*  41 */       .build());
/*     */ 
/*     */   
/*  44 */   private final Setting<Boolean> offhand = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  45 */       .name("offhand"))
/*  46 */       .description("Whether or not to replenish items in your offhand."))
/*  47 */       .defaultValue(Boolean.valueOf(true)))
/*  48 */       .build());
/*     */ 
/*     */   
/*  51 */   private final Setting<Boolean> unstackable = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  52 */       .name("unstackable"))
/*  53 */       .description("Replenish unstackable items."))
/*  54 */       .defaultValue(Boolean.valueOf(true)))
/*  55 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> sameEnchants;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> searchHotbar;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<List<class_1792>> excludedItems;
/*     */ 
/*     */ 
/*     */   
/*     */   private final class_1799[] items;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean prevHadOpenScreen;
/*     */ 
/*     */ 
/*     */   
/*     */   private int tickDelayLeft;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AutoReplenish() {
/*  88 */     super(Categories.Player, "auto-replenish", "Automatically refills items in your hotbar, main hand, or offhand."); Objects.requireNonNull(this.unstackable); this.sameEnchants = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("same-enchants")).description("Only replace unstackables with items that have the same enchants.")).defaultValue(Boolean.valueOf(true))).visible(this.unstackable::get)).build()); this.searchHotbar = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("search-hotbar")).description("Combine stacks in your hotbar/offhand as a last resort.")).defaultValue(Boolean.valueOf(false))).build()); this.excludedItems = this.sgGeneral.add((Setting)((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder()).name("excluded-items")).description("Items that won't be replenished.")).build());
/*     */     this.items = new class_1799[10];
/*  90 */     Arrays.fill((Object[])this.items, class_1802.field_8162.method_7854());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  95 */     fillItems();
/*  96 */     this.tickDelayLeft = ((Integer)this.tickDelay.get()).intValue();
/*  97 */     this.prevHadOpenScreen = (this.mc.field_1755 != null);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 102 */     if (this.mc.field_1755 == null && this.prevHadOpenScreen) {
/* 103 */       fillItems();
/*     */     }
/*     */     
/* 106 */     this.prevHadOpenScreen = (this.mc.field_1755 != null);
/* 107 */     if (this.mc.field_1724.field_7512.method_7602().size() != 46 || this.mc.field_1755 != null)
/*     */       return; 
/* 109 */     if (this.tickDelayLeft > 0) {
/* 110 */       this.tickDelayLeft--;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 115 */     for (int i = 0; i < 9; i++) {
/* 116 */       class_1799 stack = this.mc.field_1724.method_31548().method_5438(i);
/* 117 */       checkSlot(i, stack);
/*     */     } 
/*     */ 
/*     */     
/* 121 */     if (((Boolean)this.offhand.get()).booleanValue() && !((AutoTotem)Modules.get().get(AutoTotem.class)).isLocked()) {
/* 122 */       class_1799 stack = this.mc.field_1724.method_6079();
/* 123 */       checkSlot(9, stack);
/*     */     } 
/*     */     
/* 126 */     this.tickDelayLeft = ((Integer)this.tickDelay.get()).intValue();
/*     */   }
/*     */   
/*     */   private void checkSlot(int slot, class_1799 stack) {
/* 130 */     class_1799 prevStack = this.items[slot];
/* 131 */     this.items[slot] = stack.method_7972();
/*     */     
/* 133 */     if (slot == 9) slot = 40;
/*     */     
/* 135 */     if (((List)this.excludedItems.get()).contains(stack.method_7909()))
/* 136 */       return;  if (((List)this.excludedItems.get()).contains(prevStack.method_7909()))
/*     */       return; 
/* 138 */     int fromSlot = -1;
/*     */ 
/*     */     
/* 141 */     if (stack.method_7946() && !stack.method_7960() && stack.method_7947() <= ((Integer)this.minCount.get()).intValue()) {
/* 142 */       fromSlot = findItem(stack, slot, ((Integer)this.minCount.get()).intValue() - stack.method_7947() + 1, true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 148 */     if (prevStack.method_7946() && stack.method_7960() && !prevStack.method_7960()) {
/* 149 */       fromSlot = findItem(prevStack, slot, ((Integer)this.minCount.get()).intValue() - stack.method_7947() + 1, false);
/*     */     }
/*     */ 
/*     */     
/* 153 */     if (((Boolean)this.unstackable.get()).booleanValue() && !prevStack.method_7946() && stack.method_7960() && !prevStack.method_7960()) {
/* 154 */       fromSlot = findItem(prevStack, slot, 1, false);
/*     */     }
/*     */ 
/*     */     
/* 158 */     if (fromSlot == this.mc.field_1724.method_31548().method_67532() || fromSlot == 40)
/* 159 */       return;  if (fromSlot < 9 && fromSlot < slot && slot != this.mc.field_1724.method_31548().method_67532() && slot != 40)
/*     */       return; 
/* 161 */     InvUtils.move().from(fromSlot).to(slot);
/*     */   }
/*     */   
/*     */   private int findItem(class_1799 lookForStack, int excludedSlot, int goodEnoughCount, boolean mustCombine) {
/* 165 */     int slot = -1;
/* 166 */     int count = 0;
/*     */     
/* 168 */     for (int i = this.mc.field_1724.method_31548().method_5439() - 2; i >= (((Boolean)this.searchHotbar.get()).booleanValue() ? 0 : 9); i--) {
/* 169 */       if (i != excludedSlot) {
/*     */         
/* 171 */         class_1799 stack = this.mc.field_1724.method_31548().method_5438(i);
/* 172 */         if (stack.method_7909() == lookForStack.method_7909())
/*     */         {
/* 174 */           if ((!mustCombine || class_1799.method_31577(lookForStack, stack)) && (
/* 175 */             !((Boolean)this.sameEnchants.get()).booleanValue() || stack.method_58657().equals(lookForStack.method_58657())))
/*     */           {
/* 177 */             if (stack.method_7947() > count) {
/* 178 */               slot = i;
/* 179 */               count = stack.method_7947();
/*     */               
/* 181 */               if (count >= goodEnoughCount)
/*     */                 break; 
/*     */             }  }  } 
/*     */       } 
/* 185 */     }  return slot;
/*     */   }
/*     */   
/*     */   private void fillItems() {
/* 189 */     for (int i = 0; i < 9; i++) {
/* 190 */       this.items[i] = this.mc.field_1724.method_31548().method_5438(i).method_7972();
/*     */     }
/*     */     
/* 193 */     this.items[9] = this.mc.field_1724.method_6079().method_7972();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\AutoReplenish.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */