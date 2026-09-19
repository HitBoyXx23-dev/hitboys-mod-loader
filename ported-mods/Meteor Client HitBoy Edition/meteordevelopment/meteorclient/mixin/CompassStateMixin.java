/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Freecam;
/*    */ import net.minecraft.class_10473;
/*    */ import net.minecraft.class_11566;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2382;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_4184;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_10473.class})
/*    */ public abstract class CompassStateMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"method_65649"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_11566;method_73188()F")})
/*    */   private static float callLivingEntityGetYaw(float original) {
/* 26 */     if (Modules.get().isActive(Freecam.class)) return MeteorClient.mc.field_1773.method_19418().method_19330(); 
/* 27 */     return original;
/*    */   }
/*    */   
/*    */   @ModifyReturnValue(method = {"method_65651(Lnet/minecraft/class_11566;Lnet/minecraft/class_2338;)D"}, at = {@At("RETURN")})
/*    */   private static double modifyGetAngleTo(double original, class_11566 from, class_2338 to) {
/* 32 */     if (Modules.get().isActive(Freecam.class)) {
/* 33 */       class_243 vec3d = class_243.method_24953((class_2382)to);
/* 34 */       class_4184 camera = MeteorClient.mc.field_1773.method_19418();
/* 35 */       return Math.atan2(vec3d.method_10215() - (camera.method_71156()).field_1350, vec3d.method_10216() - (camera.method_71156()).field_1352) / 6.2831854820251465D;
/*    */     } 
/*    */     
/* 38 */     return original;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\CompassStateMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */