/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import net.minecraft.class_6368;
/*    */ import net.minecraft.class_639;
/*    */ import net.minecraft.class_6394;
/*    */ import org.jspecify.annotations.NullMarked;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NullMarked
/*    */ @Mixin({class_6394.class})
/*    */ public interface BlockListCheckerMixin
/*    */ {
/*    */   @Inject(method = {"method_37097"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onCreate(CallbackInfoReturnable<class_6394> cir) {
/* 22 */     cir.setReturnValue(new class_6394()
/*    */         {
/*    */           public boolean method_37098(class_6368 address) {
/* 25 */             return true;
/*    */           }
/*    */ 
/*    */           
/*    */           public boolean method_37099(class_639 address) {
/* 30 */             return true;
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BlockListCheckerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */