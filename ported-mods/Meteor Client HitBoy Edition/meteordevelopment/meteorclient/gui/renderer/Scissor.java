/*    */ package meteordevelopment.meteorclient.gui.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.mixininterface.IGpuDevice;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Scissor
/*    */ {
/*    */   public int x;
/*    */   public int y;
/*    */   public int width;
/*    */   public int height;
/* 20 */   public final List<Runnable> postTasks = new ArrayList<>();
/*    */   
/*    */   public Scissor set(double x, double y, double width, double height) {
/* 23 */     if (width < 0.0D) width = 0.0D; 
/* 24 */     if (height < 0.0D) height = 0.0D;
/*    */     
/* 26 */     this.x = (int)Math.round(x);
/* 27 */     this.y = (int)Math.round(y);
/* 28 */     this.width = (int)Math.round(width);
/* 29 */     this.height = (int)Math.round(height);
/*    */     
/* 31 */     this.postTasks.clear();
/*    */     
/* 33 */     return this;
/*    */   }
/*    */   
/*    */   public void push() {
/* 37 */     ((IGpuDevice)RenderSystem.getDevice()).meteor$pushScissor(this.x, Utils.getWindowHeight() - this.y - this.height, this.width, this.height);
/*    */   }
/*    */   
/*    */   public void pop() {
/* 41 */     ((IGpuDevice)RenderSystem.getDevice()).meteor$popScissor();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\renderer\Scissor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */