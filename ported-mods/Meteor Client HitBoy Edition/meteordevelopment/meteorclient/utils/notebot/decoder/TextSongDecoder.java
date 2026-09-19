/*    */ package meteordevelopment.meteorclient.utils.notebot.decoder;
/*    */ 
/*    */ import com.google.common.collect.ListMultimap;
/*    */ import com.google.common.collect.Multimap;
/*    */ import com.google.common.collect.MultimapBuilder;
/*    */ import java.io.File;
/*    */ import java.nio.file.Files;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.utils.notebot.song.Note;
/*    */ import meteordevelopment.meteorclient.utils.notebot.song.Song;
/*    */ import net.minecraft.class_2766;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextSongDecoder
/*    */   extends SongDecoder
/*    */ {
/*    */   public Song parse(File file) throws Exception {
/* 23 */     List<String> data = Files.readAllLines(file.toPath());
/*    */     
/* 25 */     ListMultimap listMultimap = MultimapBuilder.linkedHashKeys().arrayListValues().build();
/* 26 */     String title = FilenameUtils.getBaseName(file.getName());
/* 27 */     String author = "Unknown";
/*    */     
/* 29 */     for (int lineNumber = 0; lineNumber < data.size(); lineNumber++) {
/* 30 */       String line = data.get(lineNumber);
/* 31 */       if (line.startsWith("// Name: ")) {
/* 32 */         title = line.substring(9);
/*    */       
/*    */       }
/* 35 */       else if (line.startsWith("// Author: ")) {
/* 36 */         author = line.substring(11);
/*    */       
/*    */       }
/* 39 */       else if (!line.isEmpty()) {
/*    */         
/* 41 */         String[] parts = ((String)data.get(lineNumber)).split(":");
/* 42 */         if (parts.length < 2)
/* 43 */         { this.notebot.warning("Malformed line %d", new Object[] { Integer.valueOf(lineNumber) });
/*    */            }
/*    */         
/*    */         else
/*    */         
/* 48 */         { int key, val, type = 0;
/*    */           try {
/* 50 */             key = Integer.parseInt(parts[0]);
/* 51 */             val = Integer.parseInt(parts[1]);
/* 52 */             if (parts.length > 2) {
/* 53 */               type = Integer.parseInt(parts[2]);
/*    */             }
/* 55 */           } catch (NumberFormatException e) {
/* 56 */             this.notebot.warning("Invalid character at line %d", new Object[] { Integer.valueOf(lineNumber) });
/*    */           } 
/*    */ 
/*    */           
/* 60 */           Note note = new Note(class_2766.values()[type], val);
/* 61 */           listMultimap.put(Integer.valueOf(key), note); } 
/*    */       } 
/* 63 */     }  return new Song((Multimap)listMultimap, title, author);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\notebot\decoder\TextSongDecoder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */