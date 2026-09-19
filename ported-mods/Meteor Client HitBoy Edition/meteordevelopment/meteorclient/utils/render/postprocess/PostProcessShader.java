/*    */ package meteordevelopment.meteorclient.utils.render.postprocess;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.Std140Builder;
/*    */ import com.mojang.blaze3d.buffers.Std140SizeCalculator;
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import com.mojang.blaze3d.textures.FilterMode;
/*    */ import java.nio.ByteBuffer;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.renderer.MeshRenderer;
/*    */ import net.minecraft.class_11280;
/*    */ import net.minecraft.class_276;
/*    */ import net.minecraft.class_6367;
/*    */ import org.lwjgl.glfw.GLFW;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PostProcessShader
/*    */ {
/*    */   protected final RenderPipeline pipeline;
/*    */   public final class_276 framebuffer;
/*    */   
/*    */   protected PostProcessShader(RenderPipeline pipeline) {
/* 24 */     this.pipeline = pipeline;
/* 25 */     this.framebuffer = (class_276)new class_6367(MeteorClient.NAME + " PostProcessShader " + MeteorClient.NAME, MeteorClient.mc.method_22683().method_4489(), MeteorClient.mc.method_22683().method_4506(), true);
/*    */   }
/*    */   protected abstract boolean shouldDraw();
/*    */   
/*    */   protected void preDraw() {}
/*    */   
/*    */   protected void postDraw() {}
/*    */   
/*    */   protected abstract void setupPass(MeshRenderer paramMeshRenderer);
/*    */   
/*    */   public void clearTexture() {
/* 36 */     if (shouldDraw()) {
/* 37 */       RenderSystem.getDevice().createCommandEncoder().clearColorTexture(this.framebuffer.method_30277(), 0);
/*    */     }
/*    */   }
/*    */   
/*    */   public void submitVertices(Runnable draw) {
/* 42 */     if (!shouldDraw())
/*    */       return; 
/* 44 */     preDraw();
/* 45 */     draw.run();
/* 46 */     postDraw();
/*    */   }
/*    */   
/*    */   public void render() {
/* 50 */     if (!shouldDraw()) {
/*    */       return;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     MeshRenderer renderer = MeshRenderer.begin().attachments(MeteorClient.mc.method_1522()).pipeline(this.pipeline).fullscreen().uniform("PostData", UNIFORM_STORAGE.method_71102(new UniformData(MeteorClient.mc.method_22683().method_4489(), MeteorClient.mc.method_22683().method_4506(), (float)GLFW.glfwGetTime()))).sampler("u_Texture", this.framebuffer.method_71639(), RenderSystem.getSamplerCache().method_75294(FilterMode.NEAREST));
/*    */     
/* 62 */     setupPass(renderer);
/*    */     
/* 64 */     renderer.end();
/*    */   }
/*    */   
/*    */   public void onResized(int width, int height) {
/* 68 */     if (this.framebuffer == null)
/* 69 */       return;  this.framebuffer.method_1234(width, height);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 74 */   private static final int UNIFORM_SIZE = (new Std140SizeCalculator())
/* 75 */     .putVec2()
/* 76 */     .putFloat()
/* 77 */     .get();
/*    */   
/* 79 */   private static final class_11280<UniformData> UNIFORM_STORAGE = new class_11280("Meteor - Post UBO", UNIFORM_SIZE, 16);
/*    */   
/*    */   public static void flipFrame() {
/* 82 */     UNIFORM_STORAGE.method_71100();
/*    */   }
/*    */   private static final class UniformData extends Record implements class_11280.class_11281 { private final float sizeX; private final float sizeY; private final float time;
/* 85 */     private UniformData(float sizeX, float sizeY, float time) { this.sizeX = sizeX; this.sizeY = sizeY; this.time = time; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/render/postprocess/PostProcessShader$UniformData;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #85	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 85 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/PostProcessShader$UniformData; } public float sizeX() { return this.sizeX; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/render/postprocess/PostProcessShader$UniformData;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #85	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/PostProcessShader$UniformData; } public final boolean equals(Object o) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/render/postprocess/PostProcessShader$UniformData;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #85	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/PostProcessShader$UniformData;
/* 85 */       //   0	8	1	o	Ljava/lang/Object; } public float sizeY() { return this.sizeY; } public float time() { return this.time; }
/*    */     
/*    */     public void method_71104(ByteBuffer buffer) {
/* 88 */       Std140Builder.intoBuffer(buffer)
/* 89 */         .putVec2(this.sizeX, this.sizeY)
/* 90 */         .putFloat(this.time);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\postprocess\PostProcessShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */