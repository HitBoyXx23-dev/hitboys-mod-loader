/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.mixininterface.IBox;
/*    */ import net.minecraft.class_238;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Mutable;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ @Mixin({class_238.class})
/*    */ public abstract class BoxMixin
/*    */   implements IBox {
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   public double field_1323;
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   public double field_1322;
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   public double field_1321;
/*    */   
/*    */   public void meteor$expand(double v) {
/* 27 */     this.field_1323 -= v;
/* 28 */     this.field_1322 -= v;
/* 29 */     this.field_1321 -= v;
/* 30 */     this.field_1320 += v;
/* 31 */     this.field_1325 += v;
/* 32 */     this.field_1324 += v; } @Shadow @Final @Mutable public double field_1320; @Shadow @Final
/*    */   @Mutable
/*    */   public double field_1325; @Shadow
/*    */   @Final
/*    */   @Mutable
/* 37 */   public double field_1324; public void meteor$set(double x1, double y1, double z1, double x2, double y2, double z2) { this.field_1323 = Math.min(x1, x2);
/* 38 */     this.field_1322 = Math.min(y1, y2);
/* 39 */     this.field_1321 = Math.min(z1, z2);
/* 40 */     this.field_1320 = Math.max(x1, x2);
/* 41 */     this.field_1325 = Math.max(y1, y2);
/* 42 */     this.field_1324 = Math.max(z1, z2); }
/*    */ 
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BoxMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */