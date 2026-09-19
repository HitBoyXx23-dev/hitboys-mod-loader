/*     */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.systems.profiles.Profile;
/*     */ import meteordevelopment.meteorclient.utils.render.prompts.OkPrompt;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2507;
/*     */ import net.minecraft.class_2520;
/*     */ import org.lwjgl.util.tinyfd.TinyFileDialogs;
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
/*     */ 
/*     */ 
/*     */ class ExportProfileScreen
/*     */   extends WindowScreen
/*     */ {
/*     */   private final Profile profile;
/*     */   
/*     */   public ExportProfileScreen(GuiTheme theme, Profile profile) {
/* 260 */     super(theme, "Export Profile");
/* 261 */     this.profile = profile;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/* 266 */     add((WWidget)this.theme.label("Select which profile settings to export."));
/*     */     
/* 268 */     WContainer settingsContainer = (WContainer)add((WWidget)this.theme.verticalList()).expandX().minWidth(400.0D).widget();
/*     */     
/* 270 */     settingsContainer.add((WWidget)this.theme.horizontalSeparator()).expandX().widget();
/*     */     
/* 272 */     WCheckbox hud = addBool(settingsContainer, this.profile.settings.get("hud", Boolean.class));
/* 273 */     WCheckbox macros = addBool(settingsContainer, this.profile.settings.get("macros", Boolean.class));
/* 274 */     WCheckbox modules = addBool(settingsContainer, this.profile.settings.get("modules", Boolean.class));
/* 275 */     WCheckbox waypoints = addBool(settingsContainer, this.profile.settings.get("waypoints", Boolean.class));
/*     */     
/* 277 */     add((WWidget)this.theme.horizontalSeparator()).expandX().widget();
/*     */     
/* 279 */     WButton export = (WButton)add((WWidget)this.theme.button("Export profile")).expandX().widget();
/* 280 */     export.action = (() -> {
/*     */         exportProfile(this.profile, hud.checked, macros.checked, modules.checked, waypoints.checked);
/*     */         method_25419();
/*     */       });
/*     */   }
/*     */   
/*     */   private WCheckbox addBool(WContainer container, Setting<Boolean> setting) {
/* 287 */     WHorizontalList boolList = (WHorizontalList)container.add((WWidget)this.theme.horizontalList()).expandX().widget();
/* 288 */     ((WLabel)boolList.add((WWidget)this.theme.label(setting.title)).widget()).tooltip = setting.description;
/*     */     
/* 290 */     WCheckbox c = this.theme.checkbox(((Boolean)setting.get()).booleanValue());
/* 291 */     boolList.add((WWidget)c).expandCellX().right();
/*     */     
/* 293 */     return c;
/*     */   }
/*     */   
/*     */   private void exportProfile(Profile profile, boolean hud, boolean macros, boolean modules, boolean waypoints) {
/* 297 */     String path = TinyFileDialogs.tinyfd_saveFileDialog("Save profile", (CharSequence)profile.name.get(), ProfilesTab.filters, null);
/* 298 */     if (path == null)
/* 299 */       return;  Path p = Path.of(path.endsWith(".nbt") ? path : (path + ".nbt"), new String[0]);
/*     */     
/* 301 */     class_2487 nbt = new class_2487();
/* 302 */     nbt.method_10582("name", (String)profile.name.get());
/*     */     
/*     */     try {
/* 305 */       for (File f : profile.getFile().listFiles()) {
/* 306 */         if ((f.getName().equals("hud.nbt") && hud) || (f
/* 307 */           .getName().equals("macros.nbt") && macros) || (f
/* 308 */           .getName().equals("modules.nbt") && modules)) {
/*     */           
/* 310 */           nbt.method_10566(f.getName(), (class_2520)class_2507.method_10633(f.toPath()));
/*     */         }
/* 312 */         else if (f.getName().endsWith(".nbt") && waypoints) {
/* 313 */           nbt.method_10566(f.getName(), (class_2520)class_2507.method_10633(f.toPath()));
/*     */         } 
/*     */       } 
/* 316 */       class_2507.method_10630(nbt, p);
/* 317 */     } catch (IOException e) {
/* 318 */       MeteorClient.LOG.error("Error serialising profile {} to a file", profile.name.get(), e);
/* 319 */       ((OkPrompt)((OkPrompt)((OkPrompt)((OkPrompt)OkPrompt.create()
/* 320 */         .title("Failure exporting profile"))
/* 321 */         .message("There was an error serialising or exporting the profile %d.", new Object[] { profile.name.get()
/* 322 */           })).message("Error: %d", new Object[] { e.getMessage()
/* 323 */           })).dontShowAgainCheckboxVisible(false))
/* 324 */         .show();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\ProfilesTab$ExportProfileScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */