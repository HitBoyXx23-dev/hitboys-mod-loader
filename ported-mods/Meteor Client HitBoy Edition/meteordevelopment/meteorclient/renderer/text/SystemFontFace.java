/*    */ package meteordevelopment.meteorclient.renderer.text;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.FileChannel;
/*    */ import java.nio.channels.ReadableByteChannel;
/*    */ import java.nio.file.OpenOption;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.StandardOpenOption;
/*    */ import org.jspecify.annotations.NullMarked;
/*    */ 
/*    */ @NullMarked
/*    */ public final class SystemFontFace extends FontFace {
/*    */   private final Path path;
/*    */   
/*    */   public SystemFontFace(FontInfo info, Path path) {
/* 16 */     super(info);
/*    */     
/* 18 */     this.path = path;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReadableByteChannel byteChannelForRead() throws IOException {
/* 23 */     return FileChannel.open(this.path, new OpenOption[] { StandardOpenOption.READ });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 28 */     return super.toString() + " (" + super.toString() + ")";
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\text\SystemFontFace.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */