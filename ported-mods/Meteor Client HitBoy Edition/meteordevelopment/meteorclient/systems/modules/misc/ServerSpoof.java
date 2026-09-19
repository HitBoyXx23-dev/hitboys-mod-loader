/*     */ package meteordevelopment.meteorclient.systems.modules.misc;
/*     */ 
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.settings.StringListSetting;
/*     */ import meteordevelopment.meteorclient.settings.StringSetting;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.text.RunnableClickEvent;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2558;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2568;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2720;
/*     */ import net.minecraft.class_2817;
/*     */ import net.minecraft.class_2856;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_8709;
/*     */ import net.minecraft.class_8710;
/*     */ import org.apache.commons.lang3.Strings;
/*     */ 
/*     */ public class ServerSpoof extends Module {
/*  35 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*     */   
/*  37 */   private final Setting<Boolean> spoofBrand = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  38 */       .name("spoof-brand"))
/*  39 */       .description("Whether or not to spoof the brand."))
/*  40 */       .defaultValue(Boolean.valueOf(true)))
/*  41 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<String> brand;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> resourcePack;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> blockChannels;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<List<String>> channels;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class_5250 msg;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean silentAcceptResourcePack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerSpoof() {
/*  78 */     super(Categories.Misc, "server-spoof", "Spoof client brand, resource pack and channels."); Objects.requireNonNull(this.spoofBrand); this.brand = this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)(new StringSetting.Builder()).name("brand")).description("Specify the brand that will be send to the server.")).defaultValue("vanilla")).visible(this.spoofBrand::get)).build()); this.resourcePack = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("resource-pack")).description("Spoof accepting server resource pack.")).defaultValue(Boolean.valueOf(false))).build()); this.blockChannels = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("block-channels")).description("Whether or not to block some channels.")).defaultValue(Boolean.valueOf(true))).build()); Objects.requireNonNull(this.blockChannels); this.channels = this.sgGeneral.add((Setting)((StringListSetting.Builder)((StringListSetting.Builder)((StringListSetting.Builder)(new StringListSetting.Builder()).name("channels")).description("If the channel contains the keyword, this outgoing channel will be blocked.")).defaultValue(new String[] { "fabric", "minecraft:register" }).visible(this.blockChannels::get)).build());
/*     */     this.silentAcceptResourcePack = false;
/*  80 */     this.runInMainMenu = true;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPacketSend(PacketEvent.Send event) {
/*  85 */     if (!isActive())
/*     */       return; 
/*  87 */     if (event.packet instanceof class_2817) {
/*  88 */       class_2960 id = ((class_2817)event.packet).comp_1647().method_56479().comp_2242();
/*     */       
/*  90 */       if (((Boolean)this.blockChannels.get()).booleanValue()) {
/*  91 */         for (String channel : this.channels.get()) {
/*  92 */           if (Strings.CI.contains(id.toString(), channel)) {
/*  93 */             event.cancel();
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       }
/*  99 */       if (((Boolean)this.spoofBrand.get()).booleanValue() && id.equals(class_8709.field_48655.comp_2242())) {
/* 100 */         class_2817 spoofedPacket = new class_2817((class_8710)new class_8709((String)this.brand.get()));
/*     */         
/* 102 */         event.sendSilently((class_2596)spoofedPacket);
/* 103 */         event.cancel();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 108 */     if (this.silentAcceptResourcePack && event.packet instanceof class_2856) event.cancel(); 
/*     */   }
/*     */   @EventHandler
/*     */   private void onPacketReceive(PacketEvent.Receive event) {
/*     */     class_2720 packet;
/* 113 */     if (!isActive() || !((Boolean)this.resourcePack.get()).booleanValue())
/* 114 */       return;  class_2596 class_2596 = event.packet; if (class_2596 instanceof class_2720) { packet = (class_2720)class_2596; }
/*     */     else { return; }
/* 116 */      event.cancel();
/* 117 */     event.connection.method_10743((class_2596)new class_2856(packet.comp_2158(), class_2856.class_2857.field_13016));
/* 118 */     event.connection.method_10743((class_2596)new class_2856(packet.comp_2158(), class_2856.class_2857.field_47704));
/* 119 */     event.connection.method_10743((class_2596)new class_2856(packet.comp_2158(), class_2856.class_2857.field_13017));
/*     */     
/* 121 */     this.msg = class_2561.method_43470("This server has ");
/* 122 */     this.msg.method_27693(packet.comp_2161() ? "a required " : "an optional ").method_27693("resource pack. ");
/*     */     
/* 124 */     class_5250 link = class_2561.method_43470("[Open URL]");
/* 125 */     link.method_10862(link.method_10866()
/* 126 */         .method_10977(class_124.field_1078)
/* 127 */         .method_30938(Boolean.valueOf(true))
/* 128 */         .method_10958((class_2558)new class_2558.class_10608(URI.create(packet.comp_2159())))
/* 129 */         .method_10949((class_2568)new class_2568.class_10613((class_2561)class_2561.method_43470("Click to open the pack url"))));
/*     */ 
/*     */     
/* 132 */     class_5250 acceptance = class_2561.method_43470("[Accept Pack]");
/* 133 */     acceptance.method_10862(acceptance.method_10866()
/* 134 */         .method_10977(class_124.field_1077)
/* 135 */         .method_30938(Boolean.valueOf(true))
/* 136 */         .method_10958((class_2558)new RunnableClickEvent(() -> {
/*     */               URL url = getParsedResourcePackUrl(packet.comp_2159());
/*     */               if (url == null) {
/*     */                 error("Invalid resource pack URL: " + packet.comp_2159(), new Object[0]);
/*     */               } else {
/*     */                 this.silentAcceptResourcePack = true;
/*     */                 this.mc.method_1516().method_55523(packet.comp_2158(), url, packet.comp_2160());
/*     */               } 
/* 144 */             })).method_10949((class_2568)new class_2568.class_10613((class_2561)class_2561.method_43470("Click to accept and apply the pack."))));
/*     */ 
/*     */     
/* 147 */     this.msg.method_10852((class_2561)link).method_27693(" ");
/* 148 */     this.msg.method_10852((class_2561)acceptance).method_27693(".");
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Pre event) {
/* 153 */     if (!isActive() || !Utils.canUpdate() || this.msg == null)
/*     */       return; 
/* 155 */     info((class_2561)this.msg);
/* 156 */     this.msg = null;
/*     */   }
/*     */   
/*     */   private static URL getParsedResourcePackUrl(String url) {
/*     */     try {
/* 161 */       URL uRL = (new URI(url)).toURL();
/* 162 */       String string = uRL.getProtocol();
/* 163 */       return (!"http".equals(string) && !"https".equals(string)) ? null : uRL;
/* 164 */     } catch (MalformedURLException|java.net.URISyntaxException var3) {
/* 165 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\misc\ServerSpoof.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */