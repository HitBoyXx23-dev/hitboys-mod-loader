/*    */ package meteordevelopment.meteorclient.events.entity;
/*    */ 
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_243;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityMoveEvent
/*    */ {
/* 12 */   private static final EntityMoveEvent INSTANCE = new EntityMoveEvent();
/*    */   
/*    */   public class_1297 entity;
/*    */   public class_243 movement;
/*    */   
/*    */   public static EntityMoveEvent get(class_1297 entity, class_243 movement) {
/* 18 */     INSTANCE.entity = entity;
/* 19 */     INSTANCE.movement = movement;
/* 20 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\EntityMoveEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */