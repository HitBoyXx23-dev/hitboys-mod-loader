/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import net.minecraft.class_1799;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PickItemsEvent
/*    */ {
/* 11 */   private static final PickItemsEvent INSTANCE = new PickItemsEvent();
/*    */   
/*    */   public class_1799 itemStack;
/*    */   public int count;
/*    */   
/*    */   public static PickItemsEvent get(class_1799 itemStack, int count) {
/* 17 */     INSTANCE.itemStack = itemStack;
/* 18 */     INSTANCE.count = count;
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\PickItemsEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */