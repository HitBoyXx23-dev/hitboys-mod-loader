/*    */ package meteordevelopment.meteorclient.utils.network;
/*    */ 
/*    */ import java.util.concurrent.ExecutorService;
/*    */ import java.util.concurrent.Executors;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import meteordevelopment.meteorclient.utils.PreInit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MeteorExecutor
/*    */ {
/*    */   public static ExecutorService executor;
/*    */   
/*    */   @PreInit
/*    */   public static void init() {
/* 22 */     AtomicInteger threadNumber = new AtomicInteger(1);
/*    */     
/* 24 */     executor = Executors.newCachedThreadPool(task -> {
/*    */           Thread thread = new Thread(task);
/*    */           thread.setDaemon(true);
/*    */           thread.setName("Meteor-Executor-" + threadNumber.getAndIncrement());
/*    */           return thread;
/*    */         });
/*    */   }
/*    */   
/*    */   public static void execute(Runnable task) {
/* 33 */     executor.execute(task);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\network\MeteorExecutor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */