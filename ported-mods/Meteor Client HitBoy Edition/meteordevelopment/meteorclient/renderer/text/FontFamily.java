/*    */ package meteordevelopment.meteorclient.renderer.text;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FontFamily
/*    */ {
/*    */   private final String name;
/* 13 */   private final List<FontFace> fonts = new ArrayList<>();
/*    */   
/*    */   public FontFamily(String name) {
/* 16 */     this.name = name;
/*    */   }
/*    */   
/*    */   public boolean addFont(FontFace font) {
/* 20 */     return this.fonts.add(font);
/*    */   }
/*    */   
/*    */   public boolean hasType(FontInfo.Type type) {
/* 24 */     return (get(type) != null);
/*    */   }
/*    */   
/*    */   public FontFace get(FontInfo.Type type) {
/* 28 */     if (type == null) return null;
/*    */     
/* 30 */     for (FontFace font : this.fonts) {
/* 31 */       if (font.info.type().equals(type)) {
/* 32 */         return font;
/*    */       }
/*    */     } 
/*    */     
/* 36 */     return null;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 40 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\text\FontFamily.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */