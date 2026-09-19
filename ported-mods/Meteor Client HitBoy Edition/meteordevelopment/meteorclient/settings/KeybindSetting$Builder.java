/*     */ package meteordevelopment.meteorclient.settings;
/*     */ 
/*     */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */   extends Setting.SettingBuilder<KeybindSetting.Builder, Keybind, KeybindSetting>
/*     */ {
/*     */   private Runnable action;
/*     */   
/*     */   public Builder() {
/*  98 */     super(Keybind.none());
/*     */   }
/*     */   
/*     */   public Builder action(Runnable action) {
/* 102 */     this.action = action;
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public KeybindSetting build() {
/* 108 */     return new KeybindSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible, this.action);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\KeybindSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */