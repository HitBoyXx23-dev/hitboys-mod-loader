/*    */ package meteordevelopment.meteorclient.events.world;
/*    */ 
/*    */ import java.net.InetSocketAddress;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerConnectEndEvent
/*    */ {
/* 11 */   private static final ServerConnectEndEvent INSTANCE = new ServerConnectEndEvent();
/*    */   public InetSocketAddress address;
/*    */   
/*    */   public static ServerConnectEndEvent get(InetSocketAddress address) {
/* 15 */     INSTANCE.address = address;
/* 16 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\world\ServerConnectEndEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */