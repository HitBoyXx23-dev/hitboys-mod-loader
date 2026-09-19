/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.TridentBoost;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import net.minecraft.class_1309;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1835;
/*    */ import net.minecraft.class_1937;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1835.class})
/*    */ public abstract class TridentItemMixin
/*    */ {
/*    */   @Inject(method = {"method_7840"}, at = {@At("HEAD")})
/*    */   private void onStoppedUsingHead(class_1799 stack, class_1937 world, class_1309 user, int remainingUseTicks, CallbackInfoReturnable<Boolean> info) {
/* 29 */     if (user == MeteorClient.mc.field_1724) Utils.isReleasingTrident = true; 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_7840"}, at = {@At("TAIL")})
/*    */   private void onStoppedUsingTail(class_1799 stack, class_1937 world, class_1309 user, int remainingUseTicks, CallbackInfoReturnable<Boolean> info) {
/* 34 */     if (user == MeteorClient.mc.field_1724) Utils.isReleasingTrident = false; 
/*    */   }
/*    */   
/*    */   @ModifyArgs(method = {"method_7840"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_1657;method_5762(DDD)V"))
/*    */   private void modifyVelocity(Args args) {
/* 39 */     TridentBoost tridentBoost = (TridentBoost)Modules.get().get(TridentBoost.class);
/*    */     
/* 41 */     args.set(0, Double.valueOf(((Double)args.get(0)).doubleValue() * tridentBoost.getMultiplier()));
/* 42 */     args.set(1, Double.valueOf(((Double)args.get(1)).doubleValue() * tridentBoost.getMultiplier()));
/* 43 */     args.set(2, Double.valueOf(((Double)args.get(2)).doubleValue() * tridentBoost.getMultiplier()));
/*    */   }
/*    */   
/*    */   @ModifyExpressionValue(method = {"method_7836"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1657;method_5721()Z")})
/*    */   private boolean isInWaterUse(boolean original) {
/* 48 */     TridentBoost tridentBoost = (TridentBoost)Modules.get().get(TridentBoost.class);
/*    */     
/* 50 */     return (tridentBoost.allowOutOfWater() || original);
/*    */   }
/*    */   
/*    */   @ModifyExpressionValue(method = {"method_7840"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1657;method_5721()Z")})
/*    */   private boolean isInWaterPostUse(boolean original) {
/* 55 */     TridentBoost tridentBoost = (TridentBoost)Modules.get().get(TridentBoost.class);
/*    */     
/* 57 */     return (tridentBoost.allowOutOfWater() || original);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\TridentItemMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */