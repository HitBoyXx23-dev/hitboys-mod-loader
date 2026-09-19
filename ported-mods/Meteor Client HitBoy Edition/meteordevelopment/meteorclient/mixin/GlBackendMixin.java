/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderPass;
/*    */ import meteordevelopment.meteorclient.mixininterface.IGpuDevice;
/*    */ import net.minecraft.class_10865;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ 
/*    */ @Mixin({class_10865.class})
/*    */ public abstract class GlBackendMixin
/*    */   implements IGpuDevice {
/*    */   @Unique
/*    */   private int x;
/*    */   @Unique
/*    */   private int y;
/*    */   @Unique
/*    */   private int width;
/*    */   @Unique
/*    */   private int height;
/*    */   @Unique
/*    */   private boolean set;
/*    */   
/*    */   public void meteor$pushScissor(int x, int y, int width, int height) {
/* 24 */     if (this.set) {
/* 25 */       throw new IllegalStateException("Currently there can only be one global scissor pushed");
/*    */     }
/* 27 */     this.x = x;
/* 28 */     this.y = y;
/* 29 */     this.width = width;
/* 30 */     this.height = height;
/*    */     
/* 32 */     this.set = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$popScissor() {
/* 37 */     if (!this.set) {
/* 38 */       throw new IllegalStateException("No scissor pushed");
/*    */     }
/* 40 */     this.set = false;
/*    */   }
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public void meteor$onCreateRenderPass(RenderPass pass) {
/* 46 */     if (this.set)
/* 47 */       pass.enableScissor(this.x, this.y, this.width, this.height); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\GlBackendMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */