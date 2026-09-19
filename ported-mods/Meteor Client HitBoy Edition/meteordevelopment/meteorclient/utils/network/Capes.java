/*     */ package meteordevelopment.meteorclient.utils.network;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Stream;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.utils.PreInit;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1011;
/*     */ import net.minecraft.class_1043;
/*     */ import net.minecraft.class_1044;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_2960;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Capes
/*     */ {
/*     */   private static final String CAPE_OWNERS_URL = "https://meteorclient.com/api/capeowners";
/*     */   private static final String CAPES_URL = "https://meteorclient.com/api/capes";
/*  28 */   private static final Map<UUID, String> OWNERS = new HashMap<>();
/*  29 */   private static final Map<String, String> URLS = new HashMap<>();
/*  30 */   private static final Map<String, Cape> TEXTURES = new HashMap<>();
/*     */   
/*  32 */   private static final List<Cape> TO_REGISTER = new ArrayList<>();
/*  33 */   private static final List<Cape> TO_RETRY = new ArrayList<>();
/*  34 */   private static final List<Cape> TO_REMOVE = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @PreInit(dependencies = {MeteorExecutor.class})
/*     */   public static void init() {
/*  41 */     OWNERS.clear();
/*  42 */     URLS.clear();
/*  43 */     TEXTURES.clear();
/*  44 */     TO_REGISTER.clear();
/*  45 */     TO_RETRY.clear();
/*  46 */     TO_REMOVE.clear();
/*     */     
/*  48 */     MeteorExecutor.execute(() -> {
/*     */           Stream<String> lines = Http.get("https://meteorclient.com/api/capeowners").exceptionHandler(()).sendLines();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           if (lines != null) {
/*     */             lines.forEach(());
/*     */           } else {
/*     */             return;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           lines = Http.get("https://meteorclient.com/api/capes").sendLines();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           if (lines != null) {
/*     */             lines.forEach(());
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/*  75 */     MeteorClient.EVENT_BUS.subscribe(Capes.class);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private static void onTick(TickEvent.Post event) {
/*  80 */     synchronized (TO_REGISTER) {
/*  81 */       for (Cape cape : TO_REGISTER) cape.register(); 
/*  82 */       TO_REGISTER.clear();
/*     */     } 
/*     */     
/*  85 */     synchronized (TO_RETRY) {
/*  86 */       TO_RETRY.removeIf(Cape::tick);
/*     */     } 
/*     */     
/*  89 */     synchronized (TO_REMOVE) {
/*  90 */       for (Cape cape : TO_REMOVE) {
/*  91 */         URLS.remove(cape.name);
/*  92 */         TEXTURES.remove(cape.name);
/*  93 */         TO_REGISTER.remove(cape);
/*  94 */         TO_RETRY.remove(cape);
/*     */       } 
/*     */       
/*  97 */       TO_REMOVE.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class_2960 get(class_1657 player) {
/* 102 */     String capeName = OWNERS.get(player.method_5667());
/* 103 */     if (capeName != null) {
/* 104 */       Cape cape = TEXTURES.get(capeName);
/* 105 */       if (cape == null) return null;
/*     */       
/* 107 */       if (cape.isDownloaded()) return cape.getIdentifier();
/*     */       
/* 109 */       cape.download();
/* 110 */       return null;
/*     */     } 
/*     */     
/* 113 */     return null;
/*     */   }
/*     */   
/*     */   private static class Cape {
/* 117 */     private static int COUNT = 0;
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private final class_2960 identifier;
/*     */     
/*     */     private boolean downloaded;
/*     */     
/*     */     private boolean downloading;
/*     */     private class_1011 img;
/*     */     private int retryTimer;
/*     */     
/*     */     public Cape(String name) {
/* 130 */       this.identifier = MeteorClient.identifier("capes/" + COUNT++);
/* 131 */       this.name = name;
/*     */     }
/*     */     
/*     */     public class_2960 getIdentifier() {
/* 135 */       return this.identifier;
/*     */     }
/*     */     
/*     */     public void download() {
/* 139 */       if (this.downloaded || this.downloading || this.retryTimer > 0)
/* 140 */         return;  this.downloading = true;
/*     */       
/* 142 */       MeteorExecutor.execute(() -> {
/*     */             try {
/*     */               String url = Capes.URLS.get(this.name);
/*     */               
/*     */               if (url == null) {
/*     */                 synchronized (Capes.TO_REMOVE) {
/*     */                   Capes.TO_REMOVE.add(this);
/*     */                   
/*     */                   this.downloading = false;
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */               }
/*     */               InputStream in = Http.get(url).sendInputStream();
/*     */               if (in == null) {
/*     */                 synchronized (Capes.TO_RETRY) {
/*     */                   Capes.TO_RETRY.add(this);
/*     */                   this.retryTimer = 200;
/*     */                   this.downloading = false;
/*     */                   return;
/*     */                 } 
/*     */               }
/*     */               this.img = class_1011.method_4309(in);
/*     */               synchronized (Capes.TO_REGISTER) {
/*     */                 Capes.TO_REGISTER.add(this);
/*     */               } 
/* 168 */             } catch (IOException e) {
/*     */               MeteorClient.LOG.error("Failed to download cape '{}'", this.name, e);
/*     */             } 
/*     */           });
/*     */     }
/*     */     
/*     */     public void register() {
/* 175 */       MeteorClient.mc.method_1531().method_4616(this.identifier, (class_1044)new class_1043(null, this.img));
/* 176 */       this.img = null;
/*     */       
/* 178 */       this.downloading = false;
/* 179 */       this.downloaded = true;
/*     */     }
/*     */     
/*     */     public boolean tick() {
/* 183 */       if (this.retryTimer > 0) {
/* 184 */         this.retryTimer--;
/*     */       } else {
/* 186 */         download();
/* 187 */         return true;
/*     */       } 
/*     */       
/* 190 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isDownloaded() {
/* 194 */       return this.downloaded;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\network\Capes.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */