/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WCheckbox;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_3532;
/*    */ 
/*    */ public class WMeteorCheckbox
/*    */   extends WCheckbox
/*    */   implements MeteorWidget
/*    */ {
/*    */   private double animProgress;
/*    */   
/*    */   public WMeteorCheckbox(boolean checked) {
/* 18 */     super(checked);
/* 19 */     this.animProgress = checked ? 1.0D : 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
/* 24 */     MeteorGuiTheme theme = theme();
/*    */     
/* 26 */     this.animProgress += (this.checked ? true : -1) * delta * 14.0D;
/* 27 */     this.animProgress = class_3532.method_15350(this.animProgress, 0.0D, 1.0D);
/*    */     
/* 29 */     renderBackground(renderer, (WWidget)this, this.pressed, this.mouseOver);
/*    */     
/* 31 */     if (this.animProgress > 0.0D) {
/* 32 */       double cs = (this.width - theme.scale(2.0D)) / 1.75D * this.animProgress;
/* 33 */       renderer.quad(this.x + (this.width - cs) / 2.0D, this.y + (this.height - cs) / 2.0D, cs, cs, (Color)theme.checkboxColor.get());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\pressable\WMeteorCheckbox.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */