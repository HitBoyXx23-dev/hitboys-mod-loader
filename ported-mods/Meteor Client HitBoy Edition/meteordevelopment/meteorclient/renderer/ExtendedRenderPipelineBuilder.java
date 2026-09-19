/*    */ package meteordevelopment.meteorclient.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import meteordevelopment.meteorclient.mixininterface.IRenderPipeline;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExtendedRenderPipelineBuilder
/*    */   extends RenderPipeline.Builder
/*    */ {
/*    */   private boolean lineSmooth;
/*    */   
/*    */   public ExtendedRenderPipelineBuilder(RenderPipeline.Snippet... snippets) {
/* 15 */     for (RenderPipeline.Snippet snippet : snippets) {
/* 16 */       withSnippet(snippet);
/*    */     }
/*    */   }
/*    */   
/*    */   public ExtendedRenderPipelineBuilder withLineSmooth() {
/* 21 */     this.lineSmooth = true;
/* 22 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderPipeline build() {
/* 27 */     RenderPipeline pipeline = super.build();
/* 28 */     ((IRenderPipeline)pipeline).meteor$setLineSmooth(this.lineSmooth);
/*    */     
/* 30 */     return pipeline;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\ExtendedRenderPipelineBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */