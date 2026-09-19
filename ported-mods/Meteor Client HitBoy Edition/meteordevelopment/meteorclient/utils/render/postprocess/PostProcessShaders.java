/*    */ package meteordevelopment.meteorclient.utils.render.postprocess;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.render.Render2DEvent;
/*    */ import meteordevelopment.meteorclient.utils.PreInit;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PostProcessShaders
/*    */ {
/*    */   public static EntityShader CHAMS;
/*    */   public static EntityShader ENTITY_OUTLINE;
/*    */   public static PostProcessShader STORAGE_OUTLINE;
/*    */   
/*    */   @PreInit
/*    */   public static void init() {
/* 19 */     CHAMS = new ChamsShader();
/* 20 */     ENTITY_OUTLINE = new EntityOutlineShader();
/* 21 */     STORAGE_OUTLINE = new StorageOutlineShader();
/*    */     
/* 23 */     MeteorClient.EVENT_BUS.subscribe(PostProcessShaders.class);
/*    */   }
/*    */   
/*    */   public static void beginRender() {
/* 27 */     CHAMS.clearTexture();
/* 28 */     ENTITY_OUTLINE.clearTexture();
/* 29 */     STORAGE_OUTLINE.clearTexture();
/*    */   }
/*    */   
/*    */   public static void submitEntityVertices() {
/* 33 */     CHAMS.submitVertices();
/* 34 */     ENTITY_OUTLINE.submitVertices();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private static void onRender(Render2DEvent event) {
/* 39 */     CHAMS.render();
/* 40 */     ENTITY_OUTLINE.render();
/*    */   }
/*    */   
/*    */   public static void onResized(int width, int height) {
/* 44 */     if (MeteorClient.mc == null)
/*    */       return; 
/* 46 */     CHAMS.onResized(width, height);
/* 47 */     ENTITY_OUTLINE.onResized(width, height);
/* 48 */     STORAGE_OUTLINE.onResized(width, height);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\postprocess\PostProcessShaders.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */