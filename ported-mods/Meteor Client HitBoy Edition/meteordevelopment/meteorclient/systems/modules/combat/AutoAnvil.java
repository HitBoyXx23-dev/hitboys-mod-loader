/*     */ package meteordevelopment.meteorclient.systems.modules.combat;
/*     */ 
/*     */ import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*     */ import meteordevelopment.meteorclient.utils.entity.SortPriority;
/*     */ import meteordevelopment.meteorclient.utils.entity.TargetUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1304;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ 
/*     */ public class AutoAnvil
/*     */   extends Module
/*     */ {
/*  30 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */ 
/*     */ 
/*     */   
/*  34 */   private final Setting<Double> range = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  35 */       .name("target-range"))
/*  36 */       .description("The radius in which players get targeted."))
/*  37 */       .defaultValue(4.0D)
/*  38 */       .min(0.0D)
/*  39 */       .sliderMax(5.0D)
/*  40 */       .build());
/*     */ 
/*     */   
/*  43 */   private final Setting<SortPriority> priority = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  44 */       .name("target-priority"))
/*  45 */       .description("How to select the player to target."))
/*  46 */       .defaultValue(SortPriority.LowestHealth))
/*  47 */       .build());
/*     */ 
/*     */   
/*  50 */   private final Setting<Integer> height = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  51 */       .name("height"))
/*  52 */       .description("The height to place anvils at."))
/*  53 */       .defaultValue(Integer.valueOf(2)))
/*  54 */       .range(0, 5)
/*  55 */       .sliderMax(5)
/*  56 */       .build());
/*     */ 
/*     */   
/*  59 */   private final Setting<Integer> delay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  60 */       .name("delay"))
/*  61 */       .description("The delay in between anvil placements."))
/*  62 */       .defaultValue(Integer.valueOf(10)))
/*  63 */       .min(0)
/*  64 */       .sliderMax(50)
/*  65 */       .build());
/*     */ 
/*     */   
/*  68 */   private final Setting<Boolean> placeButton = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  69 */       .name("place-at-feet"))
/*  70 */       .description("Automatically places a button or pressure plate at the targets feet to break the anvils."))
/*  71 */       .defaultValue(Boolean.valueOf(true)))
/*  72 */       .build());
/*     */ 
/*     */   
/*  75 */   private final Setting<Boolean> multiPlace = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  76 */       .name("multi-place"))
/*  77 */       .description("Places multiple anvils at once."))
/*  78 */       .defaultValue(Boolean.valueOf(true)))
/*  79 */       .build());
/*     */ 
/*     */   
/*  82 */   private final Setting<Boolean> toggleOnBreak = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  83 */       .name("toggle-on-break"))
/*  84 */       .description("Toggles when the target's helmet slot is empty."))
/*  85 */       .defaultValue(Boolean.valueOf(false)))
/*  86 */       .build());
/*     */ 
/*     */   
/*  89 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  90 */       .name("rotate"))
/*  91 */       .description("Automatically rotates towards the position anvils/pressure plates/buttons are placed."))
/*  92 */       .defaultValue(Boolean.valueOf(true)))
/*  93 */       .build());
/*     */   
/*     */   private class_1657 target;
/*     */   
/*     */   private int timer;
/*     */   
/*     */   public AutoAnvil() {
/* 100 */     super(Categories.Combat, "auto-anvil", "Automatically places anvils above players to destroy helmets.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/* 105 */     this.timer = 0;
/* 106 */     this.target = null;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onOpenScreen(OpenScreenEvent event) {
/* 111 */     if (event.screen instanceof net.minecraft.class_471) event.cancel();
/*     */   
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 117 */     if (((Boolean)this.toggleOnBreak.get()).booleanValue() && this.target != null && this.target.method_6118(class_1304.field_6169).method_7960()) {
/* 118 */       error("Target head slot is empty... disabling.", new Object[0]);
/* 119 */       toggle();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 124 */     if (TargetUtils.isBadTarget(this.target, ((Double)this.range.get()).doubleValue())) {
/* 125 */       this.target = TargetUtils.getPlayerTarget(((Double)this.range.get()).doubleValue(), (SortPriority)this.priority.get());
/* 126 */       if (TargetUtils.isBadTarget(this.target, ((Double)this.range.get()).doubleValue()))
/*     */         return; 
/*     */     } 
/* 129 */     if (((Boolean)this.placeButton.get()).booleanValue()) {
/* 130 */       FindItemResult floorBlock = InvUtils.findInHotbar(itemStack -> (class_2248.method_9503(itemStack.method_7909()) instanceof net.minecraft.class_2231 || class_2248.method_9503(itemStack.method_7909()) instanceof net.minecraft.class_2269));
/* 131 */       BlockUtils.place(this.target.method_24515(), floorBlock, ((Boolean)this.rotate.get()).booleanValue(), 0, false);
/*     */     } 
/*     */     
/* 134 */     if (this.timer >= ((Integer)this.delay.get()).intValue())
/* 135 */     { this.timer = 0;
/*     */       
/* 137 */       FindItemResult anvil = InvUtils.findInHotbar(itemStack -> class_2248.method_9503(itemStack.method_7909()) instanceof net.minecraft.class_2199);
/* 138 */       if (!anvil.found())
/*     */         return; 
/* 140 */       for (int i = ((Integer)this.height.get()).intValue(); i > 1; i--) {
/* 141 */         class_2338 blockPos = this.target.method_24515().method_10084().method_10069(0, i, 0);
/*     */         
/* 143 */         for (int j = 0; j < i && 
/* 144 */           this.mc.field_1687.method_8320(this.target.method_24515().method_10086(j + 1)).method_45474(); j++);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 149 */         if (BlockUtils.place(blockPos, anvil, ((Boolean)this.rotate.get()).booleanValue(), 0) && !((Boolean)this.multiPlace.get()).booleanValue())
/*     */           break; 
/*     */       }  }
/* 152 */     else { this.timer++; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public String getInfoString() {
/* 158 */     return EntityUtils.getName((class_1297)this.target);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\AutoAnvil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */