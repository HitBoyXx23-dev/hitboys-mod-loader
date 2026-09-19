/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Xray;
/*    */ import net.minecraft.class_10889;
/*    */ import net.minecraft.class_1920;
/*    */ import net.minecraft.class_1922;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4588;
/*    */ import net.minecraft.class_778;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArgs;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_778.class})
/*    */ public abstract class BlockModelRendererMixin
/*    */ {
/*    */   @Unique
/* 31 */   private final ThreadLocal<Integer> alphas = new ThreadLocal<>();
/*    */ 
/*    */   
/*    */   @Inject(method = {"method_3361", "method_3373"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onRenderSmooth(class_1920 world, List<class_10889> parts, class_2680 state, class_2338 pos, class_4587 matrices, class_4588 vertexConsumer, boolean cull, int overlay, CallbackInfo ci) {
/* 36 */     int alpha = Xray.getAlpha(state, pos);
/*    */     
/* 38 */     if (alpha == 0) { ci.cancel(); }
/* 39 */     else { this.alphas.set(Integer.valueOf(alpha)); }
/*    */   
/*    */   }
/*    */   @ModifyArgs(method = {"method_23073"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_4588;method_22920(Lnet/minecraft/class_4587$class_4665;Lnet/minecraft/class_777;[FFFFF[II)V"))
/*    */   private void modifyXrayAlpha(Args args) {
/* 44 */     int alpha = ((Integer)this.alphas.get()).intValue();
/* 45 */     args.set(6, (alpha == -1) ? args.get(6) : Float.valueOf(alpha / 255.0F));
/*    */   }
/*    */   
/*    */   @ModifyReturnValue(method = {"method_68826"}, at = {@At("RETURN")})
/*    */   private static boolean modifyShouldDrawFace(boolean original, class_1920 world, class_2680 state, boolean cull, class_2350 side, class_2338 pos) {
/* 50 */     Xray xray = (Xray)Modules.get().get(Xray.class);
/*    */     
/* 52 */     if (xray.isActive()) {
/* 53 */       return xray.modifyDrawSide(state, (class_1922)world, pos.method_10093(side.method_10153()), side, original);
/*    */     }
/*    */     
/* 56 */     return original;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BlockModelRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */