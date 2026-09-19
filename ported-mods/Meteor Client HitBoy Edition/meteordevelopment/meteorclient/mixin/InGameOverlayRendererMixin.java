/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import net.minecraft.class_1058;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ import net.minecraft.class_4603;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_4603.class})
/*    */ public abstract class InGameOverlayRendererMixin
/*    */ {
/*    */   @Inject(method = {"method_23070"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onRenderFireOverlay(class_4587 matrices, class_4597 vertexConsumers, class_1058 sprite, CallbackInfo ci) {
/* 24 */     if (((NoRender)Modules.get().get(NoRender.class)).noFireOverlay()) ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_23069"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onRenderUnderwaterOverlay(class_310 client, class_4587 matrices, class_4597 vertexConsumers, CallbackInfo ci) {
/* 29 */     if (((NoRender)Modules.get().get(NoRender.class)).noLiquidOverlay()) ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_23068"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void render(class_1058 sprite, class_4587 matrices, class_4597 vertexConsumers, CallbackInfo ci) {
/* 34 */     if (((NoRender)Modules.get().get(NoRender.class)).noInWallOverlay()) ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\InGameOverlayRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */