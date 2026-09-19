/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReceiver;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.BetterChat;
/*    */ import net.minecraft.class_11735;
/*    */ import net.minecraft.class_12225;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_5481;
/*    */ import net.minecraft.class_9848;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(targets = {"net/minecraft/class_338$class_12235"}, remap = false)
/*    */ public class ChatHudInteractableMixin
/*    */ {
/*    */   @Unique
/*    */   private static BetterChat betterChat;
/*    */   @Shadow
/*    */   @Final
/*    */   private class_332 field_63876;
/*    */   
/*    */   @ModifyReceiver(method = {"method_75807"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_12225;method_75766(Lnet/minecraft/class_11735;IILnet/minecraft/class_12225$class_12227;Lnet/minecraft/class_5481;)V")})
/*    */   private class_12225 onRender_beforeDrawTextWithShadow(class_12225 instance, class_11735 alignment, int x, int y, class_12225.class_12227 transformation, class_5481 orderedText) {
/* 36 */     getBetterChat().beforeDrawMessage(this.field_63876, y, class_9848.method_61317(transformation.comp_5152()));
/* 37 */     return instance;
/*    */   }
/*    */   
/*    */   @ModifyArg(method = {"method_75807"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_12225;method_75766(Lnet/minecraft/class_11735;IILnet/minecraft/class_12225$class_12227;Lnet/minecraft/class_5481;)V"), index = 1)
/*    */   private int modifyX(int x) {
/* 42 */     return getBetterChat().modifyChatWidth(x);
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_75807"}, at = {@At("TAIL")})
/*    */   private void onRender_afterDrawTextWithShadow(int y, float f, class_5481 text, CallbackInfoReturnable<Boolean> cir) {
/* 47 */     getBetterChat().afterDrawMessage();
/*    */   }
/*    */   
/*    */   @Unique
/*    */   private static BetterChat getBetterChat() {
/* 52 */     if (betterChat == null) {
/* 53 */       betterChat = (BetterChat)Modules.get().get(BetterChat.class);
/*    */     }
/*    */     
/* 56 */     return betterChat;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ChatHudInteractableMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */