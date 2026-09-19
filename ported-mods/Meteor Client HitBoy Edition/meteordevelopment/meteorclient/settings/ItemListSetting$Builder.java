/*     */ package meteordevelopment.meteorclient.settings;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.class_1792;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends Setting.SettingBuilder<ItemListSetting.Builder, List<class_1792>, ItemListSetting>
/*     */ {
/*     */   private Predicate<class_1792> filter;
/*     */   private boolean bypassFilterWhenSavingAndLoading;
/*     */   
/*     */   public Builder() {
/*  93 */     super(new ArrayList<>(0));
/*     */   }
/*     */   
/*     */   public Builder defaultValue(class_1792... defaults) {
/*  97 */     return defaultValue((defaults != null) ? Arrays.<class_1792>asList(defaults) : new ArrayList<>());
/*     */   }
/*     */   
/*     */   public Builder filter(Predicate<class_1792> filter) {
/* 101 */     this.filter = filter;
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public Builder bypassFilterWhenSavingAndLoading() {
/* 106 */     this.bypassFilterWhenSavingAndLoading = true;
/* 107 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemListSetting build() {
/* 112 */     return new ItemListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible, this.filter, this.bypassFilterWhenSavingAndLoading);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\ItemListSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */