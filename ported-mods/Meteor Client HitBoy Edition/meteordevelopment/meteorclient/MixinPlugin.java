/*     */ package meteordevelopment.meteorclient;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.asm.Asm;
/*     */ import net.fabricmc.loader.api.FabricLoader;
/*     */ import org.objectweb.asm.tree.ClassNode;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
/*     */ import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
/*     */ import org.spongepowered.asm.mixin.transformer.IMixinTransformer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixinPlugin
/*     */   implements IMixinConfigPlugin
/*     */ {
/*     */   private static final String mixinPackage = "meteordevelopment.meteorclient.mixin";
/*     */   private static boolean loaded;
/*     */   private static boolean isOriginsPresent;
/*     */   private static boolean isIndigoPresent;
/*     */   public static boolean isSodiumPresent;
/*     */   private static boolean isLithiumPresent;
/*     */   public static boolean isIrisPresent;
/*     */   private static boolean isVFPPresent;
/*     */   
/*     */   public void onLoad(String mixinPackage) {
/*  33 */     if (loaded) {
/*     */       return;
/*     */     }
/*     */     try {
/*  37 */       ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  38 */       Class<?> classLoaderClass = classLoader.getClass();
/*     */ 
/*     */       
/*  41 */       Field delegateField = classLoaderClass.getDeclaredField("delegate");
/*  42 */       delegateField.setAccessible(true);
/*  43 */       Object delegate = delegateField.get(classLoader);
/*  44 */       Class<?> delegateClass = delegate.getClass();
/*     */ 
/*     */       
/*  47 */       Field mixinTransformerField = delegateClass.getDeclaredField("mixinTransformer");
/*  48 */       mixinTransformerField.setAccessible(true);
/*     */ 
/*     */       
/*  51 */       Asm.init();
/*     */ 
/*     */       
/*  54 */       Asm.Transformer mixinTransformer = new Asm.Transformer();
/*  55 */       mixinTransformer.delegate = (IMixinTransformer)mixinTransformerField.get(delegate);
/*     */       
/*  57 */       mixinTransformerField.set(delegate, mixinTransformer);
/*  58 */     } catch (NoSuchFieldException|IllegalAccessException e) {
/*  59 */       MeteorClient.LOG.error("Error loading the mixin plugin", e);
/*     */     } 
/*     */     
/*  62 */     isIndigoPresent = FabricLoader.getInstance().isModLoaded("fabric-renderer-indigo");
/*  63 */     isOriginsPresent = FabricLoader.getInstance().isModLoaded("origins");
/*  64 */     isSodiumPresent = FabricLoader.getInstance().isModLoaded("sodium");
/*  65 */     isLithiumPresent = FabricLoader.getInstance().isModLoaded("lithium");
/*  66 */     isIrisPresent = FabricLoader.getInstance().isModLoaded("iris");
/*  67 */     isVFPPresent = FabricLoader.getInstance().isModLoaded("viafabricplus");
/*     */     
/*  69 */     loaded = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getRefMapperConfig() {
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
/*  79 */     if (!mixinClassName.startsWith("meteordevelopment.meteorclient.mixin")) {
/*  80 */       throw new RuntimeException("Mixin " + mixinClassName + " is not in the mixin package");
/*     */     }
/*  82 */     if (mixinClassName.endsWith("PlayerEntityRendererMixin")) {
/*  83 */       return !isOriginsPresent;
/*     */     }
/*  85 */     if (mixinClassName.startsWith("meteordevelopment.meteorclient.mixin.sodium")) {
/*  86 */       return isSodiumPresent;
/*     */     }
/*  88 */     if (mixinClassName.startsWith("meteordevelopment.meteorclient.mixin.indigo")) {
/*  89 */       return isIndigoPresent;
/*     */     }
/*  91 */     if (mixinClassName.startsWith("meteordevelopment.meteorclient.mixin.lithium")) {
/*  92 */       return isLithiumPresent;
/*     */     }
/*  94 */     if (mixinClassName.startsWith("meteordevelopment.meteorclient.mixin.viafabricplus")) {
/*  95 */       return isVFPPresent;
/*     */     }
/*     */ 
/*     */     
/*  99 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}
/*     */ 
/*     */   
/*     */   public List<String> getMixins() {
/* 107 */     return null;
/*     */   }
/*     */   
/*     */   public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
/*     */   
/*     */   public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\MixinPlugin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */