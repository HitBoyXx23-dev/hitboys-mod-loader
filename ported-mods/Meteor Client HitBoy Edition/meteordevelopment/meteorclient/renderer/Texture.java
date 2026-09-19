/*    */ package meteordevelopment.meteorclient.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.TextureUtil;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import com.mojang.blaze3d.textures.AddressMode;
/*    */ import com.mojang.blaze3d.textures.FilterMode;
/*    */ import com.mojang.blaze3d.textures.TextureFormat;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import net.minecraft.class_1011;
/*    */ import net.minecraft.class_1044;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.stb.STBImage;
/*    */ import org.lwjgl.system.MemoryStack;
/*    */ import org.lwjgl.system.MemoryUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Texture
/*    */   extends class_1044
/*    */ {
/*    */   public Texture(int width, int height, TextureFormat format, FilterMode min, FilterMode mag) {
/* 27 */     this.field_56974 = RenderSystem.getDevice().createTexture("", 15, format, width, height, 1, 1);
/* 28 */     this.field_63613 = RenderSystem.getSamplerCache().method_75293(AddressMode.REPEAT, AddressMode.REPEAT, min, mag, false);
/*    */     
/* 30 */     this.field_60597 = RenderSystem.getDevice().createTextureView(this.field_56974);
/*    */   }
/*    */   
/*    */   public int getWidth() {
/* 34 */     return method_68004().getWidth(0);
/*    */   }
/*    */   
/*    */   public int getHeight() {
/* 38 */     return method_68004().getHeight(0);
/*    */   }
/*    */   
/*    */   public void upload(byte[] bytes) {
/* 42 */     upload(BufferUtils.createByteBuffer(bytes.length).put(bytes));
/*    */   }
/*    */   
/*    */   public void upload(ByteBuffer buffer) {
/* 46 */     class_1011 image = getImage();
/*    */     
/* 48 */     buffer.rewind();
/* 49 */     MemoryUtil.memCopy(MemoryUtil.memAddress(buffer), image.method_67769(), buffer.remaining());
/*    */     
/* 51 */     RenderSystem.getDevice().createCommandEncoder().writeToTexture(this.field_56974, image);
/*    */     
/* 53 */     image.close();
/*    */   }
/*    */   @NotNull
/*    */   private class_1011 getImage() {
/* 57 */     switch (this.field_56974.getFormat()) { case RGBA8:
/*    */       
/*    */       case RED8:
/* 60 */        }  throw new IllegalArgumentException();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Texture readResource(String path, boolean flipY, FilterMode filter) {
/*    */     
/* 69 */     try { InputStream in = Texture.class.getResourceAsStream(path); 
/* 70 */       try { if (in == null) { Texture texture = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 90 */           if (in != null) in.close();  return texture; }  ByteBuffer data = TextureUtil.readResource(in).rewind(); MemoryStack stack = MemoryStack.stackPush(); try { IntBuffer width = stack.mallocInt(1); IntBuffer height = stack.mallocInt(1); IntBuffer comp = stack.mallocInt(1); STBImage.stbi_set_flip_vertically_on_load(flipY); ByteBuffer image = STBImage.stbi_load_from_memory(data, width, height, comp, 4); Texture texture = new Texture(width.get(0), height.get(0), TextureFormat.RGBA8, filter, filter); texture.upload(image); STBImage.stbi_image_free(image); STBImage.stbi_set_flip_vertically_on_load(false); Texture texture1 = texture; if (stack != null) stack.close();  if (in != null) in.close();  return texture1; } catch (Throwable throwable) { if (stack != null) try { stack.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (in != null) try { in.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException e)
/* 91 */     { return null; }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\Texture.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */