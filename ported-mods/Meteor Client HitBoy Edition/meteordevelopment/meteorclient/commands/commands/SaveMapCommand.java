/*     */ package meteordevelopment.meteorclient.commands.commands;
/*     */ 
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import javax.imageio.ImageIO;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.commands.Command;
/*     */ import meteordevelopment.meteorclient.mixin.MapTextureManagerAccessor;
/*     */ import net.minecraft.class_10093;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_1806;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_22;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_9209;
/*     */ import net.minecraft.class_9334;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.PointerBuffer;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import org.lwjgl.util.tinyfd.TinyFileDialogs;
/*     */ 
/*     */ public class SaveMapCommand
/*     */   extends Command {
/*  36 */   private static final SimpleCommandExceptionType MAP_NOT_FOUND = new SimpleCommandExceptionType((Message)class_2561.method_43470("You must be holding a filled map."));
/*  37 */   private static final SimpleCommandExceptionType OOPS = new SimpleCommandExceptionType((Message)class_2561.method_43470("Something went wrong."));
/*     */   
/*     */   private final PointerBuffer filters;
/*     */   
/*     */   public SaveMapCommand() {
/*  42 */     super("save-map", "Saves a map to an image.", new String[] { "sm" });
/*     */     
/*  44 */     this.filters = BufferUtils.createPointerBuffer(1);
/*     */     
/*  46 */     ByteBuffer pngFilter = MemoryUtil.memASCII("*.png");
/*     */     
/*  48 */     this.filters.put(pngFilter);
/*  49 */     this.filters.rewind();
/*     */   }
/*     */ 
/*     */   
/*     */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/*  54 */     ((LiteralArgumentBuilder)builder.executes(context -> {
/*     */           saveMap(128);
/*     */           
/*     */           return 1;
/*  58 */         })).then(argument("scale", (ArgumentType)IntegerArgumentType.integer(1)).executes(context -> {
/*     */             saveMap(IntegerArgumentType.getInteger(context, "scale"));
/*     */             return 1;
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveMap(int scale) throws CommandSyntaxException {
/*  67 */     class_1799 map = getMap();
/*  68 */     class_22 state = getMapState();
/*  69 */     if (map == null || state == null) throw MAP_NOT_FOUND.create();
/*     */     
/*  71 */     File path = getPath();
/*  72 */     if (path == null) throw OOPS.create();
/*     */     
/*  74 */     MapTextureManagerAccessor textureManager = (MapTextureManagerAccessor)mc.field_1773.method_35772().method_61963();
/*  75 */     class_10093.class_331 texture = textureManager.meteor$invokeGetMapTexture((class_9209)map.method_58694(class_9334.field_49646), state);
/*  76 */     if (texture.field_2048.method_4525() == null) throw OOPS.create();
/*     */     
/*     */     try {
/*  79 */       if (scale == 128) { texture.field_2048.method_4525().method_4325(path); }
/*     */       else
/*  81 */       { int[] data = texture.field_2048.method_4525().method_4322();
/*  82 */         BufferedImage image = new BufferedImage(128, 128, 2);
/*  83 */         image.setRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, 128);
/*     */         
/*  85 */         BufferedImage scaledImage = new BufferedImage(scale, scale, 2);
/*  86 */         scaledImage.createGraphics().drawImage(image, 0, 0, scale, scale, null);
/*     */         
/*  88 */         ImageIO.write(scaledImage, "png", path); }
/*     */     
/*  90 */     } catch (IOException e) {
/*  91 */       error("Error writing map texture", new Object[0]);
/*  92 */       MeteorClient.LOG.error(e.toString());
/*     */     } 
/*     */   }
/*     */   @Nullable
/*     */   private class_22 getMapState() {
/*  97 */     class_1799 map = getMap();
/*  98 */     if (map == null) return null;
/*     */     
/* 100 */     return class_1806.method_7997((class_9209)map.method_58694(class_9334.field_49646), (class_1937)mc.field_1687);
/*     */   }
/*     */   @Nullable
/*     */   private File getPath() {
/* 104 */     String path = TinyFileDialogs.tinyfd_saveFileDialog("Save image", null, this.filters, null);
/* 105 */     if (path == null) return null; 
/* 106 */     if (!path.endsWith(".png")) path = path + ".png";
/*     */     
/* 108 */     return new File(path);
/*     */   }
/*     */   @Nullable
/*     */   private class_1799 getMap() {
/* 112 */     class_1799 itemStack = mc.field_1724.method_6047();
/* 113 */     if (itemStack.method_7909() == class_1802.field_8204) return itemStack;
/*     */     
/* 115 */     itemStack = mc.field_1724.method_6079();
/* 116 */     if (itemStack.method_7909() == class_1802.field_8204) return itemStack;
/*     */     
/* 118 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\SaveMapCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */