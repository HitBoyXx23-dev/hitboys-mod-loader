/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.BetterTooltips;
/*    */ import net.minecraft.class_10712;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_10712.class})
/*    */ public abstract class TooltipDisplayComponentMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"method_67214"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/class_10712;comp_3600:Z")})
/*    */   private boolean modifyHideTooltip(boolean original) {
/* 19 */     return (original && !((Boolean)((BetterTooltips)Modules.get().get(BetterTooltips.class)).tooltip.get()).booleanValue());
/*    */   }
/*    */   
/*    */   @ModifyExpressionValue(method = {"method_67214"}, at = {@At(value = "INVOKE", target = "Ljava/util/SequencedSet;contains(Ljava/lang/Object;)Z")})
/*    */   private boolean modifyHiddenComponents(boolean original) {
/* 24 */     return (original && !((Boolean)((BetterTooltips)Modules.get().get(BetterTooltips.class)).additional.get()).booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\TooltipDisplayComponentMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */