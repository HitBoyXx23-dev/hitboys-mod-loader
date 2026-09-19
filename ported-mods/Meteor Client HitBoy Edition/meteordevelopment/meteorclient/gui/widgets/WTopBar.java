/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.tabs.Tab;
/*    */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*    */ import meteordevelopment.meteorclient.gui.tabs.Tabs;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WPressable;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_437;
/*    */ import org.lwjgl.glfw.GLFW;
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
/*    */ public abstract class WTopBar
/*    */   extends WHorizontalList
/*    */ {
/*    */   protected abstract Color getButtonColor(boolean paramBoolean1, boolean paramBoolean2);
/*    */   
/*    */   protected abstract Color getNameColor();
/*    */   
/*    */   public void init() {
/* 31 */     for (Tab tab : Tabs.get())
/* 32 */       add((WWidget)new WTopBarButton(tab)); 
/*    */   }
/*    */   
/*    */   protected class WTopBarButton
/*    */     extends WPressable {
/*    */     private final Tab tab;
/*    */     
/*    */     public WTopBarButton(Tab tab) {
/* 40 */       this.tab = tab;
/*    */     }
/*    */ 
/*    */     
/*    */     protected void onCalculateSize() {
/* 45 */       double pad = pad();
/*    */       
/* 47 */       this.width = pad + this.theme.textWidth(this.tab.name) + pad;
/* 48 */       this.height = pad + this.theme.textHeight() + pad;
/*    */     }
/*    */ 
/*    */     
/*    */     protected void onPressed(int button) {
/* 53 */       class_437 screen = MeteorClient.mc.field_1755;
/*    */       
/* 55 */       if (!(screen instanceof TabScreen) || ((TabScreen)screen).tab != this.tab) {
/* 56 */         double mouseX = MeteorClient.mc.field_1729.method_1603();
/* 57 */         double mouseY = MeteorClient.mc.field_1729.method_1604();
/*    */         
/* 59 */         this.tab.openScreen(this.theme);
/* 60 */         GLFW.glfwSetCursorPos(MeteorClient.mc.method_22683().method_4490(), mouseX, mouseY);
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/*    */     protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 66 */       double pad = pad();
/* 67 */       Color color = WTopBar.this.getButtonColor((this.pressed || (MeteorClient.mc.field_1755 instanceof TabScreen && ((TabScreen)MeteorClient.mc.field_1755).tab == this.tab)), this.mouseOver);
/*    */       
/* 69 */       renderer.quad(this.x, this.y, this.width, this.height, color);
/* 70 */       renderer.text(this.tab.name, this.x + pad, this.y + pad, WTopBar.this.getNameColor(), false);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WTopBar.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */