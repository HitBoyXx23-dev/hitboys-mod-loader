/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.mixininterface.IEntityRenderState;
/*    */ import meteordevelopment.meteorclient.utils.network.Capes;
/*    */ import net.minecraft.class_10034;
/*    */ import net.minecraft.class_11659;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_3883;
/*    */ import net.minecraft.class_3887;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_583;
/*    */ import net.minecraft.class_979;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_979.class})
/*    */ public abstract class ElytraFeatureRendererMixin<S extends class_10034, M extends class_583<S>>
/*    */   extends class_3887<S, M>
/*    */ {
/*    */   public ElytraFeatureRendererMixin(class_3883<S, M> context) {
/* 26 */     super(context);
/*    */   }
/*    */   
/*    */   @ModifyExpressionValue(method = {"method_17161(Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;ILnet/minecraft/class_10034;FF)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_979;method_64084(Lnet/minecraft/class_10034;)Lnet/minecraft/class_2960;")})
/*    */   private class_2960 modifyCapeTexture(class_2960 original, class_4587 matrices, class_11659 entityRenderCommandQueue, int i, S state, float f, float g) {
/* 31 */     class_1297 class_1297 = ((IEntityRenderState)state).meteor$getEntity(); if (class_1297 instanceof class_1657) { class_1657 player = (class_1657)class_1297;
/* 32 */       class_2960 id = Capes.get(player);
/* 33 */       return (id == null) ? original : id; }
/*    */ 
/*    */     
/* 36 */     return original;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ElytraFeatureRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */