/*    */ package meteordevelopment.meteorclient.gui.screens.accounts;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AddAccountScreen
/*    */   extends WindowScreen
/*    */ {
/*    */   public final AccountsScreen parent;
/*    */   public WButton add;
/*    */   private int timer;
/*    */   
/*    */   protected AddAccountScreen(GuiTheme theme, String title, AccountsScreen parent) {
/* 18 */     super(theme, title);
/* 19 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_25393() {
/* 24 */     if (this.locked) {
/* 25 */       if (this.timer > 2) {
/* 26 */         this.add.set(getNext(this.add));
/* 27 */         this.timer = 0;
/*    */       } else {
/*    */         
/* 30 */         this.timer++;
/*    */       }
/*    */     
/*    */     }
/* 34 */     else if (!this.add.getText().equals("Add")) {
/* 35 */       this.add.set("Add");
/*    */     } 
/*    */   }
/*    */   
/*    */   private String getNext(WButton add) {
/* 40 */     switch (add.getText()) { case "Add": case "oo0": case "ooo": case "0oo": case "o0o":  }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 45 */       "Add";
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\accounts\AddAccountScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */