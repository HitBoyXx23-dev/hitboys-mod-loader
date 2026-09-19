/*     */ package meteordevelopment.meteorclient.systems.hud;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.google.common.cache.RemovalNotification;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.time.Duration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.meteor.CustomFontChangedEvent;
/*     */ import meteordevelopment.meteorclient.renderer.Fonts;
/*     */ import meteordevelopment.meteorclient.renderer.MeshBuilder;
/*     */ import meteordevelopment.meteorclient.renderer.MeshRenderer;
/*     */ import meteordevelopment.meteorclient.renderer.MeteorRenderPipelines;
/*     */ import meteordevelopment.meteorclient.renderer.Renderer2D;
/*     */ import meteordevelopment.meteorclient.renderer.text.CustomTextRenderer;
/*     */ import meteordevelopment.meteorclient.renderer.text.Font;
/*     */ import meteordevelopment.meteorclient.renderer.text.VanillaTextRenderer;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.render.RenderUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_10017;
/*     */ import net.minecraft.class_10042;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_332;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ 
/*     */ public class HudRenderer
/*     */ {
/*  42 */   public static final HudRenderer INSTANCE = new HudRenderer();
/*     */   
/*     */   private static final double SCALE_TO_HEIGHT = 0.05555555555555555D;
/*     */   
/*  46 */   private final Hud hud = Hud.get();
/*  47 */   private final List<Runnable> postTasks = new ArrayList<>();
/*     */   
/*  49 */   private final Int2ObjectMap<FontHolder> fontsInUse = (Int2ObjectMap<FontHolder>)new Int2ObjectOpenHashMap(); private final LoadingCache<Integer, FontHolder> fontCache; private HudRenderer() {
/*  50 */     this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  57 */       .fontCache = CacheBuilder.newBuilder().maximumSize(4L).expireAfterAccess(Duration.ofMinutes(10L)).removalListener(notification -> { if (notification.wasEvicted()) ((FontHolder)notification.getValue()).destroy();  }).build(CacheLoader.from(HudRenderer::loadFont));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     MeteorClient.EVENT_BUS.subscribe(this);
/*     */   }
/*     */   public class_332 drawContext; public double delta;
/*     */   public void begin(class_332 drawContext) {
/*  67 */     Renderer2D.COLOR.begin();
/*     */     
/*  69 */     this.drawContext = drawContext;
/*  70 */     this.delta = Utils.frameTime;
/*     */     
/*  72 */     drawContext.method_71048();
/*     */     
/*  74 */     if (!this.hud.hasCustomFont()) {
/*  75 */       VanillaTextRenderer.INSTANCE.scaleIndividually = true;
/*  76 */       VanillaTextRenderer.INSTANCE.begin();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void end() {
/*  81 */     Renderer2D.COLOR.render();
/*     */     
/*  83 */     if (this.hud.hasCustomFont()) {
/*     */       
/*  85 */       for (ObjectIterator<FontHolder> objectIterator = this.fontsInUse.values().iterator(); objectIterator.hasNext(); ) {
/*  86 */         FontHolder fontHolder = objectIterator.next();
/*     */         
/*  88 */         if (fontHolder.visited) {
/*  89 */           MeshRenderer.begin()
/*  90 */             .attachments(MeteorClient.mc.method_1522())
/*  91 */             .pipeline(MeteorRenderPipelines.UI_TEXT)
/*  92 */             .mesh(fontHolder.getMesh())
/*  93 */             .sampler("u_Texture", fontHolder.font.texture.method_71659(), fontHolder.font.texture.method_75484())
/*  94 */             .end();
/*     */         } else {
/*     */           
/*  97 */           objectIterator.remove();
/*  98 */           this.fontCache.put(Integer.valueOf(fontHolder.font.getHeight()), fontHolder);
/*     */         } 
/*     */         
/* 101 */         fontHolder.visited = false;
/*     */       } 
/*     */     } else {
/*     */       
/* 105 */       VanillaTextRenderer.INSTANCE.end();
/* 106 */       VanillaTextRenderer.INSTANCE.scaleIndividually = false;
/*     */     } 
/*     */     
/* 109 */     for (Runnable task : this.postTasks) task.run(); 
/* 110 */     this.postTasks.clear();
/*     */     
/* 112 */     this.drawContext.method_71048();
/*     */     
/* 114 */     this.drawContext = null;
/*     */   }
/*     */   
/*     */   public void line(double x1, double y1, double x2, double y2, Color color) {
/* 118 */     Renderer2D.COLOR.line(x1, y1, x2, y2, color);
/*     */   }
/*     */   
/*     */   public void quad(double x, double y, double width, double height, Color color) {
/* 122 */     Renderer2D.COLOR.quad(x, y, width, height, color);
/*     */   }
/*     */   
/*     */   public void quad(double x, double y, double width, double height, Color cTopLeft, Color cTopRight, Color cBottomRight, Color cBottomLeft) {
/* 126 */     Renderer2D.COLOR.quad(x, y, width, height, cTopLeft, cTopRight, cBottomRight, cBottomLeft);
/*     */   }
/*     */   
/*     */   public void triangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {
/* 130 */     Renderer2D.COLOR.triangle(x1, y1, x2, y2, x3, y3, color);
/*     */   }
/*     */   
/*     */   public void texture(class_2960 id, double x, double y, double width, double height, Color color) {
/* 134 */     Renderer2D.TEXTURE.begin();
/* 135 */     Renderer2D.TEXTURE.texQuad(x, y, width, height, color);
/* 136 */     Renderer2D.TEXTURE.render(MeteorClient.mc.method_1531().method_4619(id).method_71659(), MeteorClient.mc.method_1531().method_4619(id).method_75484());
/*     */   }
/*     */   public double text(String text, double x, double y, Color color, boolean shadow, double scale) {
/*     */     double width;
/* 140 */     if (scale == -1.0D) scale = this.hud.getTextScale();
/*     */     
/* 142 */     if (!this.hud.hasCustomFont()) {
/* 143 */       VanillaTextRenderer.INSTANCE.scale = scale * 2.0D;
/* 144 */       return VanillaTextRenderer.INSTANCE.render(text, x, y, color, shadow);
/*     */     } 
/*     */     
/* 147 */     FontHolder fontHolder = getFontHolder(scale, true);
/*     */     
/* 149 */     Font font = fontHolder.font;
/* 150 */     MeshBuilder mesh = fontHolder.getMesh();
/*     */ 
/*     */ 
/*     */     
/* 154 */     if (shadow) {
/* 155 */       int preShadowA = CustomTextRenderer.SHADOW_COLOR.a;
/* 156 */       CustomTextRenderer.SHADOW_COLOR.a = (int)(color.a / 255.0D * preShadowA);
/*     */       
/* 158 */       width = font.render(mesh, text, x + 1.0D, y + 1.0D, CustomTextRenderer.SHADOW_COLOR, scale);
/* 159 */       font.render(mesh, text, x, y, color, scale);
/*     */       
/* 161 */       CustomTextRenderer.SHADOW_COLOR.a = preShadowA;
/*     */     } else {
/*     */       
/* 164 */       width = font.render(mesh, text, x, y, color, scale);
/*     */     } 
/*     */     
/* 167 */     return width;
/*     */   }
/*     */   public double text(String text, double x, double y, Color color, boolean shadow) {
/* 170 */     return text(text, x, y, color, shadow, -1.0D);
/*     */   }
/*     */   
/*     */   public double textWidth(String text, boolean shadow, double scale) {
/* 174 */     if (text.isEmpty()) return 0.0D;
/*     */     
/* 176 */     if (this.hud.hasCustomFont()) {
/* 177 */       double width = getFont(scale).getWidth(text, text.length());
/* 178 */       return (width + (shadow ? true : false)) * ((scale == -1.0D) ? this.hud.getTextScale() : scale) + (shadow ? true : false);
/*     */     } 
/*     */     
/* 181 */     VanillaTextRenderer.INSTANCE.scale = ((scale == -1.0D) ? this.hud.getTextScale() : scale) * 2.0D;
/* 182 */     return VanillaTextRenderer.INSTANCE.getWidth(text, shadow);
/*     */   }
/*     */   public double textWidth(String text, boolean shadow) {
/* 185 */     return textWidth(text, shadow, -1.0D);
/*     */   }
/*     */   public double textWidth(String text, double scale) {
/* 188 */     return textWidth(text, false, scale);
/*     */   }
/*     */   public double textWidth(String text) {
/* 191 */     return textWidth(text, false, -1.0D);
/*     */   }
/*     */   
/*     */   public double textHeight(boolean shadow, double scale) {
/* 195 */     if (this.hud.hasCustomFont()) {
/* 196 */       double height = (getFont(scale).getHeight() + 1);
/* 197 */       return (height + (shadow ? true : false)) * ((scale == -1.0D) ? this.hud.getTextScale() : scale);
/*     */     } 
/*     */     
/* 200 */     VanillaTextRenderer.INSTANCE.scale = ((scale == -1.0D) ? this.hud.getTextScale() : scale) * 2.0D;
/* 201 */     return VanillaTextRenderer.INSTANCE.getHeight(shadow);
/*     */   }
/*     */   public double textHeight(boolean shadow) {
/* 204 */     return textHeight(shadow, -1.0D);
/*     */   }
/*     */   public double textHeight() {
/* 207 */     return textHeight(false, -1.0D);
/*     */   }
/*     */   
/*     */   public void post(Runnable task) {
/* 211 */     this.postTasks.add(task);
/*     */   }
/*     */   
/*     */   public void item(class_1799 itemStack, int x, int y, float scale, boolean overlay, String countOverlay) {
/* 215 */     RenderUtils.drawItem(this.drawContext, itemStack, x, y, scale, overlay, countOverlay, true);
/*     */   }
/*     */   
/*     */   public void item(class_1799 itemStack, int x, int y, float scale, boolean overlay) {
/* 219 */     RenderUtils.drawItem(this.drawContext, itemStack, x, y, scale, overlay);
/*     */   }
/*     */   
/*     */   public void entity(class_1309 entity, int x, int y, int width, int height, float yaw, float pitch) {
/* 223 */     float previousBodyYaw = entity.field_6283;
/* 224 */     float previousYaw = entity.method_36454();
/* 225 */     float previousPitch = entity.method_36455();
/* 226 */     float lastLastHeadYaw = entity.field_6259;
/* 227 */     float lastHeadYaw = entity.field_6241;
/*     */     
/* 229 */     float tanYaw = (float)Math.atan((yaw / 40.0F));
/* 230 */     float tanPitch = (float)Math.atan((pitch / 40.0F));
/* 231 */     entity.field_6283 = 180.0F + tanYaw * 20.0F;
/* 232 */     entity.method_36456(180.0F + tanYaw * 40.0F);
/* 233 */     entity.method_36457(-tanPitch * 20.0F);
/* 234 */     entity.field_6241 = entity.method_36454();
/* 235 */     entity.field_6259 = entity.method_36454();
/*     */     
/* 237 */     class_10042 state = (class_10042)MeteorClient.mc.method_1561().method_3953((class_1297)entity).method_62425((class_1297)entity, 1.0F);
/*     */     
/* 239 */     entity.field_6283 = previousBodyYaw;
/* 240 */     entity.method_36456(previousYaw);
/* 241 */     entity.method_36457(previousPitch);
/* 242 */     entity.field_6259 = lastLastHeadYaw;
/* 243 */     entity.field_6241 = lastHeadYaw;
/*     */     
/* 245 */     float s = 1.0F / MeteorClient.mc.method_22683().method_4495();
/* 246 */     int x1 = (int)(x * s);
/* 247 */     int y1 = (int)(y * s);
/* 248 */     int x2 = (int)((x + width) * s);
/* 249 */     int y2 = (int)((y + height) * s);
/*     */     
/* 251 */     float scale = Math.max(width, height) * s / 2.0F;
/* 252 */     Vector3f translation = new Vector3f(0.0F, 1.0F, 0.0F);
/* 253 */     Quaternionf rotation = (new Quaternionf()).rotateZ(3.1415927F);
/*     */     
/* 255 */     this.drawContext.method_70856((class_10017)state, scale, translation, rotation, null, x1, y1, x2, y2);
/*     */   }
/*     */ 
/*     */   
/*     */   private FontHolder getFontHolder(double scale, boolean render) {
/* 260 */     if (scale == -1.0D) scale = this.hud.getTextScale(); 
/* 261 */     int height = (int)Math.round(scale / 0.05555555555555555D);
/*     */ 
/*     */     
/* 264 */     FontHolder fontHolder = (FontHolder)this.fontsInUse.get(height);
/* 265 */     if (fontHolder != null) {
/* 266 */       if (render) fontHolder.visited = true; 
/* 267 */       return fontHolder;
/*     */     } 
/*     */ 
/*     */     
/* 271 */     if (render) {
/* 272 */       fontHolder = (FontHolder)this.fontCache.getIfPresent(Integer.valueOf(height));
/* 273 */       if (fontHolder == null) { fontHolder = loadFont(height); }
/* 274 */       else { this.fontCache.invalidate(Integer.valueOf(height)); }
/*     */       
/* 276 */       this.fontsInUse.put(height, fontHolder);
/* 277 */       fontHolder.visited = true;
/*     */       
/* 279 */       return fontHolder;
/*     */     } 
/*     */ 
/*     */     
/* 283 */     return (FontHolder)this.fontCache.getUnchecked(Integer.valueOf(height));
/*     */   }
/*     */   
/*     */   private Font getFont(double scale) {
/* 287 */     return (getFontHolder(scale, false)).font;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onCustomFontChanged(CustomFontChangedEvent event) {
/* 293 */     for (ObjectIterator<FontHolder> objectIterator = this.fontsInUse.values().iterator(); objectIterator.hasNext(); ) { FontHolder fontHolder = objectIterator.next(); fontHolder.destroy(); }
/* 294 */      for (FontHolder fontHolder : this.fontCache.asMap().values()) fontHolder.destroy();
/*     */ 
/*     */     
/* 297 */     this.fontsInUse.clear();
/* 298 */     this.fontCache.invalidateAll();
/*     */   }
/*     */   
/*     */   private static FontHolder loadFont(int height) {
/*     */     try {
/* 303 */       ByteBuffer buffer = Fonts.RENDERER.fontFace.readToDirectByteBuffer();
/* 304 */       return new FontHolder(new Font(buffer, height));
/* 305 */     } catch (IOException e) {
/* 306 */       throw new RuntimeException("Failed to load font: " + String.valueOf(Fonts.RENDERER.fontFace), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class FontHolder
/*     */   {
/*     */     public final Font font;
/*     */     public boolean visited;
/*     */     private MeshBuilder mesh;
/*     */     
/*     */     public FontHolder(Font font) {
/* 317 */       this.font = font;
/*     */     }
/*     */     
/*     */     public MeshBuilder getMesh() {
/* 321 */       if (this.mesh == null) this.mesh = new MeshBuilder(MeteorRenderPipelines.UI_TEXT); 
/* 322 */       if (!this.mesh.isBuilding()) this.mesh.begin(); 
/* 323 */       return this.mesh;
/*     */     }
/*     */     
/*     */     public void destroy() {
/* 327 */       this.font.texture.close();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\HudRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */