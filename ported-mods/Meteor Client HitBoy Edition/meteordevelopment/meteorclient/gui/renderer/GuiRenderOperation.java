/*    */ package meteordevelopment.meteorclient.gui.renderer;
/*    */ 
/*    */ import meteordevelopment.meteorclient.utils.misc.Pool;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class GuiRenderOperation<T extends GuiRenderOperation<T>>
/*    */ {
/*    */   protected double x;
/*    */   protected double y;
/*    */   protected Color color;
/*    */   
/*    */   public void set(double x, double y, Color color) {
/* 16 */     this.x = x;
/* 17 */     this.y = y;
/* 18 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run(Pool<T> pool) {
/* 23 */     onRun();
/* 24 */     pool.free(this);
/*    */   }
/*    */   
/*    */   protected abstract void onRun();
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\renderer\GuiRenderOperation.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */