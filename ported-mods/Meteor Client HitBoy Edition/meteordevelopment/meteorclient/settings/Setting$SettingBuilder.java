/*     */ package meteordevelopment.meteorclient.settings;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SettingBuilder<B, V, S>
/*     */ {
/* 169 */   protected String name = "undefined"; protected String description = "";
/*     */   protected V defaultValue;
/*     */   protected IVisible visible;
/*     */   protected Consumer<V> onChanged;
/*     */   protected Consumer<Setting<V>> onModuleActivated;
/*     */   
/*     */   protected SettingBuilder(V defaultValue) {
/* 176 */     this.defaultValue = defaultValue;
/*     */   }
/*     */   
/*     */   public B name(String name) {
/* 180 */     this.name = name;
/* 181 */     return (B)this;
/*     */   }
/*     */   
/*     */   public B description(String description) {
/* 185 */     this.description = description;
/* 186 */     return (B)this;
/*     */   }
/*     */   
/*     */   public B defaultValue(V defaultValue) {
/* 190 */     this.defaultValue = defaultValue;
/* 191 */     return (B)this;
/*     */   }
/*     */   
/*     */   public B visible(IVisible visible) {
/* 195 */     this.visible = visible;
/* 196 */     return (B)this;
/*     */   }
/*     */   
/*     */   public B onChanged(Consumer<V> onChanged) {
/* 200 */     this.onChanged = onChanged;
/* 201 */     return (B)this;
/*     */   }
/*     */   
/*     */   public B onModuleActivated(Consumer<Setting<V>> onModuleActivated) {
/* 205 */     this.onModuleActivated = onModuleActivated;
/* 206 */     return (B)this;
/*     */   }
/*     */   
/*     */   public abstract S build();
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\Setting$SettingBuilder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */