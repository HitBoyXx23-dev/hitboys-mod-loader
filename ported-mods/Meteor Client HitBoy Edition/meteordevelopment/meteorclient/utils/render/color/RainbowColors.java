/*    */ package meteordevelopment.meteorclient.utils.render.color;
/*    */ 
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.config.Config;
/*    */ import meteordevelopment.meteorclient.systems.waypoints.Waypoint;
/*    */ import meteordevelopment.meteorclient.systems.waypoints.Waypoints;
/*    */ import meteordevelopment.meteorclient.utils.PostInit;
/*    */ import meteordevelopment.meteorclient.utils.misc.UnorderedArrayList;
/*    */ import meteordevelopment.orbit.EventHandler;
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
/*    */ public class RainbowColors
/*    */ {
/* 27 */   private static final List<Setting<SettingColor>> colorSettings = (List<Setting<SettingColor>>)new UnorderedArrayList();
/* 28 */   private static final List<Setting<List<SettingColor>>> colorListSettings = (List<Setting<List<SettingColor>>>)new UnorderedArrayList();
/*    */   
/* 30 */   private static final List<SettingColor> colors = (List<SettingColor>)new UnorderedArrayList();
/* 31 */   private static final List<Runnable> listeners = (List<Runnable>)new UnorderedArrayList();
/*    */   
/* 33 */   public static final RainbowColor GLOBAL = new RainbowColor();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @PostInit
/*    */   public static void init() {
/* 40 */     MeteorClient.EVENT_BUS.subscribe(RainbowColors.class);
/*    */   }
/*    */   
/*    */   public static void addSetting(Setting<SettingColor> setting) {
/* 44 */     colorSettings.add(setting);
/*    */   }
/*    */   
/*    */   public static void addSettingList(Setting<List<SettingColor>> setting) {
/* 48 */     colorListSettings.add(setting);
/*    */   }
/*    */   
/*    */   public static void removeSetting(Setting<SettingColor> setting) {
/* 52 */     colorSettings.remove(setting);
/*    */   }
/*    */   
/*    */   public static void removeSettingList(Setting<List<SettingColor>> setting) {
/* 56 */     colorListSettings.remove(setting);
/*    */   }
/*    */   
/*    */   public static void add(SettingColor color) {
/* 60 */     colors.add(color);
/*    */   }
/*    */   
/*    */   public static void register(Runnable runnable) {
/* 64 */     listeners.add(runnable);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private static void onTick(TickEvent.Post event) {
/* 69 */     GLOBAL.setSpeed(((Double)(Config.get()).rainbowSpeed.get()).doubleValue() / 100.0D);
/* 70 */     GLOBAL.getNext();
/*    */     
/* 72 */     for (Setting<SettingColor> setting : colorSettings) {
/* 73 */       if (setting.module == null || setting.module.isActive()) ((SettingColor)setting.get()).update();
/*    */     
/*    */     } 
/* 76 */     for (Setting<List<SettingColor>> setting : colorListSettings) {
/* 77 */       if (setting.module == null || setting.module.isActive()) {
/* 78 */         for (SettingColor color : setting.get()) color.update();
/*    */       
/*    */       }
/*    */     } 
/* 82 */     for (SettingColor color : colors) {
/* 83 */       color.update();
/*    */     }
/*    */     
/* 86 */     for (Waypoint waypoint : Waypoints.get()) {
/* 87 */       ((SettingColor)waypoint.color.get()).update();
/*    */     }
/*    */     
/* 90 */     if (MeteorClient.mc.field_1755 instanceof meteordevelopment.meteorclient.gui.WidgetScreen) {
/* 91 */       for (SettingGroup group : (GuiThemes.get()).settings) {
/* 92 */         for (Setting<?> setting : (Iterable<Setting<?>>)group) {
/* 93 */           if (setting instanceof meteordevelopment.meteorclient.settings.ColorSetting) ((SettingColor)setting.get()).update();
/*    */         
/*    */         } 
/*    */       } 
/*    */     }
/* 98 */     for (Runnable listener : listeners) listener.run(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\color\RainbowColors.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */