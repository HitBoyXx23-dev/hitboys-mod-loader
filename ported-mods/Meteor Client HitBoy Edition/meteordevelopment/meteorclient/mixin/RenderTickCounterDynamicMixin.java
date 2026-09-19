/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.Timer;
/*    */ import net.minecraft.class_9779;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_9779.class_9781.class})
/*    */ public abstract class RenderTickCounterDynamicMixin
/*    */ {
/*    */   @Shadow
/*    */   private float field_51958;
/*    */   
/*    */   @Inject(method = {"method_60639(J)I"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/class_9779$class_9781;field_51962:J", opcode = 181)})
/*    */   private void onBeingRenderTick(long a, CallbackInfoReturnable<Integer> info) {
/* 25 */     this.field_51958 *= (float)((Timer)Modules.get().get(Timer.class)).getMultiplier();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\RenderTickCounterDynamicMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */