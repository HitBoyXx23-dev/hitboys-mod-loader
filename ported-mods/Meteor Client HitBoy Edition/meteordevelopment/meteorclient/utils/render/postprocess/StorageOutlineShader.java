/*    */ package meteordevelopment.meteorclient.utils.render.postprocess;
/*    */ 
/*    */ import meteordevelopment.meteorclient.renderer.MeshRenderer;
/*    */ import meteordevelopment.meteorclient.renderer.MeteorRenderPipelines;
/*    */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.StorageESP;
/*    */ 
/*    */ public class StorageOutlineShader
/*    */   extends PostProcessShader {
/*    */   public StorageOutlineShader() {
/* 12 */     super(MeteorRenderPipelines.POST_OUTLINE);
/*    */   }
/*    */   private static StorageESP storageESP;
/*    */   
/*    */   protected boolean shouldDraw() {
/* 17 */     if (storageESP == null) storageESP = (StorageESP)Modules.get().get(StorageESP.class); 
/* 18 */     return storageESP.isShader();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupPass(MeshRenderer renderer) {
/* 23 */     renderer.uniform("OutlineData", OutlineUniforms.write(((Integer)storageESP.outlineWidth
/* 24 */           .get()).intValue(), ((Integer)storageESP.fillOpacity
/* 25 */           .get()).intValue() / 255.0F, ((ShapeMode)storageESP.shapeMode
/* 26 */           .get()).ordinal(), ((Double)storageESP.glowMultiplier
/* 27 */           .get()).floatValue()));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\postprocess\StorageOutlineShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */