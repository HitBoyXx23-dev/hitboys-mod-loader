/*    */ package meteordevelopment.meteorclient.utils.render;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.TextureUtil;
/*    */ import com.mojang.blaze3d.textures.FilterMode;
/*    */ import com.mojang.blaze3d.textures.TextureFormat;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import javax.imageio.ImageIO;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.renderer.Texture;
/*    */ import meteordevelopment.meteorclient.utils.network.Http;
/*    */ import net.minecraft.class_3298;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.stb.STBImage;
/*    */ import org.lwjgl.system.MemoryStack;
/*    */ import org.lwjgl.system.MemoryUtil;
/*    */ 
/*    */ public class PlayerHeadTexture
/*    */   extends Texture
/*    */ {
/*    */   private boolean needsRotate;
/*    */   
/*    */   public PlayerHeadTexture(byte[] head, boolean needsRotate) {
/* 27 */     super(8, 8, TextureFormat.RGBA8, FilterMode.NEAREST, FilterMode.NEAREST);
/*    */     
/* 29 */     upload(BufferUtils.createByteBuffer(head.length).put(head));
/* 30 */     this.needsRotate = needsRotate;
/*    */   }
/*    */   
/*    */   public PlayerHeadTexture() {
/* 34 */     super(8, 8, TextureFormat.RGBA8, FilterMode.NEAREST, FilterMode.NEAREST);
/*    */     
/* 36 */     try { InputStream inputStream = ((class_3298)MeteorClient.mc.method_1478().method_14486(MeteorClient.identifier("textures/steve.png")).get()).method_14482(); 
/* 37 */       try { ByteBuffer data = TextureUtil.readResource(inputStream);
/* 38 */         data.rewind();
/*    */         
/* 40 */         MemoryStack stack = MemoryStack.stackPush(); 
/* 41 */         try { IntBuffer width = stack.mallocInt(1);
/* 42 */           IntBuffer height = stack.mallocInt(1);
/* 43 */           IntBuffer comp = stack.mallocInt(1);
/*    */           
/* 45 */           ByteBuffer image = STBImage.stbi_load_from_memory(data, width, height, comp, 4);
/* 46 */           upload(image);
/* 47 */           STBImage.stbi_image_free(image);
/* 48 */           if (stack != null) stack.close();  } catch (Throwable throwable) { if (stack != null)
/* 49 */             try { stack.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  MemoryUtil.memFree(data);
/* 50 */         if (inputStream != null) inputStream.close();  } catch (Throwable throwable) { if (inputStream != null)
/* 51 */           try { inputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 52 */     { e.printStackTrace(); }
/*    */   
/*    */   }
/*    */   
/*    */   public boolean needsRotate() {
/* 57 */     return this.needsRotate;
/*    */   }
/*    */   
/*    */   public static byte[] downloadHead(String url) throws IOException {
/*    */     BufferedImage skin;
/* 62 */     InputStream in = Http.get(url).sendInputStream(); 
/* 63 */     try { skin = ImageIO.read(in);
/* 64 */       if (in != null) in.close();  } catch (Throwable throwable) { if (in != null)
/*    */         try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 66 */      if (skin == null) throw new IOException("Failed to decode skin image.");
/*    */     
/* 68 */     byte[] head = new byte[256];
/* 69 */     int[] pixel = new int[4];
/*    */     
/* 71 */     int i = 0; int x;
/* 72 */     for (x = 8; x < 16; x++) {
/* 73 */       for (int y = 8; y < 16; y++) {
/* 74 */         skin.getData().getPixel(x, y, pixel);
/*    */         
/* 76 */         for (int j = 0; j < 4; j++) {
/* 77 */           head[i++] = (byte)pixel[j];
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 82 */     i = 0;
/* 83 */     for (x = 40; x < 48; x++) {
/* 84 */       for (int y = 8; y < 16; y++) {
/* 85 */         skin.getData().getPixel(x, y, pixel);
/*    */         
/* 87 */         if (pixel[3] != 0) {
/* 88 */           for (int j = 0; j < 4; j++) {
/* 89 */             head[i++] = (byte)pixel[j];
/*    */           }
/*    */         } else {
/* 92 */           i += 4;
/*    */         } 
/*    */       } 
/*    */     } 
/* 96 */     return head;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\PlayerHeadTexture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */