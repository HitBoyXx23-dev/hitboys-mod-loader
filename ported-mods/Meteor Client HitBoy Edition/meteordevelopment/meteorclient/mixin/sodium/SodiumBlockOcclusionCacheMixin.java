/*    */ package meteordevelopment.meteorclient.mixin.sodium;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Xray;
/*    */ import net.caffeinemc.mods.sodium.client.render.model.AbstractBlockRenderContext;
/*    */ import net.caffeinemc.mods.sodium.client.world.LevelSlice;
/*    */ import net.minecraft.class_1922;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2680;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin(value = {AbstractBlockRenderContext.class}, remap = false)
/*    */ public abstract class SodiumBlockOcclusionCacheMixin
/*    */ {
/*    */   @Shadow
/*    */   protected class_2680 state;
/*    */   @Shadow
/*    */   protected class_2338 pos;
/*    */   @Shadow
/*    */   protected LevelSlice slice;
/*    */   @Unique
/*    */   private Xray xray;
/*    */   
/*    */   @Inject(method = {"<init>"}, at = {@At("TAIL")})
/*    */   private void onInit(CallbackInfo info) {
/* 35 */     this.xray = (Xray)Modules.get().get(Xray.class);
/*    */   }
/*    */   
/*    */   @Inject(method = {"shouldDrawSide"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void meteor$forceXrayFace(class_2350 facing, CallbackInfoReturnable<Boolean> cir) {
/* 40 */     if (this.xray != null && this.xray.isActive() && !this.xray.isBlocked(this.state.method_26204(), null)) {
/* 41 */       cir.setReturnValue(Boolean.valueOf(true));
/*    */     }
/*    */   }
/*    */   
/*    */   @ModifyReturnValue(method = {"shouldDrawSide"}, at = {@At("RETURN")})
/*    */   private boolean shouldDrawSide(boolean original, class_2350 facing) {
/* 47 */     if (!this.xray.isActive()) return original; 
/* 48 */     return this.xray.modifyDrawSide(this.state, (class_1922)this.slice, this.pos, facing, original);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\sodium\SodiumBlockOcclusionCacheMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */