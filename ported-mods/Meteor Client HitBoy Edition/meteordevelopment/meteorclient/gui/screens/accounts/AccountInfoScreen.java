/*    */ package meteordevelopment.meteorclient.gui.screens.accounts;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Account;
/*    */ import meteordevelopment.meteorclient.systems.accounts.AccountType;
/*    */ import meteordevelopment.meteorclient.systems.accounts.TokenAccount;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccountInfoScreen
/*    */   extends WindowScreen
/*    */ {
/*    */   private final Account<?> account;
/*    */   
/*    */   public AccountInfoScreen(GuiTheme theme, Account<?> account) {
/* 23 */     super(theme, account.getUsername() + " details");
/* 24 */     this.account = account;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 29 */     TokenAccount e = (TokenAccount)this.account;
/* 30 */     WHorizontalList l = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*    */     
/* 32 */     String tokenLabel = String.valueOf(this.account.getType()) + " token:";
/* 33 */     if (this.account.getType() == AccountType.Session) tokenLabel = "";
/*    */     
/* 35 */     WButton copy = this.theme.button("Copy");
/* 36 */     copy.action = (() -> MeteorClient.mc.field_1774.method_1455(e.getToken()));
/*    */     
/* 38 */     l.add((WWidget)this.theme.label(tokenLabel));
/* 39 */     l.add((WWidget)this.theme.label((this.account.getType() == AccountType.Session) ? "Click to copy Token" : e.getToken()).color(Color.GRAY)).pad(5.0D);
/* 40 */     l.add((WWidget)copy);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\accounts\AccountInfoScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */