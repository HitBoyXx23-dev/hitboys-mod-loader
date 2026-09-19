/*    */ package meteordevelopment.meteorclient.events.packets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_2535;
/*    */ import net.minecraft.class_2596;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Receive
/*    */   extends Cancellable
/*    */ {
/*    */   public class_2596<?> packet;
/*    */   public class_2535 connection;
/*    */   
/*    */   public Receive(class_2596<?> packet, class_2535 connection) {
/* 18 */     setCancelled(false);
/* 19 */     this.packet = packet;
/* 20 */     this.connection = connection;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\packets\PacketEvent$Receive.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */