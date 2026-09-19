/*     */ package meteordevelopment.meteorclient.utils.render.postprocess;
/*     */ 
/*     */ import com.mojang.blaze3d.buffers.Std140Builder;
/*     */ import java.nio.ByteBuffer;
/*     */ import net.minecraft.class_11280;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class UniformData
/*     */   extends Record
/*     */   implements class_11280.class_11281
/*     */ {
/*     */   private final float r;
/*     */   private final float g;
/*     */   private final float b;
/*     */   private final float a;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #126	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #126	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object o) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #126	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/render/postprocess/ChamsShader$UniformData;
/*     */     //   0	8	1	o	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   private UniformData(float r, float g, float b, float a) {
/* 126 */     this.r = r; this.g = g; this.b = b; this.a = a; } public float r() { return this.r; } public float g() { return this.g; } public float b() { return this.b; } public float a() { return this.a; }
/*     */   
/*     */   public void method_71104(ByteBuffer buffer) {
/* 129 */     Std140Builder.intoBuffer(buffer)
/* 130 */       .putVec4(this.r, this.g, this.b, this.a);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\postprocess\ChamsShader$UniformData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */