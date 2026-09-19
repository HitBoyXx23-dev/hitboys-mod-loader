/*    */ package meteordevelopment.meteorclient.utils.render.postprocess;
/*    */ 
/*    */ import meteordevelopment.meteorclient.renderer.MeshRenderer;
/*    */ import meteordevelopment.meteorclient.renderer.MeteorRenderPipelines;
/*    */ import meteordevelopment.meteorclient.renderer.ShapeMode;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.ESP;
/*    */ import net.minecraft.class_1297;
/*    */ 
/*    */ public class EntityOutlineShader
/*    */   extends EntityShader {
/*    */   public EntityOutlineShader() {
/* 13 */     super(MeteorRenderPipelines.POST_OUTLINE);
/*    */   }
/*    */   private static ESP esp;
/*    */   
/*    */   protected boolean shouldDraw() {
/* 18 */     if (esp == null) esp = (ESP)Modules.get().get(ESP.class); 
/* 19 */     return esp.isShader();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldDraw(class_1297 entity) {
/* 24 */     if (!shouldDraw()) return false; 
/* 25 */     return !esp.shouldSkip(entity);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupPass(MeshRenderer renderer) {
/* 30 */     renderer.uniform("OutlineData", OutlineUniforms.write(((Integer)esp.outlineWidth
/* 31 */           .get()).intValue(), ((Double)esp.fillOpacity
/* 32 */           .get()).floatValue(), ((ShapeMode)esp.shapeMode
/* 33 */           .get()).ordinal(), ((Double)esp.glowMultiplier
/* 34 */           .get()).floatValue()));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\postprocess\EntityOutlineShader.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */