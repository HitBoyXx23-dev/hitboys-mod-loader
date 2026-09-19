/*     */ package meteordevelopment.meteorclient;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.lang.invoke.MethodHandles;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import meteordevelopment.meteorclient.addons.AddonManager;
/*     */ import meteordevelopment.meteorclient.addons.MeteorAddon;
/*     */ import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
/*     */ import meteordevelopment.meteorclient.events.meteor.KeyEvent;
/*     */ import meteordevelopment.meteorclient.events.meteor.MouseClickEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*     */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*     */ import meteordevelopment.meteorclient.gui.tabs.Tabs;
/*     */ import meteordevelopment.meteorclient.systems.Systems;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.misc.DiscordPresence;
/*     */ import meteordevelopment.meteorclient.utils.PostInit;
/*     */ import meteordevelopment.meteorclient.utils.PreInit;
/*     */ import meteordevelopment.meteorclient.utils.ReflectInit;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.Version;
/*     */ import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
/*     */ import meteordevelopment.meteorclient.utils.misc.input.KeyBinds;
/*     */ import meteordevelopment.meteorclient.utils.network.OnlinePlayers;
/*     */ import meteordevelopment.orbit.EventBus;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import meteordevelopment.orbit.IEventBus;
/*     */ import com.hitboy.loader.NativeMod;
/*     */ import com.hitboy.loader.api.HitBoyPlatform;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_408;
/*     */ import net.minecraft.class_437;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NativeMod(name = "Meteor Client HitBoy Edition", version = "1.21.11-86")
/*     */ public class MeteorClient
/*     */
/*     */ {
/*     */   public static final String MOD_ID = "meteor-client";
/*     */   public static final HitBoyPlatform.ModMetadata MOD_META;
/*     */   public static final String NAME;
/*     */   public static final Version VERSION;
/*     */   public static final String BUILD_NUMBER;
/*     */   public static MeteorClient INSTANCE;
/*     */   public static MeteorAddon ADDON;
/*     */   public static class_310 mc;
/*  59 */   public static final IEventBus EVENT_BUS = (IEventBus)new EventBus();
/*  60 */   public static final File FOLDER = HitBoyPlatform.getGameDirectory().resolve("meteor-client").toFile();
/*     */   public static final Logger LOG;
/*     */   
/*     */   static {
/*  64 */     MOD_META = new HitBoyPlatform.ModMetadata("meteor-client", "Meteor Client HitBoy Edition", "1.21.11-86", java.util.Map.of("meteor-client:build_number", "86"));
/*     */     
/*  66 */     NAME = MOD_META.name();
/*  67 */     LOG = LoggerFactory.getLogger(NAME);
/*     */     
/*  69 */     String versionString = MOD_META.version();
/*  70 */     if (versionString.contains("-")) versionString = versionString.split("-")[0];
/*     */ 
/*     */     
/*  73 */     if (versionString.equals("${version}")) versionString = "0.0.0";
/*     */     
/*  75 */     VERSION = new Version(versionString);
/*  76 */     BUILD_NUMBER = MOD_META.getCustomValue("meteor-client:build_number");
/*     */   }
/*     */   private boolean wasWidgetScreen; private boolean wasHudHiddenRoot;
/*     */   public MeteorClient() {
/*     */     onInitializeClient();
/*     */   }
/*     */   
/*     */   public void onInitializeClient() {
/*  81 */     if (INSTANCE == null) {
/*  82 */       INSTANCE = this;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  87 */     mc = class_310.method_1551();
/*     */     
/*  89 */     if (HitBoyPlatform.isDevelopmentEnvironment()) {
/*  90 */       LOG.info("Force loading mixins");
/*  91 */       MixinEnvironment.getCurrentEnvironment().audit();
/*     */     } 
/*     */     
/*  94 */     LOG.info("Initializing {}", NAME);
/*     */ 
/*     */     
/*  97 */     if (!FOLDER.exists()) {
/*  98 */       FOLDER.getParentFile().mkdirs();
/*  99 */       FOLDER.mkdir();
/* 100 */       Systems.addPreLoadTask(() -> ((DiscordPresence)Modules.get().get(DiscordPresence.class)).enable());
/*     */     } 
/*     */ 
/*     */     
/* 104 */     AddonManager.init();
/*     */ 
/*     */     
/* 107 */     AddonManager.ADDONS.forEach(addon -> {
/*     */           try {
/*     */             EVENT_BUS.registerLambdaFactory(addon.getPackage(), ());
/* 110 */           } catch (AbstractMethodError e) {
/*     */             throw new RuntimeException("Addon \"%s\" is too old and cannot be ran.".formatted(new Object[] { addon.name }, ), e);
/*     */           } 
/*     */         });
/*     */ 
/*     */     
/* 116 */     ReflectInit.registerPackages();
/*     */ 
/*     */     
/* 119 */     ReflectInit.init(PreInit.class);
/*     */ 
/*     */     
/* 122 */     Categories.init();
/*     */ 
/*     */     
/* 125 */     Systems.init();
/*     */ 
/*     */     
/* 128 */     EVENT_BUS.subscribe(this);
/*     */ 
/*     */     
/* 131 */     AddonManager.ADDONS.forEach(MeteorAddon::onInitialize);
/*     */ 
/*     */     
/* 134 */     Modules.get().sortModules();
/*     */ 
/*     */     
/* 137 */     Systems.load();
/*     */ 
/*     */     
/* 140 */     ReflectInit.init(PostInit.class);
/*     */ 
/*     */     
/* 143 */     Runtime.getRuntime().addShutdownHook(new Thread(() -> {
/*     */             OnlinePlayers.leave();
/*     */             Systems.save();
/*     */             GuiThemes.save();
/*     */           }));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 152 */     if (mc.field_1755 == null && mc.method_18506() == null && KeyBinds.OPEN_COMMANDS.method_1436()) {
/* 153 */       mc.method_1507((class_437)new class_408((String)(Config.get()).prefix.get(), true));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onKey(KeyEvent event) {
/* 159 */     if (event.action == KeyAction.Press && KeyBinds.OPEN_GUI.method_1417(event.input)) {
/* 160 */       toggleGui();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onMouseClick(MouseClickEvent event) {
/* 166 */     if (event.action == KeyAction.Press && KeyBinds.OPEN_GUI.method_1433(event.click)) {
/* 167 */       toggleGui();
/*     */     }
/*     */   }
/*     */   
/*     */   private void toggleGui() {
/* 172 */     if (Utils.canCloseGui()) { mc.field_1755.method_25419(); }
/* 173 */     else if (Utils.canOpenGui()) { ((Tab)Tabs.get().getFirst()).openScreen(GuiThemes.get()); }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler(priority = -200)
/*     */   private void onOpenScreen(OpenScreenEvent event) {
/* 182 */     if (event.screen instanceof meteordevelopment.meteorclient.gui.WidgetScreen) {
/* 183 */       if (!this.wasWidgetScreen) this.wasHudHiddenRoot = mc.field_1690.field_1842; 
/* 184 */       if (GuiThemes.get().hideHUD() || this.wasHudHiddenRoot)
/*     */       {
/*     */         
/* 187 */         mc.field_1690.field_1842 = !(event.screen instanceof meteordevelopment.meteorclient.systems.hud.screens.HudEditorScreen);
/*     */       }
/*     */     } else {
/* 190 */       if (this.wasWidgetScreen) mc.field_1690.field_1842 = this.wasHudHiddenRoot; 
/* 191 */       this.wasHudHiddenRoot = mc.field_1690.field_1842;
/*     */     } 
/*     */     
/* 194 */     this.wasWidgetScreen = event.screen instanceof meteordevelopment.meteorclient.gui.WidgetScreen;
/*     */   }
/*     */   
/*     */   public static class_2960 identifier(String path) {
/* 198 */     return class_2960.method_60655("meteor-client", path);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\MeteorClient.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */
