/*    */ package meteordevelopment.meteorclient.gui.renderer;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*    */ import meteordevelopment.meteorclient.renderer.MeshBuilder;
/*    */ import meteordevelopment.meteorclient.renderer.MeshRenderer;
/*    */ import meteordevelopment.meteorclient.renderer.MeteorRenderPipelines;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_11909;
/*    */ import net.minecraft.class_310;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiDebugRenderer
/*    */ {
/* 20 */   private static final Color CELL_COLOR = new Color(25, 225, 25);
/* 21 */   private static final Color WIDGET_COLOR = new Color(25, 25, 225);
/*    */   
/* 23 */   private final MeshBuilder mesh = new MeshBuilder(MeteorRenderPipelines.WORLD_COLORED_LINES);
/*    */   
/*    */   public void render(WWidget widget) {
/* 26 */     if (widget == null)
/*    */       return; 
/* 28 */     this.mesh.begin();
/* 29 */     renderWidget(widget);
/* 30 */     this.mesh.end();
/*    */     
/* 32 */     MeshRenderer.begin()
/* 33 */       .attachments(class_310.method_1551().method_1522())
/* 34 */       .pipeline(MeteorRenderPipelines.WORLD_COLORED_LINES)
/* 35 */       .mesh(this.mesh)
/* 36 */       .end();
/*    */   }
/*    */   
/*    */   public void mouseReleased(WWidget widget, class_11909 click, int i) {
/* 40 */     if (widget == null)
/*    */       return; 
/* 42 */     MeteorClient.LOG.info("{} {}", widget.getClass(), Integer.valueOf(i));
/*    */     
/* 44 */     if (widget instanceof WContainer) { WContainer container = (WContainer)widget;
/* 45 */       for (Cell<?> cell : (Iterable<Cell<?>>)container.cells) {
/* 46 */         if (cell.widget().isOver(click.comp_4798(), click.comp_4799())) {
/* 47 */           mouseReleased(cell.widget(), click, i + 1);
/*    */         }
/*    */       }  }
/*    */   
/*    */   }
/*    */   
/*    */   private void renderWidget(WWidget widget) {
/* 54 */     lineBox(widget.x, widget.y, widget.width, widget.height, WIDGET_COLOR);
/*    */     
/* 56 */     if (widget instanceof WContainer) { WContainer container = (WContainer)widget;
/* 57 */       for (Cell<?> cell : (Iterable<Cell<?>>)container.cells) {
/* 58 */         lineBox(cell.x, cell.y, cell.width, cell.height, CELL_COLOR);
/* 59 */         renderWidget(cell.widget());
/*    */       }  }
/*    */   
/*    */   }
/*    */   
/*    */   private void lineBox(double x, double y, double width, double height, Color color) {
/* 65 */     line(x, y, x + width, y, color);
/* 66 */     line(x + width, y, x + width, y + height, color);
/* 67 */     line(x, y, x, y + height, color);
/* 68 */     line(x, y + height, x + width, y + height, color);
/*    */   }
/*    */   
/*    */   private void line(double x1, double y1, double x2, double y2, Color color) {
/* 72 */     this.mesh.ensureLineCapacity();
/*    */     
/* 74 */     this.mesh.line(this.mesh
/* 75 */         .vec3(x1, y1, 0.0D).color(color).next(), this.mesh
/* 76 */         .vec3(x2, y2, 0.0D).color(color).next());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\renderer\GuiDebugRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */