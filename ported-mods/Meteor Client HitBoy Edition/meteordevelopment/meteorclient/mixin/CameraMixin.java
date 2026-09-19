/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.sugar.Local;
/*    */ import meteordevelopment.meteorclient.mixininterface.ICamera;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.CameraTweaks;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.FreeLook;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Freecam;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.HighwayBuilder;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_3532;
/*    */ import net.minecraft.class_4184;
/*    */ import net.minecraft.class_5636;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*    */ 
/*    */ @Mixin({class_4184.class})
/*    */ public abstract class CameraMixin
/*    */   implements ICamera
/*    */ {
/*    */   @Shadow
/*    */   private boolean field_18719;
/*    */   @Shadow
/*    */   private float field_18718;
/*    */   @Shadow
/*    */   private float field_18717;
/*    */   
/*    */   @Shadow
/*    */   protected abstract void method_19325(float paramFloat1, float paramFloat2);
/*    */   
/*    */   @Inject(method = {"method_19334"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void getSubmergedFluidState(CallbackInfoReturnable<class_5636> ci) {
/* 42 */     if (((NoRender)Modules.get().get(NoRender.class)).noLiquidOverlay()) ci.setReturnValue(class_5636.field_27888); 
/*    */   }
/*    */   
/*    */   @ModifyVariable(method = {"method_19318"}, at = @At("HEAD"), ordinal = 0, argsOnly = true)
/*    */   private float modifyClipToSpace(float d) {
/* 47 */     if (((Freecam)Modules.get().get(Freecam.class)).isActive()) return 0.0F;
/*    */     
/* 49 */     CameraTweaks cameraTweaks = (CameraTweaks)Modules.get().get(CameraTweaks.class);
/* 50 */     return cameraTweaks.isActive() ? (float)cameraTweaks.distance : d;
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_19318"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onClipToSpace(float desiredCameraDistance, CallbackInfoReturnable<Float> info) {
/* 55 */     if (((CameraTweaks)Modules.get().get(CameraTweaks.class)).clip()) {
/* 56 */       info.setReturnValue(Float.valueOf(desiredCameraDistance));
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_19321"}, at = {@At("TAIL")})
/*    */   private void onUpdateTail(class_1937 area, class_1297 focusedEntity, boolean thirdPerson, boolean inverseView, float tickProgress, CallbackInfo ci) {
/* 62 */     if (Modules.get().isActive(Freecam.class)) {
/* 63 */       this.field_18719 = true;
/*    */     }
/*    */   }
/*    */   
/*    */   @ModifyArgs(method = {"method_19321"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_4184;method_19327(DDD)V"))
/*    */   private void onUpdateSetPosArgs(Args args, @Local(argsOnly = true) float tickDelta) {
/* 69 */     Freecam freecam = (Freecam)Modules.get().get(Freecam.class);
/*    */     
/* 71 */     if (freecam.isActive()) {
/* 72 */       args.set(0, Double.valueOf(freecam.getX(tickDelta)));
/* 73 */       args.set(1, Double.valueOf(freecam.getY(tickDelta)));
/* 74 */       args.set(2, Double.valueOf(freecam.getZ(tickDelta)));
/*    */     } 
/*    */   }
/*    */   
/*    */   @ModifyArgs(method = {"method_19321"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_4184;method_19325(FF)V"))
/*    */   private void onUpdateSetRotationArgs(Args args, @Local(argsOnly = true) float tickDelta) {
/* 80 */     Freecam freecam = (Freecam)Modules.get().get(Freecam.class);
/* 81 */     FreeLook freeLook = (FreeLook)Modules.get().get(FreeLook.class);
/*    */     
/* 83 */     if (freecam.isActive()) {
/* 84 */       args.set(0, Float.valueOf((float)freecam.getYaw(tickDelta)));
/* 85 */       args.set(1, Float.valueOf((float)freecam.getPitch(tickDelta)));
/*    */     }
/* 87 */     else if (Modules.get().isActive(HighwayBuilder.class)) {
/* 88 */       args.set(0, Float.valueOf(this.field_18718));
/* 89 */       args.set(1, Float.valueOf(this.field_18717));
/*    */     }
/* 91 */     else if (freeLook.isActive()) {
/* 92 */       args.set(0, Float.valueOf(freeLook.cameraYaw));
/* 93 */       args.set(1, Float.valueOf(freeLook.cameraPitch));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$setRot(double yaw, double pitch) {
/* 99 */     method_19325((float)yaw, (float)class_3532.method_15350(pitch, -90.0D, 90.0D));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\CameraMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */