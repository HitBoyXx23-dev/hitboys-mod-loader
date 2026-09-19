/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.input;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorGuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WVerticalList;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
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
/*    */ class null
/*    */   extends WVerticalList
/*    */ {
/*    */   protected void onRender(GuiRenderer renderer1, double mouseX, double mouseY, double delta) {
/* 35 */     MeteorGuiTheme theme1 = WMeteorTextBox.this.theme();
/* 36 */     double s = theme1.scale(2.0D);
/* 37 */     SettingColor settingColor1 = theme1.outlineColor.get();
/*    */     
/* 39 */     SettingColor settingColor2 = theme1.backgroundColor.get();
/* 40 */     int preA = ((Color)settingColor2).a;
/* 41 */     ((Color)settingColor2).a += ((Color)settingColor2).a / 2;
/* 42 */     settingColor2.validate();
/* 43 */     renderer1.quad((WWidget)this, (Color)settingColor2);
/* 44 */     ((Color)settingColor2).a = preA;
/*    */     
/* 46 */     renderer1.quad(this.x, this.y + this.height - s, this.width, s, (Color)settingColor1);
/* 47 */     renderer1.quad(this.x, this.y, s, this.height - s, (Color)settingColor1);
/* 48 */     renderer1.quad(this.x + this.width - s, this.y, s, this.height - s, (Color)settingColor1);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\input\WMeteorTextBox$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */