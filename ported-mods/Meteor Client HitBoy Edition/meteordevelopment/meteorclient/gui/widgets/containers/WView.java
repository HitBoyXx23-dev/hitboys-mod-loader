/*     */ package meteordevelopment.meteorclient.gui.widgets.containers;
/*     */ 
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WView
/*     */   extends WVerticalList
/*     */ {
/*  17 */   public double maxHeight = Double.MAX_VALUE;
/*     */   
/*     */   public boolean scrollOnlyWhenMouseOver = true;
/*     */   
/*     */   public boolean hasScrollBar = true;
/*     */   
/*     */   protected boolean canScroll;
/*     */   
/*     */   private double actualHeight;
/*     */   private double scroll;
/*     */   private double targetScroll;
/*     */   private boolean moveAfterPositionWidgets;
/*     */   protected boolean handleMouseOver;
/*     */   
/*     */   public void init() {
/*  32 */     this.maxHeight = Utils.getWindowHeight() - this.theme.scale(128.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateSize() {
/*  37 */     boolean couldScroll = this.canScroll;
/*  38 */     this.canScroll = false;
/*  39 */     this.widthRemove = 0.0D;
/*     */     
/*  41 */     super.onCalculateSize();
/*     */     
/*  43 */     if (this.height > this.maxHeight) {
/*  44 */       this.actualHeight = this.height;
/*  45 */       this.height = this.maxHeight;
/*  46 */       this.canScroll = true;
/*     */       
/*  48 */       if (this.hasScrollBar) {
/*  49 */         this.widthRemove = handleWidth() * 2.0D;
/*  50 */         this.width += this.widthRemove;
/*     */       } 
/*     */       
/*  53 */       if (couldScroll) this.moveAfterPositionWidgets = true;
/*     */     
/*     */     } else {
/*  56 */       this.actualHeight = this.height;
/*  57 */       this.scroll = 0.0D;
/*  58 */       this.targetScroll = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateWidgetPositions() {
/*  64 */     super.onCalculateWidgetPositions();
/*     */     
/*  66 */     if (this.moveAfterPositionWidgets) {
/*  67 */       this.scroll = class_3532.method_15350(this.scroll, 0.0D, this.actualHeight - this.height);
/*  68 */       this.targetScroll = this.scroll;
/*     */       
/*  70 */       moveCells(0.0D, -this.scroll);
/*     */       
/*  72 */       this.moveAfterPositionWidgets = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMouseClicked(class_11909 click, boolean doubled) {
/*  78 */     if (this.handleMouseOver && click.method_74245() == 0 && !doubled) {
/*  79 */       setFocused(true);
/*  80 */       return true;
/*     */     } 
/*     */     
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMouseReleased(class_11909 click) {
/*  88 */     if (this.focused) setFocused(false);
/*     */     
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMouseMoved(double mouseX, double mouseY, double lastMouseX, double lastMouseY) {
/*  95 */     this.handleMouseOver = false;
/*     */     
/*  97 */     if (this.canScroll && this.hasScrollBar) {
/*  98 */       double x = handleX();
/*  99 */       double y = handleY();
/*     */       
/* 101 */       if (mouseX >= x && mouseX <= x + handleWidth() && mouseY >= y && mouseY <= y + handleHeight()) {
/* 102 */         this.handleMouseOver = true;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     if (this.focused) {
/* 107 */       double preScroll = this.scroll;
/* 108 */       double mouseDelta = mouseY - lastMouseY;
/*     */ 
/*     */ 
/*     */       
/* 112 */       this.scroll += Math.round(mouseDelta * (this.actualHeight - handleHeight() / 2.0D) / this.height);
/* 113 */       this.scroll = class_3532.method_15350(this.scroll, 0.0D, this.actualHeight - this.height);
/*     */       
/* 115 */       this.targetScroll = this.scroll;
/*     */       
/* 117 */       double delta = this.scroll - preScroll;
/* 118 */       if (delta != 0.0D) moveCells(0.0D, -delta);
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean onMouseScrolled(double amount) {
/* 124 */     if (!this.scrollOnlyWhenMouseOver || this.mouseOver) {
/* 125 */       double max = this.actualHeight - this.height;
/*     */       
/* 127 */       this.targetScroll -= Math.round(this.theme.scale(amount * 40.0D));
/* 128 */       this.targetScroll = class_3532.method_15350(this.targetScroll, 0.0D, max);
/*     */ 
/*     */       
/* 131 */       return (this.targetScroll > 0.0D && this.targetScroll < max);
/*     */     } 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 139 */     updateScroll(delta);
/*     */     
/* 141 */     if (this.canScroll) renderer.scissorStart(this.x, this.y, this.width, this.height); 
/* 142 */     boolean render = super.render(renderer, mouseX, mouseY, delta);
/* 143 */     if (this.canScroll) renderer.scissorEnd();
/*     */     
/* 145 */     return render;
/*     */   }
/*     */   
/*     */   private void updateScroll(double delta) {
/* 149 */     double preScroll = this.scroll;
/* 150 */     double max = this.actualHeight - this.height;
/*     */     
/* 152 */     if (Math.abs(this.targetScroll - this.scroll) < 1.0D) { this.scroll = this.targetScroll; }
/* 153 */     else if (this.targetScroll > this.scroll)
/* 154 */     { this.scroll += Math.round(this.theme.scale(delta * 300.0D + delta * 100.0D * Math.abs(this.targetScroll - this.scroll) / 10.0D));
/* 155 */       if (this.scroll > this.targetScroll) this.scroll = this.targetScroll;
/*     */        }
/* 157 */     else if (this.targetScroll < this.scroll)
/* 158 */     { this.scroll -= Math.round(this.theme.scale(delta * 300.0D + delta * 100.0D * Math.abs(this.targetScroll - this.scroll) / 10.0D));
/* 159 */       if (this.scroll < this.targetScroll) this.scroll = this.targetScroll;
/*     */        }
/*     */     
/* 162 */     this.scroll = class_3532.method_15350(this.scroll, 0.0D, max);
/*     */     
/* 164 */     double change = this.scroll - preScroll;
/* 165 */     if (change != 0.0D) moveCells(0.0D, -change);
/*     */   
/*     */   }
/*     */   
/*     */   protected boolean propagateEvents(WWidget widget) {
/* 170 */     if (widget.isFocused()) return true;
/*     */ 
/*     */     
/* 173 */     if (widget instanceof WView) return isWidgetInView(widget);
/*     */ 
/*     */     
/* 176 */     return (this.mouseOver && isWidgetInView(widget));
/*     */   }
/*     */   
/*     */   protected double handleWidth() {
/* 180 */     return this.theme.scale(6.0D);
/*     */   }
/*     */   
/*     */   protected double handleHeight() {
/* 184 */     return this.height / this.actualHeight * this.height;
/*     */   }
/*     */   
/*     */   protected double handleX() {
/* 188 */     return this.x + this.width - handleWidth();
/*     */   }
/*     */   
/*     */   protected double handleY() {
/* 192 */     return this.y + (this.height - handleHeight()) * this.scroll / (this.actualHeight - this.height);
/*     */   }
/*     */   
/*     */   public boolean isWidgetInView(WWidget widget) {
/* 196 */     return (widget.y < this.y + this.height && widget.y + widget.height > this.y);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\containers\WView.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */