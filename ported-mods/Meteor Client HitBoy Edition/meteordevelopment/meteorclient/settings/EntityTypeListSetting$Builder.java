/*     */ package meteordevelopment.meteorclient.settings;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*     */ import net.minecraft.class_1299;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends Setting.SettingBuilder<EntityTypeListSetting.Builder, Set<class_1299<?>>, EntityTypeListSetting>
/*     */ {
/*     */   private Predicate<class_1299<?>> filter;
/*     */   
/*     */   public Builder() {
/* 130 */     super(new ObjectOpenHashSet(0));
/*     */   }
/*     */   
/*     */   public Builder defaultValue(class_1299<?>... defaults) {
/* 134 */     return defaultValue((defaults != null) ? (Set<class_1299<?>>)new ObjectOpenHashSet((Object[])defaults) : (Set<class_1299<?>>)new ObjectOpenHashSet(0));
/*     */   }
/*     */   
/*     */   public Builder onlyAttackable() {
/* 138 */     this.filter = EntityUtils::isAttackable;
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   public Builder filter(Predicate<class_1299<?>> filter) {
/* 143 */     this.filter = filter;
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityTypeListSetting build() {
/* 149 */     return new EntityTypeListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible, this.filter);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\EntityTypeListSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */