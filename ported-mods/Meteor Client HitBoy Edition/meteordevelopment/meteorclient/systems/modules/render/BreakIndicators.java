/*     */ package meteordevelopment.meteorclient.systems.modules.render;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.mixin.ClientPlayerInteractionManagerAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.WorldRendererAccessor;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.world.HighwayBuilder;
/*     */ import meteordevelopment.meteorclient.systems.modules.world.PacketMine;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_265;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_3191;
/*     */ 
/*     */ public class BreakIndicators
/*     */   extends Module {
/*  31 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  33 */   private final Setting<ShapeMode> shapeMode = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  34 */       .name("shape-mode"))
/*  35 */       .description("How the shapes are rendered."))
/*  36 */       .defaultValue(ShapeMode.Both))
/*  37 */       .build());
/*     */ 
/*     */   
/*  40 */   public final Setting<Boolean> packetMine = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  41 */       .name("packet-mine"))
/*  42 */       .description("Whether or not to render blocks being packet mined."))
/*  43 */       .defaultValue(Boolean.valueOf(true)))
/*  44 */       .build());
/*     */ 
/*     */   
/*  47 */   private final Setting<SettingColor> startSideColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  48 */       .name("start-side-color"))
/*  49 */       .description("The side color for the non-broken block."))
/*  50 */       .defaultValue(new SettingColor(25, 252, 25, 150))
/*  51 */       .visible(() -> ((ShapeMode)this.shapeMode.get()).sides()))
/*  52 */       .build());
/*     */ 
/*     */   
/*  55 */   private final Setting<SettingColor> startLineColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  56 */       .name("start-line-color"))
/*  57 */       .description("The line color for the non-broken block."))
/*  58 */       .defaultValue(new SettingColor(25, 252, 25, 150))
/*  59 */       .visible(() -> ((ShapeMode)this.shapeMode.get()).lines()))
/*  60 */       .build());
/*     */ 
/*     */   
/*  63 */   private final Setting<SettingColor> endSideColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  64 */       .name("end-side-color"))
/*  65 */       .description("The side color for the fully-broken block."))
/*  66 */       .defaultValue(new SettingColor(255, 25, 25, 150))
/*  67 */       .visible(() -> ((ShapeMode)this.shapeMode.get()).sides()))
/*  68 */       .build());
/*     */ 
/*     */   
/*  71 */   private final Setting<SettingColor> endLineColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  72 */       .name("end-line-color"))
/*  73 */       .description("The line color for the fully-broken block."))
/*  74 */       .defaultValue(new SettingColor(255, 25, 25, 150))
/*  75 */       .visible(() -> ((ShapeMode)this.shapeMode.get()).lines()))
/*  76 */       .build());
/*     */ 
/*     */   
/*  79 */   private final Color cSides = new Color();
/*  80 */   private final Color cLines = new Color();
/*     */   
/*     */   public BreakIndicators() {
/*  83 */     super(Categories.Render, "break-indicators", "Renders the progress of a block being broken.");
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/*  88 */     renderNormal(event);
/*     */     
/*  90 */     if (((Boolean)this.packetMine.get()).booleanValue() && !((PacketMine)Modules.get().get(PacketMine.class)).blocks.isEmpty()) {
/*  91 */       renderPacket(event, ((PacketMine)Modules.get().get(PacketMine.class)).blocks);
/*     */     }
/*     */     
/*  94 */     HighwayBuilder b = (HighwayBuilder)Modules.get().get(HighwayBuilder.class);
/*  95 */     if (!b.isActive())
/*     */       return; 
/*  97 */     if (b.normalMining != null) {
/*  98 */       class_265 voxelShape = b.normalMining.blockState.method_26218((class_1922)this.mc.field_1687, b.normalMining.blockPos);
/*  99 */       if (voxelShape.method_1110())
/*     */         return; 
/* 101 */       double normalised = Math.min(1.0D, b.normalMining.progress());
/* 102 */       renderBlock(event, voxelShape.method_1107(), b.normalMining.blockPos, 1.0D - normalised, normalised);
/*     */     } 
/*     */     
/* 105 */     if (b.packetMining != null) {
/* 106 */       class_265 voxelShape = b.packetMining.blockState.method_26218((class_1922)this.mc.field_1687, b.packetMining.blockPos);
/* 107 */       if (voxelShape.method_1110())
/*     */         return; 
/* 109 */       double normalised = Math.min(1.0D, b.packetMining.progress());
/* 110 */       renderBlock(event, voxelShape.method_1107(), b.packetMining.blockPos, 1.0D - normalised, normalised);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderNormal(Render3DEvent event) {
/* 115 */     Int2ObjectMap int2ObjectMap = ((WorldRendererAccessor)this.mc.field_1769).meteor$getBlockBreakingInfos();
/*     */     
/* 117 */     float ownBreakingStage = ((ClientPlayerInteractionManagerAccessor)this.mc.field_1761).meteor$getBreakingProgress();
/* 118 */     class_2338 ownBreakingPos = ((ClientPlayerInteractionManagerAccessor)this.mc.field_1761).meteor$getCurrentBreakingBlockPos();
/*     */     
/* 120 */     if (ownBreakingPos != null && ownBreakingStage > 0.0F) {
/* 121 */       class_2680 state = this.mc.field_1687.method_8320(ownBreakingPos);
/* 122 */       class_265 shape = state.method_26218((class_1922)this.mc.field_1687, ownBreakingPos);
/* 123 */       if (shape == null || shape.method_1110())
/*     */         return; 
/* 125 */       class_238 orig = shape.method_1107();
/*     */       
/* 127 */       double shrinkFactor = 1.0D - ownBreakingStage;
/*     */       
/* 129 */       renderBlock(event, orig, ownBreakingPos, shrinkFactor, ownBreakingStage);
/*     */     } 
/*     */     
/* 132 */     int2ObjectMap.values().forEach(info -> {
/*     */           class_2338 pos = info.method_13991();
/*     */           int stage = info.method_13988();
/*     */           if (pos.equals(ownBreakingPos)) {
/*     */             return;
/*     */           }
/*     */           class_2680 state = this.mc.field_1687.method_8320(pos);
/*     */           class_265 shape = state.method_26218((class_1922)this.mc.field_1687, pos);
/*     */           if (shape == null || shape.method_1110()) {
/*     */             return;
/*     */           }
/*     */           class_238 orig = shape.method_1107();
/*     */           double shrinkFactor = (9 - stage + 1) / 9.0D;
/*     */           double progress = 1.0D - shrinkFactor;
/*     */           renderBlock(event, orig, pos, shrinkFactor, progress);
/*     */         });
/*     */   }
/*     */   
/*     */   private void renderPacket(Render3DEvent event, List<PacketMine.MyBlock> blocks) {
/* 151 */     for (PacketMine.MyBlock block : blocks) {
/* 152 */       if (block.mining && block.progress() != Double.POSITIVE_INFINITY) {
/* 153 */         class_265 shape = block.blockState.method_26218((class_1922)this.mc.field_1687, block.blockPos);
/* 154 */         if (shape == null || shape.method_1110())
/*     */           return; 
/* 156 */         class_238 orig = shape.method_1107();
/*     */         
/* 158 */         double progressNormalised = Math.min(1.0D, block.progress());
/* 159 */         double shrinkFactor = 1.0D - progressNormalised;
/* 160 */         class_2338 pos = block.blockPos;
/*     */         
/* 162 */         renderBlock(event, orig, pos, shrinkFactor, progressNormalised);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderBlock(Render3DEvent event, class_238 orig, class_2338 pos, double shrinkFactor, double progress) {
/* 168 */     class_238 box = orig.method_1002(orig
/* 169 */         .method_17939() * shrinkFactor, orig
/* 170 */         .method_17940() * shrinkFactor, orig
/* 171 */         .method_17941() * shrinkFactor);
/*     */ 
/*     */     
/* 174 */     double xShrink = orig.method_17939() * shrinkFactor / 2.0D;
/* 175 */     double yShrink = orig.method_17940() * shrinkFactor / 2.0D;
/* 176 */     double zShrink = orig.method_17941() * shrinkFactor / 2.0D;
/*     */     
/* 178 */     double x1 = pos.method_10263() + box.field_1323 + xShrink;
/* 179 */     double y1 = pos.method_10264() + box.field_1322 + yShrink;
/* 180 */     double z1 = pos.method_10260() + box.field_1321 + zShrink;
/* 181 */     double x2 = pos.method_10263() + box.field_1320 + xShrink;
/* 182 */     double y2 = pos.method_10264() + box.field_1325 + yShrink;
/* 183 */     double z2 = pos.method_10260() + box.field_1324 + zShrink;
/*     */     
/* 185 */     Color c1Sides = ((SettingColor)this.startSideColor.get()).copy().a(((SettingColor)this.startSideColor.get()).a / 2);
/* 186 */     Color c2Sides = ((SettingColor)this.endSideColor.get()).copy().a(((SettingColor)this.endSideColor.get()).a / 2);
/*     */     
/* 188 */     this.cSides.set(
/* 189 */         (int)Math.round(c1Sides.r + (c2Sides.r - c1Sides.r) * progress), 
/* 190 */         (int)Math.round(c1Sides.g + (c2Sides.g - c1Sides.g) * progress), 
/* 191 */         (int)Math.round(c1Sides.b + (c2Sides.b - c1Sides.b) * progress), 
/* 192 */         (int)Math.round(c1Sides.a + (c2Sides.a - c1Sides.a) * progress));
/*     */ 
/*     */     
/* 195 */     Color c1Lines = (Color)this.startLineColor.get();
/* 196 */     Color c2Lines = (Color)this.endLineColor.get();
/*     */     
/* 198 */     this.cLines.set(
/* 199 */         (int)Math.round(c1Lines.r + (c2Lines.r - c1Lines.r) * progress), 
/* 200 */         (int)Math.round(c1Lines.g + (c2Lines.g - c1Lines.g) * progress), 
/* 201 */         (int)Math.round(c1Lines.b + (c2Lines.b - c1Lines.b) * progress), 
/* 202 */         (int)Math.round(c1Lines.a + (c2Lines.a - c1Lines.a) * progress));
/*     */ 
/*     */     
/* 205 */     event.renderer.box(x1, y1, z1, x2, y2, z2, this.cSides, this.cLines, (ShapeMode)this.shapeMode.get(), 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\BreakIndicators.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */