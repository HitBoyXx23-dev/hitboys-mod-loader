/*    */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*    */ import meteordevelopment.meteorclient.gui.tabs.WindowTabScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*    */ import meteordevelopment.meteorclient.settings.Settings;
/*    */ import meteordevelopment.meteorclient.systems.config.Config;
/*    */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*    */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*    */ import meteordevelopment.meteorclient.utils.render.prompts.YesNoPrompt;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ 
/*    */ public class ConfigTab
/*    */   extends Tab
/*    */ {
/*    */   public ConfigTab() {
/* 20 */     super("Config");
/*    */   }
/*    */ 
/*    */   
/*    */   public TabScreen createScreen(GuiTheme theme) {
/* 25 */     return (TabScreen)new ConfigScreen(theme, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isScreen(class_437 screen) {
/* 30 */     return screen instanceof ConfigScreen;
/*    */   }
/*    */   
/*    */   public static class ConfigScreen extends WindowTabScreen {
/*    */     private final Settings settings;
/*    */     
/*    */     public ConfigScreen(GuiTheme theme, Tab tab) {
/* 37 */       super(theme, tab);
/*    */       
/* 39 */       this.settings = (Config.get()).settings;
/* 40 */       this.settings.onActivated();
/*    */       
/* 42 */       onClosed(() -> {
/*    */             String prefix = (String)(Config.get()).prefix.get();
/*    */             if (prefix.isBlank()) {
/*    */               ((YesNoPrompt)((YesNoPrompt)((YesNoPrompt)((YesNoPrompt)((YesNoPrompt)YesNoPrompt.create(theme, this.parent).title("Empty command prefix")).message("You have set your command prefix to nothing.")).message("This WILL prevent you from sending chat messages.")).message("Do you want to reset your prefix back to '.'?")).onYes(()).id("empty-command-prefix")).show();
/*    */             } else if (prefix.equals("/")) {
/*    */               ((YesNoPrompt)((YesNoPrompt)((YesNoPrompt)((YesNoPrompt)((YesNoPrompt)YesNoPrompt.create(theme, this.parent).title("Potential prefix conflict")).message("You have set your command prefix to '/', which is used by minecraft.")).message("This can cause conflict issues between meteor and minecraft commands.")).message("Do you want to reset your prefix to '.'?")).onYes(()).id("minecraft-prefix-conflict")).show();
/*    */             } else if (prefix.length() > 7) {
/*    */               ((YesNoPrompt)((YesNoPrompt)((YesNoPrompt)((YesNoPrompt)((YesNoPrompt)YesNoPrompt.create(theme, this.parent).title("Long command prefix")).message("You have set your command prefix to a very long string.")).message("This means that in order to execute any command, you will need to type %s followed by the command you want to run.", new Object[] { prefix })).message("Do you want to reset your prefix back to '.'?")).onYes(()).id("long-command-prefix")).show();
/*    */             } 
/*    */           });
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public void initWidgets() {
/* 80 */       add(this.theme.settings(this.settings)).expandX();
/*    */     }
/*    */ 
/*    */     
/*    */     public void method_25393() {
/* 85 */       super.method_25393();
/*    */       
/* 87 */       this.settings.tick((WContainer)this.window, this.theme);
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean toClipboard() {
/* 92 */       return NbtUtils.toClipboard((ISerializable)Config.get());
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean fromClipboard() {
/* 97 */       return NbtUtils.fromClipboard((ISerializable)Config.get());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\ConfigTab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */