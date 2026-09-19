/*     */ package meteordevelopment.meteorclient.systems.modules.player;
/*     */ 
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.mixin.FishingBobberEntityAccessor;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.world.TickRate;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1536;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1893;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AutoFish
/*     */   extends Module
/*     */ {
/*  26 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  28 */   private final Setting<Boolean> autoSwitch = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  29 */       .name("auto-switch"))
/*  30 */       .description("Automatically switch to a fishing rod."))
/*  31 */       .defaultValue(Boolean.valueOf(true)))
/*  32 */       .build());
/*     */ 
/*     */   
/*  35 */   private final Setting<Boolean> antiBreak = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  36 */       .name("anti-break"))
/*  37 */       .description("Avoid using rods that would break if they were cast."))
/*  38 */       .defaultValue(Boolean.valueOf(true)))
/*  39 */       .build());
/*     */ 
/*     */   
/*  42 */   private final Setting<Boolean> autoCast = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  43 */       .name("auto-cast"))
/*  44 */       .description("Automatically cast the fishing rod."))
/*  45 */       .defaultValue(Boolean.valueOf(true)))
/*  46 */       .build());
/*     */ 
/*     */   
/*  49 */   private final Setting<Integer> castDelay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  50 */       .name("cast-delay"))
/*  51 */       .description("How long to wait between recasts if the bobber fails to land in water."))
/*  52 */       .defaultValue(Integer.valueOf(14)))
/*  53 */       .min(1)
/*  54 */       .sliderMax(60)
/*  55 */       .build());
/*     */ 
/*     */   
/*  58 */   private final Setting<Integer> castDelayVariance = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  59 */       .name("cast-delay-variance"))
/*  60 */       .description("Maximum amount of randomness added to cast delay."))
/*  61 */       .defaultValue(Integer.valueOf(0)))
/*  62 */       .min(0)
/*  63 */       .sliderMax(30)
/*  64 */       .build());
/*     */ 
/*     */   
/*  67 */   private final Setting<Integer> catchDelay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  68 */       .name("catch-delay"))
/*  69 */       .description("How long to wait after hooking a fish to reel it in."))
/*  70 */       .defaultValue(Integer.valueOf(6)))
/*  71 */       .min(1)
/*  72 */       .sliderMax(20)
/*  73 */       .build());
/*     */ 
/*     */   
/*  76 */   private final Setting<Integer> catchDelayVariance = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  77 */       .name("catch-delay-variance"))
/*  78 */       .description("Maximum amount of randomness added to catch delay."))
/*  79 */       .defaultValue(Integer.valueOf(0)))
/*  80 */       .min(0)
/*  81 */       .sliderMax(10)
/*  82 */       .build());
/*     */   private double castDelayLeft;
/*     */   
/*     */   public AutoFish() {
/*  86 */     super(Categories.Player, "auto-fish", "Automatically fishes for you.");
/*     */ 
/*     */     
/*  89 */     this.castDelayLeft = 0.0D;
/*  90 */     this.catchDelayLeft = 0.0D;
/*  91 */     this.wasHooked = false;
/*     */   }
/*     */   private double catchDelayLeft; private boolean wasHooked;
/*     */   public void onActivate() {
/*  95 */     this.castDelayLeft = 0.0D;
/*  96 */     this.catchDelayLeft = 0.0D;
/*     */     
/*  98 */     this.wasHooked = false;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 103 */     int bestRodSlot = findBestRod();
/*     */     
/* 105 */     if (((Boolean)this.autoSwitch.get()).booleanValue() && bestRodSlot != -1 && this.mc.field_1724.method_31548().method_67532() != bestRodSlot) {
/* 106 */       InvUtils.swap(bestRodSlot, false);
/*     */     }
/*     */     
/* 109 */     if (!(this.mc.field_1724.method_6047().method_7909() instanceof net.minecraft.class_1787))
/*     */       return; 
/* 111 */     tryCast();
/* 112 */     tryCatch();
/*     */   }
/*     */   
/*     */   private void tryCast() {
/* 116 */     if (this.mc.field_1724.field_7513 != null)
/*     */       return; 
/* 118 */     if (!((Boolean)this.autoCast.get()).booleanValue())
/*     */       return; 
/* 120 */     if (this.castDelayLeft > 0.0D) {
/* 121 */       this.castDelayLeft -= TickRate.INSTANCE.getTickRate() / 20.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 125 */     useRod();
/*     */   }
/*     */   
/*     */   private void tryCatch() {
/* 129 */     if (this.mc.field_1724.field_7513 == null)
/* 130 */       return;  if (this.mc.field_1724.field_7513.method_26957() != null) {
/* 131 */       useRod();
/*     */       
/*     */       return;
/*     */     } 
/* 135 */     if (this.mc.field_1724.field_7513.field_7175 != class_1536.class_1537.field_7179)
/*     */       return; 
/* 137 */     if (!this.wasHooked) {
/* 138 */       if (((FishingBobberEntityAccessor)this.mc.field_1724.field_7513).meteor$hasCaughtFish()) {
/* 139 */         this.catchDelayLeft = randomizeDelay(((Integer)this.catchDelay.get()).intValue(), ((Integer)this.catchDelayVariance.get()).intValue());
/* 140 */         this.wasHooked = true;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 146 */     if (this.catchDelayLeft > 0.0D) {
/* 147 */       this.catchDelayLeft -= TickRate.INSTANCE.getTickRate() / 20.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 151 */     useRod();
/*     */   }
/*     */   
/*     */   private void useRod() {
/* 155 */     Utils.rightClick();
/* 156 */     this.wasHooked = false;
/* 157 */     this.castDelayLeft = randomizeDelay(((Integer)this.castDelay.get()).intValue(), ((Integer)this.castDelayVariance.get()).intValue());
/*     */   }
/*     */   
/*     */   private int findBestRod() {
/* 161 */     int bestSlot = -1;
/* 162 */     int bestScore = -1;
/*     */     
/* 164 */     for (int i = 0; i < 9; i++) {
/* 165 */       class_1799 stack = this.mc.field_1724.method_31548().method_5438(i);
/* 166 */       if (stack.method_7909() instanceof net.minecraft.class_1787 && (
/* 167 */         !((Boolean)this.antiBreak.get()).booleanValue() || stack.method_7919() != stack.method_7936() - 1)) {
/*     */         
/* 169 */         int score = 0;
/*     */         
/* 171 */         score += Utils.getEnchantmentLevel(stack, class_1893.field_9114);
/* 172 */         score += Utils.getEnchantmentLevel(stack, class_1893.field_9100);
/* 173 */         score += Utils.getEnchantmentLevel(stack, class_1893.field_9101);
/* 174 */         score += Utils.getEnchantmentLevel(stack, class_1893.field_9119);
/*     */         
/* 176 */         if (score > bestScore) {
/* 177 */           bestScore = score;
/* 178 */           bestSlot = i;
/*     */         } 
/*     */ 
/*     */         
/* 182 */         if (score == 10)
/*     */           break; 
/*     */       } 
/* 185 */     }  return bestSlot;
/*     */   }
/*     */   
/*     */   private double randomizeDelay(int delay, int variance) {
/* 189 */     if (variance == 0) return delay;
/*     */ 
/*     */     
/* 192 */     double scale = Math.sqrt(-2.0D * Math.log(Utils.random(1.0E-4D, 1.0D)));
/* 193 */     double angle = 6.283185307179586D * Utils.random(0.0D, 1.0D);
/* 194 */     double norm = scale * Math.cos(angle);
/*     */ 
/*     */     
/* 197 */     double MAX_SD = 3.0D;
/* 198 */     norm = Math.clamp(norm, -3.0D, 3.0D) / 3.0D;
/*     */     
/* 200 */     delay += Math.round((float)(norm * variance));
/* 201 */     return Math.max(1, delay);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\AutoFish.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */