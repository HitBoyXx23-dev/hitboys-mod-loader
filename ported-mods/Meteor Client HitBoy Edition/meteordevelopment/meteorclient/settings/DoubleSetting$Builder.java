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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */   extends Setting.SettingBuilder<DoubleSetting.Builder, Double, DoubleSetting>
/*     */ {
/*  60 */   public double min = Double.NEGATIVE_INFINITY, max = Double.POSITIVE_INFINITY;
/*  61 */   public double sliderMin = 0.0D; public double sliderMax = 10.0D;
/*     */   public boolean onSliderRelease = false;
/*  63 */   public int decimalPlaces = 3;
/*     */   public boolean noSlider = false;
/*     */   
/*     */   public Builder() {
/*  67 */     super(Double.valueOf(0.0D));
/*     */   }
/*     */   
/*     */   public Builder defaultValue(double defaultValue) {
/*  71 */     this.defaultValue = Double.valueOf(defaultValue);
/*  72 */     return this;
/*     */   }
/*     */   
/*     */   public Builder min(double min) {
/*  76 */     this.min = min;
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   public Builder max(double max) {
/*  81 */     this.max = max;
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public Builder range(double min, double max) {
/*  86 */     this.min = Math.min(min, max);
/*  87 */     this.max = Math.max(min, max);
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public Builder sliderMin(double min) {
/*  92 */     this.sliderMin = min;
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public Builder sliderMax(double max) {
/*  97 */     this.sliderMax = max;
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public Builder sliderRange(double min, double max) {
/* 102 */     this.sliderMin = min;
/* 103 */     this.sliderMax = max;
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   public Builder onSliderRelease() {
/* 108 */     this.onSliderRelease = true;
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public Builder decimalPlaces(int decimalPlaces) {
/* 113 */     this.decimalPlaces = decimalPlaces;
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public Builder noSlider() {
/* 118 */     this.noSlider = true;
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public DoubleSetting build() {
/* 123 */     return new DoubleSetting(this.name, this.description, this.defaultValue.doubleValue(), this.onChanged, this.onModuleActivated, this.visible, this.min, this.max, Math.max(this.sliderMin, this.min), Math.min(this.sliderMax, this.max), this.onSliderRelease, this.decimalPlaces, this.noSlider);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\settings\DoubleSetting$Builder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */