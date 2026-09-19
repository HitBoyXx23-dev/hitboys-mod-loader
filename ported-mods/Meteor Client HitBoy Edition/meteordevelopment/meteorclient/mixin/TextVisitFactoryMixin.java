/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.player.NameProtect;
/*    */ import net.minecraft.class_5223;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_5223.class})
/*    */ public abstract class TextVisitFactoryMixin
/*    */ {
/*    */   @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/class_5223;method_27473(Ljava/lang/String;ILnet/minecraft/class_2583;Lnet/minecraft/class_2583;Lnet/minecraft/class_5224;)Z", ordinal = 0), method = {"method_27472(Ljava/lang/String;ILnet/minecraft/class_2583;Lnet/minecraft/class_5224;)Z"}, index = 0)
/*    */   private static String adjustText(String text) {
/* 24 */     if (Modules.get() != null) return ((NameProtect)Modules.get().get(NameProtect.class)).replaceName(text); 
/* 25 */     return text;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\TextVisitFactoryMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */