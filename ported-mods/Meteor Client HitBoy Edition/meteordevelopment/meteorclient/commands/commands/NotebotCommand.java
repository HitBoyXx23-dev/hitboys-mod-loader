/*     */ package meteordevelopment.meteorclient.commands.commands;
/*     */ 
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.commands.Command;
/*     */ import meteordevelopment.meteorclient.commands.arguments.NotebotSongArgumentType;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.misc.Notebot;
/*     */ import meteordevelopment.meteorclient.utils.notebot.song.Note;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_156;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2766;
/*     */ import net.minecraft.class_2767;
/*     */ import net.minecraft.class_3414;
/*     */ 
/*     */ public class NotebotCommand extends Command {
/*     */   private static final DynamicCommandExceptionType INVALID_PATH;
/*  37 */   private static final SimpleCommandExceptionType INVALID_SONG = new SimpleCommandExceptionType((Message)class_2561.method_43470("Invalid song.")); static {
/*  38 */     INVALID_PATH = new DynamicCommandExceptionType(object -> class_2561.method_43470("'%s' is not a valid path.".formatted(new Object[] { object })));
/*     */   }
/*  40 */   int ticks = -1;
/*  41 */   private final Map<Integer, List<Note>> song = new HashMap<>();
/*     */   
/*     */   public NotebotCommand() {
/*  44 */     super("notebot", "Allows you load notebot files", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/*  49 */     builder.then(literal("help").executes(ctx -> {
/*     */             class_156.method_668().method_670("https://github.com/MeteorDevelopment/meteor-client/wiki/Notebot-Guide");
/*     */             
/*     */             return 1;
/*     */           }));
/*  54 */     builder.then(literal("status").executes(ctx -> {
/*     */             Notebot notebot = (Notebot)Modules.get().get(Notebot.class);
/*     */             
/*     */             info(notebot.getStatus(), new Object[0]);
/*     */             return 1;
/*     */           }));
/*  60 */     builder.then(literal("pause").executes(ctx -> {
/*     */             Notebot notebot = (Notebot)Modules.get().get(Notebot.class);
/*     */             
/*     */             notebot.pause();
/*     */             return 1;
/*     */           }));
/*  66 */     builder.then(literal("resume").executes(ctx -> {
/*     */             Notebot notebot = (Notebot)Modules.get().get(Notebot.class);
/*     */             
/*     */             notebot.pause();
/*     */             return 1;
/*     */           }));
/*  72 */     builder.then(literal("stop").executes(ctx -> {
/*     */             Notebot notebot = (Notebot)Modules.get().get(Notebot.class);
/*     */             
/*     */             notebot.stop();
/*     */             return 1;
/*     */           }));
/*  78 */     builder.then(literal("randomsong").executes(ctx -> {
/*     */             Notebot notebot = (Notebot)Modules.get().get(Notebot.class);
/*     */             
/*     */             notebot.playRandomSong();
/*     */             return 1;
/*     */           }));
/*  84 */     builder.then(
/*  85 */         literal("play").then(
/*  86 */           argument("song", (ArgumentType)NotebotSongArgumentType.create()).executes(ctx -> {
/*     */               Notebot notebot = (Notebot)Modules.get().get(Notebot.class);
/*     */               
/*     */               Path songPath = (Path)ctx.getArgument("song", Path.class);
/*     */               
/*     */               if (songPath == null || !songPath.toFile().exists()) {
/*     */                 throw INVALID_SONG.create();
/*     */               }
/*     */               
/*     */               notebot.loadSong(songPath.toFile());
/*     */               return 1;
/*     */             })));
/*  98 */     builder.then(
/*  99 */         literal("preview").then(
/* 100 */           argument("song", (ArgumentType)NotebotSongArgumentType.create()).executes(ctx -> {
/*     */               Notebot notebot = (Notebot)Modules.get().get(Notebot.class);
/*     */               
/*     */               Path songPath = (Path)ctx.getArgument("song", Path.class);
/*     */               if (songPath == null || !songPath.toFile().exists()) {
/*     */                 throw INVALID_SONG.create();
/*     */               }
/*     */               notebot.previewSong(songPath.toFile());
/*     */               return 1;
/*     */             })));
/* 110 */     builder.then(literal("record").then(literal("start").executes(ctx -> {
/*     */               this.ticks = -1;
/*     */               
/*     */               this.song.clear();
/*     */               MeteorClient.EVENT_BUS.subscribe(this);
/*     */               info("Recording started", new Object[0]);
/*     */               return 1;
/*     */             })));
/* 118 */     builder.then(literal("record").then(literal("cancel").executes(ctx -> {
/*     */               MeteorClient.EVENT_BUS.unsubscribe(this);
/*     */               
/*     */               info("Recording cancelled", new Object[0]);
/*     */               return 1;
/*     */             })));
/* 124 */     builder.then(literal("record").then(literal("save").then(argument("name", (ArgumentType)StringArgumentType.greedyString()).executes(ctx -> {
/*     */                 String name = (String)ctx.getArgument("name", String.class);
/*     */                 if (name == null || name.isEmpty()) {
/*     */                   throw INVALID_PATH.create(name);
/*     */                 }
/*     */                 Path notebotFolder = MeteorClient.FOLDER.toPath().resolve("notebot");
/*     */                 Path path = notebotFolder.resolve(String.format("%s.txt", new Object[] { name })).normalize();
/*     */                 if (!path.startsWith(notebotFolder)) {
/*     */                   throw INVALID_PATH.create(path);
/*     */                 }
/*     */                 saveRecording(path);
/*     */                 return 1;
/*     */               }))));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/* 141 */     if (this.ticks == -1)
/* 142 */       return;  this.ticks++;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onReadPacket(PacketEvent.Receive event) {
/* 147 */     class_2596 class_2596 = event.packet; if (class_2596 instanceof class_2767) { class_2767 sound = (class_2767)class_2596; if (((class_3414)sound.method_11894().comp_349()).comp_3319().method_12832().contains("note_block")) {
/* 148 */         if (this.ticks == -1) this.ticks = 0; 
/* 149 */         List<Note> notes = this.song.computeIfAbsent(Integer.valueOf(this.ticks), tick -> new ArrayList());
/* 150 */         Note note = getNote(sound);
/* 151 */         if (note != null)
/* 152 */           notes.add(note); 
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   private void saveRecording(Path path) {
/* 158 */     if (this.song.isEmpty()) {
/* 159 */       MeteorClient.EVENT_BUS.unsubscribe(this);
/*     */       return;
/*     */     } 
/*     */     try {
/* 163 */       MeteorClient.EVENT_BUS.unsubscribe(this);
/*     */       
/* 165 */       FileWriter file = new FileWriter(path.toFile());
/* 166 */       for (Map.Entry<Integer, List<Note>> entry : this.song.entrySet()) {
/* 167 */         int tick = ((Integer)entry.getKey()).intValue();
/* 168 */         List<Note> notes = entry.getValue();
/*     */         
/* 170 */         for (Note note : notes) {
/* 171 */           class_2766 instrument = note.getInstrument();
/* 172 */           int noteLevel = note.getNoteLevel();
/*     */           
/* 174 */           file.write(String.format("%d:%d:%d\n", new Object[] { Integer.valueOf(tick), Integer.valueOf(noteLevel), Integer.valueOf(instrument.ordinal()) }));
/*     */         } 
/*     */       } 
/*     */       
/* 178 */       file.close();
/* 179 */       info("Song saved.", new Object[0]);
/* 180 */     } catch (IOException e) {
/* 181 */       info("Couldn't create the file.", new Object[0]);
/* 182 */       MeteorClient.EVENT_BUS.unsubscribe(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Note getNote(class_2767 soundPacket) {
/* 188 */     float pitch = soundPacket.method_11892();
/*     */ 
/*     */     
/* 191 */     int noteLevel = -1;
/* 192 */     for (int n = 0; n < 25; n++) {
/* 193 */       if ((float)Math.pow(2.0D, (n - 12) / 12.0D) - 0.01D < pitch && 
/* 194 */         (float)Math.pow(2.0D, (n - 12) / 12.0D) + 0.01D > pitch) {
/* 195 */         noteLevel = n;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 200 */     if (noteLevel == -1) {
/* 201 */       error("Error while bruteforcing a note level! Sound: " + String.valueOf(soundPacket.method_11894().comp_349()) + " Pitch: " + pitch, new Object[0]);
/* 202 */       return null;
/*     */     } 
/*     */     
/* 205 */     class_2766 instrument = getInstrumentFromSound((class_3414)soundPacket.method_11894().comp_349());
/* 206 */     if (instrument == null) {
/* 207 */       error("Can't find the instrument from sound! Sound: " + String.valueOf(soundPacket.method_11894().comp_349()), new Object[0]);
/* 208 */       return null;
/*     */     } 
/*     */     
/* 211 */     return new Note(instrument, noteLevel);
/*     */   }
/*     */   
/*     */   private class_2766 getInstrumentFromSound(class_3414 sound) {
/* 215 */     String path = sound.comp_3319().method_12832();
/* 216 */     if (path.contains("harp"))
/* 217 */       return class_2766.field_12648; 
/* 218 */     if (path.contains("basedrum"))
/* 219 */       return class_2766.field_12653; 
/* 220 */     if (path.contains("snare"))
/* 221 */       return class_2766.field_12643; 
/* 222 */     if (path.contains("hat"))
/* 223 */       return class_2766.field_12645; 
/* 224 */     if (path.contains("bass"))
/* 225 */       return class_2766.field_12651; 
/* 226 */     if (path.contains("flute"))
/* 227 */       return class_2766.field_12650; 
/* 228 */     if (path.contains("bell"))
/* 229 */       return class_2766.field_12644; 
/* 230 */     if (path.contains("guitar"))
/* 231 */       return class_2766.field_12654; 
/* 232 */     if (path.contains("chime"))
/* 233 */       return class_2766.field_12647; 
/* 234 */     if (path.contains("xylophone"))
/* 235 */       return class_2766.field_12655; 
/* 236 */     if (path.contains("iron_xylophone"))
/* 237 */       return class_2766.field_18284; 
/* 238 */     if (path.contains("cow_bell"))
/* 239 */       return class_2766.field_18285; 
/* 240 */     if (path.contains("didgeridoo"))
/* 241 */       return class_2766.field_18286; 
/* 242 */     if (path.contains("bit"))
/* 243 */       return class_2766.field_18287; 
/* 244 */     if (path.contains("banjo"))
/* 245 */       return class_2766.field_18288; 
/* 246 */     if (path.contains("pling"))
/* 247 */       return class_2766.field_18289; 
/* 248 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\NotebotCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */