/*     */ package meteordevelopment.meteorclient.systems.modules.render;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import meteordevelopment.meteorclient.events.render.Render2DEvent;
/*     */ import meteordevelopment.meteorclient.renderer.Renderer2D;
/*     */ import meteordevelopment.meteorclient.renderer.text.TextRenderer;
/*     */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.network.Http;
/*     */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*     */ import meteordevelopment.meteorclient.utils.render.NametagUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_10583;
/*     */ import net.minecraft.class_10599;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1321;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1684;
/*     */ import net.minecraft.class_1937;
/*     */ import org.joml.Vector3d;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityOwner
/*     */   extends Module
/*     */ {
/*  37 */   private static final Color BACKGROUND = new Color(0, 0, 0, 75);
/*  38 */   private static final Color TEXT = new Color(255, 255, 255);
/*     */   
/*  40 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  42 */   private final Setting<Double> scale = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/*  43 */       .name("scale"))
/*  44 */       .description("The scale of the text."))
/*  45 */       .defaultValue(1.0D)
/*  46 */       .min(0.0D)
/*  47 */       .build());
/*     */ 
/*     */   
/*  50 */   private final Vector3d pos = new Vector3d();
/*  51 */   private final Map<UUID, String> uuidToName = new HashMap<>();
/*     */   
/*     */   public EntityOwner() {
/*  54 */     super(Categories.Render, "entity-owner", "Displays the name of the player who owns the entity you're looking at.");
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/*  59 */     this.uuidToName.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onRender2D(Render2DEvent event) {
/*  64 */     for (class_1297 entity : this.mc.field_1687.method_18112()) {
/*     */       class_10583<class_1309> owner;
/*     */       
/*  67 */       if (entity instanceof class_1321) { class_1321 tameable = (class_1321)entity; owner = tameable.method_66287(); }
/*  68 */       else if (entity instanceof class_1684) { class_1684 pearl = (class_1684)entity; owner = class_10583.method_73299((class_10599)pearl.method_24921()); }
/*     */       else
/*     */       { continue; }
/*  71 */        if (owner != null) {
/*  72 */         Utils.set(this.pos, entity, event.tickDelta);
/*  73 */         this.pos.add(0.0D, entity.method_18381(entity.method_18376()) + 0.75D, 0.0D);
/*     */         
/*  75 */         if (NametagUtils.to2D(this.pos, ((Double)this.scale.get()).doubleValue())) {
/*  76 */           renderNametag(getOwnerName(owner));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderNametag(String name) {
/*  83 */     TextRenderer text = TextRenderer.get();
/*     */     
/*  85 */     NametagUtils.begin(this.pos);
/*  86 */     text.beginBig();
/*     */     
/*  88 */     double w = text.getWidth(name);
/*     */     
/*  90 */     double x = -w / 2.0D;
/*  91 */     double y = -text.getHeight();
/*     */     
/*  93 */     Renderer2D.COLOR.begin();
/*  94 */     Renderer2D.COLOR.quad(x - 1.0D, y - 1.0D, w + 2.0D, text.getHeight() + 2.0D, BACKGROUND);
/*  95 */     Renderer2D.COLOR.render();
/*     */     
/*  97 */     text.render(name, x, y, TEXT);
/*     */     
/*  99 */     text.end();
/* 100 */     NametagUtils.end();
/*     */   }
/*     */ 
/*     */   
/*     */   private String getOwnerName(class_10583<class_1309> owner) {
/* 105 */     class_1309 ownerEntity = (class_1309)class_10583.method_66254(owner, (class_1937)this.mc.field_1687, class_1309.class);
/* 106 */     if (ownerEntity instanceof class_1657) { class_1657 playerEntity = (class_1657)ownerEntity; return playerEntity.method_5477().getString(); }
/*     */     
/* 108 */     UUID uuid = owner.method_66263();
/*     */ 
/*     */     
/* 111 */     String name = this.uuidToName.get(uuid);
/* 112 */     if (name != null) return name;
/*     */ 
/*     */     
/* 115 */     MeteorExecutor.execute(() -> {
/*     */           if (isActive()) {
/*     */             ProfileResponse res = (ProfileResponse)Http.get("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "")).sendJson(ProfileResponse.class);
/*     */             if (isActive())
/*     */               if (res == null) {
/*     */                 this.uuidToName.put(uuid, "Failed to get name");
/*     */               } else {
/*     */                 this.uuidToName.put(uuid, res.name);
/*     */               }  
/*     */           } 
/*     */         });
/* 126 */     name = "Retrieving";
/* 127 */     this.uuidToName.put(uuid, name);
/* 128 */     return name;
/*     */   }
/*     */   
/*     */   private static class ProfileResponse {
/*     */     public String name;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\EntityOwner.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */