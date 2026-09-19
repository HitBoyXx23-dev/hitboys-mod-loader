/*    */ package meteordevelopment.meteorclient.addons;
/*    */ 
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
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
/*    */ public abstract class MeteorAddon
/*    */ {
/*    */   public String name;
/*    */   public String[] authors;
/* 18 */   public final Color color = new Color(255, 255, 255);
/*    */   
/*    */   public abstract void onInitialize();
/*    */   
/*    */   public void onRegisterCategories() {}
/*    */   
/*    */   public abstract String getPackage();
/*    */   
/*    */   public String getWebsite() {
/* 27 */     return null;
/*    */   }
/*    */   
/*    */   public GithubRepo getRepo() {
/* 31 */     return null;
/*    */   }
/*    */   
/*    */   public String getCommit() {
/* 35 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\addons\MeteorAddon.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */