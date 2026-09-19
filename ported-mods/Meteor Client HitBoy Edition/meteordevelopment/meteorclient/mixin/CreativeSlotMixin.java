/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.mixininterface.ISlot;
/*    */ import net.minecraft.class_1735;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(targets = {"net/minecraft/class_481$class_484"})
/*    */ public abstract class CreativeSlotMixin
/*    */   implements ISlot
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   class_1735 field_2898;
/*    */   
/*    */   public int meteor$getId() {
/* 20 */     return this.field_2898.field_7874;
/*    */   }
/*    */ 
/*    */   
/*    */   public int meteor$getIndex() {
/* 25 */     return this.field_2898.method_34266();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\CreativeSlotMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */