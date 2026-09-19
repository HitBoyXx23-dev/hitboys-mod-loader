/*    */ package meteordevelopment.meteorclient.utils;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.stream.Collectors;
/*    */ import meteordevelopment.meteorclient.addons.AddonManager;
/*    */ import meteordevelopment.meteorclient.addons.MeteorAddon;
/*    */ import org.reflections.Reflections;
/*    */ import org.reflections.scanners.Scanner;
/*    */ import org.reflections.scanners.Scanners;
/*    */ 
/*    */ public class ReflectInit
/*    */ {
/* 20 */   private static final List<Reflections> reflections = new ArrayList<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void registerPackages() {
/* 26 */     for (MeteorAddon addon : AddonManager.ADDONS) {
/*    */       try {
/* 28 */         add(addon);
/* 29 */       } catch (AbstractMethodError e) {
/* 30 */         throw new RuntimeException("Addon \"%s\" is too old and cannot be ran.".formatted(new Object[] { addon.name }, ), e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private static void add(MeteorAddon addon) {
/* 36 */     String pkg = addon.getPackage();
/* 37 */     if (pkg == null || pkg.isBlank())
/* 38 */       return;  reflections.add(new Reflections(pkg, new Scanner[] { (Scanner)Scanners.MethodsAnnotated }));
/*    */   }
/*    */   
/*    */   public static void init(Class<? extends Annotation> annotation) {
/* 42 */     for (Reflections reflection : reflections) {
/* 43 */       Set<Method> initTasks = reflection.getMethodsAnnotatedWith(annotation);
/* 44 */       if (initTasks == null)
/*    */         return; 
/* 46 */       Map<Class<?>, List<Method>> byClass = (Map<Class<?>, List<Method>>)initTasks.stream().collect(Collectors.groupingBy(Method::getDeclaringClass));
/* 47 */       Set<Method> left = new HashSet<>(initTasks);
/*    */       Method m;
/* 49 */       while ((m = left.stream().findAny().orElse(null)) != null) {
/* 50 */         reflectInit(m, annotation, left, byClass);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private static <T extends Annotation> void reflectInit(Method task, Class<T> annotation, Set<Method> left, Map<Class<?>, List<Method>> byClass) {
/* 56 */     left.remove(task);
/*    */     
/* 58 */     for (Class<?> clazz : getDependencies(task, annotation)) {
/* 59 */       for (Method m : byClass.getOrDefault(clazz, Collections.emptyList())) {
/* 60 */         if (left.contains(m)) {
/* 61 */           reflectInit(m, annotation, left, byClass);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/*    */     try {
/* 67 */       task.invoke(null, new Object[0]);
/* 68 */     } catch (IllegalAccessException|java.lang.reflect.InvocationTargetException e) {
/* 69 */       throw new IllegalStateException("Error running @%s task '%s.%s'".formatted(new Object[] { annotation.getSimpleName(), task.getDeclaringClass().getSimpleName(), task.getName() }, ), e);
/* 70 */     } catch (NullPointerException e) {
/* 71 */       throw new RuntimeException("Method \"%s\" using Init annotations from non-static context".formatted(new Object[] { task.getName() }, ), e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static <T extends Annotation> Class<?>[] getDependencies(Method task, Class<T> annotation) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: invokevirtual getAnnotation : (Ljava/lang/Class;)Ljava/lang/annotation/Annotation;
/*    */     //   5: astore_2
/*    */     //   6: aload_2
/*    */     //   7: dup
/*    */     //   8: invokestatic requireNonNull : (Ljava/lang/Object;)Ljava/lang/Object;
/*    */     //   11: pop
/*    */     //   12: astore_3
/*    */     //   13: iconst_0
/*    */     //   14: istore #4
/*    */     //   16: aload_3
/*    */     //   17: iload #4
/*    */     //   19: <illegal opcode> typeSwitch : (Ljava/lang/Object;I)I
/*    */     //   24: lookupswitch default -> 84, 0 -> 52, 1 -> 68
/*    */     //   52: aload_3
/*    */     //   53: checkcast meteordevelopment/meteorclient/utils/PreInit
/*    */     //   56: astore #5
/*    */     //   58: aload #5
/*    */     //   60: invokeinterface dependencies : ()[Ljava/lang/Class;
/*    */     //   65: goto -> 88
/*    */     //   68: aload_3
/*    */     //   69: checkcast meteordevelopment/meteorclient/utils/PostInit
/*    */     //   72: astore #6
/*    */     //   74: aload #6
/*    */     //   76: invokeinterface dependencies : ()[Ljava/lang/Class;
/*    */     //   81: goto -> 88
/*    */     //   84: iconst_0
/*    */     //   85: anewarray java/lang/Class
/*    */     //   88: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #76	-> 0
/*    */     //   #78	-> 6
/*    */     //   #79	-> 52
/*    */     //   #80	-> 68
/*    */     //   #81	-> 84
/*    */     //   #78	-> 88
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   58	10	5	pre	Lmeteordevelopment/meteorclient/utils/PreInit;
/*    */     //   74	10	6	post	Lmeteordevelopment/meteorclient/utils/PostInit;
/*    */     //   0	89	0	task	Ljava/lang/reflect/Method;
/*    */     //   0	89	1	annotation	Ljava/lang/Class;
/*    */     //   6	83	2	init	Ljava/lang/annotation/Annotation;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	89	1	annotation	Ljava/lang/Class<TT;>;
/*    */     //   6	83	2	init	TT;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\ReflectInit.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */