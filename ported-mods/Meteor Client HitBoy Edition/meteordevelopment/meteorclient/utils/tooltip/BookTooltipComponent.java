/*    */ package meteordevelopment.meteorclient.utils.tooltip;
/*    */ 
/*    */ import net.minecraft.class_10799;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_327;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_5348;
/*    */ import net.minecraft.class_5481;
/*    */ import net.minecraft.class_5684;
/*    */ import org.joml.Matrix3x2fStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BookTooltipComponent
/*    */   implements class_5684, MeteorTooltipData
/*    */ {
/* 18 */   private static final class_2960 TEXTURE_BOOK_BACKGROUND = class_2960.method_60654("textures/gui/book.png");
/*    */   
/*    */   private final class_2561 page;
/*    */   
/*    */   public BookTooltipComponent(class_2561 page) {
/* 23 */     this.page = page;
/*    */   }
/*    */ 
/*    */   
/*    */   public class_5684 getComponent() {
/* 28 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_32661(class_327 textRenderer) {
/* 33 */     return 134;
/*    */   }
/*    */ 
/*    */   
/*    */   public int method_32664(class_327 textRenderer) {
/* 38 */     return 112;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void method_32666(class_327 textRenderer, int x, int y, int width, int height, class_332 context) {
/* 44 */     context.method_25290(class_10799.field_56883, TEXTURE_BOOK_BACKGROUND, x - 10, y, 0.0F, 0.0F, 128, 128, 179, 179);
/*    */ 
/*    */     
/* 47 */     Matrix3x2fStack matrices = context.method_51448();
/* 48 */     matrices.pushMatrix();
/* 49 */     matrices.translate((x + 16), (y + 12));
/* 50 */     matrices.scale(0.7F, 0.7F);
/* 51 */     int offset = 0;
/* 52 */     for (class_5481 line : textRenderer.method_1728((class_5348)this.page, 112)) {
/* 53 */       context.method_51430(textRenderer, line, 0, offset, -16777216, false);
/* 54 */       offset += 8;
/*    */     } 
/* 56 */     matrices.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\tooltip\BookTooltipComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */