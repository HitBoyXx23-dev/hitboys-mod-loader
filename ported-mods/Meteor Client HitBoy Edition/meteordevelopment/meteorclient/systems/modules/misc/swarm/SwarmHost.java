/*    */ package meteordevelopment.meteorclient.systems.modules.misc.swarm;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.ServerSocket;
/*    */ import java.net.Socket;
/*    */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SwarmHost
/*    */   extends Thread
/*    */ {
/*    */   private ServerSocket socket;
/* 17 */   private final SwarmConnection[] clientConnections = new SwarmConnection[50];
/*    */   
/*    */   public SwarmHost(int port) {
/*    */     try {
/* 21 */       this.socket = new ServerSocket(port);
/* 22 */     } catch (IOException e) {
/* 23 */       this.socket = null;
/* 24 */       ChatUtils.errorPrefix("Swarm", "Couldn't start a server on port %s.", new Object[] { Integer.valueOf(port) });
/* 25 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 28 */     if (this.socket != null) start();
/*    */   
/*    */   }
/*    */   
/*    */   public void run() {
/* 33 */     ChatUtils.infoPrefix("Swarm", "Listening for incoming connections on port %s.", new Object[] { Integer.valueOf(this.socket.getLocalPort()) });
/*    */     
/* 35 */     while (!isInterrupted()) {
/*    */       try {
/* 37 */         Socket connection = this.socket.accept();
/* 38 */         assignConnectionToSubServer(connection);
/* 39 */       } catch (IOException e) {
/* 40 */         ChatUtils.errorPrefix("Swarm", "Error making a connection to worker.", new Object[0]);
/* 41 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void assignConnectionToSubServer(Socket connection) {
/* 47 */     for (int i = 0; i < this.clientConnections.length; i++) {
/* 48 */       if (this.clientConnections[i] == null) {
/* 49 */         this.clientConnections[i] = new SwarmConnection(connection);
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void disconnect() {
/* 56 */     for (SwarmConnection connection : this.clientConnections) {
/* 57 */       if (connection != null) connection.disconnect();
/*    */     
/*    */     } 
/*    */     try {
/* 61 */       this.socket.close();
/* 62 */     } catch (IOException e) {
/* 63 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 66 */     ChatUtils.infoPrefix("Swarm", "Server closed on port %s.", new Object[] { Integer.valueOf(this.socket.getLocalPort()) });
/*    */     
/* 68 */     interrupt();
/*    */   }
/*    */   
/*    */   public void sendMessage(String s) {
/* 72 */     MeteorExecutor.execute(() -> {
/*    */           for (SwarmConnection connection : this.clientConnections) {
/*    */             if (connection != null) {
/*    */               connection.messageToSend = s;
/*    */             }
/*    */           } 
/*    */         });
/*    */   }
/*    */   
/*    */   public SwarmConnection[] getConnections() {
/* 82 */     return this.clientConnections;
/*    */   }
/*    */   
/*    */   public int getConnectionCount() {
/* 86 */     int count = 0;
/*    */     
/* 88 */     for (SwarmConnection clientConnection : this.clientConnections) {
/* 89 */       if (clientConnection != null) count++;
/*    */     
/*    */     } 
/* 92 */     return count;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\misc\swarm\SwarmHost.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */