/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.entity.EntityAddedEvent;
/*    */ import meteordevelopment.meteorclient.events.entity.EntityRemovedEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_638;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_638.class})
/*    */ public abstract class ClientWorldMixin
/*    */ {
/*    */   @Shadow
/*    */   @Nullable
/*    */   public abstract class_1297 method_8469(int paramInt);
/*    */   
/*    */   @Inject(method = {"method_53875"}, at = {@At("TAIL")})
/*    */   private void onAddEntity(class_1297 entity, CallbackInfo info) {
/* 34 */     if (entity != null) MeteorClient.EVENT_BUS.post(EntityAddedEvent.get(entity)); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_2945"}, at = {@At("HEAD")})
/*    */   private void onRemoveEntity(int entityId, class_1297.class_5529 removalReason, CallbackInfo info) {
/* 39 */     if (method_8469(entityId) != null) MeteorClient.EVENT_BUS.post(EntityRemovedEvent.get(method_8469(entityId))); 
/*    */   }
/*    */   
/*    */   @ModifyArgs(method = {"method_2941"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_638;method_2943(IIIILnet/minecraft/class_5819;Lnet/minecraft/class_2248;Lnet/minecraft/class_2338$class_2339;)V"))
/*    */   private void doRandomBlockDisplayTicks(Args args) {
/* 44 */     if (((NoRender)Modules.get().get(NoRender.class)).noBarrierInvis()) {
/* 45 */       args.set(5, class_2246.field_10499);
/*    */     }
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_31595"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onAddBlockBreakParticles(class_2338 blockPos, class_2680 state, CallbackInfo info) {
/* 51 */     if (((NoRender)Modules.get().get(NoRender.class)).noBlockBreakParticles()) info.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_74254"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onAddBlockBreakingParticles(class_2338 blockPos, class_2350 direction, CallbackInfo info) {
/* 56 */     if (((NoRender)Modules.get().get(NoRender.class)).noBlockBreakParticles()) info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ClientWorldMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */