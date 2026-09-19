/*    */ package meteordevelopment.meteorclient.mixin.sodium;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexFormat;
/*    */ import com.mojang.blaze3d.vertex.VertexFormatElement;
/*    */ import meteordevelopment.meteorclient.utils.render.MeshBuilderVertexConsumerProvider;
/*    */ import net.caffeinemc.mods.sodium.api.vertex.buffer.VertexBufferWriter;
/*    */ import net.minecraft.class_4588;
/*    */ import org.lwjgl.system.MemoryStack;
/*    */ import org.lwjgl.system.MemoryUtil;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(value = {MeshBuilderVertexConsumerProvider.MeshBuilderVertexConsumer.class}, remap = false)
/*    */ public abstract class MeshVertexConsumerMixin
/*    */   implements class_4588, VertexBufferWriter
/*    */ {
/*    */   public void push(MemoryStack stack, long ptr, int count, VertexFormat format) {
/* 21 */     int positionOffset = format.getOffset(VertexFormatElement.POSITION);
/*    */     
/* 23 */     if (positionOffset == -1)
/*    */       return; 
/* 25 */     for (int i = 0; i < count; i++) {
/* 26 */       long positionPtr = ptr + format.getVertexSize() * i + positionOffset;
/*    */       
/* 28 */       float x = MemoryUtil.memGetFloat(positionPtr);
/* 29 */       float y = MemoryUtil.memGetFloat(positionPtr + 4L);
/* 30 */       float z = MemoryUtil.memGetFloat(positionPtr + 8L);
/*    */       
/* 32 */       method_22912(x, y, z);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\sodium\MeshVertexConsumerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */