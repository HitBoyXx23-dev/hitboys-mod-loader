/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.mixininterface.IRaycastContext;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_3726;
/*    */ import net.minecraft.class_3959;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Mutable;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ @Mixin({class_3959.class})
/*    */ public abstract class RaycastContextMixin
/*    */   implements IRaycastContext
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   private class_243 field_17553;
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   private class_243 field_17554;
/*    */   
/*    */   public void meteor$set(class_243 start, class_243 end, class_3959.class_3960 shapeType, class_3959.class_242 fluidHandling, class_1297 entity) {
/* 28 */     this.field_17553 = start;
/* 29 */     this.field_17554 = end;
/* 30 */     this.field_17555 = shapeType;
/* 31 */     this.field_17556 = fluidHandling;
/* 32 */     this.field_17557 = class_3726.method_16195(entity);
/*    */   }
/*    */   
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   private class_3959.class_3960 field_17555;
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   private class_3959.class_242 field_17556;
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   private class_3726 field_17557;
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\RaycastContextMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */