/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.Collisions;
/*    */ import net.minecraft.class_2784;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_2784.class})
/*    */ public abstract class WorldBorderMixin
/*    */ {
/*    */   @Inject(method = {"method_39459"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void canCollide(CallbackInfoReturnable<Boolean> info) {
/* 20 */     if (((Collisions)Modules.get().get(Collisions.class)).ignoreBorder()) info.setReturnValue(Boolean.valueOf(false)); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_11952(Lnet/minecraft/class_2338;)Z"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void contains(CallbackInfoReturnable<Boolean> info) {
/* 25 */     if (((Collisions)Modules.get().get(Collisions.class)).ignoreBorder()) info.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\WorldBorderMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */