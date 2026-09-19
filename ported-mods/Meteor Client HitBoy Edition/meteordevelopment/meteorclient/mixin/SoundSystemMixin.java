/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.sugar.Local;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.world.PlaySoundEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.SoundBlocker;
/*    */ import meteordevelopment.orbit.ICancellable;
/*    */ import net.minecraft.class_1113;
/*    */ import net.minecraft.class_1117;
/*    */ import net.minecraft.class_1140;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1140.class})
/*    */ public abstract class SoundSystemMixin
/*    */ {
/*    */   @Shadow
/*    */   public abstract void method_19753(class_1113 paramclass_1113);
/*    */   
/*    */   @Inject(method = {"method_4854(Lnet/minecraft/class_1113;)Lnet/minecraft/class_1140$class_11518;"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onPlay(class_1113 soundInstance, CallbackInfoReturnable<class_1140.class_11518> cir) {
/* 30 */     PlaySoundEvent event = (PlaySoundEvent)MeteorClient.EVENT_BUS.post((ICancellable)PlaySoundEvent.get(soundInstance));
/*    */     
/* 32 */     if (event.isCancelled()) cir.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_4857()V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1117;method_16896()V", ordinal = 0)})
/*    */   private void onTick(CallbackInfo ci, @Local class_1117 tickableSoundInstance) {
/* 37 */     if (((SoundBlocker)Modules.get().get(SoundBlocker.class)).shouldBlock((class_1113)tickableSoundInstance)) method_19753((class_1113)tickableSoundInstance); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\SoundSystemMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */