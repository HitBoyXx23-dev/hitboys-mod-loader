/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import net.minecraft.class_10444;
/*    */ import net.minecraft.class_918;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_918.class})
/*    */ public abstract class ItemRendererMixin
/*    */ {
/*    */   @ModifyVariable(method = {"method_62476(Lnet/minecraft/class_811;Lnet/minecraft/class_4587;Lnet/minecraft/class_4597;II[ILjava/util/List;Lnet/minecraft/class_1921;Lnet/minecraft/class_10444$class_10445;)V"}, at = @At("HEAD"), argsOnly = true)
/*    */   private static class_10444.class_10445 modifyEnchant(class_10444.class_10445 glint) {
/* 24 */     if (((NoRender)Modules.get().get(NoRender.class)).noEnchantGlint()) return class_10444.class_10445.field_55341; 
/* 25 */     return glint;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ItemRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */