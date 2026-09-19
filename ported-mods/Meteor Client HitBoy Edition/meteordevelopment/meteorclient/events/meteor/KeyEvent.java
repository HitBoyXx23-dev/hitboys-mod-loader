/*    */ package meteordevelopment.meteorclient.events.meteor;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
/*    */ import net.minecraft.class_11908;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KeyEvent
/*    */   extends Cancellable
/*    */ {
/* 13 */   private static final KeyEvent INSTANCE = new KeyEvent();
/*    */   
/*    */   public class_11908 input;
/*    */   public KeyAction action;
/*    */   
/*    */   public static KeyEvent get(class_11908 input, KeyAction action) {
/* 19 */     INSTANCE.setCancelled(false);
/* 20 */     INSTANCE.input = input;
/* 21 */     INSTANCE.action = action;
/* 22 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public int key() {
/* 26 */     return INSTANCE.input.comp_4795();
/*    */   }
/*    */   
/*    */   public int modifiers() {
/* 30 */     return INSTANCE.input.comp_4797();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\meteor\KeyEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */