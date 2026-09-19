/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import net.minecraft.class_1761;
/*    */ import net.minecraft.class_481;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.gen.Accessor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_481.class})
/*    */ public interface CreativeInventoryScreenAccessor
/*    */ {
/*    */   @Accessor("field_2896")
/*    */   static class_1761 meteor$getSelectedTab() {
/* 17 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\CreativeInventoryScreenAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */