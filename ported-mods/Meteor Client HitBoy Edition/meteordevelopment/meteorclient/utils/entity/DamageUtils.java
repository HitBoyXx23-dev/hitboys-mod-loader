/*     */ package meteordevelopment.meteorclient.utils.entity;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.function.BiFunction;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import net.minecraft.class_1267;
/*     */ import net.minecraft.class_1280;
/*     */ import net.minecraft.class_1282;
/*     */ import net.minecraft.class_1293;
/*     */ import net.minecraft.class_1294;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1304;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1322;
/*     */ import net.minecraft.class_1324;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1893;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_1934;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2374;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2902;
/*     */ import net.minecraft.class_3483;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ import net.minecraft.class_5134;
/*     */ import net.minecraft.class_6880;
/*     */ import net.minecraft.class_8103;
/*     */ import net.minecraft.class_9274;
/*     */ import net.minecraft.class_9285;
/*     */ import net.minecraft.class_9334;
/*     */ import net.minecraft.class_9362;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DamageUtils
/*     */ {
/*     */   public static final RaycastFactory HIT_FACTORY;
/*     */   
/*     */   static {
/*  65 */     HIT_FACTORY = ((context, blockPos) -> {
/*     */         class_2680 blockState = MeteorClient.mc.field_1687.method_8320(blockPos);
/*     */         return (blockState.method_26204().method_9520() < 600.0F) ? null : blockState.method_26220((class_1922)MeteorClient.mc.field_1687, blockPos).method_1092(context.start(), context.end(), blockPos);
/*     */       });
/*     */   }
/*     */ 
/*     */   
/*     */   public static float crystalDamage(class_1309 target, class_243 targetPos, class_238 targetBox, class_243 explosionPos, RaycastFactory raycastFactory) {
/*  73 */     return explosionDamage(target, targetPos, targetBox, explosionPos, 12.0F, raycastFactory);
/*     */   }
/*     */   
/*     */   public static float bedDamage(class_1309 target, class_243 targetPos, class_238 targetBox, class_243 explosionPos, RaycastFactory raycastFactory) {
/*  77 */     return explosionDamage(target, targetPos, targetBox, explosionPos, 10.0F, raycastFactory);
/*     */   }
/*     */   
/*     */   public static float anchorDamage(class_1309 target, class_243 targetPos, class_238 targetBox, class_243 explosionPos, RaycastFactory raycastFactory) {
/*  81 */     return explosionDamage(target, targetPos, targetBox, explosionPos, 10.0F, raycastFactory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float explosionDamage(class_1309 target, class_243 targetPos, class_238 targetBox, class_243 explosionPos, float power, RaycastFactory raycastFactory) {
/*  90 */     double modDistance = PlayerUtils.distance(targetPos.field_1352, targetPos.field_1351, targetPos.field_1350, explosionPos.field_1352, explosionPos.field_1351, explosionPos.field_1350);
/*  91 */     if (modDistance > power) return 0.0F;
/*     */     
/*  93 */     double exposure = getExposure(explosionPos, targetBox, raycastFactory);
/*  94 */     double impact = (1.0D - modDistance / power) * exposure;
/*  95 */     float damage = (int)((impact * impact + impact) / 2.0D * 7.0D * 12.0D + 1.0D);
/*     */     
/*  97 */     return calculateReductions(damage, (class_1297)target, MeteorClient.mc.field_1687.method_48963().method_48807(null));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float crystalDamage(class_1309 target, class_243 crystal, boolean predictMovement, class_2338 obsidianPos) {
/* 103 */     return overridingExplosionDamage(target, crystal, 12.0F, predictMovement, obsidianPos, class_2246.field_10540.method_9564());
/*     */   }
/*     */   
/*     */   public static float crystalDamage(class_1309 target, class_243 crystal) {
/* 107 */     return explosionDamage(target, crystal, 12.0F, false);
/*     */   }
/*     */   
/*     */   public static float bedDamage(class_1309 target, class_243 bed) {
/* 111 */     return explosionDamage(target, bed, 10.0F, false);
/*     */   }
/*     */   
/*     */   public static float anchorDamage(class_1309 target, class_243 anchor) {
/* 115 */     return overridingExplosionDamage(target, anchor, 10.0F, false, class_2338.method_49638((class_2374)anchor), class_2246.field_10124.method_9564());
/*     */   }
/*     */   
/*     */   private static float overridingExplosionDamage(class_1309 target, class_243 explosionPos, float power, boolean predictMovement, class_2338 overridePos, class_2680 overrideState) {
/* 119 */     return explosionDamage(target, explosionPos, power, predictMovement, getOverridingHitFactory(overridePos, overrideState));
/*     */   }
/*     */   
/*     */   private static float explosionDamage(class_1309 target, class_243 explosionPos, float power, boolean predictMovement) {
/* 123 */     return explosionDamage(target, explosionPos, power, predictMovement, HIT_FACTORY);
/*     */   }
/*     */   
/*     */   private static float explosionDamage(class_1309 target, class_243 explosionPos, float power, boolean predictMovement, RaycastFactory raycastFactory) {
/* 127 */     if (target == null) return 0.0F; 
/* 128 */     if (target instanceof class_1657) { class_1657 player = (class_1657)target; if (EntityUtils.getGameMode(player) == class_1934.field_9220 && !(player instanceof meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity)) return 0.0F;  }
/*     */     
/* 130 */     class_243 position = predictMovement ? target.method_73189().method_1019(target.method_18798()) : target.method_73189();
/*     */     
/* 132 */     class_238 box = target.method_5829();
/* 133 */     if (predictMovement) box = box.method_997(target.method_18798());
/*     */     
/* 135 */     return explosionDamage(target, position, box, explosionPos, power, raycastFactory);
/*     */   }
/*     */   
/*     */   public static RaycastFactory getOverridingHitFactory(class_2338 overridePos, class_2680 overrideState) {
/* 139 */     return (context, blockPos) -> {
/*     */         class_2680 blockState;
/*     */         if (blockPos.equals(overridePos)) {
/*     */           blockState = overrideState;
/*     */         } else {
/*     */           blockState = MeteorClient.mc.field_1687.method_8320(blockPos);
/*     */           if (blockState.method_26204().method_9520() < 600.0F) {
/*     */             return null;
/*     */           }
/*     */         } 
/*     */         return blockState.method_26220((class_1922)MeteorClient.mc.field_1687, blockPos).method_1092(context.start(), context.end(), blockPos);
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getAttackDamage(class_1309 attacker, class_1297 target) {
/* 157 */     float itemDamage = (float)attacker.method_45325(class_5134.field_23721);
/* 158 */     class_1657 player = (class_1657)attacker; class_1282 damageSource = (attacker instanceof class_1657) ? MeteorClient.mc.field_1687.method_48963().method_48802(player) : MeteorClient.mc.field_1687.method_48963().method_48812(attacker);
/*     */     
/* 160 */     float damage = modifyAttackDamage(attacker, target, attacker.method_59958(), damageSource, itemDamage);
/* 161 */     return calculateReductions(damage, target, damageSource);
/*     */   }
/*     */   
/*     */   public static float getAttackDamage(class_1309 attacker, class_1297 target, class_1799 weapon) {
/* 165 */     class_1324 original = attacker.method_5996(class_5134.field_23721);
/* 166 */     class_1324 copy = new class_1324(class_5134.field_23721, o -> {
/*     */         
/* 168 */         }); copy.method_6192(original.method_6201());
/* 169 */     for (class_1322 modifier : original.method_6195()) {
/* 170 */       copy.method_26835(modifier);
/*     */     }
/* 172 */     copy.method_6200(class_1792.field_8006);
/*     */     
/* 174 */     class_9285 attributeModifiers = (class_9285)weapon.method_58694(class_9334.field_49636);
/* 175 */     if (attributeModifiers != null)
/* 176 */       attributeModifiers.method_57482(class_1304.field_6173, (entry, modifier) -> {
/*     */             if (entry == class_5134.field_23721) {
/*     */               copy.method_55696(modifier);
/*     */             }
/*     */           }); 
/* 181 */     float itemDamage = (float)copy.method_6194();
/* 182 */     class_1657 player = (class_1657)attacker; class_1282 damageSource = (attacker instanceof class_1657) ? MeteorClient.mc.field_1687.method_48963().method_48802(player) : MeteorClient.mc.field_1687.method_48963().method_48812(attacker);
/*     */     
/* 184 */     float damage = modifyAttackDamage(attacker, target, weapon, damageSource, itemDamage);
/* 185 */     return calculateReductions(damage, target, damageSource);
/*     */   }
/*     */ 
/*     */   
/*     */   private static float modifyAttackDamage(class_1309 attacker, class_1297 target, class_1799 weapon, class_1282 damageSource, float damage) {
/* 190 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/* 191 */     Utils.getEnchantments(weapon, (Object2IntMap)object2IntOpenHashMap);
/* 192 */     float enchantDamage = 0.0F;
/*     */     
/* 194 */     int sharpness = Utils.getEnchantmentLevel((Object2IntMap)object2IntOpenHashMap, class_1893.field_9118);
/* 195 */     if (sharpness > 0) {
/* 196 */       enchantDamage += 1.0F + 0.5F * (sharpness - 1);
/*     */     }
/*     */     
/* 199 */     int baneOfArthropods = Utils.getEnchantmentLevel((Object2IntMap)object2IntOpenHashMap, class_1893.field_9112);
/* 200 */     if (baneOfArthropods > 0 && target.method_5864().method_20210(class_3483.field_48285)) {
/* 201 */       enchantDamage += 2.5F * baneOfArthropods;
/*     */     }
/*     */     
/* 204 */     int impaling = Utils.getEnchantmentLevel((Object2IntMap)object2IntOpenHashMap, class_1893.field_9106);
/* 205 */     if (impaling > 0 && target.method_5864().method_20210(class_3483.field_48284)) {
/* 206 */       enchantDamage += 2.5F * impaling;
/*     */     }
/*     */     
/* 209 */     int smite = Utils.getEnchantmentLevel((Object2IntMap)object2IntOpenHashMap, class_1893.field_9123);
/* 210 */     if (smite > 0 && target.method_5864().method_20210(class_3483.field_49931)) {
/* 211 */       enchantDamage += 2.5F * smite;
/*     */     }
/*     */ 
/*     */     
/* 215 */     if (attacker instanceof class_1657) { class_1657 playerEntity = (class_1657)attacker;
/* 216 */       float charge = playerEntity.method_7261(0.5F);
/* 217 */       damage *= 0.2F + charge * charge * 0.8F;
/* 218 */       enchantDamage *= charge;
/*     */       
/* 220 */       class_1792 class_1792 = weapon.method_7909(); if (class_1792 instanceof class_9362) { class_9362 item = (class_9362)class_1792;
/* 221 */         float bonusDamage = item.method_58403(target, damage, damageSource);
/* 222 */         if (bonusDamage > 0.0F) {
/* 223 */           int density = Utils.getEnchantmentLevel(weapon, class_1893.field_50157);
/* 224 */           if (density > 0) bonusDamage += (float)(0.5D * attacker.field_6017); 
/* 225 */           damage += bonusDamage;
/*     */         }  }
/*     */ 
/*     */ 
/*     */       
/* 230 */       if (charge > 0.9F && attacker.field_6017 > 0.0D && !attacker.method_24828() && !attacker.method_6101() && !attacker.method_5799() && !attacker.method_6059(class_1294.field_5919) && !attacker.method_5765()) {
/* 231 */         damage *= 1.5F;
/*     */       } }
/*     */ 
/*     */     
/* 235 */     return damage + enchantDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float fallDamage(class_1309 entity) {
/* 244 */     if (entity instanceof class_1657) { class_1657 player = (class_1657)entity; if ((player.method_31549()).field_7479) return 0.0F;  }
/* 245 */      if (entity.method_6059(class_1294.field_5906) || entity.method_6059(class_1294.field_5902)) return 0.0F;
/*     */ 
/*     */     
/* 248 */     int surface = MeteorClient.mc.field_1687.method_8500(entity.method_24515()).method_12032(class_2902.class_2903.field_13197).method_12603(entity.method_31477() & 0xF, entity.method_31479() & 0xF);
/* 249 */     if (entity.method_31478() >= surface) return fallDamageReductions(entity, surface);
/*     */ 
/*     */     
/* 252 */     class_3965 raycastResult = MeteorClient.mc.field_1687.method_17742(new class_3959(entity.method_73189(), new class_243(entity.method_23317(), MeteorClient.mc.field_1687.method_31607(), entity.method_23321()), class_3959.class_3960.field_17558, class_3959.class_242.field_36338, (class_1297)entity));
/* 253 */     if (raycastResult.method_17783() == class_239.class_240.field_1333) return 0.0F;
/*     */     
/* 255 */     return fallDamageReductions(entity, raycastResult.method_17777().method_10264());
/*     */   }
/*     */   
/*     */   private static float fallDamageReductions(class_1309 entity, int surface) {
/* 259 */     int fallHeight = (int)(entity.method_23318() - surface + entity.field_6017 - 3.0D);
/* 260 */     class_1293 jumpBoostInstance = entity.method_6112(class_1294.field_5913);
/* 261 */     if (jumpBoostInstance != null) fallHeight -= jumpBoostInstance.method_5578() + 1;
/*     */     
/* 263 */     return calculateReductions(fallHeight, (class_1297)entity, MeteorClient.mc.field_1687.method_48963().method_48827());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float calculateReductions(float damage, class_1297 entity, class_1282 damageSource) {
/* 272 */     if (damageSource.method_5514()) {
/* 273 */       switch (MeteorClient.mc.field_1687.method_8407()) { case field_5805:
/* 274 */           damage = Math.min(damage / 2.0F + 1.0F, damage); break;
/* 275 */         case field_5807: damage *= 1.5F;
/*     */           break; }
/*     */     
/*     */     }
/* 279 */     if (entity instanceof class_1309) { class_1309 livingEntity = (class_1309)entity;
/* 280 */       damage = class_1280.method_5496(livingEntity, damage, damageSource, getArmor(livingEntity), (float)livingEntity.method_45325(class_5134.field_23725));
/*     */ 
/*     */       
/* 283 */       damage = resistanceReduction(livingEntity, damage);
/*     */ 
/*     */       
/* 286 */       damage = protectionReduction(livingEntity, damage, damageSource); }
/*     */ 
/*     */     
/* 289 */     return Math.max(damage, 0.0F);
/*     */   } @FunctionalInterface
/*     */   public static interface RaycastFactory extends BiFunction<ExposureRaycastContext, class_2338, class_3965> {}
/*     */   private static float getArmor(class_1309 entity) {
/* 293 */     return (float)Math.floor(entity.method_45325(class_5134.field_23724));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float protectionReduction(class_1309 player, float damage, class_1282 source) {
/* 300 */     if (source.method_48789(class_8103.field_42242)) return damage;
/*     */     
/* 302 */     int damageProtection = 0;
/*     */     
/* 304 */     for (class_1304 slot : class_9274.field_49224) {
/* 305 */       class_1799 stack = player.method_6118(slot);
/*     */       
/* 307 */       Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap();
/* 308 */       Utils.getEnchantments(stack, (Object2IntMap)object2IntOpenHashMap);
/*     */       
/* 310 */       int protection = Utils.getEnchantmentLevel((Object2IntMap)object2IntOpenHashMap, class_1893.field_9111);
/* 311 */       if (protection > 0) {
/* 312 */         damageProtection += protection;
/*     */       }
/*     */       
/* 315 */       int fireProtection = Utils.getEnchantmentLevel((Object2IntMap)object2IntOpenHashMap, class_1893.field_9095);
/* 316 */       if (fireProtection > 0 && source.method_48789(class_8103.field_42246)) {
/* 317 */         damageProtection += 2 * fireProtection;
/*     */       }
/*     */       
/* 320 */       int blastProtection = Utils.getEnchantmentLevel((Object2IntMap)object2IntOpenHashMap, class_1893.field_9107);
/* 321 */       if (blastProtection > 0 && source.method_48789(class_8103.field_42249)) {
/* 322 */         damageProtection += 2 * blastProtection;
/*     */       }
/*     */       
/* 325 */       int projectileProtection = Utils.getEnchantmentLevel((Object2IntMap)object2IntOpenHashMap, class_1893.field_9096);
/* 326 */       if (projectileProtection > 0 && source.method_48789(class_8103.field_42247)) {
/* 327 */         damageProtection += 2 * projectileProtection;
/*     */       }
/*     */       
/* 330 */       int featherFalling = Utils.getEnchantmentLevel((Object2IntMap)object2IntOpenHashMap, class_1893.field_9129);
/* 331 */       if (featherFalling > 0 && source.method_48789(class_8103.field_42250)) {
/* 332 */         damageProtection += 3 * featherFalling;
/*     */       }
/*     */     } 
/*     */     
/* 336 */     return class_1280.method_5497(damage, damageProtection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float resistanceReduction(class_1309 player, float damage) {
/* 343 */     class_1293 resistance = player.method_6112(class_1294.field_5907);
/* 344 */     if (resistance != null) {
/* 345 */       int lvl = resistance.method_5578() + 1;
/* 346 */       damage *= 1.0F - lvl * 0.2F;
/*     */     } 
/*     */     
/* 349 */     return Math.max(damage, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float getExposure(class_243 source, class_238 box, RaycastFactory raycastFactory) {
/* 356 */     double xDiff = box.field_1320 - box.field_1323;
/* 357 */     double yDiff = box.field_1325 - box.field_1322;
/* 358 */     double zDiff = box.field_1324 - box.field_1321;
/*     */     
/* 360 */     double xStep = 1.0D / (xDiff * 2.0D + 1.0D);
/* 361 */     double yStep = 1.0D / (yDiff * 2.0D + 1.0D);
/* 362 */     double zStep = 1.0D / (zDiff * 2.0D + 1.0D);
/*     */     
/* 364 */     if (xStep > 0.0D && yStep > 0.0D && zStep > 0.0D) {
/* 365 */       int misses = 0;
/* 366 */       int hits = 0;
/*     */       
/* 368 */       double xOffset = (1.0D - Math.floor(1.0D / xStep) * xStep) * 0.5D;
/* 369 */       double zOffset = (1.0D - Math.floor(1.0D / zStep) * zStep) * 0.5D;
/*     */       
/* 371 */       xStep *= xDiff;
/* 372 */       yStep *= yDiff;
/* 373 */       zStep *= zDiff;
/*     */       
/* 375 */       double startX = box.field_1323 + xOffset;
/* 376 */       double startY = box.field_1322;
/* 377 */       double startZ = box.field_1321 + zOffset;
/* 378 */       double endX = box.field_1320 + xOffset;
/* 379 */       double endY = box.field_1325;
/* 380 */       double endZ = box.field_1324 + zOffset;
/*     */       double x;
/* 382 */       for (x = startX; x <= endX; x += xStep) {
/* 383 */         double y; for (y = startY; y <= endY; y += yStep) {
/* 384 */           double z; for (z = startZ; z <= endZ; z += zStep) {
/* 385 */             class_243 position = new class_243(x, y, z);
/*     */             
/* 387 */             if (raycast(new ExposureRaycastContext(position, source), raycastFactory) == null) misses++;
/*     */             
/* 389 */             hits++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 394 */       return misses / hits;
/*     */     } 
/*     */     
/* 397 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class_3965 raycast(ExposureRaycastContext context, RaycastFactory raycastFactory) {
/* 403 */     return (class_3965)class_1922.method_17744(context.start, context.end, context, raycastFactory, ctx -> null);
/*     */   }
/*     */   public static final class ExposureRaycastContext extends Record { private final class_243 start; private final class_243 end;
/* 406 */     public ExposureRaycastContext(class_243 start, class_243 end) { this.start = start; this.end = end; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/entity/DamageUtils$ExposureRaycastContext;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #406	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 406 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/entity/DamageUtils$ExposureRaycastContext; } public class_243 start() { return this.start; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/entity/DamageUtils$ExposureRaycastContext;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #406	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/entity/DamageUtils$ExposureRaycastContext; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/entity/DamageUtils$ExposureRaycastContext;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #406	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/entity/DamageUtils$ExposureRaycastContext;
/* 406 */       //   0	8	1	o	Ljava/lang/Object; } public class_243 end() { return this.end; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\entity\DamageUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */