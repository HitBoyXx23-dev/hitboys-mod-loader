/*    */ package meteordevelopment.meteorclient.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexFormat;
/*    */ import com.mojang.blaze3d.vertex.VertexFormatElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MeteorVertexFormats
/*    */ {
/* 12 */   public static final VertexFormat POS2 = VertexFormat.builder()
/* 13 */     .add("Position", MeteorVertexFormatElements.POS2)
/* 14 */     .build();
/*    */   
/* 16 */   public static final VertexFormat POS2_COLOR = VertexFormat.builder()
/* 17 */     .add("Position", MeteorVertexFormatElements.POS2)
/* 18 */     .add("Color", VertexFormatElement.COLOR)
/* 19 */     .build();
/*    */   
/* 21 */   public static final VertexFormat POS2_TEXTURE_COLOR = VertexFormat.builder()
/* 22 */     .add("Position", MeteorVertexFormatElements.POS2)
/* 23 */     .add("Texture", VertexFormatElement.UV)
/* 24 */     .add("Color", VertexFormatElement.COLOR)
/* 25 */     .build();
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\MeteorVertexFormats.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */