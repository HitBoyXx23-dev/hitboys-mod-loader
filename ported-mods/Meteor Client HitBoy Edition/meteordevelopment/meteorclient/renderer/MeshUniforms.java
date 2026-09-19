/*    */ package meteordevelopment.meteorclient.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*    */ import com.mojang.blaze3d.buffers.Std140Builder;
/*    */ import com.mojang.blaze3d.buffers.Std140SizeCalculator;
/*    */ import java.nio.ByteBuffer;
/*    */ import net.minecraft.class_11280;
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Matrix4fc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MeshUniforms
/*    */ {
/* 17 */   public static final int SIZE = (new Std140SizeCalculator())
/* 18 */     .putMat4f()
/* 19 */     .putMat4f()
/* 20 */     .get();
/*    */   
/* 22 */   private static final Data DATA = new Data();
/*    */   
/* 24 */   private static final class_11280<Data> STORAGE = new class_11280("Meteor - Mesh UBO", SIZE, 16);
/*    */   
/*    */   public static void flipFrame() {
/* 27 */     STORAGE.method_71100();
/*    */   }
/*    */   
/*    */   public static GpuBufferSlice write(Matrix4f proj, Matrix4f modelView) {
/* 31 */     DATA.proj = proj;
/* 32 */     DATA.modelView = modelView;
/*    */     
/* 34 */     return STORAGE.method_71102(DATA);
/*    */   }
/*    */   
/*    */   private static final class Data
/*    */     implements class_11280.class_11281 {
/*    */     private Matrix4f proj;
/*    */     private Matrix4f modelView;
/*    */     
/*    */     public void method_71104(ByteBuffer buffer) {
/* 43 */       Std140Builder.intoBuffer(buffer)
/* 44 */         .putMat4f((Matrix4fc)this.proj)
/* 45 */         .putMat4f((Matrix4fc)this.modelView);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(Object o) {
/* 50 */       return false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\MeshUniforms.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */