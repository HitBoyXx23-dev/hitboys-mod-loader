/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.world.AmbientOcclusionEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import net.minecraft.class_1922;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_4970;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_4970.class})
/*    */ public abstract class AbstractBlockMixin
/*    */ {
/*    */   @Inject(method = {"method_9575"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onGetAmbientOcclusionLightLevel(class_2680 state, class_1922 world, class_2338 pos, CallbackInfoReturnable<Float> info) {
/* 25 */     AmbientOcclusionEvent event = (AmbientOcclusionEvent)MeteorClient.EVENT_BUS.post(AmbientOcclusionEvent.get());
/*    */     
/* 27 */     if (event.lightLevel != -1.0F) info.setReturnValue(Float.valueOf(event.lightLevel)); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_9535"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onRenderingSeed(class_2680 state, class_2338 pos, CallbackInfoReturnable<Long> cir) {
/* 32 */     if (((NoRender)Modules.get().get(NoRender.class)).noTextureRotations()) cir.setReturnValue(Long.valueOf(0L)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\AbstractBlockMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */