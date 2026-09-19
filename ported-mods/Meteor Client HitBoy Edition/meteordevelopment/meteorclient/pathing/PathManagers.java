/*    */ package meteordevelopment.meteorclient.pathing;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.utils.PreInit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathManagers
/*    */ {
/* 14 */   private static IPathManager INSTANCE = new NopPathManager();
/*    */   
/*    */   public static IPathManager get() {
/* 17 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   @PreInit
/*    */   public static void init() {
/* 22 */     if (exists("meteordevelopment.voyager.PathManager")) {
/*    */       try {
/* 24 */         INSTANCE = Class.forName("meteordevelopment.voyager.PathManager").getConstructor(new Class[0]).newInstance(new Object[0]);
/* 25 */       } catch (InstantiationException|IllegalAccessException|java.lang.reflect.InvocationTargetException|NoSuchMethodException|ClassNotFoundException e) {
/*    */         
/* 27 */         throw new RuntimeException(e);
/*    */       } 
/*    */     }
/*    */     
/* 31 */     if (exists("baritone.api.BaritoneAPI")) {
/* 32 */       BaritoneUtils.IS_AVAILABLE = true;
/*    */       
/* 34 */       if (INSTANCE instanceof NopPathManager) {
/* 35 */         INSTANCE = new BaritonePathManager();
/*    */       }
/*    */     } 
/* 38 */     MeteorClient.LOG.info("Path Manager: {}", INSTANCE.getName());
/*    */   }
/*    */   
/*    */   private static boolean exists(String name) {
/*    */     try {
/* 43 */       Class.forName(name);
/* 44 */       return true;
/* 45 */     } catch (ClassNotFoundException e) {
/* 46 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\pathing\PathManagers.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */