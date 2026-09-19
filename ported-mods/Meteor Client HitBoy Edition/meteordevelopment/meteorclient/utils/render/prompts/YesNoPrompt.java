/*    */ package meteordevelopment.meteorclient.utils.render.prompts;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ public class YesNoPrompt
/*    */   extends Prompt<YesNoPrompt> {
/*    */   private Runnable onYes = () -> {
/*    */     
/*    */     };
/*    */   private Runnable onNo = () -> {
/*    */     
/*    */     };
/*    */   
/*    */   private YesNoPrompt(GuiTheme theme, class_437 parent) {
/* 20 */     super(theme, parent);
/*    */   }
/*    */   
/*    */   public static YesNoPrompt create() {
/* 24 */     return new YesNoPrompt(GuiThemes.get(), MeteorClient.mc.field_1755);
/*    */   }
/*    */   
/*    */   public static YesNoPrompt create(GuiTheme theme, class_437 parent) {
/* 28 */     return new YesNoPrompt(theme, parent);
/*    */   }
/*    */   
/*    */   public YesNoPrompt onYes(Runnable action) {
/* 32 */     this.onYes = action;
/* 33 */     return this;
/*    */   }
/*    */   
/*    */   public YesNoPrompt onNo(Runnable action) {
/* 37 */     this.onNo = action;
/* 38 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initialiseWidgets(Prompt<YesNoPrompt>.PromptScreen screen) {
/* 43 */     WButton yesButton = (WButton)screen.list.add((WWidget)this.theme.button("Yes")).expandX().widget();
/* 44 */     yesButton.action = (() -> {
/*    */         dontShowAgain(screen);
/*    */         
/*    */         this.onYes.run();
/*    */         screen.method_25419();
/*    */       });
/* 50 */     WButton noButton = (WButton)screen.list.add((WWidget)this.theme.button("No")).expandX().widget();
/* 51 */     noButton.action = (() -> {
/*    */         dontShowAgain(screen);
/*    */         this.onNo.run();
/*    */         screen.method_25419();
/*    */       });
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\prompts\YesNoPrompt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */