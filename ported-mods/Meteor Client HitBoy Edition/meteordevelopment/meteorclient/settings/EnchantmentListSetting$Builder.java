/*     */ package meteordevelopment.meteorclient.settings;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.lang.reflect.AccessFlag;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.class_1887;
/*     */ import net.minecraft.class_1893;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_7924;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends Setting.SettingBuilder<EnchantmentListSetting.Builder, Set<class_5321<class_1887>>, EnchantmentListSetting>
/*     */ {
/*     */   private static final Set<class_5321<class_1887>> VANILLA_DEFAULTS;
/*     */   
/*     */   public Builder() {
/*  93 */     super(new ObjectOpenHashSet());
/*     */   }
/*     */   
/*     */   public Builder vanillaDefaults() {
/*  97 */     return defaultValue(VANILLA_DEFAULTS);
/*     */   }
/*     */   
/*     */   @SafeVarargs
/*     */   public final Builder defaultValue(class_5321<class_1887>... defaults) {
/* 102 */     return defaultValue((defaults != null) ? (Set<class_5321<class_1887>>)new ObjectOpenHashSet((Object[])defaults) : (Set<class_5321<class_1887>>)new ObjectOpenHashSet());
/*     */   }
/*     */ 
/*     */   
/*     */   public EnchantmentListSetting build() {
/* 107 */     return new EnchantmentListSetting(this.name, this.description, this.defaultValue, this.onChanged, this.onModuleActivated, this.visible);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 121 */     Objects.requireNonNull(class_5321.class);
/*     */     
/* 123 */     VANILLA_DEFAULTS = (Set<class_5321<class_1887>>)Arrays.<Field>stream(class_1893.class.getDeclaredFields()).filter(field -> field.accessFlags().containsAll(List.of(AccessFlag.PUBLIC, AccessFlag.STATIC, AccessFlag.FINAL))).filter(field -> (field.getType() == class_5321.class)).map(field -> { try { return (Function)field.get(null); } catch (IllegalAccessException e) { return null; }  }).filter(Objects::nonNull).map(class_5321.class::cast).filter(registryKey -> (registryKey.method_58273() == class_7924.field_41265)).collect(Collectors.toSet());
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\EnchantmentListSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */