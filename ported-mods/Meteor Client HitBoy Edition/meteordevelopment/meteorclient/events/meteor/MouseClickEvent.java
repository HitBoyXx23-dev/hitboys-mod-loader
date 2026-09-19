/*    */ package meteordevelopment.meteorclient.events.meteor;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
/*    */ import net.minecraft.class_11909;
/*    */ import net.minecraft.class_11910;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseClickEvent
/*    */   extends Cancellable
/*    */ {
/* 14 */   private static final MouseClickEvent INSTANCE = new MouseClickEvent();
/*    */   
/*    */   public class_11909 click;
/*    */   public class_11910 input;
/*    */   public KeyAction action;
/*    */   
/*    */   public static MouseClickEvent get(class_11909 click, KeyAction action) {
/* 21 */     INSTANCE.setCancelled(false);
/* 22 */     INSTANCE.click = click;
/* 23 */     INSTANCE.input = click.comp_4800();
/* 24 */     INSTANCE.action = action;
/* 25 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public int button() {
/* 29 */     return INSTANCE.input.comp_4801();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\meteor\MouseClickEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */