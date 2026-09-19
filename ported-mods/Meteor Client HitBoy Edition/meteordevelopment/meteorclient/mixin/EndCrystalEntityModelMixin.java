/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Chams;
/*    */ import net.minecraft.class_10014;
/*    */ import net.minecraft.class_3532;
/*    */ import net.minecraft.class_9946;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_9946.class})
/*    */ public abstract class EndCrystalEntityModelMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"method_62083(Lnet/minecraft/class_10014;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_892;method_23155(F)F")})
/*    */   private float setAngles$bounce(float original, class_10014 state) {
/* 23 */     Chams module = (Chams)Modules.get().get(Chams.class);
/* 24 */     if (!module.isActive() || !((Boolean)module.crystals.get()).booleanValue()) return original;
/*    */     
/* 26 */     float g = class_3532.method_15374((state.field_53328 * 0.2F)) / 2.0F + 0.5F;
/* 27 */     g = (g * g + g) * 0.4F * ((Double)module.crystalsBounce.get()).floatValue();
/* 28 */     return g - 1.4F;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyExpressionValue(method = {"method_62083(Lnet/minecraft/class_10014;)V"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/class_10014;field_53328:F", ordinal = 0)})
/*    */   private float modifySpeed(float original) {
/* 35 */     Chams module = (Chams)Modules.get().get(Chams.class);
/* 36 */     if (!module.isActive() || !((Boolean)module.crystals.get()).booleanValue()) return original;
/*    */     
/* 38 */     return original * ((Double)module.crystalsRotationSpeed.get()).floatValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\EndCrystalEntityModelMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */