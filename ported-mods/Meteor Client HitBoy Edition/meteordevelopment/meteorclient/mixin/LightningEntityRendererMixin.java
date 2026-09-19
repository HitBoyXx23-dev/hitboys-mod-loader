/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.Ambience;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_919;
/*    */ import org.joml.Matrix4f;
/*    */ import org.joml.Matrix4fc;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_919.class})
/*    */ public abstract class LightningEntityRendererMixin
/*    */ {
/*    */   @Inject(method = {"method_23183"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onSetLightningVertex(Matrix4f matrix4f, class_4588 vertexConsumer, float f, float g, int i, float h, float j, float k, float l, float m, float n, float o, boolean bl, boolean bl2, boolean bl3, boolean bl4, CallbackInfo ci) {
/* 26 */     Ambience ambience = (Ambience)Modules.get().get(Ambience.class);
/*    */     
/* 28 */     if (ambience.isActive() && ((Boolean)ambience.changeLightningColor.get()).booleanValue()) {
/* 29 */       Color color = (Color)ambience.lightningColor.get();
/*    */       
/* 31 */       vertexConsumer.method_22918((Matrix4fc)matrix4f, f + (bl ? o : -o), (i * 16), g + (bl2 ? o : -o)).method_22915(color.r / 255.0F, color.g / 255.0F, color.b / 255.0F, 0.3F);
/* 32 */       vertexConsumer.method_22918((Matrix4fc)matrix4f, h + (bl ? n : -n), ((i + 1) * 16), j + (bl2 ? n : -n)).method_22915(color.r / 255.0F, color.g / 255.0F, color.b / 255.0F, 0.3F);
/* 33 */       vertexConsumer.method_22918((Matrix4fc)matrix4f, h + (bl3 ? n : -n), ((i + 1) * 16), j + (bl4 ? n : -n)).method_22915(color.r / 255.0F, color.g / 255.0F, color.b / 255.0F, 0.3F);
/* 34 */       vertexConsumer.method_22918((Matrix4fc)matrix4f, f + (bl3 ? o : -o), (i * 16), g + (bl4 ? o : -o)).method_22915(color.r / 255.0F, color.g / 255.0F, color.b / 255.0F, 0.3F);
/*    */       
/* 36 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\LightningEntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */