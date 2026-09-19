/*    */ package meteordevelopment.meteorclient.utils.entity;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
/*    */ import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerManager;
/*    */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1934;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TargetUtils
/*    */ {
/* 25 */   private static final List<class_1297> ENTITIES = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static class_1297 get(Predicate<class_1297> isGood, SortPriority sortPriority) {
/* 32 */     ENTITIES.clear();
/* 33 */     getList(ENTITIES, isGood, sortPriority, 1);
/* 34 */     if (!ENTITIES.isEmpty()) {
/* 35 */       return ENTITIES.getFirst();
/*    */     }
/*    */     
/* 38 */     return null;
/*    */   }
/*    */   
/*    */   public static void getList(List<class_1297> targetList, Predicate<class_1297> isGood, SortPriority sortPriority, int maxCount) {
/* 42 */     targetList.clear();
/*    */     
/* 44 */     for (class_1297 entity : MeteorClient.mc.field_1687.method_18112()) {
/* 45 */       if (entity != null && isGood.test(entity)) targetList.add(entity);
/*    */     
/*    */     } 
/* 48 */     FakePlayerManager.forEach(fp -> {
/*    */           if (fp != null && isGood.test(fp))
/*    */             targetList.add(fp); 
/*    */         });
/* 52 */     targetList.sort(sortPriority);
/*    */     
/* 54 */     if (targetList.size() > maxCount) {
/* 55 */       targetList.subList(maxCount, targetList.size()).clear();
/*    */     }
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static class_1657 getPlayerTarget(double range, SortPriority priority) {
/* 61 */     if (!Utils.canUpdate()) return null; 
/* 62 */     return (class_1657)get(entity -> { if (entity instanceof class_1657) { class_1657 player = (class_1657)entity; if (entity != MeteorClient.mc.field_1724) { if (player.method_29504() || player.method_6032() <= 0.0F) return false;  if (!PlayerUtils.isWithin(entity, range)) return false;  if (!Friends.get().shouldAttack(player)) return false;  if (entity instanceof FakePlayerEntity) { FakePlayerEntity fakePlayer = (FakePlayerEntity)entity; return !fakePlayer.noHit; }  return (EntityUtils.getGameMode(player) == class_1934.field_9215); }  }  return false; }priority);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isBadTarget(class_1657 target, double range) {
/* 73 */     if (target == null) return true; 
/* 74 */     return (!PlayerUtils.isWithin((class_1297)target, range) || !target.method_5805() || target.method_29504() || target.method_6032() <= 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\entity\TargetUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */