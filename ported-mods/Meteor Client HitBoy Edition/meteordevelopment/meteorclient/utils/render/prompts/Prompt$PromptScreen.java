/*     */ package meteordevelopment.meteorclient.utils.render.prompts;
/*     */ 
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
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
/*     */ public class PromptScreen
/*     */   extends WindowScreen
/*     */ {
/*     */   protected WCheckbox dontShowAgainCheckbox;
/*     */   protected WHorizontalList list;
/*     */   
/*     */   public PromptScreen(GuiTheme theme) {
/*  82 */     super(theme, Prompt.this.title);
/*     */     
/*  84 */     this.parent = Prompt.this.parent;
/*     */   }
/*     */ 
/*     */   
/*     */   public void initWidgets() {
/*  89 */     for (String line : Prompt.this.messages) add((WWidget)this.theme.label(line)).expandX(); 
/*  90 */     add((WWidget)this.theme.horizontalSeparator()).expandX();
/*     */     
/*  92 */     if (Prompt.this.dontShowAgainCheckboxVisible)
/*  93 */     { WHorizontalList checkboxContainer = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*  94 */       this.dontShowAgainCheckbox = (WCheckbox)checkboxContainer.add((WWidget)this.theme.checkbox(false)).widget();
/*  95 */       checkboxContainer.add((WWidget)this.theme.label("Don't show this again.")).expandX(); }
/*  96 */     else { this.dontShowAgainCheckbox = null; }
/*     */     
/*  98 */     this.list = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */     
/* 100 */     Prompt.this.initialiseWidgets(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\prompts\Prompt$PromptScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */