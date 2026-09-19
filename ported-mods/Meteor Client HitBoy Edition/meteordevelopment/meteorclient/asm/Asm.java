/*     */ package meteordevelopment.meteorclient.asm;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.asm.transformers.PacketInflaterTransformer;
/*     */ import net.fabricmc.loader.api.FabricLoader;
/*     */ import org.objectweb.asm.ClassReader;
/*     */ import org.objectweb.asm.ClassVisitor;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.MixinEnvironment;
/*     */ import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
/*     */ import org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry;
/*     */ import org.spongepowered.asm.transformers.MixinClassWriter;
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
/*     */ public class Asm
/*     */ {
/*     */   public static Asm INSTANCE;
/*  33 */   private final Map<String, AsmTransformer> transformers = new HashMap<>();
/*     */   private final boolean export;
/*     */   
/*     */   public Asm(boolean export) {
/*  37 */     this.export = export;
/*     */   }
/*     */   
/*     */   public static void init() {
/*  41 */     if (INSTANCE != null)
/*     */       return; 
/*  43 */     INSTANCE = new Asm((System.getProperty("meteor.asm.export") != null));
/*  44 */     INSTANCE.add((AsmTransformer)new PacketInflaterTransformer());
/*     */   }
/*     */   
/*     */   private void add(AsmTransformer transformer) {
/*  48 */     this.transformers.put(transformer.targetName, transformer);
/*     */   }
/*     */   
/*     */   public byte[] transform(String name, byte[] bytes) {
/*  52 */     AsmTransformer transformer = this.transformers.get(name);
/*     */     
/*  54 */     if (transformer != null) {
/*  55 */       ClassNode klass = new ClassNode();
/*  56 */       ClassReader reader = new ClassReader(bytes);
/*  57 */       reader.accept((ClassVisitor)klass, 8);
/*     */       
/*  59 */       transformer.transform(klass);
/*     */       
/*  61 */       MixinClassWriter mixinClassWriter = new MixinClassWriter(reader, 2);
/*  62 */       klass.accept((ClassVisitor)mixinClassWriter);
/*  63 */       bytes = mixinClassWriter.toByteArray();
/*     */       
/*  65 */       export(name, bytes);
/*     */     } 
/*     */     
/*  68 */     return bytes;
/*     */   }
/*     */   
/*     */   private void export(String name, byte[] bytes) {
/*  72 */     if (this.export)
/*     */       try {
/*  74 */         Path path = Path.of(FabricLoader.getInstance().getGameDir().toString(), new String[] { ".meteor.asm.out", name.replace('.', '/') + ".class" });
/*  75 */         (new File(path.toUri())).getParentFile().mkdirs();
/*  76 */         Files.write(path, bytes, new java.nio.file.OpenOption[0]);
/*  77 */       } catch (IOException e) {
/*  78 */         MeteorClient.LOG.error("Failed to export transformer '{}': ", name, e);
/*     */       }  
/*     */   }
/*     */   
/*     */   public static class Transformer
/*     */     implements IMixinTransformer
/*     */   {
/*     */     public IMixinTransformer delegate;
/*     */     
/*     */     public void audit(MixinEnvironment environment) {
/*  88 */       this.delegate.audit(environment);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<String> reload(String mixinClass, ClassNode classNode) {
/*  93 */       return this.delegate.reload(mixinClass, classNode);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean computeFramesForClass(MixinEnvironment environment, String name, ClassNode classNode) {
/*  98 */       return this.delegate.computeFramesForClass(environment, name, classNode);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] transformClassBytes(String name, String transformedName, byte[] basicClass) {
/* 103 */       basicClass = this.delegate.transformClassBytes(name, transformedName, basicClass);
/* 104 */       return Asm.INSTANCE.transform(name, basicClass);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] transformClass(MixinEnvironment environment, String name, byte[] classBytes) {
/* 109 */       return this.delegate.transformClass(environment, name, classBytes);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean transformClass(MixinEnvironment environment, String name, ClassNode classNode) {
/* 114 */       return this.delegate.transformClass(environment, name, classNode);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean couldTransformClass(MixinEnvironment environment, String name) {
/* 119 */       return this.delegate.couldTransformClass(environment, name);
/*     */     }
/*     */ 
/*     */     
/*     */     public byte[] generateClass(MixinEnvironment environment, String name) {
/* 124 */       return this.delegate.generateClass(environment, name);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean generateClass(MixinEnvironment environment, String name, ClassNode classNode) {
/* 129 */       return this.delegate.generateClass(environment, name, classNode);
/*     */     }
/*     */ 
/*     */     
/*     */     public IExtensionRegistry getExtensions() {
/* 134 */       return this.delegate.getExtensions();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\asm\Asm.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */