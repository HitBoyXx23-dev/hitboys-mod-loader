/*     */ package meteordevelopment.meteorclient.settings;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.IGetter;
/*     */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2960;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Setting<T>
/*     */   implements IGetter<T>, ISerializable<T>
/*     */ {
/*  23 */   private static final List<String> NO_SUGGESTIONS = new ArrayList<>(0);
/*     */   
/*     */   public final String name;
/*     */   
/*     */   public final String title;
/*     */   public final String description;
/*     */   private final IVisible visible;
/*     */   protected final T defaultValue;
/*     */   protected T value;
/*     */   public final Consumer<Setting<T>> onModuleActivated;
/*     */   private final Consumer<T> onChanged;
/*     */   public Module module;
/*     */   public boolean lastWasVisible;
/*     */   
/*     */   public Setting(String name, String description, T defaultValue, Consumer<T> onChanged, Consumer<Setting<T>> onModuleActivated, IVisible visible) {
/*  38 */     this.name = name;
/*  39 */     this.title = Utils.nameToTitle(name);
/*  40 */     this.description = description;
/*  41 */     this.defaultValue = defaultValue;
/*  42 */     this.onChanged = onChanged;
/*  43 */     this.onModuleActivated = onModuleActivated;
/*  44 */     this.visible = visible;
/*     */     
/*  46 */     resetImpl();
/*     */   }
/*     */ 
/*     */   
/*     */   public T get() {
/*  51 */     return this.value;
/*     */   }
/*     */   
/*     */   public boolean set(T value) {
/*  55 */     if (!isValueValid(value)) return false; 
/*  56 */     this.value = value;
/*  57 */     onChanged();
/*  58 */     return true;
/*     */   }
/*     */   
/*     */   protected void resetImpl() {
/*  62 */     this.value = this.defaultValue;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  66 */     resetImpl();
/*  67 */     onChanged();
/*     */   }
/*     */   
/*     */   public T getDefaultValue() {
/*  71 */     return this.defaultValue;
/*     */   }
/*     */   
/*     */   public boolean parse(String str) {
/*  75 */     T newValue = parseImpl(str);
/*     */     
/*  77 */     if (newValue != null && 
/*  78 */       isValueValid(newValue)) {
/*  79 */       this.value = newValue;
/*  80 */       onChanged();
/*     */     } 
/*     */ 
/*     */     
/*  84 */     return (newValue != null);
/*     */   }
/*     */   
/*     */   public boolean wasChanged() {
/*  88 */     return !Objects.equals(this.value, this.defaultValue);
/*     */   }
/*     */   
/*     */   public void onChanged() {
/*  92 */     if (this.onChanged != null) this.onChanged.accept(this.value); 
/*     */   }
/*     */   
/*     */   public void onActivated() {
/*  96 */     if (this.onModuleActivated != null) this.onModuleActivated.accept(this); 
/*     */   }
/*     */   
/*     */   public boolean isVisible() {
/* 100 */     return (this.visible == null || this.visible.isVisible());
/*     */   }
/*     */   
/*     */   protected abstract T parseImpl(String paramString);
/*     */   
/*     */   protected abstract boolean isValueValid(T paramT);
/*     */   
/*     */   public Iterable<class_2960> getIdentifierSuggestions() {
/* 108 */     return null;
/*     */   }
/*     */   
/*     */   public List<String> getSuggestions() {
/* 112 */     return NO_SUGGESTIONS;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract class_2487 save(class_2487 paramclass_2487);
/*     */   
/*     */   public class_2487 toTag() {
/* 119 */     class_2487 tag = new class_2487();
/*     */     
/* 121 */     tag.method_10582("name", this.name);
/* 122 */     save(tag);
/*     */     
/* 124 */     return tag;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract T load(class_2487 paramclass_2487);
/*     */   
/*     */   public T fromTag(class_2487 tag) {
/* 131 */     T value = load(tag);
/* 132 */     onChanged();
/*     */     
/* 134 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 139 */     return this.value.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 144 */     if (this == o) return true; 
/* 145 */     if (o == null || getClass() != o.getClass()) return false; 
/* 146 */     Setting<?> setting = (Setting)o;
/* 147 */     return Objects.equals(this.name, setting.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 152 */     return Objects.hash(new Object[] { this.name });
/*     */   }
/*     */   @Nullable
/*     */   public static <T> T parseId(class_2378<T> registry, String name) {
/*     */     class_2960 id;
/* 157 */     name = name.trim();
/*     */ 
/*     */     
/* 160 */     if (name.contains(":")) { id = class_2960.method_60654(name); }
/* 161 */     else { id = class_2960.method_60655("minecraft", name); }
/* 162 */      if (registry.method_10250(id)) return (T)registry.method_63535(id);
/*     */     
/* 164 */     return null;
/*     */   }
/*     */   
/*     */   public static abstract class SettingBuilder<B, V, S>
/*     */   {
/* 169 */     protected String name = "undefined"; protected String description = "";
/*     */     protected V defaultValue;
/*     */     protected IVisible visible;
/*     */     protected Consumer<V> onChanged;
/*     */     protected Consumer<Setting<V>> onModuleActivated;
/*     */     
/*     */     protected SettingBuilder(V defaultValue) {
/* 176 */       this.defaultValue = defaultValue;
/*     */     }
/*     */     
/*     */     public B name(String name) {
/* 180 */       this.name = name;
/* 181 */       return (B)this;
/*     */     }
/*     */     
/*     */     public B description(String description) {
/* 185 */       this.description = description;
/* 186 */       return (B)this;
/*     */     }
/*     */     
/*     */     public B defaultValue(V defaultValue) {
/* 190 */       this.defaultValue = defaultValue;
/* 191 */       return (B)this;
/*     */     }
/*     */     
/*     */     public B visible(IVisible visible) {
/* 195 */       this.visible = visible;
/* 196 */       return (B)this;
/*     */     }
/*     */     
/*     */     public B onChanged(Consumer<V> onChanged) {
/* 200 */       this.onChanged = onChanged;
/* 201 */       return (B)this;
/*     */     }
/*     */     
/*     */     public B onModuleActivated(Consumer<Setting<V>> onModuleActivated) {
/* 205 */       this.onModuleActivated = onModuleActivated;
/* 206 */       return (B)this;
/*     */     }
/*     */     
/*     */     public abstract S build();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\Setting.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */