/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.entity.player.PlaceBlockEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.NoGhostBlocks;
/*    */ import meteordevelopment.orbit.ICancellable;
/*    */ import net.minecraft.class_1747;
/*    */ import net.minecraft.class_1750;
/*    */ import net.minecraft.class_2680;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1747.class})
/*    */ public abstract class BlockItemMixin
/*    */ {
/*    */   @Shadow
/*    */   protected abstract class_2680 method_7707(class_1750 paramclass_1750);
/*    */   
/*    */   @Inject(method = {"method_7708(Lnet/minecraft/class_1750;Lnet/minecraft/class_2680;)Z"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onPlace(class_1750 context, class_2680 state, CallbackInfoReturnable<Boolean> info) {
/* 29 */     if (!context.method_8045().method_8608())
/*    */       return; 
/* 31 */     if (((PlaceBlockEvent)MeteorClient.EVENT_BUS.post((ICancellable)PlaceBlockEvent.get(context.method_8037(), state.method_26204()))).isCancelled()) {
/* 32 */       info.setReturnValue(Boolean.valueOf(true));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyVariable(method = {"method_7712(Lnet/minecraft/class_1750;)Lnet/minecraft/class_1269;"}, ordinal = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_2680;method_27852(Lnet/minecraft/class_2248;)Z"))
/*    */   private class_2680 modifyState(class_2680 state, class_1750 context) {
/* 45 */     NoGhostBlocks noGhostBlocks = (NoGhostBlocks)Modules.get().get(NoGhostBlocks.class);
/*    */     
/* 47 */     if (noGhostBlocks.isActive() && ((Boolean)noGhostBlocks.placing.get()).booleanValue()) {
/* 48 */       return method_7707(context);
/*    */     }
/*    */     
/* 51 */     return state;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BlockItemMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */