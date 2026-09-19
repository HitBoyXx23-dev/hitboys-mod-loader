/*    */ package meteordevelopment.meteorclient.mixin.sodium;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Fullbright;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Xray;
/*    */ import net.caffeinemc.mods.sodium.client.model.light.data.LightDataAccess;
/*    */ import net.minecraft.class_1920;
/*    */ import net.minecraft.class_1944;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2680;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(value = {LightDataAccess.class}, remap = false)
/*    */ public abstract class SodiumLightDataAccessMixin
/*    */ {
/*    */   @Unique
/*    */   private static final int FULL_LIGHT = 4095;
/*    */   @Shadow
/*    */   protected class_1920 level;
/*    */   @Shadow
/*    */   @Final
/*    */   private class_2338.class_2339 pos;
/*    */   @Unique
/*    */   private Xray xray;
/*    */   @Unique
/*    */   private Fullbright fb;
/*    */   
/*    */   @Inject(method = {"<init>"}, at = {@At("TAIL")})
/*    */   private void onInit(CallbackInfo info) {
/* 43 */     this.xray = (Xray)Modules.get().get(Xray.class);
/* 44 */     this.fb = (Fullbright)Modules.get().get(Fullbright.class);
/*    */   }
/*    */   
/*    */   @ModifyVariable(method = {"compute"}, at = @At("TAIL"), name = {"bl"})
/*    */   private int compute_modifyBL(int light) {
/* 49 */     if (this.xray.isActive()) {
/* 50 */       class_2680 state = this.level.method_8320((class_2338)this.pos);
/* 51 */       if (!this.xray.isBlocked(state.method_26204(), (class_2338)this.pos)) return 4095;
/*    */     
/*    */     } 
/* 54 */     return light;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyVariable(method = {"compute"}, at = @At("STORE"), name = {"sl"})
/*    */   private int compute_assignSL(int sl) {
/* 61 */     return Math.max(this.fb.getLuminance(class_1944.field_9284), sl);
/*    */   }
/*    */   
/*    */   @ModifyVariable(method = {"compute"}, at = @At("STORE"), name = {"bl"})
/*    */   private int compute_assignBL(int bl) {
/* 66 */     return Math.max(this.fb.getLuminance(class_1944.field_9282), bl);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\sodium\SodiumLightDataAccessMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */