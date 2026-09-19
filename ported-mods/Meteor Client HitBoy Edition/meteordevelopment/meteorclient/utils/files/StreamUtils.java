/*    */ package meteordevelopment.meteorclient.utils.files;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StreamUtils
/*    */ {
/*    */   public static void copy(File from, File to) {
/*    */     
/* 18 */     try { InputStream in = new FileInputStream(from); 
/* 19 */       try { OutputStream out = new FileOutputStream(to); 
/* 20 */         try { in.transferTo(out);
/* 21 */           out.close(); } catch (Throwable throwable) { try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  in.close(); } catch (Throwable throwable) { try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 22 */     { MeteorClient.LOG.error("Error copying from file '{}' to file '{}'.", new Object[] { from.getName(), to.getName(), e }); }
/*    */   
/*    */   }
/*    */   public static void copy(InputStream in, File to) {
/*    */     
/* 27 */     try { OutputStream out = new FileOutputStream(to); 
/* 28 */       try { in.transferTo(out);
/* 29 */         out.close(); } catch (Throwable throwable) { try { out.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException e)
/* 30 */     { MeteorClient.LOG.error("Error writing to file '{}'.", to.getName()); }
/*    */     finally
/* 32 */     { IOUtils.closeQuietly(in); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\files\StreamUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */