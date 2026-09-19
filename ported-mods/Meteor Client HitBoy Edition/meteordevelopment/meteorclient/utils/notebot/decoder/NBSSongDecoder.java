/*     */ package meteordevelopment.meteorclient.utils.notebot.decoder;
/*     */ 
/*     */ import com.google.common.collect.ListMultimap;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.collect.MultimapBuilder;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import meteordevelopment.meteorclient.utils.notebot.song.Note;
/*     */ import meteordevelopment.meteorclient.utils.notebot.song.Song;
/*     */ import net.minecraft.class_2766;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NBSSongDecoder
/*     */   extends SongDecoder
/*     */ {
/*     */   public static final int NOTE_OFFSET = 33;
/*     */   
/*     */   @NotNull
/*     */   public Song parse(File songFile) throws Exception {
/*  36 */     return parse(new FileInputStream(songFile));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private Song parse(InputStream inputStream) throws Exception {
/*  47 */     ListMultimap listMultimap = MultimapBuilder.linkedHashKeys().arrayListValues().build();
/*     */     
/*  49 */     DataInputStream dataInputStream = new DataInputStream(inputStream);
/*  50 */     short length = readShort(dataInputStream);
/*  51 */     int nbsversion = 0;
/*  52 */     if (length == 0) {
/*  53 */       nbsversion = dataInputStream.readByte();
/*  54 */       dataInputStream.readByte();
/*  55 */       if (nbsversion >= 3) {
/*  56 */         length = readShort(dataInputStream);
/*     */       }
/*     */     } 
/*  59 */     readShort(dataInputStream);
/*  60 */     String title = readString(dataInputStream);
/*  61 */     String author = readString(dataInputStream);
/*  62 */     readString(dataInputStream);
/*  63 */     readString(dataInputStream);
/*  64 */     float speed = readShort(dataInputStream) / 100.0F;
/*  65 */     dataInputStream.readBoolean();
/*  66 */     dataInputStream.readByte();
/*  67 */     dataInputStream.readByte();
/*  68 */     readInt(dataInputStream);
/*  69 */     readInt(dataInputStream);
/*  70 */     readInt(dataInputStream);
/*  71 */     readInt(dataInputStream);
/*  72 */     readInt(dataInputStream);
/*  73 */     readString(dataInputStream);
/*  74 */     if (nbsversion >= 4) {
/*  75 */       dataInputStream.readByte();
/*  76 */       dataInputStream.readByte();
/*  77 */       readShort(dataInputStream);
/*     */     } 
/*     */     
/*  80 */     double tick = -1.0D;
/*     */     label29: while (true) {
/*  82 */       short jumpTicks = readShort(dataInputStream);
/*  83 */       if (jumpTicks == 0) {
/*     */         break;
/*     */       }
/*  86 */       tick += (jumpTicks * 20.0F / speed);
/*     */       while (true) {
/*  88 */         short jumpLayers = readShort(dataInputStream);
/*  89 */         if (jumpLayers == 0) {
/*     */           continue label29;
/*     */         }
/*  92 */         byte instrument = dataInputStream.readByte();
/*     */         
/*  94 */         byte key = dataInputStream.readByte();
/*  95 */         if (nbsversion >= 4) {
/*  96 */           dataInputStream.readUnsignedByte();
/*  97 */           dataInputStream.readUnsignedByte();
/*  98 */           readShort(dataInputStream);
/*     */         } 
/*     */         
/* 101 */         class_2766 inst = fromNBSInstrument(instrument);
/*     */ 
/*     */         
/* 104 */         if (inst == null)
/*     */           continue; 
/* 106 */         Note note = new Note(inst, key - 33);
/* 107 */         setNote((int)Math.round(tick), note, (Multimap<Integer, Note>)listMultimap);
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     return new Song((Multimap)listMultimap, title, author);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setNote(int ticks, Note note, Multimap<Integer, Note> notesMap) {
/* 118 */     notesMap.put(Integer.valueOf(ticks), note);
/*     */   }
/*     */   
/*     */   private static short readShort(DataInputStream dataInputStream) throws IOException {
/* 122 */     int byte1 = dataInputStream.readUnsignedByte();
/* 123 */     int byte2 = dataInputStream.readUnsignedByte();
/* 124 */     return (short)(byte1 + (byte2 << 8));
/*     */   }
/*     */   
/*     */   private static int readInt(DataInputStream dataInputStream) throws IOException {
/* 128 */     int byte1 = dataInputStream.readUnsignedByte();
/* 129 */     int byte2 = dataInputStream.readUnsignedByte();
/* 130 */     int byte3 = dataInputStream.readUnsignedByte();
/* 131 */     int byte4 = dataInputStream.readUnsignedByte();
/* 132 */     return byte1 + (byte2 << 8) + (byte3 << 16) + (byte4 << 24);
/*     */   }
/*     */   
/*     */   private static String readString(DataInputStream dataInputStream) throws IOException {
/* 136 */     int length = readInt(dataInputStream);
/* 137 */     if (length < 0) {
/* 138 */       throw new EOFException("Length can't be negative! Length: " + length);
/*     */     }
/* 140 */     if (length > dataInputStream.available()) {
/* 141 */       throw new EOFException("Can't read string that is larger than a buffer! Length: " + length + " Readable Bytes Length: " + dataInputStream.available());
/*     */     }
/*     */     
/* 144 */     StringBuilder builder = new StringBuilder(length);
/* 145 */     for (; length > 0; length--) {
/* 146 */       char c = (char)dataInputStream.readByte();
/* 147 */       if (c == '\r') {
/* 148 */         c = ' ';
/*     */       }
/* 150 */       builder.append(c);
/*     */     } 
/* 152 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class_2766 fromNBSInstrument(int instrument) {
/* 157 */     switch (instrument) { case 0: case 1: case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10: case 11: case 12: case 13: case 14: case 15:  }  return 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 174 */       null;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\notebot\decoder\NBSSongDecoder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */