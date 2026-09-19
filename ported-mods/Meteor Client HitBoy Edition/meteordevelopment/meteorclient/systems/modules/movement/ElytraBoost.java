/*     */ package meteordevelopment.meteorclient.systems.modules.movement;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.events.entity.player.InteractItemEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.KeybindSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1269;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1671;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_3417;
/*     */ import net.minecraft.class_3419;
/*     */ import net.minecraft.class_9284;
/*     */ import net.minecraft.class_9334;
/*     */ 
/*     */ public class ElytraBoost
/*     */   extends Module {
/*  31 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  33 */   private final Setting<Boolean> dontConsumeFirework = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  34 */       .name("anti-consume"))
/*  35 */       .description("Prevents fireworks from being consumed when using Elytra Boost."))
/*  36 */       .defaultValue(Boolean.valueOf(true)))
/*  37 */       .build());
/*     */ 
/*     */   
/*  40 */   private final Setting<Integer> fireworkLevel = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  41 */       .name("firework-duration"))
/*  42 */       .description("The duration of the firework."))
/*  43 */       .defaultValue(Integer.valueOf(0)))
/*  44 */       .range(0, 255)
/*  45 */       .sliderMax(255)
/*  46 */       .build());
/*     */ 
/*     */   
/*  49 */   private final Setting<Boolean> playSound = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  50 */       .name("play-sound"))
/*  51 */       .description("Plays the firework sound when a boost is triggered."))
/*  52 */       .defaultValue(Boolean.valueOf(true)))
/*  53 */       .build());
/*     */ 
/*     */   
/*  56 */   private final Setting<Keybind> keybind = this.sgGeneral
/*  57 */     .add((Setting)((KeybindSetting.Builder)((KeybindSetting.Builder)(new KeybindSetting.Builder())
/*  58 */       .name("keybind"))
/*  59 */       .description("The keybind to boost."))
/*  60 */       .action(this::boost)
/*  61 */       .build());
/*     */ 
/*     */   
/*  64 */   private final List<class_1671> fireworks = new ArrayList<>();
/*     */   
/*     */   public ElytraBoost() {
/*  67 */     super(Categories.Movement, "elytra-boost", "Boosts your elytra as if you used a firework.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/*  72 */     this.fireworks.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onInteractItem(InteractItemEvent event) {
/*  77 */     class_1799 itemStack = this.mc.field_1724.method_5998(event.hand);
/*     */     
/*  79 */     if (itemStack.method_7909() instanceof net.minecraft.class_1781 && ((Boolean)this.dontConsumeFirework.get()).booleanValue()) {
/*  80 */       event.toReturn = (class_1269)class_1269.field_5811;
/*     */       
/*  82 */       boost();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/*  88 */     this.fireworks.removeIf(class_1297::method_31481);
/*     */   }
/*     */   
/*     */   private void boost() {
/*  92 */     if (!Utils.canUpdate())
/*     */       return; 
/*  94 */     if (this.mc.field_1724.method_6128() && this.mc.field_1755 == null) {
/*  95 */       class_1799 itemStack = class_1802.field_8639.method_7854();
/*  96 */       itemStack.method_57379(class_9334.field_49616, new class_9284(((Integer)this.fireworkLevel.get()).intValue(), ((class_9284)itemStack.method_58694(class_9334.field_49616)).comp_2392()));
/*     */       
/*  98 */       class_1671 entity = new class_1671((class_1937)this.mc.field_1687, itemStack, (class_1309)this.mc.field_1724);
/*  99 */       this.fireworks.add(entity);
/* 100 */       if (((Boolean)this.playSound.get()).booleanValue()) this.mc.field_1687.method_43129((class_1297)this.mc.field_1724, (class_1297)entity, class_3417.field_14702, class_3419.field_15256, 3.0F, 1.0F); 
/* 101 */       this.mc.field_1687.method_53875((class_1297)entity);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isFirework(class_1671 firework) {
/* 106 */     return (isActive() && this.fireworks.contains(firework));
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\ElytraBoost.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */