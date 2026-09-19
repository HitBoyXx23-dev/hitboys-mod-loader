/*     */ package meteordevelopment.meteorclient.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*     */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import java.nio.ByteBuffer;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.fabricmc.loader.api.FabricLoader;
/*     */ import net.minecraft.class_243;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeshBuilder
/*     */ {
/*  23 */   private static final boolean DEBUG = (FabricLoader.getInstance().isDevelopmentEnvironment() || Boolean.getBoolean("meteor.render.debug"));
/*     */   
/*  25 */   public double alpha = 1.0D;
/*     */   
/*     */   private final VertexFormat format;
/*     */   
/*     */   private final int primitiveVerticesSize;
/*     */   private final int primitiveIndicesCount;
/*  31 */   private ByteBuffer vertices = null;
/*     */   private long verticesPointerStart;
/*     */   private long verticesPointer;
/*  34 */   private ByteBuffer indices = null;
/*     */   private long indicesPointer;
/*     */   private int vertexI;
/*     */   private int indicesCount;
/*     */   private boolean building;
/*     */   private double cameraX;
/*     */   private double cameraZ;
/*     */   
/*     */   public MeshBuilder(RenderPipeline pipeline) {
/*  43 */     this(pipeline.getVertexFormat(), pipeline.getVertexFormatMode());
/*     */   }
/*     */   
/*     */   public MeshBuilder(VertexFormat format, VertexFormat.class_5596 drawMode) {
/*  47 */     this.format = format;
/*  48 */     this.primitiveVerticesSize = format.getVertexSize();
/*  49 */     this.primitiveIndicesCount = drawMode.field_27384;
/*     */   }
/*     */   
/*     */   public MeshBuilder(VertexFormat format, VertexFormat.class_5596 drawMode, int vertexCount, int indexCount) {
/*  53 */     this(format, drawMode);
/*  54 */     allocateBuffers(vertexCount, indexCount);
/*     */   }
/*     */   
/*     */   public void begin() {
/*  58 */     if (this.building) throw new IllegalStateException("Mesh.begin() called while already building.");
/*     */     
/*  60 */     this.verticesPointer = this.verticesPointerStart;
/*  61 */     this.vertexI = 0;
/*  62 */     this.indicesCount = 0;
/*     */     
/*  64 */     this.building = true;
/*     */     
/*  66 */     if (Utils.rendering3D) {
/*  67 */       class_243 camera = MeteorClient.mc.field_1773.method_19418().method_71156();
/*     */       
/*  69 */       this.cameraX = camera.field_1352;
/*  70 */       this.cameraZ = camera.field_1350;
/*     */     } else {
/*     */       
/*  73 */       this.cameraX = 0.0D;
/*  74 */       this.cameraZ = 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public MeshBuilder vec3(double x, double y, double z) {
/*  79 */     debugVertexBufferCapacity();
/*     */     
/*  81 */     long p = this.verticesPointer;
/*     */     
/*  83 */     MemoryUtil.memPutFloat(p, (float)(x - this.cameraX));
/*  84 */     MemoryUtil.memPutFloat(p + 4L, (float)y);
/*  85 */     MemoryUtil.memPutFloat(p + 8L, (float)(z - this.cameraZ));
/*     */     
/*  87 */     this.verticesPointer += 12L;
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public MeshBuilder vec2(double x, double y) {
/*  92 */     debugVertexBufferCapacity();
/*     */     
/*  94 */     long p = this.verticesPointer;
/*     */     
/*  96 */     MemoryUtil.memPutFloat(p, (float)x);
/*  97 */     MemoryUtil.memPutFloat(p + 4L, (float)y);
/*     */     
/*  99 */     this.verticesPointer += 8L;
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public MeshBuilder color(Color c) {
/* 104 */     debugVertexBufferCapacity();
/*     */     
/* 106 */     long p = this.verticesPointer;
/*     */     
/* 108 */     MemoryUtil.memPutByte(p, (byte)c.r);
/* 109 */     MemoryUtil.memPutByte(p + 1L, (byte)c.g);
/* 110 */     MemoryUtil.memPutByte(p + 2L, (byte)c.b);
/* 111 */     MemoryUtil.memPutByte(p + 3L, (byte)(int)(c.a * (float)this.alpha));
/*     */     
/* 113 */     this.verticesPointer += 4L;
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public int next() {
/* 118 */     return this.vertexI++;
/*     */   }
/*     */   
/*     */   public void line(int i1, int i2) {
/* 122 */     debugIndexBufferCapacity();
/*     */     
/* 124 */     long p = this.indicesPointer + this.indicesCount * 4L;
/*     */     
/* 126 */     MemoryUtil.memPutInt(p, i1);
/* 127 */     MemoryUtil.memPutInt(p + 4L, i2);
/*     */     
/* 129 */     this.indicesCount += 2;
/*     */   }
/*     */   
/*     */   public void quad(int i1, int i2, int i3, int i4) {
/* 133 */     debugIndexBufferCapacity();
/*     */     
/* 135 */     long p = this.indicesPointer + this.indicesCount * 4L;
/*     */     
/* 137 */     MemoryUtil.memPutInt(p, i1);
/* 138 */     MemoryUtil.memPutInt(p + 4L, i2);
/* 139 */     MemoryUtil.memPutInt(p + 8L, i3);
/*     */     
/* 141 */     MemoryUtil.memPutInt(p + 12L, i3);
/* 142 */     MemoryUtil.memPutInt(p + 16L, i4);
/* 143 */     MemoryUtil.memPutInt(p + 20L, i1);
/*     */     
/* 145 */     this.indicesCount += 6;
/*     */   }
/*     */   
/*     */   public void triangle(int i1, int i2, int i3) {
/* 149 */     debugIndexBufferCapacity();
/*     */     
/* 151 */     long p = this.indicesPointer + this.indicesCount * 4L;
/*     */     
/* 153 */     MemoryUtil.memPutInt(p, i1);
/* 154 */     MemoryUtil.memPutInt(p + 4L, i2);
/* 155 */     MemoryUtil.memPutInt(p + 8L, i3);
/*     */     
/* 157 */     this.indicesCount += 3;
/*     */   }
/*     */   
/*     */   public void ensureQuadCapacity() {
/* 161 */     ensureCapacity(4, 6);
/*     */   }
/*     */   
/*     */   public void ensureTriCapacity() {
/* 165 */     ensureCapacity(3, 3);
/*     */   }
/*     */   
/*     */   public void ensureLineCapacity() {
/* 169 */     ensureCapacity(2, 2);
/*     */   }
/*     */   
/*     */   public void ensureCapacity(int vertexCount, int indexCount) {
/* 173 */     if (DEBUG && indexCount % this.primitiveIndicesCount != 0) {
/* 174 */       throw new IllegalArgumentException("Unexpected amount of indices written to MeshBuilder.");
/*     */     }
/*     */     
/* 177 */     if (this.vertices == null || this.indices == null) {
/* 178 */       allocateBuffers(1024, 2048);
/*     */       
/*     */       return;
/*     */     } 
/* 182 */     if ((this.vertexI + vertexCount) * this.primitiveVerticesSize >= this.vertices.capacity()) {
/* 183 */       int offset = getVerticesOffset();
/* 184 */       int newSize = Math.max(this.vertices.capacity() * 2, this.vertices.capacity() + vertexCount * this.primitiveVerticesSize);
/* 185 */       ByteBuffer newVertices = BufferUtils.createByteBuffer(newSize);
/* 186 */       MemoryUtil.memCopy(MemoryUtil.memAddress0(this.vertices), MemoryUtil.memAddress0(newVertices), offset);
/*     */       
/* 188 */       this.vertices = newVertices;
/* 189 */       this.verticesPointerStart = MemoryUtil.memAddress0(this.vertices);
/* 190 */       this.verticesPointer = this.verticesPointerStart + offset;
/*     */     } 
/*     */     
/* 193 */     if ((this.indicesCount + indexCount) * 4 >= this.indices.capacity()) {
/* 194 */       int newSize = Math.max(this.indices.capacity() * 2, this.indices.capacity() + indexCount * 4);
/*     */       
/* 196 */       ByteBuffer newIndices = BufferUtils.createByteBuffer(newSize);
/* 197 */       MemoryUtil.memCopy(MemoryUtil.memAddress0(this.indices), MemoryUtil.memAddress0(newIndices), this.indicesCount * 4L);
/*     */       
/* 199 */       this.indices = newIndices;
/* 200 */       this.indicesPointer = MemoryUtil.memAddress0(this.indices);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void allocateBuffers(int vertexCount, int indexCount) {
/* 205 */     this.vertices = BufferUtils.createByteBuffer(this.primitiveVerticesSize * vertexCount);
/* 206 */     this.verticesPointer = this.verticesPointerStart = MemoryUtil.memAddress0(this.vertices);
/*     */     
/* 208 */     this.indices = BufferUtils.createByteBuffer(indexCount * 4);
/* 209 */     this.indicesPointer = MemoryUtil.memAddress0(this.indices);
/*     */   }
/*     */   
/*     */   public void end() {
/* 213 */     if (!this.building) throw new IllegalStateException("Mesh.end() called while not building.");
/*     */     
/* 215 */     this.building = false;
/*     */   }
/*     */   
/*     */   public boolean isBuilding() {
/* 219 */     return this.building;
/*     */   }
/*     */   
/*     */   public GpuBuffer getVertexBuffer() {
/* 223 */     this.vertices.limit(getVerticesOffset());
/* 224 */     return this.format.uploadImmediateVertexBuffer(this.vertices);
/*     */   }
/*     */   
/*     */   public GpuBuffer getIndexBuffer() {
/* 228 */     this.indices.limit(this.indicesCount * 4);
/* 229 */     return this.format.uploadImmediateIndexBuffer(this.indices);
/*     */   }
/*     */   
/*     */   public int getIndicesCount() {
/* 233 */     return this.indicesCount;
/*     */   }
/*     */   
/*     */   private int getVerticesOffset() {
/* 237 */     return (int)(this.verticesPointer - this.verticesPointerStart);
/*     */   }
/*     */   
/*     */   private void debugVertexBufferCapacity() {
/* 241 */     if (DEBUG && (this.vertices == null || this.vertexI * this.primitiveVerticesSize >= this.vertices.capacity())) {
/* 242 */       throw new IndexOutOfBoundsException("Vertices written to MeshBuilder without calling 'ensureCapacity()' first!");
/*     */     }
/*     */   }
/*     */   
/*     */   private void debugIndexBufferCapacity() {
/* 247 */     if (DEBUG && (this.indices == null || this.indicesCount * 4 >= this.indices.capacity()))
/* 248 */       throw new IndexOutOfBoundsException("Indices written to MeshBuilder without calling 'ensureCapacity()' first!"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\MeshBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */