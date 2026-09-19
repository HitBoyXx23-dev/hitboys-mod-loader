/*     */ package meteordevelopment.meteorclient.gui.renderer.packer;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.TextureUtil;
/*     */ import com.mojang.blaze3d.textures.FilterMode;
/*     */ import com.mojang.blaze3d.textures.TextureFormat;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.renderer.Texture;
/*     */ import net.minecraft.class_2960;
/*     */ import net.minecraft.class_3298;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.stb.STBImage;
/*     */ import org.lwjgl.stb.STBImageResize;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TexturePacker
/*     */ {
/*     */   private static final int maxWidth = 2048;
/*  32 */   private final List<Image> images = new ArrayList<>();
/*     */   
/*     */   public GuiTexture add(class_2960 id) {
/*     */     try {
/*  36 */       InputStream in = ((class_3298)MeteorClient.mc.method_1478().method_14486(id).get()).method_14482();
/*  37 */       GuiTexture texture = new GuiTexture();
/*     */       
/*  39 */       MemoryStack stack = MemoryStack.stackPush(); 
/*  40 */       try { ByteBuffer rawImageBuffer = null;
/*     */         
/*     */         try {
/*  43 */           rawImageBuffer = TextureUtil.readResource(in);
/*  44 */           rawImageBuffer.rewind();
/*     */           
/*  46 */           IntBuffer w = stack.mallocInt(1);
/*  47 */           IntBuffer h = stack.mallocInt(1);
/*  48 */           IntBuffer ignored = stack.mallocInt(1);
/*     */           
/*  50 */           ByteBuffer imageBuffer = STBImage.stbi_load_from_memory(rawImageBuffer, w, h, ignored, 4);
/*     */           
/*  52 */           int width = w.get(0);
/*  53 */           int height = h.get(0);
/*     */           
/*  55 */           TextureRegion region = new TextureRegion(width, height);
/*  56 */           texture.add(region);
/*     */           
/*  58 */           this.images.add(new Image(imageBuffer, region, width, height, true));
/*     */           
/*  60 */           if (width > 20) addResized(texture, imageBuffer, width, height, 20); 
/*  61 */           if (width > 32) addResized(texture, imageBuffer, width, height, 32); 
/*  62 */           if (width > 48) addResized(texture, imageBuffer, width, height, 48); 
/*  63 */         } catch (IOException e) {
/*  64 */           e.printStackTrace();
/*     */         } finally {
/*  66 */           MemoryUtil.memFree(rawImageBuffer);
/*     */         } 
/*  68 */         if (stack != null) stack.close();  } catch (Throwable throwable) { if (stack != null)
/*     */           try { stack.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*  70 */        return texture;
/*  71 */     } catch (IOException e) {
/*  72 */       e.printStackTrace();
/*     */ 
/*     */       
/*  75 */       return null;
/*     */     } 
/*     */   }
/*     */   private void addResized(GuiTexture texture, ByteBuffer srcImageBuffer, int srcWidth, int srcHeight, int width) {
/*  79 */     double scaleFactor = width / srcWidth;
/*  80 */     int height = (int)(srcHeight * scaleFactor);
/*     */     
/*  82 */     ByteBuffer imageBuffer = BufferUtils.createByteBuffer(width * height * 4);
/*  83 */     STBImageResize.stbir_resize_uint8(srcImageBuffer, srcWidth, srcHeight, 0, imageBuffer, width, height, 0, 4);
/*     */     
/*  85 */     TextureRegion region = new TextureRegion(width, height);
/*  86 */     texture.add(region);
/*     */     
/*  88 */     this.images.add(new Image(imageBuffer, region, width, height, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture pack() {
/*  93 */     int width = 0;
/*  94 */     int height = 0;
/*     */     
/*  96 */     int rowWidth = 0;
/*  97 */     int rowHeight = 0;
/*     */     
/*  99 */     for (Image image : this.images) {
/* 100 */       if (rowWidth + image.width > 2048) {
/* 101 */         width = Math.max(width, rowWidth);
/* 102 */         height += rowHeight;
/*     */         
/* 104 */         rowWidth = 0;
/* 105 */         rowHeight = 0;
/*     */       } 
/*     */       
/* 108 */       image.x = 1 + rowWidth;
/* 109 */       image.y = 1 + height;
/*     */       
/* 111 */       rowWidth += 1 + image.width + 1;
/* 112 */       rowHeight = Math.max(rowHeight, 1 + image.height + 1);
/*     */     } 
/*     */     
/* 115 */     width = Math.max(width, rowWidth);
/* 116 */     height += rowHeight;
/*     */ 
/*     */     
/* 119 */     ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
/*     */     
/* 121 */     for (Image image : this.images) {
/*     */       
/* 123 */       byte[] row = new byte[image.width * 4];
/*     */       
/* 125 */       for (int i = 0; i < image.height; i++) {
/* 126 */         image.buffer.position(i * row.length);
/* 127 */         image.buffer.get(row);
/*     */         
/* 129 */         buffer.position(((image.y + i) * width + image.x) * 4);
/* 130 */         buffer.put(row);
/*     */       } 
/*     */       
/* 133 */       image.buffer.rewind();
/* 134 */       image.free();
/*     */ 
/*     */       
/* 137 */       image.region.x1 = image.x / width;
/* 138 */       image.region.y1 = image.y / height;
/* 139 */       image.region.x2 = (image.x + image.width) / width;
/* 140 */       image.region.y2 = (image.y + image.height) / height;
/*     */     } 
/*     */     
/* 143 */     buffer.rewind();
/*     */     
/* 145 */     Texture texture = new Texture(width, height, TextureFormat.RGBA8, FilterMode.LINEAR, FilterMode.LINEAR);
/* 146 */     texture.upload(buffer);
/*     */     
/* 148 */     return texture;
/*     */   }
/*     */   
/*     */   private static class Image
/*     */   {
/*     */     public final ByteBuffer buffer;
/*     */     public final TextureRegion region;
/*     */     public final int width;
/*     */     public final int height;
/*     */     public int x;
/*     */     public int y;
/*     */     private final boolean stb;
/*     */     
/*     */     public Image(ByteBuffer buffer, TextureRegion region, int width, int height, boolean stb) {
/* 162 */       this.buffer = buffer;
/* 163 */       this.region = region;
/* 164 */       this.width = width;
/* 165 */       this.height = height;
/* 166 */       this.stb = stb;
/*     */     }
/*     */     
/*     */     public void free() {
/* 170 */       if (this.stb) STBImage.stbi_image_free(this.buffer); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\renderer\packer\TexturePacker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */