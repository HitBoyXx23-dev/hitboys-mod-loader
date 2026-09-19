/*    */ package meteordevelopment.meteorclient.renderer.text;
/*    */ 
/*    */ import meteordevelopment.meteorclient.renderer.Fonts;
/*    */ import meteordevelopment.meteorclient.systems.config.Config;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface TextRenderer
/*    */ {
/*    */   static TextRenderer get() {
/* 14 */     return ((Boolean)(Config.get()).customFont.get()).booleanValue() ? Fonts.RENDERER : VanillaTextRenderer.INSTANCE;
/*    */   }
/*    */   
/*    */   void setAlpha(double paramDouble);
/*    */   void begin(double paramDouble, boolean paramBoolean1, boolean paramBoolean2);
/*    */   
/* 20 */   default void begin(double scale) { begin(scale, false, false); } default void begin() {
/* 21 */     begin(1.0D, false, false);
/*    */   } default void beginBig() {
/* 23 */     begin(1.0D, false, true);
/*    */   }
/*    */   double getWidth(String paramString, int paramInt, boolean paramBoolean);
/* 26 */   default double getWidth(String text, boolean shadow) { return getWidth(text, text.length(), shadow); } default double getWidth(String text) {
/* 27 */     return getWidth(text, text.length(), false);
/*    */   } double getHeight(boolean paramBoolean);
/*    */   default double getHeight() {
/* 30 */     return getHeight(false);
/*    */   } double render(String paramString, double paramDouble1, double paramDouble2, Color paramColor, boolean paramBoolean);
/*    */   default double render(String text, double x, double y, Color color) {
/* 33 */     return render(text, x, y, color, false);
/*    */   }
/*    */   
/*    */   boolean isBuilding();
/*    */   
/*    */   void end();
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\text\TextRenderer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */