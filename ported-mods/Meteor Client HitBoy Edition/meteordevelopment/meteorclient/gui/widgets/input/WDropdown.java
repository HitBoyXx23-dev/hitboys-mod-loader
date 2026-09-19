/*     */ package meteordevelopment.meteorclient.gui.widgets.input;
/*     */ 
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WRoot;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WView;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WPressable;
/*     */ import net.minecraft.class_11905;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WDropdown<T>
/*     */   extends WPressable
/*     */ {
/*     */   public Runnable action;
/*     */   protected T[] values;
/*     */   protected T value;
/*     */   protected double maxValueWidth;
/*     */   protected WDropdownRoot root;
/*     */   protected boolean expanded;
/*     */   protected double animProgress;
/*     */   
/*     */   public WDropdown(T[] values, T value) {
/*  32 */     this.values = values;
/*     */     
/*  34 */     set(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  39 */     this.root = createRootWidget();
/*  40 */     this.root.theme = this.theme;
/*  41 */     this.root.spacing = 0.0D;
/*     */     
/*  43 */     for (int i = 0; i < this.values.length; i++) {
/*  44 */       WDropdownValue widget = createValueWidget();
/*  45 */       widget.theme = this.theme;
/*  46 */       widget.value = this.values[i];
/*     */       
/*  48 */       Cell<?> cell = this.root.add((WWidget)widget).padHorizontal(2.0D).expandWidgetX();
/*  49 */       if (i >= this.values.length - 1) cell.padBottom(2.0D);
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCalculateSize() {
/*  59 */     double pad = pad();
/*     */     
/*  61 */     this.maxValueWidth = 0.0D;
/*  62 */     for (T value : this.values) {
/*  63 */       double valueWidth = this.theme.textWidth(value.toString());
/*  64 */       this.maxValueWidth = Math.max(this.maxValueWidth, valueWidth);
/*     */     } 
/*     */     
/*  67 */     this.root.calculateSize();
/*     */     
/*  69 */     this.width = pad + this.maxValueWidth + pad + this.theme.textHeight() + pad;
/*  70 */     this.height = pad + this.theme.textHeight() + pad;
/*     */     
/*  72 */     this.root.width = this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateWidgetPositions() {
/*  77 */     super.onCalculateWidgetPositions();
/*     */     
/*  79 */     this.root.x = this.x;
/*  80 */     this.root.y = this.y + this.height;
/*     */     
/*  82 */     this.root.calculateWidgetPositions();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onPressed(int button) {
/*  87 */     this.expanded = !this.expanded;
/*  88 */     this.root.setFocused(this.expanded);
/*  89 */     setFocused(this.expanded);
/*     */   }
/*     */   
/*     */   public T get() {
/*  93 */     return this.value;
/*     */   }
/*     */   
/*     */   public void set(T value) {
/*  97 */     this.value = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(double deltaX, double deltaY) {
/* 102 */     super.move(deltaX, deltaY);
/*     */     
/* 104 */     this.root.move(deltaX, deltaY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 109 */     boolean render = super.render(renderer, mouseX, mouseY, delta);
/*     */     
/* 111 */     this.animProgress += (this.expanded ? true : -1) * delta * 14.0D;
/* 112 */     this.animProgress = class_3532.method_15350(this.animProgress, 0.0D, 1.0D);
/*     */     
/* 114 */     WView view = getView();
/* 115 */     boolean rootInView = (view == null || view.isWidgetInView((WWidget)this.root));
/*     */     
/* 117 */     if (!render && this.animProgress > 0.0D && rootInView) {
/* 118 */       renderer.absolutePost(() -> {
/*     */             renderer.scissorStart(this.x, this.y + this.height, this.width, this.root.height * this.animProgress);
/*     */             
/*     */             this.root.render(renderer, mouseX, mouseY, delta);
/*     */             renderer.scissorEnd();
/*     */           });
/*     */     }
/* 125 */     if (this.expanded && this.root.mouseOver) this.theme.disableHoverColor = true;
/*     */     
/* 127 */     return render;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMouseClicked(class_11909 click, boolean doubled) {
/* 134 */     boolean used = false;
/* 135 */     if (!this.mouseOver && !this.root.mouseOver) this.expanded = false;
/*     */     
/* 137 */     if (super.onMouseClicked(click, doubled)) used = true; 
/* 138 */     if (this.expanded && this.root.mouseClicked(click, doubled)) used = true;
/*     */     
/* 140 */     return used;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMouseReleased(class_11909 click) {
/* 145 */     if (super.onMouseReleased(click)) return true;
/*     */     
/* 147 */     return (this.expanded && this.root.mouseReleased(click));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMouseMoved(double mouseX, double mouseY, double lastMouseX, double lastMouseY) {
/* 152 */     super.onMouseMoved(mouseX, mouseY, lastMouseX, lastMouseY);
/*     */     
/* 154 */     if (this.expanded) this.root.mouseMoved(mouseX, mouseY, lastMouseX, lastMouseY);
/*     */   
/*     */   }
/*     */   
/*     */   public boolean onMouseScrolled(double amount) {
/* 159 */     if (super.onMouseScrolled(amount)) return true;
/*     */     
/* 161 */     if (this.expanded) {
/* 162 */       return this.root.mouseScrolled(amount);
/*     */     }
/*     */     
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKeyPressed(class_11908 input) {
/* 170 */     if (super.onKeyPressed(input)) return true;
/*     */     
/* 172 */     return (this.expanded && this.root.keyPressed(input));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKeyRepeated(class_11908 input) {
/* 177 */     if (super.onKeyRepeated(input)) return true;
/*     */     
/* 179 */     return (this.expanded && this.root.keyRepeated(input));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCharTyped(class_11905 input) {
/* 184 */     if (super.onCharTyped(input)) return true;
/*     */     
/* 186 */     return (this.expanded && this.root.charTyped(input));
/*     */   }
/*     */   
/*     */   protected abstract WDropdownRoot createRootWidget();
/*     */   
/*     */   protected abstract WDropdownValue createValueWidget();
/*     */   
/*     */   protected static abstract class WDropdownRoot extends WVerticalList implements WRoot {
/*     */     public void invalidate() {}
/*     */   }
/*     */   
/*     */   protected abstract class WDropdownValue extends WPressable {
/*     */     protected T value;
/*     */     
/*     */     protected void onPressed(int button) {
/* 201 */       boolean isNew = !WDropdown.this.value.equals(this.value);
/*     */       
/* 203 */       WDropdown.this.value = this.value;
/* 204 */       WDropdown.this.expanded = false;
/*     */       
/* 206 */       if (isNew && WDropdown.this.action != null) WDropdown.this.action.run(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\input\WDropdown.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */