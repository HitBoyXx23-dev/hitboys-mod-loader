/*    */ package meteordevelopment.meteorclient.gui.widgets;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.WidgetScreen;
/*    */ import meteordevelopment.meteorclient.gui.screens.accounts.AccountInfoScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WConfirmedMinus;
/*    */ import meteordevelopment.meteorclient.renderer.Texture;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Account;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Accounts;
/*    */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_437;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WAccount
/*    */   extends WHorizontalList
/*    */ {
/*    */   public Runnable refreshScreenAction;
/*    */   private final WidgetScreen screen;
/*    */   private final Account<?> account;
/*    */   
/*    */   public WAccount(WidgetScreen screen, Account<?> account) {
/* 27 */     this.screen = screen;
/* 28 */     this.account = account;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void init() {
/* 37 */     add(this.theme.texture(32.0D, 32.0D, this.account.getCache().getHeadTexture().needsRotate() ? 90.0D : 0.0D, (Texture)this.account.getCache().getHeadTexture()));
/*    */ 
/*    */     
/* 40 */     WLabel name = (WLabel)add((WWidget)this.theme.label(this.account.getUsername())).widget();
/* 41 */     if (MeteorClient.mc.method_1548().method_1676().equalsIgnoreCase(this.account.getUsername())) name.color = loggedInColor();
/*    */ 
/*    */     
/* 44 */     WLabel label = (WLabel)add((WWidget)this.theme.label("(" + String.valueOf(this.account.getType()) + ")")).expandCellX().right().widget();
/* 45 */     label.color = accountTypeColor();
/*    */ 
/*    */     
/* 48 */     if (this.account instanceof meteordevelopment.meteorclient.systems.accounts.TokenAccount) {
/* 49 */       WButton info = (WButton)add((WWidget)this.theme.button("Info")).widget();
/* 50 */       info.action = (() -> MeteorClient.mc.method_1507((class_437)new AccountInfoScreen(this.theme, this.account)));
/*    */     } 
/*    */ 
/*    */     
/* 54 */     WButton login = (WButton)add((WWidget)this.theme.button("Login")).widget();
/* 55 */     login.action = (() -> {
/*    */         login.minWidth = login.width;
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         login.set("...");
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         this.screen.locked = true;
/*    */ 
/*    */ 
/*    */ 
/*    */         
/*    */         MeteorExecutor.execute(());
/*    */       });
/*    */ 
/*    */ 
/*    */     
/* 76 */     WConfirmedMinus remove = (WConfirmedMinus)add((WWidget)this.theme.confirmedMinus()).widget();
/* 77 */     remove.action = (() -> {
/*    */         Accounts.get().remove(this.account);
/*    */         if (this.refreshScreenAction != null)
/*    */           this.refreshScreenAction.run(); 
/*    */       });
/*    */   }
/*    */   
/*    */   protected abstract Color loggedInColor();
/*    */   
/*    */   protected abstract Color accountTypeColor();
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\WAccount.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */