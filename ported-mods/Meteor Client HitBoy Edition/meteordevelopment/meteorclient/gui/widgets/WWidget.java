/*     */ package meteordevelopment.meteorclient.gui.widgets;
/*     */ 
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.utils.BaseWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WView;
/*     */ import net.minecraft.class_11905;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WWidget
/*     */   implements BaseWidget
/*     */ {
/*     */   public boolean visible = true;
/*     */   public GuiTheme theme;
/*     */   public double x;
/*     */   public double y;
/*     */   public double width;
/*     */   public double height;
/*     */   public double minWidth;
/*     */   public WWidget parent;
/*     */   public String tooltip;
/*     */   public boolean mouseOver;
/*     */   public boolean focused;
/*     */   protected boolean instantTooltips;
/*     */   protected double mouseOverTimer;
/*     */   
/*     */   public void init() {}
/*     */   
/*     */   public void move(double deltaX, double deltaY) {
/*  35 */     this.x = Math.round(this.x + deltaX);
/*  36 */     this.y = Math.round(this.y + deltaY);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiTheme getTheme() {
/*  41 */     return this.theme;
/*     */   }
/*     */   
/*     */   public double pad() {
/*  45 */     return this.theme.pad();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculateSize() {
/*  51 */     onCalculateSize();
/*     */     
/*  53 */     double minWidth = this.theme.scale(this.minWidth);
/*  54 */     if (this.width < minWidth) this.width = minWidth;
/*     */     
/*  56 */     this.width = Math.round(this.width);
/*  57 */     this.height = Math.round(this.height);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateSize() {}
/*     */ 
/*     */   
/*     */   public void calculateWidgetPositions() {
/*  65 */     this.x = Math.round(this.x);
/*  66 */     this.y = Math.round(this.y);
/*     */     
/*  68 */     onCalculateWidgetPositions();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCalculateWidgetPositions() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/*  78 */     if (!this.visible) return true;
/*     */     
/*  80 */     if (isOver(mouseX, mouseY)) {
/*  81 */       this.mouseOverTimer += delta;
/*     */       
/*  83 */       if ((this.instantTooltips || this.mouseOverTimer >= 1.0D) && this.tooltip != null) {
/*  84 */         WView view = getView();
/*  85 */         if (view == null || view.mouseOver) renderer.tooltip(this.tooltip);
/*     */       
/*     */       } 
/*     */     } else {
/*  89 */       this.mouseOverTimer = 0.0D;
/*     */     } 
/*     */     
/*  92 */     onRender(renderer, mouseX, mouseY, delta);
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {}
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(class_11909 click, boolean doubled) {
/* 101 */     return onMouseClicked(click, doubled);
/*     */   } public boolean onMouseClicked(class_11909 click, boolean doubled) {
/* 103 */     return false;
/*     */   }
/*     */   public boolean mouseReleased(class_11909 click) {
/* 106 */     return onMouseReleased(click);
/*     */   } public boolean onMouseReleased(class_11909 click) {
/* 108 */     return false;
/*     */   }
/*     */   public void mouseMoved(double mouseX, double mouseY, double lastMouseX, double lastMouseY) {
/* 111 */     this.mouseOver = isOver(mouseX, mouseY);
/* 112 */     onMouseMoved(mouseX, mouseY, lastMouseX, lastMouseY);
/*     */   }
/*     */   public void onMouseMoved(double mouseX, double mouseY, double lastMouseX, double lastMouseY) {}
/*     */   
/*     */   public boolean mouseScrolled(double amount) {
/* 117 */     return onMouseScrolled(amount);
/*     */   } public boolean onMouseScrolled(double amount) {
/* 119 */     return false;
/*     */   }
/*     */   public boolean keyPressed(class_11908 input) {
/* 122 */     return onKeyPressed(input);
/*     */   } public boolean onKeyPressed(class_11908 input) {
/* 124 */     return false;
/*     */   }
/*     */   public boolean keyRepeated(class_11908 input) {
/* 127 */     return onKeyRepeated(input);
/*     */   } public boolean onKeyRepeated(class_11908 input) {
/* 129 */     return false;
/*     */   }
/*     */   public boolean charTyped(class_11905 input) {
/* 132 */     return onCharTyped(input);
/*     */   } public boolean onCharTyped(class_11905 input) {
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 139 */     WWidget root = getRoot();
/* 140 */     if (root != null) root.invalidate(); 
/*     */   }
/*     */   
/*     */   protected WWidget getRoot() {
/* 144 */     return (this.parent != null) ? this.parent.getRoot() : ((this instanceof WRoot) ? this : null);
/*     */   }
/*     */   
/*     */   public WView getView() {
/* 148 */     return (this instanceof WView) ? (WView)this : ((this.parent != null) ? this.parent.getView() : null);
/*     */   }
/*     */   
/*     */   public boolean isOver(double x, double y) {
/* 152 */     return (x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height);
/*     */   }
/*     */   
/*     */   public boolean isFocused() {
/* 156 */     return this.focused;
/*     */   }
/*     */   
/*     */   public void setFocused(boolean focused) {
/* 160 */     if (this.focused != focused) this.focused = focused; 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WWidget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */