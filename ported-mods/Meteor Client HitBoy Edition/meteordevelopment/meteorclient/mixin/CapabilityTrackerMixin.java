/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.blaze3d.opengl.GlStateManager;
/*    */ import meteordevelopment.meteorclient.mixininterface.ICapabilityTracker;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({GlStateManager.class_1018.class})
/*    */ public abstract class CapabilityTrackerMixin
/*    */   implements ICapabilityTracker
/*    */ {
/*    */   @Shadow
/*    */   private boolean field_5051;
/*    */   
/*    */   @Shadow
/*    */   public abstract void method_4470(boolean paramBoolean);
/*    */   
/*    */   public boolean meteor$get() {
/* 23 */     return this.field_5051;
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$set(boolean state) {
/* 28 */     method_4470(state);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\CapabilityTrackerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */