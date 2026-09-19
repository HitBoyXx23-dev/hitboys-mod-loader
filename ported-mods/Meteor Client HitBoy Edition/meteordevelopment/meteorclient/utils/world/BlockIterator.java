/*     */ package meteordevelopment.meteorclient.utils.world;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Supplier;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.utils.PreInit;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.Pool;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2680;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlockIterator
/*     */ {
/*  25 */   private static final Pool<Callback> callbackPool = new Pool(Callback::new);
/*  26 */   private static final List<Callback> callbacks = (List<Callback>)new ReferenceArrayList();
/*     */   
/*  28 */   private static final List<Runnable> afterCallbacks = (List<Runnable>)new ReferenceArrayList();
/*     */   
/*  30 */   private static final class_2338.class_2339 blockPos = new class_2338.class_2339();
/*     */   
/*     */   private static int hRadius;
/*     */   
/*     */   private static int vRadius;
/*     */   
/*     */   private static boolean disableCurrent;
/*     */   
/*     */   @PreInit
/*     */   public static void init() {
/*  40 */     MeteorClient.EVENT_BUS.subscribe(BlockIterator.class);
/*     */   }
/*     */   
/*     */   @EventHandler(priority = -201)
/*     */   private static void onTick(TickEvent.Pre event) {
/*  45 */     if (!Utils.canUpdate())
/*     */       return; 
/*  47 */     int px = MeteorClient.mc.field_1724.method_31477();
/*  48 */     int py = MeteorClient.mc.field_1724.method_31478();
/*  49 */     int pz = MeteorClient.mc.field_1724.method_31479();
/*     */     
/*  51 */     for (int x = px - hRadius; x <= px + hRadius; x++) {
/*  52 */       for (int z = pz - hRadius; z <= pz + hRadius; z++) {
/*  53 */         for (int y = Math.max(MeteorClient.mc.field_1687.method_31607(), py - vRadius); y <= py + vRadius && 
/*  54 */           y <= MeteorClient.mc.field_1687.method_31605(); y++) {
/*     */           
/*  56 */           blockPos.method_10103(x, y, z);
/*  57 */           class_2680 blockState = MeteorClient.mc.field_1687.method_8320((class_2338)blockPos);
/*     */           
/*  59 */           int dx = Math.abs(x - px);
/*  60 */           int dy = Math.abs(y - py);
/*  61 */           int dz = Math.abs(z - pz);
/*     */           
/*  63 */           callbacks.removeIf(callback -> {
/*     */                 if (dx <= callback.hRadius && dy <= callback.vRadius && dz <= callback.hRadius) {
/*     */                   disableCurrent = false;
/*     */                   
/*     */                   callback.function.accept(blockPos, blockState);
/*     */                   return disableCurrent;
/*     */                 } 
/*     */                 return false;
/*     */               });
/*     */         } 
/*     */       } 
/*     */     } 
/*  75 */     hRadius = 0;
/*  76 */     vRadius = 0;
/*     */     
/*  78 */     callbackPool.freeAll(callbacks);
/*  79 */     callbacks.clear();
/*     */     
/*  81 */     for (Runnable callback : afterCallbacks) callback.run(); 
/*  82 */     afterCallbacks.clear();
/*     */   }
/*     */   
/*     */   public static void register(int horizontalRadius, int verticalRadius, BiConsumer<class_2338, class_2680> function) {
/*  86 */     hRadius = Math.max(hRadius, horizontalRadius);
/*  87 */     vRadius = Math.max(vRadius, verticalRadius);
/*     */     
/*  89 */     Callback callback = (Callback)callbackPool.get();
/*     */     
/*  91 */     callback.function = function;
/*  92 */     callback.hRadius = horizontalRadius;
/*  93 */     callback.vRadius = verticalRadius;
/*     */     
/*  95 */     callbacks.add(callback);
/*     */   }
/*     */   
/*     */   public static void disableCurrent() {
/*  99 */     disableCurrent = true;
/*     */   }
/*     */   
/*     */   public static void after(Runnable callback) {
/* 103 */     afterCallbacks.add(callback);
/*     */   }
/*     */   
/*     */   private static class Callback {
/*     */     public BiConsumer<class_2338, class_2680> function;
/*     */     public int hRadius;
/*     */     public int vRadius;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\world\BlockIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */