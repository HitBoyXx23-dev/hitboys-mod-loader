/*    */ package meteordevelopment.meteorclient.gui.tabs.builtin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*    */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModulesTab
/*    */   extends Tab
/*    */ {
/*    */   public ModulesTab() {
/* 16 */     super("Modules");
/*    */   }
/*    */ 
/*    */   
/*    */   public TabScreen createScreen(GuiTheme theme) {
/* 21 */     return theme.modulesScreen();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isScreen(class_437 screen) {
/* 26 */     return GuiThemes.get().isModulesScreen(screen);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\builtin\ModulesTab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */