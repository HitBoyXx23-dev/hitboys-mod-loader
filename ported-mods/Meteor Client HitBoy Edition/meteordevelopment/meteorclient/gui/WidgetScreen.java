/*     */ package meteordevelopment.meteorclient.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.function.Consumer;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiDebugRenderer;
/*     */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*     */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*     */ import meteordevelopment.meteorclient.gui.utils.Cell;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WRoot;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*     */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.CursorStyle;
/*     */ import meteordevelopment.meteorclient.utils.misc.input.Input;
/*     */ import net.minecraft.class_11905;
/*     */ import net.minecraft.class_11908;
/*     */ import net.minecraft.class_11909;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_3532;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_6417;
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
/*     */ public abstract class WidgetScreen
/*     */   extends class_437
/*     */ {
/*  41 */   private static final GuiRenderer RENDERER = new GuiRenderer();
/*  42 */   private static final GuiDebugRenderer DEBUG_RENDERER = new GuiDebugRenderer();
/*     */   
/*     */   public Runnable taskAfterRender;
/*     */   
/*     */   protected Runnable enterAction;
/*     */   
/*     */   public class_437 parent;
/*     */   
/*     */   private final WContainer root;
/*     */   
/*     */   protected final GuiTheme theme;
/*     */   
/*     */   public boolean locked;
/*     */   
/*     */   public boolean lockedAllowClose;
/*     */   private boolean closed;
/*     */   private boolean onClose;
/*     */   private boolean debug;
/*     */   private boolean closing;
/*     */   private double lastMouseX;
/*     */   private double lastMouseY;
/*     */   public double animProgress;
/*     */   private List<Runnable> onClosed;
/*     */   protected boolean firstInit = true;
/*     */   
/*     */   public WidgetScreen(GuiTheme theme, String title) {
/*  68 */     super((class_2561)class_2561.method_43470(title));
/*     */     
/*  70 */     this.parent = MeteorClient.mc.field_1755;
/*  71 */     this.root = new WFullScreenRoot();
/*  72 */     this.theme = theme;
/*     */     
/*  74 */     this.root.theme = theme;
/*     */     
/*  76 */     if (this.parent != null) {
/*  77 */       this.animProgress = 1.0D;
/*     */       
/*  79 */       if (this instanceof TabScreen && this.parent instanceof TabScreen) {
/*  80 */         this.parent = ((TabScreen)this.parent).parent;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public <W extends WWidget> Cell<W> add(W widget) {
/*  86 */     return this.root.add((WWidget)widget);
/*     */   }
/*     */   
/*     */   public void clear() {
/*  90 */     this.root.clear();
/*     */   }
/*     */   
/*     */   public void invalidate() {
/*  94 */     this.root.invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void method_25426() {
/*  99 */     MeteorClient.EVENT_BUS.subscribe(this);
/*     */     
/* 101 */     this.closed = false;
/*     */     
/* 103 */     if (this.firstInit) {
/* 104 */       this.firstInit = false;
/* 105 */       initWidgets();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void reload() {
/* 112 */     clear();
/* 113 */     initWidgets();
/*     */   }
/*     */   
/*     */   public void onClosed(Runnable action) {
/* 117 */     if (this.onClosed == null) this.onClosed = new ArrayList<>(2); 
/* 118 */     this.onClosed.add(action);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25402(class_11909 click, boolean doubled) {
/* 123 */     if (this.locked) return false;
/*     */     
/* 125 */     double mouseX = click.comp_4798();
/* 126 */     double mouseY = click.comp_4799();
/* 127 */     double s = MeteorClient.mc.method_22683().method_4495();
/*     */     
/* 129 */     mouseX *= s;
/* 130 */     mouseY *= s;
/*     */     
/* 132 */     return this.root.mouseClicked(new class_11909(mouseX, mouseY, click.comp_4800()), doubled);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25406(class_11909 click) {
/* 137 */     if (this.locked) return false;
/*     */     
/* 139 */     double mouseX = click.comp_4798();
/* 140 */     double mouseY = click.comp_4799();
/* 141 */     double s = MeteorClient.mc.method_22683().method_4495();
/*     */     
/* 143 */     mouseX *= s;
/* 144 */     mouseY *= s;
/*     */     
/* 146 */     if (this.debug && click.method_74245() == 1) DEBUG_RENDERER.mouseReleased((WWidget)this.root, new class_11909(mouseX, mouseY, click.comp_4800()), 0);
/*     */     
/* 148 */     return this.root.mouseReleased(new class_11909(mouseX, mouseY, click.comp_4800()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_16014(double mouseX, double mouseY) {
/* 153 */     if (this.locked)
/*     */       return; 
/* 155 */     double s = MeteorClient.mc.method_22683().method_4495();
/* 156 */     mouseX *= s;
/* 157 */     mouseY *= s;
/*     */     
/* 159 */     this.root.mouseMoved(mouseX, mouseY, this.lastMouseX, this.lastMouseY);
/*     */     
/* 161 */     this.lastMouseX = mouseX;
/* 162 */     this.lastMouseY = mouseY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25401(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
/* 167 */     if (this.locked) return false;
/*     */     
/* 169 */     this.root.mouseScrolled(verticalAmount);
/*     */     
/* 171 */     return super.method_25401(mouseX, mouseY, horizontalAmount, verticalAmount);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_16803(class_11908 input) {
/* 176 */     if (this.locked) return false;
/*     */     
/* 178 */     if ((input.comp_4797() == 2 || input.comp_4797() == 8) && input.comp_4795() == 57) {
/* 179 */       this.debug = !this.debug;
/* 180 */       return true;
/*     */     } 
/*     */     
/* 183 */     if ((input.comp_4795() == 257 || input.comp_4795() == 335) && this.enterAction != null) {
/* 184 */       this.enterAction.run();
/* 185 */       return true;
/*     */     } 
/*     */     
/* 188 */     return super.method_16803(input);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25404(class_11908 input) {
/* 193 */     if (this.locked) return false;
/*     */     
/* 195 */     boolean shouldReturn = (this.root.keyPressed(input) || super.method_25404(input));
/* 196 */     if (shouldReturn) return true;
/*     */ 
/*     */     
/* 199 */     if (input.comp_4795() == 258) {
/* 200 */       AtomicReference<WTextBox> firstTextBox = new AtomicReference<>(null);
/* 201 */       AtomicBoolean done = new AtomicBoolean(false);
/* 202 */       AtomicBoolean foundFocused = new AtomicBoolean(false);
/*     */       
/* 204 */       loopWidgets((WWidget)this.root, wWidget -> {
/*     */             WTextBox textBox;
/*     */             if (!done.get() && wWidget instanceof WTextBox) {
/*     */               textBox = (WTextBox)wWidget;
/*     */             } else {
/*     */               return;
/*     */             } 
/*     */             if (foundFocused.get()) {
/*     */               textBox.setFocused(true);
/*     */               textBox.setCursorMax();
/*     */               done.set(true);
/*     */             } else if (textBox.isFocused()) {
/*     */               textBox.setFocused(false);
/*     */               foundFocused.set(true);
/*     */             } 
/*     */             if (firstTextBox.get() == null)
/*     */               firstTextBox.set(textBox); 
/*     */           });
/* 222 */       if (!done.get() && firstTextBox.get() != null) {
/* 223 */         ((WTextBox)firstTextBox.get()).setFocused(true);
/* 224 */         ((WTextBox)firstTextBox.get()).setCursorMax();
/*     */       } 
/*     */       
/* 227 */       return true;
/*     */     } 
/*     */     
/* 230 */     boolean control = class_6417.field_52734 ? ((input.comp_4797() == 8)) : ((input.comp_4797() == 2));
/*     */     
/* 232 */     return ((control && input.comp_4795() == 67 && toClipboard()) || (control && input
/* 233 */       .comp_4795() == 86 && fromClipboard()));
/*     */   }
/*     */   
/*     */   public void keyRepeated(class_11908 input) {
/* 237 */     if (this.locked)
/*     */       return; 
/* 239 */     this.root.keyRepeated(input);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25400(class_11905 input) {
/* 244 */     if (this.locked) return false;
/*     */     
/* 246 */     return this.root.charTyped(input);
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25420(class_332 context, int mouseX, int mouseY, float deltaTicks) {
/* 251 */     if (this.field_22787.field_1687 == null) {
/* 252 */       method_57728(context, deltaTicks);
/*     */     }
/*     */   }
/*     */   
/*     */   public void renderCustom(class_332 context, int mouseX, int mouseY, float delta) {
/* 257 */     int s = MeteorClient.mc.method_22683().method_4495();
/* 258 */     mouseX *= s;
/* 259 */     mouseY *= s;
/*     */     
/* 261 */     this.animProgress += (delta / 20.0F * 14.0F * (this.closing ? -1 : true));
/* 262 */     this.animProgress = class_3532.method_15350(this.animProgress, 0.0D, 1.0D);
/*     */     
/* 264 */     if (this.closing && (this.animProgress == 0.0D || this.parent != null)) {
/* 265 */       closeInternal();
/*     */     }
/*     */     
/* 268 */     GuiKeyEvents.canUseKeys = true;
/*     */ 
/*     */     
/* 271 */     Utils.unscaledProjection();
/*     */     
/* 273 */     onRenderBefore(context, delta);
/*     */     
/* 275 */     RENDERER.theme = this.theme;
/* 276 */     this.theme.beforeRender();
/*     */     
/* 278 */     RENDERER.begin(context);
/* 279 */     RENDERER.setAlpha(this.animProgress);
/* 280 */     this.root.render(RENDERER, mouseX, mouseY, (delta / 20.0F));
/* 281 */     RENDERER.setAlpha(1.0D);
/* 282 */     RENDERER.end();
/*     */     
/* 284 */     boolean tooltip = RENDERER.renderTooltip(context, mouseX, mouseY, (delta / 20.0F));
/*     */     
/* 286 */     if (this.debug) {
/* 287 */       DEBUG_RENDERER.render((WWidget)this.root);
/* 288 */       if (tooltip) DEBUG_RENDERER.render(RENDERER.tooltipWidget);
/*     */     
/*     */     } 
/* 291 */     Utils.scaledProjection();
/*     */     
/* 293 */     runAfterRenderTasks();
/*     */   }
/*     */   
/*     */   protected void runAfterRenderTasks() {
/* 297 */     if (this.taskAfterRender != null) {
/* 298 */       this.taskAfterRender.run();
/* 299 */       this.taskAfterRender = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onRenderBefore(class_332 drawContext, float delta) {}
/*     */   
/*     */   public void method_25410(int width, int height) {
/* 307 */     super.method_25410(width, height);
/* 308 */     this.root.invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25419() {
/* 313 */     if (!this.locked || this.lockedAllowClose) {
/* 314 */       this.closing = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void method_25432() {
/* 320 */     if (!this.closed || this.lockedAllowClose) {
/* 321 */       this.closed = true;
/* 322 */       onClosed();
/*     */       
/* 324 */       Input.setCursorStyle(CursorStyle.Default);
/*     */       
/* 326 */       loopWidgets((WWidget)this.root, widget -> { if (widget instanceof WTextBox) {
/*     */               WTextBox textBox = (WTextBox)widget; if (textBox.isFocused())
/*     */                 textBox.setFocused(false); 
/*     */             } 
/* 330 */           }); MeteorClient.EVENT_BUS.unsubscribe(this);
/* 331 */       GuiKeyEvents.canUseKeys = true;
/*     */       
/* 333 */       if (this.onClosed != null) {
/* 334 */         for (Runnable action : this.onClosed) action.run();
/*     */       
/*     */       }
/* 337 */       if (this.onClose) {
/* 338 */         this.taskAfterRender = (() -> {
/*     */             this.locked = true;
/*     */             MeteorClient.mc.method_1507(this.parent);
/*     */           });
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closeInternal() {
/* 347 */     boolean preOnClose = this.onClose;
/* 348 */     this.onClose = true;
/*     */     
/* 350 */     super.method_25419();
/* 351 */     method_25432();
/*     */     
/* 353 */     this.onClose = preOnClose;
/*     */   }
/*     */   
/*     */   private void loopWidgets(WWidget widget, Consumer<WWidget> action) {
/* 357 */     action.accept(widget);
/*     */     
/* 359 */     if (widget instanceof WContainer)
/* 360 */       for (Cell<?> cell : (Iterable<Cell<?>>)((WContainer)widget).cells) loopWidgets(cell.widget(), action);
/*     */        
/*     */   }
/*     */   
/*     */   protected void onClosed() {}
/*     */   
/*     */   public boolean toClipboard() {
/* 367 */     return false;
/*     */   }
/*     */   
/*     */   public boolean fromClipboard() {
/* 371 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25422() {
/* 376 */     return (!this.locked || this.lockedAllowClose);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean method_25421() {
/* 381 */     return false;
/*     */   }
/*     */   
/*     */   public abstract void initWidgets();
/*     */   
/*     */   private static class WFullScreenRoot extends WContainer implements WRoot { private boolean valid;
/*     */     
/*     */     public void invalidate() {
/* 389 */       this.valid = false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onCalculateSize() {
/* 394 */       this.width = Utils.getWindowWidth();
/* 395 */       this.height = Utils.getWindowHeight();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onCalculateWidgetPositions() {
/* 400 */       for (Cell<?> cell : (Iterable<Cell<?>>)this.cells) {
/* 401 */         cell.x = 0.0D;
/* 402 */         cell.y = 0.0D;
/*     */         
/* 404 */         cell.width = this.width;
/* 405 */         cell.height = this.height;
/*     */         
/* 407 */         cell.alignWidget();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean render(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 413 */       if (!this.valid) {
/* 414 */         calculateSize();
/* 415 */         calculateWidgetPositions();
/*     */         
/* 417 */         this.valid = true;
/* 418 */         mouseMoved(MeteorClient.mc.field_1729.method_1603(), MeteorClient.mc.field_1729.method_1604(), MeteorClient.mc.field_1729.method_1603(), MeteorClient.mc.field_1729.method_1604());
/*     */       } 
/*     */       
/* 421 */       return super.render(renderer, mouseX, mouseY, delta);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\WidgetScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */