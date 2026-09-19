/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import net.minecraft.class_11690;
/*    */ import net.minecraft.class_11788;
/*    */ import net.minecraft.class_4597;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_11690.class})
/*    */ public abstract class ShadowPiecesCommandRendererMixin
/*    */ {
/*    */   @Inject(method = {"method_73015"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void meteor$onRender(class_11788 queue, class_4597.class_4598 vertexConsumers, CallbackInfo info) {
/* 20 */     if (queue.method_73505().isEmpty())
/* 21 */       info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ShadowPiecesCommandRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */