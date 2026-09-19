/*     */ package meteordevelopment.meteorclient.renderer.text;
/*     */ 
/*     */ import com.mojang.blaze3d.textures.FilterMode;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import meteordevelopment.meteorclient.renderer.MeshBuilder;
/*     */ import meteordevelopment.meteorclient.renderer.Texture;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.stb.STBTTFontinfo;
/*     */ import org.lwjgl.stb.STBTTPackContext;
/*     */ import org.lwjgl.stb.STBTTPackRange;
/*     */ import org.lwjgl.stb.STBTTPackedchar;
/*     */ import org.lwjgl.stb.STBTruetype;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ import org.lwjgl.system.Struct;
/*     */ 
/*     */ public class Font
/*     */ {
/*     */   public final Texture texture;
/*     */   private final int height;
/*     */   private final float scale;
/*     */   private final float ascent;
/*  26 */   private final Int2ObjectOpenHashMap<CharData> charMap = new Int2ObjectOpenHashMap();
/*     */   private static final int size = 2048;
/*     */   
/*     */   public Font(ByteBuffer buffer, int height) {
/*  30 */     this.height = height;
/*     */ 
/*     */     
/*  33 */     STBTTFontinfo fontInfo = STBTTFontinfo.create();
/*  34 */     STBTruetype.stbtt_InitFont(fontInfo, buffer);
/*     */ 
/*     */     
/*  37 */     ByteBuffer bitmap = BufferUtils.createByteBuffer(4194304);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  44 */     STBTTPackedchar.Buffer[] cdata = { STBTTPackedchar.create(95), STBTTPackedchar.create(96), STBTTPackedchar.create(128), STBTTPackedchar.create(144), STBTTPackedchar.create(256), STBTTPackedchar.create(1) };
/*     */ 
/*     */ 
/*     */     
/*  48 */     STBTTPackContext packContext = STBTTPackContext.create();
/*  49 */     STBTruetype.stbtt_PackBegin(packContext, bitmap, 2048, 2048, 0, 1);
/*     */ 
/*     */     
/*  52 */     STBTTPackRange.Buffer packRange = STBTTPackRange.create(cdata.length);
/*  53 */     packRange.put((Struct)STBTTPackRange.create().set(height, 32, null, 95, cdata[0], (byte)2, (byte)2));
/*  54 */     packRange.put((Struct)STBTTPackRange.create().set(height, 160, null, 96, cdata[1], (byte)2, (byte)2));
/*  55 */     packRange.put((Struct)STBTTPackRange.create().set(height, 256, null, 128, cdata[2], (byte)2, (byte)2));
/*  56 */     packRange.put((Struct)STBTTPackRange.create().set(height, 880, null, 144, cdata[3], (byte)2, (byte)2));
/*  57 */     packRange.put((Struct)STBTTPackRange.create().set(height, 1024, null, 256, cdata[4], (byte)2, (byte)2));
/*  58 */     packRange.put((Struct)STBTTPackRange.create().set(height, 8734, null, 1, cdata[5], (byte)2, (byte)2));
/*  59 */     packRange.flip();
/*     */ 
/*     */     
/*  62 */     STBTruetype.stbtt_PackFontRanges(packContext, buffer, 0, packRange);
/*  63 */     STBTruetype.stbtt_PackEnd(packContext);
/*     */ 
/*     */     
/*  66 */     this.texture = new Texture(2048, 2048, TextureFormat.RED8, FilterMode.LINEAR, FilterMode.LINEAR);
/*  67 */     this.texture.upload(bitmap);
/*  68 */     this.scale = STBTruetype.stbtt_ScaleForPixelHeight(fontInfo, height);
/*     */ 
/*     */     
/*  71 */     MemoryStack stack = MemoryStack.stackPush(); 
/*  72 */     try { IntBuffer ascent = stack.mallocInt(1);
/*  73 */       STBTruetype.stbtt_GetFontVMetrics(fontInfo, ascent, null, null);
/*  74 */       this.ascent = ascent.get(0);
/*  75 */       if (stack != null) stack.close();  } catch (Throwable throwable) { if (stack != null)
/*     */         try { stack.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*  77 */      for (int i = 0; i < cdata.length; i++) {
/*  78 */       STBTTPackedchar.Buffer cbuf = cdata[i];
/*  79 */       int offset = ((STBTTPackRange)packRange.get(i)).first_unicode_codepoint_in_range();
/*     */       
/*  81 */       for (int j = 0; j < cbuf.capacity(); j++) {
/*  82 */         STBTTPackedchar packedChar = (STBTTPackedchar)cbuf.get(j);
/*     */         
/*  84 */         float ipw = 4.8828125E-4F;
/*  85 */         float iph = 4.8828125E-4F;
/*     */         
/*  87 */         this.charMap.put(j + offset, new CharData(packedChar
/*  88 */               .xoff(), packedChar
/*  89 */               .yoff(), packedChar
/*  90 */               .xoff2(), packedChar
/*  91 */               .yoff2(), packedChar
/*  92 */               .x0() * ipw, packedChar
/*  93 */               .y0() * iph, packedChar
/*  94 */               .x1() * ipw, packedChar
/*  95 */               .y1() * iph, packedChar
/*  96 */               .xadvance()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getWidth(String string, int length) {
/* 103 */     double width = 0.0D;
/*     */     
/* 105 */     for (int i = 0; i < length; i++) {
/* 106 */       int cp = string.charAt(i);
/* 107 */       CharData c = (CharData)this.charMap.get(cp);
/* 108 */       if (c == null) c = (CharData)this.charMap.get(32);
/*     */       
/* 110 */       width += c.xAdvance;
/*     */     } 
/*     */     
/* 113 */     return width;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/* 117 */     return this.height;
/*     */   }
/*     */   
/*     */   public double render(MeshBuilder mesh, String string, double x, double y, Color color, double scale) {
/* 121 */     y += (this.ascent * this.scale) * scale;
/*     */     
/* 123 */     int length = string.length();
/* 124 */     mesh.ensureCapacity(length * 4, length * 6);
/*     */     
/* 126 */     for (int i = 0; i < length; i++) {
/* 127 */       int cp = string.charAt(i);
/* 128 */       CharData c = (CharData)this.charMap.get(cp);
/* 129 */       if (c == null) c = (CharData)this.charMap.get(32);
/*     */       
/* 131 */       mesh.quad(mesh
/* 132 */           .vec2(x + c.x0 * scale, y + c.y0 * scale).vec2(c.u0, c.v0).color(color).next(), mesh
/* 133 */           .vec2(x + c.x0 * scale, y + c.y1 * scale).vec2(c.u0, c.v1).color(color).next(), mesh
/* 134 */           .vec2(x + c.x1 * scale, y + c.y1 * scale).vec2(c.u1, c.v1).color(color).next(), mesh
/* 135 */           .vec2(x + c.x1 * scale, y + c.y0 * scale).vec2(c.u1, c.v0).color(color).next());
/*     */ 
/*     */       
/* 138 */       x += c.xAdvance * scale;
/*     */     } 
/*     */     
/* 141 */     return x;
/*     */   }
/*     */   private static final class CharData extends Record { private final float x0; private final float y0; private final float x1; private final float y1; private final float u0; private final float v0; private final float u1; private final float v1; private final float xAdvance;
/* 144 */     private CharData(float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1, float xAdvance) { this.x0 = x0; this.y0 = y0; this.x1 = x1; this.y1 = y1; this.u0 = u0; this.v0 = v0; this.u1 = u1; this.v1 = v1; this.xAdvance = xAdvance; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/renderer/text/Font$CharData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #144	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 144 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/renderer/text/Font$CharData; } public float x0() { return this.x0; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/renderer/text/Font$CharData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #144	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lmeteordevelopment/meteorclient/renderer/text/Font$CharData; } public final boolean equals(Object o) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/renderer/text/Font$CharData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #144	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lmeteordevelopment/meteorclient/renderer/text/Font$CharData;
/* 144 */       //   0	8	1	o	Ljava/lang/Object; } public float y0() { return this.y0; } public float x1() { return this.x1; } public float y1() { return this.y1; } public float u0() { return this.u0; } public float v0() { return this.v0; } public float u1() { return this.u1; } public float v1() { return this.v1; } public float xAdvance() { return this.xAdvance; }
/*     */      }
/*     */ 
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\text\Font.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */