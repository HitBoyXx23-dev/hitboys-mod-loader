/*    */ package meteordevelopment.meteorclient.utils.misc;
/*    */ 
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Collection;
/*    */ import java.util.Objects;
/*    */ import java.util.Queue;
/*    */ import java.util.function.Supplier;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Pool<T>
/*    */ {
/* 14 */   private final Queue<T> items = new ArrayDeque<>();
/*    */   private final Supplier<T> producer;
/*    */   
/*    */   public Pool(Supplier<T> producer) {
/* 18 */     this.producer = producer;
/*    */   }
/*    */   
/*    */   public synchronized T get() {
/* 22 */     if (!this.items.isEmpty()) return this.items.poll(); 
/* 23 */     return this.producer.get();
/*    */   }
/*    */   
/*    */   public synchronized void free(T obj) {
/* 27 */     this.items.offer(obj);
/*    */   }
/*    */   
/*    */   public synchronized void freeAll(Iterable<T> objects) {
/* 31 */     if (objects instanceof Collection) { Collection<T> collection = (Collection<T>)objects;
/* 32 */       this.items.addAll(collection); }
/*    */     else
/* 34 */     { Objects.requireNonNull(this.items); objects.forEach(this.items::add); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\Pool.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */