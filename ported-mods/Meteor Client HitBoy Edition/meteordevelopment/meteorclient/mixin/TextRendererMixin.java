/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import net.minecraft.class_327;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_327.class})
/*    */ public abstract class TextRendererMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"method_72731"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_2583;method_10987()Z")})
/*    */   private boolean onRenderObfuscatedStyle(boolean original) {
/* 19 */     if (Modules.get() == null || Modules.get().get(NoRender.class) == null) {
/* 20 */       return original;
/*    */     }
/* 22 */     return (!((NoRender)Modules.get().get(NoRender.class)).noObfuscation() && original);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\TextRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */