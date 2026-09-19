/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.blaze3d.pipeline.RenderPipeline;
/*    */ import meteordevelopment.meteorclient.mixininterface.IRenderPipeline;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({RenderPipeline.class})
/*    */ public abstract class RenderPipelineMixin
/*    */   implements IRenderPipeline
/*    */ {
/*    */   @Unique
/*    */   private boolean lineSmooth;
/*    */   
/*    */   public void meteor$setLineSmooth(boolean lineSmooth) {
/* 20 */     this.lineSmooth = lineSmooth;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean meteor$getLineSmooth() {
/* 25 */     return this.lineSmooth;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\RenderPipelineMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */