/*    */ package meteordevelopment.meteorclient.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*    */ import com.mojang.blaze3d.vertex.VertexFormat;
/*    */ import meteordevelopment.meteorclient.utils.PreInit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FullScreenRenderer
/*    */ {
/*    */   public static GpuBuffer vbo;
/*    */   public static GpuBuffer ibo;
/*    */   @Deprecated(forRemoval = true)
/*    */   public static MeshBuilder mesh;
/*    */   
/*    */   @PreInit
/*    */   public static void init() {
/* 27 */     mesh = new MeshBuilder(MeteorVertexFormats.POS2, VertexFormat.class_5596.field_27379, 4, 6);
/*    */     
/* 29 */     mesh.begin();
/*    */     
/* 31 */     mesh.quad(mesh
/* 32 */         .vec2(-1.0D, -1.0D).next(), mesh
/* 33 */         .vec2(-1.0D, 1.0D).next(), mesh
/* 34 */         .vec2(1.0D, 1.0D).next(), mesh
/* 35 */         .vec2(1.0D, -1.0D).next());
/*    */ 
/*    */     
/* 38 */     mesh.end();
/*    */     
/* 40 */     vbo = mesh.getVertexBuffer();
/* 41 */     ibo = mesh.getIndexBuffer();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\FullScreenRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */