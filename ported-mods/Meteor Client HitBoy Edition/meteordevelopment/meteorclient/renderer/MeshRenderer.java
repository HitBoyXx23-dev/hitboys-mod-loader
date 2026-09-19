/*     */ package meteordevelopment.meteorclient.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*     */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import com.mojang.blaze3d.systems.RenderPass;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.textures.GpuTextureView;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.OptionalInt;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.render.RenderUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_12137;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_276;
/*     */ import net.minecraft.class_3545;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_9848;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeshRenderer
/*     */ {
/*  34 */   private static final MeshRenderer INSTANCE = new MeshRenderer();
/*     */ 
/*     */   
/*     */   private static boolean taken;
/*     */ 
/*     */   
/*     */   private GpuTextureView colorAttachment;
/*     */   
/*     */   private GpuTextureView depthAttachment;
/*     */   
/*     */   private Color clearColor;
/*     */   
/*  46 */   private final HashMap<String, GpuBufferSlice> uniforms = new HashMap<>(); private RenderPipeline pipeline; @Nullable private MeshBuilder mesh; @Nullable private GpuBuffer vertexBuffer; @Nullable
/*  47 */   private GpuBuffer indexBuffer; private Matrix4f matrix; private final HashMap<String, class_3545<GpuTextureView, class_12137>> samplers = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public static MeshRenderer begin() {
/*  52 */     if (taken) {
/*  53 */       throw new IllegalStateException("Previous instance of MeshRenderer was not ended");
/*     */     }
/*  55 */     taken = true;
/*  56 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public MeshRenderer attachments(GpuTextureView color, GpuTextureView depth) {
/*  60 */     this.colorAttachment = color;
/*  61 */     this.depthAttachment = depth;
/*  62 */     return this;
/*     */   }
/*     */   
/*     */   public MeshRenderer attachments(class_276 framebuffer) {
/*  66 */     this.colorAttachment = framebuffer.method_71639();
/*  67 */     this.depthAttachment = framebuffer.method_71640();
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public MeshRenderer clearColor(Color color) {
/*  72 */     this.clearColor = color;
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public MeshRenderer pipeline(RenderPipeline pipeline) {
/*  77 */     this.pipeline = pipeline;
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   public MeshRenderer mesh(GpuBuffer vertices, GpuBuffer indices) {
/*  82 */     this.vertexBuffer = vertices;
/*  83 */     this.indexBuffer = indices;
/*  84 */     return this;
/*     */   }
/*     */   
/*     */   public MeshRenderer mesh(MeshBuilder mesh) {
/*  88 */     this.mesh = mesh;
/*  89 */     return this;
/*     */   }
/*     */   
/*     */   public MeshRenderer mesh(MeshBuilder mesh, Matrix4f matrix) {
/*  93 */     this.mesh = mesh;
/*  94 */     return transform(matrix);
/*     */   }
/*     */   
/*     */   public MeshRenderer mesh(MeshBuilder mesh, class_4587 matrices) {
/*  98 */     this.mesh = mesh;
/*  99 */     return transform(matrices);
/*     */   }
/*     */   
/*     */   public MeshRenderer transform(Matrix4f matrix) {
/* 103 */     this.matrix = matrix;
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   public MeshRenderer transform(class_4587 matrices) {
/* 108 */     this.matrix = matrices.method_23760().method_23761();
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public MeshRenderer fullscreen() {
/* 113 */     return mesh(FullScreenRenderer.vbo, FullScreenRenderer.ibo);
/*     */   }
/*     */   
/*     */   public MeshRenderer uniform(String name, GpuBufferSlice slice) {
/* 117 */     this.uniforms.put(name, slice);
/* 118 */     return this;
/*     */   }
/*     */   
/*     */   public MeshRenderer sampler(String name, GpuTextureView view, class_12137 sampler) {
/* 122 */     if (name != null && view != null && sampler != null) {
/* 123 */       this.samplers.put(name, new class_3545(view, sampler));
/*     */     }
/*     */     
/* 126 */     return this;
/*     */   }
/*     */   
/*     */   public void end() {
/* 130 */     if (this.mesh != null && this.mesh.isBuilding()) {
/* 131 */       this.mesh.end();
/*     */     }
/*     */ 
/*     */     
/* 135 */     int indexCount = (this.mesh != null) ? this.mesh.getIndicesCount() : (int)((this.indexBuffer != null) ? (this.indexBuffer.size() / 4L) : -1L);
/*     */ 
/*     */     
/* 138 */     if (indexCount > 0) {
/*     */       
/* 140 */       if (Utils.rendering3D || this.matrix != null) {
/* 141 */         RenderSystem.getModelViewStack().pushMatrix();
/*     */       }
/*     */       
/* 144 */       if (this.matrix != null) {
/* 145 */         RenderSystem.getModelViewStack().mul((Matrix4fc)this.matrix);
/*     */       }
/*     */       
/* 148 */       if (Utils.rendering3D) {
/* 149 */         applyCameraPos();
/*     */       }
/*     */       
/* 152 */       GpuBuffer vertexBuffer = (this.mesh != null) ? this.mesh.getVertexBuffer() : this.vertexBuffer;
/* 153 */       GpuBuffer indexBuffer = (this.mesh != null) ? this.mesh.getIndexBuffer() : this.indexBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 158 */       OptionalInt clearColor = (this.clearColor != null) ? OptionalInt.of(class_9848.method_61324(this.clearColor.a, this.clearColor.r, this.clearColor.g, this.clearColor.b)) : OptionalInt.empty();
/*     */       
/* 160 */       GpuBufferSlice meshData = MeshUniforms.write(RenderUtils.projection, (Matrix4f)RenderSystem.getModelViewStack());
/*     */ 
/*     */ 
/*     */       
/* 164 */       RenderPass pass = (this.depthAttachment != null && this.pipeline.wantsDepthTexture()) ? RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Meteor MeshRenderer", this.colorAttachment, clearColor, this.depthAttachment, OptionalDouble.empty()) : RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Meteor MeshRenderer", this.colorAttachment, clearColor);
/*     */       
/* 166 */       pass.setPipeline(this.pipeline);
/* 167 */       pass.setUniform("MeshData", meshData);
/*     */       
/* 169 */       for (String name : this.uniforms.keySet()) {
/* 170 */         pass.setUniform(name, this.uniforms.get(name));
/*     */       }
/*     */       
/* 173 */       for (String name : this.samplers.keySet()) {
/* 174 */         pass.bindTexture(name, (GpuTextureView)((class_3545)this.samplers.get(name)).method_15442(), (class_12137)((class_3545)this.samplers.get(name)).method_15441());
/*     */       }
/*     */       
/* 177 */       pass.setVertexBuffer(0, vertexBuffer);
/* 178 */       pass.setIndexBuffer(indexBuffer, VertexFormat.class_5595.field_27373);
/* 179 */       pass.drawIndexed(0, 0, indexCount, 1);
/*     */       
/* 181 */       pass.close();
/*     */ 
/*     */       
/* 184 */       if (Utils.rendering3D || this.matrix != null) {
/* 185 */         RenderSystem.getModelViewStack().popMatrix();
/*     */       }
/*     */     } 
/*     */     
/* 189 */     this.colorAttachment = null;
/* 190 */     this.depthAttachment = null;
/* 191 */     this.clearColor = null;
/* 192 */     this.pipeline = null;
/* 193 */     this.mesh = null;
/* 194 */     this.vertexBuffer = null;
/* 195 */     this.indexBuffer = null;
/* 196 */     this.matrix = null;
/* 197 */     this.uniforms.clear();
/* 198 */     this.samplers.clear();
/*     */     
/* 200 */     taken = false;
/*     */   }
/*     */   
/*     */   private static void applyCameraPos() {
/* 204 */     class_243 cameraPos = MeteorClient.mc.field_1773.method_19418().method_71156();
/* 205 */     RenderSystem.getModelViewStack().translate(0.0F, (float)-cameraPos.field_1351, 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\MeshRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */