/*     */ package meteordevelopment.meteorclient.systems.modules.render.marker;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.settings.BlockPosSetting;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.EnumSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.KeybindSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ import net.minecraft.class_2338;
/*     */ 
/*     */ public class Sphere2dMarker extends BaseMarker {
/*     */   public static final String type = "Sphere-2D";
/*     */   
/*     */   private static class Block {
/*     */     public final int x;
/*     */     public final int y;
/*     */     public final int z;
/*     */     public int excludeDir;
/*     */     
/*     */     public Block(int x, int y, int z) {
/*  27 */       this.x = x;
/*  28 */       this.y = y;
/*  29 */       this.z = z;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  35 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  36 */   private final SettingGroup sgRender = this.settings.createGroup("Render");
/*  37 */   private final SettingGroup sgKeybinding = this.settings.createGroup("Keybinding");
/*     */   
/*  39 */   private final Setting<class_2338> center = this.sgGeneral.add((Setting)((BlockPosSetting.Builder)((BlockPosSetting.Builder)((BlockPosSetting.Builder)(new BlockPosSetting.Builder())
/*  40 */       .name("center"))
/*  41 */       .description("Center of the sphere"))
/*  42 */       .onChanged(bp -> this.dirty = true))
/*  43 */       .build());
/*     */ 
/*     */   
/*  46 */   private final Setting<Integer> radius = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  47 */       .name("radius"))
/*  48 */       .description("Radius of the sphere"))
/*  49 */       .defaultValue(Integer.valueOf(20)))
/*  50 */       .min(1)
/*  51 */       .noSlider()
/*  52 */       .onChanged(r -> this.dirty = true))
/*  53 */       .build());
/*     */ 
/*     */   
/*  56 */   private final Setting<Integer> layer = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  57 */       .name("layer"))
/*  58 */       .description("Which layer to render"))
/*  59 */       .defaultValue(Integer.valueOf(0)))
/*  60 */       .min(0)
/*  61 */       .noSlider()
/*  62 */       .onChanged(l -> this.dirty = true))
/*  63 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private final Setting<Boolean> limitRenderRange = this.sgRender.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  69 */       .name("limit-render-range"))
/*  70 */       .description("Whether to limit rendering range (useful in very large circles)"))
/*  71 */       .defaultValue(Boolean.valueOf(false)))
/*  72 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> renderRange;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<ShapeMode> shapeMode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> sideColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<SettingColor> lineColor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Keybind> nextLayerKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Keybind> prevLayerKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final List<Block> blocks;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean dirty;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean calculating;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sphere2dMarker() {
/* 132 */     super("Sphere-2D"); Objects.requireNonNull(this.limitRenderRange); this.renderRange = this.sgRender.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("render-range")).description("Rendering range")).defaultValue(Integer.valueOf(10))).min(1).sliderRange(1, 20).visible(this.limitRenderRange::get)).build()); this.shapeMode = this.sgRender.add((Setting)((EnumSetting.Builder)((EnumSetting.Builder)((EnumSetting.Builder)(new EnumSetting.Builder()).name("shape-mode")).description("How the shapes are rendered.")).defaultValue(ShapeMode.Both)).build()); this.sideColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("side-color")).description("The color of the sides of the blocks being rendered.")).defaultValue(new SettingColor(0, 100, 255, 50)).build()); this.lineColor = this.sgRender.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder()).name("line-color")).description("The color of the lines of the blocks being rendered.")).defaultValue(new SettingColor(0, 100, 255, 255)).build()); this.nextLayerKey = this.sgKeybinding.add((Setting)((KeybindSetting.Builder)((KeybindSetting.Builder)(new KeybindSetting.Builder()).name("next-layer-keybind")).description("Keybind to increment layer")).action(() -> { if (isVisible() && ((Integer)this.layer.get()).intValue() < ((Integer)this.radius.get()).intValue() * 2)
/*     */               this.layer.set(Integer.valueOf(((Integer)this.layer.get()).intValue() + 1)); 
/*     */           }).build()); this.prevLayerKey = this.sgKeybinding.add((Setting)((KeybindSetting.Builder)((KeybindSetting.Builder)(new KeybindSetting.Builder()).name("prev-layer-keybind")).description("Keybind to increment layer")).action(() -> {
/*     */             if (isVisible())
/*     */               this.layer.set(Integer.valueOf(((Integer)this.layer.get()).intValue() - 1)); 
/* 137 */           }).build()); this.blocks = new ArrayList<>(); this.dirty = true; } protected void render(Render3DEvent event) { if (this.dirty && !this.calculating) calcCircle();
/*     */     
/* 139 */     synchronized (this.blocks) {
/* 140 */       for (Block block : this.blocks) {
/* 141 */         if (!((Boolean)this.limitRenderRange.get()).booleanValue() || PlayerUtils.isWithin(block.x, block.y, block.z, ((Integer)this.renderRange.get()).intValue())) {
/* 142 */           event.renderer.box(block.x, block.y, block.z, (block.x + 1), (block.y + 1), (block.z + 1), (Color)this.sideColor.get(), (Color)this.lineColor.get(), (ShapeMode)this.shapeMode.get(), block.excludeDir);
/*     */         }
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeName() {
/* 150 */     return "Sphere-2D";
/*     */   }
/*     */   
/*     */   private void calcCircle() {
/* 154 */     this.calculating = true;
/* 155 */     this.blocks.clear();
/*     */     
/* 157 */     Runnable action = () -> {
/*     */         int cX = ((class_2338)this.center.get()).method_10263();
/*     */         
/*     */         int cY = ((class_2338)this.center.get()).method_10264();
/*     */         
/*     */         int cZ = ((class_2338)this.center.get()).method_10260();
/*     */         
/*     */         int rSq = ((Integer)this.radius.get()).intValue() * ((Integer)this.radius.get()).intValue();
/*     */         
/*     */         int dY = -((Integer)this.radius.get()).intValue() + ((Integer)this.layer.get()).intValue();
/*     */         
/*     */         for (int dX = 0;; dX++) {
/*     */           int dZ = (int)Math.round(Math.sqrt((rSq - dX * dX + dY * dY)));
/*     */           
/*     */           synchronized (this.blocks) {
/*     */             add(cX + dX, cY + dY, cZ + dZ);
/*     */             
/*     */             add(cX + dZ, cY + dY, cZ + dX);
/*     */             
/*     */             add(cX - dX, cY + dY, cZ - dZ);
/*     */             
/*     */             add(cX - dZ, cY + dY, cZ - dX);
/*     */             add(cX + dX, cY + dY, cZ - dZ);
/*     */             add(cX + dZ, cY + dY, cZ - dX);
/*     */             add(cX - dX, cY + dY, cZ + dZ);
/*     */             add(cX - dZ, cY + dY, cZ + dX);
/*     */           } 
/*     */           if (dX >= dZ) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */         synchronized (this.blocks) {
/*     */           for (Block block : this.blocks) {
/*     */             for (Block b : this.blocks) {
/*     */               if (b == block) {
/*     */                 continue;
/*     */               }
/*     */               if (b.x == block.x + 1 && b.z == block.z) {
/*     */                 block.excludeDir |= 0x40;
/*     */               }
/*     */               if (b.x == block.x - 1 && b.z == block.z) {
/*     */                 block.excludeDir |= 0x20;
/*     */               }
/*     */               if (b.x == block.x && b.z == block.z + 1) {
/*     */                 block.excludeDir |= 0x10;
/*     */               }
/*     */               if (b.x == block.x && b.z == block.z - 1) {
/*     */                 block.excludeDir |= 0x8;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         this.dirty = false;
/*     */         this.calculating = false;
/*     */       };
/* 212 */     if (((Integer)this.radius.get()).intValue() <= 50) { action.run(); }
/* 213 */     else { MeteorExecutor.execute(action); }
/*     */   
/*     */   }
/*     */   private void add(int x, int y, int z) {
/* 217 */     for (Block b : this.blocks) {
/* 218 */       if (b.x == x && b.y == y && b.z == z)
/*     */         return; 
/*     */     } 
/* 221 */     this.blocks.add(new Block(x, y, z));
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\marker\Sphere2dMarker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */