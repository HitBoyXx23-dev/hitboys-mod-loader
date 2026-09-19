/*     */ package meteordevelopment.meteorclient.gui.renderer;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.Stack;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.renderer.operations.TextOperation;
/*     */ import meteordevelopment.meteorclient.gui.renderer.packer.GuiTexture;
/*     */ import meteordevelopment.meteorclient.gui.renderer.packer.TexturePacker;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.renderer.Renderer2D;
/*     */ import meteordevelopment.meteorclient.renderer.Texture;
/*     */ import meteordevelopment.meteorclient.utils.PostInit;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.Pool;
/*     */ import meteordevelopment.meteorclient.utils.render.RenderUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import org.joml.Matrix3x2fStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiRenderer
/*     */ {
/*  34 */   private static final Color WHITE = new Color(255, 255, 255);
/*     */   
/*  36 */   private static final TexturePacker TEXTURE_PACKER = new TexturePacker();
/*     */   
/*     */   private static Texture TEXTURE;
/*     */   public static GuiTexture CIRCLE;
/*     */   public static GuiTexture TRIANGLE;
/*     */   public static GuiTexture EDIT;
/*     */   public static GuiTexture RESET;
/*     */   public static GuiTexture FAVORITE_NO;
/*     */   public static GuiTexture FAVORITE_YES;
/*     */   public static GuiTexture COPY;
/*     */   public static GuiTexture PASTE;
/*     */   public GuiTheme theme;
/*  48 */   private final Renderer2D r = new Renderer2D(false);
/*  49 */   private final Renderer2D rTex = new Renderer2D(true);
/*     */   
/*  51 */   private final Pool<Scissor> scissorPool = new Pool(Scissor::new);
/*  52 */   private final Stack<Scissor> scissorStack = (Stack<Scissor>)new ObjectArrayList();
/*     */   
/*  54 */   private final Pool<TextOperation> textPool = new Pool(TextOperation::new);
/*  55 */   private final List<TextOperation> texts = (List<TextOperation>)new ObjectArrayList();
/*     */   
/*  57 */   private final List<Runnable> postTasks = (List<Runnable>)new ObjectArrayList();
/*     */   
/*     */   public String tooltip;
/*     */   public String lastTooltip;
/*     */   public WWidget tooltipWidget;
/*     */   private double tooltipAnimProgress;
/*     */   private class_332 drawContext;
/*     */   
/*     */   public static GuiTexture addTexture(class_2960 id) {
/*  66 */     return TEXTURE_PACKER.add(id);
/*     */   }
/*     */   
/*     */   @PostInit
/*     */   public static void init() {
/*  71 */     CIRCLE = addTexture(MeteorClient.identifier("textures/icons/gui/circle.png"));
/*  72 */     TRIANGLE = addTexture(MeteorClient.identifier("textures/icons/gui/triangle.png"));
/*  73 */     EDIT = addTexture(MeteorClient.identifier("textures/icons/gui/edit.png"));
/*  74 */     RESET = addTexture(MeteorClient.identifier("textures/icons/gui/reset.png"));
/*  75 */     FAVORITE_NO = addTexture(MeteorClient.identifier("textures/icons/gui/favorite_no.png"));
/*  76 */     FAVORITE_YES = addTexture(MeteorClient.identifier("textures/icons/gui/favorite_yes.png"));
/*     */     
/*  78 */     COPY = addTexture(MeteorClient.identifier("textures/icons/gui/copy.png"));
/*  79 */     PASTE = addTexture(MeteorClient.identifier("textures/icons/gui/paste.png"));
/*     */     
/*  81 */     TEXTURE = TEXTURE_PACKER.pack();
/*     */   }
/*     */   
/*     */   public void begin(class_332 drawContext) {
/*  85 */     this.drawContext = drawContext;
/*  86 */     this.drawContext.method_71048();
/*     */     
/*  88 */     Matrix3x2fStack matrices = drawContext.method_51448();
/*  89 */     matrices.pushMatrix();
/*  90 */     matrices.scale(1.0F / MeteorClient.mc.method_22683().method_4495());
/*     */     
/*  92 */     scissorStart(0.0D, 0.0D, Utils.getWindowWidth(), Utils.getWindowHeight());
/*     */   }
/*     */   
/*     */   public void end() {
/*  96 */     scissorEnd();
/*     */     
/*  98 */     for (Runnable task : this.postTasks) task.run(); 
/*  99 */     this.postTasks.clear();
/*     */     
/* 101 */     this.drawContext.method_51448().popMatrix();
/* 102 */     this.drawContext.method_71048();
/*     */   }
/*     */   
/*     */   public void beginRender() {
/* 106 */     this.r.begin();
/* 107 */     this.rTex.begin();
/*     */   }
/*     */   
/*     */   public void endRender() {
/* 111 */     endRender(null);
/*     */   }
/*     */   
/*     */   public void endRender(Scissor scissor) {
/* 115 */     if (scissor != null) scissor.push();
/*     */     
/* 117 */     this.r.end();
/* 118 */     this.rTex.end();
/*     */     
/* 120 */     this.r.render();
/* 121 */     this.rTex.render("u_Texture", TEXTURE.method_71659(), TEXTURE.method_75484());
/*     */ 
/*     */     
/* 124 */     this.theme.textRenderer().begin(this.theme.scale(1.0D));
/* 125 */     for (TextOperation text : this.texts) {
/* 126 */       if (!text.title) text.run(this.textPool); 
/*     */     } 
/* 128 */     this.theme.textRenderer().end();
/*     */ 
/*     */     
/* 131 */     this.theme.textRenderer().begin(this.theme.scale(1.25D));
/* 132 */     for (TextOperation text : this.texts) {
/* 133 */       if (text.title) text.run(this.textPool); 
/*     */     } 
/* 135 */     this.theme.textRenderer().end();
/*     */     
/* 137 */     this.texts.clear();
/*     */     
/* 139 */     if (scissor != null) scissor.pop(); 
/*     */   }
/*     */   
/*     */   public void scissorStart(double x, double y, double width, double height) {
/* 143 */     if (!this.scissorStack.isEmpty()) {
/* 144 */       Scissor parent = (Scissor)this.scissorStack.top();
/*     */       
/* 146 */       if (x < parent.x) { x = parent.x; }
/* 147 */       else if (x + width > (parent.x + parent.width)) { width -= x + width - (parent.x + parent.width); }
/*     */       
/* 149 */       if (y < parent.y) { y = parent.y; }
/* 150 */       else if (y + height > (parent.y + parent.height)) { height -= y + height - (parent.y + parent.height); }
/*     */       
/* 152 */       endRender(parent);
/*     */     } 
/*     */     
/* 155 */     this.scissorStack.push(((Scissor)this.scissorPool.get()).set(x, y, width, height));
/* 156 */     this.drawContext.method_44379((int)x, (int)y, (int)(x + width), (int)(y + height));
/*     */     
/* 158 */     beginRender();
/*     */   }
/*     */   
/*     */   public void scissorEnd() {
/* 162 */     Scissor scissor = (Scissor)this.scissorStack.pop();
/*     */     
/* 164 */     endRender(scissor);
/*     */     
/* 166 */     scissor.push();
/* 167 */     for (Runnable task : scissor.postTasks) task.run(); 
/* 168 */     scissor.pop();
/*     */     
/* 170 */     this.drawContext.method_44380();
/* 171 */     if (!this.scissorStack.isEmpty()) beginRender();
/*     */     
/* 173 */     this.scissorPool.free(scissor);
/*     */   }
/*     */   
/*     */   public boolean renderTooltip(class_332 drawContext, double mouseX, double mouseY, double delta) {
/* 177 */     this.tooltipAnimProgress += ((this.tooltip != null) ? true : -1) * delta * 14.0D;
/* 178 */     this.tooltipAnimProgress = class_3532.method_15350(this.tooltipAnimProgress, 0.0D, 1.0D);
/*     */     
/* 180 */     boolean toReturn = false;
/*     */     
/* 182 */     if (this.tooltipAnimProgress > 0.0D) {
/* 183 */       if (this.tooltip != null && !this.tooltip.equals(this.lastTooltip)) {
/* 184 */         this.tooltipWidget = (WWidget)this.theme.tooltip(this.tooltip);
/* 185 */         this.tooltipWidget.init();
/*     */       } 
/*     */       
/* 188 */       double deltaX = -this.tooltipWidget.x + mouseX + 12.0D;
/* 189 */       double deltaY = -this.tooltipWidget.y + mouseY + 12.0D;
/*     */       
/* 191 */       if (mouseX + 12.0D + this.tooltipWidget.width > Utils.getWindowWidth()) deltaX = -this.tooltipWidget.x + Utils.getWindowWidth() - this.tooltipWidget.width; 
/* 192 */       if (mouseY + 12.0D + this.tooltipWidget.height > Utils.getWindowHeight()) deltaY = -this.tooltipWidget.y + Utils.getWindowHeight() - this.tooltipWidget.height;
/*     */       
/* 194 */       this.tooltipWidget.move(deltaX, deltaY);
/*     */       
/* 196 */       setAlpha(this.tooltipAnimProgress);
/*     */       
/* 198 */       begin(drawContext);
/* 199 */       this.tooltipWidget.render(this, mouseX, mouseY, delta);
/* 200 */       end();
/*     */       
/* 202 */       setAlpha(1.0D);
/*     */       
/* 204 */       this.lastTooltip = this.tooltip;
/* 205 */       toReturn = true;
/*     */     } 
/*     */     
/* 208 */     this.tooltip = null;
/* 209 */     return toReturn;
/*     */   }
/*     */   
/*     */   public void setAlpha(double a) {
/* 213 */     this.r.setAlpha(a);
/* 214 */     this.rTex.setAlpha(a);
/*     */     
/* 216 */     this.theme.textRenderer().setAlpha(a);
/*     */   }
/*     */   
/*     */   public void tooltip(String text) {
/* 220 */     this.tooltip = text;
/*     */   }
/*     */   
/*     */   public void quad(double x, double y, double width, double height, Color cTopLeft, Color cTopRight, Color cBottomRight, Color cBottomLeft) {
/* 224 */     this.r.quad(x, y, width, height, cTopLeft, cTopRight, cBottomRight, cBottomLeft);
/*     */   }
/*     */   public void quad(double x, double y, double width, double height, Color colorLeft, Color colorRight) {
/* 227 */     quad(x, y, width, height, colorLeft, colorRight, colorRight, colorLeft);
/*     */   }
/*     */   public void quad(double x, double y, double width, double height, Color color) {
/* 230 */     quad(x, y, width, height, color, color);
/*     */   }
/*     */   public void quad(WWidget widget, Color color) {
/* 233 */     quad(widget.x, widget.y, widget.width, widget.height, color);
/*     */   }
/*     */   public void quad(double x, double y, double width, double height, GuiTexture texture, Color color) {
/* 236 */     this.rTex.texQuad(x, y, width, height, texture.get(width, height), color);
/*     */   }
/*     */   
/*     */   public void rotatedQuad(double x, double y, double width, double height, double rotation, GuiTexture texture, Color color) {
/* 240 */     this.rTex.texQuad(x, y, width, height, rotation, texture.get(width, height), color);
/*     */   }
/*     */   
/*     */   public void triangle(double x1, double y1, double x2, double y2, double x3, double y3, Color color) {
/* 244 */     this.r.triangle(x1, y1, x2, y2, x3, y3, color);
/*     */   }
/*     */   
/*     */   public void text(String text, double x, double y, Color color, boolean title) {
/* 248 */     this.texts.add(((TextOperation)getOp(this.textPool, x, y, color)).set(text, this.theme.textRenderer(), title));
/*     */   }
/*     */   
/*     */   public void texture(double x, double y, double width, double height, double rotation, Texture texture) {
/* 252 */     post(() -> {
/*     */           this.rTex.begin();
/*     */           this.rTex.texQuad(x, y, width, height, rotation, 0.0D, 0.0D, 1.0D, 1.0D, WHITE);
/*     */           this.rTex.end();
/*     */           this.rTex.render(texture.method_71659(), texture.method_75484());
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void post(Runnable task) {
/* 262 */     ((Scissor)this.scissorStack.top()).postTasks.add(task);
/*     */   }
/*     */   
/*     */   public void item(class_1799 itemStack, int x, int y, float scale, boolean overlay) {
/* 266 */     RenderUtils.drawItem(this.drawContext, itemStack, x, y, scale, overlay, null, false);
/*     */   }
/*     */   
/*     */   public void absolutePost(Runnable task) {
/* 270 */     this.postTasks.add(task);
/*     */   }
/*     */   
/*     */   private <T extends GuiRenderOperation<T>> T getOp(Pool<T> pool, double x, double y, Color color) {
/* 274 */     GuiRenderOperation guiRenderOperation = (GuiRenderOperation)pool.get();
/* 275 */     guiRenderOperation.set(x, y, color);
/* 276 */     return (T)guiRenderOperation;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\renderer\GuiRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */