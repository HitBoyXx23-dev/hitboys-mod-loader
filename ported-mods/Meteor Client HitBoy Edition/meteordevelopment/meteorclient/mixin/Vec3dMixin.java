/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*    */ import net.minecraft.class_243;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Mutable;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_243.class})
/*    */ public abstract class Vec3dMixin
/*    */   implements IVec3d
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   public double field_1352;
/*    */   
/*    */   public class_243 meteor$set(double x, double y, double z) {
/* 23 */     this.field_1352 = x;
/* 24 */     this.field_1351 = y;
/* 25 */     this.field_1350 = z;
/*    */     
/* 27 */     return (class_243)this; } @Shadow @Final
/*    */   @Mutable
/*    */   public double field_1351; @Shadow
/*    */   @Final
/*    */   @Mutable
/* 32 */   public double field_1350; public class_243 meteor$setXZ(double x, double z) { this.field_1352 = x;
/* 33 */     this.field_1350 = z;
/*    */     
/* 35 */     return (class_243)this; }
/*    */ 
/*    */ 
/*    */   
/*    */   public class_243 meteor$setY(double y) {
/* 40 */     this.field_1351 = y;
/*    */     
/* 42 */     return (class_243)this;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\Vec3dMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */