/*     */ package meteordevelopment.meteorclient.utils.entity;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
/*     */ import it.unimi.dsi.fastutil.longs.LongSortedSet;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.function.Predicate;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.mixin.DirectionAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.EntityTrackingSectionAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.SectionedEntityCacheAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.SimpleEntityLookupAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.WorldAccessor;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1934;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_2382;
/*     */ import net.minecraft.class_2586;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_3611;
/*     */ import net.minecraft.class_3612;
/*     */ import net.minecraft.class_4076;
/*     */ import net.minecraft.class_4970;
/*     */ import net.minecraft.class_5572;
/*     */ import net.minecraft.class_5573;
/*     */ import net.minecraft.class_5577;
/*     */ import net.minecraft.class_5578;
/*     */ import net.minecraft.class_640;
/*     */ 
/*     */ 
/*     */ public class EntityUtils
/*     */ {
/*  42 */   private static final class_2338.class_2339 testPos = new class_2338.class_2339();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAttackable(class_1299<?> type) {
/*  48 */     return (type != class_1299.field_6083 && type != class_1299.field_6122 && type != class_1299.field_6089 && type != class_1299.field_6133 && type != class_1299.field_6052 && type != class_1299.field_6124 && type != class_1299.field_6135 && type != class_1299.field_6082 && type != class_1299.field_6064 && type != class_1299.field_56254 && type != class_1299.field_56255 && type != class_1299.field_6127 && type != class_1299.field_6112 && type != class_1299.field_6103 && type != class_1299.field_6044 && type != class_1299.field_6144);
/*     */   }
/*     */   
/*     */   public static boolean isRideable(class_1299<?> type) {
/*  52 */     return (type == class_1299.field_6093 || type == class_1299.field_23214 || type == class_1299.field_6139 || type == class_1299.field_6067 || type == class_1299.field_6057 || type == class_1299.field_6075 || type == class_1299.field_6048 || type == class_1299.field_6074 || type == class_1299.field_17714 || type == class_1299.field_40116 || type == class_1299.field_64132 || type == class_1299.field_6096 || type == class_1299.field_54410 || type == class_1299.field_54416 || type == class_1299.field_54420 || type == class_1299.field_54412 || type == class_1299.field_54408 || type == class_1299.field_54422 || type == class_1299.field_54406 || type == class_1299.field_54562 || type == class_1299.field_54414 || type == class_1299.field_54419 || type == class_1299.field_54415 || type == class_1299.field_54421 || type == class_1299.field_54423 || type == class_1299.field_54407 || type == class_1299.field_54413 || type == class_1299.field_54409 || type == class_1299.field_54411 || type == class_1299.field_54563 || type == class_1299.field_54417 || type == class_1299.field_54418 || type == class_1299.field_63289 || type == class_1299.field_63290 || type == class_1299.field_59668);
/*     */   }
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
/*     */   
/*     */   public static float getTotalHealth(class_1309 target) {
/*  90 */     return target.method_6032() + target.method_6067();
/*     */   }
/*     */   
/*     */   public static int getPing(class_1657 player) {
/*  94 */     if (MeteorClient.mc.method_1562() == null) return 0;
/*     */     
/*  96 */     class_640 playerListEntry = MeteorClient.mc.method_1562().method_2871(player.method_5667());
/*  97 */     if (playerListEntry == null) return 0; 
/*  98 */     return playerListEntry.method_2959();
/*     */   }
/*     */   
/*     */   public static class_1934 getGameMode(class_1657 player) {
/* 102 */     if (player == null) return null; 
/* 103 */     class_640 playerListEntry = MeteorClient.mc.method_1562().method_2871(player.method_5667());
/* 104 */     if (playerListEntry == null) return null; 
/* 105 */     return playerListEntry.method_2958();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isAboveWater(class_1297 entity) {
/* 110 */     class_2338.class_2339 blockPos = entity.method_24515().method_25503();
/* 111 */     int bottom = MeteorClient.mc.field_1687.method_31607();
/*     */     
/* 113 */     while (blockPos.method_10264() > bottom) {
/* 114 */       class_2680 state = MeteorClient.mc.field_1687.method_8320((class_2338)blockPos);
/*     */       
/* 116 */       if (state.method_51366())
/*     */         break; 
/* 118 */       class_3611 fluid = state.method_26227().method_15772();
/* 119 */       if (fluid == class_3612.field_15910 || fluid == class_3612.field_15909) {
/* 120 */         return true;
/*     */       }
/*     */       
/* 123 */       blockPos.method_10100(0, -1, 0);
/*     */     } 
/*     */     
/* 126 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean isInCobweb(class_1297 entity) {
/* 130 */     return MeteorClient.mc.field_1687.method_29556(entity.method_5829()).anyMatch(state -> state.method_27852(class_2246.field_10343));
/*     */   }
/*     */   
/*     */   public static boolean isInRenderDistance(class_1297 entity) {
/* 134 */     if (entity == null) return false; 
/* 135 */     return isInRenderDistance(entity.method_23317(), entity.method_23321());
/*     */   }
/*     */   
/*     */   public static boolean isInRenderDistance(class_2586 entity) {
/* 139 */     if (entity == null) return false; 
/* 140 */     return isInRenderDistance(entity.method_11016().method_10263(), entity.method_11016().method_10260());
/*     */   }
/*     */   
/*     */   public static boolean isInRenderDistance(class_2338 pos) {
/* 144 */     if (pos == null) return false; 
/* 145 */     return isInRenderDistance(pos.method_10263(), pos.method_10260());
/*     */   }
/*     */   
/*     */   public static boolean isInRenderDistance(double posX, double posZ) {
/* 149 */     double x = Math.abs((MeteorClient.mc.field_1773.method_19418().method_71156()).field_1352 - posX);
/* 150 */     double z = Math.abs((MeteorClient.mc.field_1773.method_19418().method_71156()).field_1350 - posZ);
/* 151 */     double d = ((((Integer)MeteorClient.mc.field_1690.method_42503().method_41753()).intValue() + 1) * 16);
/*     */     
/* 153 */     return (x < d && z < d);
/*     */   }
/*     */   
/*     */   public static class_2338 getCityBlock(class_1657 player) {
/* 157 */     if (player == null) return null;
/*     */     
/* 159 */     double bestDistanceSquared = 36.0D;
/* 160 */     class_2350 bestDirection = null;
/*     */     
/* 162 */     for (class_2350 direction : DirectionAccessor.meteor$getHorizontal()) {
/* 163 */       testPos.method_10101((class_2382)player.method_24515().method_10093(direction));
/*     */       
/* 165 */       class_2248 block = MeteorClient.mc.field_1687.method_8320((class_2338)testPos).method_26204();
/* 166 */       if (block == class_2246.field_10540 || block == class_2246.field_22108 || block == class_2246.field_22423 || block == class_2246.field_23152 || block == class_2246.field_22109) {
/*     */ 
/*     */         
/* 169 */         double testDistanceSquared = PlayerUtils.squaredDistanceTo((class_2338)testPos);
/* 170 */         if (testDistanceSquared < bestDistanceSquared) {
/* 171 */           bestDistanceSquared = testDistanceSquared;
/* 172 */           bestDirection = direction;
/*     */         } 
/*     */       } 
/*     */     } 
/* 176 */     if (bestDirection == null) return null; 
/* 177 */     return player.method_24515().method_10093(bestDirection);
/*     */   }
/*     */   
/*     */   public static String getName(class_1297 entity) {
/* 181 */     if (entity == null) return null; 
/* 182 */     if (entity instanceof class_1657) return entity.method_5477().getString(); 
/* 183 */     return entity.method_5864().method_5897().getString();
/*     */   }
/*     */   
/*     */   public static Color getColorFromDistance(class_1297 entity) {
/*     */     int r, g;
/* 188 */     Color distanceColor = new Color(255, 255, 255);
/* 189 */     double distance = PlayerUtils.distanceToCamera(entity);
/* 190 */     double percent = distance / 60.0D;
/*     */     
/* 192 */     if (percent < 0.0D || percent > 1.0D) {
/* 193 */       distanceColor.set(0, 255, 0, 255);
/* 194 */       return distanceColor;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 199 */     if (percent < 0.5D) {
/* 200 */       r = 255;
/* 201 */       g = (int)(255.0D * percent / 0.5D);
/*     */     } else {
/* 203 */       g = 255;
/* 204 */       r = 255 - (int)(255.0D * (percent - 0.5D) / 0.5D);
/*     */     } 
/*     */     
/* 207 */     distanceColor.set(r, g, 0, 255);
/* 208 */     return distanceColor;
/*     */   }
/*     */   public static Color getColorFromHealth(class_1297 entity, Color nonLivingEntityColor) {
/*     */     class_1309 living;
/*     */     int r, g;
/* 213 */     if (entity instanceof class_1309) { living = (class_1309)entity; }
/* 214 */     else { return new Color(nonLivingEntityColor); }
/*     */ 
/*     */     
/* 217 */     float health = living.method_6032();
/* 218 */     float maxHealth = living.method_6063();
/*     */     
/* 220 */     if (maxHealth <= 0.0F) {
/* 221 */       return new Color(nonLivingEntityColor);
/*     */     }
/*     */     
/* 224 */     double percent = (health / maxHealth);
/*     */     
/* 226 */     percent = Math.max(0.0D, Math.min(1.0D, percent));
/*     */ 
/*     */ 
/*     */     
/* 230 */     if (percent < 0.5D) {
/*     */       
/* 232 */       r = 255;
/* 233 */       g = (int)(255.0D * percent / 0.5D);
/*     */     } else {
/*     */       
/* 236 */       g = 255;
/* 237 */       r = 255 - (int)(255.0D * (percent - 0.5D) / 0.5D);
/*     */     } 
/*     */     
/* 240 */     return new Color(r, g, 0, 255);
/*     */   }
/*     */   
/*     */   public static boolean intersectsWithEntity(class_238 box, Predicate<class_1297> predicate) {
/* 244 */     class_5577<class_1297> entityLookup = ((WorldAccessor)MeteorClient.mc.field_1687).meteor$getEntityLookup();
/*     */ 
/*     */     
/* 247 */     if (entityLookup instanceof class_5578) { class_5578<class_1297> simpleEntityLookup = (class_5578<class_1297>)entityLookup;
/* 248 */       class_5573<class_1297> cache = ((SimpleEntityLookupAccessor)simpleEntityLookup).meteor$getCache();
/* 249 */       LongSortedSet trackedPositions = ((SectionedEntityCacheAccessor)cache).meteor$getTrackedPositions();
/* 250 */       Long2ObjectMap<class_5572<class_1297>> trackingSections = ((SectionedEntityCacheAccessor)cache).meteor$getTrackingSections();
/*     */       
/* 252 */       int i = class_4076.method_32204(box.field_1323 - 2.0D);
/* 253 */       int j = class_4076.method_32204(box.field_1322 - 2.0D);
/* 254 */       int k = class_4076.method_32204(box.field_1321 - 2.0D);
/* 255 */       int l = class_4076.method_32204(box.field_1320 + 2.0D);
/* 256 */       int m = class_4076.method_32204(box.field_1325 + 2.0D);
/* 257 */       int n = class_4076.method_32204(box.field_1324 + 2.0D);
/*     */       
/* 259 */       for (int o = i; o <= l; o++) {
/* 260 */         long p = class_4076.method_18685(o, 0, 0);
/* 261 */         long q = class_4076.method_18685(o, -1, -1);
/* 262 */         LongBidirectionalIterator longIterator = trackedPositions.subSet(p, q + 1L).iterator();
/*     */         
/* 264 */         while (longIterator.hasNext()) {
/* 265 */           long r = longIterator.nextLong();
/* 266 */           int s = class_4076.method_18689(r);
/* 267 */           int t = class_4076.method_18690(r);
/*     */           
/* 269 */           if (s >= j && s <= m && t >= k && t <= n) {
/* 270 */             class_5572<class_1297> entityTrackingSection = (class_5572<class_1297>)trackingSections.get(r);
/*     */             
/* 272 */             if (entityTrackingSection != null && entityTrackingSection.method_31768().method_31885()) {
/* 273 */               for (class_1297 entity : ((EntityTrackingSectionAccessor)entityTrackingSection).meteor$getCollection()) {
/* 274 */                 if (entity.method_5829().method_994(box) && predicate.test(entity)) return true;
/*     */               
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 281 */       return false; }
/*     */ 
/*     */ 
/*     */     
/* 285 */     AtomicBoolean found = new AtomicBoolean(false);
/*     */     
/* 287 */     entityLookup.method_31807(box, entity -> {
/*     */           if (!found.get() && predicate.test(entity))
/*     */             found.set(true); 
/*     */         });
/* 291 */     return found.get();
/*     */   }
/*     */   
/*     */   public static class_1299<?> getGroup(class_1297 entity) {
/* 295 */     return entity.method_5864();
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isOnAir(class_1297 entity) {
/* 300 */     return entity.method_73183().method_29546(entity.method_5829().method_1014(0.0625D).method_1012(0.0D, -0.55D, 0.0D)).allMatch(class_4970.class_4971::method_26215);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\entity\EntityUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */