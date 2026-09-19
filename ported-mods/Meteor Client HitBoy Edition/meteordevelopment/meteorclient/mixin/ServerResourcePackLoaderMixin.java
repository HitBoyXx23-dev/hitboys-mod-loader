/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.ServerSpoof;
/*    */ import net.minecraft.class_1066;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1066.class})
/*    */ public abstract class ServerResourcePackLoaderMixin
/*    */ {
/*    */   @Inject(method = {"method_55536"}, at = {@At("TAIL")})
/*    */   private void removeInactivePacksTail(CallbackInfo ci) {
/* 20 */     ((ServerSpoof)Modules.get().get(ServerSpoof.class)).silentAcceptResourcePack = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ServerResourcePackLoaderMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */