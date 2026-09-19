/*    */ package meteordevelopment.meteorclient.mixin.sodium;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer;
/*    */ import net.caffeinemc.mods.sodium.client.util.FogParameters;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({SodiumWorldRenderer.class})
/*    */ public class SodiumWorldRendererMixin
/*    */ {
/*    */   @Unique
/* 20 */   private static final FogParameters DISABLED_FOG = new FogParameters(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
/*    */   
/*    */   @ModifyVariable(method = {"setupTerrain"}, at = @At("HEAD"), argsOnly = true)
/*    */   private FogParameters modifyFogParameters(FogParameters fogParameters) {
/* 24 */     if (Modules.get() == null) return fogParameters;
/*    */     
/* 26 */     if (((NoRender)Modules.get().get(NoRender.class)).noFog()) return DISABLED_FOG;
/*    */     
/* 28 */     return fogParameters;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\sodium\SodiumWorldRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */