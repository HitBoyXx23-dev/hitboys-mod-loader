/*    */ package meteordevelopment.meteorclient.utils.tooltip;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.BetterTooltips;
/*    */ import net.minecraft.class_10090;
/*    */ import net.minecraft.class_10799;
/*    */ import net.minecraft.class_1806;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_22;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_327;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_5684;
/*    */ import net.minecraft.class_9209;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapTooltipComponent
/*    */   implements class_5684, MeteorTooltipData
/*    */ {
/* 23 */   private static final class_2960 TEXTURE_MAP_BACKGROUND = class_2960.method_60654("textures/map/map_background.png");
/*    */   private final int mapId;
/* 25 */   private final class_10090 mapRenderState = new class_10090();
/*    */   
/*    */   public MapTooltipComponent(int mapId) {
/* 28 */     this.mapId = mapId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_32661(class_327 textRenderer) {
/* 33 */     double scale = ((Double)((BetterTooltips)Modules.get().get(BetterTooltips.class)).mapsScale.get()).doubleValue();
/* 34 */     return (int)(144.0D * scale) + 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_32664(class_327 textRenderer) {
/* 39 */     double scale = ((Double)((BetterTooltips)Modules.get().get(BetterTooltips.class)).mapsScale.get()).doubleValue();
/* 40 */     return (int)(144.0D * scale);
/*    */   }
/*    */ 
/*    */   
/*    */   public class_5684 getComponent() {
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_32666(class_327 textRenderer, int x, int y, int width, int height, class_332 context) {
/* 50 */     float scale = ((Double)((BetterTooltips)Modules.get().get(BetterTooltips.class)).mapsScale.get()).floatValue();
/*    */ 
/*    */     
/* 53 */     int size = (int)(144.0F * scale);
/* 54 */     context.method_25290(class_10799.field_56883, TEXTURE_MAP_BACKGROUND, x, y, 0.0F, 0.0F, size, size, size, size);
/*    */ 
/*    */     
/* 57 */     class_22 mapState = class_1806.method_7997(new class_9209(this.mapId), (class_1937)MeteorClient.mc.field_1687);
/* 58 */     if (mapState == null)
/*    */       return; 
/* 60 */     context.method_51448().pushMatrix();
/* 61 */     context.method_51448().translate(x, y);
/* 62 */     context.method_51448().scale(scale, scale);
/* 63 */     context.method_51448().translate(8.0F, 8.0F);
/*    */     
/* 65 */     MeteorClient.mc.method_61965().method_62230(new class_9209(this.mapId), mapState, this.mapRenderState);
/* 66 */     context.method_70857(this.mapRenderState);
/*    */     
/* 68 */     context.method_51448().popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\tooltip\MapTooltipComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */