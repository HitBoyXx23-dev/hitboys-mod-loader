/*    */ package meteordevelopment.meteorclient.gui.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface CharFilter
/*    */ {
/*    */   boolean filter(String paramString, char paramChar);
/*    */   
/*    */   default boolean filter(String text, int i) {
/* 12 */     return filter(text, (char)i);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gu\\utils\CharFilter.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */