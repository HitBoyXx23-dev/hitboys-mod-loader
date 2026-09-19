/*    */ package meteordevelopment.meteorclient.systems.accounts;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class LoginData
/*    */   extends Record
/*    */ {
/*    */   private final String mcToken;
/*    */   private final String newRefreshToken;
/*    */   private final String uuid;
/*    */   private final String username;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #41	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #41	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #41	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public LoginData(String mcToken, String newRefreshToken, String uuid, String username) {
/* 41 */     this.mcToken = mcToken; this.newRefreshToken = newRefreshToken; this.uuid = uuid; this.username = username; } public String mcToken() { return this.mcToken; } public String newRefreshToken() { return this.newRefreshToken; } public String uuid() { return this.uuid; } public String username() { return this.username; }
/*    */ 
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\accounts\MicrosoftLogin$LoginData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */