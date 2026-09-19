/*    */ package meteordevelopment.meteorclient.renderer.text;
/*    */ 
/*    */ import java.io.InputStream;
/*    */ import java.nio.channels.Channels;
/*    */ import java.nio.channels.ReadableByteChannel;
/*    */ import meteordevelopment.meteorclient.utils.render.FontUtils;
/*    */ import org.jspecify.annotations.NullMarked;
/*    */ 
/*    */ @NullMarked
/*    */ public class BuiltinFontFace
/*    */   extends FontFace {
/*    */   private final String name;
/*    */   
/*    */   public BuiltinFontFace(FontInfo info, String name) {
/* 15 */     super(info);
/*    */     
/* 17 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReadableByteChannel byteChannelForRead() {
/* 22 */     InputStream inputStream = FontUtils.builtinFontStream(this.name);
/* 23 */     if (inputStream == null) {
/* 24 */       throw new IllegalArgumentException("Builtin font '" + this.name + "' not found");
/*    */     }
/* 26 */     return Channels.newChannel(inputStream);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return super.toString() + " (builtin)";
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\text\BuiltinFontFace.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */