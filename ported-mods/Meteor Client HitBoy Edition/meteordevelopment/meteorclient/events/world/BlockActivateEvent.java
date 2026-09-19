/*    */ package meteordevelopment.meteorclient.events.world;
/*    */ 
/*    */ import net.minecraft.class_2680;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockActivateEvent
/*    */ {
/* 11 */   private static final BlockActivateEvent INSTANCE = new BlockActivateEvent();
/*    */   
/*    */   public class_2680 blockState;
/*    */   
/*    */   public static BlockActivateEvent get(class_2680 blockState) {
/* 16 */     INSTANCE.blockState = blockState;
/* 17 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\world\BlockActivateEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */