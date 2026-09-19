/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.AntiPacketKick;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(targets = {"net/minecraft/class_8039$1$1"})
/*    */ public class PacketBundlingMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"method_48328"}, at = {@At(value = "CONSTANT", args = {"intValue=4096"})})
/*    */   private int add(int value) {
/* 21 */     if (((AntiPacketKick)Modules.get().get(AntiPacketKick.class)).isActive()) return Integer.MAX_VALUE; 
/* 22 */     return value;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\PacketBundlingMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */