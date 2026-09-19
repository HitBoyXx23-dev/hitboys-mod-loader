/*     */ package meteordevelopment.meteorclient.utils.misc;
/*     */ 
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteCountDataOutput
/*     */   implements DataOutput
/*     */ {
/*  14 */   public static final ByteCountDataOutput INSTANCE = new ByteCountDataOutput();
/*     */   
/*     */   private int count;
/*     */   
/*     */   public int getCount() {
/*  19 */     return this.count;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  23 */     this.count = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(int b) throws IOException {
/*  28 */     this.count++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  33 */     this.count += b.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  38 */     this.count += len;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBoolean(boolean v) {
/*  43 */     this.count++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeByte(int v) {
/*  48 */     this.count++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeShort(int v) {
/*  53 */     this.count += 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChar(int v) {
/*  58 */     this.count += 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeInt(int v) {
/*  63 */     this.count += 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeLong(long v) {
/*  68 */     this.count += 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFloat(float v) {
/*  73 */     this.count += 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDouble(double v) {
/*  78 */     this.count += 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeBytes(String s) {
/*  83 */     this.count += s.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeChars(String s) {
/*  88 */     this.count += s.length() * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeUTF(@NotNull String s) {
/*  93 */     this.count = (int)(this.count + 2L + getUTFLength(s));
/*     */   }
/*     */   
/*     */   long getUTFLength(String s) {
/*  97 */     long utflen = 0L;
/*  98 */     for (int cpos = 0; cpos < s.length(); cpos++) {
/*  99 */       char c = s.charAt(cpos);
/* 100 */       if (c >= '\001' && c <= '') {
/* 101 */         utflen++;
/* 102 */       } else if (c > '߿') {
/* 103 */         utflen += 3L;
/*     */       } else {
/* 105 */         utflen += 2L;
/*     */       } 
/*     */     } 
/* 108 */     return utflen;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\ByteCountDataOutput.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */