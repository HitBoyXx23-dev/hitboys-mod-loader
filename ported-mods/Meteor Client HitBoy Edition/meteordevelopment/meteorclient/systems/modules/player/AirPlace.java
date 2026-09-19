/*     */ package meteordevelopment.meteorclient.systems.modules.player;
/*     */ import meteordevelopment.meteorclient.events.entity.player.InteractItemEvent;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1269;
/*     */ import net.minecraft.class_1747;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class AirPlace extends Module {
/*  28 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  29 */   private final SettingGroup sgRange = this.settings.createGroup("Range");
/*     */ 
/*     */ 
/*     */   
/*  33 */   private final Setting<Boolean> render = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  34 */       .name("render"))
/*  35 */       .description("Renders a block overlay where the obsidian will be placed."))
/*  36 */       .defaultValue(Boolean.valueOf(true)))
/*  37 */       .build());
/*     */ 
/*     */   
/*  40 */   private final Setting<ShapeMode> shapeMode = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  41 */       .name("shape-mode"))
/*  42 */       .description("How the shapes are rendered."))
/*  43 */       .defaultValue(ShapeMode.Both))
/*  44 */       .build());
/*     */ 
/*     */   
/*  47 */   private final Setting<SettingColor> sideColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  48 */       .name("side-color"))
/*  49 */       .description("The color of the sides of the blocks being rendered."))
/*  50 */       .defaultValue(new SettingColor(204, 0, 0, 10))
/*  51 */       .build());
/*     */ 
/*     */   
/*  54 */   private final Setting<SettingColor> lineColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  55 */       .name("line-color"))
/*  56 */       .description("The color of the lines of the blocks being rendered."))
/*  57 */       .defaultValue(new SettingColor(204, 0, 0, 255))
/*  58 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private final Setting<Boolean> customRange = this.sgRange.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  64 */       .name("custom-range"))
/*  65 */       .description("Use custom range for air place."))
/*  66 */       .defaultValue(Boolean.valueOf(false)))
/*  67 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> range;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_239 hitResult;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AirPlace() {
/*  83 */     super(Categories.Player, "air-place", "Places a block where your crosshair is pointing at.");
/*     */     Objects.requireNonNull(this.customRange);
/*     */     this.range = this.sgRange.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("range")).description("Custom range to place at.")).visible(this.customRange::get)).defaultValue(5.0D).min(0.0D).sliderMax(6.0D).build());
/*     */   } @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/*  88 */     if (!InvUtils.testInHands(this::placeable))
/*  89 */       return;  if (this.mc.field_1765 != null && this.mc.field_1765.method_17783() != class_239.class_240.field_1333)
/*     */       return; 
/*  91 */     double r = ((Boolean)this.customRange.get()).booleanValue() ? ((Double)this.range.get()).doubleValue() : this.mc.field_1724.method_55754();
/*  92 */     this.hitResult = this.mc.method_1560().method_5745(r, 0.0F, false);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onInteractItem(InteractItemEvent event) {
/*  97 */     class_239 class_2391 = this.hitResult; if (class_2391 instanceof class_3965) { class_3965 bhr = (class_3965)class_2391; if (placeable(this.mc.field_1724.method_5998(event.hand))) {
/*     */         
/*  99 */         class_2248 toPlace = class_2246.field_10540;
/* 100 */         class_1792 i = this.mc.field_1724.method_5998(event.hand).method_7909();
/* 101 */         if (i instanceof class_1747) { class_1747 blockItem = (class_1747)i; toPlace = blockItem.method_7711(); }
/* 102 */          if (!BlockUtils.canPlaceBlock(bhr.method_17777(), (i instanceof net.minecraft.class_1742 || i instanceof class_1747), toPlace))
/*     */           return; 
/* 104 */         class_243 hitPos = class_243.method_24953((class_2382)bhr.method_17777());
/*     */         
/* 106 */         class_3965 b = new class_3965(hitPos, this.mc.field_1724.method_5755().method_10153(), bhr.method_17777(), false);
/* 107 */         BlockUtils.interact(b, event.hand, true);
/*     */         
/* 109 */         event.toReturn = (class_1269)class_1269.field_5812;
/*     */         return;
/*     */       }  }
/*     */   
/*     */   } @EventHandler
/* 114 */   private void onRender(Render3DEvent event) { class_239 class_2391 = this.hitResult; if (class_2391 instanceof class_3965) { class_3965 bhr = (class_3965)class_2391; if ((this.mc.field_1765 == null || this.mc.field_1765
/* 115 */         .method_17783() == class_239.class_240.field_1333) && this.mc.field_1687
/* 116 */         .method_8320(bhr.method_17777()).method_45474() && 
/* 117 */         InvUtils.testInHands(this::placeable) && ((Boolean)this.render
/* 118 */         .get()).booleanValue()) {
/*     */         
/* 120 */         event.renderer.box(bhr.method_17777(), (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0);
/*     */         return;
/*     */       }  }
/*     */      } private boolean placeable(class_1799 stack) {
/* 124 */     class_1792 i = stack.method_7909();
/* 125 */     return (i instanceof class_1747 || i instanceof net.minecraft.class_1826 || i instanceof net.minecraft.class_1781 || i instanceof net.minecraft.class_1742);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\AirPlace.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */