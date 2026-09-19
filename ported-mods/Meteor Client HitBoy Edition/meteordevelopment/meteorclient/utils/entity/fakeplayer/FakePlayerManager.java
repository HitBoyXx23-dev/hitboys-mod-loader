/*    */ package meteordevelopment.meteorclient.utils.entity.fakeplayer;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.stream.Stream;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import net.minecraft.class_1657;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FakePlayerManager
/*    */ {
/* 18 */   private static final List<FakePlayerEntity> ENTITIES = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static List<FakePlayerEntity> getFakePlayers() {
/* 24 */     return ENTITIES;
/*    */   }
/*    */   
/*    */   public static FakePlayerEntity get(String name) {
/* 28 */     for (FakePlayerEntity fp : ENTITIES) {
/* 29 */       if (fp.method_5477().getString().equals(name)) return fp;
/*    */     
/*    */     } 
/* 32 */     return null;
/*    */   }
/*    */   
/*    */   public static void add(String name, float health, boolean copyInv) {
/* 36 */     if (!Utils.canUpdate())
/*    */       return; 
/* 38 */     FakePlayerEntity fakePlayer = new FakePlayerEntity((class_1657)MeteorClient.mc.field_1724, name, health, copyInv);
/* 39 */     fakePlayer.spawn();
/* 40 */     ENTITIES.add(fakePlayer);
/*    */   }
/*    */   
/*    */   public static void remove(FakePlayerEntity fp) {
/* 44 */     ENTITIES.removeIf(fp1 -> {
/*    */           if (fp1.method_5477().getString().equals(fp.method_5477().getString())) {
/*    */             fp1.despawn();
/*    */             return true;
/*    */           } 
/*    */           return false;
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public static void clear() {
/* 55 */     if (ENTITIES.isEmpty())
/* 56 */       return;  ENTITIES.forEach(FakePlayerEntity::despawn);
/* 57 */     ENTITIES.clear();
/*    */   }
/*    */   
/*    */   public static void forEach(Consumer<FakePlayerEntity> action) {
/* 61 */     for (FakePlayerEntity fakePlayer : ENTITIES) {
/* 62 */       action.accept(fakePlayer);
/*    */     }
/*    */   }
/*    */   
/*    */   public static int count() {
/* 67 */     return ENTITIES.size();
/*    */   }
/*    */   
/*    */   public static Stream<FakePlayerEntity> stream() {
/* 71 */     return ENTITIES.stream();
/*    */   }
/*    */   
/*    */   public static boolean contains(FakePlayerEntity fp) {
/* 75 */     return ENTITIES.contains(fp);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\entity\fakeplayer\FakePlayerManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */