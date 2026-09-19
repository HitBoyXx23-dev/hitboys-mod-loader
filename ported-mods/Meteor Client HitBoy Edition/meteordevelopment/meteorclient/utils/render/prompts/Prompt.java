/*     */ package meteordevelopment.meteorclient.utils.render.prompts;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import net.minecraft.class_437;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Prompt<T>
/*     */ {
/*     */   protected final GuiTheme theme;
/*     */   protected final class_437 parent;
/*  21 */   protected String title = "";
/*  22 */   protected final List<String> messages = new ArrayList<>();
/*     */   protected boolean dontShowAgainCheckboxVisible = true;
/*  24 */   protected String id = null;
/*     */   
/*     */   protected Prompt(GuiTheme theme, class_437 parent) {
/*  27 */     this.theme = theme;
/*  28 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public T title(String title) {
/*  32 */     this.title = title;
/*  33 */     return (T)this;
/*     */   }
/*     */   
/*     */   public T message(String message) {
/*  37 */     this.messages.add(message);
/*  38 */     return (T)this;
/*     */   }
/*     */   
/*     */   public T message(String message, Object... args) {
/*  42 */     this.messages.add(String.format(message, args));
/*  43 */     return (T)this;
/*     */   }
/*     */   
/*     */   public T dontShowAgainCheckboxVisible(boolean visible) {
/*  47 */     this.dontShowAgainCheckboxVisible = visible;
/*  48 */     return (T)this;
/*     */   }
/*     */   
/*     */   public T id(String from) {
/*  52 */     this.id = from;
/*  53 */     return (T)this;
/*     */   }
/*     */   
/*     */   public boolean show() {
/*  57 */     if (this.id != null && (Config.get()).dontShowAgainPrompts.contains(this.id)) return false;
/*     */     
/*  59 */     if (!RenderSystem.isOnRenderThread()) {
/*  60 */       MeteorClient.mc.execute(() -> MeteorClient.mc.method_1507((class_437)new PromptScreen(this.theme)));
/*     */     } else {
/*     */       
/*  63 */       MeteorClient.mc.method_1507((class_437)new PromptScreen(this.theme));
/*     */     } 
/*     */     
/*  66 */     return true;
/*     */   }
/*     */   
/*     */   protected void dontShowAgain(PromptScreen screen) {
/*  70 */     if (screen.dontShowAgainCheckbox != null && screen.dontShowAgainCheckbox.checked && this.id != null)
/*  71 */       (Config.get()).dontShowAgainPrompts.add(this.id); 
/*     */   }
/*     */   
/*     */   protected abstract void initialiseWidgets(PromptScreen paramPromptScreen);
/*     */   
/*     */   protected class PromptScreen
/*     */     extends WindowScreen {
/*     */     protected WCheckbox dontShowAgainCheckbox;
/*     */     protected WHorizontalList list;
/*     */     
/*     */     public PromptScreen(GuiTheme theme) {
/*  82 */       super(theme, Prompt.this.title);
/*     */       
/*  84 */       this.parent = Prompt.this.parent;
/*     */     }
/*     */ 
/*     */     
/*     */     public void initWidgets() {
/*  89 */       for (String line : Prompt.this.messages) add((WWidget)this.theme.label(line)).expandX(); 
/*  90 */       add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */       
/*  92 */       if (Prompt.this.dontShowAgainCheckboxVisible)
/*  93 */       { WHorizontalList checkboxContainer = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*  94 */         this.dontShowAgainCheckbox = (WCheckbox)checkboxContainer.add((WWidget)this.theme.checkbox(false)).widget();
/*  95 */         checkboxContainer.add((WWidget)this.theme.label("Don't show this again.")).expandX(); }
/*  96 */       else { this.dontShowAgainCheckbox = null; }
/*     */       
/*  98 */       this.list = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */       
/* 100 */       Prompt.this.initialiseWidgets(this);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\prompts\Prompt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */