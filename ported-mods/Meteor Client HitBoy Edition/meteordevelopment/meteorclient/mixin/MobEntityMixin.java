/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.EntityControl;
/*    */ import net.minecraft.class_1308;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1308.class})
/*    */ public abstract class MobEntityMixin
/*    */ {
/*    */   @ModifyReturnValue(method = {"method_66672"}, at = {@At("RETURN")})
/*    */   private boolean hasSaddleEquipped(boolean original) {
/* 19 */     return (((EntityControl)Modules.get().get(EntityControl.class)).spoofSaddle() || original);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\MobEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */