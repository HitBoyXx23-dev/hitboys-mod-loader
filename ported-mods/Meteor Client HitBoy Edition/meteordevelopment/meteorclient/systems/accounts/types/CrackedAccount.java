/*    */ package meteordevelopment.meteorclient.systems.accounts.types;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import meteordevelopment.meteorclient.systems.accounts.Account;
/*    */ import meteordevelopment.meteorclient.systems.accounts.AccountType;
/*    */ import net.minecraft.class_320;
/*    */ import net.minecraft.class_4844;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CrackedAccount
/*    */   extends Account<CrackedAccount>
/*    */ {
/*    */   public CrackedAccount(String name) {
/* 17 */     super(AccountType.Cracked, name);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean fetchInfo() {
/* 22 */     this.cache.username = this.name;
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean login() {
/* 28 */     super.login();
/*    */     
/* 30 */     setSession(new class_320(this.name, class_4844.method_43344(this.name), "", Optional.empty(), Optional.empty()));
/* 31 */     return true;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/*    */     CrackedAccount account;
/* 36 */     if (o instanceof CrackedAccount) { account = (CrackedAccount)o; } else { return false; }
/* 37 */      return account.getUsername().equals(getUsername());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\accounts\types\CrackedAccount.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */