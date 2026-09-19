/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*     */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*     */ import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
/*     */ import com.llamalad7.mixinextras.sugar.Local;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.entity.DropItemsEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.PlayerTickMovementEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.EntityControl;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Flight;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.NoSlow;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Scaffold;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Sneak;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Sprint;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Velocity;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.LiquidInteract;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.NoMiningTrace;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.Portals;
/*     */ import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
/*     */ import meteordevelopment.orbit.ICancellable;
/*     */ import net.minecraft.class_10185;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1316;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_3966;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_638;
/*     */ import net.minecraft.class_742;
/*     */ import net.minecraft.class_744;
/*     */ import net.minecraft.class_746;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ @Mixin({class_746.class})
/*     */ public abstract class ClientPlayerEntityMixin
/*     */   extends class_742
/*     */ {
/*     */   public ClientPlayerEntityMixin(class_638 world, GameProfile profile) {
/*  47 */     super(world, profile);
/*     */   } @Shadow
/*     */   public class_744 field_3913;
/*     */   @Inject(method = {"method_7290"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onDropSelectedItem(boolean dropEntireStack, CallbackInfoReturnable<Boolean> info) {
/*  52 */     if (((DropItemsEvent)MeteorClient.EVENT_BUS.post((ICancellable)DropItemsEvent.get(method_6047()))).isCancelled()) info.setReturnValue(Boolean.valueOf(false)); 
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_60887"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/class_310;field_1755:Lnet/minecraft/class_437;", opcode = 180)})
/*     */   private class_437 modifyNauseaCurrentScreen(class_437 original) {
/*  57 */     if (Modules.get().isActive(Portals.class)) return null; 
/*  58 */     return original;
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_67270"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_746;method_6115()Z")})
/*     */   private boolean redirectUsingItem(boolean isUsingItem) {
/*  63 */     if (((NoSlow)Modules.get().get(NoSlow.class)).items()) return false; 
/*  64 */     return isUsingItem;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5715"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onIsSneaking(CallbackInfoReturnable<Boolean> info) {
/*  69 */     if (((Scaffold)Modules.get().get(Scaffold.class)).scaffolding()) info.setReturnValue(Boolean.valueOf(false)); 
/*  70 */     if (((Flight)Modules.get().get(Flight.class)).noSneak()) info.setReturnValue(Boolean.valueOf(false)); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_20303"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onShouldSlowDown(CallbackInfoReturnable<Boolean> info) {
/*  75 */     if (((NoSlow)Modules.get().get(NoSlow.class)).sneaking()) {
/*  76 */       info.setReturnValue(Boolean.valueOf(method_20448()));
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_30673"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onPushOutOfBlocks(double x, double d, CallbackInfo info) {
/*  82 */     Velocity velocity = (Velocity)Modules.get().get(Velocity.class);
/*  83 */     if (velocity.isActive() && ((Boolean)velocity.blocks.get()).booleanValue()) {
/*  84 */       info.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_5773"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/class_744;field_54155:Lnet/minecraft/class_10185;", opcode = 180)})
/*     */   private class_10185 isSneaking(class_10185 original) {
/*  90 */     if (((Sneak)Modules.get().get(Sneak.class)).doPacket() || ((NoSlow)Modules.get().get(NoSlow.class)).airStrict()) {
/*  91 */       return new class_10185(original
/*  92 */           .comp_3159(), original
/*  93 */           .comp_3160(), original
/*  94 */           .comp_3161(), original
/*  95 */           .comp_3162(), original
/*  96 */           .comp_3163(), true, original
/*     */           
/*  98 */           .comp_3165());
/*     */     }
/*     */     
/* 101 */     return original;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_6007"}, at = {@At("HEAD")})
/*     */   private void preTickMovement(CallbackInfo ci) {
/* 106 */     MeteorClient.EVENT_BUS.post(PlayerTickMovementEvent.get());
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_3151"}, at = {@At("RETURN")})
/*     */   private float modifyMountJumpStrength(float original) {
/* 111 */     if (((EntityControl)Modules.get().get(EntityControl.class)).maxJump()) return 1.0F; 
/* 112 */     return original;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_45773"}, at = {@At("RETURN")}, cancellable = true)
/*     */   private void changeJumpingMount(CallbackInfoReturnable<class_1316> info) {
/* 117 */     if (((EntityControl)Modules.get().get(EntityControl.class)).cancelJump()) info.setReturnValue(null); 
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_76763(Lnet/minecraft/class_1297;DDF)Lnet/minecraft/class_239;"}, at = {@At("RETURN")})
/*     */   private static class_239 onUpdateTargetedEntity(class_239 original, @Local class_239 hitResult) {
/* 122 */     if (original instanceof class_3966) { class_3966 ehr = (class_3966)original;
/* 123 */       if (((NoMiningTrace)Modules.get().get(NoMiningTrace.class)).canWork(ehr.method_17782()) && hitResult.method_17783() == class_239.class_240.field_1332) {
/* 124 */         return hitResult;
/*     */       }
/* 126 */       class_1297 class_1297 = ehr.method_17782(); if (class_1297 instanceof FakePlayerEntity) { FakePlayerEntity fakePlayer = (FakePlayerEntity)class_1297; if (fakePlayer.noHit) {
/* 127 */           return hitResult;
/*     */         } }
/*     */        }
/*     */     
/* 131 */     return original;
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_76763(Lnet/minecraft/class_1297;DDF)Lnet/minecraft/class_239;"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1297;method_5745(DFZ)Lnet/minecraft/class_239;")})
/*     */   private static class_239 modifyRaycastResult(class_239 original, class_1297 entity, double blockInteractionRange, double entityInteractionRange, float tickProgress, @Local(ordinal = 0, argsOnly = true) double maxDistance) {
/* 136 */     if (!Modules.get().isActive(LiquidInteract.class)) return original; 
/* 137 */     if (original.method_17783() != class_239.class_240.field_1333) return original;
/*     */     
/* 139 */     return entity.method_5745(maxDistance, tickProgress, true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_48300"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_744;method_20622()Z")})
/*     */   private boolean modifyIsWalking(boolean original) {
/* 146 */     if (!((Sprint)Modules.get().get(Sprint.class)).rageSprint()) return original;
/*     */     
/* 148 */     float forwards = Math.abs(this.field_6250);
/* 149 */     float sideways = Math.abs(this.field_6212);
/*     */     
/* 151 */     return method_5869() ? ((forwards > 1.0E-5F || sideways > 1.0E-5F)) : ((forwards > 0.8D || sideways > 0.8D));
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_6007"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_744;method_20622()Z")})
/*     */   private boolean modifyMovement(boolean original) {
/* 156 */     if (!((Sprint)Modules.get().get(Sprint.class)).rageSprint()) return original;
/*     */     
/* 158 */     return (Math.abs(this.field_6212) > 1.0E-5F || Math.abs(this.field_6250) > 1.0E-5F);
/*     */   }
/*     */   
/*     */   @WrapWithCondition(method = {"method_6007"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_746;method_5728(Z)V", ordinal = 3)})
/*     */   private boolean wrapSetSprinting(class_746 instance, boolean b) {
/* 163 */     Sprint s = (Sprint)Modules.get().get(Sprint.class);
/*     */     
/* 165 */     return (!s.rageSprint() || (s.unsprintInWater() && method_5799()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"method_3136"}, at = {@At("HEAD")})
/*     */   private void onSendMovementPacketsHead(CallbackInfo info) {
/* 172 */     MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Pre.get());
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5773"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_634;method_52787(Lnet/minecraft/class_2596;)V", ordinal = 1)})
/*     */   private void onTickHasVehicleBeforeSendPackets(CallbackInfo info) {
/* 177 */     MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Pre.get());
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_3136"}, at = {@At("TAIL")})
/*     */   private void onSendMovementPacketsTail(CallbackInfo info) {
/* 182 */     MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Post.get());
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_5773"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_634;method_52787(Lnet/minecraft/class_2596;)V", ordinal = 1, shift = At.Shift.AFTER)})
/*     */   private void onTickHasVehicleAfterSendPackets(CallbackInfo info) {
/* 187 */     MeteorClient.EVENT_BUS.post(SendMovementPacketsEvent.Post.get());
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ClientPlayerEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */