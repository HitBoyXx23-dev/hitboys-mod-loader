/*    */ package meteordevelopment.meteorclient.utils.notebot.decoder;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.nio.file.Path;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.Notebot;
/*    */ import meteordevelopment.meteorclient.utils.notebot.NotebotUtils;
/*    */ import meteordevelopment.meteorclient.utils.notebot.song.Note;
/*    */ import meteordevelopment.meteorclient.utils.notebot.song.Song;
/*    */ import net.minecraft.class_2766;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SongDecoders
/*    */ {
/* 23 */   private static final Map<String, SongDecoder> decoders = new HashMap<>();
/*    */   
/*    */   static {
/* 26 */     registerDecoder("nbs", new NBSSongDecoder());
/* 27 */     registerDecoder("txt", new TextSongDecoder());
/*    */   }
/*    */ 
/*    */   
/*    */   public static void registerDecoder(String extension, SongDecoder songDecoder) {
/* 32 */     decoders.put(extension, songDecoder);
/*    */   }
/*    */   
/*    */   public static SongDecoder getDecoder(File file) {
/* 36 */     return decoders.get(FilenameUtils.getExtension(file.getName()));
/*    */   }
/*    */   
/*    */   public static boolean hasDecoder(File file) {
/* 40 */     return decoders.containsKey(FilenameUtils.getExtension(file.getName()));
/*    */   }
/*    */   
/*    */   public static boolean hasDecoder(Path path) {
/* 44 */     return hasDecoder(path.toFile());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public static Song parse(File file) throws Exception {
/* 55 */     if (!hasDecoder(file)) throw new IllegalStateException("Decoder for this file does not exists!"); 
/* 56 */     SongDecoder decoder = getDecoder(file);
/* 57 */     Song song = decoder.parse(file);
/*    */     
/* 59 */     fixSong(song);
/*    */     
/* 61 */     song.finishLoading();
/*    */     
/* 63 */     return song;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void fixSong(Song song) {
/* 72 */     Notebot notebot = (Notebot)Modules.get().get(Notebot.class);
/*    */     
/* 74 */     Iterator<Map.Entry<Integer, Note>> iterator = song.getNotesMap().entries().iterator();
/* 75 */     while (iterator.hasNext()) {
/* 76 */       Map.Entry<Integer, Note> entry = iterator.next();
/* 77 */       int tick = ((Integer)entry.getKey()).intValue();
/* 78 */       Note note = entry.getValue();
/*    */       
/* 80 */       int n = note.getNoteLevel();
/* 81 */       if (n < 0 || n > 24) {
/* 82 */         if (((Boolean)notebot.roundOutOfRange.get()).booleanValue()) {
/* 83 */           note.setNoteLevel((n < 0) ? 0 : 24);
/*    */         } else {
/* 85 */           notebot.warning("Note at tick %d out of range.", new Object[] { Integer.valueOf(tick) });
/* 86 */           iterator.remove();
/*    */           
/*    */           continue;
/*    */         } 
/*    */       }
/* 91 */       if (notebot.mode.get() == NotebotUtils.NotebotMode.ExactInstruments) {
/* 92 */         class_2766 newInstrument = notebot.getMappedInstrument(note.getInstrument());
/* 93 */         if (newInstrument != null)
/* 94 */           note.setInstrument(newInstrument); 
/*    */         continue;
/*    */       } 
/* 97 */       note.setInstrument(null);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\notebot\decoder\SongDecoders.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */