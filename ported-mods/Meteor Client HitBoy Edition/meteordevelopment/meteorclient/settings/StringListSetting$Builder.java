/*     */ package meteordevelopment.meteorclient.settings;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.gui.utils.CharFilter;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends Setting.SettingBuilder<StringListSetting.Builder, List<String>, StringListSetting>
/*     */ {
/*     */   private Class<? extends WTextBox.Renderer> renderer;
/*     */   private CharFilter filter;
/*     */   
/*     */   public Builder() {
/* 126 */     super(new ArrayList<>(0));
/*     */   }
/*     */   
/*     */   public Builder defaultValue(String... defaults) {
/* 130 */     return defaultValue((defaults != null) ? Arrays.<String>asList(defaults) : new ArrayList<>());
/*     */   }
/*     */   
/*     */   public Builder renderer(Class<? extends WTextBox.Renderer> renderer) {
/* 134 */     this.renderer = renderer;
/* 135 */     return this;
/*     */   }
/*     */   
/*     */   public Builder filter(CharFilter filter) {
/* 139 */     this.filter = filter;
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public StringListSetting build() {
/* 145 */     return new StringListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible, this.renderer, this.filter);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\StringListSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */