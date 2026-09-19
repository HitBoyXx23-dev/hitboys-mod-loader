/*    */ package meteordevelopment.meteorclient.renderer.text;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.ReadableByteChannel;
/*    */ import meteordevelopment.meteorclient.utils.files.ByteBufferUtils;
/*    */ import org.jspecify.annotations.NullMarked;
/*    */ import org.lwjgl.BufferUtils;
/*    */ 
/*    */ @NullMarked
/*    */ public abstract class FontFace
/*    */ {
/*    */   public final FontInfo info;
/*    */   
/*    */   protected FontFace(FontInfo info) {
/* 16 */     this.info = info;
/*    */   }
/*    */   
/*    */   public abstract ReadableByteChannel byteChannelForRead() throws IOException;
/*    */   
/*    */   public final ByteBuffer readToDirectByteBuffer() throws IOException {
/* 22 */     ReadableByteChannel channel = byteChannelForRead(); 
/* 23 */     try { ByteBuffer byteBuffer = ByteBufferUtils.readFully(channel, BufferUtils::createByteBuffer);
/* 24 */       if (channel != null) channel.close();  return byteBuffer; }
/*    */     catch (Throwable throwable) { if (channel != null)
/*    */         try { channel.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 29 */      } public String toString() { return this.info.toString(); }
/*    */ 
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\text\FontFace.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */