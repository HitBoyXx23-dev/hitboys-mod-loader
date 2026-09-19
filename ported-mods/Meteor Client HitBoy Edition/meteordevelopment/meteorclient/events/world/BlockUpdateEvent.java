/*    */ package meteordevelopment.meteorclient.events.world;
/*    */ 
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2680;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockUpdateEvent
/*    */ {
/* 12 */   private static final BlockUpdateEvent INSTANCE = new BlockUpdateEvent();
/*    */   public class_2338 pos;
/*    */   public class_2680 oldState;
/*    */   public class_2680 newState;
/*    */   
/*    */   public static BlockUpdateEvent get(class_2338 pos, class_2680 oldState, class_2680 newState) {
/* 18 */     INSTANCE.pos = pos;
/* 19 */     INSTANCE.oldState = oldState;
/* 20 */     INSTANCE.newState = newState;
/*    */     
/* 22 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\world\BlockUpdateEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */