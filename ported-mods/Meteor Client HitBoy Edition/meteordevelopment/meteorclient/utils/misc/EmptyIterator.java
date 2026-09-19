/*    */ package meteordevelopment.meteorclient.utils.misc;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EmptyIterator<T>
/*    */   implements Iterator<T>
/*    */ {
/*    */   public boolean hasNext() {
/* 13 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public T next() {
/* 18 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\EmptyIterator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */