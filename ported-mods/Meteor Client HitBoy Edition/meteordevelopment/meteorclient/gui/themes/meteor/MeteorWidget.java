/*    */ package meteordevelopment.meteorclient.gui.themes.meteor;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.renderer.GuiRenderer;
/*    */ import meteordevelopment.meteorclient.gui.utils.BaseWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface MeteorWidget
/*    */   extends BaseWidget
/*    */ {
/*    */   default MeteorGuiTheme theme() {
/* 15 */     return (MeteorGuiTheme)getTheme();
/*    */   }
/*    */   
/*    */   default void renderBackground(GuiRenderer renderer, WWidget widget, Color outlineColor, Color backgroundColor) {
/* 19 */     MeteorGuiTheme theme = theme();
/* 20 */     double s = theme.scale(2.0D);
/*    */     
/* 22 */     renderer.quad(widget.x + s, widget.y + s, widget.width - s * 2.0D, widget.height - s * 2.0D, backgroundColor);
/*    */     
/* 24 */     renderer.quad(widget.x, widget.y, widget.width, s, outlineColor);
/* 25 */     renderer.quad(widget.x, widget.y + widget.height - s, widget.width, s, outlineColor);
/* 26 */     renderer.quad(widget.x, widget.y + s, s, widget.height - s * 2.0D, outlineColor);
/* 27 */     renderer.quad(widget.x + widget.width - s, widget.y + s, s, widget.height - s * 2.0D, outlineColor);
/*    */   }
/*    */   
/*    */   default void renderBackground(GuiRenderer renderer, WWidget widget, boolean pressed, boolean mouseOver) {
/* 31 */     MeteorGuiTheme theme = theme();
/* 32 */     renderBackground(renderer, widget, (Color)theme.outlineColor.get(pressed, mouseOver), (Color)theme.backgroundColor.get(pressed, mouseOver));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\MeteorWidget.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */