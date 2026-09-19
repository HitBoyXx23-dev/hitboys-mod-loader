/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.mixininterface.ISlot;
/*    */ import net.minecraft.class_1735;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ @Mixin({class_1735.class})
/*    */ public abstract class SlotMixin
/*    */   implements ISlot
/*    */ {
/*    */   @Shadow
/*    */   public int field_7874;
/*    */   @Shadow
/*    */   @Final
/*    */   private int field_7875;
/*    */   
/*    */   public int meteor$getId() {
/* 21 */     return this.field_7874;
/*    */   }
/*    */ 
/*    */   
/*    */   public int meteor$getIndex() {
/* 26 */     return this.field_7875;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\SlotMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */