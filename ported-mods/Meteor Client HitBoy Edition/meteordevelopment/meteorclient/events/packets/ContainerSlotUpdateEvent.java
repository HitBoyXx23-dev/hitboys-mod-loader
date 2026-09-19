/*    */ package meteordevelopment.meteorclient.events.packets;
/*    */ 
/*    */ import net.minecraft.class_2653;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerSlotUpdateEvent
/*    */ {
/* 11 */   private static final ContainerSlotUpdateEvent INSTANCE = new ContainerSlotUpdateEvent();
/*    */   
/*    */   public class_2653 packet;
/*    */   
/*    */   public static ContainerSlotUpdateEvent get(class_2653 packet) {
/* 16 */     INSTANCE.packet = packet;
/* 17 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\packets\ContainerSlotUpdateEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */