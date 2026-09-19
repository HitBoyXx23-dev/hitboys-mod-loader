/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.render.TooltipDataEvent;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_5537;
/*    */ import net.minecraft.class_5632;
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
/*    */ @Mixin({class_5537.class})
/*    */ public class BundleItemMixin
/*    */ {
/*    */   @Inject(method = {"method_32346"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onTooltipData(class_1799 stack, CallbackInfoReturnable<Optional<class_5632>> cir) {
/* 24 */     TooltipDataEvent event = (TooltipDataEvent)MeteorClient.EVENT_BUS.post(TooltipDataEvent.get(stack));
/* 25 */     if (event.tooltipData != null)
/* 26 */       cir.setReturnValue(Optional.of(event.tooltipData)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BundleItemMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */