/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WFavorite;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WMeteorFavorite
/*    */   extends WFavorite
/*    */   implements MeteorWidget
/*    */ {
/*    */   public WMeteorFavorite(boolean checked) {
/* 14 */     super(checked);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Color getColor() {
/* 19 */     return (Color)(theme()).favoriteColor.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\pressable\WMeteorFavorite.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */