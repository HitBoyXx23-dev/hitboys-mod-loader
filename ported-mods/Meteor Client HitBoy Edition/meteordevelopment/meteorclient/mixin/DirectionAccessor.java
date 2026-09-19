/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import net.minecraft.class_2350;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.gen.Accessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_2350.class})
/*    */ public interface DirectionAccessor
/*    */ {
/*    */   @Accessor("field_11041")
/*    */   static class_2350[] meteor$getHorizontal() {
/* 16 */     throw new AssertionError();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\DirectionAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */