/*    */ package meteordevelopment.meteorclient.mixin.lithium;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import com.llamalad7.mixinextras.sugar.Local;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.world.CollisionShapeEvent;
/*    */ import meteordevelopment.orbit.ICancellable;
/*    */ import net.caffeinemc.mods.lithium.common.entity.movement.ChunkAwareBlockCollisionSweeper;
/*    */ import net.caffeinemc.mods.lithium.common.entity.movement.ChunkAwareBlockCollisionSweeperVoxelShape;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_238;
/*    */ import net.minecraft.class_259;
/*    */ import net.minecraft.class_265;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_310;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ @Mixin({ChunkAwareBlockCollisionSweeperVoxelShape.class})
/*    */ public abstract class ChunkAwareBlockCollisionSweeperMixin
/*    */   extends ChunkAwareBlockCollisionSweeper<class_265>
/*    */ {
/*    */   public ChunkAwareBlockCollisionSweeperMixin(class_1937 world, @Nullable class_1297 entity, class_238 box, boolean hideLastCollision) {
/* 28 */     super(world, entity, box, hideLastCollision);
/*    */   }
/*    */   
/*    */   @ModifyExpressionValue(method = {"computeNext()Lnet/minecraft/class_265;"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_3726;method_62878(Lnet/minecraft/class_2680;Lnet/minecraft/class_1941;Lnet/minecraft/class_2338;)Lnet/minecraft/class_265;")})
/*    */   private class_265 modifyCollisionShape(class_265 original, @Local(name = {"state"}) class_2680 state) {
/* 33 */     if (this.world != (class_310.method_1551()).field_1687) return original;
/*    */     
/* 35 */     CollisionShapeEvent event = (CollisionShapeEvent)MeteorClient.EVENT_BUS.post((ICancellable)CollisionShapeEvent.get(state, (class_2338)this.pos, original));
/* 36 */     return event.isCancelled() ? class_259.method_1073() : event.shape;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\lithium\ChunkAwareBlockCollisionSweeperMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */