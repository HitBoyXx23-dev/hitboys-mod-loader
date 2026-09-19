/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.render.ApplyTransformationEvent;
/*    */ import meteordevelopment.orbit.ICancellable;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_804;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_804.class})
/*    */ public abstract class TransformationMixin
/*    */ {
/*    */   @Inject(method = {"method_23075"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onApply(boolean leftHanded, class_4587.class_4665 entry, CallbackInfo info) {
/* 21 */     ApplyTransformationEvent event = (ApplyTransformationEvent)MeteorClient.EVENT_BUS.post((ICancellable)ApplyTransformationEvent.get((class_804)this, leftHanded));
/* 22 */     if (event.isCancelled()) info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\TransformationMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */