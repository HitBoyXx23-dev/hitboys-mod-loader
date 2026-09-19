/*    */ package meteordevelopment.meteorclient.utils.tooltip;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import net.minecraft.class_10017;
/*    */ import net.minecraft.class_10042;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1309;
/*    */ import net.minecraft.class_327;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_5684;
/*    */ import org.joml.Quaternionf;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityTooltipComponent
/*    */   implements MeteorTooltipData, class_5684
/*    */ {
/*    */   protected final class_1309 entity;
/*    */   private static double spin;
/*    */   
/*    */   public EntityTooltipComponent(class_1309 entity) {
/* 23 */     this.entity = entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_5684 getComponent() {
/* 28 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_32661(class_327 textRenderer) {
/* 33 */     return 48;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_32664(class_327 textRenderer) {
/* 38 */     return 64;
/*    */   }
/*    */ 
/*    */   
/*    */   public void method_32666(class_327 textRenderer, int x, int y, int width, int height, class_332 context) {
/* 43 */     class_10042 state = (class_10042)MeteorClient.mc.method_1561().method_3953((class_1297)this.entity).method_62425((class_1297)this.entity, 1.0F);
/*    */     
/* 45 */     state.field_61820 = 15728880;
/* 46 */     state.field_61823.clear();
/* 47 */     state.field_61821 = 0;
/*    */     
/* 49 */     state.field_53446 = (float)(spin % 360.0D);
/* 50 */     state.field_53447 = 0.0F;
/* 51 */     state.field_53448 = 0.0F;
/*    */     
/* 53 */     x += (width - method_32664(null)) / 2;
/* 54 */     y += 4;
/*    */     
/* 56 */     width = method_32664(null);
/* 57 */     height = method_32661(null);
/*    */     
/* 59 */     float scale = Math.max(width, height) / 2.0F * 1.25F;
/* 60 */     Vector3f translation = new Vector3f(0.0F, 0.1F, 0.0F);
/* 61 */     Quaternionf rotation = (new Quaternionf()).rotateZ(3.1415927F);
/*    */     
/* 63 */     context.method_70856((class_10017)state, scale, translation, rotation, null, x, y, x + width, y + height);
/* 64 */     spin += (3.0F * MeteorClient.mc.method_61966().method_60636());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\tooltip\EntityTooltipComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */