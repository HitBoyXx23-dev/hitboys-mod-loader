/*    */ package meteordevelopment.meteorclient.gui.tabs;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WTopBar;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Tab
/*    */ {
/*    */   public final String name;
/*    */   
/*    */   public Tab(String name) {
/* 17 */     this.name = name;
/*    */   }
/*    */   
/*    */   public void openScreen(GuiTheme theme) {
/* 21 */     TabScreen screen = createScreen(theme);
/* 22 */     screen.<WTopBar>addDirect(theme.topBar()).top().centerX();
/* 23 */     MeteorClient.mc.method_1507((class_437)screen);
/*    */   }
/*    */   
/*    */   public abstract TabScreen createScreen(GuiTheme paramGuiTheme);
/*    */   
/*    */   public abstract boolean isScreen(class_437 paramclass_437);
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\tabs\Tab.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */