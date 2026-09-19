/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.mixininterface.IAbstractFurnaceScreenHandler;
/*    */ import net.minecraft.class_1720;
/*    */ import net.minecraft.class_1799;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1720.class})
/*    */ public abstract class AbstractFurnaceScreenHandlerMixin
/*    */   implements IAbstractFurnaceScreenHandler
/*    */ {
/*    */   @Shadow
/*    */   protected abstract boolean method_7640(class_1799 paramclass_1799);
/*    */   
/*    */   public boolean meteor$isItemSmeltable(class_1799 itemStack) {
/* 21 */     return method_7640(itemStack);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\AbstractFurnaceScreenHandlerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */