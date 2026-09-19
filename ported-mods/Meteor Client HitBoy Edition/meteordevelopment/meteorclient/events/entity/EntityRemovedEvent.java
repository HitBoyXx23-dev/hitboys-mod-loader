/*    */ package meteordevelopment.meteorclient.events.entity;
/*    */ 
/*    */ import net.minecraft.class_1297;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityRemovedEvent
/*    */ {
/* 11 */   private static final EntityRemovedEvent INSTANCE = new EntityRemovedEvent();
/*    */   
/*    */   public class_1297 entity;
/*    */   
/*    */   public static EntityRemovedEvent get(class_1297 entity) {
/* 16 */     INSTANCE.entity = entity;
/* 17 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\EntityRemovedEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */