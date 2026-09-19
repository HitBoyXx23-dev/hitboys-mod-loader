/*     */ package meteordevelopment.meteorclient.gui.widgets.containers;
/*     */ 
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.utils.WindowConfig;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WTriangle;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WHeader
/*     */   extends WContainer
/*     */ {
/*     */   private final WWidget icon;
/*     */   private WTriangle triangle;
/*     */   private WHorizontalList list;
/*     */   
/*     */   public WHeader(WWidget icon) {
/* 145 */     this.icon = icon;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 150 */     if (this.icon != null) {
/* 151 */       createList();
/* 152 */       add(this.icon).centerY();
/*     */     } 
/*     */     
/* 155 */     if (WWindow.this.beforeHeaderInit != null) {
/* 156 */       createList();
/* 157 */       WWindow.this.beforeHeaderInit.accept(this);
/*     */     } 
/*     */     
/* 160 */     add(this.theme.label(WWindow.this.title, true)).expandCellX().center().pad(4.0D);
/*     */     
/* 162 */     this.triangle = (WTriangle)add(this.theme.triangle()).pad(4.0D).right().centerY().widget();
/* 163 */     this.triangle.action = (() -> WWindow.this.setExpanded(!WWindow.this.expanded));
/*     */   }
/*     */   
/*     */   private void createList() {
/* 167 */     this.list = (WHorizontalList)add(this.theme.horizontalList()).expandX().widget();
/* 168 */     this.list.spacing = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends WWidget> Cell<T> add(T widget) {
/* 173 */     if (this.list != null) return this.list.add(widget); 
/* 174 */     return super.add(widget);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onCalculateSize() {
/* 179 */     this.width = 0.0D;
/* 180 */     this.height = 0.0D;
/*     */     
/* 182 */     for (Cell<?> cell : this.cells) {
/* 183 */       double w = cell.padLeft() + (cell.widget()).width + cell.padRight();
/* 184 */       if (cell.widget() instanceof WTriangle) w *= 2.0D;
/*     */       
/* 186 */       this.width += w;
/* 187 */       this.height = Math.max(this.height, cell.padTop() + (cell.widget()).height + cell.padBottom());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMouseClicked(class_11909 click, boolean doubled) {
/* 193 */     if (this.mouseOver && !doubled) {
/* 194 */       if (click.method_74245() == 1) { WWindow.this.setExpanded(!WWindow.this.expanded); }
/*     */       else
/* 196 */       { WWindow.this.dragging = true;
/* 197 */         WWindow.this.dragged = false; }
/*     */ 
/*     */       
/* 200 */       return true;
/*     */     } 
/*     */     
/* 203 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMouseReleased(class_11909 click) {
/* 208 */     if (WWindow.this.dragging) {
/* 209 */       WWindow.this.dragging = false;
/*     */       
/* 211 */       if (!WWindow.this.dragged) WWindow.this.setExpanded(!WWindow.this.expanded);
/*     */     
/*     */     } 
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onMouseMoved(double mouseX, double mouseY, double lastMouseX, double lastMouseY) {
/* 219 */     if (WWindow.this.dragging) {
/* 220 */       WWindow.this.move(mouseX - lastMouseX, mouseY - lastMouseY);
/*     */       
/* 222 */       WWindow.this.moved = true;
/* 223 */       WWindow.this.movedX = this.x;
/* 224 */       WWindow.this.movedY = this.y;
/*     */       
/* 226 */       if (WWindow.this.id != null) {
/* 227 */         WindowConfig config = this.theme.getWindowConfig(WWindow.this.id);
/*     */         
/* 229 */         config.x = this.x;
/* 230 */         config.y = this.y;
/*     */       } 
/*     */       
/* 233 */       WWindow.this.dragged = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 239 */     WWindow.this.animProgress += (WWindow.this.expanded ? true : -1) * delta * 14.0D;
/* 240 */     WWindow.this.animProgress = class_3532.method_15350(WWindow.this.animProgress, 0.0D, 1.0D);
/*     */     
/* 242 */     this.triangle.rotation = (1.0D - WWindow.this.animProgress) * -90.0D;
/*     */     
/* 244 */     return super.render(renderer, mouseX, mouseY, delta);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\containers\WWindow$WHeader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */