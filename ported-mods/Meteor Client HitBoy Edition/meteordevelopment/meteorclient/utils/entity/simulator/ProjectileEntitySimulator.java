/*     */ package meteordevelopment.meteorclient.utils.entity.simulator;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.NoSlow;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Sneak;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1675;
/*     */ import net.minecraft.class_1676;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_238;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_3486;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_3610;
/*     */ import net.minecraft.class_3959;
/*     */ import net.minecraft.class_3965;
/*     */ import net.minecraft.class_3966;
/*     */ import net.minecraft.class_4048;
/*     */ import net.minecraft.class_4050;
/*     */ import net.minecraft.class_4076;
/*     */ import net.minecraft.class_9109;
/*     */ import org.joml.Quaterniond;
/*     */ import org.joml.Quaterniondc;
/*     */ import org.joml.Vector3d;
/*     */ import org.joml.Vector3dc;
/*     */ 
/*     */ public class ProjectileEntitySimulator
/*     */ {
/*  41 */   private final class_2338.class_2339 blockPos = new class_2338.class_2339();
/*     */   
/*  43 */   private final class_243 pos3d = new class_243(0.0D, 0.0D, 0.0D);
/*  44 */   private final class_243 prevPos3d = new class_243(0.0D, 0.0D, 0.0D);
/*     */   
/*  46 */   public final Vector3d pos = new Vector3d();
/*  47 */   private final Vector3d velocity = new Vector3d(); private class_1676 simulatingEntity; private class_4048 dimensions; private int age; private int pierceLevel; private double gravity; private float airDrag; private float waterDrag; private boolean isTouchingWater;
/*     */   
/*     */   public static final class MotionData extends Record { private final float power;
/*     */     private final float roll;
/*     */     private final double gravity;
/*     */     private final float airDrag;
/*     */     private final float waterDrag;
/*     */     private final class_1299<?> entity;
/*     */     
/*  56 */     public MotionData(float power, float roll, double gravity, float airDrag, float waterDrag, class_1299<?> entity) { this.power = power; this.roll = roll; this.gravity = gravity; this.airDrag = airDrag; this.waterDrag = waterDrag; this.entity = entity; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #56	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  56 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData; } public float power() { return this.power; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #56	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #56	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*  56 */       //   0	8	1	o	Ljava/lang/Object; } public float roll() { return this.roll; } public double gravity() { return this.gravity; } public float airDrag() { return this.airDrag; } public float waterDrag() { return this.waterDrag; } public class_1299<?> entity() { return this.entity; }
/*     */      public MotionData withPower(float power) {
/*  58 */       return new MotionData(power, roll(), gravity(), airDrag(), waterDrag(), entity());
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static final MotionData EGG = new MotionData(1.5F, 0.0F, 0.03D, 0.99F, 0.8F, class_1299.field_6144);
/*  67 */   private static final MotionData ENDER_PEARL = new MotionData(1.5F, 0.0F, 0.03D, 0.99F, 0.8F, class_1299.field_6082);
/*  68 */   private static final MotionData SNOWBALL = new MotionData(1.5F, 0.0F, 0.03D, 0.99F, 0.8F, class_1299.field_6068);
/*  69 */   private static final MotionData EXPERIENCE_BOTTLE = new MotionData(0.7F, -20.0F, 0.07D, 0.99F, 0.8F, class_1299.field_6064);
/*  70 */   private static final MotionData LINGERING_POTION = new MotionData(0.5F, -20.0F, 0.05D, 0.99F, 0.8F, class_1299.field_56255);
/*  71 */   private static final MotionData SPLASH_POTION = new MotionData(0.5F, -20.0F, 0.05D, 0.99F, 0.8F, class_1299.field_56254);
/*     */ 
/*     */   
/*  74 */   private static final MotionData EXPLOSIVE = new MotionData(0.0F, 0.0F, 0.0D, 1.0F, 1.0F, null);
/*  75 */   private static final MotionData WIND_CHARGE = new MotionData(1.5F, 0.0F, 0.0D, 1.0F, 1.0F, class_1299.field_47243);
/*     */ 
/*     */   
/*  78 */   private static final MotionData ARROW = new MotionData(0.0F, 0.0F, 0.05D, 0.99F, 0.6F, class_1299.field_6122);
/*  79 */   private static final MotionData TRIDENT = new MotionData(2.5F, 0.0F, 0.05D, 0.99F, 0.99F, class_1299.field_6127);
/*     */ 
/*     */   
/*  82 */   private static final MotionData FIREWORK_ROCKET = new MotionData(0.0F, 0.0F, 0.0D, 1.0F, 1.0F, class_1299.field_6133);
/*  83 */   private static final MotionData FISHING_BOBBER = new MotionData(0.0F, 0.0F, 0.03D, 0.92F, 0.0F, class_1299.field_6103);
/*  84 */   private static final MotionData LLAMA_SPIT = new MotionData(1.5F, 0.0F, 0.06D, 0.99F, 0.0F, class_1299.field_6124);
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
/*     */   public boolean set(class_1297 user, class_1799 itemStack, double angleOffset, boolean accurate, float tickDelta) {
/*     */     // Byte code:
/*     */     //   0: aload_2
/*     */     //   1: invokevirtual method_7909 : ()Lnet/minecraft/class_1792;
/*     */     //   4: astore #7
/*     */     //   6: aload #7
/*     */     //   8: dup
/*     */     //   9: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   12: pop
/*     */     //   13: astore #8
/*     */     //   15: iconst_0
/*     */     //   16: istore #9
/*     */     //   18: aload #8
/*     */     //   20: iload #9
/*     */     //   22: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   27: tableswitch default -> 487, 0 -> 84, 1 -> 171, 2 -> 283, 3 -> 306, 4 -> 329, 5 -> 352, 6 -> 375, 7 -> 398, 8 -> 421, 9 -> 444, 10 -> 467
/*     */     //   84: aload #8
/*     */     //   86: checkcast net/minecraft/class_1753
/*     */     //   89: astore #10
/*     */     //   91: aload_1
/*     */     //   92: instanceof net/minecraft/class_1309
/*     */     //   95: ifeq -> 107
/*     */     //   98: aload_1
/*     */     //   99: checkcast net/minecraft/class_1309
/*     */     //   102: astore #11
/*     */     //   104: goto -> 109
/*     */     //   107: iconst_0
/*     */     //   108: ireturn
/*     */     //   109: aload #11
/*     */     //   111: invokevirtual method_6048 : ()I
/*     */     //   114: invokestatic method_7722 : (I)F
/*     */     //   117: fstore #12
/*     */     //   119: fload #12
/*     */     //   121: f2d
/*     */     //   122: ldc2_w 0.1
/*     */     //   125: dcmpg
/*     */     //   126: ifgt -> 147
/*     */     //   129: aload_1
/*     */     //   130: getstatic meteordevelopment/meteorclient/MeteorClient.mc : Lnet/minecraft/class_310;
/*     */     //   133: getfield field_1724 : Lnet/minecraft/class_746;
/*     */     //   136: if_acmpne -> 145
/*     */     //   139: fconst_1
/*     */     //   140: fstore #12
/*     */     //   142: goto -> 147
/*     */     //   145: iconst_0
/*     */     //   146: ireturn
/*     */     //   147: aload_0
/*     */     //   148: aload_1
/*     */     //   149: dload_3
/*     */     //   150: iload #5
/*     */     //   152: fload #6
/*     */     //   154: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.ARROW : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   157: fload #12
/*     */     //   159: ldc 3.0
/*     */     //   161: fmul
/*     */     //   162: invokevirtual withPower : (F)Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   165: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   168: goto -> 489
/*     */     //   171: aload #8
/*     */     //   173: checkcast net/minecraft/class_1764
/*     */     //   176: astore #11
/*     */     //   178: aload_2
/*     */     //   179: getstatic net/minecraft/class_9334.field_49649 : Lnet/minecraft/class_9331;
/*     */     //   182: invokevirtual method_58694 : (Lnet/minecraft/class_9331;)Ljava/lang/Object;
/*     */     //   185: checkcast net/minecraft/class_9278
/*     */     //   188: astore #12
/*     */     //   190: aload #12
/*     */     //   192: ifnonnull -> 197
/*     */     //   195: iconst_0
/*     */     //   196: ireturn
/*     */     //   197: aload #12
/*     */     //   199: invokestatic meteor$getSpeed : (Lnet/minecraft/class_9278;)F
/*     */     //   202: fstore #13
/*     */     //   204: aload #12
/*     */     //   206: getstatic net/minecraft/class_1802.field_8639 : Lnet/minecraft/class_1792;
/*     */     //   209: invokevirtual method_57438 : (Lnet/minecraft/class_1792;)Z
/*     */     //   212: ifeq -> 236
/*     */     //   215: aload_0
/*     */     //   216: aload_1
/*     */     //   217: dload_3
/*     */     //   218: iload #5
/*     */     //   220: fload #6
/*     */     //   222: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.FIREWORK_ROCKET : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   225: fload #13
/*     */     //   227: invokevirtual withPower : (F)Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   230: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   233: goto -> 254
/*     */     //   236: aload_0
/*     */     //   237: aload_1
/*     */     //   238: dload_3
/*     */     //   239: iload #5
/*     */     //   241: fload #6
/*     */     //   243: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.ARROW : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   246: fload #13
/*     */     //   248: invokevirtual withPower : (F)Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   251: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   254: aload_0
/*     */     //   255: aload #12
/*     */     //   257: getstatic net/minecraft/class_1802.field_8639 : Lnet/minecraft/class_1792;
/*     */     //   260: invokevirtual method_57438 : (Lnet/minecraft/class_1792;)Z
/*     */     //   263: ifeq -> 270
/*     */     //   266: iconst_0
/*     */     //   267: goto -> 277
/*     */     //   270: aload_2
/*     */     //   271: getstatic net/minecraft/class_1893.field_9132 : Lnet/minecraft/class_5321;
/*     */     //   274: invokestatic getEnchantmentLevel : (Lnet/minecraft/class_1799;Lnet/minecraft/class_5321;)I
/*     */     //   277: putfield pierceLevel : I
/*     */     //   280: goto -> 489
/*     */     //   283: aload #8
/*     */     //   285: checkcast net/minecraft/class_9239
/*     */     //   288: astore #12
/*     */     //   290: aload_0
/*     */     //   291: aload_1
/*     */     //   292: dload_3
/*     */     //   293: iload #5
/*     */     //   295: fload #6
/*     */     //   297: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.WIND_CHARGE : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   300: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   303: goto -> 489
/*     */     //   306: aload #8
/*     */     //   308: checkcast net/minecraft/class_1835
/*     */     //   311: astore #13
/*     */     //   313: aload_0
/*     */     //   314: aload_1
/*     */     //   315: dload_3
/*     */     //   316: iload #5
/*     */     //   318: fload #6
/*     */     //   320: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.TRIDENT : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   323: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   326: goto -> 489
/*     */     //   329: aload #8
/*     */     //   331: checkcast net/minecraft/class_1823
/*     */     //   334: astore #14
/*     */     //   336: aload_0
/*     */     //   337: aload_1
/*     */     //   338: dload_3
/*     */     //   339: iload #5
/*     */     //   341: fload #6
/*     */     //   343: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.SNOWBALL : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   346: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   349: goto -> 489
/*     */     //   352: aload #8
/*     */     //   354: checkcast net/minecraft/class_1771
/*     */     //   357: astore #15
/*     */     //   359: aload_0
/*     */     //   360: aload_1
/*     */     //   361: dload_3
/*     */     //   362: iload #5
/*     */     //   364: fload #6
/*     */     //   366: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.EGG : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   369: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   372: goto -> 489
/*     */     //   375: aload #8
/*     */     //   377: checkcast net/minecraft/class_1776
/*     */     //   380: astore #16
/*     */     //   382: aload_0
/*     */     //   383: aload_1
/*     */     //   384: dload_3
/*     */     //   385: iload #5
/*     */     //   387: fload #6
/*     */     //   389: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.ENDER_PEARL : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   392: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   395: goto -> 489
/*     */     //   398: aload #8
/*     */     //   400: checkcast net/minecraft/class_1779
/*     */     //   403: astore #17
/*     */     //   405: aload_0
/*     */     //   406: aload_1
/*     */     //   407: dload_3
/*     */     //   408: iload #5
/*     */     //   410: fload #6
/*     */     //   412: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.EXPERIENCE_BOTTLE : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   415: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   418: goto -> 489
/*     */     //   421: aload #8
/*     */     //   423: checkcast net/minecraft/class_1828
/*     */     //   426: astore #18
/*     */     //   428: aload_0
/*     */     //   429: aload_1
/*     */     //   430: dload_3
/*     */     //   431: iload #5
/*     */     //   433: fload #6
/*     */     //   435: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.SPLASH_POTION : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   438: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   441: goto -> 489
/*     */     //   444: aload #8
/*     */     //   446: checkcast net/minecraft/class_1803
/*     */     //   449: astore #19
/*     */     //   451: aload_0
/*     */     //   452: aload_1
/*     */     //   453: dload_3
/*     */     //   454: iload #5
/*     */     //   456: fload #6
/*     */     //   458: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.LINGERING_POTION : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   461: invokevirtual set : (Lnet/minecraft/class_1297;DZFLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   464: goto -> 489
/*     */     //   467: aload #8
/*     */     //   469: checkcast net/minecraft/class_1787
/*     */     //   472: astore #20
/*     */     //   474: aload_0
/*     */     //   475: aload_1
/*     */     //   476: fload #6
/*     */     //   478: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.FISHING_BOBBER : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   481: invokevirtual setFishingBobber : (Lnet/minecraft/class_1297;FLmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   484: goto -> 489
/*     */     //   487: iconst_0
/*     */     //   488: ireturn
/*     */     //   489: iconst_1
/*     */     //   490: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #90	-> 0
/*     */     //   #92	-> 6
/*     */     //   #93	-> 84
/*     */     //   #94	-> 91
/*     */     //   #95	-> 109
/*     */     //   #97	-> 119
/*     */     //   #98	-> 129
/*     */     //   #99	-> 145
/*     */     //   #102	-> 147
/*     */     //   #103	-> 168
/*     */     //   #104	-> 171
/*     */     //   #105	-> 178
/*     */     //   #106	-> 190
/*     */     //   #108	-> 197
/*     */     //   #109	-> 204
/*     */     //   #110	-> 215
/*     */     //   #112	-> 236
/*     */     //   #114	-> 254
/*     */     //   #115	-> 280
/*     */     //   #116	-> 283
/*     */     //   #117	-> 306
/*     */     //   #118	-> 329
/*     */     //   #119	-> 352
/*     */     //   #120	-> 375
/*     */     //   #121	-> 398
/*     */     //   #122	-> 421
/*     */     //   #123	-> 444
/*     */     //   #124	-> 467
/*     */     //   #126	-> 487
/*     */     //   #130	-> 489
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   104	3	11	livingEntity	Lnet/minecraft/class_1309;
/*     */     //   109	59	11	livingEntity	Lnet/minecraft/class_1309;
/*     */     //   119	49	12	charge	F
/*     */     //   91	80	10	ignored	Lnet/minecraft/class_1753;
/*     */     //   190	90	12	projectilesComponent	Lnet/minecraft/class_9278;
/*     */     //   204	76	13	speed	F
/*     */     //   178	105	11	ignored	Lnet/minecraft/class_1764;
/*     */     //   290	16	12	ignored	Lnet/minecraft/class_9239;
/*     */     //   313	16	13	ignored	Lnet/minecraft/class_1835;
/*     */     //   336	16	14	ignored	Lnet/minecraft/class_1823;
/*     */     //   359	16	15	ignored	Lnet/minecraft/class_1771;
/*     */     //   382	16	16	ignored	Lnet/minecraft/class_1776;
/*     */     //   405	16	17	ignored	Lnet/minecraft/class_1779;
/*     */     //   428	16	18	ignored	Lnet/minecraft/class_1828;
/*     */     //   451	16	19	ignored	Lnet/minecraft/class_1803;
/*     */     //   474	13	20	ignored	Lnet/minecraft/class_1787;
/*     */     //   0	491	0	this	Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator;
/*     */     //   0	491	1	user	Lnet/minecraft/class_1297;
/*     */     //   0	491	2	itemStack	Lnet/minecraft/class_1799;
/*     */     //   0	491	3	angleOffset	D
/*     */     //   0	491	5	accurate	Z
/*     */     //   0	491	6	tickDelta	F
/*     */     //   6	485	7	item	Lnet/minecraft/class_1792;
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
/*     */   public void set(class_1297 user, double angleOffset, boolean accurate, float tickDelta, MotionData data) {
/*     */     double yaw, pitch, x, y, z;
/* 136 */     class_4050 pose = user.method_18376();
/* 137 */     if (user == MeteorClient.mc.field_1724 && (((NoSlow)Modules.get().get(NoSlow.class)).airStrict() || ((Sneak)Modules.get().get(Sneak.class)).doPacket())) pose = class_4050.field_18081; 
/* 138 */     Utils.set(this.pos, user, tickDelta).add(0.0D, (user.method_18381(pose) - 0.1F), 0.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 143 */     if (user == MeteorClient.mc.field_1724 && Rotations.rotating) {
/* 144 */       yaw = Rotations.serverYaw;
/* 145 */       pitch = Rotations.serverPitch;
/*     */     } else {
/* 147 */       yaw = user.method_5705(tickDelta);
/* 148 */       pitch = user.method_5695(tickDelta);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 153 */     if (angleOffset == 0.0D) {
/* 154 */       x = -Math.sin(yaw * 0.017453292D) * Math.cos(pitch * 0.017453292D);
/* 155 */       y = -Math.sin((pitch + data.roll()) * 0.017453292D);
/* 156 */       z = Math.cos(yaw * 0.017453292D) * Math.cos(pitch * 0.017453292D);
/*     */     } else {
/*     */       
/* 159 */       class_243 oppositeRotationVec = user.method_18864(1.0F);
/* 160 */       Quaterniond quaternion = (new Quaterniond()).setAngleAxis(angleOffset, oppositeRotationVec.field_1352, oppositeRotationVec.field_1351, oppositeRotationVec.field_1350);
/* 161 */       class_243 rotationVec = user.method_5828(1.0F);
/* 162 */       Vector3d vector3d = new Vector3d(rotationVec.field_1352, rotationVec.field_1351, rotationVec.field_1350);
/* 163 */       vector3d.rotate((Quaterniondc)quaternion);
/*     */       
/* 165 */       x = vector3d.x;
/* 166 */       y = vector3d.y;
/* 167 */       z = vector3d.z;
/*     */     } 
/*     */     
/* 170 */     this.velocity.set(x, y, z).normalize().mul(data.power());
/*     */     
/* 172 */     if (accurate) {
/* 173 */       class_243 vel = user.method_60478();
/* 174 */       this.velocity.add(vel.field_1352, user.method_24828() ? 0.0D : vel.field_1351, vel.field_1350);
/*     */     } 
/*     */     
/* 177 */     setSimulationData((class_1676)data.entity().method_5883((class_1937)MeteorClient.mc.field_1687, null), data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFishingBobber(class_1297 user, float tickDelta, MotionData data) {
/*     */     double yaw, pitch;
/* 184 */     if (user == MeteorClient.mc.field_1724 && Rotations.rotating) {
/* 185 */       yaw = Rotations.serverYaw;
/* 186 */       pitch = Rotations.serverPitch;
/*     */     } else {
/* 188 */       yaw = user.method_5705(tickDelta);
/* 189 */       pitch = user.method_5695(tickDelta);
/*     */     } 
/*     */     
/* 192 */     double h = Math.cos(-yaw * 0.01745329238474369D - 3.1415927410125732D);
/* 193 */     double i = Math.sin(-yaw * 0.01745329238474369D - 3.1415927410125732D);
/* 194 */     double j = -Math.cos(-pitch * 0.01745329238474369D);
/* 195 */     double k = Math.sin(-pitch * 0.01745329238474369D);
/*     */     
/* 197 */     class_4050 pose = user.method_18376();
/* 198 */     if (user == MeteorClient.mc.field_1724 && (((NoSlow)Modules.get().get(NoSlow.class)).airStrict() || ((Sneak)Modules.get().get(Sneak.class)).doPacket())) pose = class_4050.field_18081; 
/* 199 */     Utils.set(this.pos, user, tickDelta).sub(i * 0.3D, 0.0D, h * 0.3D).add(0.0D, user.method_18381(pose), 0.0D);
/*     */     
/* 201 */     this.velocity.set(-i, class_3532.method_15350(-(k / j), -5.0D, 5.0D), -h);
/*     */     
/* 203 */     double l = this.velocity.length();
/* 204 */     this.velocity.mul(0.6D / l + 0.5D, 0.6D / l + 0.5D, 0.6D / l + 0.5D);
/*     */     
/* 206 */     setSimulationData((class_1676)data.entity().method_5883((class_1937)MeteorClient.mc.field_1687, null), data);
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
/*     */   public boolean set(class_1297 entity) {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: instanceof meteordevelopment/meteorclient/mixin/ProjectileInGroundAccessor
/*     */     //   4: ifeq -> 23
/*     */     //   7: aload_1
/*     */     //   8: checkcast meteordevelopment/meteorclient/mixin/ProjectileInGroundAccessor
/*     */     //   11: astore_2
/*     */     //   12: aload_2
/*     */     //   13: invokeinterface meteor$invokeIsInGround : ()Z
/*     */     //   18: ifeq -> 23
/*     */     //   21: iconst_0
/*     */     //   22: ireturn
/*     */     //   23: aload_1
/*     */     //   24: dup
/*     */     //   25: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */     //   28: pop
/*     */     //   29: astore_2
/*     */     //   30: iconst_0
/*     */     //   31: istore_3
/*     */     //   32: aload_2
/*     */     //   33: iload_3
/*     */     //   34: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   39: tableswitch default -> 316, 0 -> 100, 1 -> 118, 2 -> 136, 3 -> 154, 4 -> 172, 5 -> 190, 6 -> 208, 7 -> 226, 8 -> 244, 9 -> 262, 10 -> 280, 11 -> 298
/*     */     //   100: aload_2
/*     */     //   101: checkcast net/minecraft/class_1667
/*     */     //   104: astore #4
/*     */     //   106: aload_0
/*     */     //   107: aload #4
/*     */     //   109: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.ARROW : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   112: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   115: goto -> 318
/*     */     //   118: aload_2
/*     */     //   119: checkcast net/minecraft/class_1679
/*     */     //   122: astore #5
/*     */     //   124: aload_0
/*     */     //   125: aload #5
/*     */     //   127: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.ARROW : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   130: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   133: goto -> 318
/*     */     //   136: aload_2
/*     */     //   137: checkcast net/minecraft/class_1685
/*     */     //   140: astore #6
/*     */     //   142: aload_0
/*     */     //   143: aload #6
/*     */     //   145: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.TRIDENT : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   148: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   151: goto -> 318
/*     */     //   154: aload_2
/*     */     //   155: checkcast net/minecraft/class_1684
/*     */     //   158: astore #7
/*     */     //   160: aload_0
/*     */     //   161: aload #7
/*     */     //   163: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.ENDER_PEARL : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   166: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   169: goto -> 318
/*     */     //   172: aload_2
/*     */     //   173: checkcast net/minecraft/class_1680
/*     */     //   176: astore #8
/*     */     //   178: aload_0
/*     */     //   179: aload #8
/*     */     //   181: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.SNOWBALL : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   184: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   187: goto -> 318
/*     */     //   190: aload_2
/*     */     //   191: checkcast net/minecraft/class_1681
/*     */     //   194: astore #9
/*     */     //   196: aload_0
/*     */     //   197: aload #9
/*     */     //   199: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.EGG : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   202: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   205: goto -> 318
/*     */     //   208: aload_2
/*     */     //   209: checkcast net/minecraft/class_1683
/*     */     //   212: astore #10
/*     */     //   214: aload_0
/*     */     //   215: aload #10
/*     */     //   217: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.EXPERIENCE_BOTTLE : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   220: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   223: goto -> 318
/*     */     //   226: aload_2
/*     */     //   227: checkcast net/minecraft/class_10691
/*     */     //   230: astore #11
/*     */     //   232: aload_0
/*     */     //   233: aload #11
/*     */     //   235: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.SPLASH_POTION : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   238: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   241: goto -> 318
/*     */     //   244: aload_2
/*     */     //   245: checkcast net/minecraft/class_10690
/*     */     //   248: astore #12
/*     */     //   250: aload_0
/*     */     //   251: aload #12
/*     */     //   253: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.LINGERING_POTION : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   256: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   259: goto -> 318
/*     */     //   262: aload_2
/*     */     //   263: checkcast net/minecraft/class_9236
/*     */     //   266: astore #13
/*     */     //   268: aload_0
/*     */     //   269: aload #13
/*     */     //   271: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.WIND_CHARGE : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   274: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   277: goto -> 318
/*     */     //   280: aload_2
/*     */     //   281: checkcast net/minecraft/class_1668
/*     */     //   284: astore #14
/*     */     //   286: aload_0
/*     */     //   287: aload #14
/*     */     //   289: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.EXPLOSIVE : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   292: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   295: goto -> 318
/*     */     //   298: aload_2
/*     */     //   299: checkcast net/minecraft/class_1673
/*     */     //   302: astore #15
/*     */     //   304: aload_0
/*     */     //   305: aload #15
/*     */     //   307: getstatic meteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator.LLAMA_SPIT : Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*     */     //   310: invokevirtual set : (Lnet/minecraft/class_1676;Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)V
/*     */     //   313: goto -> 318
/*     */     //   316: iconst_0
/*     */     //   317: ireturn
/*     */     //   318: aload_1
/*     */     //   319: invokevirtual method_5740 : ()Z
/*     */     //   322: ifeq -> 330
/*     */     //   325: aload_0
/*     */     //   326: dconst_0
/*     */     //   327: putfield gravity : D
/*     */     //   330: iconst_1
/*     */     //   331: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #214	-> 0
/*     */     //   #216	-> 23
/*     */     //   #217	-> 100
/*     */     //   #218	-> 118
/*     */     //   #219	-> 136
/*     */     //   #220	-> 154
/*     */     //   #221	-> 172
/*     */     //   #222	-> 190
/*     */     //   #223	-> 208
/*     */     //   #224	-> 226
/*     */     //   #225	-> 244
/*     */     //   #226	-> 262
/*     */     //   #227	-> 280
/*     */     //   #228	-> 298
/*     */     //   #230	-> 316
/*     */     //   #234	-> 318
/*     */     //   #235	-> 325
/*     */     //   #238	-> 330
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   12	11	2	ppe	Lmeteordevelopment/meteorclient/mixin/ProjectileInGroundAccessor;
/*     */     //   106	12	4	e	Lnet/minecraft/class_1667;
/*     */     //   124	12	5	e	Lnet/minecraft/class_1679;
/*     */     //   142	12	6	e	Lnet/minecraft/class_1685;
/*     */     //   160	12	7	e	Lnet/minecraft/class_1684;
/*     */     //   178	12	8	e	Lnet/minecraft/class_1680;
/*     */     //   196	12	9	e	Lnet/minecraft/class_1681;
/*     */     //   214	12	10	e	Lnet/minecraft/class_1683;
/*     */     //   232	12	11	e	Lnet/minecraft/class_10691;
/*     */     //   250	12	12	e	Lnet/minecraft/class_10690;
/*     */     //   268	12	13	e	Lnet/minecraft/class_9236;
/*     */     //   286	12	14	e	Lnet/minecraft/class_1668;
/*     */     //   304	12	15	e	Lnet/minecraft/class_1673;
/*     */     //   0	332	0	this	Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator;
/*     */     //   0	332	1	entity	Lnet/minecraft/class_1297;
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
/*     */   public void set(class_1676 entity, MotionData data) {
/* 242 */     this.pos.set(entity.method_23317(), entity.method_23318(), entity.method_23321());
/*     */     
/* 244 */     double speed = entity.method_18798().method_1033();
/* 245 */     this.velocity.set((entity.method_18798()).field_1352, (entity.method_18798()).field_1351, (entity.method_18798()).field_1350).normalize().mul(speed);
/*     */     
/* 247 */     setSimulationData(entity, data);
/*     */   }
/*     */   
/*     */   private void setSimulationData(class_1676 entity, MotionData data) {
/* 251 */     this.gravity = data.gravity();
/* 252 */     this.airDrag = data.airDrag();
/* 253 */     this.waterDrag = data.waterDrag();
/* 254 */     this.simulatingEntity = entity;
/* 255 */     this.dimensions = this.simulatingEntity.method_18377(this.simulatingEntity.method_18376());
/* 256 */     this.isTouchingWater = this.simulatingEntity.method_5799();
/* 257 */     this.age = this.simulatingEntity.field_6012;
/* 258 */     this.pierceLevel = 0;
/*     */   }
/*     */   
/*     */   public SimulationStep tick() {
/* 262 */     this.age++;
/* 263 */     ((IVec3d)this.prevPos3d).meteor$set(this.pos);
/*     */ 
/*     */     
/* 266 */     if (this.simulatingEntity instanceof net.minecraft.class_1682 || this.simulatingEntity instanceof net.minecraft.class_1668) {
/* 267 */       this.velocity.sub(0.0D, this.gravity, 0.0D);
/* 268 */       this.velocity.mul(this.isTouchingWater ? this.waterDrag : this.airDrag);
/* 269 */       this.pos.add((Vector3dc)this.velocity);
/* 270 */       tickIsTouchingWater();
/*     */     
/*     */     }
/* 273 */     else if (this.simulatingEntity instanceof net.minecraft.class_1665 || this.simulatingEntity instanceof net.minecraft.class_1673) {
/* 274 */       this.pos.add((Vector3dc)this.velocity);
/* 275 */       this.velocity.mul(this.isTouchingWater ? this.waterDrag : this.airDrag);
/* 276 */       this.velocity.sub(0.0D, this.gravity, 0.0D);
/* 277 */       tickIsTouchingWater();
/*     */ 
/*     */     
/*     */     }
/* 281 */     else if (this.simulatingEntity instanceof class_1676) {
/* 282 */       tickIsTouchingWater();
/* 283 */       this.velocity.sub(0.0D, this.gravity, 0.0D);
/* 284 */       this.pos.add((Vector3dc)this.velocity);
/* 285 */       this.velocity.mul(this.isTouchingWater ? this.waterDrag : this.airDrag);
/*     */     } 
/*     */ 
/*     */     
/* 289 */     if (this.pos.y < MeteorClient.mc.field_1687.method_31607()) return SimulationStep.MISS;
/*     */ 
/*     */     
/* 292 */     int chunkX = class_4076.method_32204(this.pos.x);
/* 293 */     int chunkZ = class_4076.method_32204(this.pos.z);
/* 294 */     if (!MeteorClient.mc.field_1687.method_2935().method_12123(chunkX, chunkZ)) return SimulationStep.MISS;
/*     */ 
/*     */     
/* 297 */     ((IVec3d)this.pos3d).meteor$set(this.pos);
/* 298 */     if (this.pos3d.equals(this.prevPos3d)) return SimulationStep.MISS;
/*     */     
/* 300 */     return getCollision();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tickIsTouchingWater() {
/* 307 */     class_238 box = this.dimensions.method_30231(this.pos.x, this.pos.y, this.pos.z).method_1011(0.001D);
/* 308 */     int minX = class_3532.method_15357(box.field_1323);
/* 309 */     int maxX = class_3532.method_15384(box.field_1320);
/* 310 */     int minY = class_3532.method_15357(box.field_1322);
/* 311 */     int maxY = class_3532.method_15384(box.field_1325);
/* 312 */     int minZ = class_3532.method_15357(box.field_1321);
/* 313 */     int maxZ = class_3532.method_15384(box.field_1324);
/*     */     
/* 315 */     for (int x = minX; x < maxX; x++) {
/* 316 */       for (int y = minY; y < maxY; y++) {
/* 317 */         for (int z = minZ; z < maxZ; z++) {
/* 318 */           this.blockPos.method_10103(x, y, z);
/* 319 */           class_3610 fluidState = MeteorClient.mc.field_1687.method_8316((class_2338)this.blockPos);
/* 320 */           if (fluidState.method_15767(class_3486.field_15517)) {
/* 321 */             double fluidY = (y + fluidState.method_15763((class_1922)MeteorClient.mc.field_1687, (class_2338)this.blockPos));
/* 322 */             if (fluidY >= box.field_1322) {
/* 323 */               this.isTouchingWater = true;
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 331 */     this.isTouchingWater = false;
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
/*     */   private SimulationStep getCollision() {
/* 343 */     class_3965 class_3965 = MeteorClient.mc.field_1687.method_61717(new class_3959(this.prevPos3d, this.pos3d, class_3959.class_3960.field_17558, 
/*     */ 
/*     */ 
/*     */           
/* 347 */           (this.waterDrag == 0.0F) ? class_3959.class_242.field_1347 : class_3959.class_242.field_1348, (class_1297)this.simulatingEntity));
/*     */ 
/*     */     
/* 350 */     if (class_3965.method_17783() != class_239.class_240.field_1333) {
/* 351 */       ((IVec3d)this.pos3d).meteor$set(class_3965.method_17784());
/*     */     }
/*     */ 
/*     */     
/* 355 */     if (this.simulatingEntity instanceof net.minecraft.class_1665) {
/* 356 */       Collection<class_3966> entityCollisions = class_1675.method_75215((class_1937)MeteorClient.mc.field_1687, (class_1297)this.simulatingEntity, this.prevPos3d, this.pos3d, this.dimensions
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 361 */           .method_30757(this.prevPos3d).method_1012(this.velocity.x, this.velocity.y, this.velocity.z).method_1014(1.0D), entity -> 
/* 362 */           (!entity.method_7325() && entity.method_5805() && entity.method_5863()), 
/* 363 */           getToleranceMargin(), class_3959.class_3960.field_17558, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 369 */       entityCollisions.removeIf(collision -> (this.age <= 1 && collision.method_17782() == MeteorClient.mc.field_1724));
/* 370 */       if (entityCollisions.isEmpty()) return new SimulationStep(hitOrDeflect((class_239)class_3965), new class_239[] { (class_239)class_3965 });
/*     */       
/* 372 */       boolean stop = false;
/* 373 */       ArrayList<class_3966> hits = new ArrayList<>();
/* 374 */       for (class_3966 result : entityCollisions) {
/* 375 */         boolean hit = hitOrDeflect((class_239)result);
/* 376 */         if (!hit)
/*     */           break; 
/* 378 */         hits.add(result);
/* 379 */         if (this.pierceLevel <= 0) {
/* 380 */           stop = true;
/*     */           
/*     */           break;
/*     */         } 
/* 384 */         this.pierceLevel--;
/*     */       } 
/*     */       
/* 387 */       return new SimulationStep(stop, hits.<class_239>toArray(new class_239[0]));
/*     */     } 
/*     */     
/* 390 */     class_3966 class_3966 = class_1675.method_37226((class_1937)MeteorClient.mc.field_1687, (class_1297)this.simulatingEntity, this.prevPos3d, this.pos3d, this.dimensions
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 395 */         .method_30757(this.prevPos3d).method_1012(this.velocity.x, this.velocity.y, this.velocity.z).method_1014(1.0D), entity -> 
/* 396 */         (!entity.method_7325() && entity.method_5805() && entity.method_5863()), 
/* 397 */         getToleranceMargin());
/*     */ 
/*     */     
/* 400 */     if (class_3966 != null) { if (this.age <= 1 && class_3966 instanceof class_3966) { class_3966 ehr = class_3966; if (ehr.method_17782() == MeteorClient.mc.field_1724)
/* 401 */           return new SimulationStep(hitOrDeflect((class_239)class_3965), new class_239[] { (class_239)class_3965 });  }  } else { return new SimulationStep(hitOrDeflect((class_239)class_3965), new class_239[] { (class_239)class_3965 }); }
/*     */ 
/*     */     
/* 404 */     if (hitOrDeflect((class_239)class_3966)) return new SimulationStep(true, new class_239[] { (class_239)class_3966 }); 
/* 405 */     return new SimulationStep(false, new class_239[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean hitOrDeflect(class_239 hitResult) {
/* 415 */     if (hitResult instanceof class_3966) { class_3966 entityHitResult = (class_3966)hitResult;
/* 416 */       class_1297 entity = entityHitResult.method_17782();
/* 417 */       Utils.set(this.pos, entityHitResult.method_17784());
/*     */       
/* 419 */       if ((entity instanceof net.minecraft.class_8949 && !(this.simulatingEntity instanceof net.minecraft.class_9236)) || entity.method_56071(this.simulatingEntity) == class_9109.field_48348) {
/* 420 */         this.velocity.mul(-0.5D);
/* 421 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 431 */       if (entity instanceof class_1309) { class_1309 livingEntity = (class_1309)entity; if (livingEntity.method_6039() && this.simulatingEntity instanceof net.minecraft.class_1665) {
/* 432 */           this.velocity.mul(-0.5D).mul(0.2D);
/* 433 */           return (this.velocity.lengthSquared() < 1.0E-7D);
/*     */         }  }
/*     */       
/* 436 */       return true; }
/*     */     
/* 438 */     if (hitResult instanceof class_3965) { class_3965 bhr = (class_3965)hitResult;
/* 439 */       Utils.set(this.pos, bhr.method_17784());
/*     */       
/* 441 */       if (this.simulatingEntity.method_62823() && bhr.method_62877()) {
/* 442 */         this.velocity.mul(-0.5D).mul(0.2D);
/* 443 */         return false;
/*     */       } 
/*     */       
/* 446 */       return (bhr.method_17783() != class_239.class_240.field_1333); }
/*     */ 
/*     */     
/* 449 */     return false;
/*     */   }
/*     */   
/*     */   private float getToleranceMargin() {
/* 453 */     return Math.max(0.0F, Math.min(0.3F, (this.age - 2) / 20.0F));
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\entity\simulator\ProjectileEntitySimulator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */