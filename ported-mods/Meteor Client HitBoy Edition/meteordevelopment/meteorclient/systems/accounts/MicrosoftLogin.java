/*     */ package meteordevelopment.meteorclient.systems.accounts;
/*     */ 
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.function.Consumer;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*     */ import net.minecraft.class_156;
/*     */ import net.raphimc.minecraftauth.MinecraftAuth;
/*     */ import net.raphimc.minecraftauth.java.JavaAuthManager;
/*     */ import net.raphimc.minecraftauth.java.model.MinecraftProfile;
/*     */ import net.raphimc.minecraftauth.java.model.MinecraftToken;
/*     */ import net.raphimc.minecraftauth.msa.model.MsaApplicationConfig;
/*     */ import net.raphimc.minecraftauth.msa.model.MsaDeviceCode;
/*     */ import net.raphimc.minecraftauth.msa.model.MsaToken;
/*     */ import net.raphimc.minecraftauth.msa.service.util.ParamMsaAuthServiceSupplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MicrosoftLogin
/*     */ {
/*  30 */   private static final MsaApplicationConfig APPLICATION_CONFIG = new MsaApplicationConfig("00000000402b5328", "service::user.auth.xboxlive.com::MBI_SSL");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   private static final AtomicReference<Future<?>> loginTask = new AtomicReference<>();
/*  36 */   private static final AtomicBoolean cancelled = new AtomicBoolean();
/*     */   public static final class LoginData extends Record { private final String mcToken; private final String newRefreshToken;
/*     */     private final String uuid;
/*     */     private final String username;
/*     */     
/*  41 */     public LoginData(String mcToken, String newRefreshToken, String uuid, String username) { this.mcToken = mcToken; this.newRefreshToken = newRefreshToken; this.uuid = uuid; this.username = username; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  41 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData; } public String mcToken() { return this.mcToken; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #41	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/systems/accounts/MicrosoftLogin$LoginData;
/*  41 */       //   0	8	1	o	Ljava/lang/Object; } public String newRefreshToken() { return this.newRefreshToken; } public String uuid() { return this.uuid; } public String username() { return this.username; }
/*     */      }
/*     */   
/*     */   public static String getRefreshToken(Consumer<String> callback) {
/*  45 */     cancelLogin();
/*  46 */     cancelled.set(false);
/*     */     
/*  48 */     CompletableFuture<String> urlFuture = new CompletableFuture<>();
/*     */     
/*  50 */     loginTask.set(MeteorExecutor.executor.submit(() -> {
/*     */             try {
/*     */               JavaAuthManager authManager = JavaAuthManager.create(MinecraftAuth.createHttpClient()).msaApplicationConfig(APPLICATION_CONFIG).login(net.raphimc.minecraftauth.msa.service.impl.DeviceCodeMsaAuthService::new, ());
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               MsaToken msaToken = (MsaToken)authManager.getMsaToken().getUpToDate();
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               if (!cancelled.get()) {
/*     */                 callback.accept(msaToken.getRefreshToken());
/*     */               }
/*  65 */             } catch (InterruptedException e) {
/*     */               Thread.currentThread().interrupt(); if (!urlFuture.isDone())
/*     */                 urlFuture.completeExceptionally(e);  if (cancelled.get())
/*     */                 return; 
/*     */               MeteorClient.LOG.error("Error logging into Microsoft account", e);
/*     */               callback.accept(null);
/*  71 */             } catch (Exception e) {
/*     */               if (!urlFuture.isDone())
/*     */                 urlFuture.completeExceptionally(e); 
/*     */               if (cancelled.get())
/*     */                 return; 
/*     */               MeteorClient.LOG.error("Error logging into Microsoft account", e);
/*     */               callback.accept(null);
/*     */             } 
/*     */           }));
/*     */     try {
/*  81 */       return urlFuture.get();
/*  82 */     } catch (InterruptedException e) {
/*  83 */       Thread.currentThread().interrupt();
/*  84 */       throw new RuntimeException("Interrupted while starting Microsoft login", e);
/*  85 */     } catch (ExecutionException e) {
/*  86 */       throw new RuntimeException("Failed to start Microsoft login", e.getCause());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static LoginData login(String refreshToken) {
/*     */     try {
/*  94 */       JavaAuthManager authManager = JavaAuthManager.create(MinecraftAuth.createHttpClient()).msaApplicationConfig(APPLICATION_CONFIG).login(refreshToken);
/*     */       
/*  96 */       MsaToken msaToken = (MsaToken)authManager.getMsaToken().getUpToDate();
/*  97 */       MinecraftToken minecraftToken = (MinecraftToken)authManager.getMinecraftToken().getUpToDate();
/*  98 */       MinecraftProfile profile = (MinecraftProfile)authManager.getMinecraftProfile().getUpToDate();
/*     */       
/* 100 */       return new LoginData(minecraftToken
/* 101 */           .getToken(), msaToken
/* 102 */           .getRefreshToken(), profile
/* 103 */           .getId().toString(), profile
/* 104 */           .getName());
/*     */     }
/* 106 */     catch (Exception e) {
/* 107 */       MeteorClient.LOG.error("Error logging into Microsoft account", e);
/* 108 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void cancelLogin() {
/* 113 */     cancelled.set(true);
/*     */     
/* 115 */     Future<?> task = loginTask.getAndSet(null);
/*     */     
/* 117 */     if (task != null)
/* 118 */       task.cancel(true); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\accounts\MicrosoftLogin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */