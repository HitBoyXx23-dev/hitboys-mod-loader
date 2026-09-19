/*     */ package meteordevelopment.meteorclient.systems.modules.player;
/*     */ 
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ItemListSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1893;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_9334;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoMend
/*     */   extends Module
/*     */ {
/*  26 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<List<class_1792>> blacklist;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> force;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> autoDisable;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean didMove;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AutoMend() {
/*  52 */     super(Categories.Player, "auto-mend", "Automatically replaces items in your offhand with mending when fully repaired.");
/*     */     this.blacklist = this.sgGeneral.add((Setting)((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder()).name("blacklist")).description("Item blacklist.")).filter(item -> (item.method_57347().method_58694(class_9334.field_49629) != null)).build());
/*     */     this.force = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("force")).description("Replaces item in offhand even if there is some other non-repairable item.")).defaultValue(Boolean.valueOf(false))).build());
/*     */     this.autoDisable = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("auto-disable")).description("Automatically disables when there are no more items to repair.")).defaultValue(Boolean.valueOf(true))).build());
/*     */   } public void onActivate() {
/*  57 */     this.didMove = false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/*  62 */     if (shouldWait())
/*     */       return; 
/*  64 */     int slot = getSlot();
/*     */     
/*  66 */     if (slot == -1) {
/*  67 */       if (((Boolean)this.autoDisable.get()).booleanValue()) {
/*  68 */         info("Repaired all items, disabling", new Object[0]);
/*     */         
/*  70 */         if (this.didMove) {
/*  71 */           int emptySlot = getEmptySlot();
/*  72 */           InvUtils.move().fromOffhand().to(emptySlot);
/*     */         } 
/*     */         
/*  75 */         toggle();
/*     */       } 
/*     */     } else {
/*  78 */       InvUtils.move().from(slot).toOffhand();
/*  79 */       this.didMove = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldWait() {
/*  84 */     class_1799 itemStack = this.mc.field_1724.method_6079();
/*     */     
/*  86 */     if (itemStack.method_7960()) return false;
/*     */     
/*  88 */     if (Utils.hasEnchantments(itemStack, new class_5321[] { class_1893.field_9101 })) {
/*  89 */       return (itemStack.method_7919() != 0);
/*     */     }
/*     */     
/*  92 */     return !((Boolean)this.force.get()).booleanValue();
/*     */   }
/*     */   
/*     */   private int getSlot() {
/*  96 */     for (int i = 0; i < this.mc.field_1724.method_31548().method_67533().size(); i++) {
/*  97 */       class_1799 itemStack = this.mc.field_1724.method_31548().method_5438(i);
/*  98 */       if (!((List)this.blacklist.get()).contains(itemStack.method_7909()))
/*     */       {
/* 100 */         if (Utils.hasEnchantments(itemStack, new class_5321[] { class_1893.field_9101 }) && itemStack.method_7919() > 0) {
/* 101 */           return i;
/*     */         }
/*     */       }
/*     */     } 
/* 105 */     return -1;
/*     */   }
/*     */   
/*     */   private int getEmptySlot() {
/* 109 */     for (int i = 0; i < this.mc.field_1724.method_31548().method_67533().size(); i++) {
/* 110 */       if (this.mc.field_1724.method_31548().method_5438(i).method_7960()) return i;
/*     */     
/*     */     } 
/* 113 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\AutoMend.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */