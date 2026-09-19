/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*     */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.entity.EntityMoveEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.JumpVelocityMultiplierEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
/*     */ import meteordevelopment.meteorclient.mixininterface.ICamera;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.combat.Hitboxes;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Flight;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Jesus;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.NoFall;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.NoSlow;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Velocity;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.elytrafly.ElytraFly;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.ESP;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.FreeLook;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.Freecam;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*     */ import meteordevelopment.meteorclient.systems.modules.world.HighwayBuilder;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1313;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_4050;
/*     */ import net.minecraft.class_4184;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_1297.class})
/*     */ public abstract class EntityMixin
/*     */ {
/*     */   @ModifyExpressionValue(method = {"method_5692"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_3610;method_15758(Lnet/minecraft/class_1922;Lnet/minecraft/class_2338;)Lnet/minecraft/class_243;")})
/*     */   private class_243 updateMovementInFluidFluidStateGetVelocity(class_243 vec) {
/*  48 */     if (this != MeteorClient.mc.field_1724) return vec;
/*     */     
/*  50 */     Velocity velocity = (Velocity)Modules.get().get(Velocity.class);
/*  51 */     if (velocity.isActive() && ((Boolean)velocity.liquids.get()).booleanValue()) {
/*  52 */       vec = vec.method_18805(velocity.getHorizontal(velocity.liquidsHorizontal), velocity.getVertical(velocity.liquidsVertical), velocity.getHorizontal(velocity.liquidsHorizontal));
/*     */     }
/*     */     
/*  55 */     return vec;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5799"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void isTouchingWater(CallbackInfoReturnable<Boolean> info) {
/*  60 */     if (this != MeteorClient.mc.field_1724)
/*     */       return; 
/*  62 */     if (((Flight)Modules.get().get(Flight.class)).isActive()) info.setReturnValue(Boolean.valueOf(false)); 
/*  63 */     if (((NoSlow)Modules.get().get(NoSlow.class)).fluidDrag()) info.setReturnValue(Boolean.valueOf(false)); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5771"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void isInLava(CallbackInfoReturnable<Boolean> info) {
/*  68 */     if (this != MeteorClient.mc.field_1724)
/*     */       return; 
/*  70 */     if (((Flight)Modules.get().get(Flight.class)).isActive()) info.setReturnValue(Boolean.valueOf(false)); 
/*  71 */     if (((NoSlow)Modules.get().get(NoSlow.class)).fluidDrag()) info.setReturnValue(Boolean.valueOf(false)); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5700"}, at = {@At("HEAD")})
/*     */   private void onBubbleColumnSurfaceCollision(CallbackInfo info) {
/*  76 */     if (this != MeteorClient.mc.field_1724)
/*     */       return; 
/*  78 */     Jesus jesus = (Jesus)Modules.get().get(Jesus.class);
/*  79 */     if (jesus.isActive()) {
/*  80 */       jesus.isInBubbleColumn = true;
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5764"}, at = {@At("HEAD")})
/*     */   private void onBubbleColumnCollision(CallbackInfo info) {
/*  86 */     if (this != MeteorClient.mc.field_1724)
/*     */       return; 
/*  88 */     Jesus jesus = (Jesus)Modules.get().get(Jesus.class);
/*  89 */     if (jesus.isActive()) {
/*  90 */       jesus.isInBubbleColumn = true;
/*     */     }
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_5790"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1297;method_5869()Z")})
/*     */   private boolean isSubmergedInWater(boolean submerged) {
/*  96 */     if (this != MeteorClient.mc.field_1724) return submerged;
/*     */     
/*  98 */     if (((NoSlow)Modules.get().get(NoSlow.class)).fluidDrag()) return false; 
/*  99 */     if (((Flight)Modules.get().get(Flight.class)).isActive()) return false; 
/* 100 */     return submerged;
/*     */   }
/*     */   
/*     */   @ModifyArgs(method = {"method_5697(Lnet/minecraft/class_1297;)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_1297;method_5762(DDD)V"))
/*     */   private void onPushAwayFrom(Args args, class_1297 entity) {
/* 105 */     Velocity velocity = (Velocity)Modules.get().get(Velocity.class);
/*     */ 
/*     */     
/* 108 */     if (this == MeteorClient.mc.field_1724 && velocity.isActive() && ((Boolean)velocity.entityPush.get()).booleanValue())
/* 109 */     { double multiplier = ((Double)velocity.entityPushAmount.get()).doubleValue();
/* 110 */       args.set(0, Double.valueOf(((Double)args.get(0)).doubleValue() * multiplier));
/* 111 */       args.set(2, Double.valueOf(((Double)args.get(2)).doubleValue() * multiplier));
/*     */        }
/*     */     
/* 114 */     else if (entity instanceof FakePlayerEntity) { FakePlayerEntity player = (FakePlayerEntity)entity; if (player.doNotPush) {
/* 115 */         args.set(0, Double.valueOf(0.0D));
/* 116 */         args.set(2, Double.valueOf(0.0D));
/*     */       }  }
/*     */   
/*     */   }
/*     */   @ModifyReturnValue(method = {"method_23313"}, at = {@At("RETURN")})
/*     */   private float onGetJumpVelocityMultiplier(float original) {
/* 122 */     if (this == MeteorClient.mc.field_1724) {
/* 123 */       JumpVelocityMultiplierEvent event = (JumpVelocityMultiplierEvent)MeteorClient.EVENT_BUS.post(JumpVelocityMultiplierEvent.get());
/* 124 */       return original * event.multiplier;
/*     */     } 
/*     */     
/* 127 */     return original;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5784"}, at = {@At("HEAD")})
/*     */   private void onMove(class_1313 type, class_243 movement, CallbackInfo info) {
/* 132 */     if (this == MeteorClient.mc.field_1724) {
/* 133 */       MeteorClient.EVENT_BUS.post(PlayerMoveEvent.get(type, movement));
/*     */     } else {
/*     */       
/* 136 */       MeteorClient.EVENT_BUS.post(EntityMoveEvent.get((class_1297)this, movement));
/*     */     } 
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_23326"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_2680;method_26204()Lnet/minecraft/class_2248;")})
/*     */   private class_2248 modifyVelocityMultiplierBlock(class_2248 original) {
/* 142 */     if (this != MeteorClient.mc.field_1724) return original;
/*     */     
/* 144 */     if (original == class_2246.field_10114 && ((NoSlow)Modules.get().get(NoSlow.class)).soulSand()) return class_2246.field_10340; 
/* 145 */     if (original == class_2246.field_21211 && ((NoSlow)Modules.get().get(NoSlow.class)).honeyBlock()) return class_2246.field_10340; 
/* 146 */     return original;
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_5756(Lnet/minecraft/class_1657;)Z"}, at = {@At("RETURN")})
/*     */   private boolean isInvisibleToCanceller(boolean original) {
/* 151 */     if (!Utils.canUpdate()) return original; 
/* 152 */     ESP esp = (ESP)Modules.get().get(ESP.class);
/* 153 */     if (((NoRender)Modules.get().get(NoRender.class)).noInvisibility() || (esp.isActive() && !esp.shouldSkip((class_1297)this))) return false; 
/* 154 */     return original;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5851"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void isGlowing(CallbackInfoReturnable<Boolean> info) {
/* 159 */     if (((NoRender)Modules.get().get(NoRender.class)).noGlowing()) info.setReturnValue(Boolean.valueOf(false)); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5871"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onGetTargetingMargin(CallbackInfoReturnable<Float> info) {
/* 164 */     double v = ((Hitboxes)Modules.get().get(Hitboxes.class)).getEntityValue((class_1297)this);
/* 165 */     if (v != 0.0D) info.setReturnValue(Float.valueOf((float)v)); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5756"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onIsInvisibleTo(class_1657 player, CallbackInfoReturnable<Boolean> info) {
/* 170 */     if (player == null) info.setReturnValue(Boolean.valueOf(false)); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_18376"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void getPoseHook(CallbackInfoReturnable<class_4050> info) {
/* 175 */     if (this != MeteorClient.mc.field_1724)
/*     */       return; 
/* 177 */     if (((ElytraFly)Modules.get().get(ElytraFly.class)).canPacketEfly()) {
/* 178 */       info.setReturnValue(class_4050.field_18077);
/*     */     }
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_18376"}, at = {@At("RETURN")})
/*     */   private class_4050 modifyGetPose(class_4050 original) {
/* 184 */     if (this != MeteorClient.mc.field_1724) return original;
/*     */     
/* 186 */     if (original == class_4050.field_18081 && !MeteorClient.mc.field_1724.method_5715() && ((PlayerEntityAccessor)MeteorClient.mc.field_1724).meteor$canChangeIntoPose(class_4050.field_18076)) return class_4050.field_18076; 
/* 187 */     return original;
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_21750"}, at = {@At("RETURN")})
/*     */   private boolean cancelBounce(boolean original) {
/* 192 */     return (((NoFall)Modules.get().get(NoFall.class)).cancelBounce() || original);
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5872"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void updateChangeLookDirection(double cursorDeltaX, double cursorDeltaY, CallbackInfo ci) {
/* 197 */     if (this != MeteorClient.mc.field_1724)
/*     */       return; 
/* 199 */     Freecam freecam = (Freecam)Modules.get().get(Freecam.class);
/* 200 */     FreeLook freeLook = (FreeLook)Modules.get().get(FreeLook.class);
/*     */     
/* 202 */     if (freecam.isActive()) {
/* 203 */       freecam.changeLookDirection(cursorDeltaX * 0.15D, cursorDeltaY * 0.15D);
/* 204 */       ci.cancel();
/*     */     }
/* 206 */     else if (Modules.get().isActive(HighwayBuilder.class)) {
/* 207 */       class_4184 camera = MeteorClient.mc.field_1773.method_19418();
/* 208 */       ((ICamera)camera).meteor$setRot(camera.method_19330() + cursorDeltaX * 0.15D, camera.method_19329() + cursorDeltaY * 0.15D);
/* 209 */       ci.cancel();
/*     */     }
/* 211 */     else if (freeLook.cameraMode()) {
/* 212 */       freeLook.cameraYaw += (float)(cursorDeltaX / ((Double)freeLook.sensitivity.get()).floatValue());
/* 213 */       freeLook.cameraPitch += (float)(cursorDeltaY / ((Double)freeLook.sensitivity.get()).floatValue());
/*     */       
/* 215 */       if (Math.abs(freeLook.cameraPitch) > 90.0F) freeLook.cameraPitch = (freeLook.cameraPitch > 0.0F) ? 90.0F : -90.0F; 
/* 216 */       ci.cancel();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\EntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */