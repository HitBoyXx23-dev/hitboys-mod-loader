/*    */ package meteordevelopment.meteorclient.systems.modules.misc.swarm;
/*    */ 
/*    */ import java.io.DataInputStream;
/*    */ import java.io.IOException;
/*    */ import java.net.Socket;
/*    */ import meteordevelopment.meteorclient.commands.Commands;
/*    */ import meteordevelopment.meteorclient.pathing.PathManagers;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ import net.minecraft.class_2248;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SwarmWorker
/*    */   extends Thread
/*    */ {
/*    */   private Socket socket;
/*    */   public class_2248 target;
/*    */   
/*    */   public SwarmWorker(String ip, int port) {
/*    */     try {
/* 23 */       this.socket = new Socket(ip, port);
/* 24 */     } catch (Exception e) {
/* 25 */       this.socket = null;
/* 26 */       ChatUtils.warningPrefix("Swarm", "Server not found at %s on port %s.", new Object[] { ip, Integer.valueOf(port) });
/* 27 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 30 */     if (this.socket != null) start();
/*    */   
/*    */   }
/*    */   
/*    */   public void run() {
/* 35 */     ChatUtils.infoPrefix("Swarm", "Connected to Swarm host on at %s on port %s.", new Object[] { getIp(this.socket.getInetAddress().getHostAddress()), Integer.valueOf(this.socket.getPort()) });
/*    */     
/*    */     try {
/* 38 */       DataInputStream in = new DataInputStream(this.socket.getInputStream());
/*    */ 
/*    */       
/* 41 */       while (!isInterrupted()) {
/* 42 */         String read = in.readUTF();
/*    */         
/* 44 */         if (read.startsWith("swarm")) {
/* 45 */           ChatUtils.infoPrefix("Swarm", "Received command: (highlight)%s", new Object[] { read });
/*    */           
/*    */           try {
/* 48 */             Commands.dispatch(read);
/* 49 */           } catch (Exception e) {
/* 50 */             ChatUtils.error("Error fetching command.", new Object[0]);
/* 51 */             e.printStackTrace();
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 56 */       in.close();
/* 57 */     } catch (IOException e) {
/* 58 */       ChatUtils.errorPrefix("Swarm", "Error in connection to host.", new Object[0]);
/* 59 */       e.printStackTrace();
/* 60 */       disconnect();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void disconnect() {
/*    */     try {
/* 66 */       this.socket.close();
/* 67 */     } catch (IOException e) {
/* 68 */       e.printStackTrace();
/*    */     } 
/*    */     
/* 71 */     PathManagers.get().stop();
/*    */     
/* 73 */     ChatUtils.infoPrefix("Swarm", "Disconnected from host.", new Object[0]);
/*    */     
/* 75 */     interrupt();
/*    */   }
/*    */   
/*    */   public void tick() {
/* 79 */     if (this.target == null)
/*    */       return; 
/* 81 */     PathManagers.get().stop();
/* 82 */     PathManagers.get().mine(new class_2248[] { this.target });
/*    */     
/* 84 */     this.target = null;
/*    */   }
/*    */   
/*    */   public String getConnection() {
/* 88 */     return getIp(this.socket.getInetAddress().getHostAddress()) + ":" + getIp(this.socket.getInetAddress().getHostAddress());
/*    */   }
/*    */   
/*    */   private String getIp(String ip) {
/* 92 */     return ip.equals("127.0.0.1") ? "localhost" : ip;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\misc\swarm\SwarmWorker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */