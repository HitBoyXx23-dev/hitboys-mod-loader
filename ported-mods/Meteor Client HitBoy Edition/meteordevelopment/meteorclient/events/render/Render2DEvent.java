/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import net.minecraft.class_332;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Render2DEvent
/*    */ {
/* 12 */   private static final Render2DEvent INSTANCE = new Render2DEvent();
/*    */   public class_332 drawContext;
/*    */   public int screenWidth;
/*    */   public int screenHeight;
/*    */   public double frameTime;
/*    */   public float tickDelta;
/*    */   
/*    */   public static Render2DEvent get(class_332 drawContext, int screenWidth, int screenHeight, float tickDelta) {
/* 20 */     INSTANCE.drawContext = drawContext;
/* 21 */     INSTANCE.screenWidth = screenWidth;
/* 22 */     INSTANCE.screenHeight = screenHeight;
/* 23 */     INSTANCE.frameTime = Utils.frameTime;
/* 24 */     INSTANCE.tickDelta = tickDelta;
/* 25 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\Render2DEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */