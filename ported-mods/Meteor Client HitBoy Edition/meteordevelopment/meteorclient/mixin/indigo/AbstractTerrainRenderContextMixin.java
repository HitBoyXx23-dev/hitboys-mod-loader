/*    */ package meteordevelopment.meteorclient.mixin.indigo;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Xray;
/*    */ import net.fabricmc.fabric.impl.client.indigo.renderer.mesh.MutableQuadViewImpl;
/*    */ import net.fabricmc.fabric.impl.client.indigo.renderer.render.AbstractTerrainRenderContext;
/*    */ import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({AbstractTerrainRenderContext.class})
/*    */ public abstract class AbstractTerrainRenderContextMixin
/*    */ {
/*    */   @Final
/*    */   @Shadow(remap = false)
/*    */   protected BlockRenderInfo blockInfo;
/*    */   
/*    */   @Inject(method = {"bufferQuad"}, at = {@At(value = "INVOKE", target = "Lnet/fabricmc/fabric/impl/client/indigo/renderer/render/AbstractTerrainRenderContext;bufferQuad(Lnet/fabricmc/fabric/impl/client/indigo/renderer/mesh/MutableQuadViewImpl;Lnet/minecraft/class_4588;)V")}, cancellable = true)
/*    */   private void onBufferQuad(MutableQuadViewImpl quad, CallbackInfo ci) {
/* 27 */     int alpha = Xray.getAlpha(this.blockInfo.blockState, this.blockInfo.blockPos);
/*    */     
/* 29 */     if (alpha == 0) { ci.cancel(); }
/* 30 */     else if (alpha != -1)
/* 31 */     { for (int i = 0; i < 4; i++) {
/* 32 */         quad.color(i, rewriteQuadAlpha(quad.color(i), alpha));
/*    */       } }
/*    */   
/*    */   }
/*    */   
/*    */   @Unique
/*    */   private int rewriteQuadAlpha(int color, int alpha) {
/* 39 */     return (alpha & 0xFF) << 24 | color & 0xFFFFFF;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\indigo\AbstractTerrainRenderContextMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */