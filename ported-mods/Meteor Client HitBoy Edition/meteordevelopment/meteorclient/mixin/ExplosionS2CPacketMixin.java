/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import meteordevelopment.meteorclient.mixininterface.IExplosionS2CPacket;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_2664;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Mutable;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_2664.class})
/*    */ public abstract class ExplosionS2CPacketMixin
/*    */   implements IExplosionS2CPacket
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   private Optional<class_243> comp_2884;
/*    */   
/*    */   public void meteor$setVelocityX(float velocity) {
/* 27 */     if (this.comp_2884.isPresent()) {
/* 28 */       class_243 kb = this.comp_2884.get();
/* 29 */       this.comp_2884 = Optional.of(new class_243(velocity, kb.field_1351, kb.field_1350));
/*    */     } else {
/* 31 */       this.comp_2884 = Optional.of(new class_243(velocity, 0.0D, 0.0D));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$setVelocityY(float velocity) {
/* 37 */     if (this.comp_2884.isPresent()) {
/* 38 */       class_243 kb = this.comp_2884.get();
/* 39 */       this.comp_2884 = Optional.of(new class_243(kb.field_1352, velocity, kb.field_1350));
/*    */     } else {
/* 41 */       this.comp_2884 = Optional.of(new class_243(0.0D, velocity, 0.0D));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$setVelocityZ(float velocity) {
/* 47 */     if (this.comp_2884.isPresent()) {
/* 48 */       class_243 kb = this.comp_2884.get();
/* 49 */       this.comp_2884 = Optional.of(new class_243(kb.field_1352, kb.field_1351, velocity));
/*    */     } else {
/* 51 */       this.comp_2884 = Optional.of(new class_243(0.0D, 0.0D, velocity));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ExplosionS2CPacketMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */