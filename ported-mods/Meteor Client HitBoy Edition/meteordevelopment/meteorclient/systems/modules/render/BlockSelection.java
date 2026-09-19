/*     */ package meteordevelopment.meteorclient.systems.modules.render;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_265;
/*     */ import net.minecraft.class_3965;
/*     */ 
/*     */ public class BlockSelection extends Module {
/*  23 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  25 */   private final Setting<Boolean> advanced = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  26 */       .name("advanced"))
/*  27 */       .description("Shows a more advanced outline on different types of shape blocks."))
/*  28 */       .defaultValue(Boolean.valueOf(true)))
/*  29 */       .build());
/*     */ 
/*     */   
/*  32 */   private final Setting<Boolean> oneSide = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  33 */       .name("single-side"))
/*  34 */       .description("Only renders the side you are looking at."))
/*  35 */       .defaultValue(Boolean.valueOf(false)))
/*  36 */       .build());
/*     */ 
/*     */   
/*  39 */   private final Setting<ShapeMode> shapeMode = this.sgGeneral.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder())
/*  40 */       .name("shape-mode"))
/*  41 */       .description("How the shapes are rendered."))
/*  42 */       .defaultValue(ShapeMode.Both))
/*  43 */       .build());
/*     */ 
/*     */   
/*  46 */   private final Setting<SettingColor> sideColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  47 */       .name("side-color"))
/*  48 */       .description("The side color."))
/*  49 */       .defaultValue(new SettingColor(255, 255, 255, 50))
/*  50 */       .build());
/*     */ 
/*     */   
/*  53 */   private final Setting<SettingColor> lineColor = this.sgGeneral.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/*  54 */       .name("line-color"))
/*  55 */       .description("The line color."))
/*  56 */       .defaultValue(new SettingColor(255, 255, 255, 255))
/*  57 */       .build());
/*     */ 
/*     */   
/*  60 */   private final Setting<Boolean> hideInside = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  61 */       .name("hide-when-inside-block"))
/*  62 */       .description("Hide selection when inside target block."))
/*  63 */       .defaultValue(Boolean.valueOf(true)))
/*  64 */       .build());
/*     */ 
/*     */   
/*     */   public BlockSelection() {
/*  68 */     super(Categories.Render, "block-selection", "Modifies how your block selection is rendered.");
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender(Render3DEvent event) {
/*  73 */     if (this.mc.field_1765 != null) { class_239 class_239 = this.mc.field_1765; if (class_239 instanceof class_3965) { class_3965 result = (class_3965)class_239; if (result.method_17783() != class_239.class_240.field_1333) {
/*     */           
/*  75 */           if (((Boolean)this.hideInside.get()).booleanValue() && result.method_17781())
/*     */             return; 
/*  77 */           class_2338 bp = result.method_17777();
/*  78 */           class_2350 side = result.method_17780();
/*     */           
/*  80 */           class_265 shape = this.mc.field_1687.method_8320(bp).method_26218((class_1922)this.mc.field_1687, bp);
/*     */           
/*  82 */           if (shape.method_1110())
/*  83 */             return;  class_238 box = shape.method_1107();
/*     */           
/*  85 */           if (((Boolean)this.oneSide.get()).booleanValue()) {
/*  86 */             if (side == class_2350.field_11036 || side == class_2350.field_11033) {
/*  87 */               event.renderer.sideHorizontal(bp.method_10263() + box.field_1323, bp.method_10264() + ((side == class_2350.field_11033) ? box.field_1322 : box.field_1325), bp.method_10260() + box.field_1321, bp.method_10263() + box.field_1320, bp.method_10260() + box.field_1324, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get());
/*     */             }
/*  89 */             else if (side == class_2350.field_11035 || side == class_2350.field_11043) {
/*  90 */               double z = (side == class_2350.field_11043) ? box.field_1321 : box.field_1324;
/*  91 */               event.renderer.sideVertical(bp.method_10263() + box.field_1323, bp.method_10264() + box.field_1322, bp.method_10260() + z, bp.method_10263() + box.field_1320, bp.method_10264() + box.field_1325, bp.method_10260() + z, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get());
/*     */             } else {
/*     */               
/*  94 */               double x = (side == class_2350.field_11039) ? box.field_1323 : box.field_1320;
/*  95 */               event.renderer.sideVertical(bp.method_10263() + x, bp.method_10264() + box.field_1322, bp.method_10260() + box.field_1321, bp.method_10263() + x, bp.method_10264() + box.field_1325, bp.method_10260() + box.field_1324, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get());
/*     */             }
/*     */           
/*     */           }
/*  99 */           else if (((Boolean)this.advanced.get()).booleanValue()) {
/* 100 */             if (this.shapeMode.get() == ShapeMode.Both || this.shapeMode.get() == ShapeMode.Lines) {
/* 101 */               shape.method_1104((minX, minY, minZ, maxX, maxY, maxZ) -> event.renderer.line(bp.method_10263() + minX, bp.method_10264() + minY, bp.method_10260() + minZ, bp.method_10263() + maxX, bp.method_10264() + maxY, bp.method_10260() + maxZ, (Color)this.lineColor.get()));
/*     */             }
/*     */ 
/*     */ 
/*     */             
/* 106 */             if (this.shapeMode.get() == ShapeMode.Both || this.shapeMode.get() == ShapeMode.Sides) {
/* 107 */               for (class_238 b : shape.method_1090()) {
/* 108 */                 render(event, bp, b);
/*     */               }
/*     */             }
/*     */           } else {
/*     */             
/* 113 */             render(event, bp, box);
/*     */           } 
/*     */           return;
/*     */         }  }
/*     */        }
/*     */      } private void render(Render3DEvent event, class_2338 bp, class_238 box) {
/* 119 */     event.renderer.box(bp.method_10263() + box.field_1323, bp.method_10264() + box.field_1322, bp.method_10260() + box.field_1321, bp.method_10263() + box.field_1320, bp.method_10264() + box.field_1325, bp.method_10260() + box.field_1324, (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\BlockSelection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */