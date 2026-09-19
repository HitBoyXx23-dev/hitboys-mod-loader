/*    */ package meteordevelopment.meteorclient.systems.accounts.types;
/*    */ 
/*    */ import com.mojang.util.UndashedUuid;
/*    */ import java.util.Optional;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Account;
/*    */ import meteordevelopment.meteorclient.systems.accounts.AccountType;
/*    */ import meteordevelopment.meteorclient.systems.accounts.MicrosoftLogin;
/*    */ import net.minecraft.class_320;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MicrosoftAccount
/*    */   extends Account<MicrosoftAccount>
/*    */ {
/*    */   @Nullable
/*    */   private String token;
/*    */   
/*    */   public MicrosoftAccount(String refreshToken) {
/* 20 */     super(AccountType.Microsoft, refreshToken);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean fetchInfo() {
/* 25 */     this.token = auth();
/* 26 */     return (this.token != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean login() {
/* 31 */     if (this.token == null) return false;
/*    */     
/* 33 */     super.login();
/*    */     
/* 35 */     setSession(new class_320(this.cache.username, UndashedUuid.fromStringLenient(this.cache.uuid), this.token, Optional.empty(), Optional.empty()));
/* 36 */     return true;
/*    */   }
/*    */   @Nullable
/*    */   private String auth() {
/* 40 */     MicrosoftLogin.LoginData data = MicrosoftLogin.login(this.name);
/* 41 */     if (data == null || data.newRefreshToken() == null) return null;
/*    */     
/* 43 */     this.name = data.newRefreshToken();
/* 44 */     this.cache.username = data.username();
/* 45 */     this.cache.uuid = data.uuid();
/*    */     
/* 47 */     return data.mcToken();
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/*    */     MicrosoftAccount account;
/* 52 */     if (o instanceof MicrosoftAccount) { account = (MicrosoftAccount)o; } else { return false; }
/* 53 */      return account.name.equals(this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\accounts\types\MicrosoftAccount.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */