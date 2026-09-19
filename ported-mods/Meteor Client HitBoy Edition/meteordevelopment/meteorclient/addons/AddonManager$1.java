/*    */ package meteordevelopment.meteorclient.addons;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
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
/*    */ 
/*    */ class null
/*    */   extends MeteorAddon
/*    */ {
/*    */   public void onInitialize() {}
/*    */   
/*    */   public String getPackage() {
/* 29 */     return "meteordevelopment.meteorclient";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getWebsite() {
/* 34 */     return "https://meteorclient.com";
/*    */   }
/*    */ 
/*    */   
/*    */   public GithubRepo getRepo() {
/* 39 */     return new GithubRepo("MeteorDevelopment", "meteor-client");
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCommit() {
/* 44 */     String commit = MeteorClient.MOD_META.getCustomValue("meteor-client:commit").getAsString();
/* 45 */     return commit.isEmpty() ? null : commit;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\addons\AddonManager$1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */