/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.mixininterface.IEntityRenderState;
/*    */ import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
/*    */ import net.minecraft.class_10017;
/*    */ import net.minecraft.class_11659;
/*    */ import net.minecraft.class_12075;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_3532;
/*    */ import net.minecraft.class_4184;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_898;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_898.class})
/*    */ public abstract class EntityRenderManagerMixin
/*    */ {
/*    */   @Shadow
/*    */   public class_4184 field_4686;
/*    */   
/*    */   @Inject(method = {"method_72976"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private <S extends class_10017> void render(S renderState, class_12075 cameraRenderState, double d, double e, double f, class_4587 matrixStack, class_11659 orderedRenderCommandQueue, CallbackInfo info) {
/* 32 */     class_1297 entity = ((IEntityRenderState)renderState).meteor$getEntity();
/*    */     
/* 34 */     if (entity instanceof FakePlayerEntity) { FakePlayerEntity player = (FakePlayerEntity)entity; if (player.hideWhenInsideCamera) {
/* 35 */         int cX = class_3532.method_15357((this.field_4686.method_71156()).field_1352);
/* 36 */         int cY = class_3532.method_15357((this.field_4686.method_71156()).field_1351);
/* 37 */         int cZ = class_3532.method_15357((this.field_4686.method_71156()).field_1350);
/*    */         
/* 39 */         if (cX == entity.method_31477() && cZ == entity.method_31479() && (cY == entity.method_31478() || cY == entity.method_31478() + 1)) info.cancel();
/*    */       
/*    */       }  }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyExpressionValue(method = {"method_72977(Lnet/minecraft/class_1297;F)Lnet/minecraft/class_10017;"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_897;method_62425(Lnet/minecraft/class_1297;F)Lnet/minecraft/class_10017;")})
/*    */   private <E extends class_1297> class_10017 getAndUpdateRenderState$setEntity(class_10017 state, E entity, float tickProgress) {
/* 50 */     ((IEntityRenderState)state).meteor$setEntity((class_1297)entity);
/* 51 */     return state;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\EntityRenderManagerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */