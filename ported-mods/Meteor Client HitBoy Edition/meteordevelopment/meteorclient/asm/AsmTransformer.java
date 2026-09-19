/*    */ package meteordevelopment.meteorclient.asm;
/*    */ 
/*    */ import net.fabricmc.loader.api.FabricLoader;
/*    */ import org.objectweb.asm.tree.ClassNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AsmTransformer
/*    */ {
/*    */   public final String targetName;
/*    */   
/*    */   protected AsmTransformer(String targetName) {
/* 16 */     this.targetName = targetName;
/*    */   }
/*    */   
/*    */   public abstract void transform(ClassNode paramClassNode);
/*    */   
/*    */   protected MethodNode getMethod(ClassNode klass, MethodInfo methodInfo) {
/* 22 */     for (MethodNode method : klass.methods) {
/* 23 */       if (methodInfo.equals(method)) return method;
/*    */     
/*    */     } 
/* 26 */     return null;
/*    */   }
/*    */   
/*    */   protected static void error(String message) {
/* 30 */     System.err.println(message);
/* 31 */     throw new RuntimeException(message);
/*    */   }
/*    */   
/*    */   protected static String mapClassName(String name) {
/* 35 */     return FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", name.replace('/', '.'));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\asm\AsmTransformer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */