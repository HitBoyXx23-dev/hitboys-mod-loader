/*    */ package meteordevelopment.meteorclient.gui.utils;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.function.Function;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.Settings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SettingsWidgetFactory
/*    */ {
/* 19 */   private static final Map<Class<?>, Function<GuiTheme, Factory>> customFactories = new HashMap<>();
/*    */   
/*    */   protected final GuiTheme theme;
/* 22 */   protected final Map<Class<?>, Factory> factories = new HashMap<>();
/*    */   
/*    */   public SettingsWidgetFactory(GuiTheme theme) {
/* 25 */     this.theme = theme;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerCustomFactory(Class<?> settingClass, Function<GuiTheme, Factory> factoryFunction) {
/* 30 */     customFactories.put(settingClass, factoryFunction);
/*    */   }
/*    */   
/*    */   public static void unregisterCustomFactory(Class<?> settingClass) {
/* 34 */     customFactories.remove(settingClass);
/*    */   }
/*    */   
/*    */   public abstract WWidget create(GuiTheme paramGuiTheme, Settings paramSettings, String paramString);
/*    */   
/*    */   protected Factory getFactory(Class<?> settingClass) {
/* 40 */     if (customFactories.containsKey(settingClass)) return ((Function<GuiTheme, Factory>)customFactories.get(settingClass)).apply(this.theme); 
/* 41 */     return this.factories.get(settingClass);
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface Factory {
/*    */     void create(WTable param1WTable, Setting<?> param1Setting);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gu\\utils\SettingsWidgetFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */