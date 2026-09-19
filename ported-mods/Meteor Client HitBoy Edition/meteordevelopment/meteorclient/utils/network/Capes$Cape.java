/*     */ package meteordevelopment.meteorclient.utils.network;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_2960;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Cape
/*     */ {
/* 117 */   private static int COUNT = 0;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final class_2960 identifier;
/*     */   
/*     */   private boolean downloaded;
/*     */   
/*     */   private boolean downloading;
/*     */   private class_1011 img;
/*     */   private int retryTimer;
/*     */   
/*     */   public Cape(String name) {
/* 130 */     this.identifier = MeteorClient.identifier("capes/" + COUNT++);
/* 131 */     this.name = name;
/*     */   }
/*     */   
/*     */   public class_2960 getIdentifier() {
/* 135 */     return this.identifier;
/*     */   }
/*     */   
/*     */   public void download() {
/* 139 */     if (this.downloaded || this.downloading || this.retryTimer > 0)
/* 140 */       return;  this.downloading = true;
/*     */     
/* 142 */     MeteorExecutor.execute(() -> {
/*     */           try {
/*     */             String url = Capes.URLS.get(this.name);
/*     */             
/*     */             if (url == null) {
/*     */               synchronized (Capes.TO_REMOVE) {
/*     */                 Capes.TO_REMOVE.add(this);
/*     */                 
/*     */                 this.downloading = false;
/*     */                 
/*     */                 return;
/*     */               } 
/*     */             }
/*     */             InputStream in = Http.get(url).sendInputStream();
/*     */             if (in == null) {
/*     */               synchronized (Capes.TO_RETRY) {
/*     */                 Capes.TO_RETRY.add(this);
/*     */                 this.retryTimer = 200;
/*     */                 this.downloading = false;
/*     */                 return;
/*     */               } 
/*     */             }
/*     */             this.img = class_1011.method_4309(in);
/*     */             synchronized (Capes.TO_REGISTER) {
/*     */               Capes.TO_REGISTER.add(this);
/*     */             } 
/* 168 */           } catch (IOException e) {
/*     */             MeteorClient.LOG.error("Failed to download cape '{}'", this.name, e);
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public void register() {
/* 175 */     MeteorClient.mc.method_1531().method_4616(this.identifier, (class_1044)new class_1043(null, this.img));
/* 176 */     this.img = null;
/*     */     
/* 178 */     this.downloading = false;
/* 179 */     this.downloaded = true;
/*     */   }
/*     */   
/*     */   public boolean tick() {
/* 183 */     if (this.retryTimer > 0) {
/* 184 */       this.retryTimer--;
/*     */     } else {
/* 186 */       download();
/* 187 */       return true;
/*     */     } 
/*     */     
/* 190 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isDownloaded() {
/* 194 */     return this.downloaded;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\network\Capes$Cape.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */