/*    */ package meteordevelopment.meteorclient.gui.themes.meteor.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.WidgetScreen;
/*    */ import meteordevelopment.meteorclient.gui.themes.meteor.MeteorWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WAccount;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Account;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WMeteorAccount
/*    */   extends WAccount
/*    */   implements MeteorWidget
/*    */ {
/*    */   public WMeteorAccount(WidgetScreen screen, Account<?> account) {
/* 16 */     super(screen, account);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Color loggedInColor() {
/* 21 */     return (Color)(theme()).loggedInColor.get();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Color accountTypeColor() {
/* 26 */     return (Color)(theme()).textSecondaryColor.get();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\themes\meteor\widgets\WMeteorAccount.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */