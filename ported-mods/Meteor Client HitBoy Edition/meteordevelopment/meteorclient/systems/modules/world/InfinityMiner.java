/*     */ package meteordevelopment.meteorclient.systems.modules.world;
/*     */ 
/*     */ import baritone.api.BaritoneAPI;
/*     */ import baritone.api.IBaritone;
/*     */ import baritone.api.Settings;
/*     */ import baritone.api.pathing.goals.Goal;
/*     */ import baritone.api.pathing.goals.GoalBlock;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BlockListSetting;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.ItemListSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1893;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2661;
/*     */ import net.minecraft.class_3489;
/*     */ 
/*     */ public class InfinityMiner extends Module {
/*  38 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  39 */   private final SettingGroup sgWhenFull = this.settings.createGroup("When Full");
/*     */ 
/*     */ 
/*     */   
/*  43 */   public final Setting<List<class_2248>> targetBlocks = this.sgGeneral.add((Setting)((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder())
/*  44 */       .name("target-blocks"))
/*  45 */       .description("The target blocks to mine."))
/*  46 */       .defaultValue(new class_2248[] { class_2246.field_10442, class_2246.field_29029
/*  47 */         }).filter(this::filterBlocks)
/*  48 */       .build());
/*     */ 
/*     */   
/*  51 */   public final Setting<List<class_1792>> targetItems = this.sgGeneral.add((Setting)((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder())
/*  52 */       .name("target-items"))
/*  53 */       .description("The target items to collect."))
/*  54 */       .defaultValue(new class_1792[] { class_1802.field_8477
/*  55 */         }).build());
/*     */ 
/*     */   
/*  58 */   public final Setting<List<class_2248>> repairBlocks = this.sgGeneral.add((Setting)((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder())
/*  59 */       .name("repair-blocks"))
/*  60 */       .description("The repair blocks to mine."))
/*  61 */       .defaultValue(new class_2248[] { class_2246.field_10418, class_2246.field_10080, class_2246.field_10213
/*  62 */         }).filter(this::filterBlocks)
/*  63 */       .build());
/*     */ 
/*     */   
/*  66 */   public final Setting<Double> startRepairing = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  67 */       .name("repair-threshold"))
/*  68 */       .description("The durability percentage at which to start repairing."))
/*  69 */       .defaultValue(20.0D)
/*  70 */       .range(1.0D, 99.0D)
/*  71 */       .sliderRange(1.0D, 99.0D)
/*  72 */       .build());
/*     */ 
/*     */   
/*  75 */   public final Setting<Double> startMining = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  76 */       .name("mine-threshold"))
/*  77 */       .description("The durability percentage at which to start mining."))
/*  78 */       .defaultValue(70.0D)
/*  79 */       .range(1.0D, 99.0D)
/*  80 */       .sliderRange(1.0D, 99.0D)
/*  81 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   public final Setting<Boolean> walkHome = this.sgWhenFull.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  87 */       .name("walk-home"))
/*  88 */       .description("Will walk 'home' when your inventory is full."))
/*  89 */       .defaultValue(Boolean.valueOf(false)))
/*  90 */       .build());
/*     */ 
/*     */   
/*  93 */   public final Setting<Boolean> logOut = this.sgWhenFull.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  94 */       .name("log-out"))
/*  95 */       .description("Logs out when your inventory is full. Will walk home FIRST if walk home is enabled."))
/*  96 */       .defaultValue(Boolean.valueOf(false)))
/*  97 */       .build());
/*     */ 
/*     */   
/* 100 */   private final IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
/* 101 */   private final Settings baritoneSettings = BaritoneAPI.getSettings();
/*     */   
/* 103 */   private final class_2338.class_2339 homePos = new class_2338.class_2339();
/*     */   
/*     */   private boolean prevMineScanDroppedItems;
/*     */   private boolean repairing;
/*     */   
/*     */   public InfinityMiner() {
/* 109 */     super(Categories.World, "infinity-miner", "Allows you to essentially mine forever by mining repair blocks when the durability gets low. Needs a mending pickaxe.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/* 114 */     this.prevMineScanDroppedItems = ((Boolean)this.baritoneSettings.mineScanDroppedItems.value).booleanValue();
/* 115 */     this.baritoneSettings.mineScanDroppedItems.value = Boolean.valueOf(true);
/* 116 */     this.homePos.method_10101((class_2382)this.mc.field_1724.method_24515());
/* 117 */     this.repairing = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 122 */     this.baritone.getPathingBehavior().cancelEverything();
/* 123 */     this.baritoneSettings.mineScanDroppedItems.value = Boolean.valueOf(this.prevMineScanDroppedItems);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 128 */     if (isFull()) {
/* 129 */       if (((Boolean)this.walkHome.get()).booleanValue())
/* 130 */       { if (isBaritoneNotWalking())
/* 131 */         { info("Walking home.", new Object[0]);
/* 132 */           this.baritone.getCustomGoalProcess().setGoalAndPath((Goal)new GoalBlock((class_2338)this.homePos)); }
/*     */         
/* 134 */         else if (this.mc.field_1724.method_24515().equals(this.homePos) && ((Boolean)this.logOut.get()).booleanValue()) { logOut(); }
/*     */          }
/* 136 */       else if (((Boolean)this.logOut.get()).booleanValue()) { logOut(); }
/*     */       else
/* 138 */       { info("Inventory full, stopping process.", new Object[0]);
/* 139 */         toggle(); }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 145 */     if (!findPickaxe()) {
/* 146 */       error("Could not find a usable mending pickaxe.", new Object[0]);
/* 147 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/* 151 */     if (!checkThresholds()) {
/* 152 */       error("Start mining value can't be lower than start repairing value.", new Object[0]);
/* 153 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/* 157 */     if (this.repairing) {
/* 158 */       if (!needsRepair()) {
/* 159 */         warning("Finished repairing, going back to mining.", new Object[0]);
/* 160 */         this.repairing = false;
/* 161 */         this.baritoneSettings.mineScanDroppedItems.value = Boolean.valueOf(true);
/* 162 */         mineTargetBlocks();
/*     */         
/*     */         return;
/*     */       } 
/* 166 */       if (isBaritoneNotMining()) mineRepairBlocks();
/*     */     
/*     */     } else {
/* 169 */       if (needsRepair()) {
/* 170 */         warning("Pickaxe needs repair, beginning repair process", new Object[0]);
/* 171 */         this.repairing = true;
/* 172 */         this.baritoneSettings.mineScanDroppedItems.value = Boolean.valueOf(false);
/* 173 */         mineRepairBlocks();
/*     */         
/*     */         return;
/*     */       } 
/* 177 */       if (isBaritoneNotMining()) mineTargetBlocks(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean needsRepair() {
/* 182 */     class_1799 itemStack = this.mc.field_1724.method_6047();
/* 183 */     double toolPercentage = ((itemStack.method_7936() - itemStack.method_7919()) * 100.0F / itemStack.method_7936());
/* 184 */     return (toolPercentage <= ((Double)this.startMining.get()).doubleValue() && (toolPercentage <= ((Double)this.startRepairing.get()).doubleValue() || this.repairing));
/*     */   }
/*     */   
/*     */   private boolean findPickaxe() {
/* 188 */     Predicate<class_1799> pickaxePredicate = stack -> (stack.method_31573(class_3489.field_42614) && Utils.hasEnchantment(stack, class_1893.field_9101) && !Utils.hasEnchantment(stack, class_1893.field_9099));
/*     */ 
/*     */     
/* 191 */     FindItemResult bestPick = InvUtils.findInHotbar(pickaxePredicate);
/*     */     
/* 193 */     if (bestPick.isOffhand()) { InvUtils.shiftClick().fromOffhand().toHotbar(this.mc.field_1724.method_31548().method_67532()); }
/* 194 */     else if (bestPick.isHotbar()) { InvUtils.swap(bestPick.slot(), false); }
/*     */     
/* 196 */     return InvUtils.testInMainHand(pickaxePredicate);
/*     */   }
/*     */   
/*     */   private boolean checkThresholds() {
/* 200 */     return (((Double)this.startRepairing.get()).doubleValue() < ((Double)this.startMining.get()).doubleValue());
/*     */   }
/*     */   
/*     */   private void mineTargetBlocks() {
/* 204 */     class_2248[] array = new class_2248[((List)this.targetBlocks.get()).size()];
/*     */     
/* 206 */     this.baritone.getPathingBehavior().cancelEverything();
/* 207 */     this.baritone.getMineProcess().mine((class_2248[])((List)this.targetBlocks.get()).toArray((Object[])array));
/*     */   }
/*     */   
/*     */   private void mineRepairBlocks() {
/* 211 */     class_2248[] array = new class_2248[((List)this.repairBlocks.get()).size()];
/*     */     
/* 213 */     this.baritone.getPathingBehavior().cancelEverything();
/* 214 */     this.baritone.getMineProcess().mine((class_2248[])((List)this.repairBlocks.get()).toArray((Object[])array));
/*     */   }
/*     */   
/*     */   private void logOut() {
/* 218 */     toggle();
/* 219 */     this.mc.field_1724.field_3944.method_52787((class_2596)new class_2661((class_2561)class_2561.method_43470("[Infinity Miner] Inventory is full.")));
/*     */   }
/*     */   
/*     */   private boolean isBaritoneNotMining() {
/* 223 */     return !(this.baritone.getPathingControlManager().mostRecentInControl().orElse(null) instanceof baritone.api.process.IMineProcess);
/*     */   }
/*     */   
/*     */   private boolean isBaritoneNotWalking() {
/* 227 */     return !(this.baritone.getPathingControlManager().mostRecentInControl().orElse(null) instanceof baritone.api.process.ICustomGoalProcess);
/*     */   }
/*     */   
/*     */   private boolean filterBlocks(class_2248 block) {
/* 231 */     return (block != class_2246.field_10124 && block.method_9564().method_26214((class_1922)this.mc.field_1687, null) != -1.0F && !(block instanceof net.minecraft.class_2404));
/*     */   }
/*     */   
/*     */   private boolean isFull() {
/* 235 */     for (int i = 0; i <= 35; i++) {
/* 236 */       class_1799 itemStack = this.mc.field_1724.method_31548().method_5438(i);
/* 237 */       if (itemStack.method_7960()) return false;
/*     */       
/* 239 */       for (class_1792 item : this.targetItems.get()) {
/* 240 */         if (itemStack.method_7909() == item && itemStack.method_7947() < itemStack.method_7914()) {
/* 241 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 246 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\InfinityMiner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */