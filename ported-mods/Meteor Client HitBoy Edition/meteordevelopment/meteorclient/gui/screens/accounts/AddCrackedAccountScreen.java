/*    */ package meteordevelopment.meteorclient.gui.screens.accounts;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Account;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Accounts;
/*    */ import meteordevelopment.meteorclient.systems.accounts.types.CrackedAccount;
/*    */ 
/*    */ public class AddCrackedAccountScreen
/*    */   extends AddAccountScreen
/*    */ {
/*    */   public AddCrackedAccountScreen(GuiTheme theme, AccountsScreen parent) {
/* 16 */     super(theme, "Add Cracked Account", parent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 21 */     WTable t = (WTable)add((WWidget)this.theme.table()).widget();
/*    */ 
/*    */     
/* 24 */     t.add((WWidget)this.theme.label("Name: "));
/*    */ 
/*    */ 
/*    */     
/* 28 */     WTextBox name = (WTextBox)t.add((WWidget)this.theme.textBox("", "seasnail8169", (text, c) -> (c > ' ' && c < ''))).minWidth(400.0D).expandX().widget();
/* 29 */     name.setFocused(true);
/* 30 */     t.row();
/*    */ 
/*    */     
/* 33 */     this.add = (WButton)t.add((WWidget)this.theme.button("Add")).expandX().widget();
/* 34 */     this.add.action = (() -> {
/*    */         String username = name.get().trim();
/*    */         if (username.length() > 16) {
/*    */           return;
/*    */         }
/*    */         CrackedAccount account = new CrackedAccount(username);
/*    */         if (!Accounts.get().exists((Account)account)) {
/*    */           AccountsScreen.addAccount(this, this.parent, (Account<?>)account);
/*    */         }
/*    */       });
/* 44 */     this.enterAction = this.add.action;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\accounts\AddCrackedAccountScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */