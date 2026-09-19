/*    */ package meteordevelopment.meteorclient.events.world;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_1113;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlaySoundEvent
/*    */   extends Cancellable
/*    */ {
/* 12 */   private static final PlaySoundEvent INSTANCE = new PlaySoundEvent();
/*    */   
/*    */   public class_1113 sound;
/*    */   
/*    */   public static PlaySoundEvent get(class_1113 sound) {
/* 17 */     INSTANCE.setCancelled(false);
/* 18 */     INSTANCE.sound = sound;
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\world\PlaySoundEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */