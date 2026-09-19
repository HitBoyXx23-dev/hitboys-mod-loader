/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_5632;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TooltipDataEvent
/*    */ {
/* 13 */   private static final TooltipDataEvent INSTANCE = new TooltipDataEvent();
/*    */   
/*    */   public class_5632 tooltipData;
/*    */   public class_1799 itemStack;
/*    */   
/*    */   public static TooltipDataEvent get(class_1799 itemStack) {
/* 19 */     INSTANCE.tooltipData = null;
/* 20 */     INSTANCE.itemStack = itemStack;
/* 21 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\TooltipDataEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */