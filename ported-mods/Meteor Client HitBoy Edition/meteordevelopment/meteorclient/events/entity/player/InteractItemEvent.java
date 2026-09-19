/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_1269;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InteractItemEvent
/*    */ {
/* 12 */   private static final InteractItemEvent INSTANCE = new InteractItemEvent();
/*    */   
/*    */   public class_1268 hand;
/*    */   public class_1269 toReturn;
/*    */   
/*    */   public static InteractItemEvent get(class_1268 hand) {
/* 18 */     INSTANCE.hand = hand;
/* 19 */     INSTANCE.toReturn = null;
/*    */     
/* 21 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\InteractItemEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */