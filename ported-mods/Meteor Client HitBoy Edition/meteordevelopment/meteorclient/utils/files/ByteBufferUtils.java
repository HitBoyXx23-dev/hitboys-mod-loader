/*    */ package meteordevelopment.meteorclient.utils.files;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.FileChannel;
/*    */ import java.nio.channels.ReadableByteChannel;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.OpenOption;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.StandardOpenOption;
/*    */ import java.util.function.IntFunction;
/*    */ import org.jspecify.annotations.NullMarked;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @NullMarked
/*    */ public final class ByteBufferUtils
/*    */ {
/*    */   public static ByteBuffer readFully(Path path, IntFunction<ByteBuffer> allocator) throws IOException {
/* 26 */     FileChannel channel = FileChannel.open(path, new OpenOption[] { StandardOpenOption.READ }); 
/* 27 */     try { long size = Files.size(path);
/* 28 */       if (size > 2147483647L) {
/* 29 */         throw new IOException("File too large to read into ByteBuffer: " + String.valueOf(path));
/*    */       }
/* 31 */       ByteBuffer buffer = allocator.apply((int)size);
/* 32 */       while (buffer.hasRemaining()) {
/* 33 */         int bytesRead = channel.read(buffer);
/* 34 */         if (bytesRead == -1)
/*    */           break; 
/* 36 */       }  buffer.flip();
/* 37 */       ByteBuffer byteBuffer1 = buffer;
/* 38 */       if (channel != null) channel.close();  return byteBuffer1; } catch (Throwable throwable) { if (channel != null)
/*    */         try { channel.close(); }
/*    */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*    */           throw throwable; }
/* 42 */      } public static ByteBuffer readFully(ReadableByteChannel channel, IntFunction<ByteBuffer> allocator) throws IOException { ByteBuffer buffer = requireCapacity(allocator.apply(8192), 8192);
/*    */     
/*    */     while (true) {
/* 45 */       int bytesRead = channel.read(buffer);
/*    */       
/* 47 */       if (bytesRead == -1)
/*    */         break; 
/* 49 */       if (bytesRead == 0) {
/*    */ 
/*    */         
/* 52 */         if (!buffer.hasRemaining()) {
/* 53 */           buffer = grow(buffer, allocator);
/*    */           
/*    */           continue;
/*    */         } 
/*    */         
/*    */         break;
/*    */       } 
/*    */       
/* 61 */       if (!buffer.hasRemaining()) {
/* 62 */         buffer = grow(buffer, allocator);
/*    */       }
/*    */     } 
/*    */     
/* 66 */     buffer.flip();
/* 67 */     return buffer; }
/*    */ 
/*    */   
/*    */   private static ByteBuffer grow(ByteBuffer buffer, IntFunction<ByteBuffer> allocator) {
/* 71 */     int oldCap = buffer.capacity();
/* 72 */     int newCap = oldCap << 1;
/* 73 */     if (newCap <= 0) throw new OutOfMemoryError("Buffer too large (overflow): " + oldCap);
/*    */     
/* 75 */     ByteBuffer newBuffer = requireCapacity(allocator.apply(newCap), newCap);
/* 76 */     buffer.flip();
/* 77 */     newBuffer.put(buffer);
/* 78 */     return newBuffer;
/*    */   }
/*    */   
/*    */   private static ByteBuffer requireCapacity(ByteBuffer buf, int minCap) {
/* 82 */     if (buf.capacity() < minCap) {
/* 83 */       throw new IllegalArgumentException("Allocator returned capacity " + buf.capacity() + " < " + minCap);
/*    */     }
/* 85 */     return buf;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\files\ByteBufferUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */