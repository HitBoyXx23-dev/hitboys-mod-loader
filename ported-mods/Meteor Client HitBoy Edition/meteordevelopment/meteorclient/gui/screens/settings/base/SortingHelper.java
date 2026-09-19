/*     */ package meteordevelopment.meteorclient.gui.screens.settings.base;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_2359;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SortingHelper
/*     */ {
/*  23 */   private static final Comparator<Entry<?>> FILTER_COMPARATOR = Comparator.comparingInt(Entry::distance);
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Iterable<T> sort(Iterable<T> registry, Predicate<T> filter, Function<T, String[]> nameFunction, String filterText) {
/*  28 */     return sortInternal(registry, filter, nameFunction, filterText, null);
/*     */   }
/*     */   
/*     */   public static <T> Iterable<T> sortWithPriority(Iterable<T> registry, Predicate<T> filter, Function<T, String[]> nameFunction, String filterText, Comparator<T> comparator) {
/*  32 */     return sortInternal(registry, filter, nameFunction, filterText, comparator);
/*     */   }
/*     */   
/*     */   private static <T> Iterable<T> sortInternal(Iterable<T> registry, Predicate<T> filter, Function<T, String[]> nameFunction, String filterText, @Nullable Comparator<T> comparator) {
/*  36 */     if (filterText.isBlank()) {
/*  37 */       if (comparator == null) {
/*  38 */         return filtering(registry, filter);
/*     */       }
/*  40 */       List<T> list1 = createList(registry);
/*     */       
/*  42 */       for (T value : registry) {
/*  43 */         if (filter.test(value)) {
/*  44 */           list1.add(value);
/*     */         }
/*     */       } 
/*     */       
/*  48 */       list1.sort(comparator);
/*     */       
/*  50 */       return list1;
/*     */     } 
/*     */     
/*  53 */     List<Entry<T>> list = createList(registry);
/*     */     
/*  55 */     for (T value : registry) {
/*  56 */       if (!filter.test(value)) {
/*     */         continue;
/*     */       }
/*     */       
/*  60 */       String[] names = nameFunction.apply(value);
/*  61 */       int bestWords = 0;
/*  62 */       int bestDistance = Integer.MAX_VALUE;
/*  63 */       float relevancy = 0.0F;
/*  64 */       for (String name : names) {
/*  65 */         int words = Utils.searchInWords(name, filterText);
/*  66 */         int distance = Utils.searchLevenshteinDefault(name, filterText, false);
/*  67 */         bestWords = Math.max(bestWords, words);
/*  68 */         bestDistance = Math.min(bestDistance, distance);
/*  69 */         relevancy = Math.max(relevancy, 1.0F - distance / name.length());
/*     */       } 
/*     */       
/*  72 */       if (bestWords > 0 || relevancy >= 0.5F) {
/*  73 */         list.add(new Entry<>(value, bestDistance));
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  79 */     Comparator<Entry<T>> entryComparator = (comparator != null) ? Comparator.<Entry<T>, T>comparing(Entry::value, comparator).thenComparing((Comparator)filterComparator()) : filterComparator();
/*     */     
/*  81 */     list.sort(entryComparator);
/*     */     
/*  83 */     return iterate(list);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> List<T> createList(Iterable<?> iterable) {
/*  88 */     if (iterable instanceof class_2359) { class_2359<?> indexed = (class_2359)iterable;
/*  89 */       return (List<T>)new ObjectArrayList(indexed.method_10204()); }
/*  90 */      if (iterable instanceof Collection) { Collection<?> collection = (Collection)iterable;
/*  91 */       return (List<T>)new ObjectArrayList(collection.size()); }
/*     */     
/*  93 */     return (List<T>)new ObjectArrayList();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> Comparator<Entry<T>> filterComparator() {
/*  99 */     return (Comparator)FILTER_COMPARATOR;
/*     */   }
/*     */   
/*     */   private static <T> Iterable<T> iterate(final List<Entry<T>> sortedList) {
/* 103 */     return new Iterable<T>()
/*     */       {
/*     */         @NotNull
/*     */         public Iterator<T> iterator() {
/* 107 */           return new Iterator() {
/* 108 */               private final Iterator<SortingHelper.Entry<T>> it = sortedList.iterator();
/*     */ 
/*     */               
/*     */               public boolean hasNext() {
/* 112 */                 return this.it.hasNext();
/*     */               }
/*     */ 
/*     */               
/*     */               public T next() {
/* 117 */                 return ((SortingHelper.Entry<T>)this.it.next()).value();
/*     */               }
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private static <T> Iterable<T> filtering(final Iterable<T> iterable, final Predicate<T> filter) {
/* 125 */     return new Iterable<T>()
/*     */       {
/*     */         @NotNull
/*     */         public Iterator<T> iterator() {
/* 129 */           throw new UnsupportedOperationException("iterator() not supported by this Iterable, use forEach() instead.");
/*     */         }
/*     */ 
/*     */         
/*     */         public void forEach(Consumer<? super T> action) {
/* 134 */           for (T value : iterable) {
/* 135 */             if (filter.test(value))
/* 136 */               action.accept(value); 
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   public static final class Entry<T> extends Record { private final T value; private final int distance;
/*     */     
/* 143 */     public Entry(T value, int distance) { this.value = value; this.distance = distance; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/gui/screens/settings/base/SortingHelper$Entry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #143	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/gui/screens/settings/base/SortingHelper$Entry;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 143 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/gui/screens/settings/base/SortingHelper$Entry<TT;>; } public T value() { return this.value; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/gui/screens/settings/base/SortingHelper$Entry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #143	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/gui/screens/settings/base/SortingHelper$Entry;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/gui/screens/settings/base/SortingHelper$Entry<TT;>; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/gui/screens/settings/base/SortingHelper$Entry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #143	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/gui/screens/settings/base/SortingHelper$Entry;
/*     */       //   0	8	1	o	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 143 */       //   0	8	0	this	Lmeteordevelopment/meteorclient/gui/screens/settings/base/SortingHelper$Entry<TT;>; } public int distance() { return this.distance; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\screens\settings\base\SortingHelper.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */