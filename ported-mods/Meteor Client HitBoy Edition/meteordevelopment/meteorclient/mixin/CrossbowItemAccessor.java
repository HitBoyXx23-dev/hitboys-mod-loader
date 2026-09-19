/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import net.minecraft.class_1764;
/*    */ import net.minecraft.class_9278;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.gen.Invoker;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1764.class})
/*    */ public interface CrossbowItemAccessor
/*    */ {
/*    */   @Invoker("method_20309")
/*    */   static float meteor$getSpeed(class_9278 itemStack) {
/* 16 */     return 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\CrossbowItemAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */