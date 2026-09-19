/*    */ package meteordevelopment.meteorclient.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.VertexFormatElement;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MeteorVertexFormatElements
/*    */ {
/* 11 */   public static final VertexFormatElement POS2 = VertexFormatElement.register(getNextVertexFormatElementId(), 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.POSITION, 2);
/*    */ 
/*    */ 
/*    */   
/*    */   private static int getNextVertexFormatElementId() {
/* 16 */     int id = 0;
/*    */     
/* 18 */     while (VertexFormatElement.byId(id) != null) {
/* 19 */       id++;
/*    */       
/* 21 */       if (id >= 32) {
/* 22 */         throw new RuntimeException("Too many mods registering VertexFormatElements");
/*    */       }
/*    */     } 
/*    */     
/* 26 */     return id;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\renderer\MeteorVertexFormatElements.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */