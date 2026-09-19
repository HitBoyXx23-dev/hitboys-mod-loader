/*    */ package meteordevelopment.meteorclient.events.world;
/*    */ 
/*    */ import net.minecraft.class_639;
/*    */ import net.minecraft.class_642;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ServerConnectBeginEvent
/*    */ {
/* 12 */   private static final ServerConnectBeginEvent INSTANCE = new ServerConnectBeginEvent();
/*    */   public class_639 address;
/*    */   public class_642 info;
/*    */   
/*    */   public static ServerConnectBeginEvent get(class_639 address, class_642 info) {
/* 17 */     INSTANCE.address = address;
/* 18 */     INSTANCE.info = info;
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\world\ServerConnectBeginEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */