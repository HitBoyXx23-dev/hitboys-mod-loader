/*     */ package meteordevelopment.meteorclient.utils;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntArrayMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntArrayMap;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntMap;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.stream.Collectors;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.mixin.ClientPlayNetworkHandlerAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.ContainerComponentAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.MinecraftClientAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.MinecraftServerAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.ProjectionMatrix2Accessor;
/*     */ import meteordevelopment.meteorclient.mixin.ReloadStateAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.ResourceReloadLoggerAccessor;
/*     */ import meteordevelopment.meteorclient.mixininterface.IMinecraftClient;
/*     */ import meteordevelopment.meteorclient.settings.StatusEffectAmplifierMapSetting;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.BetterTooltips;
/*     */ import meteordevelopment.meteorclient.systems.modules.world.Timer;
/*     */ import meteordevelopment.meteorclient.utils.misc.Names;
/*     */ import meteordevelopment.meteorclient.utils.render.PeekScreen;
/*     */ import meteordevelopment.meteorclient.utils.render.RenderUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.meteorclient.utils.world.ChunkIterator;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_10366;
/*     */ import net.minecraft.class_11278;
/*     */ import net.minecraft.class_11580;
/*     */ import net.minecraft.class_1291;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1747;
/*     */ import net.minecraft.class_1767;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1887;
/*     */ import net.minecraft.class_1890;
/*     */ import net.minecraft.class_2246;
/*     */ import net.minecraft.class_2248;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2480;
/*     */ import net.minecraft.class_2586;
/*     */ import net.minecraft.class_2591;
/*     */ import net.minecraft.class_2791;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_6360;
/*     */ import net.minecraft.class_6880;
/*     */ import net.minecraft.class_9304;
/*     */ import net.minecraft.class_9334;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.joml.Vector3d;
/*     */ import org.lwjgl.glfw.GLFW;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Utils
/*     */ {
/*  78 */   public static final Pattern FILE_NAME_INVALID_CHARS_PATTERN = Pattern.compile("[\\s\\\\/:*?\"<>|]");
/*  79 */   public static final Color WHITE = new Color(255, 255, 255);
/*     */   
/*  81 */   private static final Random random = new Random();
/*     */   
/*     */   public static boolean isReleasingTrident;
/*     */   public static boolean rendering3D = true;
/*     */   public static double frameTime;
/*     */   public static class_437 screenToOpen;
/*  87 */   private static final class_11278 matrix = new class_11278("meteor-projection-matrix", -10.0F, 100.0F, true);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PreInit
/*     */   public static void init() {
/*  94 */     MeteorClient.EVENT_BUS.subscribe(Utils.class);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private static void onTick(TickEvent.Post event) {
/*  99 */     if (screenToOpen != null && MeteorClient.mc.field_1755 == null) {
/* 100 */       MeteorClient.mc.method_1507(screenToOpen);
/* 101 */       screenToOpen = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class_243 getPlayerSpeed() {
/* 106 */     if (MeteorClient.mc.field_1724 == null) return class_243.field_1353;
/*     */     
/* 108 */     double tX = MeteorClient.mc.field_1724.method_23317() - MeteorClient.mc.field_1724.field_6014;
/* 109 */     double tY = MeteorClient.mc.field_1724.method_23318() - MeteorClient.mc.field_1724.field_6036;
/* 110 */     double tZ = MeteorClient.mc.field_1724.method_23321() - MeteorClient.mc.field_1724.field_5969;
/*     */     
/* 112 */     Timer timer = (Timer)Modules.get().get(Timer.class);
/* 113 */     if (timer.isActive()) {
/* 114 */       tX *= timer.getMultiplier();
/* 115 */       tY *= timer.getMultiplier();
/* 116 */       tZ *= timer.getMultiplier();
/*     */     } 
/*     */     
/* 119 */     tX *= 20.0D;
/* 120 */     tY *= 20.0D;
/* 121 */     tZ *= 20.0D;
/*     */     
/* 123 */     return new class_243(tX, tY, tZ);
/*     */   }
/*     */   
/*     */   public static String getWorldTime() {
/* 127 */     if (MeteorClient.mc.field_1687 == null) return "00:00";
/*     */     
/* 129 */     int ticks = (int)(MeteorClient.mc.field_1687.method_8532() % 24000L);
/* 130 */     ticks += 6000;
/* 131 */     if (ticks > 24000) ticks -= 24000;
/*     */     
/* 133 */     return String.format("%02d:%02d", new Object[] { Integer.valueOf(ticks / 1000), Integer.valueOf((int)((ticks % 1000) / 1000.0D * 60.0D)) });
/*     */   }
/*     */   
/*     */   public static Iterable<class_2791> chunks(boolean onlyWithLoadedNeighbours) {
/* 137 */     return () -> new ChunkIterator(onlyWithLoadedNeighbours);
/*     */   }
/*     */   
/*     */   public static Iterable<class_2791> chunks() {
/* 141 */     return chunks(false);
/*     */   }
/*     */   
/*     */   public static Iterable<class_2586> blockEntities() {
/* 145 */     return meteordevelopment.meteorclient.utils.world.BlockEntityIterator::new;
/*     */   }
/*     */   
/*     */   public static void getEnchantments(class_1799 itemStack, Object2IntMap<class_6880<class_1887>> enchantments) {
/* 149 */     enchantments.clear();
/*     */     
/* 151 */     if (!itemStack.method_7960()) {
/*     */ 
/*     */       
/* 154 */       Set<Object2IntMap.Entry<class_6880<class_1887>>> itemEnchantments = (itemStack.method_7909() == class_1802.field_8598) ? ((class_9304)itemStack.method_58695(class_9334.field_49643, class_9304.field_49385)).method_57539() : itemStack.method_58657().method_57539();
/*     */       
/* 156 */       for (Object2IntMap.Entry<class_6880<class_1887>> entry : itemEnchantments) {
/* 157 */         enchantments.put(entry.getKey(), entry.getIntValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static int getEnchantmentLevel(class_1799 itemStack, class_5321<class_1887> enchantment) {
/* 163 */     if (itemStack.method_7960()) return 0; 
/* 164 */     Object2IntArrayMap object2IntArrayMap = new Object2IntArrayMap();
/* 165 */     getEnchantments(itemStack, (Object2IntMap<class_6880<class_1887>>)object2IntArrayMap);
/* 166 */     return getEnchantmentLevel((Object2IntMap<class_6880<class_1887>>)object2IntArrayMap, enchantment);
/*     */   }
/*     */   
/*     */   public static int getEnchantmentLevel(Object2IntMap<class_6880<class_1887>> itemEnchantments, class_5321<class_1887> enchantment) {
/* 170 */     for (ObjectIterator<Object2IntMap.Entry<class_6880<class_1887>>> objectIterator = Object2IntMaps.fastIterable(itemEnchantments).iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<class_6880<class_1887>> entry = objectIterator.next();
/* 171 */       if (((class_6880)entry.getKey()).method_40225(enchantment)) return entry.getIntValue();  }
/*     */     
/* 173 */     return 0;
/*     */   }
/*     */   
/*     */   @SafeVarargs
/*     */   public static boolean hasEnchantments(class_1799 itemStack, class_5321<class_1887>... enchantments) {
/* 178 */     if (itemStack.method_7960()) return false; 
/* 179 */     Object2IntArrayMap object2IntArrayMap = new Object2IntArrayMap();
/* 180 */     getEnchantments(itemStack, (Object2IntMap<class_6880<class_1887>>)object2IntArrayMap);
/*     */     
/* 182 */     for (class_5321<class_1887> enchantment : enchantments) {
/* 183 */       if (!hasEnchantment((Object2IntMap<class_6880<class_1887>>)object2IntArrayMap, enchantment)) return false; 
/*     */     } 
/* 185 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean hasEnchantment(class_1799 itemStack, class_5321<class_1887> enchantmentKey) {
/* 189 */     if (itemStack.method_7960()) return false; 
/* 190 */     Object2IntArrayMap object2IntArrayMap = new Object2IntArrayMap();
/* 191 */     getEnchantments(itemStack, (Object2IntMap<class_6880<class_1887>>)object2IntArrayMap);
/* 192 */     return hasEnchantment((Object2IntMap<class_6880<class_1887>>)object2IntArrayMap, enchantmentKey);
/*     */   }
/*     */   
/*     */   private static boolean hasEnchantment(Object2IntMap<class_6880<class_1887>> itemEnchantments, class_5321<class_1887> enchantmentKey) {
/* 196 */     for (ObjectIterator<class_6880<class_1887>> objectIterator = itemEnchantments.keySet().iterator(); objectIterator.hasNext(); ) { class_6880<class_1887> enchantment = objectIterator.next();
/* 197 */       if (enchantment.method_40225(enchantmentKey)) return true;  }
/*     */     
/* 199 */     return false;
/*     */   }
/*     */   
/*     */   public static int getRenderDistance() {
/* 203 */     return Math.max(((Integer)MeteorClient.mc.field_1690.method_42503().method_41753()).intValue(), ((ClientPlayNetworkHandlerAccessor)MeteorClient.mc.method_1562()).meteor$getChunkLoadDistance());
/*     */   }
/*     */   
/*     */   public static int getWindowWidth() {
/* 207 */     return MeteorClient.mc.method_22683().method_4489();
/*     */   }
/*     */   
/*     */   public static int getWindowHeight() {
/* 211 */     return MeteorClient.mc.method_22683().method_4506();
/*     */   }
/*     */   
/*     */   public static void unscaledProjection() {
/* 215 */     float width = MeteorClient.mc.method_22683().method_4489();
/* 216 */     float height = MeteorClient.mc.method_22683().method_4506();
/*     */     
/* 218 */     RenderSystem.setProjectionMatrix(matrix.method_71092(width, height), class_10366.field_54954);
/* 219 */     RenderUtils.projection.set((Matrix4fc)((ProjectionMatrix2Accessor)matrix).meteor$callGetMatrix(width, height));
/*     */     
/* 221 */     rendering3D = false;
/*     */   }
/*     */   
/*     */   public static void scaledProjection() {
/* 225 */     float width = (MeteorClient.mc.method_22683().method_4489() / MeteorClient.mc.method_22683().method_4495());
/* 226 */     float height = (MeteorClient.mc.method_22683().method_4506() / MeteorClient.mc.method_22683().method_4495());
/*     */     
/* 228 */     RenderSystem.setProjectionMatrix(matrix.method_71092(width, height), class_10366.field_54953);
/* 229 */     RenderUtils.projection.set((Matrix4fc)((ProjectionMatrix2Accessor)matrix).meteor$callGetMatrix(width, height));
/*     */     
/* 231 */     rendering3D = true;
/*     */   }
/*     */   
/*     */   public static class_243 vec3d(class_2338 pos) {
/* 235 */     return new class_243(pos.method_10263(), pos.method_10264(), pos.method_10260());
/*     */   }
/*     */   
/*     */   public static boolean openContainer(class_1799 itemStack, class_1799[] contents, boolean pause) {
/* 239 */     if (hasItems(itemStack) || itemStack.method_7909() == class_1802.field_8466) {
/* 240 */       getItemsInContainerItem(itemStack, contents);
/* 241 */       if (pause) { screenToOpen = (class_437)new PeekScreen(itemStack, contents); }
/* 242 */       else { MeteorClient.mc.method_1507((class_437)new PeekScreen(itemStack, contents)); }
/* 243 */        return true;
/*     */     } 
/*     */     
/* 246 */     return false;
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
/*     */   public static void getItemsInContainerItem(class_1799 itemStack, class_1799[] items) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
/*     */     //   4: getstatic net/minecraft/class_1802.field_8466 : Lnet/minecraft/class_1792;
/*     */     //   7: if_acmpne -> 42
/*     */     //   10: iconst_0
/*     */     //   11: istore_2
/*     */     //   12: iload_2
/*     */     //   13: getstatic meteordevelopment/meteorclient/utils/player/EChestMemory.ITEMS : Lnet/minecraft/class_2371;
/*     */     //   16: invokevirtual size : ()I
/*     */     //   19: if_icmpge -> 41
/*     */     //   22: aload_1
/*     */     //   23: iload_2
/*     */     //   24: getstatic meteordevelopment/meteorclient/utils/player/EChestMemory.ITEMS : Lnet/minecraft/class_2371;
/*     */     //   27: iload_2
/*     */     //   28: invokevirtual get : (I)Ljava/lang/Object;
/*     */     //   31: checkcast net/minecraft/class_1799
/*     */     //   34: aastore
/*     */     //   35: iinc #2, 1
/*     */     //   38: goto -> 12
/*     */     //   41: return
/*     */     //   42: aload_1
/*     */     //   43: getstatic net/minecraft/class_1799.field_8037 : Lnet/minecraft/class_1799;
/*     */     //   46: invokestatic fill : ([Ljava/lang/Object;Ljava/lang/Object;)V
/*     */     //   49: aload_0
/*     */     //   50: invokevirtual method_57353 : ()Lnet/minecraft/class_9323;
/*     */     //   53: astore_2
/*     */     //   54: aload_2
/*     */     //   55: getstatic net/minecraft/class_9334.field_49622 : Lnet/minecraft/class_9331;
/*     */     //   58: invokeinterface method_57832 : (Lnet/minecraft/class_9331;)Z
/*     */     //   63: ifeq -> 135
/*     */     //   66: aload_2
/*     */     //   67: getstatic net/minecraft/class_9334.field_49622 : Lnet/minecraft/class_9331;
/*     */     //   70: invokeinterface method_58694 : (Lnet/minecraft/class_9331;)Ljava/lang/Object;
/*     */     //   75: checkcast meteordevelopment/meteorclient/mixin/ContainerComponentAccessor
/*     */     //   78: astore_3
/*     */     //   79: aload_3
/*     */     //   80: invokeinterface meteor$getStacks : ()Lnet/minecraft/class_2371;
/*     */     //   85: astore #4
/*     */     //   87: iconst_0
/*     */     //   88: istore #5
/*     */     //   90: iload #5
/*     */     //   92: aload #4
/*     */     //   94: invokevirtual size : ()I
/*     */     //   97: if_icmpge -> 132
/*     */     //   100: iload #5
/*     */     //   102: iflt -> 126
/*     */     //   105: iload #5
/*     */     //   107: aload_1
/*     */     //   108: arraylength
/*     */     //   109: if_icmpge -> 126
/*     */     //   112: aload_1
/*     */     //   113: iload #5
/*     */     //   115: aload #4
/*     */     //   117: iload #5
/*     */     //   119: invokevirtual get : (I)Ljava/lang/Object;
/*     */     //   122: checkcast net/minecraft/class_1799
/*     */     //   125: aastore
/*     */     //   126: iinc #5, 1
/*     */     //   129: goto -> 90
/*     */     //   132: goto -> 420
/*     */     //   135: aload_2
/*     */     //   136: getstatic net/minecraft/class_9334.field_49611 : Lnet/minecraft/class_9331;
/*     */     //   139: invokeinterface method_57832 : (Lnet/minecraft/class_9331;)Z
/*     */     //   144: ifeq -> 420
/*     */     //   147: aload_2
/*     */     //   148: getstatic net/minecraft/class_9334.field_49611 : Lnet/minecraft/class_9331;
/*     */     //   151: invokeinterface method_58694 : (Lnet/minecraft/class_9331;)Ljava/lang/Object;
/*     */     //   156: checkcast net/minecraft/class_11580
/*     */     //   159: astore_3
/*     */     //   160: aload_3
/*     */     //   161: ifnonnull -> 165
/*     */     //   164: return
/*     */     //   165: aload_3
/*     */     //   166: invokevirtual method_72540 : ()Lnet/minecraft/class_2487;
/*     */     //   169: ldc_w 'Items'
/*     */     //   172: invokevirtual method_68569 : (Ljava/lang/String;)Lnet/minecraft/class_2499;
/*     */     //   175: astore #4
/*     */     //   177: iconst_0
/*     */     //   178: istore #5
/*     */     //   180: iload #5
/*     */     //   182: aload #4
/*     */     //   184: invokevirtual size : ()I
/*     */     //   187: if_icmpge -> 420
/*     */     //   190: aload #4
/*     */     //   192: iload #5
/*     */     //   194: invokevirtual method_10602 : (I)Ljava/util/Optional;
/*     */     //   197: astore #6
/*     */     //   199: aload #6
/*     */     //   201: invokevirtual isEmpty : ()Z
/*     */     //   204: ifeq -> 210
/*     */     //   207: goto -> 414
/*     */     //   210: aload #6
/*     */     //   212: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   215: checkcast net/minecraft/class_2487
/*     */     //   218: ldc_w 'Slot'
/*     */     //   221: invokevirtual method_10571 : (Ljava/lang/String;)Ljava/util/Optional;
/*     */     //   224: astore #7
/*     */     //   226: aload #7
/*     */     //   228: invokevirtual isEmpty : ()Z
/*     */     //   231: ifeq -> 237
/*     */     //   234: goto -> 414
/*     */     //   237: aload #7
/*     */     //   239: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   242: checkcast java/lang/Byte
/*     */     //   245: invokevirtual byteValue : ()B
/*     */     //   248: iflt -> 414
/*     */     //   251: aload #7
/*     */     //   253: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   256: checkcast java/lang/Byte
/*     */     //   259: invokevirtual byteValue : ()B
/*     */     //   262: aload_1
/*     */     //   263: arraylength
/*     */     //   264: if_icmpge -> 414
/*     */     //   267: getstatic net/minecraft/class_11343.field_60354 : Lcom/mojang/serialization/Codec;
/*     */     //   270: getstatic meteordevelopment/meteorclient/MeteorClient.mc : Lnet/minecraft/class_310;
/*     */     //   273: getfield field_1724 : Lnet/minecraft/class_746;
/*     */     //   276: invokevirtual method_56673 : ()Lnet/minecraft/class_5455;
/*     */     //   279: getstatic net/minecraft/class_2509.field_11560 : Lnet/minecraft/class_2509;
/*     */     //   282: invokeinterface method_57093 : (Lcom/mojang/serialization/DynamicOps;)Lnet/minecraft/class_6903;
/*     */     //   287: aload #6
/*     */     //   289: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   292: checkcast net/minecraft/class_2520
/*     */     //   295: invokeinterface parse : (Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;
/*     */     //   300: dup
/*     */     //   301: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   304: pop
/*     */     //   305: astore #8
/*     */     //   307: iconst_0
/*     */     //   308: istore #9
/*     */     //   310: aload #8
/*     */     //   312: iload #9
/*     */     //   314: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   319: lookupswitch default -> 404, 0 -> 344, 1 -> 378
/*     */     //   344: aload #8
/*     */     //   346: checkcast com/mojang/serialization/DataResult$Success
/*     */     //   349: astore #10
/*     */     //   351: aload_1
/*     */     //   352: aload #7
/*     */     //   354: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   357: checkcast java/lang/Byte
/*     */     //   360: invokevirtual byteValue : ()B
/*     */     //   363: aload #10
/*     */     //   365: invokevirtual value : ()Ljava/lang/Object;
/*     */     //   368: checkcast net/minecraft/class_11343
/*     */     //   371: invokevirtual comp_4212 : ()Lnet/minecraft/class_1799;
/*     */     //   374: aastore
/*     */     //   375: goto -> 414
/*     */     //   378: aload #8
/*     */     //   380: checkcast com/mojang/serialization/DataResult$Error
/*     */     //   383: astore #11
/*     */     //   385: aload_1
/*     */     //   386: aload #7
/*     */     //   388: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   391: checkcast java/lang/Byte
/*     */     //   394: invokevirtual byteValue : ()B
/*     */     //   397: getstatic net/minecraft/class_1799.field_8037 : Lnet/minecraft/class_1799;
/*     */     //   400: aastore
/*     */     //   401: goto -> 414
/*     */     //   404: new java/lang/MatchException
/*     */     //   407: dup
/*     */     //   408: aconst_null
/*     */     //   409: aconst_null
/*     */     //   410: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
/*     */     //   413: athrow
/*     */     //   414: iinc #5, 1
/*     */     //   417: goto -> 180
/*     */     //   420: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #250	-> 0
/*     */     //   #251	-> 10
/*     */     //   #252	-> 22
/*     */     //   #251	-> 35
/*     */     //   #255	-> 41
/*     */     //   #258	-> 42
/*     */     //   #259	-> 49
/*     */     //   #261	-> 54
/*     */     //   #262	-> 66
/*     */     //   #263	-> 79
/*     */     //   #265	-> 87
/*     */     //   #266	-> 100
/*     */     //   #265	-> 126
/*     */     //   #268	-> 132
/*     */     //   #269	-> 135
/*     */     //   #270	-> 147
/*     */     //   #271	-> 160
/*     */     //   #272	-> 165
/*     */     //   #274	-> 177
/*     */     //   #275	-> 190
/*     */     //   #276	-> 199
/*     */     //   #278	-> 210
/*     */     //   #279	-> 226
/*     */     //   #282	-> 237
/*     */     //   #283	-> 267
/*     */     //   #284	-> 344
/*     */     //   #285	-> 378
/*     */     //   #286	-> 404
/*     */     //   #274	-> 414
/*     */     //   #291	-> 420
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   12	29	2	i	I
/*     */     //   90	42	5	i	I
/*     */     //   79	53	3	container	Lmeteordevelopment/meteorclient/mixin/ContainerComponentAccessor;
/*     */     //   87	45	4	stacks	Lnet/minecraft/class_2371;
/*     */     //   351	27	10	success	Lcom/mojang/serialization/DataResult$Success;
/*     */     //   385	19	11	ignored	Lcom/mojang/serialization/DataResult$Error;
/*     */     //   199	215	6	compound	Ljava/util/Optional;
/*     */     //   226	188	7	slot	Ljava/util/Optional;
/*     */     //   180	240	5	i	I
/*     */     //   160	260	3	blockEntityData	Lnet/minecraft/class_11580;
/*     */     //   177	243	4	nbt3	Lnet/minecraft/class_2499;
/*     */     //   0	421	0	itemStack	Lnet/minecraft/class_1799;
/*     */     //   0	421	1	items	[Lnet/minecraft/class_1799;
/*     */     //   54	367	2	components	Lnet/minecraft/class_9323;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   87	45	4	stacks	Lnet/minecraft/class_2371<Lnet/minecraft/class_1799;>;
/*     */     //   351	27	10	success	Lcom/mojang/serialization/DataResult$Success<Lnet/minecraft/class_11343;>;
/*     */     //   385	19	11	ignored	Lcom/mojang/serialization/DataResult$Error<Lnet/minecraft/class_11343;>;
/*     */     //   199	215	6	compound	Ljava/util/Optional<Lnet/minecraft/class_2487;>;
/*     */     //   226	188	7	slot	Ljava/util/Optional<Ljava/lang/Byte;>;
/*     */     //   160	260	3	blockEntityData	Lnet/minecraft/class_11580<Lnet/minecraft/class_2591<*>;>;
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
/*     */   public static Color getShulkerColor(class_1799 shulkerItem) {
/* 294 */     class_1792 class_1792 = shulkerItem.method_7909(); if (class_1792 instanceof class_1747) { class_1747 blockItem = (class_1747)class_1792;
/* 295 */       class_2248 block = blockItem.method_7711();
/* 296 */       if (block == class_2246.field_10443) return BetterTooltips.ECHEST_COLOR;
/*     */       
/* 298 */       if (block instanceof class_2480) { class_2480 shulkerBlock = (class_2480)block;
/* 299 */         class_1767 dye = shulkerBlock.method_10528();
/* 300 */         if (dye == null) return WHITE;
/*     */         
/* 302 */         int color = dye.method_7787();
/* 303 */         return new Color(color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255); }
/*     */        }
/*     */ 
/*     */     
/* 307 */     return WHITE;
/*     */   }
/*     */   
/*     */   public static boolean hasItems(class_1799 itemStack) {
/* 311 */     ContainerComponentAccessor container = (ContainerComponentAccessor)itemStack.method_58694(class_9334.field_49622);
/* 312 */     if (container != null && !container.meteor$getStacks().isEmpty()) return true;
/*     */     
/* 314 */     class_11580<class_2591<?>> blockEntityData = (class_11580<class_2591<?>>)itemStack.method_58694(class_9334.field_49611);
/* 315 */     return (blockEntityData != null && blockEntityData.method_72536("Items"));
/*     */   }
/*     */   
/*     */   public static Reference2IntMap<class_1291> createStatusEffectMap() {
/* 319 */     return (Reference2IntMap<class_1291>)new Reference2IntArrayMap(StatusEffectAmplifierMapSetting.EMPTY_STATUS_EFFECT_MAP);
/*     */   }
/*     */   
/*     */   public static String getEnchantSimpleName(class_6880<class_1887> enchantment, int length) {
/* 323 */     String name = Names.get(enchantment);
/* 324 */     return (name.length() > length) ? name.substring(0, length) : name;
/*     */   }
/*     */   
/*     */   public static boolean searchTextDefault(String text, String filter, boolean caseSensitive) {
/* 328 */     return (searchInWords(text, filter) > 0 || searchLevenshteinDefault(text, filter, caseSensitive) < text.length() / 2);
/*     */   }
/*     */   
/*     */   public static int searchLevenshteinDefault(String text, String filter, boolean caseSensitive) {
/* 332 */     return levenshteinDistance(caseSensitive ? filter : filter.toLowerCase(Locale.ROOT), caseSensitive ? text : text.toLowerCase(Locale.ROOT), 1, 8, 8);
/*     */   }
/*     */   
/*     */   public static int searchInWords(String text, String filter) {
/* 336 */     if (filter.isEmpty()) return 1;
/*     */     
/* 338 */     int wordsFound = 0;
/* 339 */     text = text.toLowerCase(Locale.ROOT);
/* 340 */     String[] words = filter.toLowerCase(Locale.ROOT).split(" ");
/*     */     
/* 342 */     for (String word : words) {
/* 343 */       if (!text.contains(word)) return 0; 
/* 344 */       wordsFound += StringUtils.countMatches(text, word);
/*     */     } 
/*     */     
/* 347 */     return wordsFound;
/*     */   }
/*     */   
/*     */   public static int levenshteinDistance(String from, String to, int insCost, int subCost, int delCost) {
/* 351 */     int textLength = from.length();
/* 352 */     int filterLength = to.length();
/*     */     
/* 354 */     if (textLength == 0) return filterLength * insCost; 
/* 355 */     if (filterLength == 0) return textLength * delCost;
/*     */ 
/*     */     
/* 358 */     int[][] d = new int[textLength + 1][filterLength + 1];
/*     */     
/* 360 */     for (int k = 0; k <= textLength; k++) {
/* 361 */       d[k][0] = k * delCost;
/*     */     }
/*     */     
/* 364 */     for (int j = 0; j <= filterLength; j++) {
/* 365 */       d[0][j] = j * insCost;
/*     */     }
/*     */ 
/*     */     
/* 369 */     for (int i = 1; i <= textLength; i++) {
/* 370 */       for (int m = 1; m <= filterLength; m++) {
/* 371 */         int sCost = d[i - 1][m - 1] + ((from.charAt(i - 1) == to.charAt(m - 1)) ? 0 : subCost);
/* 372 */         int dCost = d[i - 1][m] + delCost;
/* 373 */         int iCost = d[i][m - 1] + insCost;
/* 374 */         d[i][m] = Math.min(Math.min(dCost, iCost), sCost);
/*     */       } 
/*     */     } 
/*     */     
/* 378 */     return d[textLength][filterLength];
/*     */   }
/*     */   
/*     */   public static double squaredDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 382 */     double dX = x2 - x1;
/* 383 */     double dY = y2 - y1;
/* 384 */     double dZ = z2 - z1;
/* 385 */     return dX * dX + dY * dY + dZ * dZ;
/*     */   }
/*     */   
/*     */   public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 389 */     double dX = x2 - x1;
/* 390 */     double dY = y2 - y1;
/* 391 */     double dZ = z2 - z1;
/* 392 */     return Math.sqrt(dX * dX + dY * dY + dZ * dZ);
/*     */   }
/*     */   
/*     */   public static String getFileWorldName() {
/* 396 */     return FILE_NAME_INVALID_CHARS_PATTERN.matcher(getWorldName()).replaceAll("_");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getWorldName() {
/* 404 */     if (MeteorClient.mc.method_1542()) {
/* 405 */       if (MeteorClient.mc.field_1687 == null) return ""; 
/* 406 */       if (MeteorClient.mc.method_1576() == null) return "FAILED_BECAUSE_LEFT_WORLD";
/*     */       
/* 408 */       File folder = ((MinecraftServerAccessor)MeteorClient.mc.method_1576()).meteor$getSession().method_27424(MeteorClient.mc.field_1687.method_27983()).toFile();
/* 409 */       if (folder.toPath().relativize(MeteorClient.mc.field_1697.toPath()).getNameCount() != 2) {
/* 410 */         folder = folder.getParentFile();
/*     */       }
/* 412 */       return folder.getName();
/*     */     } 
/*     */ 
/*     */     
/* 416 */     if (MeteorClient.mc.method_1558() != null) {
/* 417 */       return MeteorClient.mc.method_1558().method_52811() ? "realms" : (MeteorClient.mc.method_1558()).field_3761;
/*     */     }
/*     */     
/* 420 */     return "";
/*     */   }
/*     */   
/*     */   public static String nameToTitle(String name) {
/* 424 */     return Arrays.<String>stream(name.split("-")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
/*     */   }
/*     */   
/*     */   public static String titleToName(String title) {
/* 428 */     return title.replace(" ", "-").toLowerCase(Locale.ROOT);
/*     */   }
/*     */   
/*     */   public static String getKeyName(int key) {
/* 432 */     switch (key) { case -1: 
/*     */       case 256: 
/*     */       case 96: 
/*     */       case 161: 
/*     */       case 162: 
/*     */       case 283: 
/*     */       case 284: 
/*     */       case 260: 
/*     */       case 261: 
/*     */       case 268: 
/*     */       case 266: 
/*     */       case 267: 
/*     */       case 269: 
/*     */       case 258: 
/*     */       case 341: 
/*     */       case 345: 
/*     */       case 342: 
/*     */       case 346: 
/*     */       case 340: 
/*     */       case 344: 
/*     */       case 265: 
/*     */       case 264: 
/*     */       case 263: 
/*     */       case 262: 
/*     */       case 39: 
/*     */       case 259: 
/*     */       case 280: 
/*     */       case 348: 
/*     */       case 343: 
/*     */       case 347: 
/*     */       case 257: 
/*     */       case 335: 
/*     */       case 282: 
/*     */       case 281: 
/*     */       case 32: 
/*     */       case 290: 
/*     */       case 291: 
/*     */       case 292: 
/*     */       case 293: 
/*     */       case 294: 
/*     */       case 295: 
/*     */       case 296: 
/*     */       case 297: 
/*     */       case 298: 
/*     */       case 299: 
/*     */       case 300: 
/*     */       case 301: 
/*     */       case 302: 
/*     */       case 303: 
/*     */       case 304: 
/*     */       case 305: 
/*     */       case 306: 
/*     */       case 307: 
/*     */       case 308: 
/*     */       case 309: 
/*     */       case 310: 
/*     */       case 311: 
/*     */       case 312:
/*     */       
/*     */       case 313:
/*     */       
/*     */       case 314:
/* 494 */        }  String keyName = GLFW.glfwGetKeyName(key, 0);
/* 495 */     return (keyName == null) ? "Unknown" : StringUtils.capitalize(keyName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getButtonName(int button) {
/* 501 */     switch (button) { case -1: 
/*     */       case 0: 
/*     */       case 1:
/*     */       
/*     */       case 2:
/* 506 */        }  return "Mouse " + button;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] readBytes(InputStream in) {
/*     */     try {
/* 512 */       return in.readAllBytes();
/* 513 */     } catch (IOException e) {
/* 514 */       MeteorClient.LOG.error("Error reading from stream.", e);
/* 515 */       return new byte[0];
/*     */     } finally {
/* 517 */       IOUtils.closeQuietly(in);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean canUpdate() {
/* 522 */     return (MeteorClient.mc != null && MeteorClient.mc.field_1687 != null && MeteorClient.mc.field_1724 != null);
/*     */   }
/*     */   
/*     */   public static boolean canOpenGui() {
/* 526 */     if (canUpdate()) return (MeteorClient.mc.field_1755 == null);
/*     */     
/* 528 */     return (MeteorClient.mc.field_1755 instanceof net.minecraft.class_442 || MeteorClient.mc.field_1755 instanceof net.minecraft.class_500 || MeteorClient.mc.field_1755 instanceof net.minecraft.class_526);
/*     */   }
/*     */   
/*     */   public static boolean canCloseGui() {
/* 532 */     return MeteorClient.mc.field_1755 instanceof meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*     */   }
/*     */   
/*     */   public static int random(int min, int max) {
/* 536 */     return random.nextInt(max - min) + min;
/*     */   }
/*     */   
/*     */   public static double random(double min, double max) {
/* 540 */     return min + (max - min) * random.nextDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void leftClick() {
/* 547 */     int attackCooldown = ((MinecraftClientAccessor)MeteorClient.mc).meteor$getAttackCooldown();
/* 548 */     if (attackCooldown == 10000) {
/* 549 */       ((MinecraftClientAccessor)MeteorClient.mc).meteor$setAttackCooldown(0);
/*     */     }
/*     */     
/* 552 */     MeteorClient.mc.field_1690.field_1886.method_23481(true);
/* 553 */     ((MinecraftClientAccessor)MeteorClient.mc).meteor$leftClick();
/* 554 */     MeteorClient.mc.field_1690.field_1886.method_23481(false);
/*     */   }
/*     */   
/*     */   public static void rightClick() {
/* 558 */     ((IMinecraftClient)MeteorClient.mc).meteor$rightClick();
/*     */   }
/*     */   
/*     */   public static boolean isShulker(class_1792 item) {
/* 562 */     return (item == class_1802.field_8545 || item == class_1802.field_8722 || item == class_1802.field_8380 || item == class_1802.field_8050 || item == class_1802.field_8829 || item == class_1802.field_8271 || item == class_1802.field_8548 || item == class_1802.field_8520 || item == class_1802.field_8627 || item == class_1802.field_8451 || item == class_1802.field_8213 || item == class_1802.field_8816 || item == class_1802.field_8350 || item == class_1802.field_8584 || item == class_1802.field_8461 || item == class_1802.field_8676 || item == class_1802.field_8268);
/*     */   }
/*     */   
/*     */   public static boolean isThrowable(class_1792 item) {
/* 566 */     return (item instanceof net.minecraft.class_1779 || item instanceof net.minecraft.class_1753 || item instanceof net.minecraft.class_1764 || item instanceof net.minecraft.class_1823 || item instanceof net.minecraft.class_1771 || item instanceof net.minecraft.class_1776 || item instanceof net.minecraft.class_1828 || item instanceof net.minecraft.class_1803 || item instanceof net.minecraft.class_1787 || item instanceof net.minecraft.class_1835);
/*     */   }
/*     */   
/*     */   public static void addEnchantment(class_1799 itemStack, class_6880<class_1887> enchantment, int level) {
/* 570 */     class_9304.class_9305 b = new class_9304.class_9305(class_1890.method_57532(itemStack));
/* 571 */     b.method_57550(enchantment, level);
/*     */     
/* 573 */     class_1890.method_57530(itemStack, b.method_57549());
/*     */   }
/*     */   
/*     */   public static void clearEnchantments(class_1799 itemStack) {
/* 577 */     class_1890.method_57531(itemStack, components -> components.method_57548(()));
/*     */   }
/*     */   
/*     */   public static void removeEnchantment(class_1799 itemStack, class_1887 enchantment) {
/* 581 */     class_1890.method_57531(itemStack, components -> components.method_57548(()));
/*     */   }
/*     */   
/*     */   public static Color lerp(Color first, Color second, float v) {
/* 585 */     return new Color((int)(first.r * (1.0F - v) + second.r * v), (int)(first.g * (1.0F - v) + second.g * v), (int)(first.b * (1.0F - v) + second.b * v));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isLoading() {
/* 593 */     class_6360.class_6363 state = ((ResourceReloadLoggerAccessor)((MinecraftClientAccessor)MeteorClient.mc).meteor$getResourceReloadLogger()).meteor$getReloadState();
/* 594 */     return (state == null || !((ReloadStateAccessor)state).meteor$isFinished());
/*     */   }
/*     */   public static int parsePort(String full) {
/*     */     int port;
/* 598 */     if (full == null || full.isBlank() || !full.contains(":")) return -1;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 603 */       port = Integer.parseInt(full.substring(full.lastIndexOf(':') + 1, full.length() - 1));
/* 604 */     } catch (NumberFormatException ignored) {
/* 605 */       port = -1;
/*     */     } 
/*     */     
/* 608 */     return port;
/*     */   }
/*     */   
/*     */   public static String parseAddress(String full) {
/* 612 */     if (full == null || full.isBlank() || !full.contains(":")) return full; 
/* 613 */     return full.substring(0, full.lastIndexOf(':'));
/*     */   }
/*     */   
/*     */   public static boolean resolveAddress(String address) {
/* 617 */     if (address == null || address.isBlank()) return false;
/*     */     
/* 619 */     int port = parsePort(address);
/* 620 */     if (port == -1) { port = 25565; }
/* 621 */     else { address = parseAddress(address); }
/*     */     
/* 623 */     return resolveAddress(address, port);
/*     */   }
/*     */   
/*     */   public static boolean resolveAddress(String address, int port) {
/* 627 */     if (port <= 0 || port > 65535 || address == null || address.isBlank()) return false; 
/* 628 */     InetSocketAddress socketAddress = new InetSocketAddress(address, port);
/* 629 */     return !socketAddress.isUnresolved();
/*     */   }
/*     */   
/*     */   public static Vector3d set(Vector3d vec, class_243 v) {
/* 633 */     vec.x = v.field_1352;
/* 634 */     vec.y = v.field_1351;
/* 635 */     vec.z = v.field_1350;
/*     */     
/* 637 */     return vec;
/*     */   }
/*     */   
/*     */   public static Vector3d set(Vector3d vec, class_1297 entity, double tickDelta) {
/* 641 */     vec.x = class_3532.method_16436(tickDelta, entity.field_6038, entity.method_23317());
/* 642 */     vec.y = class_3532.method_16436(tickDelta, entity.field_5971, entity.method_23318());
/* 643 */     vec.z = class_3532.method_16436(tickDelta, entity.field_5989, entity.method_23321());
/*     */     
/* 645 */     return vec;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean nameFilter(String text, char character) {
/* 651 */     return ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z') || (character >= '0' && character <= '9') || character == '_' || character == '-' || character == '.' || character == ' ');
/*     */   }
/*     */   
/*     */   public static boolean ipFilter(String text, char character) {
/* 655 */     if (text.contains(":") && character == ':') return false; 
/* 656 */     return ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z') || (character >= '0' && character <= '9') || character == '.' || character == '-' || character == ':');
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\Utils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */