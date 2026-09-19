/*     */ package meteordevelopment.meteorclient.systems.modules.world;
/*     */ 
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.EntityTypeListSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Flamethrower
/*     */   extends Module
/*     */ {
/*  32 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  34 */   private final Setting<Double> distance = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  35 */       .name("distance"))
/*  36 */       .description("The maximum distance the animal has to be to be roasted."))
/*  37 */       .min(0.0D)
/*  38 */       .defaultValue(5.0D)
/*  39 */       .build());
/*     */ 
/*     */   
/*  42 */   private final Setting<Boolean> antiBreak = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  43 */       .name("anti-break"))
/*  44 */       .description("Prevents flint and steel from being broken."))
/*  45 */       .defaultValue(Boolean.valueOf(false)))
/*  46 */       .build());
/*     */ 
/*     */   
/*  49 */   private final Setting<Boolean> putOutFire = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  50 */       .name("put-out-fire"))
/*  51 */       .description("Tries to put out the fire when animal is low health, so the items don't burn."))
/*  52 */       .defaultValue(Boolean.valueOf(true)))
/*  53 */       .build());
/*     */ 
/*     */   
/*  56 */   private final Setting<Boolean> targetBabies = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  57 */       .name("target-babies"))
/*  58 */       .description("If checked babies will also be killed."))
/*  59 */       .defaultValue(Boolean.valueOf(false)))
/*  60 */       .build());
/*     */ 
/*     */   
/*  63 */   private final Setting<Integer> tickInterval = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/*  64 */       .name("tick-interval"))
/*  65 */       .defaultValue(Integer.valueOf(5)))
/*  66 */       .build());
/*     */ 
/*     */   
/*  69 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  70 */       .name("rotate"))
/*  71 */       .description("Automatically faces towards the animal roasted."))
/*  72 */       .defaultValue(Boolean.valueOf(true)))
/*  73 */       .build());
/*     */ 
/*     */   
/*  76 */   private final Setting<Set<class_1299<?>>> entities = this.sgGeneral.add((Setting)((EntityTypeListSetting.Builder)((EntityTypeListSetting.Builder)(new EntityTypeListSetting.Builder())
/*  77 */       .name("entities"))
/*  78 */       .description("Entities to cook."))
/*  79 */       .defaultValue(new class_1299[] {
/*     */ 
/*     */           
/*     */           class_1299.field_6093, class_1299.field_6085, class_1299.field_6115, class_1299.field_6132, class_1299.field_6140
/*     */ 
/*     */ 
/*     */         
/*  86 */         }).build());
/*     */   
/*     */   private class_1297 entity;
/*     */   
/*  90 */   private int ticks = 0;
/*     */   private class_1268 hand;
/*     */   
/*     */   public Flamethrower() {
/*  94 */     super(Categories.World, "flamethrower", "Ignites every alive piece of food.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/*  99 */     this.entity = null;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 104 */     this.entity = null;
/* 105 */     this.ticks++;
/* 106 */     for (class_1297 entity : this.mc.field_1687.method_18112()) {
/* 107 */       if (!((Set)this.entities.get()).contains(entity.method_5864()) || !PlayerUtils.isWithin(entity, ((Double)this.distance.get()).doubleValue()) || 
/* 108 */         entity == this.mc.field_1724 || 
/* 109 */         !entity.method_5805() || entity.field_27857 || entity.method_5721() || entity.method_5753())
/*     */         continue; 
/* 111 */       if (!((Boolean)this.targetBabies.get()).booleanValue() && entity instanceof class_1309) { class_1309 livingEntity = (class_1309)entity; if (livingEntity.method_6109())
/*     */           continue;  }
/* 113 */        FindItemResult item = InvUtils.findInHotbar(itemStack -> ((itemStack.method_31574(class_1802.field_8884) || itemStack.method_31574(class_1802.field_8814)) && (!itemStack.method_7963() || !((Boolean)this.antiBreak.get()).booleanValue() || itemStack.method_7919() < itemStack.method_7936() - 1)));
/*     */       
/* 115 */       if (!InvUtils.swap(item.slot(), true))
/*     */         return; 
/* 117 */       this.hand = item.getHand();
/* 118 */       this.entity = entity;
/*     */       
/* 120 */       if (((Boolean)this.rotate.get()).booleanValue()) { Rotations.rotate(Rotations.getYaw(entity.method_24515()), Rotations.getPitch(entity.method_24515()), -100, this::interact); }
/* 121 */       else { interact(); }
/*     */       
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void interact() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield mc : Lnet/minecraft/class_310;
/*     */     //   4: getfield field_1687 : Lnet/minecraft/class_638;
/*     */     //   7: aload_0
/*     */     //   8: getfield entity : Lnet/minecraft/class_1297;
/*     */     //   11: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
/*     */     //   14: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
/*     */     //   17: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
/*     */     //   20: astore_1
/*     */     //   21: aload_0
/*     */     //   22: getfield mc : Lnet/minecraft/class_310;
/*     */     //   25: getfield field_1687 : Lnet/minecraft/class_638;
/*     */     //   28: aload_0
/*     */     //   29: getfield entity : Lnet/minecraft/class_1297;
/*     */     //   32: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
/*     */     //   35: invokevirtual method_10074 : ()Lnet/minecraft/class_2338;
/*     */     //   38: invokevirtual method_8320 : (Lnet/minecraft/class_2338;)Lnet/minecraft/class_2680;
/*     */     //   41: invokevirtual method_26204 : ()Lnet/minecraft/class_2248;
/*     */     //   44: astore_2
/*     */     //   45: aload_1
/*     */     //   46: getstatic net/minecraft/class_2246.field_10382 : Lnet/minecraft/class_2248;
/*     */     //   49: if_acmpeq -> 66
/*     */     //   52: aload_2
/*     */     //   53: getstatic net/minecraft/class_2246.field_10382 : Lnet/minecraft/class_2248;
/*     */     //   56: if_acmpeq -> 66
/*     */     //   59: aload_2
/*     */     //   60: getstatic net/minecraft/class_2246.field_10194 : Lnet/minecraft/class_2248;
/*     */     //   63: if_acmpne -> 67
/*     */     //   66: return
/*     */     //   67: aload_1
/*     */     //   68: getstatic net/minecraft/class_2246.field_10219 : Lnet/minecraft/class_2248;
/*     */     //   71: if_acmpne -> 95
/*     */     //   74: aload_0
/*     */     //   75: getfield mc : Lnet/minecraft/class_310;
/*     */     //   78: getfield field_1761 : Lnet/minecraft/class_636;
/*     */     //   81: aload_0
/*     */     //   82: getfield entity : Lnet/minecraft/class_1297;
/*     */     //   85: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
/*     */     //   88: getstatic net/minecraft/class_2350.field_11033 : Lnet/minecraft/class_2350;
/*     */     //   91: invokevirtual method_2910 : (Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)Z
/*     */     //   94: pop
/*     */     //   95: aload_0
/*     */     //   96: getfield putOutFire : Lmeteordevelopment/meteorclient/settings/Setting;
/*     */     //   99: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   102: checkcast java/lang/Boolean
/*     */     //   105: invokevirtual booleanValue : ()Z
/*     */     //   108: ifeq -> 232
/*     */     //   111: aload_0
/*     */     //   112: getfield entity : Lnet/minecraft/class_1297;
/*     */     //   115: astore #4
/*     */     //   117: aload #4
/*     */     //   119: instanceof net/minecraft/class_1309
/*     */     //   122: ifeq -> 232
/*     */     //   125: aload #4
/*     */     //   127: checkcast net/minecraft/class_1309
/*     */     //   130: astore_3
/*     */     //   131: aload_3
/*     */     //   132: invokevirtual method_6032 : ()F
/*     */     //   135: fconst_2
/*     */     //   136: fcmpg
/*     */     //   137: ifge -> 232
/*     */     //   140: aload_0
/*     */     //   141: getfield mc : Lnet/minecraft/class_310;
/*     */     //   144: getfield field_1761 : Lnet/minecraft/class_636;
/*     */     //   147: aload_0
/*     */     //   148: getfield entity : Lnet/minecraft/class_1297;
/*     */     //   151: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
/*     */     //   154: getstatic net/minecraft/class_2350.field_11033 : Lnet/minecraft/class_2350;
/*     */     //   157: invokevirtual method_2910 : (Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)Z
/*     */     //   160: pop
/*     */     //   161: invokestatic values : ()[Lmeteordevelopment/meteorclient/utils/misc/HorizontalDirection;
/*     */     //   164: astore #4
/*     */     //   166: aload #4
/*     */     //   168: arraylength
/*     */     //   169: istore #5
/*     */     //   171: iconst_0
/*     */     //   172: istore #6
/*     */     //   174: iload #6
/*     */     //   176: iload #5
/*     */     //   178: if_icmpge -> 229
/*     */     //   181: aload #4
/*     */     //   183: iload #6
/*     */     //   185: aaload
/*     */     //   186: astore #7
/*     */     //   188: aload_0
/*     */     //   189: getfield mc : Lnet/minecraft/class_310;
/*     */     //   192: getfield field_1761 : Lnet/minecraft/class_636;
/*     */     //   195: aload_0
/*     */     //   196: getfield entity : Lnet/minecraft/class_1297;
/*     */     //   199: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
/*     */     //   202: aload #7
/*     */     //   204: getfield offsetX : I
/*     */     //   207: iconst_0
/*     */     //   208: aload #7
/*     */     //   210: getfield offsetZ : I
/*     */     //   213: invokevirtual method_10069 : (III)Lnet/minecraft/class_2338;
/*     */     //   216: getstatic net/minecraft/class_2350.field_11033 : Lnet/minecraft/class_2350;
/*     */     //   219: invokevirtual method_2910 : (Lnet/minecraft/class_2338;Lnet/minecraft/class_2350;)Z
/*     */     //   222: pop
/*     */     //   223: iinc #6, 1
/*     */     //   226: goto -> 174
/*     */     //   229: goto -> 330
/*     */     //   232: aload_0
/*     */     //   233: getfield ticks : I
/*     */     //   236: aload_0
/*     */     //   237: getfield tickInterval : Lmeteordevelopment/meteorclient/settings/Setting;
/*     */     //   240: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   243: checkcast java/lang/Integer
/*     */     //   246: invokevirtual intValue : ()I
/*     */     //   249: if_icmplt -> 330
/*     */     //   252: aload_0
/*     */     //   253: getfield entity : Lnet/minecraft/class_1297;
/*     */     //   256: invokevirtual method_5809 : ()Z
/*     */     //   259: ifne -> 330
/*     */     //   262: aload_0
/*     */     //   263: getfield mc : Lnet/minecraft/class_310;
/*     */     //   266: getfield field_1761 : Lnet/minecraft/class_636;
/*     */     //   269: aload_0
/*     */     //   270: getfield mc : Lnet/minecraft/class_310;
/*     */     //   273: getfield field_1724 : Lnet/minecraft/class_746;
/*     */     //   276: aload_0
/*     */     //   277: getfield hand : Lnet/minecraft/class_1268;
/*     */     //   280: new net/minecraft/class_3965
/*     */     //   283: dup
/*     */     //   284: aload_0
/*     */     //   285: getfield entity : Lnet/minecraft/class_1297;
/*     */     //   288: invokevirtual method_73189 : ()Lnet/minecraft/class_243;
/*     */     //   291: new net/minecraft/class_243
/*     */     //   294: dup
/*     */     //   295: dconst_0
/*     */     //   296: dconst_1
/*     */     //   297: dconst_0
/*     */     //   298: invokespecial <init> : (DDD)V
/*     */     //   301: invokevirtual method_1020 : (Lnet/minecraft/class_243;)Lnet/minecraft/class_243;
/*     */     //   304: getstatic net/minecraft/class_2350.field_11036 : Lnet/minecraft/class_2350;
/*     */     //   307: aload_0
/*     */     //   308: getfield entity : Lnet/minecraft/class_1297;
/*     */     //   311: invokevirtual method_24515 : ()Lnet/minecraft/class_2338;
/*     */     //   314: invokevirtual method_10074 : ()Lnet/minecraft/class_2338;
/*     */     //   317: iconst_0
/*     */     //   318: invokespecial <init> : (Lnet/minecraft/class_243;Lnet/minecraft/class_2350;Lnet/minecraft/class_2338;Z)V
/*     */     //   321: invokevirtual method_2896 : (Lnet/minecraft/class_746;Lnet/minecraft/class_1268;Lnet/minecraft/class_3965;)Lnet/minecraft/class_1269;
/*     */     //   324: pop
/*     */     //   325: aload_0
/*     */     //   326: iconst_0
/*     */     //   327: putfield ticks : I
/*     */     //   330: invokestatic swapBack : ()Z
/*     */     //   333: pop
/*     */     //   334: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #128	-> 0
/*     */     //   #129	-> 21
/*     */     //   #130	-> 45
/*     */     //   #131	-> 67
/*     */     //   #133	-> 95
/*     */     //   #134	-> 140
/*     */     //   #135	-> 161
/*     */     //   #136	-> 188
/*     */     //   #135	-> 223
/*     */     //   #139	-> 232
/*     */     //   #140	-> 262
/*     */     //   #141	-> 288
/*     */     //   #140	-> 321
/*     */     //   #142	-> 325
/*     */     //   #146	-> 330
/*     */     //   #147	-> 334
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   188	35	7	direction	Lmeteordevelopment/meteorclient/utils/misc/HorizontalDirection;
/*     */     //   131	101	3	animal	Lnet/minecraft/class_1309;
/*     */     //   0	335	0	this	Lmeteordevelopment/meteorclient/systems/modules/world/Flamethrower;
/*     */     //   21	314	1	block	Lnet/minecraft/class_2248;
/*     */     //   45	290	2	bottom	Lnet/minecraft/class_2248;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\Flamethrower.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */