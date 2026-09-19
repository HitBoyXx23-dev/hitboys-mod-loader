/*     */ package meteordevelopment.meteorclient.gui.widgets.input;
/*     */ 
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSlider
/*     */   extends WWidget
/*     */ {
/*     */   public Runnable action;
/*     */   public Runnable actionOnRelease;
/*     */   protected double value;
/*     */   protected double min;
/*     */   protected double max;
/*     */   protected double scrollHandleX;
/*     */   protected double scrollHandleY;
/*     */   protected double scrollHandleH;
/*     */   protected boolean scrollHandleMouseOver;
/*     */   protected boolean handleMouseOver;
/*     */   protected boolean dragging;
/*     */   protected double valueAtDragStart;
/*     */   
/*     */   public WSlider(double value, double min, double max) {
/*  28 */     this.value = class_3532.method_15350(value, min, max);
/*  29 */     this.min = min;
/*  30 */     this.max = max;
/*     */   }
/*     */   
/*     */   protected double handleSize() {
/*  34 */     return this.theme.textHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateSize() {
/*  39 */     double s = handleSize();
/*     */     
/*  41 */     this.width = s;
/*  42 */     this.height = s;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMouseClicked(class_11909 click, boolean doubled) {
/*  47 */     if (this.mouseOver && !doubled) {
/*  48 */       this.valueAtDragStart = this.value;
/*  49 */       double handleSize = handleSize();
/*     */       
/*  51 */       double valueWidth = click.comp_4798() - this.x + handleSize / 2.0D;
/*  52 */       set(valueWidth / (this.width - handleSize) * (this.max - this.min) + this.min);
/*  53 */       if (this.action != null) this.action.run();
/*     */       
/*  55 */       this.dragging = true;
/*  56 */       setFocused(true);
/*  57 */       return true;
/*     */     } 
/*     */     
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMouseMoved(double mouseX, double mouseY, double lastMouseX, double lastMouseY) {
/*  65 */     double valueWidth = valueWidth();
/*  66 */     double s = handleSize();
/*  67 */     double s2 = s / 2.0D;
/*     */     
/*  69 */     double x = this.x + s2 + valueWidth - this.height / 2.0D;
/*  70 */     this.handleMouseOver = (mouseX >= x && mouseX <= x + this.height && mouseY >= this.y && mouseY <= this.y + this.height);
/*     */     
/*  72 */     if (!this.scrollHandleMouseOver) {
/*  73 */       this.scrollHandleX = x;
/*  74 */       this.scrollHandleY = this.y;
/*  75 */       this.scrollHandleH = this.height;
/*  76 */       if (this.handleMouseOver) {
/*  77 */         this.scrollHandleMouseOver = true;
/*     */       }
/*     */     } else {
/*  80 */       this.scrollHandleMouseOver = (mouseX >= this.scrollHandleX && mouseX <= this.scrollHandleX + this.scrollHandleH && mouseY >= this.scrollHandleY && mouseY <= this.scrollHandleY + this.scrollHandleH);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     boolean mouseOverX = (mouseX >= this.x + s2 && mouseX <= this.x + s2 + this.width - s);
/*  87 */     this.mouseOver = (mouseOverX && mouseY >= this.y && mouseY <= this.y + this.height);
/*     */     
/*  89 */     if (this.dragging) {
/*  90 */       if (mouseOverX) {
/*  91 */         valueWidth += mouseX - lastMouseX;
/*  92 */         valueWidth = class_3532.method_15350(valueWidth, 0.0D, this.width - s);
/*     */         
/*  94 */         set(valueWidth / (this.width - s) * (this.max - this.min) + this.min);
/*  95 */         if (this.action != null) this.action.run();
/*     */       
/*  97 */       } else if (this.value > this.min && mouseX < this.x + s2) {
/*  98 */         this.value = this.min;
/*  99 */         if (this.action != null) this.action.run(); 
/* 100 */       } else if (this.value < this.max && mouseX > this.x + s2 + this.width - s) {
/* 101 */         this.value = this.max;
/* 102 */         if (this.action != null) this.action.run();
/*     */       
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMouseReleased(class_11909 click) {
/* 110 */     if (this.dragging) {
/* 111 */       if (this.value != this.valueAtDragStart && this.actionOnRelease != null) {
/* 112 */         this.actionOnRelease.run();
/*     */       }
/*     */       
/* 115 */       this.dragging = false;
/* 116 */       setFocused(false);
/* 117 */       return true;
/*     */     } 
/*     */     
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMouseScrolled(double amount) {
/* 128 */     if (!this.scrollHandleMouseOver && this.handleMouseOver) {
/* 129 */       this.scrollHandleX = this.x;
/* 130 */       this.scrollHandleY = this.y;
/* 131 */       this.scrollHandleH = this.height;
/* 132 */       this.scrollHandleMouseOver = true;
/*     */     } 
/*     */     
/* 135 */     if (this.scrollHandleMouseOver) {
/* 136 */       if (this.parent instanceof WIntEdit) {
/* 137 */         set(this.value + amount);
/*     */       } else {
/*     */         
/* 140 */         set(this.value + 0.05D * amount);
/*     */       } 
/*     */       
/* 143 */       if (this.action != null) this.action.run(); 
/* 144 */       return true;
/*     */     } 
/*     */     
/* 147 */     return false;
/*     */   }
/*     */   
/*     */   public void set(double value) {
/* 151 */     this.value = class_3532.method_15350(value, this.min, this.max);
/*     */   }
/*     */   
/*     */   public double get() {
/* 155 */     return this.value;
/*     */   }
/*     */   
/*     */   protected double valueWidth() {
/* 159 */     double valuePercentage = (this.value - this.min) / (this.max - this.min);
/* 160 */     return valuePercentage * (this.width - handleSize());
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\input\WSlider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */