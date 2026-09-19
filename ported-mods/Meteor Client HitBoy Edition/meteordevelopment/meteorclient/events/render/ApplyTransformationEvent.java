/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_804;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ApplyTransformationEvent
/*    */   extends Cancellable
/*    */ {
/* 12 */   private static final ApplyTransformationEvent INSTANCE = new ApplyTransformationEvent();
/*    */   
/*    */   public class_804 transformation;
/*    */   public boolean leftHanded;
/*    */   
/*    */   public static ApplyTransformationEvent get(class_804 transformation, boolean leftHanded) {
/* 18 */     INSTANCE.setCancelled(false);
/*    */     
/* 20 */     INSTANCE.transformation = transformation;
/* 21 */     INSTANCE.leftHanded = leftHanded;
/*    */     
/* 23 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\ApplyTransformationEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */