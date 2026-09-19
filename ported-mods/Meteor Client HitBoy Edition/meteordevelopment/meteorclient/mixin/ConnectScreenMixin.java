/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.world.ServerConnectBeginEvent;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_412;
/*    */ import net.minecraft.class_639;
/*    */ import net.minecraft.class_642;
/*    */ import net.minecraft.class_9112;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_412.class})
/*    */ public abstract class ConnectScreenMixin
/*    */ {
/*    */   @Inject(method = {"method_2130(Lnet/minecraft/class_310;Lnet/minecraft/class_639;Lnet/minecraft/class_642;Lnet/minecraft/class_9112;)V"}, at = {@At("HEAD")})
/*    */   private void tryConnectEvent(class_310 client, class_639 address, class_642 info, class_9112 cookieStorage, CallbackInfo ci) {
/* 24 */     MeteorClient.EVENT_BUS.post(ServerConnectBeginEvent.get(address, info));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ConnectScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */