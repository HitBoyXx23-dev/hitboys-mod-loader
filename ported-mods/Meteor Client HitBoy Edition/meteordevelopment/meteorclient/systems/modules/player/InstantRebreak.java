/*     */ package meteordevelopment.meteorclient.systems.modules.player;
/*     */ import meteordevelopment.meteorclient.events.entity.player.StartBreakingBlockEvent;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2846;
/*     */ import net.minecraft.class_3489;
/*     */ 
/*     */ public class InstantRebreak extends Module {
/*  27 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  28 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*     */   
/*  30 */   private final Setting<Integer> tickDelay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  31 */       .name("delay"))
/*  32 */       .description("The delay between break attempts."))
/*  33 */       .defaultValue(Integer.valueOf(0)))
/*  34 */       .min(0)
/*  35 */       .sliderMax(20)
/*  36 */       .build());
/*     */ 
/*     */   
/*  39 */   private final Setting<Boolean> pick = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  40 */       .name("only-pick"))
/*  41 */       .description("Only tries to mine the block if you are holding a pickaxe."))
/*  42 */       .defaultValue(Boolean.valueOf(true)))
/*  43 */       .build());
/*     */ 
/*     */   
/*  46 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  47 */       .name("rotate"))
/*  48 */       .description("Faces the block being mined server side."))
/*  49 */       .defaultValue(Boolean.valueOf(true)))
/*  50 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private final Setting<Boolean> render = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  56 */       .name("render"))
/*  57 */       .description("Renders an overlay on the block being broken."))
/*  58 */       .defaultValue(Boolean.valueOf(true)))
/*  59 */       .build());
/*     */ 
/*     */   
/*  62 */   private final Setting<ShapeMode> shapeMode = this.sgRender.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  63 */       .name("shape-mode"))
/*  64 */       .description("How the shapes are rendered."))
/*  65 */       .defaultValue(ShapeMode.Both))
/*  66 */       .build());
/*     */ 
/*     */   
/*  69 */   private final Setting<SettingColor> sideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  70 */       .name("side-color"))
/*  71 */       .description("The color of the sides of the blocks being rendered."))
/*  72 */       .defaultValue(new SettingColor(204, 0, 0, 10))
/*  73 */       .build());
/*     */ 
/*     */   
/*  76 */   private final Setting<SettingColor> lineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  77 */       .name("line-color"))
/*  78 */       .description("The color of the lines of the blocks being rendered."))
/*  79 */       .defaultValue(new SettingColor(204, 0, 0, 255))
/*  80 */       .build());
/*     */ 
/*     */   
/*  83 */   public final class_2338.class_2339 blockPos = new class_2338.class_2339(0, -2147483648, 0);
/*     */   private int ticks;
/*     */   private class_2350 direction;
/*     */   
/*     */   public InstantRebreak() {
/*  88 */     super(Categories.Player, "instant-rebreak", "Instantly re-breaks blocks in the same position.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  93 */     this.ticks = 0;
/*  94 */     this.blockPos.method_10103(0, -1, 0);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onStartBreakingBlock(StartBreakingBlockEvent event) {
/*  99 */     this.direction = event.direction;
/* 100 */     this.blockPos.method_10101((class_2382)event.blockPos);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 105 */     if (this.ticks >= ((Integer)this.tickDelay.get()).intValue()) {
/* 106 */       this.ticks = 0;
/*     */       
/* 108 */       if (shouldMine()) {
/* 109 */         if (((Boolean)this.rotate.get()).booleanValue()) { Rotations.rotate(Rotations.getYaw((class_2338)this.blockPos), Rotations.getPitch((class_2338)this.blockPos), this::sendPacket); }
/* 110 */         else { sendPacket(); }
/*     */         
/* 112 */         this.mc.method_1562().method_52787((class_2596)new class_2879(class_1268.field_5808));
/*     */       } 
/*     */     } else {
/* 115 */       this.ticks++;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendPacket() {
/* 120 */     this.mc.field_1761.method_41931(this.mc.field_1687, sequence -> new class_2846(class_2846.class_2847.field_12973, (class_2338)this.blockPos, (this.direction == null) ? class_2350.field_11036 : this.direction, sequence));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldMine() {
/* 126 */     if (this.mc.field_1687.method_31606((class_2338)this.blockPos) || !BlockUtils.canBreak((class_2338)this.blockPos)) return false;
/*     */     
/* 128 */     return (!((Boolean)this.pick.get()).booleanValue() || this.mc.field_1724.method_6047().method_31573(class_3489.field_42614));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/* 133 */     if (!((Boolean)this.render.get()).booleanValue() || !shouldMine())
/*     */       return; 
/* 135 */     event.renderer.box((class_2338)this.blockPos, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\InstantRebreak.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */