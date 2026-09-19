/*    */ package meteordevelopment.meteorclient.gui.widgets.pressable;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import net.minecraft.class_11909;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class WPressable
/*    */   extends WWidget
/*    */ {
/*    */   public Runnable action;
/*    */   protected boolean pressed;
/*    */   
/*    */   public boolean onMouseClicked(class_11909 click, boolean doubled) {
/* 21 */     if (this.mouseOver && (click.method_74245() == 0 || click.method_74245() == 1)) this.pressed = true; 
/* 22 */     return this.pressed;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onMouseReleased(class_11909 click) {
/* 27 */     if (this.pressed) {
/* 28 */       onPressed(click.method_74245());
/* 29 */       if (this.action != null) this.action.run();
/*    */       
/* 31 */       this.pressed = false;
/*    */     } 
/*    */     
/* 34 */     return false;
/*    */   }
/*    */   
/*    */   protected void onPressed(int button) {}
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\gui\widgets\pressable\WPressable.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */