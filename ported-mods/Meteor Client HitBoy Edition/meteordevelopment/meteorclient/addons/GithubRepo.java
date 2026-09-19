/*    */ package meteordevelopment.meteorclient.addons;
/*    */ public final class GithubRepo extends Record { private final String owner; private final String name; private final String branch; @Nullable
/*    */   private final String accessToken;
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/addons/GithubRepo;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/addons/GithubRepo;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/addons/GithubRepo;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/addons/GithubRepo;
/*    */   }
/*    */   
/* 12 */   public GithubRepo(String owner, String name, String branch, @Nullable String accessToken) { this.owner = owner; this.name = name; this.branch = branch; this.accessToken = accessToken; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/addons/GithubRepo;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lmeteordevelopment/meteorclient/addons/GithubRepo;
/* 12 */     //   0	8	1	o	Ljava/lang/Object; } public String owner() { return this.owner; } public String name() { return this.name; } public String branch() { return this.branch; } @Nullable public String accessToken() { return this.accessToken; }
/*    */    public GithubRepo(String owner, String name, @Nullable String accessToken) {
/* 14 */     this(owner, name, "master", accessToken);
/*    */   }
/*    */   
/*    */   public GithubRepo(String owner, String name) {
/* 18 */     this(owner, name, "master", null);
/*    */   }
/*    */   
/*    */   public String getOwnerName() {
/* 22 */     return this.owner + "/" + this.owner;
/*    */   }
/*    */   
/*    */   public void authenticate(Http.Request request) {
/* 26 */     if (this.accessToken != null && !this.accessToken.isBlank()) {
/* 27 */       request.bearer(this.accessToken);
/*    */     } else {
/* 29 */       String personalAuthToken = System.getenv("meteor.github.authorization");
/* 30 */       if (personalAuthToken != null && !personalAuthToken.isBlank())
/* 31 */         request.bearer(personalAuthToken); 
/*    */     } 
/*    */   } }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\addons\GithubRepo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */