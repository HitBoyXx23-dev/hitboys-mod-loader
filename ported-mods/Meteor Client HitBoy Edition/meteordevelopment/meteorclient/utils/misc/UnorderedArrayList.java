/*     */ package meteordevelopment.meteorclient.utils.misc;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Predicate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnorderedArrayList<T>
/*     */   extends AbstractList<T>
/*     */ {
/*  14 */   private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = new Object[0];
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_CAPACITY = 10;
/*     */ 
/*     */   
/*     */   private static final int MAX_ARRAY_SIZE = 2147483639;
/*     */ 
/*     */   
/*  23 */   private transient T[] items = (T[])DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
/*     */   
/*     */   private int size;
/*     */   
/*     */   public boolean add(T t) {
/*  28 */     if (this.size == this.items.length) grow(this.size + 1); 
/*  29 */     this.items[this.size++] = t;
/*  30 */     this.modCount++;
/*  31 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public T set(int index, T element) {
/*  36 */     T old = this.items[index];
/*  37 */     this.items[index] = element;
/*  38 */     return old;
/*     */   }
/*     */ 
/*     */   
/*     */   public T get(int index) {
/*  43 */     return this.items[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  48 */     this.modCount++;
/*  49 */     for (int i = 0; i < this.size; ) { this.items[i] = null; i++; }
/*  50 */      this.size = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int indexOf(Object o) {
/*  55 */     for (int i = 0; i < this.size; i++) {
/*  56 */       if (Objects.equals(this.items[i], o)) return i; 
/*     */     } 
/*  58 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int lastIndexOf(Object o) {
/*  63 */     T[] elements = this.items;
/*  64 */     for (int i = this.size - 1; i >= 0; i--) {
/*  65 */       if (Objects.equals(elements[i], o)) return i; 
/*     */     } 
/*  67 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/*  72 */     int i = indexOf(o);
/*  73 */     if (i == -1) return false;
/*     */     
/*  75 */     this.items[i] = null;
/*  76 */     this.items[i] = this.items[--this.size];
/*  77 */     this.modCount++;
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public T remove(int index) {
/*  83 */     T old = this.items[index];
/*  84 */     this.items[index] = null;
/*  85 */     this.items[index] = this.items[--this.size];
/*  86 */     this.modCount++;
/*  87 */     return old;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeIf(Predicate<? super T> filter) {
/*  92 */     int preSize = this.size;
/*  93 */     int j = 0;
/*     */     
/*  95 */     for (int i = 0; i < this.size; i++) {
/*  96 */       T item = this.items[i];
/*     */       
/*  98 */       if (!filter.test(item)) {
/*  99 */         if (j < i) this.items[j] = item;
/*     */         
/* 101 */         j++;
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     this.size = j;
/* 106 */     return (this.size != preSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 111 */     return this.size;
/*     */   }
/*     */   
/*     */   public void ensureCapacity(int minCapacity) {
/* 115 */     if (minCapacity > this.items.length && (this.items != DEFAULTCAPACITY_EMPTY_ELEMENTDATA || minCapacity > 10)) {
/*     */ 
/*     */       
/* 118 */       this.modCount++;
/* 119 */       grow(minCapacity);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void grow(int minCapacity) {
/* 124 */     this.items = Arrays.copyOf(this.items, newCapacity(minCapacity));
/*     */   }
/*     */ 
/*     */   
/*     */   private int newCapacity(int minCapacity) {
/* 129 */     int oldCapacity = this.items.length;
/* 130 */     int newCapacity = oldCapacity + (oldCapacity >> 1);
/* 131 */     if (newCapacity - minCapacity <= 0) {
/* 132 */       if (this.items == DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
/* 133 */         return Math.max(10, minCapacity); 
/* 134 */       if (minCapacity < 0)
/* 135 */         throw new OutOfMemoryError(); 
/* 136 */       return minCapacity;
/*     */     } 
/* 138 */     return (newCapacity - 2147483639 <= 0) ? 
/* 139 */       newCapacity : 
/* 140 */       hugeCapacity(minCapacity);
/*     */   }
/*     */   
/*     */   private static int hugeCapacity(int minCapacity) {
/* 144 */     if (minCapacity < 0)
/* 145 */       throw new OutOfMemoryError(); 
/* 146 */     return (minCapacity > 2147483639) ? Integer
/* 147 */       .MAX_VALUE : 
/* 148 */       2147483639;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\UnorderedArrayList.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */