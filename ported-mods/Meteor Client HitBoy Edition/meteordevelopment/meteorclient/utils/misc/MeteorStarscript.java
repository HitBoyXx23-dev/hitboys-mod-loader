/*     */ package meteordevelopment.meteorclient.utils.misc;
/*     */ 
/*     */ import baritone.api.BaritoneAPI;
/*     */ import baritone.api.pathing.goals.Goal;
/*     */ import baritone.api.process.IBaritoneProcess;
/*     */ import java.time.LocalTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.format.FormatStyle;
/*     */ import java.util.Arrays;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Collectors;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.mixin.ClientPlayerInteractionManagerAccessor;
/*     */ import meteordevelopment.meteorclient.mixin.MinecraftClientAccessor;
/*     */ import meteordevelopment.meteorclient.pathing.BaritoneUtils;
/*     */ import meteordevelopment.meteorclient.pathing.PathManagers;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.utils.PreInit;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.world.Dimension;
/*     */ import meteordevelopment.meteorclient.utils.world.TickRate;
/*     */ import net.minecraft.class_1291;
/*     */ import net.minecraft.class_1293;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_151;
/*     */ import net.minecraft.class_155;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2799;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_3445;
/*     */ import net.minecraft.class_3965;
/*     */ import net.minecraft.class_3966;
/*     */ import net.minecraft.class_640;
/*     */ import net.minecraft.class_6880;
/*     */ import net.minecraft.class_7923;
/*     */ import net.minecraft.class_7924;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.meteordev.starscript.Script;
/*     */ import org.meteordev.starscript.Section;
/*     */ import org.meteordev.starscript.StandardLib;
/*     */ import org.meteordev.starscript.Starscript;
/*     */ import org.meteordev.starscript.compiler.Compiler;
/*     */ import org.meteordev.starscript.compiler.Parser;
/*     */ import org.meteordev.starscript.utils.Error;
/*     */ import org.meteordev.starscript.utils.StarscriptError;
/*     */ import org.meteordev.starscript.value.Value;
/*     */ import org.meteordev.starscript.value.ValueMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeteorStarscript
/*     */ {
/*  71 */   public static Starscript ss = new Starscript();
/*     */   
/*  73 */   private static final class_2338.class_2339 BP = new class_2338.class_2339();
/*  74 */   private static final StringBuilder SB = new StringBuilder();
/*     */   
/*     */   @PreInit(dependencies = {PathManagers.class})
/*     */   public static void init() {
/*  78 */     StandardLib.init(ss);
/*     */ 
/*     */     
/*  81 */     ss.set("mc_version", class_155.method_16673().comp_4025());
/*  82 */     ss.set("fps", () -> Value.number(MinecraftClientAccessor.meteor$getFps()));
/*  83 */     ss.set("ping", MeteorStarscript::ping);
/*  84 */     ss.set("time", () -> Value.string(LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))));
/*  85 */     ss.set("cps", () -> Value.number(CPSUtils.getCpsAverage()));
/*     */ 
/*     */     
/*  88 */     ss.set("meteor", (new ValueMap())
/*  89 */         .set("name", MeteorClient.NAME)
/*  90 */         .set("version", (MeteorClient.VERSION != null) ? (MeteorClient.BUILD_NUMBER.isEmpty() ? MeteorClient.VERSION.toString() : (String.valueOf(MeteorClient.VERSION) + " " + String.valueOf(MeteorClient.VERSION))) : "")
/*  91 */         .set("modules", () -> Value.number(Modules.get().getAll().size()))
/*  92 */         .set("active_modules", () -> Value.number(Modules.get().getActive().size()))
/*  93 */         .set("is_module_active", MeteorStarscript::isModuleActive)
/*  94 */         .set("get_module_info", MeteorStarscript::getModuleInfo)
/*  95 */         .set("get_module_setting", MeteorStarscript::getModuleSetting)
/*  96 */         .set("prefix", MeteorStarscript::getMeteorPrefix));
/*     */ 
/*     */ 
/*     */     
/* 100 */     if (BaritoneUtils.IS_AVAILABLE) {
/* 101 */       ss.set("baritone", (new ValueMap())
/* 102 */           .set("is_pathing", () -> Value.bool(BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().isPathing()))
/* 103 */           .set("distance_to_goal", MeteorStarscript::baritoneDistanceToGoal)
/* 104 */           .set("process", MeteorStarscript::baritoneProcess)
/* 105 */           .set("process_name", MeteorStarscript::baritoneProcessName)
/* 106 */           .set("eta", MeteorStarscript::baritoneETA));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 111 */     ss.set("camera", (new ValueMap())
/* 112 */         .set("pos", (new ValueMap())
/* 113 */           .set("_toString", () -> posString(false, true))
/* 114 */           .set("x", () -> Value.number((MeteorClient.mc.field_1773.method_19418().method_71156()).field_1352))
/* 115 */           .set("y", () -> Value.number((MeteorClient.mc.field_1773.method_19418().method_71156()).field_1351))
/* 116 */           .set("z", () -> Value.number((MeteorClient.mc.field_1773.method_19418().method_71156()).field_1350)))
/*     */         
/* 118 */         .set("opposite_dim_pos", (new ValueMap())
/* 119 */           .set("_toString", () -> posString(true, true))
/* 120 */           .set("x", () -> oppositeX(true))
/* 121 */           .set("y", () -> Value.number((MeteorClient.mc.field_1773.method_19418().method_71156()).field_1351))
/* 122 */           .set("z", () -> oppositeZ(true)))
/*     */ 
/*     */         
/* 125 */         .set("yaw", () -> yaw(true))
/* 126 */         .set("pitch", () -> pitch(true))
/* 127 */         .set("direction", () -> direction(true)));
/*     */ 
/*     */ 
/*     */     
/* 131 */     ss.set("player", (new ValueMap())
/* 132 */         .set("_toString", () -> Value.string(MeteorClient.mc.method_1548().method_1676()))
/* 133 */         .set("health", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_6032() : 0.0D))
/* 134 */         .set("absorption", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_6067() : 0.0D))
/* 135 */         .set("hunger", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_7344().method_7586() : 0.0D))
/* 136 */         .set("saturation", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_7344().method_7589() : 0.0D))
/*     */         
/* 138 */         .set("speed", () -> Value.number(Utils.getPlayerSpeed().method_37267()))
/* 139 */         .set("speed_all", (new ValueMap())
/* 140 */           .set("_toString", () -> Value.string((MeteorClient.mc.field_1724 != null) ? Utils.getPlayerSpeed().toString() : ""))
/* 141 */           .set("x", () -> Value.number((MeteorClient.mc.field_1724 != null) ? (Utils.getPlayerSpeed()).field_1352 : 0.0D))
/* 142 */           .set("y", () -> Value.number((MeteorClient.mc.field_1724 != null) ? (Utils.getPlayerSpeed()).field_1351 : 0.0D))
/* 143 */           .set("z", () -> Value.number((MeteorClient.mc.field_1724 != null) ? (Utils.getPlayerSpeed()).field_1350 : 0.0D)))
/*     */ 
/*     */         
/* 146 */         .set("breaking_progress", () -> Value.number((MeteorClient.mc.field_1761 != null) ? ((ClientPlayerInteractionManagerAccessor)MeteorClient.mc.field_1761).meteor$getBreakingProgress() : 0.0D))
/* 147 */         .set("biome", MeteorStarscript::biome)
/*     */         
/* 149 */         .set("dimension", () -> Value.string(PlayerUtils.getDimension().name()))
/* 150 */         .set("opposite_dimension", () -> Value.string(PlayerUtils.getDimension().opposite().name()))
/*     */         
/* 152 */         .set("gamemode", () -> (PlayerUtils.getGameMode() != null) ? Value.string(StringUtils.capitalize(PlayerUtils.getGameMode().method_8381())) : Value.null_())
/*     */         
/* 154 */         .set("pos", (new ValueMap())
/* 155 */           .set("_toString", () -> posString(false, false))
/* 156 */           .set("x", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_23317() : 0.0D))
/* 157 */           .set("y", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_23318() : 0.0D))
/* 158 */           .set("z", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_23321() : 0.0D)))
/*     */         
/* 160 */         .set("opposite_dim_pos", (new ValueMap())
/* 161 */           .set("_toString", () -> posString(true, false))
/* 162 */           .set("x", () -> oppositeX(false))
/* 163 */           .set("y", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_23318() : 0.0D))
/* 164 */           .set("z", () -> oppositeZ(false)))
/*     */ 
/*     */         
/* 167 */         .set("yaw", () -> yaw(false))
/* 168 */         .set("pitch", () -> pitch(false))
/* 169 */         .set("direction", () -> direction(false))
/*     */         
/* 171 */         .set("hand", () -> (MeteorClient.mc.field_1724 != null) ? wrap(MeteorClient.mc.field_1724.method_6047()) : Value.null_())
/* 172 */         .set("offhand", () -> (MeteorClient.mc.field_1724 != null) ? wrap(MeteorClient.mc.field_1724.method_6079()) : Value.null_())
/* 173 */         .set("hand_or_offhand", MeteorStarscript::handOrOffhand)
/* 174 */         .set("get_item", MeteorStarscript::getItem)
/* 175 */         .set("count_items", MeteorStarscript::countItems)
/*     */         
/* 177 */         .set("xp", (new ValueMap())
/* 178 */           .set("level", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.field_7520 : 0.0D))
/* 179 */           .set("progress", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.field_7510 : 0.0D))
/* 180 */           .set("total", () -> Value.number((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.field_7495 : 0.0D)))
/*     */ 
/*     */         
/* 183 */         .set("has_potion_effect", MeteorStarscript::hasPotionEffect)
/* 184 */         .set("get_potion_effect", MeteorStarscript::getPotionEffect)
/*     */         
/* 186 */         .set("get_stat", MeteorStarscript::getStat));
/*     */ 
/*     */ 
/*     */     
/* 190 */     ss.set("crosshair_target", (new ValueMap())
/* 191 */         .set("type", MeteorStarscript::crosshairType)
/* 192 */         .set("value", MeteorStarscript::crosshairValue));
/*     */ 
/*     */ 
/*     */     
/* 196 */     ss.set("server", (new ValueMap())
/* 197 */         .set("_toString", () -> Value.string(Utils.getWorldName()))
/* 198 */         .set("tps", () -> Value.number(TickRate.INSTANCE.getTickRate()))
/* 199 */         .set("time", () -> Value.string(Utils.getWorldTime()))
/* 200 */         .set("player_count", () -> Value.number((MeteorClient.mc.method_1562() != null) ? MeteorClient.mc.method_1562().method_2880().size() : 0.0D))
/* 201 */         .set("difficulty", () -> Value.string((MeteorClient.mc.field_1687 != null) ? MeteorClient.mc.field_1687.method_8407().method_5460() : "")));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Script compile(String source) {
/* 208 */     Parser.Result result = Parser.parse(source);
/*     */     
/* 210 */     if (result.hasErrors()) {
/* 211 */       for (Error error : result.errors) printChatError(error); 
/* 212 */       return null;
/*     */     } 
/*     */     
/* 215 */     return Compiler.compile(result);
/*     */   }
/*     */   
/*     */   public static Section runSection(Script script, StringBuilder sb) {
/*     */     try {
/* 220 */       return ss.run(script, sb);
/*     */     }
/* 222 */     catch (StarscriptError error) {
/* 223 */       printChatError(error);
/* 224 */       return null;
/*     */     } 
/*     */   }
/*     */   public static String run(Script script, StringBuilder sb) {
/* 228 */     Section section = runSection(script, sb);
/* 229 */     return (section != null) ? section.toString() : null;
/*     */   }
/*     */   
/*     */   public static Section runSection(Script script) {
/* 233 */     return runSection(script, new StringBuilder());
/*     */   }
/*     */   
/*     */   public static String run(Script script) {
/* 237 */     return run(script, new StringBuilder());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void printChatError(int i, Error error) {
/* 243 */     String caller = getCallerName();
/*     */     
/* 245 */     if (caller != null)
/* 246 */     { if (i != -1) { ChatUtils.errorPrefix("Starscript", "%d, %d '%c': %s (from %s)", new Object[] { Integer.valueOf(i), Integer.valueOf(error.character), Character.valueOf(error.ch), error.message, caller }); }
/* 247 */       else { ChatUtils.errorPrefix("Starscript", "%d '%c': %s (from %s)", new Object[] { Integer.valueOf(error.character), Character.valueOf(error.ch), error.message, caller }); }
/*     */       
/*     */        }
/* 250 */     else if (i != -1) { ChatUtils.errorPrefix("Starscript", "%d, %d '%c': %s", new Object[] { Integer.valueOf(i), Integer.valueOf(error.character), Character.valueOf(error.ch), error.message }); }
/* 251 */     else { ChatUtils.errorPrefix("Starscript", "%d '%c': %s", new Object[] { Integer.valueOf(error.character), Character.valueOf(error.ch), error.message }); }
/*     */   
/*     */   }
/*     */   
/*     */   public static void printChatError(Error error) {
/* 256 */     printChatError(-1, error);
/*     */   }
/*     */   
/*     */   public static void printChatError(StarscriptError e) {
/* 260 */     String caller = getCallerName();
/*     */     
/* 262 */     if (caller != null) { ChatUtils.errorPrefix("Starscript", "%s (from %s)", new Object[] { e.getMessage(), caller }); }
/* 263 */     else { ChatUtils.errorPrefix("Starscript", "%s", new Object[] { e.getMessage() }); }
/*     */   
/*     */   }
/*     */   private static String getCallerName() {
/* 267 */     StackTraceElement[] elements = Thread.currentThread().getStackTrace();
/* 268 */     if (elements.length == 0) return null;
/*     */     
/* 270 */     for (int i = 1; i < elements.length; ) {
/* 271 */       String name = elements[i].getClassName();
/*     */       
/* 273 */       if (name.startsWith(Starscript.class.getPackageName()) || 
/* 274 */         name.equals(MeteorStarscript.class.getName())) {
/*     */         i++; continue;
/* 276 */       }  return name.substring(name.lastIndexOf('.') + 1);
/*     */     } 
/*     */     
/* 279 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 284 */   private static long lastRequestedStatsTime = 0L;
/*     */   
/*     */   private static Value hasPotionEffect(Starscript ss, int argCount) {
/* 287 */     if (argCount < 1) ss.error("player.has_potion_effect() requires 1 argument, got %d.", new Object[] { Integer.valueOf(argCount) }); 
/* 288 */     if (MeteorClient.mc.field_1724 == null) return Value.bool(false);
/*     */     
/* 290 */     class_2960 name = popIdentifier(ss, "First argument to player.has_potion_effect() needs to a string.");
/*     */     
/* 292 */     Optional<class_6880.class_6883<class_1291>> effect = class_7923.field_41174.method_10223(name);
/* 293 */     if (effect.isEmpty()) return Value.bool(false);
/*     */     
/* 295 */     class_1293 effectInstance = MeteorClient.mc.field_1724.method_6112((class_6880)effect.get());
/* 296 */     return Value.bool((effectInstance != null));
/*     */   }
/*     */   
/*     */   private static Value getPotionEffect(Starscript ss, int argCount) {
/* 300 */     if (argCount < 1) ss.error("player.get_potion_effect() requires 1 argument, got %d.", new Object[] { Integer.valueOf(argCount) }); 
/* 301 */     if (MeteorClient.mc.field_1724 == null) return Value.null_();
/*     */     
/* 303 */     class_2960 name = popIdentifier(ss, "First argument to player.get_potion_effect() needs to a string.");
/*     */     
/* 305 */     Optional<class_6880.class_6883<class_1291>> effect = class_7923.field_41174.method_10223(name);
/* 306 */     if (effect.isEmpty()) return Value.null_();
/*     */     
/* 308 */     class_1293 effectInstance = MeteorClient.mc.field_1724.method_6112((class_6880)effect.get());
/* 309 */     if (effectInstance == null) return Value.null_();
/*     */     
/* 311 */     return wrap(effectInstance);
/*     */   }
/*     */   
/*     */   private static Value getStat(Starscript ss, int argCount) {
/* 315 */     if (argCount < 1) ss.error("player.get_stat() requires 1 argument, got %d.", new Object[] { Integer.valueOf(argCount) }); 
/* 316 */     if (MeteorClient.mc.field_1724 == null) return Value.number(0.0D);
/*     */     
/* 318 */     long time = System.currentTimeMillis();
/* 319 */     if ((time - lastRequestedStatsTime) / 1000.0D >= 1.0D && MeteorClient.mc.method_1562() != null) {
/* 320 */       MeteorClient.mc.method_1562().method_52787((class_2596)new class_2799(class_2799.class_2800.field_12775));
/* 321 */       lastRequestedStatsTime = time;
/*     */     } 
/*     */     
/* 324 */     String type = (argCount > 1) ? ss.popString("First argument to player.get_stat() needs to be a string.") : "custom";
/* 325 */     class_2960 name = popIdentifier(ss, ((argCount > 1) ? "Second" : "First") + " argument to player.get_stat() needs to be a string.");
/*     */     
/* 327 */     switch (type) { case "mined": 
/*     */       case "crafted": 
/*     */       case "used": 
/*     */       case "broken": 
/*     */       case "picked_up": 
/*     */       case "dropped": 
/*     */       case "killed": 
/*     */       case "killed_by":
/*     */       
/*     */       case "custom":
/* 337 */         name = (class_2960)class_7923.field_41183.method_63535(name);
/* 338 */         if (name != null);
/*     */       default:
/* 340 */         break; }  class_3445<?> stat = null;
/*     */ 
/*     */     
/* 343 */     return Value.number((stat != null) ? MeteorClient.mc.field_1724.method_3143().method_15025(stat) : 0.0D);
/*     */   }
/*     */   
/*     */   private static Value getModuleInfo(Starscript ss, int argCount) {
/* 347 */     if (argCount != 1) ss.error("meteor.get_module_info() requires 1 argument, got %d.", new Object[] { Integer.valueOf(argCount) });
/*     */     
/* 349 */     Module module = Modules.get().get(ss.popString("First argument to meteor.get_module_info() needs to be a string."));
/* 350 */     if (module != null && module.isActive()) {
/* 351 */       String info = module.getInfoString();
/* 352 */       return Value.string((info == null) ? "" : info);
/*     */     } 
/*     */     
/* 355 */     return Value.string("");
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
/*     */   private static Value getModuleSetting(Starscript ss, int argCount) {
/*     */     // Byte code:
/*     */     //   0: iload_1
/*     */     //   1: iconst_2
/*     */     //   2: if_icmpeq -> 23
/*     */     //   5: aload_0
/*     */     //   6: ldc_w 'meteor.get_module_setting() requires 2 arguments, got %d.'
/*     */     //   9: iconst_1
/*     */     //   10: anewarray java/lang/Object
/*     */     //   13: dup
/*     */     //   14: iconst_0
/*     */     //   15: iload_1
/*     */     //   16: invokestatic valueOf : (I)Ljava/lang/Integer;
/*     */     //   19: aastore
/*     */     //   20: invokevirtual error : (Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   23: aload_0
/*     */     //   24: ldc_w 'Second argument to meteor.get_module_setting() needs to be a string.'
/*     */     //   27: invokevirtual popString : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   30: astore_2
/*     */     //   31: aload_0
/*     */     //   32: ldc_w 'First argument to meteor.get_module_setting() needs to be a string.'
/*     */     //   35: invokevirtual popString : (Ljava/lang/String;)Ljava/lang/String;
/*     */     //   38: astore_3
/*     */     //   39: invokestatic get : ()Lmeteordevelopment/meteorclient/systems/modules/Modules;
/*     */     //   42: aload_3
/*     */     //   43: invokevirtual get : (Ljava/lang/String;)Lmeteordevelopment/meteorclient/systems/modules/Module;
/*     */     //   46: astore #4
/*     */     //   48: aload #4
/*     */     //   50: ifnonnull -> 68
/*     */     //   53: aload_0
/*     */     //   54: ldc_w 'Unable to get module %s for meteor.get_module_setting()'
/*     */     //   57: iconst_1
/*     */     //   58: anewarray java/lang/Object
/*     */     //   61: dup
/*     */     //   62: iconst_0
/*     */     //   63: aload_3
/*     */     //   64: aastore
/*     */     //   65: invokevirtual error : (Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   68: aload #4
/*     */     //   70: getfield settings : Lmeteordevelopment/meteorclient/settings/Settings;
/*     */     //   73: aload_2
/*     */     //   74: invokevirtual get : (Ljava/lang/String;)Lmeteordevelopment/meteorclient/settings/Setting;
/*     */     //   77: astore #5
/*     */     //   79: aload #5
/*     */     //   81: ifnonnull -> 103
/*     */     //   84: aload_0
/*     */     //   85: ldc_w 'Unable to get setting %s for module %s for meteor.get_module_setting()'
/*     */     //   88: iconst_2
/*     */     //   89: anewarray java/lang/Object
/*     */     //   92: dup
/*     */     //   93: iconst_0
/*     */     //   94: aload_2
/*     */     //   95: aastore
/*     */     //   96: dup
/*     */     //   97: iconst_1
/*     */     //   98: aload_3
/*     */     //   99: aastore
/*     */     //   100: invokevirtual error : (Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   103: aload #5
/*     */     //   105: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   108: astore #6
/*     */     //   110: aload #6
/*     */     //   112: astore #7
/*     */     //   114: iconst_0
/*     */     //   115: istore #8
/*     */     //   117: aload #7
/*     */     //   119: iload #8
/*     */     //   121: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*     */     //   126: tableswitch default -> 236, -1 -> 236, 0 -> 160, 1 -> 178, 2 -> 197, 3 -> 215
/*     */     //   160: aload #7
/*     */     //   162: checkcast java/lang/Double
/*     */     //   165: astore #9
/*     */     //   167: aload #9
/*     */     //   169: invokevirtual doubleValue : ()D
/*     */     //   172: invokestatic number : (D)Lorg/meteordev/starscript/value/Value;
/*     */     //   175: goto -> 244
/*     */     //   178: aload #7
/*     */     //   180: checkcast java/lang/Integer
/*     */     //   183: astore #10
/*     */     //   185: aload #10
/*     */     //   187: invokevirtual intValue : ()I
/*     */     //   190: i2d
/*     */     //   191: invokestatic number : (D)Lorg/meteordev/starscript/value/Value;
/*     */     //   194: goto -> 244
/*     */     //   197: aload #7
/*     */     //   199: checkcast java/lang/Boolean
/*     */     //   202: astore #11
/*     */     //   204: aload #11
/*     */     //   206: invokevirtual booleanValue : ()Z
/*     */     //   209: invokestatic bool : (Z)Lorg/meteordev/starscript/value/Value;
/*     */     //   212: goto -> 244
/*     */     //   215: aload #7
/*     */     //   217: checkcast java/util/List
/*     */     //   220: astore #12
/*     */     //   222: aload #12
/*     */     //   224: invokeinterface size : ()I
/*     */     //   229: i2d
/*     */     //   230: invokestatic number : (D)Lorg/meteordev/starscript/value/Value;
/*     */     //   233: goto -> 244
/*     */     //   236: aload #6
/*     */     //   238: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   241: invokestatic string : (Ljava/lang/String;)Lorg/meteordev/starscript/value/Value;
/*     */     //   244: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #359	-> 0
/*     */     //   #361	-> 23
/*     */     //   #362	-> 31
/*     */     //   #363	-> 39
/*     */     //   #364	-> 48
/*     */     //   #365	-> 53
/*     */     //   #367	-> 68
/*     */     //   #368	-> 79
/*     */     //   #369	-> 84
/*     */     //   #371	-> 103
/*     */     //   #372	-> 110
/*     */     //   #373	-> 160
/*     */     //   #374	-> 178
/*     */     //   #375	-> 197
/*     */     //   #376	-> 215
/*     */     //   #377	-> 236
/*     */     //   #372	-> 244
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   167	11	9	d	Ljava/lang/Double;
/*     */     //   185	12	10	i	Ljava/lang/Integer;
/*     */     //   204	11	11	b	Ljava/lang/Boolean;
/*     */     //   222	14	12	list	Ljava/util/List;
/*     */     //   0	245	0	ss	Lorg/meteordev/starscript/Starscript;
/*     */     //   0	245	1	argCount	I
/*     */     //   31	214	2	settingName	Ljava/lang/String;
/*     */     //   39	206	3	moduleName	Ljava/lang/String;
/*     */     //   48	197	4	module	Lmeteordevelopment/meteorclient/systems/modules/Module;
/*     */     //   79	166	5	setting	Lmeteordevelopment/meteorclient/settings/Setting;
/*     */     //   110	135	6	value	Ljava/lang/Object;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   222	14	12	list	Ljava/util/List<*>;
/*     */     //   79	166	5	setting	Lmeteordevelopment/meteorclient/settings/Setting<*>;
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
/*     */   private static Value isModuleActive(Starscript ss, int argCount) {
/* 382 */     if (argCount != 1) ss.error("meteor.is_module_active() requires 1 argument, got %d.", new Object[] { Integer.valueOf(argCount) });
/*     */     
/* 384 */     Module module = Modules.get().get(ss.popString("First argument to meteor.is_module_active() needs to be a string."));
/* 385 */     return Value.bool((module != null && module.isActive()));
/*     */   }
/*     */   
/*     */   private static Value getItem(Starscript ss, int argCount) {
/* 389 */     if (argCount != 1) ss.error("player.get_item() requires 1 argument, got %d.", new Object[] { Integer.valueOf(argCount) });
/*     */     
/* 391 */     int i = (int)ss.popNumber("First argument to player.get_item() needs to be a number.");
/* 392 */     if (i < 0) ss.error("First argument to player.get_item() needs to be a non-negative integer.", new Object[] { Integer.valueOf(i) }); 
/* 393 */     return (MeteorClient.mc.field_1724 != null) ? wrap(MeteorClient.mc.field_1724.method_31548().method_5438(i)) : Value.null_();
/*     */   }
/*     */   
/*     */   private static Value countItems(Starscript ss, int argCount) {
/* 397 */     if (argCount != 1) ss.error("player.count_items() requires 1 argument, got %d.", new Object[] { Integer.valueOf(argCount) });
/*     */     
/* 399 */     String idRaw = ss.popString("First argument to player.count_items() needs to be a string.");
/* 400 */     class_2960 id = class_2960.method_12829(idRaw);
/* 401 */     if (id == null) return Value.number(0.0D);
/*     */     
/* 403 */     class_1792 item = (class_1792)class_7923.field_41178.method_63535(id);
/* 404 */     if (item == class_1802.field_8162 || MeteorClient.mc.field_1724 == null) return Value.number(0.0D);
/*     */     
/* 406 */     int count = 0;
/* 407 */     for (int i = 0; i < MeteorClient.mc.field_1724.method_31548().method_5439(); i++) {
/* 408 */       class_1799 itemStack = MeteorClient.mc.field_1724.method_31548().method_5438(i);
/* 409 */       if (itemStack.method_7909() == item) count += itemStack.method_7947();
/*     */     
/*     */     } 
/* 412 */     return Value.number(count);
/*     */   }
/*     */   
/*     */   private static Value getMeteorPrefix() {
/* 416 */     if (Config.get() == null) return Value.null_(); 
/* 417 */     return Value.string((String)(Config.get()).prefix.get());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Value baritoneProcess() {
/* 423 */     Optional<IBaritoneProcess> process = BaritoneAPI.getProvider().getPrimaryBaritone().getPathingControlManager().mostRecentInControl();
/* 424 */     return Value.string(process.isEmpty() ? "" : ((IBaritoneProcess)process.get()).displayName0());
/*     */   }
/*     */   
/*     */   private static Value baritoneProcessName() {
/* 428 */     Optional<IBaritoneProcess> process = BaritoneAPI.getProvider().getPrimaryBaritone().getPathingControlManager().mostRecentInControl();
/* 429 */     if (process.isEmpty()) return Value.string("");
/*     */     
/* 431 */     String className = ((IBaritoneProcess)process.get()).getClass().getSimpleName();
/* 432 */     if (className.endsWith("Process")) className = className.substring(0, className.length() - 7);
/*     */     
/* 434 */     SB.append(className);
/* 435 */     int i = 0;
/* 436 */     for (int j = 0; j < className.length(); j++) {
/* 437 */       if (j > 0 && Character.isUpperCase(className.charAt(j))) {
/* 438 */         SB.insert(i, ' ');
/* 439 */         i++;
/*     */       } 
/*     */       
/* 442 */       i++;
/*     */     } 
/*     */     
/* 445 */     String name = SB.toString();
/* 446 */     SB.setLength(0);
/* 447 */     return Value.string(name);
/*     */   }
/*     */ 
/*     */   
/*     */   private static Value baritoneETA() {
/* 452 */     if (MeteorClient.mc.field_1724 == null) return Value.number(0.0D); 
/* 453 */     Optional<Double> ticksTillGoal = BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().estimatedTicksToGoal();
/* 454 */     return ticksTillGoal.<Value>map(aDouble -> Value.number(aDouble.doubleValue() / 20.0D)).orElseGet(() -> Value.number(0.0D));
/*     */   }
/*     */   
/*     */   private static Value oppositeX(boolean camera) {
/* 458 */     double x = camera ? (MeteorClient.mc.field_1773.method_19418().method_71156()).field_1352 : ((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_23317() : 0.0D);
/* 459 */     Dimension dimension = PlayerUtils.getDimension();
/*     */     
/* 461 */     if (dimension == Dimension.Overworld) { x /= 8.0D; }
/* 462 */     else if (dimension == Dimension.Nether) { x *= 8.0D; }
/*     */     
/* 464 */     return Value.number(x);
/*     */   }
/*     */   
/*     */   private static Value oppositeZ(boolean camera) {
/* 468 */     double z = camera ? (MeteorClient.mc.field_1773.method_19418().method_71156()).field_1350 : ((MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_23321() : 0.0D);
/* 469 */     Dimension dimension = PlayerUtils.getDimension();
/*     */     
/* 471 */     if (dimension == Dimension.Overworld) { z /= 8.0D; }
/* 472 */     else if (dimension == Dimension.Nether) { z *= 8.0D; }
/*     */     
/* 474 */     return Value.number(z);
/*     */   }
/*     */   
/*     */   private static Value yaw(boolean camera) {
/*     */     float yaw;
/* 479 */     if (camera) { yaw = MeteorClient.mc.field_1773.method_19418().method_19330(); }
/* 480 */     else { yaw = (MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_36454() : 0.0F; }
/* 481 */      yaw %= 360.0F;
/*     */     
/* 483 */     if (yaw < 0.0F) yaw += 360.0F; 
/* 484 */     if (yaw > 180.0F) yaw -= 360.0F;
/*     */     
/* 486 */     return Value.number(yaw);
/*     */   }
/*     */   
/*     */   private static Value pitch(boolean camera) {
/*     */     float pitch;
/* 491 */     if (camera) { pitch = MeteorClient.mc.field_1773.method_19418().method_19329(); }
/* 492 */     else { pitch = (MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_36455() : 0.0F; }
/* 493 */      pitch %= 360.0F;
/*     */     
/* 495 */     if (pitch < 0.0F) pitch += 360.0F; 
/* 496 */     if (pitch > 180.0F) pitch -= 360.0F;
/*     */     
/* 498 */     return Value.number(pitch);
/*     */   }
/*     */   
/*     */   private static Value direction(boolean camera) {
/*     */     float yaw;
/* 503 */     if (camera) { yaw = MeteorClient.mc.field_1773.method_19418().method_19330(); }
/* 504 */     else { yaw = (MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_36454() : 0.0F; }
/*     */     
/* 506 */     return wrap(HorizontalDirection.get(yaw));
/*     */   }
/*     */   
/*     */   private static Value biome() {
/* 510 */     if (MeteorClient.mc.field_1724 == null || MeteorClient.mc.field_1687 == null) return Value.string("");
/*     */     
/* 512 */     BP.method_10102(MeteorClient.mc.field_1724.method_23317(), MeteorClient.mc.field_1724.method_23318(), MeteorClient.mc.field_1724.method_23321());
/* 513 */     return MeteorClient.mc.field_1687.method_30349().method_46759(class_7924.field_41236)
/* 514 */       .map(biomeRegistry -> {
/*     */           class_2960 id = biomeRegistry.method_10221(MeteorClient.mc.field_1687.method_23753((class_2338)BP).comp_349());
/*     */ 
/*     */           
/*     */           return (id == null) ? Value.string("Unknown") : Value.string(Arrays.<String>stream(id.method_12832().split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" ")));
/* 519 */         }).orElse(Value.string("Unknown"));
/*     */   }
/*     */   
/*     */   private static Value handOrOffhand() {
/* 523 */     if (MeteorClient.mc.field_1724 == null) return Value.null_();
/*     */     
/* 525 */     class_1799 itemStack = MeteorClient.mc.field_1724.method_6047();
/* 526 */     if (itemStack.method_7960()) itemStack = MeteorClient.mc.field_1724.method_6079();
/*     */     
/* 528 */     return (itemStack != null) ? wrap(itemStack) : Value.null_();
/*     */   }
/*     */   
/*     */   private static Value ping() {
/* 532 */     if (MeteorClient.mc.method_1562() == null || MeteorClient.mc.field_1724 == null) return Value.number(0.0D);
/*     */     
/* 534 */     class_640 playerListEntry = MeteorClient.mc.method_1562().method_2871(MeteorClient.mc.field_1724.method_5667());
/* 535 */     return Value.number((playerListEntry != null) ? playerListEntry.method_2959() : 0.0D);
/*     */   }
/*     */   
/*     */   private static Value baritoneDistanceToGoal() {
/* 539 */     Goal goal = BaritoneAPI.getProvider().getPrimaryBaritone().getPathingBehavior().getGoal();
/* 540 */     return Value.number((goal != null && MeteorClient.mc.field_1724 != null) ? goal.heuristic(MeteorClient.mc.field_1724.method_24515()) : 0.0D);
/*     */   }
/*     */   
/*     */   private static Value posString(boolean opposite, boolean camera) {
/*     */     class_243 pos;
/* 545 */     if (camera) { pos = MeteorClient.mc.field_1773.method_19418().method_71156(); }
/* 546 */     else { pos = (MeteorClient.mc.field_1724 != null) ? MeteorClient.mc.field_1724.method_73189() : class_243.field_1353; }
/*     */     
/* 548 */     double x = pos.field_1352;
/* 549 */     double z = pos.field_1350;
/*     */     
/* 551 */     if (opposite) {
/* 552 */       Dimension dimension = PlayerUtils.getDimension();
/*     */       
/* 554 */       if (dimension == Dimension.Overworld) {
/* 555 */         x /= 8.0D;
/* 556 */         z /= 8.0D;
/*     */       }
/* 558 */       else if (dimension == Dimension.Nether) {
/* 559 */         x *= 8.0D;
/* 560 */         z *= 8.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 564 */     return posString(x, pos.field_1351, z);
/*     */   }
/*     */   
/*     */   private static Value posString(double x, double y, double z) {
/* 568 */     return Value.string(String.format("X: %.0f Y: %.0f Z: %.0f", new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) }));
/*     */   }
/*     */   
/*     */   private static Value crosshairType() {
/* 572 */     if (MeteorClient.mc.field_1765 == null) return Value.string("miss");
/*     */     
/* 574 */     switch (MeteorClient.mc.field_1765.method_17783()) { default: throw new MatchException(null, null);case field_1333: case field_1332: case field_1331: break; }  return Value.string(
/*     */ 
/*     */         
/* 577 */         "entity");
/*     */   }
/*     */ 
/*     */   
/*     */   private static Value crosshairValue() {
/* 582 */     if (MeteorClient.mc.field_1687 == null || MeteorClient.mc.field_1765 == null) return Value.null_();
/*     */     
/* 584 */     if (MeteorClient.mc.field_1765.method_17783() == class_239.class_240.field_1333) return Value.string(""); 
/* 585 */     class_239 class_239 = MeteorClient.mc.field_1765; if (class_239 instanceof class_3965) { class_3965 hit = (class_3965)class_239; return wrap(hit.method_17777(), MeteorClient.mc.field_1687.method_8320(hit.method_17777())); }
/* 586 */      return wrap(((class_3966)MeteorClient.mc.field_1765).method_17782());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class_2960 popIdentifier(Starscript ss, String errorMessage) {
/*     */     try {
/* 593 */       return class_2960.method_60654(ss.popString(errorMessage));
/*     */     }
/* 595 */     catch (class_151 e) {
/* 596 */       ss.error(e.getMessage(), new Object[0]);
/* 597 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Value wrap(class_1799 itemStack) {
/* 604 */     String name = itemStack.method_7960() ? "" : Names.get(itemStack.method_7909());
/*     */     
/* 606 */     int durability = 0;
/* 607 */     if (!itemStack.method_7960() && itemStack.method_7963()) durability = itemStack.method_7936() - itemStack.method_7919();
/*     */     
/* 609 */     return Value.map((new ValueMap())
/* 610 */         .set("_toString", Value.string((itemStack.method_7947() <= 1) ? name : String.format("%s %dx", new Object[] { name, Integer.valueOf(itemStack.method_7947())
/* 611 */               }))).set("name", Value.string(name))
/* 612 */         .set("id", Value.string(class_7923.field_41178.method_10221(itemStack.method_7909()).toString()))
/* 613 */         .set("count", Value.number(itemStack.method_7947()))
/* 614 */         .set("durability", Value.number(durability))
/* 615 */         .set("max_durability", Value.number(itemStack.method_7936())));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Value wrap(class_2338 blockPos, class_2680 blockState) {
/* 620 */     return Value.map((new ValueMap())
/* 621 */         .set("_toString", Value.string(Names.get(blockState.method_26204())))
/* 622 */         .set("id", Value.string(class_7923.field_41175.method_10221(blockState.method_26204()).toString()))
/* 623 */         .set("pos", Value.map((new ValueMap())
/* 624 */             .set("_toString", posString(blockPos.method_10263(), blockPos.method_10264(), blockPos.method_10260()))
/* 625 */             .set("x", Value.number(blockPos.method_10263()))
/* 626 */             .set("y", Value.number(blockPos.method_10264()))
/* 627 */             .set("z", Value.number(blockPos.method_10260())))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Value wrap(class_1297 entity) {
/* 636 */     class_1309 e = (class_1309)entity;
/* 637 */     e = (class_1309)entity; return Value.map((new ValueMap()).set("_toString", Value.string(entity.method_5477().getString())).set("id", Value.string(class_7923.field_41177.method_10221(entity.method_5864()).toString())).set("health", Value.number((entity instanceof class_1309) ? e.method_6032() : 0.0D)).set("absorption", Value.number((entity instanceof class_1309) ? e.method_6067() : 0.0D))
/* 638 */         .set("pos", Value.map((new ValueMap())
/* 639 */             .set("_toString", posString(entity.method_23317(), entity.method_23318(), entity.method_23321()))
/* 640 */             .set("x", Value.number(entity.method_23317()))
/* 641 */             .set("y", Value.number(entity.method_23318()))
/* 642 */             .set("z", Value.number(entity.method_23321())))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Value wrap(HorizontalDirection dir) {
/* 648 */     return Value.map((new ValueMap())
/* 649 */         .set("_toString", Value.string(dir.name + " " + dir.name))
/* 650 */         .set("name", Value.string(dir.name))
/* 651 */         .set("axis", Value.string(dir.axis)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Value wrap(class_1293 effectInstance) {
/* 656 */     return Value.map((new ValueMap())
/* 657 */         .set("duration", effectInstance.method_5584())
/* 658 */         .set("level", (effectInstance.method_5578() + 1)));
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\MeteorStarscript.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */