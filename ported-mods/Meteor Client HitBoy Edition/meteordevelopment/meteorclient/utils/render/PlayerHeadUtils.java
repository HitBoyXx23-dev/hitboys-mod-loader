/*    */ package meteordevelopment.meteorclient.utils.render;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import java.io.IOException;
/*    */ import java.util.Base64;
/*    */ import java.util.UUID;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.systems.accounts.TexturesJson;
/*    */ import meteordevelopment.meteorclient.systems.accounts.UuidToProfileResponse;
/*    */ import meteordevelopment.meteorclient.utils.PostInit;
/*    */ import meteordevelopment.meteorclient.utils.network.Http;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerHeadUtils
/*    */ {
/*    */   public static PlayerHeadTexture STEVE_HEAD;
/*    */   
/*    */   @PostInit
/*    */   public static void init() {
/* 21 */     STEVE_HEAD = new PlayerHeadTexture();
/*    */   }
/*    */   
/*    */   public static byte[] fetchHead(UUID id) {
/* 25 */     if (id == null) return null;
/*    */     
/* 27 */     String url = getSkinUrl(id);
/* 28 */     if (url == null) return null;
/*    */     
/*    */     try {
/* 31 */       return PlayerHeadTexture.downloadHead(url);
/* 32 */     } catch (IOException e) {
/* 33 */       MeteorClient.LOG.error("Could not fetch player head for {}.", id, e);
/* 34 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getSkinUrl(UUID id) {
/* 41 */     UuidToProfileResponse res2 = (UuidToProfileResponse)Http.get("https://sessionserver.mojang.com/session/minecraft/profile/" + String.valueOf(id)).exceptionHandler(e -> MeteorClient.LOG.error("Could not contact mojang session servers.", e)).sendJson(UuidToProfileResponse.class);
/* 42 */     if (res2 == null) return null;
/*    */     
/* 44 */     String base64Textures = res2.getPropertyValue("textures");
/* 45 */     if (base64Textures == null) return null;
/*    */     
/* 47 */     TexturesJson textures = (TexturesJson)(new Gson()).fromJson(new String(Base64.getDecoder().decode(base64Textures)), TexturesJson.class);
/* 48 */     if (textures.textures.SKIN == null) return null;
/*    */     
/* 50 */     return textures.textures.SKIN.url;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\PlayerHeadUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */