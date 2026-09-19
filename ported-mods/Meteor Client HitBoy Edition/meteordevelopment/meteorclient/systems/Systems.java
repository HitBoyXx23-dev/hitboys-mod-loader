/*     */ package meteordevelopment.meteorclient.systems;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.game.GameLeftEvent;
/*     */ import meteordevelopment.meteorclient.systems.accounts.Accounts;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*     */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*     */ import meteordevelopment.meteorclient.systems.macros.Macros;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.profiles.Profiles;
/*     */ import meteordevelopment.meteorclient.systems.proxies.Proxies;
/*     */ import meteordevelopment.meteorclient.systems.waypoints.Waypoints;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Systems
/*     */ {
/*  29 */   private static final Map<Class<? extends System>, System<?>> systems = (Map<Class<? extends System>, System<?>>)new Reference2ReferenceOpenHashMap();
/*  30 */   private static final List<Runnable> preLoadTasks = new ArrayList<>(1);
/*     */   
/*     */   public static void addPreLoadTask(Runnable task) {
/*  33 */     preLoadTasks.add(task);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void init() {
/*  38 */     add((System<?>)new Modules());
/*     */     
/*  40 */     Config config = new Config();
/*  41 */     System<?> configSystem = add((System<?>)config);
/*  42 */     configSystem.init();
/*  43 */     configSystem.load();
/*     */ 
/*     */     
/*  46 */     config.settings.registerColorSettings(null);
/*     */     
/*  48 */     add((System<?>)new Macros());
/*  49 */     add((System<?>)new Friends());
/*  50 */     add((System<?>)new Accounts());
/*  51 */     add((System<?>)new Waypoints());
/*  52 */     add((System<?>)new Profiles());
/*  53 */     add((System<?>)new Proxies());
/*  54 */     add((System<?>)new Hud());
/*     */     
/*  56 */     MeteorClient.EVENT_BUS.subscribe(Systems.class);
/*     */   }
/*     */   
/*     */   public static System<?> add(System<?> system) {
/*  60 */     systems.put(system.getClass(), system);
/*  61 */     MeteorClient.EVENT_BUS.subscribe(system);
/*  62 */     system.init();
/*     */     
/*  64 */     return system;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private static void onGameLeft(GameLeftEvent event) {
/*  71 */     save();
/*     */   }
/*     */   
/*     */   public static void save(File folder) {
/*  75 */     long start = java.lang.System.currentTimeMillis();
/*  76 */     MeteorClient.LOG.info("Saving");
/*     */     
/*  78 */     for (System<?> system : systems.values()) system.save(folder);
/*     */     
/*  80 */     MeteorClient.LOG.info("Saved in {} milliseconds.", Long.valueOf(java.lang.System.currentTimeMillis() - start));
/*     */   }
/*     */   
/*     */   public static void save() {
/*  84 */     save(null);
/*     */   }
/*     */   
/*     */   public static void load(File folder) {
/*  88 */     long start = java.lang.System.currentTimeMillis();
/*  89 */     MeteorClient.LOG.info("Loading");
/*     */     
/*  91 */     for (Runnable task : preLoadTasks) task.run(); 
/*  92 */     for (System<?> system : systems.values()) system.load(folder);
/*     */     
/*  94 */     MeteorClient.LOG.info("Loaded in {} milliseconds", Long.valueOf(java.lang.System.currentTimeMillis() - start));
/*     */   }
/*     */   
/*     */   public static void load() {
/*  98 */     load(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T extends System<?>> T get(Class<T> klass) {
/* 103 */     return (T)systems.get(klass);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\Systems.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */