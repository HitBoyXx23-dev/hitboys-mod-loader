/*    */ package meteordevelopment.meteorclient.events.entity;
/*    */ 
/*    */ import net.minecraft.class_10255;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BoatMoveEvent
/*    */ {
/* 11 */   private static final BoatMoveEvent INSTANCE = new BoatMoveEvent();
/*    */   
/*    */   public class_10255 boat;
/*    */   
/*    */   public static BoatMoveEvent get(class_10255 entity) {
/* 16 */     INSTANCE.boat = entity;
/* 17 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\BoatMoveEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */