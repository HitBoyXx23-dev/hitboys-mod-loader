/*    */ package meteordevelopment.meteorclient.systems.modules.render.marker;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
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
/*    */ public class MarkerFactory
/*    */ {
/*    */   private final Map<String, Factory> factories;
/*    */   private final String[] names;
/*    */   
/*    */   public MarkerFactory() {
/* 22 */     this.factories = new HashMap<>();
/* 23 */     this.factories.put("Cuboid", CuboidMarker::new);
/* 24 */     this.factories.put("Sphere-2D", Sphere2dMarker::new);
/*    */     
/* 26 */     this.names = new String[this.factories.size()];
/* 27 */     int i = 0;
/* 28 */     for (String key : this.factories.keySet()) this.names[i++] = key; 
/*    */   }
/*    */   
/*    */   public String[] getNames() {
/* 32 */     return this.names;
/*    */   }
/*    */   
/*    */   public BaseMarker createMarker(String name) {
/* 36 */     if (this.factories.containsKey(name)) {
/* 37 */       BaseMarker marker = ((Factory)this.factories.get(name)).create();
/* 38 */       marker.settings.registerColorSettings(Modules.get().get(Marker.class));
/*    */       
/* 40 */       return marker;
/*    */     } 
/*    */     
/* 43 */     return null;
/*    */   }
/*    */   
/*    */   private static interface Factory {
/*    */     BaseMarker create();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\marker\MarkerFactory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */