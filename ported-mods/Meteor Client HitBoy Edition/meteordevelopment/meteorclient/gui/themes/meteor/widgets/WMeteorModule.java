/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.utils.AlignmentX;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WPressable;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_3532;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WMeteorModule
/*    */   extends WPressable
/*    */   implements MeteorWidget
/*    */ {
/*    */   private final Module module;
/*    */   private final String title;
/*    */   private double titleWidth;
/*    */   private double animationProgress1;
/*    */   private double animationProgress2;
/*    */   
/*    */   public WMeteorModule(Module module, String title) {
/* 31 */     this.module = module;
/* 32 */     this.title = title;
/* 33 */     this.tooltip = module.description;
/*    */     
/* 35 */     if (module.isActive()) {
/* 36 */       this.animationProgress1 = 1.0D;
/* 37 */       this.animationProgress2 = 1.0D;
/*    */     } else {
/* 39 */       this.animationProgress1 = 0.0D;
/* 40 */       this.animationProgress2 = 0.0D;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public double pad() {
/* 46 */     return this.theme.scale(4.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCalculateSize() {
/* 51 */     double pad = pad();
/*    */     
/* 53 */     if (this.titleWidth == 0.0D) this.titleWidth = this.theme.textWidth(this.title);
/*    */     
/* 55 */     this.width = pad + this.titleWidth + pad;
/* 56 */     this.height = pad + this.theme.textHeight() + pad;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onPressed(int button) {
/* 61 */     if (button == 0) { this.module.toggle(); }
/* 62 */     else if (button == 1) { MeteorClient.mc.method_1507((class_437)this.theme.moduleScreen(this.module)); }
/*    */   
/*    */   }
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 67 */     MeteorGuiTheme theme = theme();
/* 68 */     double pad = pad();
/*    */     
/* 70 */     this.animationProgress1 += delta * 4.0D * ((this.module.isActive() || this.mouseOver) ? true : -1);
/* 71 */     this.animationProgress1 = class_3532.method_15350(this.animationProgress1, 0.0D, 1.0D);
/*    */     
/* 73 */     this.animationProgress2 += delta * 6.0D * (this.module.isActive() ? true : -1);
/* 74 */     this.animationProgress2 = class_3532.method_15350(this.animationProgress2, 0.0D, 1.0D);
/*    */     
/* 76 */     if (this.animationProgress1 > 0.0D) {
/* 77 */       renderer.quad(this.x, this.y, this.width * this.animationProgress1, this.height, (Color)theme.moduleBackground.get());
/*    */     }
/* 79 */     if (this.animationProgress2 > 0.0D) {
/* 80 */       renderer.quad(this.x, this.y + this.height * (1.0D - this.animationProgress2), theme.scale(2.0D), this.height * this.animationProgress2, (Color)theme.accentColor.get());
/*    */     }
/*    */     
/* 83 */     double x = this.x + pad;
/* 84 */     double w = this.width - pad * 2.0D;
/*    */     
/* 86 */     if (theme.moduleAlignment.get() == AlignmentX.Center) {
/* 87 */       x += w / 2.0D - this.titleWidth / 2.0D;
/*    */     }
/* 89 */     else if (theme.moduleAlignment.get() == AlignmentX.Right) {
/* 90 */       x += w - this.titleWidth;
/*    */     } 
/*    */     
/* 93 */     renderer.text(this.title, x, this.y + pad, (Color)theme.textColor.get(), false);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\WMeteorModule.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */