/*     */ package meteordevelopment.meteorclient.gui.widgets.containers;
/*     */ 
/*     */ import java.util.function.Consumer;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.utils.WindowConfig;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WTriangle;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WWindow
/*     */   extends WVerticalList
/*     */ {
/*  23 */   public double padding = 8.0D;
/*     */   
/*     */   public Consumer<WContainer> beforeHeaderInit;
/*     */   
/*     */   public String id;
/*     */   
/*     */   public final WWidget icon;
/*     */   
/*     */   protected final String title;
/*     */   protected WHeader header;
/*     */   public WView view;
/*     */   protected boolean dragging;
/*     */   protected boolean expanded = true;
/*     */   protected boolean dragged;
/*  37 */   protected double animProgress = 1.0D;
/*     */   
/*     */   protected boolean moved = false;
/*     */   protected double movedX;
/*     */   protected double movedY;
/*     */   private boolean propagateEventsExpanded;
/*     */   
/*     */   public WWindow(WWidget icon, String title) {
/*  45 */     this.icon = icon;
/*  46 */     this.title = title;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  51 */     this.header = header(this.icon);
/*  52 */     this.header.theme = this.theme;
/*  53 */     super.<WHeader>add(this.header).expandWidgetX().widget();
/*     */     
/*  55 */     this.view = (WView)super.<WView>add(this.theme.view()).expandX().pad(this.padding).widget();
/*     */     
/*  57 */     if (this.id != null) {
/*  58 */       this.expanded = (this.theme.getWindowConfig(this.id)).expanded;
/*  59 */       this.animProgress = this.expanded ? 1.0D : 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract WHeader header(WWidget paramWWidget);
/*     */   
/*     */   public <T extends WWidget> Cell<T> add(T widget) {
/*  67 */     return this.view.add(widget);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  72 */     this.view.clear();
/*     */   }
/*     */   
/*     */   public void setExpanded(boolean expanded) {
/*  76 */     this.expanded = expanded;
/*     */     
/*  78 */     if (this.id != null) {
/*  79 */       WindowConfig config = this.theme.getWindowConfig(this.id);
/*  80 */       config.expanded = expanded;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateWidgetPositions() {
/*  86 */     if (this.id != null) {
/*  87 */       WindowConfig config = this.theme.getWindowConfig(this.id);
/*     */       
/*  89 */       if (config.x != -1.0D) {
/*  90 */         this.x = config.x;
/*     */         
/*  92 */         if (this.x + this.width > Utils.getWindowWidth()) {
/*  93 */           this.x = Utils.getWindowWidth() - this.width;
/*     */         }
/*     */       } 
/*     */       
/*  97 */       if (config.y != -1.0D) {
/*  98 */         this.y = config.y;
/*     */         
/* 100 */         if (this.y + this.height > Utils.getWindowHeight()) {
/* 101 */           this.y = Utils.getWindowHeight() - this.height;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     super.onCalculateWidgetPositions();
/*     */     
/* 108 */     if (this.moved) {
/* 109 */       move(this.movedX - this.x, this.movedY - this.y);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 115 */     if (!this.visible) return true;
/*     */     
/* 117 */     boolean scissor = ((this.animProgress != 0.0D && this.animProgress != 1.0D) || (this.expanded && this.animProgress != 1.0D));
/* 118 */     if (scissor) renderer.scissorStart(this.x, this.y, this.width, (this.height - this.header.height) * this.animProgress + this.header.height); 
/* 119 */     boolean toReturn = super.render(renderer, mouseX, mouseY, delta);
/* 120 */     if (scissor) renderer.scissorEnd();
/*     */     
/* 122 */     return toReturn;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderWidget(WWidget widget, GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 127 */     if (this.expanded || this.animProgress > 0.0D || widget instanceof WHeader) {
/* 128 */       widget.render(renderer, mouseX, mouseY, delta);
/*     */     }
/*     */     
/* 131 */     this.propagateEventsExpanded = this.expanded;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean propagateEvents(WWidget widget) {
/* 136 */     return (widget instanceof WHeader || this.propagateEventsExpanded);
/*     */   }
/*     */   
/*     */   protected abstract class WHeader extends WContainer {
/*     */     private final WWidget icon;
/*     */     private WTriangle triangle;
/*     */     private WHorizontalList list;
/*     */     
/*     */     public WHeader(WWidget icon) {
/* 145 */       this.icon = icon;
/*     */     }
/*     */ 
/*     */     
/*     */     public void init() {
/* 150 */       if (this.icon != null) {
/* 151 */         createList();
/* 152 */         add(this.icon).centerY();
/*     */       } 
/*     */       
/* 155 */       if (WWindow.this.beforeHeaderInit != null) {
/* 156 */         createList();
/* 157 */         WWindow.this.beforeHeaderInit.accept(this);
/*     */       } 
/*     */       
/* 160 */       add(this.theme.label(WWindow.this.title, true)).expandCellX().center().pad(4.0D);
/*     */       
/* 162 */       this.triangle = (WTriangle)add(this.theme.triangle()).pad(4.0D).right().centerY().widget();
/* 163 */       this.triangle.action = (() -> WWindow.this.setExpanded(!WWindow.this.expanded));
/*     */     }
/*     */     
/*     */     private void createList() {
/* 167 */       this.list = (WHorizontalList)add(this.theme.horizontalList()).expandX().widget();
/* 168 */       this.list.spacing = 0.0D;
/*     */     }
/*     */ 
/*     */     
/*     */     public <T extends WWidget> Cell<T> add(T widget) {
/* 173 */       if (this.list != null) return this.list.add(widget); 
/* 174 */       return super.add(widget);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onCalculateSize() {
/* 179 */       this.width = 0.0D;
/* 180 */       this.height = 0.0D;
/*     */       
/* 182 */       for (Cell<?> cell : this.cells) {
/* 183 */         double w = cell.padLeft() + (cell.widget()).width + cell.padRight();
/* 184 */         if (cell.widget() instanceof WTriangle) w *= 2.0D;
/*     */         
/* 186 */         this.width += w;
/* 187 */         this.height = Math.max(this.height, cell.padTop() + (cell.widget()).height + cell.padBottom());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onMouseClicked(class_11909 click, boolean doubled) {
/* 193 */       if (this.mouseOver && !doubled) {
/* 194 */         if (click.method_74245() == 1) { WWindow.this.setExpanded(!WWindow.this.expanded); }
/*     */         else
/* 196 */         { WWindow.this.dragging = true;
/* 197 */           WWindow.this.dragged = false; }
/*     */ 
/*     */         
/* 200 */         return true;
/*     */       } 
/*     */       
/* 203 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onMouseReleased(class_11909 click) {
/* 208 */       if (WWindow.this.dragging) {
/* 209 */         WWindow.this.dragging = false;
/*     */         
/* 211 */         if (!WWindow.this.dragged) WWindow.this.setExpanded(!WWindow.this.expanded);
/*     */       
/*     */       } 
/* 214 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onMouseMoved(double mouseX, double mouseY, double lastMouseX, double lastMouseY) {
/* 219 */       if (WWindow.this.dragging) {
/* 220 */         WWindow.this.move(mouseX - lastMouseX, mouseY - lastMouseY);
/*     */         
/* 222 */         WWindow.this.moved = true;
/* 223 */         WWindow.this.movedX = this.x;
/* 224 */         WWindow.this.movedY = this.y;
/*     */         
/* 226 */         if (WWindow.this.id != null) {
/* 227 */           WindowConfig config = this.theme.getWindowConfig(WWindow.this.id);
/*     */           
/* 229 */           config.x = this.x;
/* 230 */           config.y = this.y;
/*     */         } 
/*     */         
/* 233 */         WWindow.this.dragged = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 239 */       WWindow.this.animProgress += (WWindow.this.expanded ? true : -1) * delta * 14.0D;
/* 240 */       WWindow.this.animProgress = class_3532.method_15350(WWindow.this.animProgress, 0.0D, 1.0D);
/*     */       
/* 242 */       this.triangle.rotation = (1.0D - WWindow.this.animProgress) * -90.0D;
/*     */       
/* 244 */       return super.render(renderer, mouseX, mouseY, delta);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\containers\WWindow.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */