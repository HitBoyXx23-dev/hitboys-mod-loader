/*     */ package meteordevelopment.meteorclient.gui.screens;
/*     */ 
/*     */ import java.net.http.HttpResponse;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.format.FormatStyle;
/*     */ import meteordevelopment.meteorclient.addons.GithubRepo;
/*     */ import meteordevelopment.meteorclient.addons.MeteorAddon;
/*     */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*     */ import meteordevelopment.meteorclient.gui.WindowScreen;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WLabel;
/*     */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WHorizontalList;
/*     */ import meteordevelopment.meteorclient.gui.widgets.containers.WTable;
/*     */ import meteordevelopment.meteorclient.gui.widgets.pressable.WButton;
/*     */ import meteordevelopment.meteorclient.utils.network.Http;
/*     */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*     */ import net.minecraft.class_156;
/*     */ 
/*     */ 
/*     */ public class CommitsScreen
/*     */   extends WindowScreen
/*     */ {
/*     */   private final MeteorAddon addon;
/*     */   private Commit[] commits;
/*     */   private int statusCode;
/*     */   
/*     */   public CommitsScreen(GuiTheme theme, MeteorAddon addon) {
/*  28 */     super(theme, "Commits for " + addon.name);
/*     */     
/*  30 */     this.addon = addon;
/*     */     
/*  32 */     this.locked = true;
/*  33 */     this.lockedAllowClose = true;
/*     */     
/*  35 */     MeteorExecutor.execute(() -> {
/*     */           GithubRepo repo = addon.getRepo();
/*     */           if (addon.getCommit() == null || addon.getCommit().equals("${commit}")) {
/*     */             this.statusCode = 404;
/*     */             this.taskAfterRender = this::populateError;
/*     */             return;
/*     */           } 
/*     */           Http.Request request = Http.get(String.format("https://api.github.com/repos/%s/compare/%s...%s", new Object[] { repo.getOwnerName(), addon.getCommit(), repo.branch() }));
/*     */           repo.authenticate(request);
/*     */           HttpResponse<Response> res = request.sendJsonResponse(Response.class);
/*     */           if (res.statusCode() == 200) {
/*     */             this.commits = ((Response)res.body()).commits;
/*     */             this.taskAfterRender = this::populateCommits;
/*     */           } else {
/*     */             this.statusCode = res.statusCode();
/*     */             this.taskAfterRender = this::populateError;
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initWidgets() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateHeader(String headerMessage) {
/*  64 */     WHorizontalList l = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */     
/*  66 */     l.add((WWidget)this.theme.label(headerMessage)).expandX();
/*     */     
/*  68 */     String website = this.addon.getWebsite();
/*  69 */     if (website != null) ((WButton)l.add((WWidget)this.theme.button("Website")).widget()).action = (() -> class_156.method_668().method_670(website));
/*     */     
/*  71 */     ((WButton)l.add((WWidget)this.theme.button("GitHub")).widget()).action = (() -> {
/*     */         GithubRepo repo = this.addon.getRepo();
/*     */         class_156.method_668().method_670(String.format("https://github.com/%s/tree/%s", new Object[] { repo.getOwnerName(), repo.branch() }));
/*     */       });
/*     */   }
/*     */   
/*     */   private void populateError() {
/*  78 */     switch (this.statusCode) { case 400: 
/*     */       case 401: 
/*     */       case 403: 
/*     */       case 404: 
/*     */       default:
/*  83 */         break; }  String errorMessage = "Error Code: " + this.statusCode;
/*     */ 
/*     */     
/*  86 */     populateHeader("There was an error fetching commits: " + errorMessage);
/*     */     
/*  88 */     if (this.statusCode == 401) {
/*  89 */       add((WWidget)this.theme.horizontalSeparator()).padVertical(this.theme.scale(8.0D)).expandX();
/*  90 */       WHorizontalList l = (WHorizontalList)add((WWidget)this.theme.horizontalList()).expandX().widget();
/*     */       
/*  92 */       l.add((WWidget)this.theme.label("Consider using an authentication token: ")).expandX();
/*  93 */       ((WButton)l.add((WWidget)this.theme.button("Authorization Guide")).widget()).action = (() -> class_156.method_668().method_670("https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens"));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  98 */     this.locked = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void populateCommits() {
/* 103 */     String text = "There are %d new commits";
/* 104 */     if (this.commits.length == 1) text = "There is %d new commit"; 
/* 105 */     populateHeader(String.format(text, new Object[] { Integer.valueOf(this.commits.length) }));
/*     */ 
/*     */     
/* 108 */     if (this.commits.length > 0) {
/* 109 */       add((WWidget)this.theme.horizontalSeparator()).padVertical(this.theme.scale(8.0D)).expandX();
/*     */       
/* 111 */       WTable t = (WTable)add((WWidget)this.theme.table()).expandX().widget();
/* 112 */       t.horizontalSpacing = 0.0D;
/*     */       
/* 114 */       for (Commit commit : this.commits) {
/* 115 */         String date = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(commit.commit.committer.date));
/* 116 */         ((WLabel)t.add((WWidget)this.theme.label(date)).top().right().widget()).color = this.theme.textSecondaryColor();
/*     */         
/* 118 */         ((WLabel)t.add((WWidget)this.theme.label(getMessage(commit))).widget()).action = (() -> class_156.method_668().method_670(String.format("https://github.com/%s/commit/%s", new Object[] { this.addon.getRepo().getOwnerName(), commit.sha })));
/* 119 */         t.row();
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     this.locked = false;
/*     */   }
/*     */   
/*     */   private static String getMessage(Commit commit) {
/* 127 */     StringBuilder sb = new StringBuilder(" - ");
/* 128 */     String message = commit.commit.message;
/*     */     
/* 130 */     for (int i = 0; i < message.length(); i++) {
/* 131 */       if (i >= 80) {
/* 132 */         sb.append("...");
/*     */         
/*     */         break;
/*     */       } 
/* 136 */       char c = message.charAt(i);
/*     */       
/* 138 */       if (c == '\n') {
/* 139 */         sb.append("...");
/*     */         
/*     */         break;
/*     */       } 
/* 143 */       sb.append(c);
/*     */     } 
/*     */     
/* 146 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private static class Response {
/*     */     public CommitsScreen.Commit[] commits;
/*     */   }
/*     */   
/*     */   private static class Commit {
/*     */     public String sha;
/*     */     public CommitsScreen.CommitInner commit;
/*     */   }
/*     */   
/*     */   private static class CommitInner {
/*     */     public CommitsScreen.Committer committer;
/*     */     public String message;
/*     */   }
/*     */   
/*     */   private static class Committer {
/*     */     public String date;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\CommitsScreen.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */