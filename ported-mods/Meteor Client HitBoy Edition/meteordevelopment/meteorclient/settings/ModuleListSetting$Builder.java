/*     */ package meteordevelopment.meteorclient.settings;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends Setting.SettingBuilder<ModuleListSetting.Builder, List<Module>, ModuleListSetting>
/*     */ {
/*     */   public Builder() {
/*  85 */     super(new ArrayList<>(0));
/*     */   }
/*     */   
/*     */   @SafeVarargs
/*     */   public final Builder defaultValue(Class<? extends Module>... defaults) {
/*  90 */     List<Module> modules = new ArrayList<>();
/*     */     
/*  92 */     for (Class<? extends Module> klass : defaults) {
/*  93 */       if (Modules.get().get(klass) != null) modules.add(Modules.get().get(klass));
/*     */     
/*     */     } 
/*  96 */     return defaultValue(modules);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModuleListSetting build() {
/* 101 */     return new ModuleListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\ModuleListSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */