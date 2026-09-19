/*    */ package meteordevelopment.meteorclient.systems.modules.misc.swarm;
/*    */ 
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SwarmConnection
/*    */   extends Thread
/*    */ {
/*    */   public final Socket socket;
/*    */   public String messageToSend;
/*    */   
/*    */   public SwarmConnection(Socket socket) {
/* 19 */     this.socket = socket;
/* 20 */     start();
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 25 */     ChatUtils.infoPrefix("Swarm", "New worker connected on %s.", new Object[] { getIp(this.socket.getInetAddress().getHostAddress()) });
/*    */     
/*    */     try {
/* 28 */       DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
/*    */       
/* 30 */       while (!isInterrupted()) {
/* 31 */         if (this.messageToSend != null) {
/*    */           try {
/* 33 */             out.writeUTF(this.messageToSend);
/* 34 */             out.flush();
/* 35 */           } catch (Exception e) {
/* 36 */             ChatUtils.errorPrefix("Swarm", "Encountered error when sending command.", new Object[0]);
/* 37 */             e.printStackTrace();
/*    */           } 
/*    */           
/* 40 */           this.messageToSend = null;
/*    */         } 
/*    */       } 
/*    */       
/* 44 */       out.close();
/* 45 */     } catch (IOException e) {
/* 46 */       ChatUtils.infoPrefix("Swarm", "Error creating a connection with %s on port %s.", new Object[] { getIp(this.socket.getInetAddress().getHostAddress()), Integer.valueOf(this.socket.getPort()) });
/* 47 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void disconnect() {
/*    */     try {
/* 53 */       this.socket.close();
/* 54 */     } catch (IOException e) {
/* 55 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 58 */     ChatUtils.infoPrefix("Swarm", "Worker disconnected on ip: %s.", new Object[] { this.socket.getInetAddress().getHostAddress() });
/*    */     
/* 60 */     interrupt();
/*    */   }
/*    */   
/*    */   public String getConnection() {
/* 64 */     return getIp(this.socket.getInetAddress().getHostAddress()) + ":" + getIp(this.socket.getInetAddress().getHostAddress());
/*    */   }
/*    */   
/*    */   private String getIp(String ip) {
/* 68 */     return ip.equals("127.0.0.1") ? "localhost" : ip;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\misc\swarm\SwarmConnection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */