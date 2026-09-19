/*     */ package meteordevelopment.meteorclient.gui.widgets.input;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WDoubleEdit
/*     */   extends WHorizontalList
/*     */ {
/*     */   private double value;
/*     */   private final double min;
/*     */   private final double max;
/*     */   private final double sliderMin;
/*     */   private final double sliderMax;
/*     */   public int decimalPlaces;
/*     */   public boolean noSlider = false;
/*     */   public boolean small = false;
/*     */   public Runnable action;
/*     */   public Runnable actionOnRelease;
/*     */   private WTextBox textBox;
/*     */   private WSlider slider;
/*     */   
/*     */   public WDoubleEdit(double value, double min, double max, double sliderMin, double sliderMax, int decimalPlaces, boolean noSlider) {
/*  29 */     this.value = value;
/*  30 */     this.min = min;
/*  31 */     this.max = max;
/*  32 */     this.decimalPlaces = decimalPlaces;
/*  33 */     this.sliderMin = sliderMin;
/*  34 */     this.sliderMax = sliderMax;
/*     */     
/*  36 */     if (noSlider || (sliderMin == 0.0D && sliderMax == 0.0D)) this.noSlider = true;
/*     */   
/*     */   }
/*     */   
/*     */   public void init() {
/*  41 */     this.textBox = (WTextBox)add(this.theme.textBox(valueString(), this::filter)).minWidth(75.0D).widget();
/*     */     
/*  43 */     if (this.noSlider) {
/*  44 */       ((WButton)add((WWidget)this.theme.button("+")).widget()).action = (() -> setButton(get() + 1.0D));
/*  45 */       ((WButton)add((WWidget)this.theme.button("-")).widget()).action = (() -> setButton(get() - 1.0D));
/*     */     } else {
/*  47 */       this.slider = (WSlider)add(this.theme.slider(this.value, this.sliderMin, this.sliderMax)).minWidth(this.small ? (125.0D - this.spacing) : 200.0D).centerY().expandX().widget();
/*     */     } 
/*  49 */     this.textBox.actionOnUnfocused = (() -> { double lastValue = this.value; if (this.textBox.get().isEmpty()) { this.value = 0.0D; }
/*     */         else if (this.textBox.get().equals("-"))
/*     */         { this.value = 0.0D; }
/*     */         else if (this.textBox.get().equals("."))
/*     */         { this.value = 0.0D; }
/*     */         else if (this.textBox.get().equals("-."))
/*     */         { this.value = 0.0D; }
/*     */         else
/*     */         { try {
/*     */             this.value = Double.parseDouble(this.textBox.get());
/*  59 */           } catch (NumberFormatException numberFormatException) {} }
/*     */          double preValidationValue = this.value; if (this.value < this.min) {
/*     */           this.value = this.min;
/*     */         } else if (this.value > this.max) {
/*     */           this.value = this.max;
/*     */         } 
/*     */         if (this.value != preValidationValue)
/*     */           this.textBox.set(valueString()); 
/*     */         if (this.slider != null)
/*     */           this.slider.set(this.value); 
/*     */         if (this.value != lastValue) {
/*     */           if (this.action != null)
/*     */             this.action.run(); 
/*     */           if (this.actionOnRelease != null)
/*     */             this.actionOnRelease.run(); 
/*     */         } 
/*     */       });
/*  76 */     if (this.slider != null) {
/*  77 */       this.slider.action = (() -> {
/*     */           double lastValue = this.value;
/*     */           
/*     */           this.value = this.slider.get();
/*     */           this.textBox.set(valueString());
/*     */           if (this.action != null && this.value != lastValue) {
/*     */             this.action.run();
/*     */           }
/*     */         });
/*  86 */       this.slider.actionOnRelease = (() -> {
/*     */           if (this.actionOnRelease != null)
/*     */             this.actionOnRelease.run(); 
/*     */         });
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean filter(String text, char c) {
/*  94 */     boolean good, validate = true;
/*     */     
/*  96 */     if (c == '-' && !text.contains("-") && this.textBox.cursor == 0) {
/*  97 */       good = true;
/*  98 */       validate = false;
/*     */     }
/* 100 */     else if (c == '.' && !text.contains(".")) {
/* 101 */       good = true;
/* 102 */       if (text.isEmpty()) validate = false; 
/*     */     } else {
/* 104 */       good = Character.isDigit(c);
/*     */     } 
/* 106 */     if (good && validate) {
/*     */       try {
/* 108 */         Double.parseDouble(text + text);
/* 109 */       } catch (NumberFormatException ignored) {
/* 110 */         good = false;
/*     */       } 
/*     */     }
/*     */     
/* 114 */     return good;
/*     */   }
/*     */   
/*     */   private void setButton(double v) {
/* 118 */     if (this.value == v)
/*     */       return; 
/* 120 */     if (v < this.min) { this.value = this.min; }
/* 121 */     else { this.value = Math.min(v, this.max); }
/*     */     
/* 123 */     if (this.value == v) {
/* 124 */       this.textBox.set(valueString());
/* 125 */       if (this.slider != null) this.slider.set(this.value);
/*     */       
/* 127 */       if (this.action != null) this.action.run(); 
/* 128 */       if (this.actionOnRelease != null) this.actionOnRelease.run(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public double get() {
/* 133 */     return this.value;
/*     */   }
/*     */   
/*     */   public void set(double value) {
/* 137 */     this.value = value;
/*     */     
/* 139 */     this.textBox.set(valueString());
/* 140 */     if (this.slider != null) this.slider.set(value); 
/*     */   }
/*     */   
/*     */   private String valueString() {
/* 144 */     return String.format(Locale.US, "%." + this.decimalPlaces + "f", new Object[] { Double.valueOf(this.value) });
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\input\WDoubleEdit.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */