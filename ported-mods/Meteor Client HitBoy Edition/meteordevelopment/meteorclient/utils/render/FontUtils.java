/*     */ package meteordevelopment.meteorclient.utils.render;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ReadableByteChannel;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.renderer.Fonts;
/*     */ import meteordevelopment.meteorclient.renderer.text.BuiltinFontFace;
/*     */ import meteordevelopment.meteorclient.renderer.text.FontFace;
/*     */ import meteordevelopment.meteorclient.renderer.text.FontFamily;
/*     */ import meteordevelopment.meteorclient.renderer.text.FontInfo;
/*     */ import meteordevelopment.meteorclient.renderer.text.SystemFontFace;
/*     */ import meteordevelopment.meteorclient.utils.files.ByteBufferUtils;
/*     */ import net.minecraft.class_156;
/*     */ import org.jspecify.annotations.NullMarked;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.stb.STBTTFontinfo;
/*     */ import org.lwjgl.stb.STBTruetype;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @NullMarked
/*     */ public final class FontUtils
/*     */ {
/*     */   public static FontInfo getSysFontInfo(File file) {
/*  33 */     return getFontInfo(file);
/*     */   }
/*     */   
/*     */   public static FontInfo getBuiltinFontInfo(String builtin) {
/*  37 */     return getFontInfo(builtinFontStream(builtin));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FontInfo getFontInfo(File file) {
/*  44 */     if (file == null || !file.isFile()) return null;
/*     */     
/*     */     try {
/*  47 */       return getFontInfo(ByteBufferUtils.readFully(file.toPath(), BufferUtils::createByteBuffer));
/*  48 */     } catch (Exception e) {
/*  49 */       MeteorClient.LOG.warn("Failed to read font file: {}", file, e);
/*  50 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static FontInfo getFontInfo(InputStream stream) {
/*  58 */     if (stream == null) return null; 
/*     */     
/*  60 */     try { ReadableByteChannel ch = Channels.newChannel(stream); 
/*  61 */       try { ByteBuffer buf = ByteBufferUtils.readFully(ch, BufferUtils::createByteBuffer);
/*  62 */         FontInfo fontInfo = getFontInfo(buf);
/*  63 */         if (ch != null) ch.close();  return fontInfo; } catch (Throwable throwable) { if (ch != null) try { ch.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception e)
/*  64 */     { MeteorClient.LOG.warn("Failed to read font stream.", e);
/*  65 */       return null; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FontInfo getFontInfo(ByteBuffer buffer) {
/*  74 */     if (buffer.remaining() < 5) return null;
/*     */ 
/*     */     
/*  77 */     if (buffer
/*  78 */       .get(0) != 0 || buffer
/*  79 */       .get(1) != 1 || buffer
/*  80 */       .get(2) != 0 || buffer
/*  81 */       .get(3) != 0 || buffer
/*  82 */       .get(4) != 0) {
/*  83 */       return null;
/*     */     }
/*  85 */     STBTTFontinfo fontInfo = STBTTFontinfo.create();
/*  86 */     if (!STBTruetype.stbtt_InitFont(fontInfo, buffer)) return null;
/*     */     
/*  88 */     ByteBuffer nameBuffer = STBTruetype.stbtt_GetFontNameString(fontInfo, 3, 1, 1033, 1);
/*  89 */     ByteBuffer typeBuffer = STBTruetype.stbtt_GetFontNameString(fontInfo, 3, 1, 1033, 2);
/*  90 */     if (typeBuffer == null || nameBuffer == null) return null;
/*     */     
/*  92 */     return new FontInfo(StandardCharsets.UTF_16
/*  93 */         .decode(nameBuffer).toString(), 
/*  94 */         FontInfo.Type.fromString(StandardCharsets.UTF_16.decode(typeBuffer).toString()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static Set<String> getSearchPaths() {
/*  99 */     ObjectOpenHashSet<String> objectOpenHashSet = new ObjectOpenHashSet();
/* 100 */     objectOpenHashSet.add(System.getProperty("java.home") + "/lib/fonts");
/*     */     
/* 102 */     for (File dir : getUFontDirs()) {
/* 103 */       if (dir.exists()) objectOpenHashSet.add(dir.getAbsolutePath());
/*     */     
/*     */     } 
/* 106 */     for (File dir : getSFontDirs()) {
/* 107 */       if (dir.exists()) objectOpenHashSet.add(dir.getAbsolutePath());
/*     */     
/*     */     } 
/* 110 */     return (Set<String>)objectOpenHashSet;
/*     */   }
/*     */   
/*     */   public static List<File> getUFontDirs() {
/* 114 */     switch (class_156.method_668()) { case field_1133: case field_1137:  }  return 
/*     */ 
/*     */       
/* 117 */       List.of(new File(System.getProperty("user.home") + "/.local/share/fonts"), new File(System.getProperty("user.home") + "/.fonts"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<File> getSFontDirs() {
/* 122 */     switch (class_156.method_668()) { case field_1133: case field_1137:  }  return 
/*     */ 
/*     */       
/* 125 */       List.of(new File("/usr/share/fonts/"));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadBuiltin(List<FontFamily> fontList, String builtin) {
/* 130 */     FontInfo fontInfo = getBuiltinFontInfo(builtin);
/* 131 */     if (fontInfo == null)
/*     */       return; 
/* 133 */     BuiltinFontFace builtinFontFace = new BuiltinFontFace(fontInfo, builtin);
/* 134 */     if (!addFont(fontList, (FontFace)builtinFontFace)) {
/* 135 */       MeteorClient.LOG.warn("Failed to load builtin font {}", builtinFontFace);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void loadSystem(List<FontFamily> fontList, File dir) {
/* 140 */     if (!dir.exists() || !dir.isDirectory())
/*     */       return; 
/* 142 */     File[] files = dir.listFiles(file -> ((file.isFile() && file.getName().endsWith(".ttf")) || file.isDirectory()));
/* 143 */     if (files == null)
/*     */       return; 
/* 145 */     for (File file : files) {
/* 146 */       if (file.isDirectory()) {
/* 147 */         loadSystem(fontList, file);
/*     */       }
/*     */       else {
/*     */         
/* 151 */         FontInfo fontInfo = getSysFontInfo(file);
/* 152 */         if (fontInfo != null) {
/*     */           
/* 154 */           boolean isBuiltin = false;
/* 155 */           for (String builtinFont : Fonts.BUILTIN_FONTS) {
/* 156 */             if (builtinFont.equals(fontInfo.family())) {
/* 157 */               isBuiltin = true;
/*     */               break;
/*     */             } 
/*     */           } 
/* 161 */           if (!isBuiltin) {
/*     */             
/* 163 */             SystemFontFace systemFontFace = new SystemFontFace(fontInfo, file.toPath());
/* 164 */             if (!addFont(fontList, (FontFace)systemFontFace))
/* 165 */               MeteorClient.LOG.warn("Failed to load system font {}", systemFontFace); 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private static boolean addFont(List<FontFamily> fontList, FontFace font) {
/* 171 */     if (font == null) return false;
/*     */     
/* 173 */     FontInfo info = font.info;
/*     */     
/* 175 */     FontFamily family = Fonts.getFamily(info.family());
/* 176 */     if (family == null) {
/* 177 */       family = new FontFamily(info.family());
/* 178 */       fontList.add(family);
/*     */     } 
/*     */     
/* 181 */     if (family.hasType(info.type())) return false;
/*     */     
/* 183 */     return family.addFont(font);
/*     */   }
/*     */   
/*     */   public static InputStream builtinFontStream(String name) {
/* 187 */     return FontUtils.class.getResourceAsStream("/assets/meteor-client/fonts/" + name + ".ttf");
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\FontUtils.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */