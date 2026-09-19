/*    */ package meteordevelopment.meteorclient.utils.misc;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.utils.PreInit;
/*    */ import net.minecraft.class_1267;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_2535;
/*    */ import net.minecraft.class_2598;
/*    */ import net.minecraft.class_634;
/*    */ import net.minecraft.class_638;
/*    */ import net.minecraft.class_640;
/*    */ import net.minecraft.class_745;
/*    */ import net.minecraft.class_8675;
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
/*    */ public class FakeClientPlayer
/*    */ {
/*    */   private static class_638 world;
/*    */   private static class_1657 player;
/*    */   private static class_640 playerListEntry;
/*    */   private static UUID lastId;
/*    */   private static boolean needsNewEntry;
/*    */   
/*    */   @PreInit
/*    */   public static void init() {
/* 38 */     MeteorClient.EVENT_BUS.subscribe(FakeClientPlayer.class);
/*    */   }
/*    */   
/*    */   public static class_1657 getPlayer() {
/* 42 */     UUID id = MeteorClient.mc.method_1548().method_44717();
/*    */     
/* 44 */     if (player == null || !id.equals(lastId)) {
/* 45 */       if (world == null)
/*    */       {
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
/* 71 */         world = new class_638(new class_634(MeteorClient.mc, new class_2535(class_2598.field_11942), new class_8675(null, new GameProfile(MeteorClient.mc.method_1548().method_44717(), MeteorClient.mc.method_1548().method_1676()), null, null, null, null, MeteorClient.mc.method_1558(), null, null, null, null, null, null, false)), new class_638.class_5271(class_1267.field_5802, false, false), world.method_27983(), world.method_40134(), 1, 1, null, false, 0L, world.method_8615());
/*    */       }
/*    */ 
/*    */       
/* 75 */       player = (class_1657)new class_745(world, new GameProfile(id, MeteorClient.mc.method_1548().method_1676()));
/*    */       
/* 77 */       lastId = id;
/* 78 */       needsNewEntry = true;
/*    */     } 
/*    */     
/* 81 */     return player;
/*    */   }
/*    */   
/*    */   public static class_640 getPlayerListEntry() {
/* 85 */     if (playerListEntry == null || needsNewEntry) {
/* 86 */       playerListEntry = new class_640(new GameProfile(lastId, MeteorClient.mc.method_1548().method_1676()), false);
/* 87 */       needsNewEntry = false;
/*    */     } 
/*    */     
/* 90 */     return playerListEntry;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\FakeClientPlayer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */