/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ import meteordevelopment.meteorclient.renderer.Renderer3D;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import net.minecraft.class_4587;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Render3DEvent
/*    */ {
/* 13 */   private static final Render3DEvent INSTANCE = new Render3DEvent();
/*    */   
/*    */   public class_4587 matrices;
/*    */   
/*    */   public Renderer3D renderer;
/*    */   
/*    */   public Renderer3D depthRenderer;
/*    */   public double frameTime;
/*    */   
/*    */   public static Render3DEvent get(class_4587 matrices, Renderer3D renderer, Renderer3D depthRenderer, float tickDelta, double offsetX, double offsetY, double offsetZ) {
/* 23 */     INSTANCE.matrices = matrices;
/* 24 */     INSTANCE.renderer = renderer;
/* 25 */     INSTANCE.depthRenderer = depthRenderer;
/* 26 */     INSTANCE.frameTime = Utils.frameTime;
/* 27 */     INSTANCE.tickDelta = tickDelta;
/* 28 */     INSTANCE.offsetX = offsetX;
/* 29 */     INSTANCE.offsetY = offsetY;
/* 30 */     INSTANCE.offsetZ = offsetZ;
/* 31 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public float tickDelta;
/*    */   public double offsetX;
/*    */   public double offsetY;
/*    */   public double offsetZ;
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\Render3DEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */