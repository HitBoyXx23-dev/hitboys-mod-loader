/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.Ambience;
/*    */ import net.minecraft.class_12076;
/*    */ import net.minecraft.class_2874;
/*    */ import net.minecraft.class_4184;
/*    */ import net.minecraft.class_638;
/*    */ import net.minecraft.class_9975;
/*    */ import org.joml.Vector4fc;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_9975.class})
/*    */ public class SkyRenderingMixin
/*    */ {
/*    */   @Inject(method = {"method_74926"}, at = {@At("TAIL")})
/*    */   private void updateRenderState(class_638 world, float tickProgress, class_4184 camera, class_12076 state, CallbackInfo ci) {
/* 26 */     Ambience ambience = (Ambience)Modules.get().get(Ambience.class);
/* 27 */     if (!ambience.isActive())
/*    */       return; 
/* 29 */     if (((Boolean)ambience.endSky.get()).booleanValue()) state.field_64464 = class_2874.class_12326.field_64387; 
/* 30 */     if (((Boolean)ambience.customSkyColor.get()).booleanValue()) state.field_63097 = ambience.skyColor().getPacked(); 
/*    */   }
/*    */   
/*    */   @ModifyArg(method = {"method_62312"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_11282;method_71106(Lorg/joml/Matrix4fc;Lorg/joml/Vector4fc;Lorg/joml/Vector3fc;Lorg/joml/Matrix4fc;)Lcom/mojang/blaze3d/buffers/GpuBufferSlice;"))
/*    */   private Vector4fc modifyEndSkyColor(Vector4fc original) {
/* 35 */     Ambience ambience = (Ambience)Modules.get().get(Ambience.class);
/*    */     
/* 37 */     if (ambience.isActive() && ((Boolean)ambience.endSky.get()).booleanValue() && ((Boolean)ambience.customSkyColor.get()).booleanValue()) return (Vector4fc)ambience.skyColor().getVec4f(); 
/* 38 */     return original;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\SkyRenderingMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */