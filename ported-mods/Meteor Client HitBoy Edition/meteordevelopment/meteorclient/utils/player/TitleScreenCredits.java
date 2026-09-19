/*     */ package meteordevelopment.meteorclient.utils.player;
/*     */ 
/*     */ import java.net.http.HttpResponse;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.addons.AddonManager;
/*     */ import meteordevelopment.meteorclient.addons.GithubRepo;
/*     */ import meteordevelopment.meteorclient.addons.MeteorAddon;
/*     */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*     */ import meteordevelopment.meteorclient.gui.screens.CommitsScreen;
/*     */ import meteordevelopment.meteorclient.mixininterface.IText;
/*     */ import meteordevelopment.meteorclient.utils.network.Http;
/*     */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*     */ import meteordevelopment.meteorclient.utils.render.MeteorToast;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_368;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5348;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TitleScreenCredits
/*     */ {
/*  32 */   private static final List<Credit> credits = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void init() {
/*  39 */     for (MeteorAddon addon : AddonManager.ADDONS) add(addon);
/*     */ 
/*     */     
/*  42 */     credits.sort(Comparator.comparingInt(value -> (value.addon == MeteorClient.ADDON) ? Integer.MIN_VALUE : -MeteorClient.mc.field_1772.method_27525((class_5348)value.text)));
/*     */ 
/*     */     
/*  45 */     MeteorExecutor.execute(() -> {
/*     */           for (Credit credit : credits) {
/*     */             String message;
/*     */             MeteorToast toast;
/*     */             if (credit.addon.getRepo() == null || credit.addon.getCommit() == null) {
/*     */               continue;
/*     */             }
/*     */             GithubRepo repo = credit.addon.getRepo();
/*     */             Http.Request request = Http.get("https://api.github.com/repos/%s/branches/%s".formatted(new Object[] { repo.getOwnerName(), repo.branch() }));
/*     */             request.exceptionHandler(());
/*     */             repo.authenticate(request);
/*     */             HttpResponse<Response> res = request.sendJsonResponse(Response.class);
/*     */             switch (res.statusCode()) {
/*     */               case 401:
/*     */                 message = "Invalid authentication token for repository '%s'".formatted(new Object[] { repo.getOwnerName() });
/*     */                 toast = (new MeteorToast.Builder("GitHub: Unauthorized")).icon(class_1802.field_8077).text(message).build();
/*     */                 MeteorClient.mc.method_1566().method_1999((class_368)toast);
/*     */                 MeteorClient.LOG.warn(message);
/*     */                 if (System.getenv("meteor.github.authorization") == null) {
/*     */                   MeteorClient.LOG.info("Consider setting an authorization token with the 'meteor.github.authorization' environment variable.");
/*     */                   MeteorClient.LOG.info("See: https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens");
/*     */                 } 
/*     */               case 403:
/*     */                 MeteorClient.LOG.warn("Could not fetch updates for addon '{}': Rate-limited by GitHub.", credit.addon.name);
/*     */               case 404:
/*     */                 MeteorClient.LOG.warn("Could not fetch updates for addon '{}': GitHub repository '{}' not found.", credit.addon.name, repo.getOwnerName());
/*     */               case 200:
/*     */                 if (!credit.addon.getCommit().equals(((Response)res.body()).commit.sha))
/*     */                   synchronized (credit.text) {
/*     */                     credit.text.method_10852((class_2561)class_2561.method_43470("*").method_27692(class_124.field_1061));
/*     */                     ((IText)credit.text).meteor$invalidateCache();
/*     */                   }  
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private static void add(MeteorAddon addon) {
/*  83 */     Credit credit = new Credit(addon);
/*     */     
/*  85 */     credit.text.method_10852((class_2561)class_2561.method_43470(addon.name).method_27694(style -> style.method_36139(addon.color.getPacked())));
/*  86 */     credit.text.method_10852((class_2561)class_2561.method_43470(" by ").method_27692(class_124.field_1080));
/*     */     
/*  88 */     for (int i = 0; i < addon.authors.length; i++) {
/*  89 */       if (i > 0) {
/*  90 */         credit.text.method_10852((class_2561)class_2561.method_43470((i == addon.authors.length - 1) ? " & " : ", ").method_27692(class_124.field_1080));
/*     */       }
/*     */       
/*  93 */       credit.text.method_10852((class_2561)class_2561.method_43470(addon.authors[i]).method_27692(class_124.field_1068));
/*     */     } 
/*     */     
/*  96 */     credits.add(credit);
/*     */   }
/*     */   
/*     */   public static void render(class_332 context) {
/* 100 */     if (credits.isEmpty()) init();
/*     */     
/* 102 */     int y = 3;
/* 103 */     for (Credit credit : credits) {
/* 104 */       synchronized (credit.text) {
/* 105 */         int x = MeteorClient.mc.field_1755.field_22789 - 3 - MeteorClient.mc.field_1772.method_27525((class_5348)credit.text);
/*     */         
/* 107 */         context.method_27535(MeteorClient.mc.field_1772, (class_2561)credit.text, x, y, -1);
/*     */       } 
/*     */       
/* 110 */       Objects.requireNonNull(MeteorClient.mc.field_1772); y += 9 + 2;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean onClicked(double mouseX, double mouseY) {
/* 115 */     int y = 3;
/* 116 */     for (Credit credit : credits) {
/*     */       int width;
/* 118 */       synchronized (credit.text) {
/* 119 */         width = MeteorClient.mc.field_1772.method_27525((class_5348)credit.text);
/*     */       } 
/*     */       
/* 122 */       int x = MeteorClient.mc.field_1755.field_22789 - 3 - width;
/*     */       
/* 124 */       Objects.requireNonNull(MeteorClient.mc.field_1772); if (mouseX >= x && mouseX <= (x + width) && mouseY >= y && mouseY <= (y + 9 + 2) && 
/* 125 */         credit.addon.getRepo() != null && credit.addon.getCommit() != null) {
/* 126 */         MeteorClient.mc.method_1507((class_437)new CommitsScreen(GuiThemes.get(), credit.addon));
/* 127 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 131 */       Objects.requireNonNull(MeteorClient.mc.field_1772); y += 9 + 2;
/*     */     } 
/*     */     
/* 134 */     return false;
/*     */   }
/*     */   
/*     */   private static class Credit {
/*     */     public final MeteorAddon addon;
/* 139 */     public final class_5250 text = class_2561.method_43473();
/*     */     
/*     */     public Credit(MeteorAddon addon) {
/* 142 */       this.addon = addon;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Response {
/*     */     public TitleScreenCredits.Commit commit;
/*     */   }
/*     */   
/*     */   private static class Commit {
/*     */     public String sha;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\TitleScreenCredits.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */