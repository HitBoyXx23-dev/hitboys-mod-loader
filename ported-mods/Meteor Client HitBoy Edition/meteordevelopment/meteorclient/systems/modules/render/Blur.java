/*     */ package meteordevelopment.meteorclient.systems.modules.render;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*     */ import com.mojang.blaze3d.buffers.Std140Builder;
/*     */ import com.mojang.blaze3d.buffers.Std140SizeCalculator;
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.FilterMode;
/*     */ import com.mojang.blaze3d.textures.GpuTextureView;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import it.unimi.dsi.fastutil.ints.IntFloatImmutablePair;
/*     */ import java.nio.ByteBuffer;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.game.ResolutionChangedEvent;
/*     */ import meteordevelopment.meteorclient.events.render.RenderAfterWorldEvent;
/*     */ import meteordevelopment.meteorclient.renderer.FixedUniformStorage;
/*     */ import meteordevelopment.meteorclient.renderer.MeshRenderer;
/*     */ import meteordevelopment.meteorclient.renderer.MeteorRenderPipelines;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.orbit.listeners.ConsumerListener;
/*     */ import meteordevelopment.orbit.listeners.IListener;
/*     */ import net.minecraft.class_11280;
/*     */ import net.minecraft.class_437;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Blur
/*     */   extends Module
/*     */ {
/*  39 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  40 */   private final SettingGroup sgScreens = this.settings.createGroup("Screens");
/*     */ 
/*     */   
/*  43 */   private final IntFloatImmutablePair[] strengths = new IntFloatImmutablePair[] { 
/*  44 */       IntFloatImmutablePair.of(1, 1.25F), 
/*  45 */       IntFloatImmutablePair.of(1, 2.25F), 
/*  46 */       IntFloatImmutablePair.of(2, 2.0F), 
/*  47 */       IntFloatImmutablePair.of(2, 3.0F), 
/*  48 */       IntFloatImmutablePair.of(2, 4.25F), 
/*  49 */       IntFloatImmutablePair.of(3, 2.5F), 
/*  50 */       IntFloatImmutablePair.of(3, 3.25F), 
/*  51 */       IntFloatImmutablePair.of(3, 4.25F), 
/*  52 */       IntFloatImmutablePair.of(3, 5.5F), 
/*  53 */       IntFloatImmutablePair.of(4, 3.25F), 
/*  54 */       IntFloatImmutablePair.of(4, 4.0F), 
/*  55 */       IntFloatImmutablePair.of(4, 5.0F), 
/*  56 */       IntFloatImmutablePair.of(4, 6.0F), 
/*  57 */       IntFloatImmutablePair.of(4, 7.25F), 
/*  58 */       IntFloatImmutablePair.of(4, 8.25F), 
/*  59 */       IntFloatImmutablePair.of(5, 4.5F), 
/*  60 */       IntFloatImmutablePair.of(5, 5.25F), 
/*  61 */       IntFloatImmutablePair.of(5, 6.25F), 
/*  62 */       IntFloatImmutablePair.of(5, 7.25F), 
/*  63 */       IntFloatImmutablePair.of(5, 8.5F) };
/*     */ 
/*     */ 
/*     */   
/*  67 */   private final Setting<Integer> strength = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  68 */       .name("strength"))
/*  69 */       .description("How strong the blur should be."))
/*  70 */       .defaultValue(Integer.valueOf(5)))
/*  71 */       .min(1)
/*  72 */       .max(20)
/*  73 */       .sliderRange(1, 20)
/*  74 */       .build());
/*     */ 
/*     */   
/*  77 */   private final Setting<Integer> fadeTime = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  78 */       .name("fade-time"))
/*  79 */       .description("How long the fade will last in milliseconds."))
/*  80 */       .defaultValue(Integer.valueOf(100)))
/*  81 */       .min(0)
/*  82 */       .sliderMax(500)
/*  83 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private final Setting<Boolean> meteor = this.sgScreens.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  89 */       .name("meteor"))
/*  90 */       .description("Applies blur to Meteor screens."))
/*  91 */       .defaultValue(Boolean.valueOf(true)))
/*  92 */       .build());
/*     */   
/*  94 */   private final Setting<Boolean> inventories = this.sgScreens.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  95 */       .name("inventories"))
/*  96 */       .description("Applies blur to inventory screens."))
/*  97 */       .defaultValue(Boolean.valueOf(true)))
/*  98 */       .build());
/*     */ 
/*     */   
/* 101 */   private final Setting<Boolean> chat = this.sgScreens.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 102 */       .name("chat"))
/* 103 */       .description("Applies blur when in chat."))
/* 104 */       .defaultValue(Boolean.valueOf(false)))
/* 105 */       .build());
/*     */ 
/*     */   
/* 108 */   private final Setting<Boolean> other = this.sgScreens.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 109 */       .name("other"))
/* 110 */       .description("Applies blur to all other screen types."))
/* 111 */       .defaultValue(Boolean.valueOf(true)))
/* 112 */       .build());
/*     */ 
/*     */   
/* 115 */   private final GpuTextureView[] fbos = new GpuTextureView[6];
/*     */   
/*     */   private GpuBufferSlice[] ubos;
/*     */   private boolean enabled;
/*     */   private long fadeEndAt;
/* 120 */   private float previousOffset = -1.0F;
/*     */   
/*     */   public Blur() {
/* 123 */     super(Categories.Render, "blur", "Blurs background when in GUI screens.");
/*     */ 
/*     */     
/* 126 */     for (int i = 0; i < this.fbos.length; i++) {
/* 127 */       this.fbos[i] = createFbo(i);
/*     */     }
/*     */ 
/*     */     
/* 131 */     MeteorClient.EVENT_BUS.subscribe((IListener)new ConsumerListener(ResolutionChangedEvent.class, event -> {
/*     */             for (int i = 0; i < this.fbos.length; i++) {
/*     */               if (this.fbos[i] != null) {
/*     */                 this.fbos[i].close();
/*     */               }
/*     */ 
/*     */               
/*     */               this.fbos[i] = createFbo(i);
/*     */             } 
/*     */ 
/*     */             
/*     */             this.previousOffset = -1.0F;
/*     */           }));
/*     */     
/* 145 */     MeteorClient.EVENT_BUS.subscribe((IListener)new ConsumerListener(RenderAfterWorldEvent.class, event -> onRenderAfterWorld()));
/*     */   }
/*     */   
/*     */   private GpuTextureView createFbo(int i) {
/* 149 */     double scale = 1.0D / Math.pow(2.0D, i);
/*     */     
/* 151 */     int width = (int)(this.mc.method_22683().method_4489() * scale);
/* 152 */     int height = (int)(this.mc.method_22683().method_4506() * scale);
/*     */     
/* 154 */     return RenderSystem.getDevice().createTextureView(RenderSystem.getDevice().createTexture("Blur - " + i, 15, TextureFormat.RGBA8, width, height, 1, 1));
/*     */   }
/*     */ 
/*     */   
/*     */   private void onRenderAfterWorld() {
/* 159 */     boolean shouldRender = shouldRender();
/* 160 */     long time = System.currentTimeMillis();
/*     */     
/* 162 */     if (this.enabled) {
/* 163 */       if (!shouldRender) {
/* 164 */         if (this.fadeEndAt == -1L) this.fadeEndAt = System.currentTimeMillis() + ((Integer)this.fadeTime.get()).intValue();
/*     */         
/* 166 */         if (time >= this.fadeEndAt) {
/* 167 */           this.enabled = false;
/* 168 */           this.fadeEndAt = -1L;
/*     */         }
/*     */       
/*     */       } 
/* 172 */     } else if (shouldRender) {
/* 173 */       this.enabled = true;
/* 174 */       this.fadeEndAt = System.currentTimeMillis() + ((Integer)this.fadeTime.get()).intValue();
/*     */     } 
/*     */ 
/*     */     
/* 178 */     if (!this.enabled) {
/*     */       return;
/*     */     }
/* 181 */     double progress = 1.0D;
/*     */     
/* 183 */     if (time < this.fadeEndAt)
/* 184 */     { if (shouldRender) { progress = 1.0D - (this.fadeEndAt - time) / ((Integer)this.fadeTime.get()).doubleValue(); }
/* 185 */       else { progress = (this.fadeEndAt - time) / ((Integer)this.fadeTime.get()).doubleValue(); }
/*     */        }
/* 187 */     else { this.fadeEndAt = -1L; }
/*     */ 
/*     */ 
/*     */     
/* 191 */     IntFloatImmutablePair strength = this.strengths[(int)((((Integer)this.strength.get()).intValue() - 1) * progress)];
/* 192 */     int iterations = strength.leftInt();
/* 193 */     float offset = strength.rightFloat();
/*     */ 
/*     */     
/* 196 */     if (this.previousOffset != offset) {
/* 197 */       updateUniforms(offset);
/* 198 */       this.previousOffset = offset;
/*     */     } 
/*     */ 
/*     */     
/* 202 */     renderToFbo(this.fbos[0], this.mc.method_1522().method_71639(), MeteorRenderPipelines.BLUR_DOWN, this.ubos[0]);
/*     */     
/*     */     int i;
/* 205 */     for (i = 0; i < iterations; i++) {
/* 206 */       renderToFbo(this.fbos[i + 1], this.fbos[i], MeteorRenderPipelines.BLUR_DOWN, this.ubos[i + 1]);
/*     */     }
/*     */ 
/*     */     
/* 210 */     for (i = iterations; i >= 1; i--) {
/* 211 */       renderToFbo(this.fbos[i - 1], this.fbos[i], MeteorRenderPipelines.BLUR_UP, this.ubos[i - 1]);
/*     */     }
/*     */ 
/*     */     
/* 215 */     MeshRenderer.begin()
/* 216 */       .attachments(this.mc.method_1522())
/* 217 */       .pipeline(MeteorRenderPipelines.BLUR_PASSTHROUGH)
/* 218 */       .fullscreen()
/* 219 */       .sampler("u_Texture", this.fbos[0], RenderSystem.getSamplerCache().method_75294(FilterMode.LINEAR))
/* 220 */       .end();
/*     */   }
/*     */   
/*     */   private void renderToFbo(GpuTextureView targetFbo, GpuTextureView sourceTexture, RenderPipeline pipeline, GpuBufferSlice ubo) {
/* 224 */     MeshRenderer.begin()
/* 225 */       .attachments(targetFbo, null)
/* 226 */       .pipeline(pipeline)
/* 227 */       .fullscreen()
/* 228 */       .uniform("BlurData", ubo)
/* 229 */       .sampler("u_Texture", sourceTexture, RenderSystem.getSamplerCache().method_75294(FilterMode.LINEAR))
/* 230 */       .end();
/*     */   }
/*     */   
/*     */   private boolean shouldRender() {
/* 234 */     if (!isActive()) return false; 
/* 235 */     class_437 screen = this.mc.field_1755;
/*     */     
/* 237 */     if (screen instanceof meteordevelopment.meteorclient.gui.WidgetScreen) return ((Boolean)this.meteor.get()).booleanValue(); 
/* 238 */     if (screen instanceof net.minecraft.class_465) return ((Boolean)this.inventories.get()).booleanValue(); 
/* 239 */     if (screen instanceof net.minecraft.class_408) return ((Boolean)this.chat.get()).booleanValue(); 
/* 240 */     if (screen != null) return ((Boolean)this.other.get()).booleanValue();
/*     */     
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateUniforms(float offset) {
/* 248 */     UNIFORM_STORAGE.clear();
/*     */     
/* 250 */     BlurUniformData[] uboData = new BlurUniformData[6];
/* 251 */     for (int i = 0; i < uboData.length; i++) {
/* 252 */       GpuTextureView fbo = this.fbos[i];
/* 253 */       uboData[i] = new BlurUniformData(0.5F / fbo
/* 254 */           .getWidth(0), 0.5F / fbo.getHeight(0), offset);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 259 */     this.ubos = UNIFORM_STORAGE.writeAll((class_11280.class_11281[])uboData);
/*     */   }
/*     */   
/* 262 */   private static final int UNIFORM_SIZE = (new Std140SizeCalculator())
/* 263 */     .putVec2()
/* 264 */     .putFloat()
/* 265 */     .get();
/*     */   
/* 267 */   private static final FixedUniformStorage<BlurUniformData> UNIFORM_STORAGE = new FixedUniformStorage("Meteor - Blur UBO", UNIFORM_SIZE, 6);
/*     */   private static final class BlurUniformData extends Record implements class_11280.class_11281 { private final float halfTexelSizeX; private final float halfTexelSizeY; private final float offset;
/* 269 */     private BlurUniformData(float halfTexelSizeX, float halfTexelSizeY, float offset) { this.halfTexelSizeX = halfTexelSizeX; this.halfTexelSizeY = halfTexelSizeY; this.offset = offset; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/systems/modules/render/Blur$BlurUniformData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #269	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 269 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/modules/render/Blur$BlurUniformData; } public float halfTexelSizeX() { return this.halfTexelSizeX; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/systems/modules/render/Blur$BlurUniformData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #269	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/modules/render/Blur$BlurUniformData; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/systems/modules/render/Blur$BlurUniformData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #269	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/systems/modules/render/Blur$BlurUniformData;
/* 269 */       //   0	8	1	o	Ljava/lang/Object; } public float halfTexelSizeY() { return this.halfTexelSizeY; } public float offset() { return this.offset; }
/*     */     
/*     */     public void method_71104(ByteBuffer buffer) {
/* 272 */       Std140Builder.intoBuffer(buffer)
/* 273 */         .putVec2(this.halfTexelSizeX, this.halfTexelSizeY)
/* 274 */         .putFloat(this.offset);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\Blur.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */