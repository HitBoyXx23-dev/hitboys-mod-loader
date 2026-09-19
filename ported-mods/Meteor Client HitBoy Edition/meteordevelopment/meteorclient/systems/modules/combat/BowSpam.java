/*     */ package meteordevelopment.meteorclient.systems.modules.combat;
/*     */ 
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1764;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BowSpam
/*     */   extends Module
/*     */ {
/*  25 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  26 */   private final SettingGroup sgCrossbows = this.settings.createGroup("Crossbows");
/*     */   
/*  28 */   private final Setting<Integer> charge = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  29 */       .name("charge"))
/*  30 */       .description("How long to charge the bow before releasing in ticks."))
/*  31 */       .defaultValue(Integer.valueOf(5)))
/*  32 */       .range(4, 20)
/*  33 */       .sliderRange(4, 20)
/*  34 */       .build());
/*     */ 
/*     */   
/*  37 */   private final Setting<Boolean> onlyWhenHoldingRightClick = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  38 */       .name("when-holding-right-click"))
/*  39 */       .description("Works only when holding right click."))
/*  40 */       .defaultValue(Boolean.valueOf(false)))
/*  41 */       .build());
/*     */ 
/*     */   
/*  44 */   private final Setting<Boolean> spamCrossbows = this.sgCrossbows.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  45 */       .name("spam-crossbows"))
/*  46 */       .description("Whether to spam loaded crossbows; takes priority over charging bows."))
/*  47 */       .defaultValue(Boolean.valueOf(true)))
/*  48 */       .build());
/*     */ 
/*     */   
/*  51 */   private final Setting<Integer> crossbowDelay = this.sgCrossbows.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  52 */       .name("crossbow-delay"))
/*  53 */       .description("Delay between shooting crossbows in ticks."))
/*  54 */       .defaultValue(Integer.valueOf(10)))
/*  55 */       .sliderRange(0, 20)
/*  56 */       .min(0)
/*  57 */       .build());
/*     */ 
/*     */   
/*  60 */   private final Setting<Boolean> searchInventory = this.sgCrossbows.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  61 */       .name("search-inventory"))
/*  62 */       .description("Whether to search your inventory to find loaded crossbows."))
/*  63 */       .defaultValue(Boolean.valueOf(true)))
/*  64 */       .build());
/*     */   
/*     */   private boolean wasBow = false;
/*     */   
/*     */   private boolean wasHoldingRightClick = false;
/*  69 */   private int ticks = 0;
/*     */   
/*     */   public BowSpam() {
/*  72 */     super(Categories.Combat, "bow-spam", "Spams bows and crossbows.", new String[] { "auto-bow", "crossbow-spam", "auto-crossbow" });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  77 */     this.wasBow = false;
/*  78 */     this.wasHoldingRightClick = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/*  83 */     setPressed(false);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/*  88 */     FindItemResult crossbow = ((Boolean)this.searchInventory.get()).booleanValue() ? InvUtils.find(this::crossbow) : InvUtils.find(this::crossbow, 0, 8);
/*  89 */     if (((Boolean)this.spamCrossbows.get()).booleanValue() && crossbow.found()) {
/*  90 */       if (this.ticks >= ((Integer)this.crossbowDelay.get()).intValue()) {
/*  91 */         int slot = crossbow.slot();
/*  92 */         if (!crossbow.isHotbar()) {
/*  93 */           FindItemResult valid = InvUtils.find(stack -> (stack.method_7960() || stack.method_31574(class_1802.field_8399) || stack.method_31574(class_1802.field_8107)), 0, 8);
/*  94 */           if (!valid.found())
/*     */             return; 
/*  96 */           InvUtils.quickSwap().fromId(valid.slot()).to(crossbow.slot());
/*  97 */           slot = valid.slot();
/*     */         } 
/*     */         
/* 100 */         InvUtils.swap(slot, true);
/* 101 */         this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, class_1268.field_5808);
/* 102 */         InvUtils.swapBack();
/*     */         
/* 104 */         this.ticks = 0;
/*     */       } else {
/*     */         
/* 107 */         this.ticks++;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 113 */     if (!(this.mc.field_1724.method_31549()).field_7477 && !InvUtils.find(itemStack -> itemStack.method_7909() instanceof net.minecraft.class_1744).found()) {
/*     */       return;
/*     */     }
/* 116 */     if (!((Boolean)this.onlyWhenHoldingRightClick.get()).booleanValue() || this.mc.field_1690.field_1904.method_1434()) {
/* 117 */       boolean isBow = InvUtils.testInHands(new class_1792[] { class_1802.field_8102 });
/* 118 */       if (!isBow && this.wasBow) setPressed(false);
/*     */       
/* 120 */       this.wasBow = isBow;
/* 121 */       if (!isBow)
/*     */         return; 
/* 123 */       if (this.mc.field_1724.method_6048() >= ((Integer)this.charge.get()).intValue()) {
/* 124 */         this.mc.field_1761.method_2897((class_1657)this.mc.field_1724);
/*     */       } else {
/* 126 */         setPressed(true);
/*     */       } 
/*     */       
/* 129 */       this.wasHoldingRightClick = this.mc.field_1690.field_1904.method_1434();
/*     */     }
/* 131 */     else if (this.wasHoldingRightClick) {
/* 132 */       setPressed(false);
/* 133 */       this.wasHoldingRightClick = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setPressed(boolean pressed) {
/* 139 */     this.mc.field_1690.field_1904.method_23481(pressed);
/*     */   }
/*     */   
/*     */   private boolean crossbow(class_1799 stack) {
/* 143 */     return (stack.method_7909() instanceof class_1764 && class_1764.method_7781(stack));
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\BowSpam.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */