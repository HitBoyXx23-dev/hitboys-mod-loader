/*    */ package meteordevelopment.meteorclient.events.entity;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_1799;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DropItemsEvent
/*    */   extends Cancellable
/*    */ {
/* 12 */   private static final DropItemsEvent INSTANCE = new DropItemsEvent();
/*    */   
/*    */   public class_1799 itemStack;
/*    */   
/*    */   public static DropItemsEvent get(class_1799 itemStack) {
/* 17 */     INSTANCE.setCancelled(false);
/* 18 */     INSTANCE.itemStack = itemStack;
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\DropItemsEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */