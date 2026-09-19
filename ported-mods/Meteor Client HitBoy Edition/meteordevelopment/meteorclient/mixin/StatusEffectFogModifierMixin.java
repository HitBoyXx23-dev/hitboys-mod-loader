/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import net.minecraft.class_1291;
/*    */ import net.minecraft.class_1294;
/*    */ import net.minecraft.class_6880;
/*    */ import net.minecraft.class_7286;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_7286.class})
/*    */ public abstract class StatusEffectFogModifierMixin
/*    */ {
/*    */   @Shadow
/*    */   public abstract class_6880<class_1291> method_42590();
/*    */   
/*    */   @ModifyReturnValue(method = {"method_42593"}, at = {@At("RETURN")})
/*    */   private boolean modifyShouldApply(boolean original) {
/* 26 */     NoRender noRender = (NoRender)Modules.get().get(NoRender.class);
/* 27 */     if (method_42590() == class_1294.field_5919) return (original && !noRender.noBlindness()); 
/* 28 */     if (method_42590() == class_1294.field_38092) return (original && !noRender.noDarkness()); 
/* 29 */     return original;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\StatusEffectFogModifierMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */