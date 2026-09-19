/*     */ package meteordevelopment.meteorclient.utils.player;
/*     */ 
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*     */ import meteordevelopment.meteorclient.pathing.PathManagers;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.NoFall;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.entity.DamageUtils;
/*     */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*     */ import meteordevelopment.meteorclient.utils.misc.text.TextUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.world.Dimension;
/*     */ import net.minecraft.class_12195;
/*     */ import net.minecraft.class_12206;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1934;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2586;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2828;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_640;
/*     */ import net.minecraft.class_9334;
/*     */ import org.joml.Math;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerUtils
/*     */ {
/*  43 */   private static final double diagonal = 1.0D / Math.sqrt(2.0D);
/*  44 */   private static final class_243 horizontalVelocity = new class_243(0.0D, 0.0D, 0.0D);
/*     */   
/*  46 */   private static final Color color = new Color();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Color getPlayerColor(class_1657 entity, Color defaultColor) {
/*  52 */     if (Friends.get().isFriend(entity)) {
/*  53 */       return color.set((Color)(Config.get()).friendColor.get()).a(defaultColor.a);
/*     */     }
/*     */     
/*  56 */     if (((Boolean)(Config.get()).useTeamColor.get()).booleanValue() && !color.set(TextUtils.getMostPopularColor(entity.method_5476())).equals(Utils.WHITE)) {
/*  57 */       return color.a(defaultColor.a);
/*     */     }
/*     */     
/*  60 */     return defaultColor;
/*     */   }
/*     */   
/*     */   public static class_243 getHorizontalVelocity(double bps) {
/*  64 */     float yaw = MeteorClient.mc.field_1724.method_36454();
/*     */     
/*  66 */     if (PathManagers.get().isPathing()) {
/*  67 */       yaw = PathManagers.get().getTargetYaw();
/*     */     }
/*     */     
/*  70 */     class_243 forward = class_243.method_1030(0.0F, yaw);
/*  71 */     class_243 right = class_243.method_1030(0.0F, yaw + 90.0F);
/*  72 */     double velX = 0.0D;
/*  73 */     double velZ = 0.0D;
/*     */     
/*  75 */     boolean a = false;
/*  76 */     if (MeteorClient.mc.field_1724.field_3913.field_54155.comp_3159()) {
/*  77 */       velX += forward.field_1352 / 20.0D * bps;
/*  78 */       velZ += forward.field_1350 / 20.0D * bps;
/*  79 */       a = true;
/*     */     } 
/*  81 */     if (MeteorClient.mc.field_1724.field_3913.field_54155.comp_3160()) {
/*  82 */       velX -= forward.field_1352 / 20.0D * bps;
/*  83 */       velZ -= forward.field_1350 / 20.0D * bps;
/*  84 */       a = true;
/*     */     } 
/*     */     
/*  87 */     boolean b = false;
/*  88 */     if (MeteorClient.mc.field_1724.field_3913.field_54155.comp_3162()) {
/*  89 */       velX += right.field_1352 / 20.0D * bps;
/*  90 */       velZ += right.field_1350 / 20.0D * bps;
/*  91 */       b = true;
/*     */     } 
/*  93 */     if (MeteorClient.mc.field_1724.field_3913.field_54155.comp_3161()) {
/*  94 */       velX -= right.field_1352 / 20.0D * bps;
/*  95 */       velZ -= right.field_1350 / 20.0D * bps;
/*  96 */       b = true;
/*     */     } 
/*     */     
/*  99 */     if (a && b) {
/* 100 */       velX *= diagonal;
/* 101 */       velZ *= diagonal;
/*     */     } 
/*     */     
/* 104 */     ((IVec3d)horizontalVelocity).meteor$setXZ(velX, velZ);
/* 105 */     return horizontalVelocity;
/*     */   }
/*     */   
/*     */   public static void centerPlayer() {
/* 109 */     double x = class_3532.method_15357(MeteorClient.mc.field_1724.method_23317()) + 0.5D;
/* 110 */     double z = class_3532.method_15357(MeteorClient.mc.field_1724.method_23321()) + 0.5D;
/* 111 */     MeteorClient.mc.field_1724.method_5814(x, MeteorClient.mc.field_1724.method_23318(), z);
/* 112 */     MeteorClient.mc.field_1724.field_3944.method_52787((class_2596)new class_2828.class_2829(MeteorClient.mc.field_1724.method_23317(), MeteorClient.mc.field_1724.method_23318(), MeteorClient.mc.field_1724.method_23321(), MeteorClient.mc.field_1724.method_24828(), MeteorClient.mc.field_1724.field_5976));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canSeeEntity(class_1297 entity) {
/* 117 */     class_243 vec1 = new class_243(0.0D, 0.0D, 0.0D);
/* 118 */     class_243 vec2 = new class_243(0.0D, 0.0D, 0.0D);
/*     */     
/* 120 */     ((IVec3d)vec1).meteor$set(MeteorClient.mc.field_1724.method_23317(), MeteorClient.mc.field_1724.method_23318() + MeteorClient.mc.field_1724.method_5751(), MeteorClient.mc.field_1724.method_23321());
/* 121 */     ((IVec3d)vec2).meteor$set(entity.method_23317(), entity.method_23318(), entity.method_23321());
/* 122 */     boolean canSeeFeet = (MeteorClient.mc.field_1687.method_17742(new class_3959(vec1, vec2, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)MeteorClient.mc.field_1724)).method_17783() == class_239.class_240.field_1333);
/*     */     
/* 124 */     ((IVec3d)vec2).meteor$set(entity.method_23317(), entity.method_23318() + entity.method_5751(), entity.method_23321());
/* 125 */     boolean canSeeEyes = (MeteorClient.mc.field_1687.method_17742(new class_3959(vec1, vec2, class_3959.class_3960.field_17558, class_3959.class_242.field_1348, (class_1297)MeteorClient.mc.field_1724)).method_17783() == class_239.class_240.field_1333);
/*     */     
/* 127 */     return (canSeeFeet || canSeeEyes);
/*     */   }
/*     */   
/*     */   public static float[] calculateAngle(class_243 target) {
/* 131 */     class_243 eyesPos = new class_243(MeteorClient.mc.field_1724.method_23317(), MeteorClient.mc.field_1724.method_23318() + MeteorClient.mc.field_1724.method_18381(MeteorClient.mc.field_1724.method_18376()), MeteorClient.mc.field_1724.method_23321());
/*     */     
/* 133 */     double dX = target.field_1352 - eyesPos.field_1352;
/* 134 */     double dY = (target.field_1351 - eyesPos.field_1351) * -1.0D;
/* 135 */     double dZ = target.field_1350 - eyesPos.field_1350;
/*     */     
/* 137 */     double dist = Math.sqrt(dX * dX + dZ * dZ);
/*     */     
/* 139 */     return new float[] { (float)class_3532.method_15338(Math.toDegrees(Math.atan2(dZ, dX)) - 90.0D), (float)class_3532.method_15338(Math.toDegrees(Math.atan2(dY, dist))) };
/*     */   }
/*     */   
/*     */   public static boolean shouldPause(boolean ifBreaking, boolean ifEating, boolean ifDrinking) {
/* 143 */     if (ifBreaking && MeteorClient.mc.field_1761.method_2923()) return true; 
/* 144 */     if (ifEating && MeteorClient.mc.field_1724.method_6115() && (MeteorClient.mc.field_1724.method_6047().method_7909().method_57347().method_57832(class_9334.field_50075) || MeteorClient.mc.field_1724.method_6079().method_7909().method_57347().method_57832(class_9334.field_50075))) return true; 
/* 145 */     return (ifDrinking && MeteorClient.mc.field_1724.method_6115() && (MeteorClient.mc.field_1724.method_6047().method_7909() instanceof net.minecraft.class_1812 || MeteorClient.mc.field_1724.method_6079().method_7909() instanceof net.minecraft.class_1812));
/*     */   }
/*     */   
/*     */   public static boolean isMoving() {
/* 149 */     return (MeteorClient.mc.field_1724.field_6250 != 0.0F || MeteorClient.mc.field_1724.field_6212 != 0.0F);
/*     */   }
/*     */   
/*     */   public static boolean isSprinting() {
/* 153 */     return (MeteorClient.mc.field_1724.method_5624() && (MeteorClient.mc.field_1724.field_6250 != 0.0F || MeteorClient.mc.field_1724.field_6212 != 0.0F));
/*     */   }
/*     */   
/*     */   public static boolean isInHole(boolean doubles) {
/* 157 */     if (!Utils.canUpdate()) return false;
/*     */     
/* 159 */     class_2338 blockPos = MeteorClient.mc.field_1724.method_24515();
/* 160 */     int air = 0;
/*     */     
/* 162 */     for (class_2350 direction : class_2350.values()) {
/* 163 */       if (direction != class_2350.field_11036) {
/*     */         
/* 165 */         class_2680 state = MeteorClient.mc.field_1687.method_8320(blockPos.method_10093(direction));
/*     */         
/* 167 */         if (state.method_26204().method_9520() < 600.0F) {
/* 168 */           if (!doubles || direction == class_2350.field_11033) return false;
/*     */           
/* 170 */           air++;
/*     */           
/* 172 */           for (class_2350 dir : class_2350.values()) {
/* 173 */             if (dir != direction.method_10153() && dir != class_2350.field_11036) {
/*     */               
/* 175 */               class_2680 blockState1 = MeteorClient.mc.field_1687.method_8320(blockPos.method_10093(direction).method_10093(dir));
/*     */               
/* 177 */               if (blockState1.method_26204().method_9520() < 600.0F)
/* 178 */                 return false; 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 184 */     return (air < 2);
/*     */   }
/*     */   
/*     */   public static float possibleHealthReductions() {
/* 188 */     return possibleHealthReductions(true, true);
/*     */   }
/*     */   
/*     */   public static float possibleHealthReductions(boolean entities, boolean fall) {
/* 192 */     float damageTaken = 0.0F;
/*     */     
/* 194 */     if (entities) {
/* 195 */       for (class_1297 entity : MeteorClient.mc.field_1687.method_18112()) {
/*     */         
/* 197 */         if (entity instanceof net.minecraft.class_1511) {
/* 198 */           float crystalDamage = DamageUtils.crystalDamage((class_1309)MeteorClient.mc.field_1724, entity.method_73189());
/* 199 */           if (crystalDamage > damageTaken) damageTaken = crystalDamage; 
/*     */           continue;
/*     */         } 
/* 202 */         if (entity instanceof class_1657) { class_1657 player = (class_1657)entity; if (!Friends.get().isFriend(player) && isWithin(entity, 5.0D)) {
/* 203 */             float attackDamage = DamageUtils.getAttackDamage((class_1309)player, (class_1297)MeteorClient.mc.field_1724);
/* 204 */             if (attackDamage > damageTaken) damageTaken = attackDamage;
/*     */           
/*     */           }  }
/*     */       
/*     */       } 
/* 209 */       if (((class_12195)MeteorClient.mc.field_1687.method_75728().method_75694(class_12206.field_63756)).comp_5138()) {
/* 210 */         for (class_2586 blockEntity : Utils.blockEntities()) {
/* 211 */           class_2338 bp = blockEntity.method_11016();
/* 212 */           class_243 pos = new class_243(bp.method_10263(), bp.method_10264(), bp.method_10260());
/*     */           
/* 214 */           if (blockEntity instanceof net.minecraft.class_2587) {
/* 215 */             float explosionDamage = DamageUtils.bedDamage((class_1309)MeteorClient.mc.field_1724, pos);
/* 216 */             if (explosionDamage > damageTaken) damageTaken = explosionDamage;
/*     */           
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 223 */     if (fall && 
/* 224 */       !Modules.get().isActive(NoFall.class) && MeteorClient.mc.field_1724.field_6017 > 3.0D) {
/* 225 */       float damage = DamageUtils.fallDamage((class_1309)MeteorClient.mc.field_1724);
/*     */       
/* 227 */       if (damage > damageTaken && !EntityUtils.isAboveWater((class_1297)MeteorClient.mc.field_1724)) {
/* 228 */         damageTaken = damage;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 233 */     return damageTaken;
/*     */   }
/*     */   
/*     */   public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 237 */     return Math.sqrt(squaredDistance(x1, y1, z1, x2, y2, z2));
/*     */   }
/*     */   
/*     */   public static double distanceTo(class_1297 entity) {
/* 241 */     return distanceTo(entity.method_23317(), entity.method_23318(), entity.method_23321());
/*     */   }
/*     */   
/*     */   public static double distanceTo(class_2338 blockPos) {
/* 245 */     return distanceTo(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260());
/*     */   }
/*     */   
/*     */   public static double distanceTo(class_243 vec3d) {
/* 249 */     return distanceTo(vec3d.method_10216(), vec3d.method_10214(), vec3d.method_10215());
/*     */   }
/*     */   
/*     */   public static double distanceTo(double x, double y, double z) {
/* 253 */     return Math.sqrt(squaredDistanceTo(x, y, z));
/*     */   }
/*     */   
/*     */   public static double squaredDistanceTo(class_1297 entity) {
/* 257 */     return squaredDistanceTo(entity.method_23317(), entity.method_23318(), entity.method_23321());
/*     */   }
/*     */   
/*     */   public static double squaredDistanceTo(class_2338 blockPos) {
/* 261 */     return squaredDistanceTo(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260());
/*     */   }
/*     */   
/*     */   public static double squaredDistanceTo(double x, double y, double z) {
/* 265 */     return squaredDistance(MeteorClient.mc.field_1724.method_23317(), MeteorClient.mc.field_1724.method_23318(), MeteorClient.mc.field_1724.method_23321(), x, y, z);
/*     */   }
/*     */   
/*     */   public static double squaredDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 269 */     double f = x1 - x2;
/* 270 */     double g = y1 - y2;
/* 271 */     double h = z1 - z2;
/* 272 */     return Math.fma(f, f, Math.fma(g, g, h * h));
/*     */   }
/*     */   
/*     */   public static boolean isWithin(class_1297 entity, double r) {
/* 276 */     return (squaredDistanceTo(entity.method_23317(), entity.method_23318(), entity.method_23321()) <= r * r);
/*     */   }
/*     */   
/*     */   public static boolean isWithin(class_243 vec3d, double r) {
/* 280 */     return (squaredDistanceTo(vec3d.method_10216(), vec3d.method_10214(), vec3d.method_10215()) <= r * r);
/*     */   }
/*     */   
/*     */   public static boolean isWithin(class_2338 blockPos, double r) {
/* 284 */     return (squaredDistanceTo(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260()) <= r * r);
/*     */   }
/*     */   
/*     */   public static boolean isWithin(double x, double y, double z, double r) {
/* 288 */     return (squaredDistanceTo(x, y, z) <= r * r);
/*     */   }
/*     */   
/*     */   public static double distanceToCamera(double x, double y, double z) {
/* 292 */     return Math.sqrt(squaredDistanceToCamera(x, y, z));
/*     */   }
/*     */   
/*     */   public static double distanceToCamera(class_1297 entity) {
/* 296 */     return distanceToCamera(entity.method_23317(), entity.method_23318() + entity.method_18381(entity.method_18376()), entity.method_23321());
/*     */   }
/*     */   
/*     */   public static double squaredDistanceToCamera(double x, double y, double z) {
/* 300 */     class_243 cameraPos = MeteorClient.mc.field_1773.method_19418().method_71156();
/* 301 */     return squaredDistance(cameraPos.field_1352, cameraPos.field_1351, cameraPos.field_1350, x, y, z);
/*     */   }
/*     */   
/*     */   public static double squaredDistanceToCamera(class_1297 entity) {
/* 305 */     return squaredDistanceToCamera(entity.method_23317(), entity.method_23318() + entity.method_18381(entity.method_18376()), entity.method_23321());
/*     */   }
/*     */   
/*     */   public static boolean isWithinCamera(class_1297 entity, double r) {
/* 309 */     return (squaredDistanceToCamera(entity.method_23317(), entity.method_23318(), entity.method_23321()) <= r * r);
/*     */   }
/*     */   
/*     */   public static boolean isWithinCamera(class_243 vec3d, double r) {
/* 313 */     return (squaredDistanceToCamera(vec3d.method_10216(), vec3d.method_10214(), vec3d.method_10215()) <= r * r);
/*     */   }
/*     */   
/*     */   public static boolean isWithinCamera(class_2338 blockPos, double r) {
/* 317 */     return (squaredDistanceToCamera(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260()) <= r * r);
/*     */   }
/*     */   
/*     */   public static boolean isWithinCamera(double x, double y, double z, double r) {
/* 321 */     return (squaredDistanceToCamera(x, y, z) <= r * r);
/*     */   }
/*     */   
/*     */   public static boolean isWithinReach(class_1297 entity) {
/* 325 */     return isWithinReach(entity.method_23317(), entity.method_23318(), entity.method_23321());
/*     */   }
/*     */   
/*     */   public static boolean isWithinReach(class_243 vec3d) {
/* 329 */     return isWithinReach(vec3d.method_10216(), vec3d.method_10214(), vec3d.method_10215());
/*     */   }
/*     */   
/*     */   public static boolean isWithinReach(class_2338 blockPos) {
/* 333 */     return isWithinReach(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260());
/*     */   }
/*     */   
/*     */   public static boolean isWithinReach(double x, double y, double z) {
/* 337 */     return (squaredDistance(MeteorClient.mc.field_1724.method_23317(), MeteorClient.mc.field_1724.method_23320(), MeteorClient.mc.field_1724.method_23321(), x, y, z) <= MeteorClient.mc.field_1724.method_55754() * MeteorClient.mc.field_1724.method_55754());
/*     */   }
/*     */   
/*     */   public static Dimension getDimension() {
/* 341 */     if (MeteorClient.mc.field_1687 == null) return Dimension.Overworld;
/*     */     
/* 343 */     switch (MeteorClient.mc.field_1687.method_27983().method_29177().method_12832()) { case "the_nether": case "the_end":  }  return 
/*     */ 
/*     */       
/* 346 */       Dimension.Overworld;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class_1934 getGameMode() {
/* 351 */     if (MeteorClient.mc.field_1724 == null) return null; 
/* 352 */     class_640 playerListEntry = MeteorClient.mc.method_1562().method_2871(MeteorClient.mc.field_1724.method_5667());
/* 353 */     if (playerListEntry == null) return null; 
/* 354 */     return playerListEntry.method_2958();
/*     */   }
/*     */   
/*     */   public static float getTotalHealth() {
/* 358 */     return MeteorClient.mc.field_1724.method_6032() + MeteorClient.mc.field_1724.method_6067();
/*     */   }
/*     */   
/*     */   public static boolean isAlive() {
/* 362 */     return (MeteorClient.mc.field_1724.method_5805() && !MeteorClient.mc.field_1724.method_29504());
/*     */   }
/*     */   
/*     */   public static int getPing() {
/* 366 */     if (MeteorClient.mc.method_1562() == null) return 0;
/*     */     
/* 368 */     class_640 playerListEntry = MeteorClient.mc.method_1562().method_2871(MeteorClient.mc.field_1724.method_5667());
/* 369 */     if (playerListEntry == null) return 0; 
/* 370 */     return playerListEntry.method_2959();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\PlayerUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */