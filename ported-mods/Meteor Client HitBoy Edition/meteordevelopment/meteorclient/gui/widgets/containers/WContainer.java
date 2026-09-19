/*     */ package meteordevelopment.meteorclient.gui.widgets.containers;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_11905;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_312;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WContainer
/*     */   extends WWidget
/*     */ {
/*  24 */   public final List<Cell<?>> cells = new ArrayList<>();
/*     */   
/*     */   public <T extends WWidget> Cell<T> add(T widget) {
/*  27 */     ((WWidget)widget).parent = this;
/*  28 */     ((WWidget)widget).theme = this.theme;
/*     */     
/*  30 */     Cell<T> cell = (new Cell((WWidget)widget)).centerY();
/*  31 */     this.cells.add(cell);
/*     */     
/*  33 */     widget.init();
/*  34 */     invalidate();
/*     */     
/*  36 */     return cell;
/*     */   }
/*     */   
/*     */   public void clear() {
/*  40 */     if (!this.cells.isEmpty()) {
/*  41 */       this.cells.clear();
/*  42 */       invalidate();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void remove(Cell<?> cell) {
/*  47 */     if (this.cells.remove(cell)) invalidate();
/*     */   
/*     */   }
/*     */   
/*     */   public void move(double deltaX, double deltaY) {
/*  52 */     super.move(deltaX, deltaY);
/*  53 */     for (Cell<?> cell : this.cells) cell.move(deltaX, deltaY); 
/*     */   }
/*     */   
/*     */   public void moveCells(double deltaX, double deltaY) {
/*  57 */     for (Cell<?> cell : this.cells) {
/*  58 */       cell.move(deltaX, deltaY);
/*     */       
/*  60 */       class_312 mouse = MeteorClient.mc.field_1729;
/*  61 */       cell.widget().mouseMoved(mouse.method_1603(), mouse.method_1604(), mouse.method_1603(), mouse.method_1604());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFocused() {
/*  67 */     if (this.focused) return true;
/*     */     
/*  69 */     for (Cell<?> cell : this.cells) {
/*  70 */       if (cell.widget().isFocused()) {
/*  71 */         return true;
/*     */       }
/*     */     } 
/*  74 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculateSize() {
/*  81 */     for (Cell<?> cell : this.cells) cell.widget().calculateSize(); 
/*  82 */     super.calculateSize();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateSize() {
/*  87 */     this.width = 0.0D;
/*  88 */     this.height = 0.0D;
/*     */     
/*  90 */     for (Cell<?> cell : this.cells) {
/*  91 */       this.width = Math.max(this.width, cell.padLeft() + (cell.widget()).width + cell.padRight());
/*  92 */       this.height = Math.max(this.height, cell.padTop() + (cell.widget()).height + cell.padBottom());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void calculateWidgetPositions() {
/*  98 */     super.calculateWidgetPositions();
/*  99 */     for (Cell<?> cell : this.cells) cell.widget().calculateWidgetPositions();
/*     */   
/*     */   }
/*     */   
/*     */   protected void onCalculateWidgetPositions() {
/* 104 */     for (Cell<?> cell : this.cells) {
/* 105 */       cell.x = this.x + cell.padLeft();
/* 106 */       cell.y = this.y + cell.padTop();
/*     */       
/* 108 */       cell.width = this.width - cell.padLeft() - cell.padRight();
/* 109 */       cell.height = this.height - cell.padTop() - cell.padBottom();
/*     */       
/* 111 */       cell.alignWidget();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 119 */     if (super.render(renderer, mouseX, mouseY, delta)) return true;
/*     */     
/* 121 */     WView view = getView();
/* 122 */     double windowHeight = Utils.getWindowHeight();
/*     */     
/* 124 */     for (Cell<?> cell : this.cells) {
/* 125 */       WWidget widget = cell.widget();
/*     */       
/* 127 */       if (widget.y > windowHeight)
/* 128 */         break;  if (widget.y + widget.height <= 0.0D)
/*     */         continue; 
/* 130 */       if (shouldRenderWidget(widget, view)) renderWidget(widget, renderer, mouseX, mouseY, delta);
/*     */     
/*     */     } 
/* 133 */     return false;
/*     */   }
/*     */   
/*     */   protected void renderWidget(WWidget widget, GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 137 */     widget.render(renderer, mouseX, mouseY, delta);
/*     */   }
/*     */   
/*     */   private boolean shouldRenderWidget(WWidget widget, WView view) {
/* 141 */     if (view == null) return true; 
/* 142 */     if (!view.isWidgetInView(widget)) return false;
/*     */     
/* 144 */     if (widget.mouseOver && !view.mouseOver) {
/* 145 */       widget.mouseOver = false;
/*     */     }
/*     */     
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean propagateEvents(WWidget widget) {
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(class_11909 click, boolean doubled) {
/*     */     try {
/* 160 */       for (Cell<?> cell : this.cells) {
/* 161 */         if (propagateEvents(cell.widget()) && cell.widget().mouseClicked(click, doubled)) return true; 
/*     */       } 
/* 163 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */     
/* 165 */     return super.mouseClicked(click, doubled);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(class_11909 click) {
/*     */     try {
/* 171 */       for (Cell<?> cell : this.cells) {
/* 172 */         if (propagateEvents(cell.widget()) && cell.widget().mouseReleased(click)) return true; 
/*     */       } 
/* 174 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */     
/* 176 */     return super.mouseReleased(click);
/*     */   }
/*     */ 
/*     */   
/*     */   public void mouseMoved(double mouseX, double mouseY, double lastMouseX, double lastMouseY) {
/*     */     try {
/* 182 */       for (Cell<?> cell : this.cells) {
/* 183 */         if (propagateEvents(cell.widget())) cell.widget().mouseMoved(mouseX, mouseY, lastMouseX, lastMouseY); 
/*     */       } 
/* 185 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */     
/* 187 */     super.mouseMoved(mouseX, mouseY, lastMouseX, lastMouseY);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double amount) {
/*     */     try {
/* 193 */       for (Cell<?> cell : this.cells) {
/* 194 */         if (propagateEvents(cell.widget()) && cell.widget().mouseScrolled(amount)) return true; 
/*     */       } 
/* 196 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */     
/* 198 */     return super.mouseScrolled(amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(class_11908 input) {
/*     */     try {
/* 204 */       for (Cell<?> cell : this.cells) {
/* 205 */         if (propagateEvents(cell.widget()) && cell.widget().keyPressed(input)) return true; 
/*     */       } 
/* 207 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */     
/* 209 */     return onKeyPressed(input);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyRepeated(class_11908 input) {
/*     */     try {
/* 215 */       for (Cell<?> cell : this.cells) {
/* 216 */         if (propagateEvents(cell.widget()) && cell.widget().keyRepeated(input)) return true; 
/*     */       } 
/* 218 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */     
/* 220 */     return onKeyRepeated(input);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean charTyped(class_11905 input) {
/*     */     try {
/* 226 */       for (Cell<?> cell : this.cells) {
/* 227 */         if (propagateEvents(cell.widget()) && cell.widget().charTyped(input)) return true; 
/*     */       } 
/* 229 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */     
/* 231 */     return super.charTyped(input);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\containers\WContainer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */