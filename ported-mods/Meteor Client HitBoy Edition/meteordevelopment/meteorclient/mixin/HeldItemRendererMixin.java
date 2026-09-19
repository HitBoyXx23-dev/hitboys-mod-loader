/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*     */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.render.ArmRenderEvent;
/*     */ import meteordevelopment.meteorclient.events.render.HeldItemRendererEvent;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.HandView;
/*     */ import net.minecraft.class_11659;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1306;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_3489;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_742;
/*     */ import net.minecraft.class_759;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_759.class})
/*     */ public abstract class HeldItemRendererMixin
/*     */ {
/*     */   @Shadow
/*     */   private float field_4043;
/*     */   @Shadow
/*     */   private float field_4052;
/*     */   @Shadow
/*     */   private class_1799 field_4047;
/*     */   @Shadow
/*     */   private class_1799 field_4048;
/*     */   
/*     */   @Shadow
/*     */   protected abstract boolean method_65910(class_1799 paramclass_17991, class_1799 paramclass_17992);
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_22976(FLnet/minecraft/class_4587;Lnet/minecraft/class_11659;Lnet/minecraft/class_746;I)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_746;method_6055(F)F")})
/*     */   private float modifySwing(float swingProgress) {
/*  54 */     HandView module = (HandView)Modules.get().get(HandView.class);
/*  55 */     class_1268 hand = Objects.<class_1268>requireNonNullElse(MeteorClient.mc.field_1724.field_6266, class_1268.field_5808);
/*     */     
/*  57 */     if (module.isActive()) {
/*  58 */       if (module.swordSlash() && hand == class_1268.field_5808 && this.field_4047.method_31573(class_3489.field_42611)) {
/*  59 */         return 0.0F;
/*     */       }
/*  61 */       if (hand == class_1268.field_5810 && !this.field_4048.method_7960()) {
/*  62 */         return swingProgress + ((Double)module.offSwing.get()).floatValue();
/*     */       }
/*  64 */       if (hand == class_1268.field_5808 && !this.field_4047.method_7960()) {
/*  65 */         return swingProgress + ((Double)module.mainSwing.get()).floatValue();
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return swingProgress;
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_65910"}, at = {@At("RETURN")})
/*     */   private boolean modifySkipSwapAnimation(boolean original) {
/*  74 */     return (original || ((HandView)Modules.get().get(HandView.class)).skipSwapping());
/*     */   }
/*     */   
/*     */   @ModifyArg(method = {"method_3220"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_3532;method_15363(FFF)F", ordinal = 2), index = 0)
/*     */   private float modifyEquipProgressMainhand(float value) {
/*  79 */     HandView handView = (HandView)Modules.get().get(HandView.class);
/*  80 */     if (handView.swordSlash() && MeteorClient.mc.field_1724.method_6047().method_31573(class_3489.field_42611)) return value;
/*     */     
/*  82 */     float f = MeteorClient.mc.field_1724.method_75194(1.0F);
/*  83 */     float modified = handView.oldAnimations() ? 1.0F : (f * f * f);
/*     */     
/*  85 */     return (method_65910(this.field_4047, MeteorClient.mc.field_1724.method_6047()) ? modified : 0.0F) - this.field_4043;
/*     */   }
/*     */   
/*     */   @ModifyArg(method = {"method_3220"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_3532;method_15363(FFF)F", ordinal = 3), index = 0)
/*     */   private float modifyEquipProgressOffhand(float value) {
/*  90 */     return (method_65910(this.field_4048, MeteorClient.mc.field_1724.method_6079()) ? true : false) - this.field_4052;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_3228"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_759;method_3233(Lnet/minecraft/class_1309;Lnet/minecraft/class_1799;Lnet/minecraft/class_811;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;I)V", shift = At.Shift.BEFORE)})
/*     */   private void onRenderItem(class_742 player, float tickProgress, float pitch, class_1268 hand, float swingProgress, class_1799 item, float equipProgress, class_4587 matrices, class_11659 orderedRenderCommandQueue, int light, CallbackInfo ci) {
/*  95 */     MeteorClient.EVENT_BUS.post(HeldItemRendererEvent.get(hand, matrices));
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_3228"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_759;method_3219(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;IFFLnet/minecraft/class_1306;)V")})
/*     */   private void onRenderArm(class_742 player, float tickProgress, float pitch, class_1268 hand, float swingProgress, class_1799 item, float equipProgress, class_4587 matrices, class_11659 orderedRenderCommandQueue, int light, CallbackInfo ci) {
/* 100 */     MeteorClient.EVENT_BUS.post(ArmRenderEvent.get(hand, matrices));
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_3218"}, at = {@At(value = "INVOKE", target = "Ljava/lang/Math;pow(DD)D", shift = At.Shift.BEFORE)}, cancellable = true)
/*     */   private void cancelTransformations(class_4587 matrices, float tickDelta, class_1306 arm, class_1799 stack, class_1657 player, CallbackInfo ci) {
/* 105 */     if (((HandView)Modules.get().get(HandView.class)).disableFoodAnimation()) ci.cancel(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\HeldItemRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */