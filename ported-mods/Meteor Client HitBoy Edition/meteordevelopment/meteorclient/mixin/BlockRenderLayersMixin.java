/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Xray;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.Ambience;
/*    */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*    */ import net.minecraft.class_11515;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_3610;
/*    */ import net.minecraft.class_4696;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_4696.class})
/*    */ public class BlockRenderLayersMixin
/*    */ {
/*    */   @Inject(method = {"method_23679"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onGetBlockLayer(class_2680 state, CallbackInfoReturnable<class_11515> cir) {
/* 24 */     if (Modules.get() == null)
/*    */       return; 
/* 26 */     int alpha = Xray.getAlpha(state, null);
/* 27 */     if (alpha > 0 && alpha < 255) cir.setReturnValue(class_11515.field_60926); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_23680"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onGetFluidLayer(class_3610 state, CallbackInfoReturnable<class_11515> cir) {
/* 32 */     if (Modules.get() == null)
/*    */       return; 
/* 34 */     int alpha = Xray.getAlpha(state.method_15759(), null);
/* 35 */     if (alpha > 0 && alpha < 255) {
/* 36 */       cir.setReturnValue(class_11515.field_60926);
/*    */     }
/*    */     else {
/*    */       
/* 40 */       Ambience ambience = (Ambience)Modules.get().get(Ambience.class);
/* 41 */       int a = ((SettingColor)ambience.lavaColor.get()).a;
/* 42 */       if (ambience.isActive() && ((Boolean)ambience.customLavaColor.get()).booleanValue() && a > 0 && a < 255)
/* 43 */         cir.setReturnValue(class_11515.field_60926); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BlockRenderLayersMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */