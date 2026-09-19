/*    */ package meteordevelopment.meteorclient.events.packets;
/*    */ 
/*    */ import net.minecraft.class_2767;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlaySoundPacketEvent
/*    */ {
/* 12 */   private static final PlaySoundPacketEvent INSTANCE = new PlaySoundPacketEvent();
/*    */   
/*    */   public class_2767 packet;
/*    */   
/*    */   public static PlaySoundPacketEvent get(class_2767 packet) {
/* 17 */     INSTANCE.packet = packet;
/* 18 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\packets\PlaySoundPacketEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */