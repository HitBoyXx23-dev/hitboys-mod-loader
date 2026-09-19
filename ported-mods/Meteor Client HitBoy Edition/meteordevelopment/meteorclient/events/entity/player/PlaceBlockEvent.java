/*    */ package meteordevelopment.meteorclient.events.entity.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_2248;
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlaceBlockEvent
/*    */   extends Cancellable
/*    */ {
/* 13 */   private static final PlaceBlockEvent INSTANCE = new PlaceBlockEvent();
/*    */   
/*    */   public class_2338 blockPos;
/*    */   public class_2248 block;
/*    */   
/*    */   public static PlaceBlockEvent get(class_2338 blockPos, class_2248 block) {
/* 19 */     INSTANCE.setCancelled(false);
/* 20 */     INSTANCE.blockPos = blockPos;
/* 21 */     INSTANCE.block = block;
/* 22 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\entity\player\PlaceBlockEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */