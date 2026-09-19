/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.renderer.MeshUniforms;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.InventoryTweaks;
/*    */ import meteordevelopment.meteorclient.utils.render.postprocess.ChamsShader;
/*    */ import meteordevelopment.meteorclient.utils.render.postprocess.OutlineUniforms;
/*    */ import meteordevelopment.meteorclient.utils.render.postprocess.PostProcessShader;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({RenderSystem.class})
/*    */ public abstract class RenderSystemMixin
/*    */ {
/*    */   @Inject(method = {"flipFrame"}, at = {@At("TAIL")})
/*    */   private static void meteor$flipFrame(CallbackInfo info) {
/* 26 */     MeshUniforms.flipFrame();
/* 27 */     PostProcessShader.flipFrame();
/* 28 */     ChamsShader.flipFrame();
/* 29 */     OutlineUniforms.flipFrame();
/*    */     
/* 31 */     if (Modules.get() == null || MeteorClient.mc.field_1724 == null)
/* 32 */       return;  if (((InventoryTweaks)Modules.get().get(InventoryTweaks.class)).frameInput()) ((MinecraftClientAccessor)MeteorClient.mc).meteor$handleInputEvents(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\RenderSystemMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */