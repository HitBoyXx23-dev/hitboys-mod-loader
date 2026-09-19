/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.BetterTooltips;
/*    */ import net.minecraft.class_7706;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_7706.class})
/*    */ public abstract class ItemGroupsMixin
/*    */ {
/*    */   @ModifyReturnValue(method = {"method_47330"}, at = {@At("RETURN")})
/*    */   private static boolean modifyReturn(boolean original) {
/* 19 */     return (original || ((BetterTooltips)Modules.get().get(BetterTooltips.class)).updateTooltips());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ItemGroupsMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */