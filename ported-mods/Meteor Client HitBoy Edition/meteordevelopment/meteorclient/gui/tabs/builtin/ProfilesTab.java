/*     */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*     */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*     */ import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedMinus;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.systems.profiles.Profile;
/*     */ import meteordevelopment.meteorclient.systems.profiles.Profiles;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*     */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.prompts.OkPrompt;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2507;
/*     */ import net.minecraft.class_2520;
/*     */ import net.minecraft.class_437;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.PointerBuffer;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import org.lwjgl.util.tinyfd.TinyFileDialogs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProfilesTab
/*     */   extends Tab
/*     */ {
/*  53 */   private static final PointerBuffer filters = BufferUtils.createPointerBuffer(1);
/*     */   static {
/*  55 */     ByteBuffer pngFilter = MemoryUtil.memASCII("*.nbt");
/*     */     
/*  57 */     filters.put(pngFilter);
/*  58 */     filters.rewind();
/*     */   }
/*     */   
/*     */   public ProfilesTab() {
/*  62 */     super("Profiles");
/*     */   }
/*     */ 
/*     */   
/*     */   public TabScreen createScreen(GuiTheme theme) {
/*  67 */     return (TabScreen)new ProfilesScreen(theme, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isScreen(class_437 screen) {
/*  72 */     return screen instanceof ProfilesScreen;
/*     */   }
/*     */   
/*     */   private static class ProfilesScreen extends WindowTabScreen {
/*     */     public ProfilesScreen(GuiTheme theme, Tab tab) {
/*  77 */       super(theme, tab);
/*     */     }
/*     */ 
/*     */     
/*     */     public void initWidgets() {
/*  82 */       WTable table = (WTable)add((WWidget)this.theme.table()).expandX().minWidth(400.0D).widget();
/*  83 */       initTable(table);
/*     */       
/*  85 */       add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */       
/*  87 */       WHorizontalList l = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */ 
/*     */       
/*  90 */       WButton create = (WButton)l.add((WWidget)this.theme.button("Create")).expandX().widget();
/*  91 */       create.tooltip = "Create new profile";
/*  92 */       create.action = (() -> MeteorClient.mc.method_1507((class_437)new ProfilesTab.EditProfileScreen(this.theme, null, this::reload)));
/*     */ 
/*     */       
/*  95 */       WButton importBtn = (WButton)l.add((WWidget)this.theme.button("Import")).expandX().widget();
/*  96 */       importBtn.tooltip = "Import profile";
/*  97 */       importBtn.action = (() -> {
/*     */           try {
/*     */             Profile imported = importProfile(); if (imported != null)
/*     */               MeteorClient.LOG.info("Successfully imported profile '{}'.", imported.name.get()); 
/*     */             reload();
/* 102 */           } catch (IOException e) {
/*     */             MeteorClient.LOG.error("Error importing profile", e);
/*     */             ((OkPrompt)((OkPrompt)((OkPrompt)((OkPrompt)OkPrompt.create().title("Failure importing profile")).message("There was an error importing the profile.")).message("Error: %d", new Object[] { e.getMessage() })).dontShowAgainCheckboxVisible(false)).show();
/*     */           } 
/*     */         });
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private void initTable(WTable table) {
/* 115 */       table.clear();
/* 116 */       if (Profiles.get().isEmpty())
/*     */         return; 
/* 118 */       for (Profile profile : Profiles.get()) {
/* 119 */         table.add((WWidget)this.theme.label((String)profile.name.get())).expandCellX();
/*     */         
/* 121 */         WConfirmedButton save = this.theme.confirmedButton("Save", "Confirm");
/* 122 */         Objects.requireNonNull(profile); save.action = profile::save;
/* 123 */         table.add((WWidget)save).right();
/*     */         
/* 125 */         WButton load = (WButton)table.add((WWidget)this.theme.button("Load")).widget();
/* 126 */         Objects.requireNonNull(profile); load.action = profile::load;
/*     */         
/* 128 */         WButton export = (WButton)table.add((WWidget)this.theme.button("Export")).widget();
/* 129 */         export.action = (() -> MeteorClient.mc.method_1507((class_437)new ProfilesTab.ExportProfileScreen(this.theme, profile)));
/*     */         
/* 131 */         WButton edit = (WButton)table.add((WWidget)this.theme.button(GuiRenderer.EDIT)).widget();
/* 132 */         edit.action = (() -> MeteorClient.mc.method_1507((class_437)new ProfilesTab.EditProfileScreen(this.theme, profile, this::reload)));
/*     */         
/* 134 */         WConfirmedMinus remove = (WConfirmedMinus)table.add((WWidget)this.theme.confirmedMinus()).widget();
/* 135 */         remove.action = (() -> {
/*     */             Profiles.get().remove(profile);
/*     */             
/*     */             reload();
/*     */           });
/* 140 */         table.row();
/*     */       } 
/*     */     }
/*     */     
/*     */     private Profile importProfile() throws IOException {
/* 145 */       String file = TinyFileDialogs.tinyfd_openFileDialog("Select profile to import", null, ProfilesTab.filters, null, false);
/* 146 */       if (file == null) return null; 
/* 147 */       File profileFile = new File(file);
/*     */       
/* 149 */       class_2487 nbt = class_2507.method_10633(profileFile.toPath());
/*     */       
/* 151 */       Profile p = new Profile();
/* 152 */       if (!p.name.set(nbt.method_68564("name", profileFile.getName()))) return null; 
/* 153 */       File profileFolder = p.getSafeFile();
/* 154 */       if (profileFolder == null) return null;
/*     */       
/* 156 */       profileFolder.mkdirs();
/*     */       
/* 158 */       nbt.method_10551("name");
/* 159 */       for (Map.Entry<String, class_2520> entry : (Iterable<Map.Entry<String, class_2520>>)nbt.method_59874()) {
/* 160 */         String filename = entry.getKey();
/* 161 */         if (!filename.endsWith(".nbt") || 
/* 162 */           filename.contains("/") || filename.contains("\\") || (new File(filename)).isAbsolute())
/*     */           continue; 
/* 164 */         switch (filename) { case "hud.nbt":
/* 165 */             p.hud.set(Boolean.valueOf(true)); break;
/* 166 */           case "macros.nbt": p.macros.set(Boolean.valueOf(true)); break;
/* 167 */           case "modules.nbt": p.modules.set(Boolean.valueOf(true)); break;
/*     */           default:
/* 169 */             if (filename.endsWith(".nbt")) p.waypoints.set(Boolean.valueOf(true));
/*     */             
/*     */             break; }
/*     */         
/* 173 */         File f = (new File(profileFolder, filename)).getCanonicalFile();
/* 174 */         if (!f.toPath().startsWith(profileFolder.toPath()))
/*     */           continue; 
/* 176 */         class_2507.method_55324(entry.getValue(), new DataOutputStream(new FileOutputStream(f)));
/*     */       } 
/*     */       
/* 179 */       Profiles.get().getAll().add(p);
/* 180 */       Profiles.get().save();
/*     */       
/* 182 */       return p;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean toClipboard() {
/* 187 */       return NbtUtils.toClipboard((ISerializable)Profiles.get());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean fromClipboard() {
/* 192 */       return NbtUtils.fromClipboard((ISerializable)Profiles.get());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class EditProfileScreen extends WindowScreen {
/*     */     private WContainer settingsContainer;
/*     */     private final Profile profile;
/*     */     private final boolean isNew;
/*     */     private final Runnable action;
/*     */     
/*     */     public EditProfileScreen(GuiTheme theme, Profile profile, Runnable action) {
/* 203 */       super(theme, (profile == null) ? "New Profile" : "Edit Profile");
/*     */       
/* 205 */       this.isNew = (profile == null);
/* 206 */       this.profile = this.isNew ? new Profile() : profile;
/* 207 */       this.action = action;
/*     */     }
/*     */ 
/*     */     
/*     */     public void initWidgets() {
/* 212 */       this.settingsContainer = (WContainer)add((WWidget)this.theme.verticalList()).expandX().minWidth(400.0D).widget();
/* 213 */       this.settingsContainer.add(this.theme.settings(this.profile.settings)).expandX();
/*     */       
/* 215 */       add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */       
/* 217 */       WButton save = (WButton)add((WWidget)this.theme.button(this.isNew ? "Create" : "Save")).expandX().widget();
/* 218 */       save.action = (() -> {
/*     */           if (this.profile.getSafeFile() == null) {
/*     */             return;
/*     */           }
/*     */           if (this.isNew)
/*     */             for (Profile p : Profiles.get()) {
/*     */               if (this.profile.equals(p))
/*     */                 return; 
/*     */             }  
/*     */           List<String> valid = new ArrayList<>();
/*     */           for (String address : this.profile.loadOnJoin.get()) {
/*     */             if (Utils.resolveAddress(address))
/*     */               valid.add(address); 
/*     */           } 
/*     */           this.profile.loadOnJoin.set(valid);
/*     */           if (this.isNew) {
/*     */             Profiles.get().add(this.profile);
/*     */           } else {
/*     */             Profiles.get().save();
/*     */           } 
/*     */           method_25419();
/*     */         });
/* 240 */       this.enterAction = save.action;
/*     */     }
/*     */ 
/*     */     
/*     */     public void method_25393() {
/* 245 */       super.method_25393();
/*     */       
/* 247 */       this.profile.settings.tick(this.settingsContainer, this.theme);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onClosed() {
/* 252 */       if (this.action != null) this.action.run(); 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class ExportProfileScreen extends WindowScreen {
/*     */     private final Profile profile;
/*     */     
/*     */     public ExportProfileScreen(GuiTheme theme, Profile profile) {
/* 260 */       super(theme, "Export Profile");
/* 261 */       this.profile = profile;
/*     */     }
/*     */ 
/*     */     
/*     */     public void initWidgets() {
/* 266 */       add((WWidget)this.theme.label("Select which profile settings to export."));
/*     */       
/* 268 */       WContainer settingsContainer = (WContainer)add((WWidget)this.theme.verticalList()).expandX().minWidth(400.0D).widget();
/*     */       
/* 270 */       settingsContainer.add((WWidget)this.theme.horizontalSeparator()).expandX().widget();
/*     */       
/* 272 */       WCheckbox hud = addBool(settingsContainer, this.profile.settings.get("hud", Boolean.class));
/* 273 */       WCheckbox macros = addBool(settingsContainer, this.profile.settings.get("macros", Boolean.class));
/* 274 */       WCheckbox modules = addBool(settingsContainer, this.profile.settings.get("modules", Boolean.class));
/* 275 */       WCheckbox waypoints = addBool(settingsContainer, this.profile.settings.get("waypoints", Boolean.class));
/*     */       
/* 277 */       add((WWidget)this.theme.horizontalSeparator()).expandX().widget();
/*     */       
/* 279 */       WButton export = (WButton)add((WWidget)this.theme.button("Export profile")).expandX().widget();
/* 280 */       export.action = (() -> {
/*     */           exportProfile(this.profile, hud.checked, macros.checked, modules.checked, waypoints.checked);
/*     */           method_25419();
/*     */         });
/*     */     }
/*     */     
/*     */     private WCheckbox addBool(WContainer container, Setting<Boolean> setting) {
/* 287 */       WHorizontalList boolList = (WHorizontalList)container.add((WWidget)this.theme.horizontalList()).expandX().widget();
/* 288 */       ((WLabel)boolList.add((WWidget)this.theme.label(setting.title)).widget()).tooltip = setting.description;
/*     */       
/* 290 */       WCheckbox c = this.theme.checkbox(((Boolean)setting.get()).booleanValue());
/* 291 */       boolList.add((WWidget)c).expandCellX().right();
/*     */       
/* 293 */       return c;
/*     */     }
/*     */     
/*     */     private void exportProfile(Profile profile, boolean hud, boolean macros, boolean modules, boolean waypoints) {
/* 297 */       String path = TinyFileDialogs.tinyfd_saveFileDialog("Save profile", (CharSequence)profile.name.get(), ProfilesTab.filters, null);
/* 298 */       if (path == null)
/* 299 */         return;  Path p = Path.of(path.endsWith(".nbt") ? path : (path + ".nbt"), new String[0]);
/*     */       
/* 301 */       class_2487 nbt = new class_2487();
/* 302 */       nbt.method_10582("name", (String)profile.name.get());
/*     */       
/*     */       try {
/* 305 */         for (File f : profile.getFile().listFiles()) {
/* 306 */           if ((f.getName().equals("hud.nbt") && hud) || (f
/* 307 */             .getName().equals("macros.nbt") && macros) || (f
/* 308 */             .getName().equals("modules.nbt") && modules)) {
/*     */             
/* 310 */             nbt.method_10566(f.getName(), (class_2520)class_2507.method_10633(f.toPath()));
/*     */           }
/* 312 */           else if (f.getName().endsWith(".nbt") && waypoints) {
/* 313 */             nbt.method_10566(f.getName(), (class_2520)class_2507.method_10633(f.toPath()));
/*     */           } 
/*     */         } 
/* 316 */         class_2507.method_10630(nbt, p);
/* 317 */       } catch (IOException e) {
/* 318 */         MeteorClient.LOG.error("Error serialising profile {} to a file", profile.name.get(), e);
/* 319 */         ((OkPrompt)((OkPrompt)((OkPrompt)((OkPrompt)OkPrompt.create()
/* 320 */           .title("Failure exporting profile"))
/* 321 */           .message("There was an error serialising or exporting the profile %d.", new Object[] { profile.name.get()
/* 322 */             })).message("Error: %d", new Object[] { e.getMessage()
/* 323 */             })).dontShowAgainCheckboxVisible(false))
/* 324 */           .show();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\ProfilesTab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */