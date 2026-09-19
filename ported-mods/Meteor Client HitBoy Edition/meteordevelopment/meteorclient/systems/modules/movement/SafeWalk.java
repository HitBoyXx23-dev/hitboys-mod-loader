/*     */ package meteordevelopment.meteorclient.systems.modules.movement;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.entity.player.ClipAtLedgeEvent;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_10185;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2902;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class SafeWalk extends Module {
/*  25 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  26 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*     */   
/*  28 */   private final Setting<Integer> fallDistance = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  29 */       .name("minimum-fall-distance"))
/*  30 */       .description("The minimum number of blocks you are expected to fall before the module activates."))
/*  31 */       .defaultValue(Integer.valueOf(1)))
/*  32 */       .min(1)
/*  33 */       .build());
/*     */ 
/*     */   
/*  36 */   private final Setting<Boolean> sneak = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  37 */       .name("sneak"))
/*  38 */       .description("Sneak when approaching edge of block."))
/*  39 */       .defaultValue(Boolean.valueOf(false)))
/*  40 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> safeSneak;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> sneakSprint;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Double> edgeDistance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> renderEdgeDistance;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> renderPlayerBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SafeWalk() {
/*  86 */     super(Categories.Movement, "safe-walk", "Prevents you from walking off blocks."); Objects.requireNonNull(this.sneak); this.safeSneak = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("safe-sneak")).description("Prevent you from falling if sneak doesn't trigger correctly.")).defaultValue(Boolean.valueOf(true))).visible(this.sneak::get)).build()); Objects.requireNonNull(this.sneak); this.sneakSprint = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("sneak-on-sprint")).description("Sneak even when sprinting at the block edge.")).defaultValue(Boolean.valueOf(true))).visible(this.sneak::get)).build());
/*     */     Objects.requireNonNull(this.sneak);
/*     */     this.edgeDistance = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("edge-distance")).description("Distance offset before reaching an edge.")).defaultValue(0.3D).sliderRange(0.0D, 0.3D).decimalPlaces(2).visible(this.sneak::get)).build());
/*     */     Objects.requireNonNull(this.sneak);
/*     */     this.renderEdgeDistance = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("render")).description("Render edge distance helper.")).defaultValue(Boolean.valueOf(false))).visible(this.sneak::get)).build());
/*  91 */     this.renderPlayerBox = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("render-player-box")).description("Render player box helper.")).defaultValue(Boolean.valueOf(false))).visible(() -> (((Boolean)this.sneak.get()).booleanValue() && ((Boolean)this.renderEdgeDistance.get()).booleanValue()))).build()); } @EventHandler private void onClipAtLedge(ClipAtLedgeEvent event) { if (((Integer)this.fallDistance.get()).intValue() > 1) {
/*     */       
/*  93 */       int surface = this.mc.field_1687.method_8500(this.mc.field_1724.method_24515()).method_12032(class_2902.class_2903.field_13197).method_12603(this.mc.field_1724.method_31477() & 0xF, this.mc.field_1724.method_31479() & 0xF);
/*  94 */       if (this.mc.field_1724.method_31478() >= surface) {
/*  95 */         if (this.mc.field_1724.method_31478() - surface < ((Integer)this.fallDistance.get()).intValue()) {
/*     */           return;
/*     */         }
/*     */       } else {
/*  99 */         class_3965 raycastResult = this.mc.field_1687.method_17742(new class_3959(this.mc.field_1724.method_73189(), new class_243(this.mc.field_1724.method_23317(), this.mc.field_1687.method_31607(), this.mc.field_1724.method_23321()), class_3959.class_3960.field_17558, class_3959.class_242.field_36338, (class_1297)this.mc.field_1724));
/* 100 */         if (raycastResult.method_17783() != class_239.class_240.field_1333 && 
/* 101 */           (int)(this.mc.field_1724.method_23318() - raycastResult.method_17777().method_10084().method_10264()) < ((Integer)this.fallDistance.get()).intValue()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/* 106 */     if (((Boolean)this.sneak.get()).booleanValue())
/* 107 */     { boolean closeToEdge = false;
/* 108 */       boolean isSprinting = (!((Boolean)this.sneakSprint.get()).booleanValue() && this.mc.field_1690.field_1867.method_1434());
/*     */       
/* 110 */       class_238 playerBox = this.mc.field_1724.method_5829();
/* 111 */       class_238 adjustedBox = getAdjustedPlayerBox(playerBox);
/*     */       
/* 113 */       if (this.mc.field_1687.method_8587((class_1297)this.mc.field_1724, adjustedBox) && this.mc.field_1724.method_24828()) closeToEdge = true;
/*     */       
/* 115 */       if (!isSprinting) {
/* 116 */         if (closeToEdge) {
/* 117 */           this.mc.field_1724.field_3913
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 124 */             .field_54155 = new class_10185(this.mc.field_1724.field_3913.field_54155.comp_3159(), this.mc.field_1724.field_3913.field_54155.comp_3160(), this.mc.field_1724.field_3913.field_54155.comp_3161(), this.mc.field_1724.field_3913.field_54155.comp_3162(), this.mc.field_1724.field_3913.field_54155.comp_3163(), true, this.mc.field_1724.field_3913.field_54155.comp_3165());
/*     */         }
/* 126 */         else if (((Boolean)this.safeSneak.get()).booleanValue()) {
/* 127 */           event.setClip(true);
/*     */         }
/*     */       
/*     */       } }
/* 131 */     else if (!this.mc.field_1724.method_5715()) { event.setClip(true); }
/*     */      }
/*     */ 
/*     */   
/*     */   private class_238 getAdjustedPlayerBox(class_238 playerBox) {
/* 136 */     return playerBox.method_1012(0.0D, -this.mc.field_1724.method_49476(), 0.0D)
/* 137 */       .method_1009(-((Double)this.edgeDistance.get()).doubleValue(), 0.0D, -((Double)this.edgeDistance.get()).doubleValue());
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/* 142 */     if (((Boolean)this.sneak.get()).booleanValue() && ((Boolean)this.renderEdgeDistance.get()).booleanValue()) {
/* 143 */       class_238 playerBox = this.mc.field_1724.method_5829();
/* 144 */       class_238 adjustedBox = getAdjustedPlayerBox(playerBox);
/*     */       
/* 146 */       event.renderer.box(adjustedBox, Color.BLUE, Color.RED, ShapeMode.Lines, 0);
/*     */       
/* 148 */       if (((Boolean)this.renderPlayerBox.get()).booleanValue())
/* 149 */         event.renderer.box(playerBox, Color.BLUE, Color.GREEN, ShapeMode.Lines, 0); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\SafeWalk.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */