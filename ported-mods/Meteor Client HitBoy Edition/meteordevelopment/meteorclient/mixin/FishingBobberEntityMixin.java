/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
/*    */ import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.Velocity;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1536;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1536.class})
/*    */ public abstract class FishingBobberEntityMixin
/*    */ {
/*    */   @WrapOperation(method = {"method_5711"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1536;method_6954(Lnet/minecraft/class_1297;)V")})
/*    */   private void preventFishingRodPull(class_1536 instance, class_1297 entity, Operation<Void> original) {
/* 23 */     if (!instance.method_73183().method_8608() || entity != MeteorClient.mc.field_1724) original.call(new Object[] { instance, entity });
/*    */     
/* 25 */     Velocity velocity = (Velocity)Modules.get().get(Velocity.class);
/* 26 */     if (!velocity.isActive() || !((Boolean)velocity.fishing.get()).booleanValue()) original.call(new Object[] { instance, entity }); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\FishingBobberEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */