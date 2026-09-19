/*     */ package meteordevelopment.meteorclient.utils.render;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*     */ import meteordevelopment.meteorclient.utils.PostInit;
/*     */ import meteordevelopment.meteorclient.utils.misc.Pool;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.irisshaders.iris.api.v0.IrisApi;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_332;
/*     */ import org.joml.Matrix3x2fStack;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Vector4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderUtils
/*     */ {
/*     */   public static class_243 center;
/*  32 */   public static final Matrix4f projection = new Matrix4f();
/*     */   
/*  34 */   private static final Pool<RenderBlock> renderBlockPool = new Pool(RenderBlock::new);
/*  35 */   private static final List<RenderBlock> renderBlocks = (List<RenderBlock>)new ObjectArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PostInit
/*     */   public static void init() {
/*  42 */     MeteorClient.EVENT_BUS.subscribe(RenderUtils.class);
/*     */   }
/*     */   
/*     */   public static boolean isShaderPackInUse() {
/*  46 */     return IrisApi.getInstance().isShaderPackInUse();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void drawItem(class_332 drawContext, class_1799 itemStack, int x, int y, float scale, boolean overlay, String countOverride, boolean disableGuiScale) {
/*  51 */     Matrix3x2fStack matrices = drawContext.method_51448();
/*  52 */     matrices.pushMatrix();
/*     */     
/*  54 */     if (disableGuiScale) {
/*  55 */       matrices.scale(1.0F / MeteorClient.mc.method_22683().method_4495());
/*     */     }
/*     */     
/*  58 */     matrices.scale(scale, scale);
/*     */     
/*  60 */     int scaledX = (int)(x / scale);
/*  61 */     int scaledY = (int)(y / scale);
/*     */     
/*  63 */     drawContext.method_51427(itemStack, scaledX, scaledY);
/*  64 */     if (overlay) drawContext.method_51432(MeteorClient.mc.field_1772, itemStack, scaledX, scaledY, countOverride);
/*     */     
/*  66 */     matrices.popMatrix();
/*     */   }
/*     */   
/*     */   public static void drawItem(class_332 drawContext, class_1799 itemStack, int x, int y, float scale, boolean overlay) {
/*  70 */     drawItem(drawContext, itemStack, x, y, scale, overlay, null, true);
/*     */   }
/*     */   
/*     */   public static void updateScreenCenter(Matrix4f projection, Matrix4f view) {
/*  74 */     RenderUtils.projection.set((Matrix4fc)projection);
/*     */     
/*  76 */     Matrix4f invProjection = (new Matrix4f((Matrix4fc)projection)).invert();
/*  77 */     Matrix4f invView = (new Matrix4f((Matrix4fc)view)).invert();
/*     */     
/*  79 */     Vector4f center4 = (new Vector4f(0.0F, 0.0F, 0.0F, 1.0F)).mul((Matrix4fc)invProjection).mul((Matrix4fc)invView);
/*  80 */     center4.div(center4.w);
/*     */     
/*  82 */     class_243 camera = MeteorClient.mc.field_1773.method_19418().method_71156();
/*  83 */     center = new class_243(camera.field_1352 + center4.x, camera.field_1351 + center4.y, camera.field_1350 + center4.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void renderTickingBlock(class_2338 blockPos, Color sideColor, Color lineColor, ShapeMode shapeMode, int excludeDir, int duration, boolean fade, boolean shrink) {
/*  88 */     renderBlocks.removeIf(next -> {
/*     */           if (next.pos.equals(blockPos)) {
/*     */             renderBlockPool.free(next);
/*     */             
/*     */             return true;
/*     */           } 
/*     */           
/*     */           return false;
/*     */         });
/*  97 */     renderBlocks.add(((RenderBlock)renderBlockPool.get()).set(blockPos, sideColor, lineColor, shapeMode, excludeDir, duration, fade, shrink));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private static void onTick(TickEvent.Pre event) {
/* 102 */     if (renderBlocks.isEmpty())
/*     */       return; 
/* 104 */     renderBlocks.removeIf(next -> {
/*     */           next.tick();
/*     */           if (next.ticks <= 0) {
/*     */             renderBlockPool.free(next);
/*     */             return true;
/*     */           } 
/*     */           return false;
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private static void onRender(Render3DEvent event) {
/* 118 */     renderBlocks.forEach(block -> block.render(event));
/*     */   }
/*     */   
/*     */   public static class RenderBlock {
/* 122 */     public class_2338.class_2339 pos = new class_2338.class_2339();
/*     */     
/*     */     public Color sideColor;
/*     */     
/*     */     public Color lineColor;
/*     */     
/*     */     public ShapeMode shapeMode;
/*     */     public int excludeDir;
/*     */     
/*     */     public RenderBlock set(class_2338 blockPos, Color sideColor, Color lineColor, ShapeMode shapeMode, int excludeDir, int duration, boolean fade, boolean shrink) {
/* 132 */       this.pos.method_10101((class_2382)blockPos);
/* 133 */       this.sideColor = sideColor;
/* 134 */       this.lineColor = lineColor;
/* 135 */       this.shapeMode = shapeMode;
/* 136 */       this.excludeDir = excludeDir;
/* 137 */       this.fade = fade;
/* 138 */       this.shrink = shrink;
/* 139 */       this.ticks = duration;
/* 140 */       this.duration = duration;
/*     */       
/* 142 */       return this;
/*     */     }
/*     */     public int ticks; public int duration; public boolean fade; public boolean shrink;
/*     */     public void tick() {
/* 146 */       this.ticks--;
/*     */     }
/*     */     
/*     */     public void render(Render3DEvent event) {
/* 150 */       int preSideA = this.sideColor.a;
/* 151 */       int preLineA = this.lineColor.a;
/* 152 */       double x1 = this.pos.method_10263(), y1 = this.pos.method_10264(), z1 = this.pos.method_10260();
/* 153 */       double x2 = (this.pos.method_10263() + 1), y2 = (this.pos.method_10264() + 1), z2 = (this.pos.method_10260() + 1);
/*     */       
/* 155 */       double d = (this.ticks - event.tickDelta) / this.duration;
/*     */       
/* 157 */       if (this.fade) {
/* 158 */         this.sideColor.a = (int)(this.sideColor.a * d);
/* 159 */         this.lineColor.a = (int)(this.lineColor.a * d);
/*     */       } 
/* 161 */       if (this.shrink) {
/* 162 */         x1 += d; y1 += d; z1 += d;
/* 163 */         x2 -= d; y2 -= d; z2 -= d;
/*     */       } 
/*     */       
/* 166 */       event.renderer.box(x1, y1, z1, x2, y2, z2, this.sideColor, this.lineColor, this.shapeMode, this.excludeDir);
/*     */       
/* 168 */       this.sideColor.a = preSideA;
/* 169 */       this.lineColor.a = preLineA;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\RenderUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */