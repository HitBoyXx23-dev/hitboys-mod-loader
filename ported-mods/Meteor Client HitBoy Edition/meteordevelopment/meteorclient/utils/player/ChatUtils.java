/*     */ package meteordevelopment.meteorclient.utils.player;
/*     */ 
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.function.Supplier;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.mixininterface.IChatHud;
/*     */ import meteordevelopment.meteorclient.pathing.BaritoneUtils;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.utils.PostInit;
/*     */ import meteordevelopment.meteorclient.utils.misc.text.MeteorClickEvent;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2558;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2568;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_3545;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5251;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChatUtils
/*     */ {
/*  28 */   private static final List<class_3545<String, Supplier<class_2561>>> customPrefixes = new ArrayList<>();
/*     */ 
/*     */ 
/*     */   
/*     */   private static String forcedPrefixClassName;
/*     */ 
/*     */ 
/*     */   
/*     */   private static class_2561 PREFIX;
/*     */ 
/*     */ 
/*     */   
/*     */   @PostInit
/*     */   public static void init() {
/*  42 */     PREFIX = (class_2561)class_2561.method_43473().method_10862(class_2583.field_24360.method_27706(class_124.field_1080)).method_27693("[").method_10852((class_2561)class_2561.method_43470("Meteor").method_10862(class_2583.field_24360.method_27703(class_5251.method_27717(MeteorClient.ADDON.color.getPacked())))).method_27693("] ");
/*     */   }
/*     */   
/*     */   public static class_2561 getMeteorPrefix() {
/*  46 */     return PREFIX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerCustomPrefix(String packageName, Supplier<class_2561> supplier) {
/*  54 */     for (class_3545<String, Supplier<class_2561>> pair : customPrefixes) {
/*  55 */       if (((String)pair.method_15442()).equals(packageName)) {
/*  56 */         pair.method_34965(supplier);
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  61 */     customPrefixes.add(new class_3545(packageName, supplier));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregisterCustomPrefix(String packageName) {
/*  69 */     customPrefixes.removeIf(pair -> ((String)pair.method_15442()).equals(packageName));
/*     */   }
/*     */   
/*     */   public static void forceNextPrefixClass(Class<?> klass) {
/*  73 */     forcedPrefixClassName = klass.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendPlayerMsg(String message) {
/*  82 */     sendPlayerMsg(message, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendPlayerMsg(String message, boolean addToHistory) {
/*  89 */     if (addToHistory) MeteorClient.mc.field_1705.method_1743().method_1803(message);
/*     */     
/*  91 */     if (message.startsWith("/")) { MeteorClient.mc.field_1724.field_3944.method_45730(message.substring(1)); }
/*  92 */     else { MeteorClient.mc.field_1724.field_3944.method_45729(message); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public static void info(String message, Object... args) {
/*  98 */     sendMsg(class_124.field_1080, message, args);
/*     */   }
/*     */   
/*     */   public static void infoPrefix(String prefix, String message, Object... args) {
/* 102 */     sendMsg(0, prefix, class_124.field_1076, class_124.field_1080, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void warning(String message, Object... args) {
/* 108 */     sendMsg(class_124.field_1054, message, args);
/*     */   }
/*     */   
/*     */   public static void warningPrefix(String prefix, String message, Object... args) {
/* 112 */     sendMsg(0, prefix, class_124.field_1076, class_124.field_1054, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void error(String message, Object... args) {
/* 118 */     sendMsg(class_124.field_1061, message, args);
/*     */   }
/*     */   
/*     */   public static void errorPrefix(String prefix, String message, Object... args) {
/* 122 */     sendMsg(0, prefix, class_124.field_1076, class_124.field_1061, message, args);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendMsg(class_2561 message) {
/* 128 */     sendMsg(null, message);
/*     */   }
/*     */   
/*     */   public static void sendMsg(String prefix, class_2561 message) {
/* 132 */     sendMsg(0, prefix, class_124.field_1076, message);
/*     */   }
/*     */   
/*     */   public static void sendMsg(class_124 color, String message, Object... args) {
/* 136 */     sendMsg(0, null, null, color, message, args);
/*     */   }
/*     */   
/*     */   public static void sendMsg(int id, class_124 color, String message, Object... args) {
/* 140 */     sendMsg(id, null, null, color, message, args);
/*     */   }
/*     */   
/*     */   public static void sendMsg(int id, @Nullable String prefixTitle, @Nullable class_124 prefixColor, class_124 messageColor, String messageContent, Object... args) {
/* 144 */     class_5250 message = formatMsg(String.format(messageContent, args), messageColor);
/* 145 */     sendMsg(id, prefixTitle, prefixColor, (class_2561)message);
/*     */   }
/*     */   
/*     */   public static void sendMsg(int id, @Nullable String prefixTitle, @Nullable class_124 prefixColor, String messageContent, class_124 messageColor) {
/* 149 */     class_5250 message = formatMsg(messageContent, messageColor);
/* 150 */     sendMsg(id, prefixTitle, prefixColor, (class_2561)message);
/*     */   }
/*     */   
/*     */   public static void sendMsg(int id, @Nullable String prefixTitle, @Nullable class_124 prefixColor, class_2561 msg) {
/* 154 */     if (MeteorClient.mc.field_1687 == null)
/*     */       return; 
/* 156 */     class_5250 message = class_2561.method_43473();
/* 157 */     message.method_10852(getPrefix());
/* 158 */     if (prefixTitle != null) message.method_10852((class_2561)getCustomPrefix(prefixTitle, prefixColor)); 
/* 159 */     message.method_10852(msg);
/*     */     
/* 161 */     if (!((Boolean)(Config.get()).deleteChatFeedback.get()).booleanValue()) id = 0;
/*     */     
/* 163 */     int finalId = id;
/* 164 */     MeteorClient.mc.execute(() -> ((IChatHud)MeteorClient.mc.field_1705.method_1743()).meteor$add((class_2561)message, finalId));
/*     */   }
/*     */   
/*     */   private static class_5250 getCustomPrefix(String prefixTitle, class_124 prefixColor) {
/* 168 */     class_5250 prefix = class_2561.method_43473();
/* 169 */     prefix.method_10862(prefix.method_10866().method_27706(class_124.field_1080));
/*     */     
/* 171 */     prefix.method_27693("[");
/*     */     
/* 173 */     class_5250 moduleTitle = class_2561.method_43470(prefixTitle);
/* 174 */     moduleTitle.method_10862(moduleTitle.method_10866().method_27706(prefixColor));
/* 175 */     prefix.method_10852((class_2561)moduleTitle);
/*     */     
/* 177 */     prefix.method_27693("] ");
/*     */     
/* 179 */     return prefix;
/*     */   }
/*     */   
/*     */   private static class_2561 getPrefix() {
/* 183 */     if (customPrefixes.isEmpty()) {
/* 184 */       forcedPrefixClassName = null;
/* 185 */       return PREFIX;
/*     */     } 
/*     */     
/* 188 */     boolean foundChatUtils = false;
/* 189 */     String className = null;
/*     */     
/* 191 */     if (forcedPrefixClassName != null) {
/* 192 */       className = forcedPrefixClassName;
/* 193 */       forcedPrefixClassName = null;
/*     */     } else {
/* 195 */       for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
/* 196 */         if (foundChatUtils)
/* 197 */         { if (!element.getClassName().equals(ChatUtils.class.getName())) {
/* 198 */             className = element.getClassName();
/*     */             
/*     */             break;
/*     */           }  }
/* 202 */         else if (element.getClassName().equals(ChatUtils.class.getName())) { foundChatUtils = true; }
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 207 */     if (className == null) return PREFIX;
/*     */     
/* 209 */     for (class_3545<String, Supplier<class_2561>> pair : customPrefixes) {
/* 210 */       if (className.startsWith((String)pair.method_15442())) {
/* 211 */         class_2561 prefix = ((Supplier<class_2561>)pair.method_15441()).get();
/* 212 */         return (prefix != null) ? prefix : PREFIX;
/*     */       } 
/*     */     } 
/*     */     
/* 216 */     return PREFIX;
/*     */   }
/*     */   
/*     */   private static class_5250 formatMsg(String message, class_124 defaultColor) {
/* 220 */     StringReader reader = new StringReader(message);
/* 221 */     class_5250 text = class_2561.method_43473();
/* 222 */     class_2583 style = class_2583.field_24360.method_27706(defaultColor);
/* 223 */     StringBuilder result = new StringBuilder();
/* 224 */     boolean formatting = false;
/* 225 */     while (reader.canRead()) {
/* 226 */       char c = reader.read();
/* 227 */       if (c == '(') {
/* 228 */         text.method_10852((class_2561)class_2561.method_43470(result.toString()).method_10862(style));
/* 229 */         result.setLength(0);
/* 230 */         result.append(c);
/* 231 */         formatting = true; continue;
/*     */       } 
/* 233 */       result.append(c);
/*     */       
/* 235 */       if (formatting && c == ')') {
/* 236 */         switch (result.toString()) {
/*     */           case "(default)":
/* 238 */             style = style.method_27706(defaultColor);
/* 239 */             result.setLength(0);
/*     */             break;
/*     */           case "(highlight)":
/* 242 */             style = style.method_27706(class_124.field_1068);
/* 243 */             result.setLength(0);
/*     */             break;
/*     */           case "(underline)":
/* 246 */             style = style.method_27706(class_124.field_1073);
/* 247 */             result.setLength(0);
/*     */             break;
/*     */           case "(bold)":
/* 250 */             style = style.method_27706(class_124.field_1067);
/* 251 */             result.setLength(0);
/*     */             break;
/*     */         } 
/* 254 */         formatting = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 259 */     if (!result.isEmpty()) text.method_10852((class_2561)class_2561.method_43470(result.toString()).method_10862(style));
/*     */     
/* 261 */     return text;
/*     */   }
/*     */   
/*     */   public static class_5250 formatCoords(class_243 pos) {
/* 265 */     String coordsString = String.format("(highlight)(underline)%.0f, %.0f, %.0f(default)", new Object[] { Double.valueOf(pos.field_1352), Double.valueOf(pos.field_1351), Double.valueOf(pos.field_1350) });
/* 266 */     class_5250 coordsText = formatMsg(coordsString, class_124.field_1080);
/*     */     
/* 268 */     if (BaritoneUtils.IS_AVAILABLE) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 273 */       class_2583 style = coordsText.method_10866().method_27706(class_124.field_1067).method_10949((class_2568)new class_2568.class_10613((class_2561)class_2561.method_43470("Set as Baritone goal"))).method_10958((class_2558)new MeteorClickEvent(
/* 274 */             String.format("%sgoto %d %d %d", new Object[] { BaritoneUtils.getPrefix(), Integer.valueOf((int)pos.field_1352), Integer.valueOf((int)pos.field_1351), Integer.valueOf((int)pos.field_1350) })));
/*     */ 
/*     */       
/* 277 */       coordsText.method_10862(style);
/*     */     } 
/*     */     
/* 280 */     return coordsText;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\ChatUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */