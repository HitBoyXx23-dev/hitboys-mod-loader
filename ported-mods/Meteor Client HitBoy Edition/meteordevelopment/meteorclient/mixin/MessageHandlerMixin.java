/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.time.Instant;
/*    */ import meteordevelopment.meteorclient.mixininterface.IMessageHandler;
/*    */ import net.minecraft.class_2556;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_7471;
/*    */ import net.minecraft.class_7594;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_7594.class})
/*    */ public abstract class MessageHandlerMixin
/*    */   implements IMessageHandler
/*    */ {
/*    */   @Unique
/*    */   private GameProfile sender;
/*    */   
/*    */   @Inject(method = {"method_44943"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_338;method_44811(Lnet/minecraft/class_2561;Lnet/minecraft/class_7469;Lnet/minecraft/class_7591;)V", shift = At.Shift.BEFORE)})
/*    */   private void onProcessChatMessageInternal_beforeAddMessage(class_2556.class_7602 params, class_7471 message, class_2561 decorated, GameProfile sender, boolean onlyShowSecureChat, Instant receptionTimestamp, CallbackInfoReturnable<Boolean> info) {
/* 29 */     this.sender = sender;
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_44943"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_338;method_44811(Lnet/minecraft/class_2561;Lnet/minecraft/class_7469;Lnet/minecraft/class_7591;)V", shift = At.Shift.AFTER)})
/*    */   private void onProcessChatMessageInternal_afterAddMessage(class_2556.class_7602 params, class_7471 message, class_2561 decorated, GameProfile sender, boolean onlyShowSecureChat, Instant receptionTimestamp, CallbackInfoReturnable<Boolean> info) {
/* 34 */     this.sender = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfile meteor$getSender() {
/* 39 */     return this.sender;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\MessageHandlerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */