/*    */ package meteordevelopment.meteorclient.utils.tooltip;
/*    */ 
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_5481;
/*    */ import net.minecraft.class_5683;
/*    */ import net.minecraft.class_5684;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TextTooltipComponent
/*    */   extends class_5683
/*    */   implements MeteorTooltipData
/*    */ {
/*    */   public TextTooltipComponent(class_5481 text) {
/* 15 */     super(text);
/*    */   }
/*    */   
/*    */   public TextTooltipComponent(class_2561 text) {
/* 19 */     this(text.method_30937());
/*    */   }
/*    */ 
/*    */   
/*    */   public class_5684 getComponent() {
/* 24 */     return (class_5684)this;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\tooltip\TextTooltipComponent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */