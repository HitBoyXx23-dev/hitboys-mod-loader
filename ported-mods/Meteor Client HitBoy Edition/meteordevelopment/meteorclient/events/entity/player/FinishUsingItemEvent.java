/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import net.minecraft.class_1799;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FinishUsingItemEvent
/*    */ {
/* 11 */   private static final FinishUsingItemEvent INSTANCE = new FinishUsingItemEvent();
/*    */   
/*    */   public class_1799 itemStack;
/*    */   
/*    */   public static FinishUsingItemEvent get(class_1799 itemStack) {
/* 16 */     INSTANCE.itemStack = itemStack;
/* 17 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\FinishUsingItemEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */