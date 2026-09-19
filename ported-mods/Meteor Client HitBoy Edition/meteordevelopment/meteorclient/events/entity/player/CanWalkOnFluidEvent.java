/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import net.minecraft.class_3610;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CanWalkOnFluidEvent
/*    */ {
/* 23 */   private static final CanWalkOnFluidEvent INSTANCE = new CanWalkOnFluidEvent();
/*    */   
/*    */   public class_3610 fluidState;
/*    */   public boolean walkOnFluid;
/*    */   
/*    */   public static CanWalkOnFluidEvent get(class_3610 fluid) {
/* 29 */     INSTANCE.fluidState = fluid;
/* 30 */     INSTANCE.walkOnFluid = false;
/* 31 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\CanWalkOnFluidEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */