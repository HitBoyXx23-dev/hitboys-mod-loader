/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import net.minecraft.class_5632;
/*    */ import net.minecraft.class_5684;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_5684.class})
/*    */ public interface TooltipComponentMixin
/*    */ {
/*    */   @Inject(method = {"method_32663(Lnet/minecraft/class_5632;)Lnet/minecraft/class_5684;"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void shortcutMeteorTooltipData(class_5632 tooltipData, CallbackInfoReturnable<class_5684> cir) {
/* 20 */     if (tooltipData instanceof meteordevelopment.meteorclient.utils.tooltip.MeteorTooltipData) cir.setReturnValue(null); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\TooltipComponentMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */