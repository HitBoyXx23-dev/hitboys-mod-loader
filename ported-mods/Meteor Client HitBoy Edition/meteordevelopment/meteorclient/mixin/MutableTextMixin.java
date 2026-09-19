/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.mixininterface.IText;
/*    */ import net.minecraft.class_2477;
/*    */ import net.minecraft.class_5250;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_5250.class})
/*    */ public abstract class MutableTextMixin
/*    */   implements IText
/*    */ {
/*    */   @Shadow
/*    */   @Nullable
/*    */   private class_2477 field_39009;
/*    */   
/*    */   public void meteor$invalidateCache() {
/* 22 */     this.field_39009 = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\MutableTextMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */