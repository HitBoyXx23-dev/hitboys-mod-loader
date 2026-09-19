/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Xray;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.Ambience;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_1920;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_3486;
/*    */ import net.minecraft.class_3610;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_775;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_775.class})
/*    */ public abstract class FluidRendererMixin
/*    */ {
/*    */   @Unique
/* 27 */   private final ThreadLocal<Integer> alphas = new ThreadLocal<>(); @Unique
/* 28 */   private final ThreadLocal<Boolean> ambient = ThreadLocal.withInitial(() -> Boolean.valueOf(false));
/*    */   
/*    */   @Inject(method = {"method_3347"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onRender(class_1920 world, class_2338 pos, class_4588 vertexConsumer, class_2680 blockState, class_3610 fluidState, CallbackInfo info) {
/* 32 */     Ambience ambience = (Ambience)Modules.get().get(Ambience.class);
/* 33 */     this.ambient.set(Boolean.valueOf((ambience.isActive() && ((Boolean)ambience.customLavaColor.get()).booleanValue() && fluidState.method_15767(class_3486.field_15518))));
/*    */ 
/*    */     
/* 36 */     int alpha = Xray.getAlpha(fluidState.method_15759(), pos);
/*    */     
/* 38 */     if (alpha == 0) { info.cancel(); }
/* 39 */     else { this.alphas.set(Integer.valueOf(alpha)); }
/*    */   
/*    */   }
/*    */   @Inject(method = {"method_23072"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onVertex(class_4588 vertexConsumer, float x, float y, float z, float red, float green, float blue, float u, float v, int light, CallbackInfo info) {
/* 44 */     int alpha = ((Integer)this.alphas.get()).intValue();
/*    */     
/* 46 */     if (((Boolean)this.ambient.get()).booleanValue()) {
/* 47 */       Color color = (Color)((Ambience)Modules.get().get(Ambience.class)).lavaColor.get();
/* 48 */       vertex(vertexConsumer, x, y, z, color.r, color.g, color.b, (alpha != -1) ? alpha : color.a, u, v, light);
/* 49 */       info.cancel();
/*    */     }
/* 51 */     else if (alpha != -1) {
/* 52 */       vertex(vertexConsumer, x, y, z, (int)(red * 255.0F), (int)(green * 255.0F), (int)(blue * 255.0F), alpha, u, v, light);
/* 53 */       info.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Unique
/*    */   private void vertex(class_4588 vertexConsumer, float x, float y, float z, int red, int green, int blue, int alpha, float u, float v, int light) {
/* 59 */     vertexConsumer.method_22912(x, y, z).method_1336(red, green, blue, alpha).method_22913(u, v).method_60803(light).method_22914(0.0F, 1.0F, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\FluidRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */