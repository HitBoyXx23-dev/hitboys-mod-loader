/*    */ package meteordevelopment.meteorclient.gui.screens.accounts;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*    */ import meteordevelopment.meteorclient.gui.widgets.input.WTextBox;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Account;
/*    */ import meteordevelopment.meteorclient.systems.accounts.types.TheAlteningAccount;
/*    */ 
/*    */ public class AddAlteningAccountScreen
/*    */   extends AddAccountScreen
/*    */ {
/*    */   public AddAlteningAccountScreen(GuiTheme theme, AccountsScreen parent) {
/* 15 */     super(theme, "Add The Altening Account", parent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 20 */     WTable t = (WTable)add((WWidget)this.theme.table()).widget();
/*    */ 
/*    */     
/* 23 */     t.add((WWidget)this.theme.label("Token: "));
/* 24 */     WTextBox token = (WTextBox)t.add((WWidget)this.theme.textBox("")).minWidth(400.0D).expandX().widget();
/* 25 */     token.setFocused(true);
/* 26 */     t.row();
/*    */ 
/*    */     
/* 29 */     this.add = (WButton)t.add((WWidget)this.theme.button("Add")).expandX().widget();
/* 30 */     this.add.action = (() -> {
/*    */         if (!token.get().isEmpty()) {
/*    */           AccountsScreen.addAccount(this, this.parent, (Account<?>)new TheAlteningAccount(token.get()));
/*    */         }
/*    */       });
/*    */     
/* 36 */     this.enterAction = this.add.action;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\accounts\AddAlteningAccountScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */