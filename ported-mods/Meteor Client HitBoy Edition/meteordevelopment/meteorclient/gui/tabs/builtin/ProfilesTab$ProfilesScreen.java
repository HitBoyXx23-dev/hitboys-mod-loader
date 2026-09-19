/*     */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*     */ 
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*     */ import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedButton;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedMinus;
/*     */ import meteordevelopment.meteorclient.systems.profiles.Profile;
/*     */ import meteordevelopment.meteorclient.systems.profiles.Profiles;
/*     */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*     */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.prompts.OkPrompt;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2507;
/*     */ import net.minecraft.class_2520;
/*     */ import net.minecraft.class_437;
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
/*     */ class ProfilesScreen
/*     */   extends WindowTabScreen
/*     */ {
/*     */   public ProfilesScreen(GuiTheme theme, Tab tab) {
/*  77 */     super(theme, tab);
/*     */   }
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/*  82 */     WTable table = (WTable)add((WWidget)this.theme.table()).expandX().minWidth(400.0D).widget();
/*  83 */     initTable(table);
/*     */     
/*  85 */     add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */     
/*  87 */     WHorizontalList l = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */ 
/*     */     
/*  90 */     WButton create = (WButton)l.add((WWidget)this.theme.button("Create")).expandX().widget();
/*  91 */     create.tooltip = "Create new profile";
/*  92 */     create.action = (() -> MeteorClient.mc.method_1507((class_437)new ProfilesTab.EditProfileScreen(this.theme, null, this::reload)));
/*     */ 
/*     */     
/*  95 */     WButton importBtn = (WButton)l.add((WWidget)this.theme.button("Import")).expandX().widget();
/*  96 */     importBtn.tooltip = "Import profile";
/*  97 */     importBtn.action = (() -> {
/*     */         try {
/*     */           Profile imported = importProfile(); if (imported != null)
/*     */             MeteorClient.LOG.info("Successfully imported profile '{}'.", imported.name.get()); 
/*     */           reload();
/* 102 */         } catch (IOException e) {
/*     */           MeteorClient.LOG.error("Error importing profile", e);
/*     */           ((OkPrompt)((OkPrompt)((OkPrompt)((OkPrompt)OkPrompt.create().title("Failure importing profile")).message("There was an error importing the profile.")).message("Error: %d", new Object[] { e.getMessage() })).dontShowAgainCheckboxVisible(false)).show();
/*     */         } 
/*     */       });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initTable(WTable table) {
/* 115 */     table.clear();
/* 116 */     if (Profiles.get().isEmpty())
/*     */       return; 
/* 118 */     for (Profile profile : Profiles.get()) {
/* 119 */       table.add((WWidget)this.theme.label((String)profile.name.get())).expandCellX();
/*     */       
/* 121 */       WConfirmedButton save = this.theme.confirmedButton("Save", "Confirm");
/* 122 */       Objects.requireNonNull(profile); save.action = profile::save;
/* 123 */       table.add((WWidget)save).right();
/*     */       
/* 125 */       WButton load = (WButton)table.add((WWidget)this.theme.button("Load")).widget();
/* 126 */       Objects.requireNonNull(profile); load.action = profile::load;
/*     */       
/* 128 */       WButton export = (WButton)table.add((WWidget)this.theme.button("Export")).widget();
/* 129 */       export.action = (() -> MeteorClient.mc.method_1507((class_437)new ProfilesTab.ExportProfileScreen(this.theme, profile)));
/*     */       
/* 131 */       WButton edit = (WButton)table.add((WWidget)this.theme.button(GuiRenderer.EDIT)).widget();
/* 132 */       edit.action = (() -> MeteorClient.mc.method_1507((class_437)new ProfilesTab.EditProfileScreen(this.theme, profile, this::reload)));
/*     */       
/* 134 */       WConfirmedMinus remove = (WConfirmedMinus)table.add((WWidget)this.theme.confirmedMinus()).widget();
/* 135 */       remove.action = (() -> {
/*     */           Profiles.get().remove(profile);
/*     */           
/*     */           reload();
/*     */         });
/* 140 */       table.row();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Profile importProfile() throws IOException {
/* 145 */     String file = TinyFileDialogs.tinyfd_openFileDialog("Select profile to import", null, ProfilesTab.filters, null, false);
/* 146 */     if (file == null) return null; 
/* 147 */     File profileFile = new File(file);
/*     */     
/* 149 */     class_2487 nbt = class_2507.method_10633(profileFile.toPath());
/*     */     
/* 151 */     Profile p = new Profile();
/* 152 */     if (!p.name.set(nbt.method_68564("name", profileFile.getName()))) return null; 
/* 153 */     File profileFolder = p.getSafeFile();
/* 154 */     if (profileFolder == null) return null;
/*     */     
/* 156 */     profileFolder.mkdirs();
/*     */     
/* 158 */     nbt.method_10551("name");
/* 159 */     for (Map.Entry<String, class_2520> entry : (Iterable<Map.Entry<String, class_2520>>)nbt.method_59874()) {
/* 160 */       String filename = entry.getKey();
/* 161 */       if (!filename.endsWith(".nbt") || 
/* 162 */         filename.contains("/") || filename.contains("\\") || (new File(filename)).isAbsolute())
/*     */         continue; 
/* 164 */       switch (filename) { case "hud.nbt":
/* 165 */           p.hud.set(Boolean.valueOf(true)); break;
/* 166 */         case "macros.nbt": p.macros.set(Boolean.valueOf(true)); break;
/* 167 */         case "modules.nbt": p.modules.set(Boolean.valueOf(true)); break;
/*     */         default:
/* 169 */           if (filename.endsWith(".nbt")) p.waypoints.set(Boolean.valueOf(true));
/*     */           
/*     */           break; }
/*     */       
/* 173 */       File f = (new File(profileFolder, filename)).getCanonicalFile();
/* 174 */       if (!f.toPath().startsWith(profileFolder.toPath()))
/*     */         continue; 
/* 176 */       class_2507.method_55324(entry.getValue(), new DataOutputStream(new FileOutputStream(f)));
/*     */     } 
/*     */     
/* 179 */     Profiles.get().getAll().add(p);
/* 180 */     Profiles.get().save();
/*     */     
/* 182 */     return p;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean toClipboard() {
/* 187 */     return NbtUtils.toClipboard((ISerializable)Profiles.get());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fromClipboard() {
/* 192 */     return NbtUtils.fromClipboard((ISerializable)Profiles.get());
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\ProfilesTab$ProfilesScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */