/*    */ package meteordevelopment.meteorclient.systems.hud.elements;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*    */ import meteordevelopment.meteorclient.systems.hud.HudElementInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MeteorTextHud
/*    */ {
/* 12 */   public static final HudElementInfo<TextHud> INFO = new HudElementInfo(Hud.GROUP, "text", "Displays arbitrary text with Starscript.", MeteorTextHud::create);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 36 */     addPreset("Empty", null);
/* 37 */   } public static final HudElementInfo<TextHud>.Preset FPS = addPreset("FPS", "FPS: #1{fps}", 0);
/* 38 */   public static final HudElementInfo<TextHud>.Preset TPS = addPreset("TPS", "TPS: #1{round(server.tps, 1)}");
/* 39 */   public static final HudElementInfo<TextHud>.Preset PING = addPreset("Ping", "Ping: #1{ping}");
/* 40 */   public static final HudElementInfo<TextHud>.Preset SPEED = addPreset("Speed", "Speed: #1{round(player.speed, 1)}", 0);
/* 41 */   public static final HudElementInfo<TextHud>.Preset GAME_MODE = addPreset("Game mode", "Game mode: #1{player.gamemode}", 0);
/* 42 */   public static final HudElementInfo<TextHud>.Preset DURABILITY = addPreset("Durability", "Durability: #1{player.hand_or_offhand.durability}");
/* 43 */   public static final HudElementInfo<TextHud>.Preset POSITION = addPreset("Position", "Pos: #1{floor(camera.pos.x)}, {floor(camera.pos.y)}, {floor(camera.pos.z)}", 0);
/* 44 */   public static final HudElementInfo<TextHud>.Preset OPPOSITE_POSITION = addPreset("Opposite Position", "{player.opposite_dimension != \"End\" ? player.opposite_dimension + \":\" : \"\"} #1{player.opposite_dimension != \"End\" ? \"\" + floor(camera.opposite_dim_pos.x) + \", \" + floor(camera.opposite_dim_pos.y) + \", \" + floor(camera.opposite_dim_pos.z) : \"\"}", 0);
/* 45 */   public static final HudElementInfo<TextHud>.Preset LOOKING_AT = addPreset("Looking at", "Looking at: #1{crosshair_target.value}", 0);
/* 46 */   public static final HudElementInfo<TextHud>.Preset LOOKING_AT_WITH_POSITION = addPreset("Looking at with position", "Looking at: #1{crosshair_target.value} {crosshair_target.type != \"miss\" ? \"(\" + \"\" + floor(crosshair_target.value.pos.x) + \", \" + floor(crosshair_target.value.pos.y) + \", \" + floor(crosshair_target.value.pos.z) + \")\" : \"\"}", 0);
/* 47 */   public static final HudElementInfo<TextHud>.Preset BREAKING_PROGRESS = addPreset("Breaking progress", "Breaking progress: #1{round(player.breaking_progress * 100)}%", 0);
/* 48 */   public static final HudElementInfo<TextHud>.Preset SERVER = addPreset("Server", "Server: #1{server}");
/* 49 */   public static final HudElementInfo<TextHud>.Preset BIOME = addPreset("Biome", "Biome: #1{player.biome}", 0);
/* 50 */   public static final HudElementInfo<TextHud>.Preset WORLD_TIME = addPreset("World time", "Time: #1{server.time}");
/* 51 */   public static final HudElementInfo<TextHud>.Preset REAL_TIME = addPreset("Real time", "Time: #1{time}");
/* 52 */   public static final HudElementInfo<TextHud>.Preset ROTATION = addPreset("Rotation", "{camera.direction} #1({round(camera.yaw, 1)}, {round(camera.pitch, 1)})", 0);
/* 53 */   public static final HudElementInfo<TextHud>.Preset MODULE_ENABLED = addPreset("Module enabled", "Kill Aura: {meteor.is_module_active(\"kill-aura\") ? #2 \"ON\" : #3 \"OFF\"}", 0);
/* 54 */   public static final HudElementInfo<TextHud>.Preset MODULE_ENABLED_WITH_INFO = addPreset("Module enabled with info", "Kill Aura: {meteor.is_module_active(\"kill-aura\") ? #2 \"ON\" : #3 \"OFF\"} #1{meteor.get_module_info(\"kill-aura\")}", 0);
/* 55 */   public static final HudElementInfo<TextHud>.Preset WATERMARK = addPreset("Watermark", "{meteor.name} #1{meteor.version}");
/* 56 */   public static final HudElementInfo<TextHud>.Preset BARITONE = addPreset("Baritone", "Baritone: #1{baritone.process_name}");
/*    */ 
/*    */   
/*    */   private static TextHud create() {
/* 60 */     return new TextHud(INFO);
/*    */   }
/*    */   
/*    */   private static HudElementInfo<TextHud>.Preset addPreset(String title, String text, int updateDelay) {
/* 64 */     return INFO.addPreset(title, textHud -> {
/*    */           if (text != null)
/*    */             textHud.text.set(text); 
/*    */           if (updateDelay != -1)
/*    */             textHud.updateDelay.set(Integer.valueOf(updateDelay)); 
/*    */         });
/*    */   } private static HudElementInfo<TextHud>.Preset addPreset(String title, String text) {
/* 71 */     return addPreset(title, text, -1);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\elements\MeteorTextHud.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */