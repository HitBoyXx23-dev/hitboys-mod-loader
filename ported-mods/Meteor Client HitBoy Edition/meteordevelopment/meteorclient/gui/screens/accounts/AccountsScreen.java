/*    */ package meteordevelopment.meteorclient.gui.screens.accounts;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.WidgetScreen;
/*    */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WAccount;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WContainer;
/*    */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*    */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Account;
/*    */ import meteordevelopment.meteorclient.systems.accounts.AccountType;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Accounts;
/*    */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*    */ import meteordevelopment.meteorclient.utils.misc.NbtUtils;
/*    */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*    */ import net.minecraft.class_437;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public class AccountsScreen
/*    */   extends WindowScreen {
/*    */   public AccountsScreen(GuiTheme theme) {
/* 25 */     super(theme, "Accounts");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void initWidgets() {
/* 31 */     for (Account<?> account : (Iterable<Account<?>>)Accounts.get()) {
/* 32 */       WAccount wAccount = (WAccount)add((WWidget)this.theme.account((WidgetScreen)this, account)).expandX().widget();
/* 33 */       wAccount.refreshScreenAction = this::reload;
/*    */     } 
/*    */ 
/*    */     
/* 37 */     WHorizontalList l = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*    */     
/* 39 */     addButton((WContainer)l, "Cracked", () -> MeteorClient.mc.method_1507((class_437)new AddCrackedAccountScreen(this.theme, this)));
/* 40 */     addButton((WContainer)l, "Altening", () -> MeteorClient.mc.method_1507((class_437)new AddAlteningAccountScreen(this.theme, this)));
/* 41 */     addButton((WContainer)l, "Session", () -> MeteorClient.mc.method_1507((class_437)new AddSessionAccountScreen(this.theme, this)));
/* 42 */     addButton((WContainer)l, "Microsoft", () -> MeteorClient.mc.method_1507((class_437)new AddMicrosoftAccountScreen(this.theme, this)));
/*    */   }
/*    */   
/*    */   private void addButton(WContainer c, String text, Runnable action) {
/* 46 */     WButton button = (WButton)c.add((WWidget)this.theme.button(text)).expandX().widget();
/* 47 */     button.action = action;
/*    */   }
/*    */   
/*    */   public static void addAccount(@Nullable AddAccountScreen screen, AccountsScreen parent, Account<?> account) {
/* 51 */     if (screen != null) screen.locked = true;
/*    */     
/* 53 */     MeteorExecutor.execute(() -> {
/*    */           if (!account.fetchInfo()) {
/*    */             MeteorClient.mc.execute(());
/*    */             return;
/*    */           } 
/*    */           Accounts.get().add(account);
/*    */           if (account.login()) {
/*    */             if (account.getType() != AccountType.Cracked) {
/*    */               Objects.requireNonNull(parent);
/*    */               account.getCache().loadHead(parent::reload);
/*    */             } 
/*    */             Accounts.get().save();
/*    */           } 
/*    */           MeteorClient.mc.execute(());
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean toClipboard() {
/* 81 */     return NbtUtils.toClipboard((ISerializable)Accounts.get());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean fromClipboard() {
/* 86 */     return NbtUtils.fromClipboard((ISerializable)Accounts.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\accounts\AccountsScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */