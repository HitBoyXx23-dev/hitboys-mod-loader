/*    */ package meteordevelopment.meteorclient.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.GpuBuffer;
/*    */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*    */ import com.mojang.blaze3d.systems.GpuDevice;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.nio.ByteBuffer;
/*    */ import net.minecraft.class_11280;
/*    */ import net.minecraft.class_11285;
/*    */ import net.minecraft.class_3532;
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
/*    */ public class FixedUniformStorage<T extends class_11280.class_11281>
/*    */ {
/*    */   private final class_11285 buffer;
/*    */   private final int blockSize;
/*    */   private final int capacity;
/*    */   private int size;
/*    */   
/*    */   public FixedUniformStorage(String name, int blockSize, int capacity) {
/* 31 */     GpuDevice gpuDevice = RenderSystem.getDevice();
/* 32 */     this.blockSize = class_3532.method_28139(blockSize, gpuDevice.getUniformOffsetAlignment());
/* 33 */     this.capacity = capacity;
/* 34 */     int alignedCapacity = class_3532.method_15339(capacity);
/* 35 */     this.size = 0;
/* 36 */     this.buffer = new class_11285(() -> name + " x" + name, 130, this.blockSize * alignedCapacity);
/*    */   }
/*    */   
/*    */   public GpuBufferSlice write(T value) {
/* 40 */     if (this.size >= this.capacity) {
/* 41 */       throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", new Object[] { Integer.valueOf(this.size), Integer.valueOf(this.capacity) }));
/*    */     }
/* 43 */     int i = this.size * this.blockSize;
/* 44 */     GpuBufferSlice slice = this.buffer.method_71119().slice(i, this.blockSize);
/*    */ 
/*    */ 
/*    */     
/* 48 */     GpuBuffer.MappedView mappedView = RenderSystem.getDevice().createCommandEncoder().mapBuffer(slice, false, true); 
/* 49 */     try { value.method_71104(mappedView.data());
/* 50 */       if (mappedView != null) mappedView.close();  } catch (Throwable throwable) { if (mappedView != null)
/*    */         try { mappedView.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 52 */      this.size++;
/* 53 */     return slice;
/*    */   }
/*    */ 
/*    */   
/*    */   public GpuBufferSlice[] writeAll(T[] values) {
/* 58 */     if (values.length == 0)
/* 59 */       return new GpuBufferSlice[0]; 
/* 60 */     if (this.size + values.length > this.capacity) {
/* 61 */       throw new IndexOutOfBoundsException(String.format("Index %s out of bounds for length %s", new Object[] { Integer.valueOf(this.size + values.length - 1), Integer.valueOf(this.capacity) }));
/*    */     }
/* 63 */     int i = this.size * this.blockSize;
/* 64 */     GpuBufferSlice[] gpuBufferSlices = new GpuBufferSlice[values.length];
/* 65 */     GpuBuffer ubo = this.buffer.method_71119();
/*    */ 
/*    */ 
/*    */     
/* 69 */     GpuBuffer.MappedView mappedView = RenderSystem.getDevice().createCommandEncoder().mapBuffer(ubo.slice(i, (values.length * this.blockSize)), false, true); 
/* 70 */     try { ByteBuffer byteBuffer = mappedView.data();
/*    */       
/* 72 */       for (int j = 0; j < values.length; j++) {
/* 73 */         T uploadable = values[j];
/* 74 */         gpuBufferSlices[j] = ubo.slice((i + j * this.blockSize), this.blockSize);
/* 75 */         byteBuffer.position(j * this.blockSize);
/* 76 */         uploadable.method_71104(byteBuffer);
/*    */       } 
/* 78 */       if (mappedView != null) mappedView.close();  } catch (Throwable throwable) { if (mappedView != null)
/*    */         try { mappedView.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 80 */      this.size += values.length;
/* 81 */     return gpuBufferSlices;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 86 */     this.size = 0;
/* 87 */     this.buffer.method_71121();
/*    */   }
/*    */   
/*    */   public void close() {
/* 91 */     this.buffer.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\FixedUniformStorage.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */