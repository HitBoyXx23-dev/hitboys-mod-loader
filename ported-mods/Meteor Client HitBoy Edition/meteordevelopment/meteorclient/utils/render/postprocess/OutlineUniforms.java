/*    */ package meteordevelopment.meteorclient.utils.render.postprocess;
/*    */ 
/*    */ import com.mojang.blaze3d.buffers.GpuBufferSlice;
/*    */ import com.mojang.blaze3d.buffers.Std140Builder;
/*    */ import com.mojang.blaze3d.buffers.Std140SizeCalculator;
/*    */ import java.nio.ByteBuffer;
/*    */ import net.minecraft.class_11280;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OutlineUniforms
/*    */ {
/* 16 */   private static final int UNIFORM_SIZE = (new Std140SizeCalculator())
/* 17 */     .putInt()
/* 18 */     .putFloat()
/* 19 */     .putInt()
/* 20 */     .putFloat()
/* 21 */     .get();
/*    */   
/* 23 */   private static final class_11280<Data> STORAGE = new class_11280("Meteor - Outline UBO", UNIFORM_SIZE, 16);
/*    */   
/*    */   public static void flipFrame() {
/* 26 */     STORAGE.method_71100();
/*    */   }
/*    */   
/*    */   public static GpuBufferSlice write(int width, float fillOpacity, int shapeMode, float glowMultiplier) {
/* 30 */     return STORAGE.method_71102(new Data(width, fillOpacity, shapeMode, glowMultiplier));
/*    */   }
/*    */   private static final class Data extends Record implements class_11280.class_11281 { private final int width; private final float fillOpacity; private final int shapeMode; private final float glowMultiplier;
/* 33 */     private Data(int width, float fillOpacity, int shapeMode, float glowMultiplier) { this.width = width; this.fillOpacity = fillOpacity; this.shapeMode = shapeMode; this.glowMultiplier = glowMultiplier; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/render/postprocess/OutlineUniforms$Data;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #33	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 33 */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/OutlineUniforms$Data; } public int width() { return this.width; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/render/postprocess/OutlineUniforms$Data;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #33	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/OutlineUniforms$Data; } public final boolean equals(Object o) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/render/postprocess/OutlineUniforms$Data;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #33	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/OutlineUniforms$Data;
/* 33 */       //   0	8	1	o	Ljava/lang/Object; } public float fillOpacity() { return this.fillOpacity; } public int shapeMode() { return this.shapeMode; } public float glowMultiplier() { return this.glowMultiplier; }
/*    */     
/*    */     public void method_71104(ByteBuffer buffer) {
/* 36 */       Std140Builder.intoBuffer(buffer)
/* 37 */         .putInt(this.width)
/* 38 */         .putFloat(this.fillOpacity)
/* 39 */         .putInt(this.shapeMode)
/* 40 */         .putFloat(this.glowMultiplier);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\postprocess\OutlineUniforms.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */