/*     */ package meteordevelopment.meteorclient.systems;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.AtomicMoveNotSupportedException;
/*     */ import java.nio.file.CopyOption;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.StandardCopyOption;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.Locale;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.utils.files.StreamUtils;
/*     */ import meteordevelopment.meteorclient.utils.misc.ISerializable;
/*     */ import net.minecraft.class_148;
/*     */ import net.minecraft.class_2487;
/*     */ import net.minecraft.class_2507;
/*     */ import org.apache.commons.io.FilenameUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class System<T>
/*     */   implements ISerializable<T>
/*     */ {
/*     */   private final String name;
/*     */   private File file;
/*     */   protected boolean isFirstInit;
/*  30 */   private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss", Locale.ROOT);
/*     */   
/*     */   public System(String name) {
/*  33 */     this.name = name;
/*     */     
/*  35 */     if (name != null) {
/*  36 */       this.file = new File(MeteorClient.FOLDER, name + ".nbt");
/*  37 */       this.isFirstInit = !this.file.exists();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void init() {}
/*     */   
/*     */   public void save(File folder) {
/*  44 */     File file = getFile();
/*  45 */     if (file == null)
/*     */       return; 
/*  47 */     class_2487 tag = toTag();
/*  48 */     if (tag == null)
/*     */       return; 
/*     */     try {
/*  51 */       File tempFile = File.createTempFile("meteor-client", file.getName());
/*  52 */       class_2507.method_10630(tag, tempFile.toPath());
/*     */       
/*  54 */       if (folder != null) file = new File(folder, file.getName());
/*     */       
/*  56 */       file.getParentFile().mkdirs();
/*     */       
/*     */       try {
/*  59 */         Files.move(tempFile.toPath(), file.toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE });
/*  60 */       } catch (AtomicMoveNotSupportedException e) {
/*  61 */         StreamUtils.copy(tempFile, file);
/*     */       } 
/*     */       
/*  64 */       tempFile.delete();
/*  65 */     } catch (IOException e) {
/*  66 */       MeteorClient.LOG.error("Error saving {}. Possibly corrupted?", this.name, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void save() {
/*  71 */     save(null);
/*     */   }
/*     */   
/*     */   public void load(File folder) {
/*  75 */     File file = getFile();
/*  76 */     if (file == null)
/*     */       return; 
/*     */     try {
/*  79 */       if (folder != null) file = new File(folder, file.getName());
/*     */       
/*  81 */       if (file.exists()) {
/*     */         try {
/*  83 */           fromTag(class_2507.method_10633(file.toPath()));
/*  84 */         } catch (class_148 e) {
/*  85 */           String backupName = FilenameUtils.removeExtension(file.getName()) + "-" + FilenameUtils.removeExtension(file.getName()) + ".backup.nbt";
/*  86 */           File backup = new File(file.getParentFile(), backupName);
/*     */           
/*     */           try {
/*  89 */             Files.move(file.toPath(), backup.toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE });
/*  90 */           } catch (AtomicMoveNotSupportedException ex) {
/*  91 */             StreamUtils.copy(file, backup);
/*     */           } 
/*     */           
/*  94 */           MeteorClient.LOG.error("Error loading {}. Possibly corrupted?", this.name, e);
/*  95 */           MeteorClient.LOG.info("Saved settings backup to '{}'.", backup);
/*     */         } 
/*     */       }
/*  98 */     } catch (IOException e) {
/*  99 */       MeteorClient.LOG.error("Error loading {}. Possibly corrupted?", this.name, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void load() {
/* 104 */     load(null);
/*     */   }
/*     */   
/*     */   public File getFile() {
/* 108 */     return this.file;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 112 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public class_2487 toTag() {
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public T fromTag(class_2487 tag) {
/* 122 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\System.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */