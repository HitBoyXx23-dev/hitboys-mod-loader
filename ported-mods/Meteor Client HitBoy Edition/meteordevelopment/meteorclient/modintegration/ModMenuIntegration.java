/*    */ package meteordevelopment.meteorclient.modintegration;
/*    */ 
/*    */ import com.terraformersmc.modmenu.api.ConfigScreenFactory;
/*    */ import com.terraformersmc.modmenu.api.ModMenuApi;
/*    */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*    */ import meteordevelopment.meteorclient.gui.screens.ModulesScreen;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModMenuIntegration
/*    */   implements ModMenuApi
/*    */ {
/*    */   public ConfigScreenFactory<?> getModConfigScreenFactory() {
/* 16 */     return screen -> new ModulesScreen(GuiThemes.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\modintegration\ModMenuIntegration.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */