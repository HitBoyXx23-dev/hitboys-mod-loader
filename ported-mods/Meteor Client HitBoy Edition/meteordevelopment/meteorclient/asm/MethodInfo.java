/*    */ package meteordevelopment.meteorclient.asm;
/*    */ 
/*    */ import net.fabricmc.loader.api.FabricLoader;
/*    */ import net.fabricmc.loader.api.MappingResolver;
/*    */ import org.objectweb.asm.tree.MethodInsnNode;
/*    */ import org.objectweb.asm.tree.MethodNode;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodInfo
/*    */ {
/*    */   private String owner;
/*    */   private String name;
/*    */   private String descriptor;
/*    */   
/*    */   public MethodInfo(String owner, String name, Descriptor descriptor, boolean map) {
/* 17 */     if (map) {
/* 18 */       MappingResolver mappings = FabricLoader.getInstance().getMappingResolver();
/* 19 */       String ownerDot = owner.replace('/', '.');
/*    */       
/* 21 */       if (owner != null) this.owner = mappings.mapClassName("intermediary", ownerDot).replace('.', '/'); 
/* 22 */       if (name != null && descriptor != null) this.name = mappings.mapMethodName("intermediary", ownerDot, name, descriptor.toString(true, false));
/*    */     
/*    */     } else {
/* 25 */       this.owner = owner;
/* 26 */       this.name = name;
/*    */     } 
/*    */     
/* 29 */     if (descriptor != null) this.descriptor = descriptor.toString(true, map); 
/*    */   }
/*    */   
/*    */   public boolean equals(MethodNode method) {
/* 33 */     return ((this.name == null || method.name.equals(this.name)) && (this.descriptor == null || method.desc.equals(this.descriptor)));
/*    */   }
/*    */   
/*    */   public boolean equals(MethodInsnNode insn) {
/* 37 */     return ((this.owner == null || insn.owner.equals(this.owner)) && (this.name == null || insn.name.equals(this.name)) && (this.descriptor == null || insn.desc.equals(this.descriptor)));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\asm\MethodInfo.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */