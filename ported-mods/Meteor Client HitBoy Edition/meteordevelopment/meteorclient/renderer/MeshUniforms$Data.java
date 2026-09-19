/*    */ package meteordevelopment.meteorclient.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.Std140Builder;
/*    */ import java.nio.ByteBuffer;
/*    */ import net.minecraft.class_11280;
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Matrix4fc;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class Data
/*    */   implements class_11280.class_11281
/*    */ {
/*    */   private Matrix4f proj;
/*    */   private Matrix4f modelView;
/*    */   
/*    */   public void method_71104(ByteBuffer buffer) {
/* 43 */     Std140Builder.intoBuffer(buffer)
/* 44 */       .putMat4f((Matrix4fc)this.proj)
/* 45 */       .putMat4f((Matrix4fc)this.modelView);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 50 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\MeshUniforms$Data.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */