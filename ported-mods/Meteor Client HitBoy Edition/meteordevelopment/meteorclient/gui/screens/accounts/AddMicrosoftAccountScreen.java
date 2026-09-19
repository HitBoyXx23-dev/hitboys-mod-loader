/*    */ package meteordevelopment.meteorclient.gui.screens.accounts;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Account;
/*    */ import meteordevelopment.meteorclient.systems.accounts.MicrosoftLogin;
/*    */ import meteordevelopment.meteorclient.systems.accounts.types.MicrosoftAccount;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AddMicrosoftAccountScreen
/*    */   extends AddAccountScreen
/*    */ {
/*    */   public AddMicrosoftAccountScreen(GuiTheme theme, AccountsScreen parent) {
/* 18 */     super(theme, "Add Microsoft Account", parent);
/*    */   }
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 23 */     String url = MicrosoftLogin.getRefreshToken(refreshToken -> {
/*    */           if (refreshToken != null) {
/*    */             MicrosoftAccount account = new MicrosoftAccount(refreshToken);
/*    */             
/*    */             AccountsScreen.addAccount(null, this.parent, (Account<?>)account);
/*    */           } 
/*    */           
/*    */           method_25419();
/*    */         });
/*    */     
/* 33 */     add((WWidget)this.theme.label("Please select the account to log into in your browser."));
/* 34 */     add((WWidget)this.theme.label("If the link does not automatically open in a few seconds, copy it into your browser."));
/*    */     
/* 36 */     WHorizontalList l = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*    */     
/* 38 */     WButton copy = (WButton)l.add((WWidget)this.theme.button("Copy link")).expandX().widget();
/* 39 */     copy.action = (() -> MeteorClient.mc.field_1774.method_1455(url));
/*    */     
/* 41 */     WButton cancel = (WButton)l.add((WWidget)this.theme.button("Cancel")).expandX().widget();
/* 42 */     cancel.action = (() -> {
/*    */         MicrosoftLogin.cancelLogin();
/*    */         method_25419();
/*    */       });
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_25393() {}
/*    */ 
/*    */   
/*    */   public boolean method_25422() {
/* 53 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\accounts\AddMicrosoftAccountScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */