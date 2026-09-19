/*     */ package meteordevelopment.meteorclient.asm;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Transformer
/*     */   implements IMixinTransformer
/*     */ {
/*     */   public IMixinTransformer delegate;
/*     */   
/*     */   public void audit(MixinEnvironment environment) {
/*  88 */     this.delegate.audit(environment);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> reload(String mixinClass, ClassNode classNode) {
/*  93 */     return this.delegate.reload(mixinClass, classNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean computeFramesForClass(MixinEnvironment environment, String name, ClassNode classNode) {
/*  98 */     return this.delegate.computeFramesForClass(environment, name, classNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
/* 103 */     basicClass = this.delegate.transformClassBytes(name, transformedName, basicClass);
/* 104 */     return Asm.INSTANCE.transform(name, basicClass);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] transformClass(MixinEnvironment environment, String name, byte[] classBytes) {
/* 109 */     return this.delegate.transformClass(environment, name, classBytes);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean transformClass(MixinEnvironment environment, String name, ClassNode classNode) {
/* 114 */     return this.delegate.transformClass(environment, name, classNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean couldTransformClass(MixinEnvironment environment, String name) {
/* 119 */     return this.delegate.couldTransformClass(environment, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] generateClass(MixinEnvironment environment, String name) {
/* 124 */     return this.delegate.generateClass(environment, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generateClass(MixinEnvironment environment, String name, ClassNode classNode) {
/* 129 */     return this.delegate.generateClass(environment, name, classNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public IExtensionRegistry getExtensions() {
/* 134 */     return this.delegate.getExtensions();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\asm\Asm$Transformer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */