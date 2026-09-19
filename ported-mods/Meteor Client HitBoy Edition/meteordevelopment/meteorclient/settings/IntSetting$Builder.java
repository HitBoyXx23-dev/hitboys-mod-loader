/*     */ package meteordevelopment.meteorclient.settings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends Setting.SettingBuilder<IntSetting.Builder, Integer, IntSetting>
/*     */ {
/*  56 */   private int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
/*  57 */   private int sliderMin = 0; private int sliderMax = 10;
/*     */   private boolean noSlider = false;
/*     */   
/*     */   public Builder() {
/*  61 */     super(Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public Builder min(int min) {
/*  65 */     this.min = min;
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   public Builder max(int max) {
/*  70 */     this.max = max;
/*  71 */     return this;
/*     */   }
/*     */   
/*     */   public Builder range(int min, int max) {
/*  75 */     this.min = Math.min(min, max);
/*  76 */     this.max = Math.max(min, max);
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   public Builder sliderMin(int min) {
/*  81 */     this.sliderMin = min;
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public Builder sliderMax(int max) {
/*  86 */     this.sliderMax = max;
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public Builder sliderRange(int min, int max) {
/*  91 */     this.sliderMin = min;
/*  92 */     this.sliderMax = max;
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public Builder noSlider() {
/*  97 */     this.noSlider = true;
/*  98 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntSetting build() {
/* 103 */     return new IntSetting(this.name, this.description, this.defaultValue.intValue(), this.onChanged, this.onModuleActivated, this.visible, this.min, this.max, Math.max(this.sliderMin, this.min), Math.min(this.sliderMax, this.max), this.noSlider);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\IntSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */