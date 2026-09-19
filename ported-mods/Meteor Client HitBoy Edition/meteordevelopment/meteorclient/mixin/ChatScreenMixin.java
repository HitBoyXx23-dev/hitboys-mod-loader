/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.BetterChat;
/*    */ import net.minecraft.class_342;
/*    */ import net.minecraft.class_408;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(value = {class_408.class}, priority = 1001)
/*    */ public abstract class ChatScreenMixin
/*    */ {
/*    */   @Shadow
/*    */   protected class_342 field_2382;
/*    */   
/*    */   @Inject(method = {"method_25426"}, at = {@At("RETURN")})
/*    */   private void onInit(CallbackInfo info) {
/* 24 */     if (((BetterChat)Modules.get().get(BetterChat.class)).isInfiniteChatBox()) this.field_2382.method_1880(2147483647); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ChatScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */