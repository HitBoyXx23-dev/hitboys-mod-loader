/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import net.minecraft.class_10633;
/*    */ import net.minecraft.class_11659;
/*    */ import net.minecraft.class_11950;
/*    */ import net.minecraft.class_2586;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_822;
/*    */ import net.minecraft.class_827;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_822.class})
/*    */ public abstract class BeaconBlockEntityRendererMixin<T extends class_2586 & class_10633>
/*    */   implements class_827<T, class_11950>
/*    */ {
/*    */   @Inject(method = {"method_3545(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;Lnet/minecraft/class_2960;FFIIIFF)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onRender(class_4587 matrixStack, class_11659 orderedRenderCommandQueue, class_2960 textureId, float tickProgress, float heightScale, int i, int j, int k, float f, float g, CallbackInfo ci) {
/* 27 */     if (((NoRender)Modules.get().get(NoRender.class)).noBeaconBeams()) ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BeaconBlockEntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */