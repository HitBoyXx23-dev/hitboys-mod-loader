/*     */ package meteordevelopment.meteorclient.systems.modules.misc;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.OpenOption;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.StandardOpenOption;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.LocalDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*     */ import meteordevelopment.meteorclient.settings.IntSetting;
/*     */ import meteordevelopment.meteorclient.settings.PacketListSetting;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*     */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.network.PacketUtils;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_2596;
/*     */ import org.jspecify.annotations.NullMarked;
/*     */ 
/*     */ @NullMarked
/*     */ public class PacketLogger extends Module {
/*  36 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*  37 */   private final SettingGroup sgOutput = this.settings.createGroup("Output");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Set<Class<? extends class_2596<?>>>> s2cPackets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Set<Class<? extends class_2596<?>>>> c2sPackets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> showTimestamp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> showPacketData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> showCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> showSummary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> logToChat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Boolean> logToFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> flushInterval;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> maxFileSizeMB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Setting<Integer> maxTotalLogsMB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   private static final Path PACKET_LOGS_DIR = MeteorClient.FOLDER.toPath().resolve("packet-logs");
/* 128 */   private static final int LINE_SEPARATOR_BYTES = (System.lineSeparator().getBytes(StandardCharsets.UTF_8)).length;
/* 129 */   private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
/* 130 */   private static final DateTimeFormatter FILE_NAME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
/*     */   private final Reference2IntOpenHashMap<Class<? extends class_2596<?>>> packetCounts;
/*     */   private BufferedWriter fileWriter;
/*     */   private long lastFlushMs;
/*     */   private long currentFileSizeBytes;
/*     */   private int currentFileIndex;
/*     */   private LocalDateTime sessionStartTime;
/*     */   
/*     */   public PacketLogger() {
/* 139 */     super(Categories.Misc, "packet-logger", "Allows you to log certain packets."); this.s2cPackets = this.sgGeneral.add((Setting)((PacketListSetting.Builder)((PacketListSetting.Builder)(new PacketListSetting.Builder()).name("S2C-packets")).description("Server-to-client packets to log.")).filter(aClass -> PacketUtils.getS2CPackets().contains(aClass)).build()); this.c2sPackets = this.sgGeneral.add((Setting)((PacketListSetting.Builder)((PacketListSetting.Builder)(new PacketListSetting.Builder()).name("C2S-packets")).description("Client-to-server packets to log.")).filter(aClass -> PacketUtils.getC2SPackets().contains(aClass)).build()); this.showTimestamp = this.sgOutput.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("show-timestamp")).description("Show timestamp for each logged packet.")).defaultValue(Boolean.valueOf(true))).build()); this.showPacketData = this.sgOutput.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("show-packet-data")).description("Show the packet's toString() data for debugging.")).defaultValue(Boolean.valueOf(false))).build()); this.showCount = this.sgOutput.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("show-count")).description("Show how many times each packet type has been logged.")).defaultValue(Boolean.valueOf(true))).build()); this.showSummary = this.sgOutput.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("show-summary")).description("Show final packet count summary when module is deactivated.")).defaultValue(Boolean.valueOf(true))).build()); this.logToChat = this.sgOutput.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("log-to-chat")).description("Log packets to chat.")).defaultValue(Boolean.valueOf(true))).build()); this.logToFile = this.sgOutput.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("log-to-file")).description("Save packet logs to a file in the meteor-client folder.")).defaultValue(Boolean.valueOf(false))).build()); Objects.requireNonNull(this.logToFile); this.flushInterval = this.sgOutput.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("flush-interval")).description("How often to flush logs to disk (in seconds).")).defaultValue(Integer.valueOf(1))).min(1).sliderMax(10).visible(this.logToFile::get)).build()); Objects.requireNonNull(this.logToFile); this.maxFileSizeMB = this.sgOutput.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("max-file-size-mb")).description("Maximum size per log file in MB. Creates new file when exceeded.")).defaultValue(Integer.valueOf(10))).min(1).sliderMax(100).visible(this.logToFile::get)).build()); Objects.requireNonNull(this.logToFile); this.maxTotalLogsMB = this.sgOutput.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("max-total-logs-mb")).description("Maximum total disk space for all packet logs in MB. Deletes oldest when exceeded.")).defaultValue(Integer.valueOf(50))).min(1).sliderMax(500).visible(this.logToFile::get)).build()); this.packetCounts = new Reference2IntOpenHashMap();
/* 140 */     this.runInMainMenu = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onActivate() {
/* 145 */     closeFileWriter();
/*     */     
/* 147 */     this.packetCounts.clear();
/* 148 */     this.lastFlushMs = System.currentTimeMillis();
/* 149 */     this.sessionStartTime = LocalDateTime.now();
/* 150 */     this.currentFileIndex = 0;
/* 151 */     this.currentFileSizeBytes = 0L;
/*     */     
/* 153 */     if (((Boolean)this.logToFile.get()).booleanValue()) {
/*     */       try {
/* 155 */         Files.createDirectories(PACKET_LOGS_DIR, (FileAttribute<?>[])new FileAttribute[0]);
/* 156 */         cleanupOldLogs();
/* 157 */         openNewLogFile();
/* 158 */       } catch (IOException e) {
/* 159 */         error("Failed to initialize packet logging: %s", new Object[] { e.getMessage() });
/* 160 */         this.fileWriter = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 167 */     if (((Boolean)this.showSummary.get()).booleanValue() && !this.packetCounts.isEmpty()) {
/* 168 */       logSummary();
/*     */     }
/* 170 */     closeFileWriter();
/*     */   }
/*     */   
/*     */   private void logPacket(String direction, class_2596<?> packet) {
/* 174 */     if (!((Boolean)this.logToChat.get()).booleanValue() && !((Boolean)this.logToFile.get()).booleanValue()) {
/*     */       return;
/*     */     }
/* 177 */     Class<? extends class_2596<?>> packetClass = packet.getClass();
/*     */ 
/*     */     
/* 180 */     this.packetCounts.addTo(packetClass, 1);
/*     */ 
/*     */     
/* 183 */     StringBuilder msg = new StringBuilder(128);
/* 184 */     if (((Boolean)this.showTimestamp.get()).booleanValue()) msg.append("[").append(LocalDateTime.now().format(TIME_FORMATTER)).append("] "); 
/* 185 */     msg.append(direction).append(" ").append(PacketUtils.getName(packetClass));
/* 186 */     if (((Boolean)this.showCount.get()).booleanValue()) msg.append(" (#").append(this.packetCounts.getInt(packetClass)).append(")"); 
/* 187 */     if (((Boolean)this.showPacketData.get()).booleanValue()) msg.append("\n  Data: ").append(packet);
/*     */ 
/*     */     
/* 190 */     String line = msg.toString();
/* 191 */     if (((Boolean)this.logToChat.get()).booleanValue()) info(line, new Object[0]); 
/* 192 */     if (((Boolean)this.logToFile.get()).booleanValue()) writeLine(line); 
/*     */   }
/*     */   
/*     */   private void logSummary() {
/* 196 */     int totalPackets = this.packetCounts.values().intStream().sum();
/*     */     
/* 198 */     List<String> lines = new ArrayList<>();
/* 199 */     lines.add("--- SUMMARY ---");
/* 200 */     lines.add("Final packet counts (total " + totalPackets + "):");
/*     */     
/* 202 */     this.packetCounts.reference2IntEntrySet().stream()
/* 203 */       .sorted((a, b) -> Integer.compare(b.getIntValue(), a.getIntValue()))
/* 204 */       .forEach(e -> lines.add("  %s: %d".formatted(new Object[] { PacketUtils.getName((Class)e.getKey()), Integer.valueOf(e.getIntValue()) })));
/*     */     
/* 206 */     for (String line : lines) {
/* 207 */       if (((Boolean)this.logToChat.get()).booleanValue()) info(line, new Object[0]); 
/* 208 */       if (((Boolean)this.logToFile.get()).booleanValue()) writeLine(line); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeLine(String line) {
/* 213 */     if (this.fileWriter == null)
/*     */       return; 
/*     */     try {
/* 216 */       int lineBytes = (line.getBytes(StandardCharsets.UTF_8)).length + LINE_SEPARATOR_BYTES;
/* 217 */       if (this.currentFileSizeBytes + lineBytes > ((Integer)this.maxFileSizeMB.get()).intValue() * 1024L * 1024L) openNewLogFile();
/*     */       
/* 219 */       this.fileWriter.write(line);
/* 220 */       this.fileWriter.newLine();
/* 221 */       this.currentFileSizeBytes += lineBytes;
/*     */       
/* 223 */       long now = System.currentTimeMillis();
/* 224 */       if (now - this.lastFlushMs >= ((Integer)this.flushInterval.get()).intValue() * 1000L) {
/* 225 */         this.fileWriter.flush();
/* 226 */         this.lastFlushMs = now;
/*     */       } 
/* 228 */     } catch (IOException e) {
/* 229 */       error("Failed to write to packet log file: %s. File logging disabled.", new Object[] { e.getMessage() });
/* 230 */       closeFileWriter();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void openNewLogFile() throws IOException {
/* 235 */     if (this.fileWriter != null) this.fileWriter.close(); 
/* 236 */     if (this.sessionStartTime == null) this.sessionStartTime = LocalDateTime.now();
/*     */     
/* 238 */     String fileName = "packets-%s-%d.log".formatted(new Object[] { this.sessionStartTime.format(FILE_NAME_FORMATTER), Integer.valueOf(this.currentFileIndex++) });
/* 239 */     this.fileWriter = Files.newBufferedWriter(PACKET_LOGS_DIR
/* 240 */         .resolve(fileName), StandardCharsets.UTF_8, new OpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.WRITE });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 245 */     this.currentFileSizeBytes = 0L;
/* 246 */     cleanupOldLogs();
/*     */   }
/*     */   
/*     */   private void closeFileWriter() {
/* 250 */     if (this.fileWriter != null) {
/*     */       try {
/* 252 */         this.fileWriter.flush();
/* 253 */         this.fileWriter.close();
/* 254 */       } catch (IOException iOException) {}
/*     */ 
/*     */       
/* 257 */       this.fileWriter = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanupOldLogs() throws IOException {
/* 266 */     long maxBytes = ((Integer)this.maxTotalLogsMB.get()).intValue() * 1024L * 1024L;
/* 267 */     List<LogFileEntry> logFiles = new ArrayList<>();
/* 268 */     Stream<Path> stream = Files.list(PACKET_LOGS_DIR); 
/* 269 */     try { for (Path p : stream.toList()) {
/* 270 */         String name = p.getFileName().toString();
/* 271 */         if (!name.startsWith("packets-") || !name.endsWith(".log"))
/*     */           continue;  try {
/* 273 */           logFiles.add(new LogFileEntry(p, Files.size(p), Files.getLastModifiedTime(p, new java.nio.file.LinkOption[0]).toMillis()));
/* 274 */         } catch (IOException iOException) {}
/*     */       } 
/*     */ 
/*     */       
/* 278 */       if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null)
/*     */         try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 280 */      logFiles.sort(Comparator.comparingLong(LogFileEntry::lastModified));
/* 281 */     long totalSize = 0L;
/* 282 */     for (LogFileEntry entry : logFiles) {
/* 283 */       totalSize += entry.size();
/* 284 */       if (totalSize > maxBytes) Files.deleteIfExists(entry.path()); 
/*     */     } 
/*     */   }
/*     */   private static final class LogFileEntry extends Record { private final Path path; private final long size; private final long lastModified;
/* 288 */     private LogFileEntry(Path path, long size, long lastModified) { this.path = path; this.size = size; this.lastModified = lastModified; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/systems/modules/misc/PacketLogger$LogFileEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #288	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 288 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/modules/misc/PacketLogger$LogFileEntry; } public Path path() { return this.path; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/systems/modules/misc/PacketLogger$LogFileEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #288	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/systems/modules/misc/PacketLogger$LogFileEntry; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/systems/modules/misc/PacketLogger$LogFileEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #288	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/systems/modules/misc/PacketLogger$LogFileEntry;
/* 288 */       //   0	8	1	o	Ljava/lang/Object; } public long size() { return this.size; } public long lastModified() { return this.lastModified; }
/*     */      }
/*     */   
/*     */   @EventHandler(priority = 201)
/*     */   private void onReceivePacket(PacketEvent.Receive event) {
/* 293 */     if (((Set)this.s2cPackets.get()).contains(event.packet.getClass())) logPacket("<- S2C", event.packet); 
/*     */   }
/*     */   
/*     */   @EventHandler(priority = 201)
/*     */   private void onSendPacket(PacketEvent.Send event) {
/* 298 */     if (((Set)this.c2sPackets.get()).contains(event.packet.getClass())) logPacket("-> C2S", event.packet); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\misc\PacketLogger.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */