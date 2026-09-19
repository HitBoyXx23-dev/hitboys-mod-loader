/*    */ package meteordevelopment.meteorclient.events.packets;
/*    */ 
/*    */ import net.minecraft.class_2535;
/*    */ import net.minecraft.class_2596;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Sent
/*    */ {
/*    */   public class_2596<?> packet;
/*    */   public class_2535 connection;
/*    */   
/*    */   public Sent(class_2596<?> packet, class_2535 connection) {
/* 50 */     this.packet = packet;
/* 51 */     this.connection = connection;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendSilently(class_2596<?> packet) {
/* 61 */     this.connection.method_52906(packet, null, true);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\packets\PacketEvent$Sent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */