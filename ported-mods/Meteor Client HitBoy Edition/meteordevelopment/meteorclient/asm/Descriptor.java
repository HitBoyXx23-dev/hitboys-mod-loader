/*    */ package meteordevelopment.meteorclient.asm;
/*    */ 
/*    */ import net.fabricmc.loader.api.FabricLoader;
/*    */ import net.fabricmc.loader.api.MappingResolver;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Descriptor
/*    */ {
/*    */   private final String[] components;
/*    */   
/*    */   public Descriptor(String... components) {
/* 15 */     this.components = components;
/*    */   }
/*    */   
/*    */   public String toString(boolean method, boolean map) {
/* 19 */     MappingResolver mappings = FabricLoader.getInstance().getMappingResolver();
/* 20 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 22 */     if (method) sb.append('('); 
/* 23 */     for (int i = 0; i < this.components.length; i++) {
/* 24 */       if (method && i == this.components.length - 1) sb.append(')');
/*    */       
/* 26 */       String component = this.components[i];
/*    */       
/* 28 */       if (map && component.startsWith("L") && component.endsWith(";")) {
/* 29 */         sb.append('L').append(mappings.mapClassName("intermediary", component.substring(1, component.length() - 1).replace('/', '.')).replace('.', '/')).append(';');
/*    */       } else {
/* 31 */         sb.append(component);
/*    */       } 
/*    */     } 
/* 34 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\asm\Descriptor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */