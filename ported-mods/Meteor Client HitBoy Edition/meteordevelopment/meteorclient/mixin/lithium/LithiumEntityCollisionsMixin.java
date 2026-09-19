/*    */ package meteordevelopment.meteorclient.mixin.lithium;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.Collisions;
/*    */ import net.caffeinemc.mods.lithium.common.entity.LithiumEntityCollisions;
/*    */ import net.minecraft.class_238;
/*    */ import net.minecraft.class_2784;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({LithiumEntityCollisions.class})
/*    */ public abstract class LithiumEntityCollisionsMixin
/*    */ {
/*    */   @Inject(method = {"isWithinWorldBorder"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onIsWithinWorldBorder(class_2784 border, class_238 box, CallbackInfoReturnable<Boolean> cir) {
/* 22 */     if (((Collisions)Modules.get().get(Collisions.class)).ignoreBorder())
/* 23 */       cir.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\lithium\LithiumEntityCollisionsMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */