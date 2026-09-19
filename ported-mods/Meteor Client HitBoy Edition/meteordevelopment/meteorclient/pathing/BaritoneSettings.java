/*     */ package meteordevelopment.meteorclient.pathing;
/*     */ import baritone.api.BaritoneAPI;
/*     */ import baritone.api.Settings;
/*     */ import java.awt.Color;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.settings.BlockListSetting;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.ColorSetting;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.ItemListSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.settings.Settings;
/*     */ import meteordevelopment.meteorclient.settings.StringSetting;
/*     */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*     */ 
/*     */ public class BaritoneSettings implements IPathManager.ISettings {
/*  22 */   private final Settings settings = new Settings();
/*     */   private Setting<Boolean> walkOnWater;
/*     */   private Setting<Boolean> walkOnLava;
/*     */   private Setting<Boolean> step;
/*     */   private Setting<Boolean> noFall;
/*  27 */   private static final Map<String, Double> SETTING_MAX_VALUES = new HashMap<>(); private static Map<String, String> descriptions;
/*     */   
/*     */   public BaritoneSettings() {
/*  30 */     createWrappers();
/*     */   }
/*     */ 
/*     */   
/*     */   public Settings get() {
/*  35 */     return this.settings;
/*     */   }
/*     */ 
/*     */   
/*     */   public Setting<Boolean> getWalkOnWater() {
/*  40 */     return this.walkOnWater;
/*     */   }
/*     */ 
/*     */   
/*     */   public Setting<Boolean> getWalkOnLava() {
/*  45 */     return this.walkOnLava;
/*     */   }
/*     */ 
/*     */   
/*     */   public Setting<Boolean> getStep() {
/*  50 */     return this.step;
/*     */   }
/*     */ 
/*     */   
/*     */   public Setting<Boolean> getNoFall() {
/*  55 */     return this.noFall;
/*     */   }
/*     */ 
/*     */   
/*     */   public void save() {
/*  60 */     SettingsUtil.save(BaritoneAPI.getSettings());
/*     */   }
/*     */   
/*     */   static {
/*  64 */     SETTING_MAX_VALUES.put("pathCutoffFactor", Double.valueOf(1.0D));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createWrappers() {
/*  71 */     SettingGroup sgBool = this.settings.createGroup("Checkboxes");
/*  72 */     SettingGroup sgDouble = this.settings.createGroup("Numbers");
/*  73 */     SettingGroup sgInt = this.settings.createGroup("Whole Numbers");
/*  74 */     SettingGroup sgString = this.settings.createGroup("Strings");
/*  75 */     SettingGroup sgColor = this.settings.createGroup("Colors");
/*     */     
/*  77 */     SettingGroup sgBlockLists = this.settings.createGroup("Block Lists");
/*  78 */     SettingGroup sgItemLists = this.settings.createGroup("Item Lists");
/*     */     
/*     */     try {
/*  81 */       Class<? extends Settings> klass = (Class)BaritoneAPI.getSettings().getClass();
/*     */       
/*  83 */       for (Field field : klass.getDeclaredFields()) {
/*  84 */         if (!Modifier.isStatic(field.getModifiers())) {
/*     */           
/*  86 */           Object obj = field.get(BaritoneAPI.getSettings());
/*  87 */           if (obj instanceof Settings.Setting) { Settings.Setting setting = (Settings.Setting)obj;
/*     */             
/*  89 */             Object value = setting.value;
/*     */             
/*  91 */             if (value instanceof Boolean) {
/*  92 */               Setting<Boolean> wrapper = sgBool.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  93 */                   .name(setting.getName()))
/*  94 */                   .description(getDescription(setting.getName())))
/*  95 */                   .defaultValue(Boolean.valueOf(((Boolean)setting.defaultValue).booleanValue())))
/*  96 */                   .onChanged(aBoolean -> setting.value = aBoolean))
/*  97 */                   .onModuleActivated(booleanSetting -> booleanSetting.set(setting.value)))
/*  98 */                   .build());
/*     */ 
/*     */               
/* 101 */               switch (wrapper.name) { case "assumeWalkOnWater":
/* 102 */                   this.walkOnWater = wrapper; break;
/* 103 */                 case "assumeWalkOnLava": this.walkOnLava = wrapper; break;
/* 104 */                 case "assumeStep": this.step = wrapper;
/*     */                   break; }
/*     */             
/* 107 */             } else if (value instanceof Double) {
/* 108 */               sgDouble.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 109 */                   .name(setting.getName()))
/* 110 */                   .description(getDescription(setting.getName())))
/* 111 */                   .defaultValue(((Double)setting.defaultValue).doubleValue())
/* 112 */                   .max(((Double)SETTING_MAX_VALUES.getOrDefault(setting.getName(), Double.valueOf(10.0D))).doubleValue())
/* 113 */                   .sliderMax(((Double)SETTING_MAX_VALUES.getOrDefault(setting.getName(), Double.valueOf(10.0D))).doubleValue())
/* 114 */                   .onChanged(aDouble -> setting.value = aDouble))
/* 115 */                   .onModuleActivated(doubleSetting -> doubleSetting.set(setting.value)))
/* 116 */                   .build());
/*     */             
/*     */             }
/* 119 */             else if (value instanceof Float) {
/* 120 */               sgDouble.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 121 */                   .name(setting.getName()))
/* 122 */                   .description(getDescription(setting.getName())))
/* 123 */                   .defaultValue(((Float)setting.defaultValue).doubleValue())
/* 124 */                   .max(((Double)SETTING_MAX_VALUES.getOrDefault(setting.getName(), Double.valueOf(10.0D))).doubleValue())
/* 125 */                   .sliderMax(((Double)SETTING_MAX_VALUES.getOrDefault(setting.getName(), Double.valueOf(10.0D))).doubleValue())
/* 126 */                   .onChanged(aDouble -> setting.value = Float.valueOf(aDouble.floatValue())))
/* 127 */                   .onModuleActivated(doubleSetting -> doubleSetting.set(Double.valueOf(((Float)setting.value).doubleValue()))))
/* 128 */                   .build());
/*     */             
/*     */             }
/* 131 */             else if (value instanceof Integer) {
/* 132 */               Setting<Integer> wrapper = sgInt.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/* 133 */                   .name(setting.getName()))
/* 134 */                   .description(getDescription(setting.getName())))
/* 135 */                   .defaultValue(Integer.valueOf(((Integer)setting.defaultValue).intValue())))
/* 136 */                   .onChanged(integer -> setting.value = integer))
/* 137 */                   .onModuleActivated(integerSetting -> integerSetting.set(setting.value)))
/* 138 */                   .build());
/*     */ 
/*     */               
/* 141 */               if (wrapper.name.equals("maxFallHeightNoWater")) {
/* 142 */                 this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 148 */                   .noFall = (Setting<Boolean>)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name(wrapper.name)).description(wrapper.description)).defaultValue(Boolean.valueOf(false))).onChanged(aBoolean -> wrapper.set(Integer.valueOf(aBoolean.booleanValue() ? 159159 : ((Integer)wrapper.getDefaultValue()).intValue())))).onModuleActivated(booleanSetting -> booleanSetting.set(Boolean.valueOf((((Integer)wrapper.get()).intValue() >= 255))))).build();
/*     */               }
/*     */             }
/* 151 */             else if (value instanceof Long) {
/* 152 */               sgInt.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/* 153 */                   .name(setting.getName()))
/* 154 */                   .description(getDescription(setting.getName())))
/* 155 */                   .defaultValue(Integer.valueOf(((Long)setting.defaultValue).intValue())))
/* 156 */                   .onChanged(integer -> setting.value = Long.valueOf(integer.longValue())))
/* 157 */                   .onModuleActivated(integerSetting -> integerSetting.set(Integer.valueOf(((Long)setting.value).intValue()))))
/* 158 */                   .build());
/*     */             
/*     */             }
/* 161 */             else if (value instanceof String) {
/* 162 */               sgString.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)(new StringSetting.Builder())
/* 163 */                   .name(setting.getName()))
/* 164 */                   .description(getDescription(setting.getName())))
/* 165 */                   .defaultValue(setting.defaultValue))
/* 166 */                   .onChanged(string -> setting.value = string))
/* 167 */                   .onModuleActivated(stringSetting -> stringSetting.set(setting.value)))
/* 168 */                   .build());
/*     */             
/*     */             }
/* 171 */             else if (value instanceof Color) {
/* 172 */               Color c = (Color)setting.value;
/*     */               
/* 174 */               sgColor.add((Setting)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)((ColorSetting.Builder)(new ColorSetting.Builder())
/* 175 */                   .name(setting.getName()))
/* 176 */                   .description(getDescription(setting.getName())))
/* 177 */                   .defaultValue(new SettingColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()))
/* 178 */                   .onChanged(color -> setting.value = new Color(color.r, color.g, color.b, color.a)))
/* 179 */                   .onModuleActivated(colorSetting -> colorSetting.set(new SettingColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()))))
/* 180 */                   .build());
/*     */             
/*     */             }
/* 183 */             else if (value instanceof List) {
/* 184 */               Type listType = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
/* 185 */               Type type = ((ParameterizedType)listType).getActualTypeArguments()[0];
/*     */               
/* 187 */               if (type == class_2248.class) {
/* 188 */                 sgBlockLists.add((Setting)((BlockListSetting.Builder)((BlockListSetting.Builder)((BlockListSetting.Builder)((BlockListSetting.Builder)((BlockListSetting.Builder)(new BlockListSetting.Builder())
/* 189 */                     .name(setting.getName()))
/* 190 */                     .description(getDescription(setting.getName())))
/* 191 */                     .defaultValue(setting.defaultValue))
/* 192 */                     .onChanged(blockList -> setting.value = blockList))
/* 193 */                     .onModuleActivated(blockListSetting -> blockListSetting.set(setting.value)))
/* 194 */                     .build());
/*     */               
/*     */               }
/* 197 */               else if (type == class_1792.class) {
/* 198 */                 sgItemLists.add((Setting)((ItemListSetting.Builder)((ItemListSetting.Builder)((ItemListSetting.Builder)((ItemListSetting.Builder)((ItemListSetting.Builder)(new ItemListSetting.Builder())
/* 199 */                     .name(setting.getName()))
/* 200 */                     .description(getDescription(setting.getName())))
/* 201 */                     .defaultValue(setting.defaultValue))
/* 202 */                     .onChanged(itemList -> setting.value = itemList))
/* 203 */                     .onModuleActivated(itemListSetting -> itemListSetting.set(setting.value)))
/* 204 */                     .build());
/*     */               } 
/*     */             }  }
/*     */         
/*     */         } 
/*     */       } 
/* 210 */     } catch (IllegalAccessException e) {
/* 211 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addDescription(String settingName, String description) {
/* 220 */     descriptions.put(settingName.toLowerCase(), description);
/*     */   }
/*     */   
/*     */   private static String getDescription(String settingName) {
/* 224 */     if (descriptions == null) loadDescriptions();
/*     */     
/* 226 */     return descriptions.get(settingName.toLowerCase());
/*     */   }
/*     */   
/*     */   private static void loadDescriptions() {
/* 230 */     descriptions = new HashMap<>();
/* 231 */     addDescription("acceptableThrowawayItems", "Blocks that Baritone is allowed to place (as throwaway, for sneak bridging, pillaring, etc.)");
/* 232 */     addDescription("allowBreak", "Allow Baritone to break blocks");
/* 233 */     addDescription("allowBreakAnyway", "Blocks that baritone will be allowed to break even with allowBreak set to false");
/* 234 */     addDescription("allowDiagonalAscend", "Allow diagonal ascending");
/* 235 */     addDescription("allowDiagonalDescend", "Allow descending diagonally");
/* 236 */     addDescription("allowDownward", "Allow mining the block directly beneath its feet");
/* 237 */     addDescription("allowInventory", "Allow Baritone to move items in your inventory to your hotbar");
/* 238 */     addDescription("allowJumpAt256", "If true, parkour is allowed to make jumps when standing on blocks at the maximum height, so player feet is y=256");
/* 239 */     addDescription("allowOnlyExposedOres", "This will only allow baritone to mine exposed ores, can be used to stop ore obfuscators on servers that use them.");
/* 240 */     addDescription("allowOnlyExposedOresDistance", "When allowOnlyExposedOres is enabled this is the distance around to search.");
/* 241 */     addDescription("allowOvershootDiagonalDescend", "Is it okay to sprint through a descend followed by a diagonal? The player overshoots the landing, but not enough to fall off.");
/* 242 */     addDescription("allowParkour", "You know what it is");
/* 243 */     addDescription("allowParkourAscend", "This should be monetized it's so good");
/* 244 */     addDescription("allowParkourPlace", "Actually pretty reliable.");
/* 245 */     addDescription("allowPlace", "Allow Baritone to place blocks");
/* 246 */     addDescription("allowSprint", "Allow Baritone to sprint");
/* 247 */     addDescription("allowVines", "Enables some more advanced vine features.");
/* 248 */     addDescription("allowWalkOnBottomSlab", "Slab behavior is complicated, disable this for higher path reliability.");
/* 249 */     addDescription("allowWaterBucketFall", "Allow Baritone to fall arbitrary distances and place a water bucket beneath it.");
/* 250 */     addDescription("antiCheatCompatibility", "Will cause some minor behavioral differences to ensure that Baritone works on anticheats.");
/* 251 */     addDescription("assumeExternalAutoTool", "Disable baritone's auto-tool at runtime, but still assume that another mod will provide auto tool functionality");
/* 252 */     addDescription("assumeSafeWalk", "Assume safe walk functionality; don't sneak on a backplace traverse.");
/* 253 */     addDescription("assumeStep", "Assume step functionality; don't jump on an Ascend.");
/* 254 */     addDescription("assumeWalkOnLava", "If you have Fire Resistance and Jesus then I guess you could turn this on lol");
/* 255 */     addDescription("assumeWalkOnWater", "Allow Baritone to assume it can walk on still water just like any other block.");
/* 256 */     addDescription("autoTool", "Automatically select the best available tool");
/* 257 */     addDescription("avoidance", "Toggle the following 4 settings");
/* 258 */     addDescription("avoidBreakingMultiplier", "this multiplies the break speed, if set above 1 it's \"encourage breaking\" instead");
/* 259 */     addDescription("avoidUpdatingFallingBlocks", "If this setting is true, Baritone will never break a block that is adjacent to an unsupported falling block.");
/* 260 */     addDescription("axisHeight", "The \"axis\" command (aka GoalAxis) will go to a axis, or diagonal axis, at this Y level.");
/* 261 */     addDescription("backfill", "Fill in blocks behind you (stealth +100)");
/* 262 */     addDescription("backtrackCostFavoringCoefficient", "Set to 1.0 to effectively disable this feature");
/* 263 */     addDescription("blacklistClosestOnFailure", "When GetToBlockProcess or MineProcess fails to calculate a path, instead of just giving up, mark the closest instance of that block as \"unreachable\" and go towards the next closest.");
/* 264 */     addDescription("blockBreakAdditionalPenalty", "This is just a tiebreaker to make it less likely to break blocks if it can avoid it.");
/* 265 */     addDescription("blockPlacementPenalty", "It doesn't actually take twenty ticks to place a block, this cost is so high because we want to generally conserve blocks which might be limited.");
/* 266 */     addDescription("blockReachDistance", "Block reach distance");
/* 267 */     addDescription("blocksToAvoid", "Blocks that Baritone will attempt to avoid (Used in avoidance)");
/* 268 */     addDescription("blocksToAvoidBreaking", "blocks that baritone shouldn't break, but can if it needs to.");
/* 269 */     addDescription("blocksToDisallowBreaking", "Blocks that Baritone is not allowed to break");
/* 270 */     addDescription("breakCorrectBlockPenaltyMultiplier", "Multiply the cost of breaking a block that's correct in the builder's schematic by this coefficient");
/* 271 */     addDescription("breakFromAbove", "Allow standing above a block while mining it, in BuilderProcess");
/* 272 */     addDescription("builderTickScanRadius", "Distance to scan every tick for updates.");
/* 273 */     addDescription("buildIgnoreBlocks", "A list of blocks to be treated as if they're air.");
/* 274 */     addDescription("buildIgnoreDirection", "If this is true, the builder will ignore directionality of certain blocks like glazed terracotta.");
/* 275 */     addDescription("buildIgnoreExisting", "If this is true, the builder will treat all non-air blocks as correct.");
/* 276 */     addDescription("buildInLayers", "Don't consider the next layer in builder until the current one is done");
/* 277 */     addDescription("buildOnlySelection", "Only build the selected part of schematics");
/* 278 */     addDescription("buildRepeat", "How far to move before repeating the build.");
/* 279 */     addDescription("buildRepeatCount", "How many times to buildrepeat.");
/* 280 */     addDescription("buildRepeatSneaky", "Don't notify schematics that they are moved.");
/* 281 */     addDescription("buildSkipBlocks", "A list of blocks to be treated as correct.");
/* 282 */     addDescription("buildSubstitutes", "A mapping of blocks to blocks to be built instead");
/* 283 */     addDescription("buildValidSubstitutes", "A mapping of blocks to blocks treated as correct in their position.");
/* 284 */     addDescription("cachedChunksExpirySeconds", "Cached chunks (regardless of if they're in RAM or saved to disk) expire and are deleted after this number of seconds -1 to disable");
/* 285 */     addDescription("cachedChunksOpacity", "0.0f = not visible, fully transparent (instead of setting this to 0, turn off renderCachedChunks) 1.0f = fully opaque");
/* 286 */     addDescription("cancelOnGoalInvalidation", "Cancel the current path if the goal has changed, and the path originally ended in the goal but doesn't anymore.");
/* 287 */     addDescription("censorCoordinates", "Censor coordinates in goals and block positions");
/* 288 */     addDescription("censorRanCommands", "Censor arguments to ran commands, to hide, for example, coordinates to #goal");
/* 289 */     addDescription("chatControl", "Allow chat based control of Baritone.");
/* 290 */     addDescription("chatControlAnyway", "Some clients like Impact try to force chatControl to off, so here's a second setting to do it anyway");
/* 291 */     addDescription("chatDebug", "Print all the debug messages to chat");
/* 292 */     addDescription("chunkCaching", "The big one.");
/* 293 */     addDescription("colorBestPathSoFar", "The color of the best path so far");
/* 294 */     addDescription("colorBlocksToBreak", "The color of the blocks to break");
/* 295 */     addDescription("colorBlocksToPlace", "The color of the blocks to place");
/* 296 */     addDescription("colorBlocksToWalkInto", "The color of the blocks to walk into");
/* 297 */     addDescription("colorCurrentPath", "The color of the current path");
/* 298 */     addDescription("colorGoalBox", "The color of the goal box");
/* 299 */     addDescription("colorInvertedGoalBox", "The color of the goal box when it's inverted");
/* 300 */     addDescription("colorMostRecentConsidered", "The color of the path to the most recent considered node");
/* 301 */     addDescription("colorNextPath", "The color of the next path");
/* 302 */     addDescription("colorSelection", "The color of all selections");
/* 303 */     addDescription("colorSelectionPos1", "The color of the selection pos 1");
/* 304 */     addDescription("colorSelectionPos2", "The color of the selection pos 2");
/* 305 */     addDescription("considerPotionEffects", "For example, if you have Mining Fatigue or Haste, adjust the costs of breaking blocks accordingly.");
/* 306 */     addDescription("costHeuristic", "This is the big A* setting.");
/* 307 */     addDescription("costVerificationLookahead", "Stop 5 movements before anything that made the path COST_INF.");
/* 308 */     addDescription("cutoffAtLoadBoundary", "After calculating a path (potentially through cached chunks), artificially cut it off to just the part that is entirely within currently loaded chunks.");
/* 309 */     addDescription("desktopNotifications", "Desktop notifications");
/* 310 */     addDescription("disableCompletionCheck", "Turn this on if your exploration filter is enormous, you don't want it to check if it's done, and you are just fine with it just hanging on completion");
/* 311 */     addDescription("disconnectOnArrival", "Disconnect from the server upon arriving at your goal");
/* 312 */     addDescription("distanceTrim", "Trim incorrect positions too far away, helps performance but hurts reliability in very large schematics");
/* 313 */     addDescription("doBedWaypoints", "Allows baritone to save bed waypoints when interacting with beds");
/* 314 */     addDescription("doDeathWaypoints", "Allows baritone to save death waypoints");
/* 315 */     addDescription("echoCommands", "Echo commands to chat when they are run");
/* 316 */     addDescription("enterPortal", "When running a goto towards a nether portal block, walk all the way into the portal instead of stopping one block before.");
/* 317 */     addDescription("exploreChunkSetMinimumSize", "Take the 10 closest chunks, even if they aren't strictly tied for distance metric from origin.");
/* 318 */     addDescription("exploreForBlocks", "When GetToBlock or non-legit Mine doesn't know any locations for the desired block, explore randomly instead of giving up.");
/* 319 */     addDescription("exploreMaintainY", "Attempt to maintain Y coordinate while exploring");
/* 320 */     addDescription("extendCacheOnThreshold", "When the cache scan gives less blocks than the maximum threshold (but still above zero), scan the main world too.");
/* 321 */     addDescription("fadePath", "Start fading out the path at 20 movements ahead, and stop rendering it entirely 30 movements ahead.");
/* 322 */     addDescription("failureTimeoutMS", "Pathing can never take longer than this, even if that means failing to find any path at all");
/* 323 */     addDescription("followOffsetDirection", "The actual GoalNear is set in this direction from the entity you're following.");
/* 324 */     addDescription("followOffsetDistance", "The actual GoalNear is set this distance away from the entity you're following");
/* 325 */     addDescription("followRadius", "The radius (for the GoalNear) of how close to your target position you actually have to be");
/* 326 */     addDescription("forceInternalMining", "When mining block of a certain type, try to mine two at once instead of one.");
/* 327 */     addDescription("freeLook", "Move without having to force the client-sided rotations");
/* 328 */     addDescription("goalBreakFromAbove", "As well as breaking from above, set a goal to up and to the side of all blocks to break.");
/* 329 */     addDescription("goalRenderLineWidthPixels", "Line width of the goal when rendered, in pixels");
/* 330 */     addDescription("incorrectSize", "The set of incorrect blocks can never grow beyond this size");
/* 331 */     addDescription("internalMiningAirException", "Modification to the previous setting, only has effect if forceInternalMining is true If true, only apply the previous setting if the block adjacent to the goal isn't air.");
/* 332 */     addDescription("itemSaver", "Stop using tools just before they are going to break.");
/* 333 */     addDescription("itemSaverThreshold", "Durability to leave on the tool when using itemSaver");
/* 334 */     addDescription("jumpPenalty", "Additional penalty for hitting the space bar (ascend, pillar, or parkour) because it uses hunger");
/* 335 */     addDescription("layerHeight", "How high should the individual layers be?");
/* 336 */     addDescription("layerOrder", "false = build from bottom to top");
/* 337 */     addDescription("legitMine", "Disallow MineBehavior from using X-Ray to see where the ores are.");
/* 338 */     addDescription("legitMineIncludeDiagonals", "Magically see ores that are separated diagonally from existing ores.");
/* 339 */     addDescription("legitMineYLevel", "What Y level to go to for legit strip mining");
/* 340 */     addDescription("logAsToast", "Shows popup message in the upper right corner, similarly to when you make an advancement");
/* 341 */     addDescription("mapArtMode", "Build in map art mode, which makes baritone only care about the top block in each column");
/* 342 */     addDescription("maxCachedWorldScanCount", "After finding this many instances of the target block in the cache, it will stop expanding outward the chunk search.");
/* 343 */     addDescription("maxCostIncrease", "If a movement's cost increases by more than this amount between calculation and execution (due to changes in the environment / world), cancel and recalculate");
/* 344 */     addDescription("maxFallHeightBucket", "How far are you allowed to fall onto solid ground (with a water bucket)? It's not that reliable, so I've set it below what would kill an unarmored player (23)");
/* 345 */     addDescription("maxFallHeightNoWater", "How far are you allowed to fall onto solid ground (without a water bucket)? 3 won't deal any damage.");
/* 346 */     addDescription("maxPathHistoryLength", "If we are more than 300 movements into the current path, discard the oldest segments, as they are no longer useful");
/* 347 */     addDescription("mineDropLoiterDurationMSThanksLouca", "While mining, wait this number of milliseconds after mining an ore to see if it will drop an item instead of immediately going onto the next one");
/* 348 */     addDescription("mineGoalUpdateInterval", "Rescan for the goal once every 5 ticks.");
/* 349 */     addDescription("mineScanDroppedItems", "While mining, should it also consider dropped items of the correct type as a pathing destination (as well as ore blocks)?");
/* 350 */     addDescription("minimumImprovementRepropagation", "Don't repropagate cost improvements below 0.01 ticks.");
/* 351 */     addDescription("minYLevelWhileMining", "Sets the minimum y level whilst mining - set to 0 to turn off. if world has negative y values, subtract the min world height to get the value to put here");
/* 352 */     addDescription("mobAvoidanceCoefficient", "Set to 1.0 to effectively disable this feature");
/* 353 */     addDescription("mobAvoidanceRadius", "Distance to avoid mobs.");
/* 354 */     addDescription("mobSpawnerAvoidanceCoefficient", "Set to 1.0 to effectively disable this feature");
/* 355 */     addDescription("mobSpawnerAvoidanceRadius", "Distance to avoid mob spawners.");
/* 356 */     addDescription("movementTimeoutTicks", "If a movement takes this many ticks more than its initial cost estimate, cancel it");
/* 357 */     addDescription("notificationOnBuildFinished", "Desktop notification on build finished");
/* 358 */     addDescription("notificationOnExploreFinished", "Desktop notification on explore finished");
/* 359 */     addDescription("notificationOnFarmFail", "Desktop notification on farm fail");
/* 360 */     addDescription("notificationOnMineFail", "Desktop notification on mine fail");
/* 361 */     addDescription("notificationOnPathComplete", "Desktop notification on path complete");
/* 362 */     addDescription("notifier", "The function that is called when Baritone will send a desktop notification.");
/* 363 */     addDescription("okIfAir", "A list of blocks to become air");
/* 364 */     addDescription("okIfWater", "Override builder's behavior to not attempt to correct blocks that are currently water");
/* 365 */     addDescription("overshootTraverse", "If we overshoot a traverse and end up one block beyond the destination, mark it as successful anyway.");
/* 366 */     addDescription("pathCutoffFactor", "Static cutoff factor.");
/* 367 */     addDescription("pathCutoffMinimumLength", "Only apply static cutoff for paths of at least this length (in terms of number of movements)");
/* 368 */     addDescription("pathHistoryCutoffAmount", "If the current path is too long, cut off this many movements from the beginning.");
/* 369 */     addDescription("pathingMapDefaultSize", "Default size of the Long2ObjectOpenHashMap used in pathing");
/* 370 */     addDescription("pathingMapLoadFactor", "Load factor coefficient for the Long2ObjectOpenHashMap used in pathing");
/* 371 */     addDescription("pathingMaxChunkBorderFetch", "The maximum number of times it will fetch outside loaded or cached chunks before assuming that pathing has reached the end of the known area, and should therefore stop.");
/* 372 */     addDescription("pathRenderLineWidthPixels", "Line width of the path when rendered, in pixels");
/* 373 */     addDescription("pathThroughCachedOnly", "Exclusively use cached chunks for pathing");
/* 374 */     addDescription("pauseMiningForFallingBlocks", "When breaking blocks for a movement, wait until all falling blocks have settled before continuing");
/* 375 */     addDescription("planAheadFailureTimeoutMS", "Planning ahead while executing a segment can never take longer than this, even if that means failing to find any path at all");
/* 376 */     addDescription("planAheadPrimaryTimeoutMS", "Planning ahead while executing a segment ends after this amount of time, but only if a path has been found");
/* 377 */     addDescription("planningTickLookahead", "Start planning the next path once the remaining movements tick estimates sum up to less than this value");
/* 378 */     addDescription("preferSilkTouch", "Always prefer silk touch tools over regular tools.");
/* 379 */     addDescription("prefix", "The command prefix for chat control");
/* 380 */     addDescription("prefixControl", "Whether or not to allow you to run Baritone commands with the prefix");
/* 381 */     addDescription("primaryTimeoutMS", "Pathing ends after this amount of time, but only if a path has been found");
/* 382 */     addDescription("pruneRegionsFromRAM", "On save, delete from RAM any cached regions that are more than 1024 blocks away from the player");
/* 383 */     addDescription("randomLooking", "How many degrees to randomize the pitch and yaw every tick.");
/* 384 */     addDescription("randomLooking113", "How many degrees to randomize the yaw every tick. Set to 0 to disable");
/* 385 */     addDescription("renderCachedChunks", "Render cached chunks as semitransparent.");
/* 386 */     addDescription("renderGoal", "Render the goal");
/* 387 */     addDescription("renderGoalAnimated", "Render the goal as a sick animated thingy instead of just a box (also controls animation of GoalXZ if renderGoalXZBeacon is enabled)");
/* 388 */     addDescription("renderGoalIgnoreDepth", "Ignore depth when rendering the goal");
/* 389 */     addDescription("renderGoalXZBeacon", "Renders X/Z type Goals with the vanilla beacon beam effect.");
/* 390 */     addDescription("renderPath", "Render the path");
/* 391 */     addDescription("renderPathAsLine", "Render the path as a line instead of a frickin thingy");
/* 392 */     addDescription("renderPathIgnoreDepth", "Ignore depth when rendering the path");
/* 393 */     addDescription("renderSelection", "Render selections");
/* 394 */     addDescription("renderSelectionBoxes", "Render selection boxes");
/* 395 */     addDescription("renderSelectionBoxesIgnoreDepth", "Ignore depth when rendering the selection boxes (to break, to place, to walk into)");
/* 396 */     addDescription("renderSelectionCorners", "Render selection corners");
/* 397 */     addDescription("renderSelectionIgnoreDepth", "Ignore depth when rendering selections");
/* 398 */     addDescription("repackOnAnyBlockChange", "Whenever a block changes, repack the whole chunk that it's in");
/* 399 */     addDescription("replantCrops", "Replant normal Crops while farming and leave cactus and sugarcane to regrow");
/* 400 */     addDescription("replantNetherWart", "Replant nether wart while farming.");
/* 401 */     addDescription("rightClickContainerOnArrival", "When running a goto towards a container block (chest, ender chest, furnace, etc), right click and open it once you arrive.");
/* 402 */     addDescription("rightClickSpeed", "How many ticks between right clicks are allowed.");
/* 403 */     addDescription("schematicFallbackExtension", "The fallback used by the build command when no extension is specified.");
/* 404 */     addDescription("schematicOrientationX", "When this setting is true, build a schematic with the highest X coordinate being the origin, instead of the lowest");
/* 405 */     addDescription("schematicOrientationY", "When this setting is true, build a schematic with the highest Y coordinate being the origin, instead of the lowest");
/* 406 */     addDescription("schematicOrientationZ", "When this setting is true, build a schematic with the highest Z coordinate being the origin, instead of the lowest");
/* 407 */     addDescription("selectionLineWidth", "Line width of the goal when rendered, in pixels");
/* 408 */     addDescription("selectionOpacity", "The opacity of the selection.");
/* 409 */     addDescription("shortBaritonePrefix", "Use a short Baritone prefix [B] instead of [Baritone] when logging to chat");
/* 410 */     addDescription("simplifyUnloadedYCoord", "If your goal is a GoalBlock in an unloaded chunk, assume it's far enough away that the Y coord doesn't matter yet, and replace it with a GoalXZ to the same place before calculating a path.");
/* 411 */     addDescription("skipFailedLayers", "If a layer is unable to be constructed, just skip it.");
/* 412 */     addDescription("slowPath", "For debugging, consider nodes much much slower");
/* 413 */     addDescription("slowPathTimeDelayMS", "Milliseconds between each node");
/* 414 */     addDescription("slowPathTimeoutMS", "The alternative timeout number when slowPath is on");
/* 415 */     addDescription("splicePath", "When a new segment is calculated that doesn't overlap with the current one, but simply begins where the current segment ends, splice it on and make a longer combined path.");
/* 416 */     addDescription("sprintAscends", "Sprint and jump a block early on ascends wherever possible");
/* 417 */     addDescription("sprintInWater", "Continue sprinting while in water");
/* 418 */     addDescription("startAtLayer", "Start building the schematic at a specific layer.");
/* 419 */     addDescription("toaster", "The function that is called when Baritone will show a toast.");
/* 420 */     addDescription("toastTimer", "The time of how long the message in the pop-up will display");
/* 421 */     addDescription("useSwordToMine", "Use sword to mine.");
/* 422 */     addDescription("verboseCommandExceptions", "Print out ALL command exceptions as a stack trace to stdout, even simple syntax errors");
/* 423 */     addDescription("walkOnWaterOnePenalty", "Walking on water uses up hunger really quick, so penalize it");
/* 424 */     addDescription("walkWhileBreaking", "Don't stop walking forward when you need to break blocks in your way");
/* 425 */     addDescription("worldExploringChunkOffset", "While exploring the world, offset the closest unloaded chunk by this much in both axes.");
/* 426 */     addDescription("yLevelBoxSize", "The size of the box that is rendered when the current goal is a GoalYLevel");
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\pathing\BaritoneSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */