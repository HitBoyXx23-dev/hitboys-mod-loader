/*    */ package meteordevelopment.meteorclient.utils.world;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.game.GameJoinedEvent;
/*    */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_3532;
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
/*    */ public class TickRate
/*    */ {
/* 22 */   public static TickRate INSTANCE = new TickRate();
/*    */   
/* 24 */   private final float[] tickRates = new float[20];
/* 25 */   private int nextIndex = 0;
/* 26 */   private long timeLastTimeUpdate = -1L;
/*    */   private long timeGameJoined;
/*    */   
/*    */   private TickRate() {
/* 30 */     MeteorClient.EVENT_BUS.subscribe(this);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onReceivePacket(PacketEvent.Receive event) {
/* 35 */     if (event.packet instanceof net.minecraft.class_2761) {
/* 36 */       long now = System.currentTimeMillis();
/* 37 */       float timeElapsed = (float)(now - this.timeLastTimeUpdate) / 1000.0F;
/* 38 */       this.tickRates[this.nextIndex] = class_3532.method_15363(20.0F / timeElapsed, 0.0F, 20.0F);
/* 39 */       this.nextIndex = (this.nextIndex + 1) % this.tickRates.length;
/* 40 */       this.timeLastTimeUpdate = now;
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onGameJoined(GameJoinedEvent event) {
/* 46 */     Arrays.fill(this.tickRates, 0.0F);
/* 47 */     this.nextIndex = 0;
/* 48 */     this.timeGameJoined = this.timeLastTimeUpdate = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public float getTickRate() {
/* 52 */     if (!Utils.canUpdate()) return 0.0F; 
/* 53 */     if (System.currentTimeMillis() - this.timeGameJoined < 4000L) return 20.0F;
/*    */     
/* 55 */     int numTicks = 0;
/* 56 */     float sumTickRates = 0.0F;
/* 57 */     for (float tickRate : this.tickRates) {
/* 58 */       if (tickRate > 0.0F) {
/* 59 */         sumTickRates += tickRate;
/* 60 */         numTicks++;
/*    */       } 
/*    */     } 
/* 63 */     return sumTickRates / numTicks;
/*    */   }
/*    */   
/*    */   public float getTimeSinceLastTick() {
/* 67 */     long now = System.currentTimeMillis();
/* 68 */     if (now - this.timeGameJoined < 4000L) return 0.0F; 
/* 69 */     return (float)(now - this.timeLastTimeUpdate) / 1000.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\world\TickRate.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */