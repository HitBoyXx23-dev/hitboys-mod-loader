/*    */ package meteordevelopment.meteorclient.utils.render.prompts;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ 
/*    */ public class OkPrompt
/*    */   extends Prompt<OkPrompt>
/*    */ {
/*    */   private Runnable onOk = () -> {
/*    */     
/*    */     };
/*    */   
/*    */   private OkPrompt(GuiTheme theme, class_437 parent) {
/* 19 */     super(theme, parent);
/*    */   }
/*    */   
/*    */   public static OkPrompt create() {
/* 23 */     return new OkPrompt(GuiThemes.get(), MeteorClient.mc.field_1755);
/*    */   }
/*    */   
/*    */   public static OkPrompt create(GuiTheme theme, class_437 parent) {
/* 27 */     return new OkPrompt(theme, parent);
/*    */   }
/*    */   
/*    */   public OkPrompt onOk(Runnable action) {
/* 31 */     this.onOk = action;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initialiseWidgets(Prompt<OkPrompt>.PromptScreen screen) {
/* 37 */     WButton okButton = (WButton)screen.list.add((WWidget)this.theme.button("Ok")).expandX().widget();
/* 38 */     okButton.action = (() -> {
/*    */         dontShowAgain(screen);
/*    */         this.onOk.run();
/*    */         screen.method_25419();
/*    */       });
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\prompts\OkPrompt.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */