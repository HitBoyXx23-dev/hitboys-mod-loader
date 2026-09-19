/*    */ package meteordevelopment.meteorclient.utils.render.postprocess;
/*    */ 
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import java.util.Objects;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.mixininterface.IWorldRenderer;
/*    */ import meteordevelopment.meteorclient.utils.render.CustomOutlineVertexConsumerProvider;
/*    */ import net.minecraft.class_1297;
/*    */ 
/*    */ public abstract class EntityShader extends PostProcessShader {
/*    */   public final CustomOutlineVertexConsumerProvider vertexConsumerProvider;
/*    */   
/*    */   protected EntityShader(RenderPipeline pipeline) {
/* 14 */     super(pipeline);
/* 15 */     this.vertexConsumerProvider = new CustomOutlineVertexConsumerProvider();
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract boolean shouldDraw(class_1297 paramclass_1297);
/*    */   
/*    */   protected void preDraw() {
/* 22 */     ((IWorldRenderer)MeteorClient.mc.field_1769).meteor$pushEntityOutlineFramebuffer(this.framebuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void postDraw() {
/* 27 */     ((IWorldRenderer)MeteorClient.mc.field_1769).meteor$popEntityOutlineFramebuffer();
/*    */   }
/*    */   
/*    */   public void submitVertices() {
/* 31 */     Objects.requireNonNull(this.vertexConsumerProvider); submitVertices(this.vertexConsumerProvider::draw);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\postprocess\EntityShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */