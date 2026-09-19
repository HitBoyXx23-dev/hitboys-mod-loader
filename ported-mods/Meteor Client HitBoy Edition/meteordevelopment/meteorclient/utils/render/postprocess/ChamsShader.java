/*     */ package meteordevelopment.meteorclient.utils.render.postprocess;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.Std140Builder;
/*     */ import com.mojang.blaze3d.buffers.Std140SizeCalculator;
/*     */ import com.mojang.blaze3d.platform.TextureUtil;
/*     */ import com.mojang.blaze3d.textures.FilterMode;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.game.ResourcePacksReloadedEvent;
/*     */ import meteordevelopment.meteorclient.renderer.MeshRenderer;
/*     */ import meteordevelopment.meteorclient.renderer.MeteorRenderPipelines;
/*     */ import meteordevelopment.meteorclient.renderer.Texture;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.Chams;
/*     */ import meteordevelopment.meteorclient.utils.PostInit;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_11280;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_3298;
/*     */ import org.lwjgl.stb.STBImage;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChamsShader
/*     */   extends EntityShader
/*     */ {
/*  37 */   private static final String[] FILE_FORMATS = new String[] { "png", "jpg" };
/*     */   
/*     */   private static Texture IMAGE_TEX;
/*     */   private static Chams chams;
/*     */   
/*     */   public ChamsShader() {
/*  43 */     super(MeteorRenderPipelines.POST_IMAGE);
/*  44 */     MeteorClient.EVENT_BUS.subscribe(ChamsShader.class);
/*     */   }
/*     */   
/*     */   @PostInit
/*     */   public static void load() {
/*     */     try {
/*  50 */       ByteBuffer data = null; String[] arrayOfString; int i; byte b;
/*  51 */       for (arrayOfString = FILE_FORMATS, i = arrayOfString.length, b = 0; b < i; ) { String fileFormat = arrayOfString[b];
/*  52 */         Optional<class_3298> optional = MeteorClient.mc.method_1478().method_14486(MeteorClient.identifier("textures/chams." + fileFormat));
/*  53 */         if (optional.isEmpty() || ((class_3298)optional.get()).method_14482() == null) {
/*     */           b++;
/*     */           continue;
/*     */         } 
/*  57 */         data = TextureUtil.readResource(((class_3298)optional.get()).method_14482()); }
/*     */ 
/*     */       
/*  60 */       if (data == null)
/*     */         return; 
/*  62 */       data.rewind();
/*     */       
/*  64 */       MemoryStack stack = MemoryStack.stackPush(); 
/*  65 */       try { IntBuffer width = stack.mallocInt(1);
/*  66 */         IntBuffer height = stack.mallocInt(1);
/*  67 */         IntBuffer comp = stack.mallocInt(1);
/*     */         
/*  69 */         STBImage.stbi_set_flip_vertically_on_load(true);
/*  70 */         ByteBuffer image = STBImage.stbi_load_from_memory(data, width, height, comp, 4);
/*     */         
/*  72 */         IMAGE_TEX = new Texture(width.get(0), height.get(0), TextureFormat.RGBA8, FilterMode.NEAREST, FilterMode.NEAREST);
/*  73 */         IMAGE_TEX.upload(image);
/*     */         
/*  75 */         STBImage.stbi_image_free(image);
/*  76 */         STBImage.stbi_set_flip_vertically_on_load(false);
/*  77 */         if (stack != null) stack.close();  } catch (Throwable throwable) { if (stack != null)
/*     */           try { stack.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; } 
/*  79 */     } catch (IOException e) {
/*  80 */       MeteorClient.LOG.error("Error loading the chams shader", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private static void onResourcePacksReloaded(ResourcePacksReloadedEvent event) {
/*  86 */     load();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setupPass(MeshRenderer renderer) {
/*  91 */     Color color = (Color)chams.shaderColor.get();
/*     */     
/*  93 */     renderer.uniform("ImageData", UNIFORM_STORAGE.method_71102(new UniformData(color.r / 255.0F, color.g / 255.0F, color.b / 255.0F, color.a / 255.0F)));
/*     */ 
/*     */ 
/*     */     
/*  97 */     if (chams.isShader() && chams.shader.get() == Chams.Shader.Image && IMAGE_TEX != null) {
/*  98 */       renderer.sampler("u_TextureI", IMAGE_TEX.method_71659(), IMAGE_TEX.method_75484());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldDraw() {
/* 104 */     if (chams == null) chams = (Chams)Modules.get().get(Chams.class); 
/* 105 */     return chams.isShader();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDraw(class_1297 entity) {
/* 110 */     if (!shouldDraw()) return false; 
/* 111 */     return (((Set)chams.entities.get()).contains(entity.method_5864()) && (entity != MeteorClient.mc.field_1724 || !((Boolean)chams.ignoreSelfDepth.get()).booleanValue()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 116 */   private static final int UNIFORM_SIZE = (new Std140SizeCalculator())
/* 117 */     .putVec4()
/* 118 */     .get();
/*     */   
/* 120 */   private static final class_11280<UniformData> UNIFORM_STORAGE = new class_11280("Meteor - Image UBO", UNIFORM_SIZE, 16);
/*     */   
/*     */   public static void flipFrame() {
/* 123 */     UNIFORM_STORAGE.method_71100();
/*     */   }
/*     */   private static final class UniformData extends Record implements class_11280.class_11281 { private final float r; private final float g; private final float b; private final float a;
/* 126 */     private UniformData(float r, float g, float b, float a) { this.r = r; this.g = g; this.b = b; this.a = a; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #126	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 126 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData; } public float r() { return this.r; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #126	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #126	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData;
/* 126 */       //   0	8	1	o	Ljava/lang/Object; } public float g() { return this.g; } public float b() { return this.b; } public float a() { return this.a; }
/*     */     
/*     */     public void method_71104(ByteBuffer buffer) {
/* 129 */       Std140Builder.intoBuffer(buffer)
/* 130 */         .putVec4(this.r, this.g, this.b, this.a);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\postprocess\ChamsShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */