/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WSection;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WTriangle;
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
/*    */ public class WMeteorHeader
/*    */   extends WSection.WHeader
/*    */ {
/*    */   private WTriangle triangle;
/*    */   
/*    */   public WMeteorHeader(String title) {
/* 28 */     super(WMeteorSection.this, title);
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 33 */     add((WWidget)this.theme.horizontalSeparator(this.title)).expandX();
/*    */     
/* 35 */     if (WMeteorSection.access$000(WMeteorSection.this) != null) add(WMeteorSection.access$100(WMeteorSection.this));
/*    */     
/* 37 */     this.triangle = new WMeteorSection.WHeaderTriangle();
/* 38 */     this.triangle.theme = this.theme;
/* 39 */     this.triangle.action = (() -> rec$.onClick());
/*    */     
/* 41 */     add((WWidget)this.triangle);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 46 */     this.triangle.rotation = (1.0D - WMeteorSection.access$200(WMeteorSection.this)) * -90.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\WMeteorSection$WMeteorHeader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */