/*     */ package meteordevelopment.meteorclient.gui.utils;
/*     */ 
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Cell<T extends WWidget>
/*     */ {
/*     */   private final T widget;
/*     */   public double x;
/*     */   public double y;
/*     */   public double width;
/*     */   public double height;
/*  17 */   private AlignmentX alignX = AlignmentX.Left;
/*  18 */   private AlignmentY alignY = AlignmentY.Top;
/*     */   private double padTop;
/*     */   private double padRight;
/*     */   private double padBottom;
/*     */   private double padLeft;
/*     */   private double marginTop;
/*     */   private boolean expandWidgetX;
/*     */   private boolean expandWidgetY;
/*     */   public boolean expandCellX;
/*     */   @Nullable
/*     */   public String group;
/*     */   
/*     */   public Cell(T widget) {
/*  31 */     this.widget = widget;
/*     */   }
/*     */   
/*     */   public T widget() {
/*  35 */     return this.widget;
/*     */   }
/*     */   
/*     */   public void move(double deltaX, double deltaY) {
/*  39 */     this.x += deltaX;
/*  40 */     this.y += deltaY;
/*     */     
/*  42 */     this.widget.move(deltaX, deltaY);
/*     */   }
/*     */   
/*     */   public Cell<T> minWidth(double width) {
/*  46 */     ((WWidget)this.widget).minWidth = width;
/*  47 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Cell<T> centerX() {
/*  53 */     this.alignX = AlignmentX.Center;
/*  54 */     return this;
/*     */   }
/*     */   
/*     */   public Cell<T> right() {
/*  58 */     this.alignX = AlignmentX.Right;
/*  59 */     return this;
/*     */   }
/*     */   
/*     */   public Cell<T> centerY() {
/*  63 */     this.alignY = AlignmentY.Center;
/*  64 */     return this;
/*     */   }
/*     */   
/*     */   public Cell<T> bottom() {
/*  68 */     this.alignY = AlignmentY.Bottom;
/*  69 */     return this;
/*     */   }
/*     */   
/*     */   public Cell<T> center() {
/*  73 */     this.alignX = AlignmentX.Center;
/*  74 */     this.alignY = AlignmentY.Center;
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public Cell<T> top() {
/*  79 */     this.alignY = AlignmentY.Top;
/*  80 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Cell<T> padTop(double pad) {
/*  86 */     this.padTop = pad;
/*  87 */     return this;
/*     */   }
/*     */   public Cell<T> padRight(double pad) {
/*  90 */     this.padRight = pad;
/*  91 */     return this;
/*     */   }
/*     */   public Cell<T> padBottom(double pad) {
/*  94 */     this.padBottom = pad;
/*  95 */     return this;
/*     */   }
/*     */   public Cell<T> padLeft(double pad) {
/*  98 */     this.padLeft = pad;
/*  99 */     return this;
/*     */   }
/*     */   
/*     */   public Cell<T> padHorizontal(double pad) {
/* 103 */     this.padRight = this.padLeft = pad;
/* 104 */     return this;
/*     */   }
/*     */   public Cell<T> padVertical(double pad) {
/* 107 */     this.padTop = this.padBottom = pad;
/* 108 */     return this;
/*     */   }
/*     */   public Cell<T> pad(double pad) {
/* 111 */     this.padTop = this.padRight = this.padBottom = this.padLeft = pad;
/* 112 */     return this;
/*     */   }
/*     */   
/*     */   public double padTop() {
/* 116 */     return s(this.padTop);
/*     */   }
/*     */   public double padRight() {
/* 119 */     return s(this.padRight);
/*     */   }
/*     */   public double padBottom() {
/* 122 */     return s(this.padBottom);
/*     */   }
/*     */   public double padLeft() {
/* 125 */     return s(this.padLeft);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Cell<T> marginTop(double m) {
/* 131 */     this.marginTop = m;
/* 132 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Cell<T> expandWidgetX() {
/* 138 */     this.expandWidgetX = true;
/* 139 */     return this;
/*     */   }
/*     */   
/*     */   public Cell<T> expandWidgetY() {
/* 143 */     this.expandWidgetY = true;
/* 144 */     return this;
/*     */   }
/*     */   
/*     */   public Cell<T> expandCellX() {
/* 148 */     this.expandCellX = true;
/* 149 */     return this;
/*     */   }
/*     */   
/*     */   public Cell<T> expandX() {
/* 153 */     this.expandWidgetX = true;
/* 154 */     this.expandCellX = true;
/* 155 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Cell<T> group(String group) {
/* 162 */     this.group = group;
/* 163 */     return this;
/*     */   }
/*     */   
/*     */   public void alignWidget() {
/* 167 */     if (this.expandWidgetX) {
/* 168 */       ((WWidget)this.widget).x = this.x;
/* 169 */       ((WWidget)this.widget).width = this.width;
/*     */     } else {
/* 171 */       switch (this.alignX) { case Top:
/* 172 */           ((WWidget)this.widget).x = this.x; break;
/* 173 */         case Center: ((WWidget)this.widget).x = this.x + this.width / 2.0D - ((WWidget)this.widget).width / 2.0D; break;
/* 174 */         case Bottom: ((WWidget)this.widget).x = this.x + this.width - ((WWidget)this.widget).width;
/*     */           break; }
/*     */     
/*     */     } 
/* 178 */     if (this.expandWidgetY) {
/* 179 */       ((WWidget)this.widget).y = this.y;
/* 180 */       ((WWidget)this.widget).height = this.height;
/*     */     } else {
/* 182 */       switch (this.alignY) { case Top:
/* 183 */           ((WWidget)this.widget).y = this.y + s(this.marginTop); break;
/* 184 */         case Center: ((WWidget)this.widget).y = this.y + this.height / 2.0D - ((WWidget)this.widget).height / 2.0D; break;
/* 185 */         case Bottom: ((WWidget)this.widget).y = this.y + this.height - ((WWidget)this.widget).height;
/*     */           break; }
/*     */     
/*     */     } 
/*     */   }
/*     */   private double s(double value) {
/* 191 */     return ((WWidget)this.widget).theme.scale(value);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gu\\utils\Cell.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */