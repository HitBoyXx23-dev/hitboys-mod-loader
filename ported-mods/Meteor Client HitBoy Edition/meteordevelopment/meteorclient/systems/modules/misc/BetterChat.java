/*     */ package meteordevelopment.meteorclient.systems.modules.misc;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import it.unimi.dsi.fastutil.chars.Char2CharMap;
/*     */ import it.unimi.dsi.fastutil.chars.Char2CharOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.commands.Commands;
/*     */ import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
/*     */ import meteordevelopment.meteorclient.events.game.SendMessageEvent;
/*     */ import meteordevelopment.meteorclient.mixin.ChatHudAccessor;
/*     */ import meteordevelopment.meteorclient.mixininterface.IChatHudLine;
/*     */ import meteordevelopment.meteorclient.mixininterface.IChatHudLineVisible;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.settings.StringListSetting;
/*     */ import meteordevelopment.meteorclient.settings.StringSetting;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.text.MeteorClickEvent;
/*     */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_10799;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_2558;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2568;
/*     */ import net.minecraft.class_2583;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_303;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_640;
/*     */ import net.minecraft.class_7532;
/*     */ 
/*     */ public class BetterChat extends Module {
/*  49 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  50 */   private final SettingGroup sgFilter = this.settings.createGroup("Filter");
/*  51 */   private final SettingGroup sgLongerChat = this.settings.createGroup("Longer Chat");
/*  52 */   private final SettingGroup sgPrefix = this.settings.createGroup("Prefix");
/*  53 */   private final SettingGroup sgSuffix = this.settings.createGroup("Suffix");
/*     */   
/*  55 */   private final Setting<Boolean> annoy = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  56 */       .name("annoy"))
/*  57 */       .description("Makes your messages aNnOyInG."))
/*  58 */       .defaultValue(Boolean.valueOf(false)))
/*  59 */       .build());
/*     */ 
/*     */   
/*  62 */   private final Setting<Boolean> fancy = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  63 */       .name("fancy-chat"))
/*  64 */       .description("Makes your messages ғᴀɴᴄʏ!"))
/*  65 */       .defaultValue(Boolean.valueOf(false)))
/*  66 */       .build());
/*     */ 
/*     */   
/*  69 */   private final Setting<Boolean> timestamps = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/*  70 */       .name("timestamps"))
/*  71 */       .description("Adds client-side time stamps to the beginning of chat messages."))
/*  72 */       .defaultValue(Boolean.valueOf(false)))
/*  73 */       .build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> showSeconds;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> playerHeads;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> coordsProtection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> keepHistory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> antiSpam;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> antiSpamDepth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> antiClear;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> filterRegex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<List<String>> regexFilters;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> infiniteChatBox;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> longerChatHistory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> longerChatLines;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> prefix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> prefixRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<String> prefixText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> prefixSmallCaps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> suffix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> suffixRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<String> suffixText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> suffixSmallCaps;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 238 */   private static final Pattern antiSpamRegex = Pattern.compile(" \\(([0-9]{1,9})\\)$");
/* 239 */   private static final Pattern antiClearRegex = Pattern.compile("\\n(\\n|\\s)+\\n");
/* 240 */   private static final Pattern timestampRegex = Pattern.compile("^(<[0-9]{2}:[0-9]{2}(?::[0-9]{2})?> )");
/* 241 */   private final Char2CharMap SMALL_CAPS; public final IntList lines; @EventHandler private void onMessageReceive(ReceiveMessageEvent event) { class_5250 class_52502; class_2561 class_25611; class_5250 class_52501; class_2561 message = event.getMessage(); if (((Boolean)this.filterRegex.get()).booleanValue()) { String messageString = message.getString(); for (Pattern pattern : this.filterRegexList) { if (pattern.matcher(messageString).find()) { event.cancel(); return; }  }  }  if (((Boolean)this.antiClear.get()).booleanValue()) { String messageString = message.getString(); if (antiClearRegex.matcher(messageString).find()) { class_5250 newMessage = class_2561.method_43473(); message.method_27658((style, string) -> { Matcher antiClearMatcher = antiClearRegex.matcher(string); newMessage.method_10852((class_2561)class_2561.method_43470(antiClearMatcher.find() ? antiClearMatcher.replaceAll("\n\n") : string).method_10862(style)); return Optional.empty(); }class_2583.field_24360); class_52502 = newMessage; }  }  if (((Boolean)this.antiSpam.get()).booleanValue()) { class_2561 antiSpammed = appendAntiSpam((class_2561)class_52502); if (antiSpammed != null) class_25611 = antiSpammed;  }  if (((Boolean)this.timestamps.get()).booleanValue()) { class_5250 class_5250 = class_2561.method_43470("<" + this.dateFormat.format(new Date()) + "> ").method_27692(class_124.field_1080); class_52501 = class_2561.method_43473().method_10852((class_2561)class_5250).method_10852(class_25611); }  event.setMessage((class_2561)class_52501); } @EventHandler private void onMessageSend(SendMessageEvent event) { String message = event.message; if (((Boolean)this.annoy.get()).booleanValue()) message = applyAnnoy(message);  if (((Boolean)this.fancy.get()).booleanValue()) message = applyFancy(message);  message = getPrefix() + getPrefix() + message; if (((Boolean)this.coordsProtection.get()).booleanValue() && containsCoordinates(message)) { class_5250 warningMessage = class_2561.method_43470("It looks like there are coordinates in your message! "); class_5250 sendButton = getSendButton(message); warningMessage.method_10852((class_2561)sendButton); ChatUtils.sendMsg((class_2561)warningMessage); event.cancel(); return; }  event.message = message; } private static final Pattern usernameRegex = Pattern.compile("^(?:<[0-9]{2}:[0-9]{2}>\\s)?<(.*?)>.*");
/*     */   private class_2561 appendAntiSpam(class_2561 text) { class_5250 class_5250; String textString = text.getString(); class_2561 returnText = null; int messageIndex = -1; List<class_303> messages = ((ChatHudAccessor)this.mc.field_1705.method_1743()).meteor$getMessages(); if (messages.isEmpty()) return null;  for (int i = 0; i < Math.min(((Integer)this.antiSpamDepth.get()).intValue(), messages.size()); i++) { String stringToCheck = ((class_303)messages.get(i)).comp_893().getString(); Matcher timestampMatcher = timestampRegex.matcher(stringToCheck); if (timestampMatcher.find()) stringToCheck = stringToCheck.substring(timestampMatcher.end());  if (textString.equals(stringToCheck)) { messageIndex = i; class_5250 = text.method_27661().method_10852((class_2561)class_2561.method_43470(" (2)").method_27692(class_124.field_1080)); break; }  Matcher matcher = antiSpamRegex.matcher(stringToCheck); if (matcher.find()) { String group = matcher.group(matcher.groupCount()); int number = Integer.parseInt(group); if (stringToCheck.substring(0, matcher.start()).equals(textString)) { messageIndex = i; class_5250 = text.method_27661().method_10852((class_2561)class_2561.method_43470(" (" + number + 1 + ")").method_27692(class_124.field_1080)); break; }  }  }  if (class_5250 != null) { List<class_303.class_7590> visible = ((ChatHudAccessor)this.mc.field_1705.method_1743()).meteor$getVisibleMessages(); int start = -1; int j; for (j = 0; j < messageIndex; j++) start += this.lines.getInt(j);  j = this.lines.getInt(messageIndex); while (j > 0) { visible.remove(start + 1); j--; }  messages.remove(messageIndex); this.lines.removeInt(messageIndex); }  return (class_2561)class_5250; }
/*     */   public void removeLine(int index) { if (index >= this.lines.size()) { if (((Boolean)this.antiSpam.get()).booleanValue()) { error("Issue detected with the anti-spam system! Likely a compatibility issue with another mod. Disabling anti-spam to protect chat integrity.", new Object[0]); this.antiSpam.set(Boolean.valueOf(false)); }  return; }  this.lines.removeInt(index); }
/*     */   private static final class CustomHeadEntry extends Record {
/*     */     private final String prefix;
/*     */     private final class_2960 texture;
/*     */     private CustomHeadEntry(String prefix, class_2960 texture) { this.prefix = prefix; this.texture = texture; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/systems/modules/misc/BetterChat$CustomHeadEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #400	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/modules/misc/BetterChat$CustomHeadEntry; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/systems/modules/misc/BetterChat$CustomHeadEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #400	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/modules/misc/BetterChat$CustomHeadEntry; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/systems/modules/misc/BetterChat$CustomHeadEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #400	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/systems/modules/misc/BetterChat$CustomHeadEntry;
/* 247 */       //   0	8	1	o	Ljava/lang/Object; } public String prefix() { return this.prefix; } public class_2960 texture() { return this.texture; } } public BetterChat() { super(Categories.Misc, "better-chat", "Improves your chat experience in various ways.");
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
/*     */     Objects.requireNonNull(this.timestamps);
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
/*     */     this.showSeconds = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("show-seconds")).description("Shows seconds in the chat message timestamps")).defaultValue(Boolean.valueOf(false))).visible(this.timestamps::get)).onChanged(o -> updateDateFormat())).build());
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
/*     */     this.playerHeads = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("player-heads")).description("Displays player heads next to their messages.")).defaultValue(Boolean.valueOf(true))).build());
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
/*     */     this.coordsProtection = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("coords-protection")).description("Prevents you from sending messages in chat that may contain coordinates.")).defaultValue(Boolean.valueOf(true))).build());
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
/*     */     this.keepHistory = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("keep-history")).description("Prevents the chat history from being cleared when disconnecting.")).defaultValue(Boolean.valueOf(true))).build());
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
/*     */     this.antiSpam = this.sgFilter.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("anti-spam")).description("Blocks duplicate messages from filling your chat.")).defaultValue(Boolean.valueOf(true))).build());
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
/*     */     Objects.requireNonNull(this.antiSpam);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.antiSpamDepth = this.sgFilter.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("depth")).description("How many messages to filter.")).defaultValue(Integer.valueOf(20))).min(1).sliderMin(1).visible(this.antiSpam::get)).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.antiClear = this.sgFilter.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("anti-clear")).description("Prevents servers from clearing chat.")).defaultValue(Boolean.valueOf(true))).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.filterRegex = this.sgFilter.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("filter-regex")).description("Filter out chat messages that match the regex filter.")).defaultValue(Boolean.valueOf(false))).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Objects.requireNonNull(this.filterRegex);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.regexFilters = this.sgFilter.add((Setting)((StringListSetting.Builder)((StringListSetting.Builder)((StringListSetting.Builder)((StringListSetting.Builder)(new StringListSetting.Builder()).name("regex-filter")).description("Regex filter used for filtering chat messages.")).visible(this.filterRegex::get)).onChanged(strings -> compileFilterRegexList())).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.infiniteChatBox = this.sgLongerChat.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("infinite-chat-box")).description("Lets you type infinitely long messages.")).defaultValue(Boolean.valueOf(true))).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.longerChatHistory = this.sgLongerChat.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("longer-chat-history")).description("Extends chat length.")).defaultValue(Boolean.valueOf(true))).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Objects.requireNonNull(this.longerChatHistory);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.longerChatLines = this.sgLongerChat.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("extra-lines")).description("The amount of extra chat lines.")).defaultValue(Integer.valueOf(1000))).min(0).sliderRange(0, 1000).visible(this.longerChatHistory::get)).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.prefix = this.sgPrefix.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("prefix")).description("Adds a prefix to your chat messages.")).defaultValue(Boolean.valueOf(false))).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.prefixRandom = this.sgPrefix.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("random")).description("Uses a random number as your prefix.")).defaultValue(Boolean.valueOf(false))).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.prefixText = this.sgPrefix.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)(new StringSetting.Builder()).name("text")).description("The text to add as your prefix.")).defaultValue("> ")).visible(() -> !((Boolean)this.prefixRandom.get()).booleanValue())).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.prefixSmallCaps = this.sgPrefix.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("small-caps")).description("Uses small caps in the prefix.")).defaultValue(Boolean.valueOf(false))).visible(() -> !((Boolean)this.prefixRandom.get()).booleanValue())).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.suffix = this.sgSuffix.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("suffix")).description("Adds a suffix to your chat messages.")).defaultValue(Boolean.valueOf(false))).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.suffixRandom = this.sgSuffix.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("random")).description("Uses a random number as your suffix.")).defaultValue(Boolean.valueOf(false))).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.suffixText = this.sgSuffix.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)(new StringSetting.Builder()).name("text")).description("The text to add as your suffix.")).defaultValue(" | meteor on crack!")).visible(() -> !((Boolean)this.suffixRandom.get()).booleanValue())).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.suffixSmallCaps = this.sgSuffix.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("small-caps")).description("Uses small caps in the suffix.")).defaultValue(Boolean.valueOf(true))).visible(() -> !((Boolean)this.suffixRandom.get()).booleanValue())).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.SMALL_CAPS = (Char2CharMap)new Char2CharOpenHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     this.lines = (IntList)new IntArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 524 */     this.filterRegexList = new ArrayList<>(); String[] a = "abcdefghijklmnopqrstuvwxyz".split(""); String[] b = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴩqʀꜱᴛᴜᴠᴡxyᴢ".split(""); for (int i = 0; i < a.length; ) {
/*     */       this.SMALL_CAPS.put(a[i].charAt(0), b[i].charAt(0)); i++;
/*     */     } 
/* 527 */     compileFilterRegexList(); } private static final List<CustomHeadEntry> CUSTOM_HEAD_ENTRIES = new ArrayList<>(); private static final Pattern TIMESTAMP_REGEX = Pattern.compile("^<\\d{1,2}:\\d{1,2}>"); public class_303.class_7590 line; private SimpleDateFormat dateFormat; private final List<Pattern> filterRegexList; private void compileFilterRegexList() { this.filterRegexList.clear();
/*     */     
/* 529 */     for (int i = 0; i < ((List)this.regexFilters.get()).size(); i++)
/*     */     { 
/* 531 */       try { this.filterRegexList.add(Pattern.compile(((List<String>)this.regexFilters.get()).get(i))); }
/* 532 */       catch (PatternSyntaxException e)
/* 533 */       { String removed = ((List<String>)this.regexFilters.get()).remove(i);
/* 534 */         error("Removing Invalid regex: %s", new Object[] { removed }); }  }  } public static void registerCustomHead(String prefix, class_2960 texture) { CUSTOM_HEAD_ENTRIES.add(new CustomHeadEntry(prefix, texture)); } static { registerCustomHead("[Meteor]", MeteorClient.identifier("textures/icons/chat/meteor.png")); registerCustomHead("[Baritone]", MeteorClient.identifier("textures/icons/chat/baritone.png")); } public int modifyChatWidth(int width) { if (isActive() && ((Boolean)this.playerHeads.get()).booleanValue()) return width + 10;  return width; }
/*     */   public void beforeDrawMessage(class_332 context, int y, int color) { if (!isActive() || !((Boolean)this.playerHeads.get()).booleanValue() || this.line == null) return;  if (((IChatHudLineVisible)this.line).meteor$isStartOfEntry()) drawTexture(context, (IChatHudLine)this.line, y, color);  }
/*     */   public void afterDrawMessage() { if (!isActive() || !((Boolean)this.playerHeads.get()).booleanValue()) return;  this.line = null; }
/*     */   private void drawTexture(class_332 context, IChatHudLine line, int y, int color) { String text = line.meteor$getText().trim(); int startOffset = 0; try { Matcher m = TIMESTAMP_REGEX.matcher(text); if (m.find()) startOffset = m.end() + 1;  } catch (IllegalStateException illegalStateException) {} for (CustomHeadEntry customHeadEntry : CUSTOM_HEAD_ENTRIES) { if (text.startsWith(customHeadEntry.prefix(), startOffset)) { context.method_25293(class_10799.field_56883, customHeadEntry.texture(), 0, y, 0.0F, 0.0F, 8, 8, 64, 64, 64, 64, color); return; }  }  GameProfile sender = getSender(line, text); if (sender == null) return;  class_640 entry = this.mc.method_1562().method_2871(sender.id()); if (entry == null) return;  class_7532.method_44443(context, entry.method_52810(), 0, y, 8, color); }
/*     */   private GameProfile getSender(IChatHudLine line, String text) { GameProfile sender = line.meteor$getSender(); if (sender == null) { Matcher usernameMatcher = usernameRegex.matcher(text); if (usernameMatcher.matches()) { String username = usernameMatcher.group(1); class_640 entry = this.mc.method_1562().method_2874(username); if (entry != null) sender = entry.method_2966();  }  }  return sender; }
/*     */   private void updateDateFormat() { this.dateFormat = new SimpleDateFormat(((Boolean)this.showSeconds.get()).booleanValue() ? "HH:mm:ss" : "HH:mm"); }
/*     */   private String applyAnnoy(String message) { StringBuilder sb = new StringBuilder(message.length()); boolean upperCase = true; for (int cp : message.codePoints().toArray()) { if (upperCase) { sb.appendCodePoint(Character.toUpperCase(cp)); } else { sb.appendCodePoint(Character.toLowerCase(cp)); }  upperCase = !upperCase; }  message = sb.toString(); return message; }
/*     */   private String applyFancy(String message) { StringBuilder sb = new StringBuilder(); for (char ch : message.toCharArray()) sb.append(this.SMALL_CAPS.getOrDefault(ch, ch));  return sb.toString(); }
/* 542 */   private String getPrefix() { return ((Boolean)this.prefix.get()).booleanValue() ? getAffix((String)this.prefixText.get(), ((Boolean)this.prefixSmallCaps.get()).booleanValue(), ((Boolean)this.prefixRandom.get()).booleanValue()) : ""; }
/*     */ 
/*     */   
/*     */   private String getSuffix() {
/* 546 */     return ((Boolean)this.suffix.get()).booleanValue() ? getAffix((String)this.suffixText.get(), ((Boolean)this.suffixSmallCaps.get()).booleanValue(), ((Boolean)this.suffixRandom.get()).booleanValue()) : "";
/*     */   }
/*     */   
/*     */   private String getAffix(String text, boolean smallcaps, boolean random) {
/* 550 */     if (random) return String.format("(%03d) ", new Object[] { Integer.valueOf(Utils.random(0, 1000)) }); 
/* 551 */     if (smallcaps) return applyFancy(text); 
/* 552 */     return text;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 557 */   private static final Pattern coordRegex = Pattern.compile("(?<x>-?\\d{3,}(?:\\.\\d*)?)(?:\\s+(?<y>-?\\d{1,3}(?:\\.\\d*)?))?\\s+(?<z>-?\\d{3,}(?:\\.\\d*)?)");
/*     */   
/*     */   private boolean containsCoordinates(String message) {
/* 560 */     return coordRegex.matcher(message).find();
/*     */   }
/*     */   
/*     */   private class_5250 getSendButton(String message) {
/* 564 */     class_5250 sendButton = class_2561.method_43470("[SEND ANYWAY]");
/* 565 */     class_5250 hintBaseText = class_2561.method_43470("");
/*     */     
/* 567 */     class_5250 hintMsg = class_2561.method_43470("Send your message to the global chat even if there are coordinates:");
/* 568 */     hintMsg.method_10862(hintBaseText.method_10866().method_27706(class_124.field_1080));
/* 569 */     hintBaseText.method_10852((class_2561)hintMsg);
/*     */     
/* 571 */     hintBaseText.method_10852((class_2561)class_2561.method_43470("\n" + message));
/*     */     
/* 573 */     sendButton.method_10862(sendButton.method_10866()
/* 574 */         .method_27706(class_124.field_1079)
/* 575 */         .method_10958((class_2558)new MeteorClickEvent(Commands.get("say").toString(new String[] { message
/* 576 */               }))).method_10949((class_2568)new class_2568.class_10613((class_2561)hintBaseText)));
/*     */ 
/*     */     
/* 579 */     return sendButton;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInfiniteChatBox() {
/* 585 */     return (isActive() && ((Boolean)this.infiniteChatBox.get()).booleanValue());
/*     */   }
/*     */   
/*     */   public boolean isLongerChat() {
/* 589 */     return (isActive() && ((Boolean)this.longerChatHistory.get()).booleanValue());
/*     */   }
/*     */   public boolean keepHistory() {
/* 592 */     return (isActive() && ((Boolean)this.keepHistory.get()).booleanValue());
/*     */   }
/*     */   public int getExtraChatLines() {
/* 595 */     return ((Integer)this.longerChatLines.get()).intValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\misc\BetterChat.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */