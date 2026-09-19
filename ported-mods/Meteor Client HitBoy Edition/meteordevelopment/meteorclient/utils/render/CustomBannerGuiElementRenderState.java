/*    */ package meteordevelopment.meteorclient.utils.render;
/*    */ 
/*    */ import net.minecraft.class_10377;
/*    */ import net.minecraft.class_1767;
/*    */ import net.minecraft.class_8030;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public final class CustomBannerGuiElementRenderState extends Record implements class_11256 {
/*    */   private final class_10377 flag;
/*    */   private final class_1767 baseColor;
/*    */   private final class_9307 resultBannerPatterns;
/*    */   private final int x1;
/*    */   private final int y1;
/*    */   
/* 15 */   public float comp_4133() { return this.scale; } private final int x2; private final int y2; @Nullable private final class_8030 scissorArea; @Nullable private final class_8030 bounds; private final float scale; @Nullable public class_8030 comp_4274() { return this.bounds; } @Nullable public class_8030 comp_4128() { return this.scissorArea; } public int comp_4125() { return this.y2; } public int comp_4124() { return this.x2; } public int comp_4123() { return this.y1; } public int comp_4122() { return this.x1; } public class_9307 resultBannerPatterns() { return this.resultBannerPatterns; } public class_1767 baseColor() { return this.baseColor; } public class_10377 flag() { return this.flag; } public final boolean equals(Object o) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/render/CustomBannerGuiElementRenderState;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/render/CustomBannerGuiElementRenderState;
/* 15 */     //   0	8	1	o	Ljava/lang/Object; } public CustomBannerGuiElementRenderState(class_10377 flag, class_1767 baseColor, class_9307 resultBannerPatterns, int x1, int y1, int x2, int y2, @Nullable class_8030 scissorArea, @Nullable class_8030 bounds, float scale) { this.flag = flag; this.baseColor = baseColor; this.resultBannerPatterns = resultBannerPatterns; this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2; this.scissorArea = scissorArea; this.bounds = bounds; this.scale = scale; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/render/CustomBannerGuiElementRenderState;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/CustomBannerGuiElementRenderState;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/render/CustomBannerGuiElementRenderState;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #15	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/render/CustomBannerGuiElementRenderState;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CustomBannerGuiElementRenderState(class_10377 bannerFlagBlockModel, class_1767 color, class_9307 bannerPatterns, int x1, int y1, int x2, int y2, @Nullable class_8030 scissorArea, float scale) {
/* 38 */     this(bannerFlagBlockModel, color, bannerPatterns, x1, y1, x2, y2, scissorArea, class_11256.method_71535(x1, y1, x2, y2, scissorArea), scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\CustomBannerGuiElementRenderState.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */