/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.mixininterface.IEntityRenderState;
/*    */ import meteordevelopment.meteorclient.utils.network.Capes;
/*    */ import net.minecraft.class_10055;
/*    */ import net.minecraft.class_11659;
/*    */ import net.minecraft.class_12079;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_972;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_972.class})
/*    */ public abstract class CapeFeatureRendererMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"method_4177(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_10055;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_8685;comp_1627()Lnet/minecraft/class_12079$class_12081;")})
/*    */   private class_12079.class_12081 modifyCapeTexture(class_12079.class_12081 original, class_4587 matrices, class_11659 entityRenderCommandQueue, int i, class_10055 state, float f, float g) {
/* 25 */     class_1297 class_1297 = ((IEntityRenderState)state).meteor$getEntity(); if (class_1297 instanceof class_1657) { class_1657 player = (class_1657)class_1297;
/* 26 */       class_2960 id = Capes.get(player);
/* 27 */       return (id == null) ? original : (class_12079.class_12081)new class_12079.class_10726(id, id); }
/*    */ 
/*    */     
/* 30 */     return original;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\CapeFeatureRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */