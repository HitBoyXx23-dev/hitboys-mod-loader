/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import net.minecraft.class_1761;
/*    */ import net.minecraft.class_5321;
/*    */ import net.minecraft.class_7706;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.gen.Accessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_7706.class})
/*    */ public interface ItemGroupsAccessor
/*    */ {
/*    */   @Accessor("field_40206")
/*    */   static class_5321<class_1761> meteor$getInventory() {
/* 18 */     throw new AssertionError();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ItemGroupsAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */