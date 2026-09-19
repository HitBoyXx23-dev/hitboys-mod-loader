/*     */ package meteordevelopment.meteorclient.utils.misc;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.WeakHashMap;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.game.ResourcePacksReloadedEvent;
/*     */ import meteordevelopment.meteorclient.utils.PreInit;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_1146;
/*     */ import net.minecraft.class_1291;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1887;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2396;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_3544;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_5455;
/*     */ import net.minecraft.class_634;
/*     */ import net.minecraft.class_6880;
/*     */ import net.minecraft.class_7923;
/*     */ import net.minecraft.class_7924;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Names
/*     */ {
/*  42 */   private static final Map<class_1291, String> statusEffectNames = (Map<class_1291, String>)new Reference2ObjectOpenHashMap(16);
/*  43 */   private static final Map<class_1792, String> itemNames = (Map<class_1792, String>)new Reference2ObjectOpenHashMap(128);
/*  44 */   private static final Map<class_2248, String> blockNames = (Map<class_2248, String>)new Reference2ObjectOpenHashMap(128);
/*  45 */   private static final Map<class_5321<class_1887>, String> enchantmentKeyNames = new WeakHashMap<>(16);
/*  46 */   private static final Map<class_6880<class_1887>, String> enchantmentEntryNames = (Map<class_6880<class_1887>, String>)new Reference2ObjectOpenHashMap(16);
/*  47 */   private static final Map<class_1299<?>, String> entityTypeNames = (Map<class_1299<?>, String>)new Reference2ObjectOpenHashMap(64);
/*  48 */   private static final Map<class_2396<?>, String> particleTypesNames = (Map<class_2396<?>, String>)new Reference2ObjectOpenHashMap(64);
/*  49 */   private static final Map<class_2960, String> soundNames = new HashMap<>(64);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PreInit
/*     */   public static void init() {
/*  56 */     MeteorClient.EVENT_BUS.subscribe(Names.class);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private static void onResourcePacksReloaded(ResourcePacksReloadedEvent event) {
/*  61 */     statusEffectNames.clear();
/*  62 */     itemNames.clear();
/*  63 */     blockNames.clear();
/*  64 */     enchantmentEntryNames.clear();
/*  65 */     entityTypeNames.clear();
/*  66 */     particleTypesNames.clear();
/*  67 */     soundNames.clear();
/*     */   }
/*     */   
/*     */   public static String get(class_1291 effect) {
/*  71 */     return statusEffectNames.computeIfAbsent(effect, effect1 -> class_3544.method_15440(class_1074.method_4662(effect1.method_5567(), new Object[0])));
/*     */   }
/*     */   
/*     */   public static String get(class_1792 item) {
/*  75 */     return itemNames.computeIfAbsent(item, item1 -> class_3544.method_15440(class_1074.method_4662(item1.method_7876(), new Object[0])));
/*     */   }
/*     */   
/*     */   public static String get(class_2248 block) {
/*  79 */     return blockNames.computeIfAbsent(block, block1 -> class_3544.method_15440(class_1074.method_4662(block1.method_63499(), new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String get(class_5321<class_1887> enchantment) {
/*  88 */     return enchantmentKeyNames.computeIfAbsent(enchantment, enchantment1 -> (String)Optional.<class_634>ofNullable(class_310.method_1551().method_1562()).map(class_634::method_29091).flatMap(()).flatMap(()).map(Names::get).orElseGet(()));
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
/*     */   public static String get(class_6880<class_1887> enchantment) {
/* 101 */     return enchantmentEntryNames.computeIfAbsent(enchantment, enchantment1 -> class_3544.method_15440(((class_1887)enchantment.comp_349()).comp_2686().getString()));
/*     */   }
/*     */   
/*     */   public static String get(class_1299<?> entityType) {
/* 105 */     return entityTypeNames.computeIfAbsent(entityType, entityType1 -> class_3544.method_15440(class_1074.method_4662(entityType1.method_5882(), new Object[0])));
/*     */   }
/*     */   
/*     */   public static String get(class_2396<?> type) {
/* 109 */     if (!(type instanceof net.minecraft.class_2394)) return ""; 
/* 110 */     return particleTypesNames.computeIfAbsent(type, effect1 -> StringUtils.capitalize(class_7923.field_41180.method_10221(type).method_12832().replace("_", " ")));
/*     */   }
/*     */   
/*     */   public static String getSoundName(class_2960 id) {
/* 114 */     return soundNames.computeIfAbsent(id, identifier -> {
/*     */           class_1146 soundSet = MeteorClient.mc.method_1483().method_4869(identifier);
/*     */           if (soundSet == null) {
/*     */             return identifier.method_12832();
/*     */           }
/*     */           class_2561 text = soundSet.method_4886();
/*     */           return (text == null) ? identifier.method_12832() : class_3544.method_15440(text.getString());
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String get(class_1799 stack) {
/* 126 */     return stack.method_7964().getString();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\Names.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */