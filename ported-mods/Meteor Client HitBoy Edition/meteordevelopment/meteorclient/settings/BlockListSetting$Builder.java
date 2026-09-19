/*     */ package meteordevelopment.meteorclient.settings;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.class_2248;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends Setting.SettingBuilder<BlockListSetting.Builder, List<class_2248>, BlockListSetting>
/*     */ {
/*     */   private Predicate<class_2248> filter;
/*     */   
/*     */   public Builder() {
/*  90 */     super(new ArrayList<>(0));
/*     */   }
/*     */   
/*     */   public Builder defaultValue(class_2248... defaults) {
/*  94 */     return defaultValue((defaults != null) ? Arrays.<class_2248>asList(defaults) : new ArrayList<>());
/*     */   }
/*     */   
/*     */   public Builder filter(Predicate<class_2248> filter) {
/*  98 */     this.filter = filter;
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockListSetting build() {
/* 104 */     return new BlockListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.filter, this.visible);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\BlockListSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */