/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.render.RenderItemEntityEvent;
/*    */ import meteordevelopment.orbit.ICancellable;
/*    */ import net.minecraft.class_10039;
/*    */ import net.minecraft.class_10442;
/*    */ import net.minecraft.class_11659;
/*    */ import net.minecraft.class_12075;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_916;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_916.class})
/*    */ public abstract class ItemEntityRendererMixin
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_10442 field_55293;
/*    */   
/*    */   @Inject(method = {"method_3996(Lnet/minecraft/class_10039;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;Lnet/minecraft/class_12075;)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void renderStack(class_10039 itemEntityRenderState, class_4587 matrixStack, class_11659 orderedRenderCommandQueue, class_12075 arg, CallbackInfo ci) {
/* 33 */     RenderItemEntityEvent event = (RenderItemEntityEvent)MeteorClient.EVENT_BUS.post((ICancellable)RenderItemEntityEvent.get(itemEntityRenderState, MeteorClient.mc.method_61966().method_60637(true), matrixStack, null, itemEntityRenderState.field_61820, this.field_55293, orderedRenderCommandQueue));
/* 34 */     if (event.isCancelled()) ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ItemEntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */