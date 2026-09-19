/*     */ package meteordevelopment.meteorclient.systems.modules.movement;
/*     */ 
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.mixin.AbstractBlockAccessor;
/*     */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ public class Anchor
/*     */   extends Module {
/*  21 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  23 */   private final Setting<Integer> maxHeight = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  24 */       .name("max-height"))
/*  25 */       .description("The maximum height Anchor will work at."))
/*  26 */       .defaultValue(Integer.valueOf(10)))
/*  27 */       .range(0, 255)
/*  28 */       .sliderMax(20)
/*  29 */       .build());
/*     */ 
/*     */   
/*  32 */   private final Setting<Integer> minPitch = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  33 */       .name("min-pitch"))
/*  34 */       .description("The minimum pitch at which anchor will work."))
/*  35 */       .defaultValue(Integer.valueOf(0)))
/*  36 */       .range(-90, 90)
/*  37 */       .sliderRange(-90, 90)
/*  38 */       .build());
/*     */ 
/*     */   
/*  41 */   private final Setting<Boolean> cancelMove = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  42 */       .name("cancel-jump-in-hole"))
/*  43 */       .description("Prevents you from jumping when Anchor is active and Min Pitch is met."))
/*  44 */       .defaultValue(Boolean.valueOf(false)))
/*  45 */       .build());
/*     */ 
/*     */   
/*  48 */   private final Setting<Boolean> pull = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  49 */       .name("pull"))
/*  50 */       .description("The pull strength of Anchor."))
/*  51 */       .defaultValue(Boolean.valueOf(false)))
/*  52 */       .build());
/*     */ 
/*     */   
/*  55 */   private final Setting<Double> pullSpeed = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  56 */       .name("pull-speed"))
/*  57 */       .description("How fast to pull towards the hole in blocks per second."))
/*  58 */       .defaultValue(0.3D)
/*  59 */       .min(0.0D)
/*  60 */       .sliderMax(5.0D)
/*  61 */       .build());
/*     */ 
/*     */   
/*  64 */   private final class_2338.class_2339 blockPos = new class_2338.class_2339();
/*     */   private boolean wasInHole;
/*     */   private boolean foundHole;
/*     */   private int holeX;
/*     */   private int holeZ;
/*     */   public boolean cancelJump;
/*     */   public boolean controlMovement;
/*     */   public double deltaX;
/*     */   public double deltaZ;
/*     */   
/*     */   public Anchor() {
/*  75 */     super(Categories.Movement, "anchor", "Helps you get into holes by stopping your movement completely over a hole.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  80 */     this.wasInHole = false;
/*  81 */     this.holeX = this.holeZ = 0;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPreTick(TickEvent.Pre event) {
/*  86 */     this.cancelJump = (this.foundHole && ((Boolean)this.cancelMove.get()).booleanValue() && this.mc.field_1724.method_36455() >= ((Integer)this.minPitch.get()).intValue());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPostTick(TickEvent.Post event) {
/*  91 */     this.controlMovement = false;
/*     */     
/*  93 */     int x = class_3532.method_15357(this.mc.field_1724.method_23317());
/*  94 */     int y = class_3532.method_15357(this.mc.field_1724.method_23318());
/*  95 */     int z = class_3532.method_15357(this.mc.field_1724.method_23321());
/*     */     
/*  97 */     if (isHole(x, y, z)) {
/*  98 */       this.wasInHole = true;
/*  99 */       this.holeX = x;
/* 100 */       this.holeZ = z;
/*     */       
/*     */       return;
/*     */     } 
/* 104 */     if (this.wasInHole && this.holeX == x && this.holeZ == z)
/* 105 */       return;  if (this.wasInHole) this.wasInHole = false;
/*     */     
/* 107 */     if (this.mc.field_1724.method_36455() < ((Integer)this.minPitch.get()).intValue())
/*     */       return; 
/* 109 */     this.foundHole = false;
/* 110 */     double holeX = 0.0D;
/* 111 */     double holeZ = 0.0D;
/*     */     
/* 113 */     int i = 0;
/* 114 */     y--;
/* 115 */     for (; i < ((Integer)this.maxHeight.get()).intValue() && y > this.mc.field_1687.method_31607() && isAir(x, y, z); i++) {
/*     */       
/* 117 */       if (isHole(x, y, z)) {
/* 118 */         this.foundHole = true;
/* 119 */         holeX = x + 0.5D;
/* 120 */         holeZ = z + 0.5D;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 125 */     if (this.foundHole) {
/* 126 */       this.controlMovement = true;
/* 127 */       this.deltaX = class_3532.method_15350(holeX - this.mc.field_1724.method_23317(), -0.05D, 0.05D);
/* 128 */       this.deltaZ = class_3532.method_15350(holeZ - this.mc.field_1724.method_23321(), -0.05D, 0.05D);
/*     */       
/* 130 */       ((IVec3d)this.mc.field_1724.method_18798()).meteor$set(this.deltaX, (this.mc.field_1724.method_18798()).field_1351 - (((Boolean)this.pull.get()).booleanValue() ? ((Double)this.pullSpeed.get()).doubleValue() : 0.0D), this.deltaZ);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isHole(int x, int y, int z) {
/* 135 */     return (isHoleBlock(x, y - 1, z) && 
/* 136 */       isHoleBlock(x + 1, y, z) && 
/* 137 */       isHoleBlock(x - 1, y, z) && 
/* 138 */       isHoleBlock(x, y, z + 1) && 
/* 139 */       isHoleBlock(x, y, z - 1));
/*     */   }
/*     */   
/*     */   private boolean isHoleBlock(int x, int y, int z) {
/* 143 */     this.blockPos.method_10103(x, y, z);
/* 144 */     class_2248 block = this.mc.field_1687.method_8320((class_2338)this.blockPos).method_26204();
/* 145 */     return (block == class_2246.field_9987 || block == class_2246.field_10540 || block == class_2246.field_22423);
/*     */   }
/*     */   
/*     */   private boolean isAir(int x, int y, int z) {
/* 149 */     this.blockPos.method_10103(x, y, z);
/* 150 */     return !((AbstractBlockAccessor)this.mc.field_1687.method_8320((class_2338)this.blockPos).method_26204()).meteor$isCollidable();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\Anchor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */