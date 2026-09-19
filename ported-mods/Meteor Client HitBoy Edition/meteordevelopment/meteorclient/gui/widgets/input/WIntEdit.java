/*     */ package meteordevelopment.meteorclient.gui.widgets.input;
/*     */ 
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WIntEdit
/*     */   extends WHorizontalList
/*     */ {
/*     */   private int value;
/*     */   public final int min;
/*     */   public final int max;
/*     */   private final int sliderMin;
/*     */   private final int sliderMax;
/*     */   public boolean noSlider = false;
/*     */   public boolean small = false;
/*     */   public Runnable action;
/*     */   public Runnable actionOnRelease;
/*     */   private WTextBox textBox;
/*     */   private WSlider slider;
/*     */   
/*     */   public WIntEdit(int value, int min, int max, int sliderMin, int sliderMax, boolean noSlider) {
/*  25 */     this.value = value;
/*  26 */     this.min = min;
/*  27 */     this.max = max;
/*  28 */     this.sliderMin = sliderMin;
/*  29 */     this.sliderMax = sliderMax;
/*     */     
/*  31 */     if (noSlider || (sliderMin == 0 && sliderMax == 0)) this.noSlider = true;
/*     */   
/*     */   }
/*     */   
/*     */   public void init() {
/*  36 */     this.textBox = (WTextBox)add(this.theme.textBox(Integer.toString(this.value), this::filter)).minWidth(75.0D).widget();
/*     */     
/*  38 */     if (this.noSlider) {
/*  39 */       ((WButton)add((WWidget)this.theme.button("+")).widget()).action = (() -> setButton(get() + 1));
/*  40 */       ((WButton)add((WWidget)this.theme.button("-")).widget()).action = (() -> setButton(get() - 1));
/*     */     } else {
/*     */       
/*  43 */       this.slider = (WSlider)add(this.theme.slider(this.value, this.sliderMin, this.sliderMax)).minWidth(this.small ? (125.0D - this.spacing) : 200.0D).centerY().expandX().widget();
/*     */     } 
/*     */     
/*  46 */     this.textBox.actionOnUnfocused = (() -> {
/*     */         int lastValue = this.value; if (this.textBox.get().isEmpty()) {
/*     */           this.value = 0;
/*     */         } else if (this.textBox.get().equals("-")) {
/*     */           this.value = 0;
/*     */         } else {
/*     */           try {
/*     */             this.value = Integer.parseInt(this.textBox.get());
/*  54 */           } catch (NumberFormatException numberFormatException) {}
/*     */         } 
/*     */         if (this.slider != null)
/*     */           this.slider.set(this.value); 
/*     */         if (this.value != lastValue) {
/*     */           if (this.action != null)
/*     */             this.action.run(); 
/*     */           if (this.actionOnRelease != null)
/*     */             this.actionOnRelease.run(); 
/*     */         } 
/*     */       });
/*  65 */     if (this.slider != null) {
/*  66 */       this.slider.action = (() -> {
/*     */           int lastValue = this.value;
/*     */           
/*     */           this.value = (int)Math.round(this.slider.get());
/*     */           this.textBox.set(Integer.toString(this.value));
/*     */           if (this.action != null && this.value != lastValue) {
/*     */             this.action.run();
/*     */           }
/*     */         });
/*  75 */       this.slider.actionOnRelease = (() -> {
/*     */           if (this.actionOnRelease != null)
/*     */             this.actionOnRelease.run(); 
/*     */         });
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean filter(String text, char c) {
/*  83 */     boolean good, validate = true;
/*     */     
/*  85 */     if (c == '-' && !text.contains("-") && this.textBox.cursor == 0) {
/*  86 */       good = true;
/*  87 */       validate = false;
/*     */     } else {
/*  89 */       good = Character.isDigit(c);
/*     */     } 
/*  91 */     if (good && validate) {
/*     */       try {
/*  93 */         Integer.parseInt(text + text);
/*  94 */       } catch (NumberFormatException ignored) {
/*  95 */         good = false;
/*     */       } 
/*     */     }
/*     */     
/*  99 */     return good;
/*     */   }
/*     */   
/*     */   private void setButton(int v) {
/* 103 */     if (this.value == v)
/*     */       return; 
/* 105 */     if (v < this.min) { this.value = this.min; }
/* 106 */     else { this.value = Math.min(v, this.max); }
/*     */     
/* 108 */     if (this.value == v) {
/* 109 */       this.textBox.set(Integer.toString(this.value));
/* 110 */       if (this.slider != null) this.slider.set(this.value);
/*     */       
/* 112 */       if (this.action != null) this.action.run(); 
/* 113 */       if (this.actionOnRelease != null) this.actionOnRelease.run(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public int get() {
/* 118 */     return this.value;
/*     */   }
/*     */   
/*     */   public void set(int value) {
/* 122 */     this.value = value;
/*     */     
/* 124 */     this.textBox.set(Integer.toString(value));
/* 125 */     if (this.slider != null) this.slider.set(value); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\input\WIntEdit.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */