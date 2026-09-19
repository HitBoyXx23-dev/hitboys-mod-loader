/*    */ package meteordevelopment.meteorclient.addons;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import net.fabricmc.loader.api.FabricLoader;
/*    */ import net.fabricmc.loader.api.ModContainer;
/*    */ import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
/*    */ import net.fabricmc.loader.api.metadata.ModMetadata;
/*    */ import net.fabricmc.loader.api.metadata.Person;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AddonManager
/*    */ {
/* 18 */   public static final List<MeteorAddon> ADDONS = new ArrayList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public static void init() {
/* 23 */     MeteorClient.ADDON = new MeteorAddon()
/*    */       {
/*    */         public void onInitialize() {}
/*    */ 
/*    */         
/*    */         public String getPackage() {
/* 29 */           return "meteordevelopment.meteorclient";
/*    */         }
/*    */ 
/*    */         
/*    */         public String getWebsite() {
/* 34 */           return "https://meteorclient.com";
/*    */         }
/*    */ 
/*    */         
/*    */         public GithubRepo getRepo() {
/* 39 */           return new GithubRepo("MeteorDevelopment", "meteor-client");
/*    */         }
/*    */ 
/*    */         
/*    */         public String getCommit() {
/* 44 */           String commit = MeteorClient.MOD_META.getCustomValue("meteor-client:commit").getAsString();
/* 45 */           return commit.isEmpty() ? null : commit;
/*    */         }
/*    */       };
/*    */     
/* 49 */     ModMetadata metadata = ((ModContainer)FabricLoader.getInstance().getModContainer("meteor-client").get()).getMetadata();
/*    */     
/* 51 */     MeteorClient.ADDON.name = metadata.getName();
/* 52 */     MeteorClient.ADDON.authors = new String[metadata.getAuthors().size()];
/* 53 */     if (metadata.containsCustomValue("meteor-client:color")) {
/* 54 */       MeteorClient.ADDON.color.parse(metadata.getCustomValue("meteor-client:color").getAsString());
/*    */     }
/*    */     
/* 57 */     int i = 0;
/* 58 */     for (Person author : metadata.getAuthors()) {
/* 59 */       MeteorClient.ADDON.authors[i++] = author.getName();
/*    */     }
/*    */     
/* 62 */     ADDONS.add(MeteorClient.ADDON);
/*    */ 
/*    */ 
/*    */     
/* 66 */     for (EntrypointContainer<MeteorAddon> entrypoint : (Iterable<EntrypointContainer<MeteorAddon>>)FabricLoader.getInstance().getEntrypointContainers("meteor", MeteorAddon.class)) {
/* 67 */       MeteorAddon addon; ModMetadata modMetadata = entrypoint.getProvider().getMetadata();
/*    */       
/*    */       try {
/* 70 */         addon = (MeteorAddon)entrypoint.getEntrypoint();
/* 71 */       } catch (Throwable throwable) {
/* 72 */         throw new RuntimeException("Exception during addon init \"%s\".".formatted(new Object[] { modMetadata.getName() }, ), throwable);
/*    */       } 
/*    */       
/* 75 */       addon.name = modMetadata.getName();
/*    */       
/* 77 */       if (modMetadata.getAuthors().isEmpty()) throw new RuntimeException("Addon \"%s\" requires at least 1 author to be defined in it's fabric.mod.json. See https://fabricmc.net/wiki/documentation:fabric_mod_json_spec".formatted(new Object[] { addon.name })); 
/* 78 */       addon.authors = new String[modMetadata.getAuthors().size()];
/*    */       
/* 80 */       if (modMetadata.containsCustomValue("meteor-client:color")) {
/* 81 */         addon.color.parse(modMetadata.getCustomValue("meteor-client:color").getAsString());
/*    */       }
/*    */       
/* 84 */       int j = 0;
/* 85 */       for (Person author : modMetadata.getAuthors()) {
/* 86 */         addon.authors[j++] = author.getName();
/*    */       }
/*    */       
/* 89 */       ADDONS.add(addon);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\addons\AddonManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */