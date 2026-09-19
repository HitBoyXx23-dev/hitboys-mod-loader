/*    */ package meteordevelopment.meteorclient.utils.tooltip;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.utils.render.RenderUtils;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_10799;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_327;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_5684;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ContainerTooltipComponent
/*    */   implements class_5684, MeteorTooltipData
/*    */ {
/* 19 */   private static final class_2960 TEXTURE_CONTAINER_BACKGROUND = MeteorClient.identifier("textures/container.png");
/*    */   
/*    */   private final class_1799[] items;
/*    */   private final Color color;
/*    */   
/*    */   public ContainerTooltipComponent(class_1799[] items, Color color) {
/* 25 */     this.items = items;
/* 26 */     this.color = color;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_5684 getComponent() {
/* 31 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_32661(class_327 textRenderer) {
/* 36 */     return 67;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_32664(class_327 textRenderer) {
/* 41 */     return 176;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void method_32666(class_327 textRenderer, int x, int y, int width, int height, class_332 context) {
/* 47 */     context.method_25291(class_10799.field_56883, TEXTURE_CONTAINER_BACKGROUND, x, y, 0.0F, 0.0F, 176, 67, 176, 67, this.color.getPacked());
/*    */ 
/*    */     
/* 50 */     int row = 0;
/* 51 */     int i = 0;
/*    */     
/* 53 */     for (class_1799 itemStack : this.items) {
/* 54 */       RenderUtils.drawItem(context, itemStack, x + 8 + i * 18, y + 7 + row * 18, 1.0F, true, null, false);
/*    */       
/* 56 */       i++;
/* 57 */       if (i >= 9) {
/* 58 */         i = 0;
/* 59 */         row++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\tooltip\ContainerTooltipComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */