/*     */ package meteordevelopment.meteorclient.commands.commands;
/*     */ 
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.suggestion.Suggestion;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.tree.CommandNode;
/*     */ import java.net.InetAddress;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.commands.Command;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.mixin.ClientPlayNetworkHandlerAccessor;
/*     */ import meteordevelopment.meteorclient.utils.world.TickRate;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1132;
/*     */ import net.minecraft.class_12096;
/*     */ import net.minecraft.class_12099;
/*     */ import net.minecraft.class_12131;
/*     */ import net.minecraft.class_12206;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1266;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_2558;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2568;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2639;
/*     */ import net.minecraft.class_2641;
/*     */ import net.minecraft.class_2805;
/*     */ import net.minecraft.class_2874;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_639;
/*     */ import net.minecraft.class_642;
/*     */ import net.minecraft.class_7157;
/*     */ import net.minecraft.class_7225;
/*     */ import org.apache.commons.lang3.Strings;
/*     */ 
/*     */ public class ServerCommand extends Command {
/*  46 */   private static final Set<String> ANTICHEAT_LIST = Set.of(new String[] { "nocheatplus", "negativity", "warden", "horizon", "illegalstack", "coreprotect", "exploitsx", "vulcan", "abc", "spartan", "kauri", "anticheatreloaded", "witherac", "godseye", "matrix", "wraith", "antixrayheuristics", "grimac", "themis", "foxaddition", "guardianac", "ggintegrity", "lightanticheat", "anarchyexploitfixes", "polar" });
/*  47 */   private static final Set<String> VERSION_ALIASES = Set.of("version", "ver", "about", "bukkit:version", "bukkit:ver", "bukkit:about");
/*     */   private String alias;
/*  49 */   private int ticks = 0;
/*     */   private boolean tick = false;
/*  51 */   private final List<String> plugins = new ArrayList<>();
/*  52 */   private final List<String> commandTreePlugins = new ArrayList<>();
/*  53 */   private static final Random RANDOM = new Random();
/*     */ 
/*     */   
/*     */   public ServerCommand() {
/*  57 */     super("server", "Prints server information", new String[0]);
/*     */     
/*  59 */     MeteorClient.EVENT_BUS.subscribe(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/*  64 */     builder.executes(context -> {
/*     */           basicInfo();
/*     */           
/*     */           return 1;
/*     */         });
/*  69 */     builder.then(literal("info").executes(ctx -> {
/*     */             basicInfo();
/*     */             
/*     */             return 1;
/*     */           }));
/*  74 */     builder.then(literal("plugins").executes(ctx -> {
/*     */             this.plugins.addAll(this.commandTreePlugins);
/*     */             
/*     */             if (this.alias != null) {
/*     */               mc.method_1562().method_52787((class_2596)new class_2805(RANDOM.nextInt(200), this.alias + " "));
/*     */               this.tick = true;
/*     */             } else {
/*     */               printPlugins();
/*     */             } 
/*     */             return 1;
/*     */           }));
/*  85 */     builder.then(literal("tps").executes(ctx -> {
/*     */             class_124 color; float tps = TickRate.INSTANCE.getTickRate();
/*     */             if (tps > 17.0F) {
/*     */               color = class_124.field_1060;
/*     */             } else if (tps > 12.0F) {
/*     */               color = class_124.field_1054;
/*     */             } else {
/*     */               color = class_124.field_1061;
/*     */             } 
/*     */             info("Current TPS: %s%.2f(default).", new Object[] { color, Float.valueOf(tps) });
/*     */             return 1;
/*     */           })); } private void basicInfo() { class_5250 ipText;
/*  97 */     if (mc.method_1496()) {
/*  98 */       class_1132 class_1132 = mc.method_1576();
/*     */       
/* 100 */       info("Singleplayer", new Object[0]);
/* 101 */       if (class_1132 != null) info("Version: %s", new Object[] { class_1132.method_3827() });
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     class_642 server = mc.method_1558();
/*     */     
/* 108 */     if (server == null) {
/* 109 */       info("Couldn't obtain any server information.", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 113 */     String ipv4 = "";
/*     */     try {
/* 115 */       ipv4 = InetAddress.getByName(server.field_3761).getHostAddress();
/* 116 */     } catch (UnknownHostException unknownHostException) {}
/*     */ 
/*     */ 
/*     */     
/* 120 */     if (ipv4.isEmpty()) {
/* 121 */       ipText = class_2561.method_43470(String.valueOf(class_124.field_1080) + String.valueOf(class_124.field_1080));
/* 122 */       ipText.method_10862(ipText.method_10866()
/* 123 */           .method_10958((class_2558)new class_2558.class_10606(server.field_3761))
/* 124 */           .method_10949((class_2568)new class_2568.class_10613((class_2561)class_2561.method_43470("Copy to clipboard"))));
/*     */     }
/*     */     else {
/*     */       
/* 128 */       ipText = class_2561.method_43470(String.valueOf(class_124.field_1080) + String.valueOf(class_124.field_1080));
/* 129 */       ipText.method_10862(ipText.method_10866()
/* 130 */           .method_10958((class_2558)new class_2558.class_10606(server.field_3761))
/* 131 */           .method_10949((class_2568)new class_2568.class_10613((class_2561)class_2561.method_43470("Copy to clipboard"))));
/*     */       
/* 133 */       class_5250 ipv4Text = class_2561.method_43470(String.format("%s (%s)", new Object[] { class_124.field_1080, ipv4 }));
/* 134 */       ipv4Text.method_10862(ipText.method_10866()
/* 135 */           .method_10958((class_2558)new class_2558.class_10606(ipv4))
/* 136 */           .method_10949((class_2568)new class_2568.class_10613((class_2561)class_2561.method_43470("Copy to clipboard"))));
/*     */       
/* 138 */       ipText.method_10852((class_2561)ipv4Text);
/*     */     } 
/* 140 */     info(
/* 141 */         (class_2561)class_2561.method_43470(String.format("%sIP: ", new Object[] { class_124.field_1080
/* 142 */             })).method_10852((class_2561)ipText));
/*     */ 
/*     */     
/* 145 */     info("Port: %d", new Object[] { Integer.valueOf(class_639.method_2950(server.field_3761).method_2954()) });
/* 146 */     info("Type: %s", new Object[] { (mc.method_1562().method_52790() != null) ? mc.method_1562().method_52790() : "unknown" });
/* 147 */     info("Motd: %s", new Object[] { (server.field_3757 != null) ? server.field_3757.getString() : "unknown" });
/* 148 */     info("Version: %s", new Object[] { server.field_3760.getString() });
/* 149 */     info("Protocol version: %d", new Object[] { Integer.valueOf(server.field_3756) });
/* 150 */     info("Difficulty: %s (Local: %.2f)", new Object[] { mc.field_1687
/* 151 */           .method_8407().method_5463().getString(), 
/* 152 */           Float.valueOf((new class_1266(mc.field_1687
/* 153 */               .method_8407(), mc.field_1687
/* 154 */               .method_8532(), mc.field_1687
/* 155 */               .method_22350(mc.field_1724.method_24515()).method_12033(), class_2874.field_24752[((class_12131)mc.field_1687
/* 156 */                 .method_75728().method_75697(class_12206.field_64343, mc.field_1724.method_24515())).method_75261()]))
/* 157 */             .method_5457()) });
/*     */     
/* 159 */     info("Day: %d", new Object[] { Long.valueOf(mc.field_1687.method_8532() / 24000L) });
/* 160 */     info("Permission level: %s", new Object[] { formatPerms() }); }
/*     */ 
/*     */   
/*     */   public String formatPerms() {
/* 164 */     class_12096 permissions = mc.field_1724.method_75004();
/*     */     
/* 166 */     if (permissions.hasPermission(class_12099.field_63212)) return "4 (Owner)"; 
/* 167 */     if (permissions.hasPermission(class_12099.field_63211)) return "3 (Admin)"; 
/* 168 */     if (permissions.hasPermission(class_12099.field_63210)) return "2 (Gamemaster)"; 
/* 169 */     if (permissions.hasPermission(class_12099.field_63209)) return "1 (Moderator)"; 
/* 170 */     return "0 (No Perms)";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void printPlugins() {
/* 177 */     this.plugins.sort(String.CASE_INSENSITIVE_ORDER);
/* 178 */     this.plugins.replaceAll(this::formatName);
/*     */     
/* 180 */     if (!this.plugins.isEmpty()) {
/* 181 */       info("Plugins (%d): %s ", new Object[] { Integer.valueOf(this.plugins.size()), String.join(", ", (Iterable)this.plugins) });
/*     */     } else {
/* 183 */       error("No plugins found.", new Object[0]);
/*     */     } 
/*     */     
/* 186 */     this.tick = false;
/* 187 */     this.ticks = 0;
/* 188 */     this.plugins.clear();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 193 */     if (!this.tick)
/* 194 */       return;  this.ticks++;
/*     */     
/* 196 */     if (this.ticks >= 100) printPlugins(); 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onSendPacket(PacketEvent.Send event) {
/* 201 */     if (this.tick && event.packet instanceof class_2805) event.cancel();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onReadPacket(PacketEvent.Receive event) {
/* 209 */     class_2596 class_2596 = event.packet; if (class_2596 instanceof class_2641) { class_2641 packet = (class_2641)class_2596;
/* 210 */       ClientPlayNetworkHandlerAccessor handler = (ClientPlayNetworkHandlerAccessor)event.connection.method_10744();
/* 211 */       this.commandTreePlugins.clear();
/* 212 */       this.alias = null;
/*     */ 
/*     */ 
/*     */       
/* 216 */       packet.method_11403(
/* 217 */           class_7157.method_46722((class_7225.class_7874)handler.meteor$getCombinedDynamicRegistries(), handler.meteor$getEnabledFeatures()), 
/* 218 */           ClientPlayNetworkHandlerAccessor.meteor$getCommandNodeFactory())
/* 219 */         .getChildren().forEach(node -> {
/*     */             String[] split = node.getName().split(":");
/*     */ 
/*     */             
/*     */             if (split.length > 1 && !this.commandTreePlugins.contains(split[0])) {
/*     */               this.commandTreePlugins.add(split[0]);
/*     */             }
/*     */             
/*     */             if (this.alias == null && VERSION_ALIASES.contains(node.getName())) {
/*     */               this.alias = node.getName();
/*     */             }
/*     */           }); }
/*     */ 
/*     */     
/* 233 */     if (!this.tick)
/*     */       return; 
/*     */     try {
/* 236 */       class_2596 = event.packet; if (class_2596 instanceof class_2639) { class_2639 packet = (class_2639)class_2596;
/* 237 */         Suggestions matches = packet.method_11397();
/*     */         
/* 239 */         if (matches.isEmpty()) {
/* 240 */           error("An error occurred while trying to find plugins.", new Object[0]);
/*     */           
/*     */           return;
/*     */         } 
/* 244 */         for (Suggestion suggestion : matches.getList()) {
/* 245 */           String pluginName = suggestion.getText();
/* 246 */           if (!this.plugins.contains(pluginName.toLowerCase())) this.plugins.add(pluginName);
/*     */         
/*     */         } 
/* 249 */         printPlugins(); }
/*     */     
/* 251 */     } catch (Exception e) {
/* 252 */       error("An error occurred while trying to find plugins.", new Object[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String formatName(String name) {
/* 257 */     if (ANTICHEAT_LIST.contains(name.toLowerCase())) {
/* 258 */       return String.format("%s%s(default)", new Object[] { class_124.field_1061, name });
/*     */     }
/* 260 */     if (Strings.CI.contains(name, "exploit") || Strings.CI.contains(name, "cheat") || Strings.CI.contains(name, "illegal")) {
/* 261 */       return String.format("%s%s(default)", new Object[] { class_124.field_1061, name });
/*     */     }
/*     */     
/* 264 */     return String.format("(highlight)%s(default)", new Object[] { name });
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\ServerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */