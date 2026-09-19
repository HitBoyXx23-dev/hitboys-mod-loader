/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.BetterChat;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import net.minecraft.class_303;
/*    */ import net.minecraft.class_7591;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(targets = {"net/minecraft/class_338$1"}, remap = false)
/*    */ public class ChatHudLineConsumerMixin
/*    */ {
/*    */   @Inject(method = {"accept"}, at = {@At("HEAD")})
/*    */   private void setLine(class_303.class_7590 visible, int i, float f, CallbackInfo ci) {
/* 24 */     ((BetterChat)Modules.get().get(BetterChat.class)).line = visible;
/*    */   }
/*    */ 
/*    */   
/*    */   @ModifyExpressionValue(method = {"accept"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_303$class_7590;comp_897()Lnet/minecraft/class_7591;")})
/*    */   private class_7591 onRender_modifyIndicator(class_7591 indicator) {
/* 30 */     return ((NoRender)Modules.get().get(NoRender.class)).noMessageSignatureIndicator() ? null : indicator;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ChatHudLineConsumerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */