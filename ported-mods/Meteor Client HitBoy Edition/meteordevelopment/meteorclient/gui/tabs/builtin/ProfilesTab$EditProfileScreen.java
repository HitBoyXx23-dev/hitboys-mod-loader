/*     */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.systems.profiles.Profile;
/*     */ import meteordevelopment.meteorclient.systems.profiles.Profiles;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EditProfileScreen
/*     */   extends WindowScreen
/*     */ {
/*     */   private WContainer settingsContainer;
/*     */   private final Profile profile;
/*     */   private final boolean isNew;
/*     */   private final Runnable action;
/*     */   
/*     */   public EditProfileScreen(GuiTheme theme, Profile profile, Runnable action) {
/* 203 */     super(theme, (profile == null) ? "New Profile" : "Edit Profile");
/*     */     
/* 205 */     this.isNew = (profile == null);
/* 206 */     this.profile = this.isNew ? new Profile() : profile;
/* 207 */     this.action = action;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/* 212 */     this.settingsContainer = (WContainer)add((WWidget)this.theme.verticalList()).expandX().minWidth(400.0D).widget();
/* 213 */     this.settingsContainer.add(this.theme.settings(this.profile.settings)).expandX();
/*     */     
/* 215 */     add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */     
/* 217 */     WButton save = (WButton)add((WWidget)this.theme.button(this.isNew ? "Create" : "Save")).expandX().widget();
/* 218 */     save.action = (() -> {
/*     */         if (this.profile.getSafeFile() == null) {
/*     */           return;
/*     */         }
/*     */         if (this.isNew)
/*     */           for (Profile p : Profiles.get()) {
/*     */             if (this.profile.equals(p))
/*     */               return; 
/*     */           }  
/*     */         List<String> valid = new ArrayList<>();
/*     */         for (String address : this.profile.loadOnJoin.get()) {
/*     */           if (Utils.resolveAddress(address))
/*     */             valid.add(address); 
/*     */         } 
/*     */         this.profile.loadOnJoin.set(valid);
/*     */         if (this.isNew) {
/*     */           Profiles.get().add(this.profile);
/*     */         } else {
/*     */           Profiles.get().save();
/*     */         } 
/*     */         method_25419();
/*     */       });
/* 240 */     this.enterAction = save.action;
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25393() {
/* 245 */     super.method_25393();
/*     */     
/* 247 */     this.profile.settings.tick(this.settingsContainer, this.theme);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onClosed() {
/* 252 */     if (this.action != null) this.action.run(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\ProfilesTab$EditProfileScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */