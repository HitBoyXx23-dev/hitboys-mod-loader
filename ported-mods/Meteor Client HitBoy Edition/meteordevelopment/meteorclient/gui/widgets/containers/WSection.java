/*     */ package meteordevelopment.meteorclient.gui.widgets.containers;
/*     */ 
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_3532;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSection
/*     */   extends WVerticalList
/*     */ {
/*     */   public Runnable action;
/*     */   protected String title;
/*     */   protected boolean expanded;
/*     */   protected double animProgress;
/*     */   private WHeader header;
/*     */   protected final WWidget headerWidget;
/*     */   private double actualWidth;
/*     */   private double actualHeight;
/*  28 */   private double forcedHeight = -1.0D;
/*     */   private boolean firstTime = true;
/*     */   
/*     */   public WSection(String title, boolean expanded, WWidget headerWidget) {
/*  32 */     this.title = title;
/*  33 */     this.expanded = expanded;
/*  34 */     this.headerWidget = headerWidget;
/*     */     
/*  36 */     this.animProgress = expanded ? 1.0D : 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  41 */     this.header = createHeader();
/*  42 */     this.header.theme = this.theme;
/*     */     
/*  44 */     super.<WHeader>add(this.header).expandX();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends WWidget> Cell<T> add(T widget) {
/*  49 */     return super.<T>add(widget).padHorizontal(6.0D);
/*     */   }
/*     */   
/*     */   protected abstract WHeader createHeader();
/*     */   
/*     */   public void setExpanded(boolean expanded) {
/*  55 */     this.expanded = expanded;
/*     */   }
/*     */   
/*     */   public boolean isExpanded() {
/*  59 */     return this.expanded;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateSize() {
/*  64 */     if (this.forcedHeight == -1.0D) {
/*  65 */       super.onCalculateSize();
/*     */       
/*  67 */       this.actualWidth = this.width;
/*  68 */       this.actualHeight = this.height;
/*     */     } else {
/*     */       
/*  71 */       this.width = this.actualWidth;
/*  72 */       this.height = this.forcedHeight;
/*     */       
/*  74 */       if (this.animProgress == 1.0D) this.forcedHeight = -1.0D;
/*     */     
/*     */     } 
/*  77 */     if (this.firstTime) {
/*  78 */       this.firstTime = false;
/*     */       
/*  80 */       this.forcedHeight = (this.actualHeight - this.header.height) * this.animProgress + this.header.height;
/*  81 */       onCalculateSize();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/*  87 */     if (!this.visible) return true;
/*     */     
/*  89 */     double preProgress = this.animProgress;
/*     */     
/*  91 */     this.animProgress += (this.expanded ? true : -1) * delta * 14.0D;
/*  92 */     this.animProgress = class_3532.method_15350(this.animProgress, 0.0D, 1.0D);
/*     */     
/*  94 */     if (this.animProgress != preProgress) {
/*  95 */       this.forcedHeight = (this.actualHeight - this.header.height) * this.animProgress + this.header.height;
/*  96 */       invalidate();
/*     */     } 
/*     */     
/*  99 */     boolean scissor = ((this.animProgress != 0.0D && this.animProgress != 1.0D) || (this.expanded && this.animProgress != 1.0D));
/* 100 */     if (scissor) renderer.scissorStart(this.x, this.y, this.width, (this.height - this.header.height) * this.animProgress + this.header.height); 
/* 101 */     boolean toReturn = super.render(renderer, mouseX, mouseY, delta);
/* 102 */     if (scissor) renderer.scissorEnd();
/*     */     
/* 104 */     return toReturn;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderWidget(WWidget widget, GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 109 */     if (this.expanded || this.animProgress > 0.0D || widget instanceof WHeader) {
/* 110 */       widget.render(renderer, mouseX, mouseY, delta);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean propagateEvents(WWidget widget) {
/* 116 */     return (this.expanded || widget instanceof WHeader);
/*     */   }
/*     */   
/*     */   protected abstract class WHeader extends WHorizontalList {
/*     */     protected String title;
/*     */     
/*     */     public WHeader(String title) {
/* 123 */       this.title = title;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onMouseClicked(class_11909 click, boolean doubled) {
/* 128 */       if (this.mouseOver && click.method_74245() == 0 && !doubled) {
/* 129 */         onClick();
/* 130 */         return true;
/*     */       } 
/*     */       
/* 133 */       return false;
/*     */     }
/*     */     
/*     */     protected void onClick() {
/* 137 */       WSection.this.setExpanded(!WSection.this.expanded);
/*     */       
/* 139 */       if (WSection.this.action != null) WSection.this.action.run(); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\containers\WSection.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */