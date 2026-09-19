/*    */ package meteordevelopment.meteorclient.utils.notebot.decoder;
/*    */ 
/*    */ import java.io.File;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.Notebot;
/*    */ import meteordevelopment.meteorclient.utils.notebot.song.Song;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SongDecoder
/*    */ {
/* 15 */   protected Notebot notebot = (Notebot)Modules.get().get(Notebot.class);
/*    */   
/*    */   public abstract Song parse(File paramFile) throws Exception;
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\notebot\decoder\SongDecoder.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */