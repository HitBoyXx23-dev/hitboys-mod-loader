/*    */ package meteordevelopment.meteorclient.utils.misc.input;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.gui.GuiKeyEvents;
/*    */ import meteordevelopment.meteorclient.mixin.KeyBindingAccessor;
/*    */ import meteordevelopment.meteorclient.utils.misc.CursorStyle;
/*    */ import net.minecraft.class_304;
/*    */ import org.lwjgl.glfw.GLFW;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Input
/*    */ {
/* 17 */   private static final boolean[] keys = new boolean[512];
/* 18 */   private static final boolean[] buttons = new boolean[16];
/*    */   
/* 20 */   private static CursorStyle lastCursorStyle = CursorStyle.Default;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setKeyState(int key, boolean pressed) {
/* 26 */     if (key >= 0 && key < keys.length) keys[key] = pressed; 
/*    */   }
/*    */   
/*    */   public static void setButtonState(int button, boolean pressed) {
/* 30 */     if (button >= 0 && button < buttons.length) buttons[button] = pressed; 
/*    */   }
/*    */   
/*    */   public static int getKey(class_304 bind) {
/* 34 */     return ((KeyBindingAccessor)bind).meteor$getKey().method_1444();
/*    */   }
/*    */   
/*    */   public static void setKeyState(class_304 bind, boolean pressed) {
/* 38 */     setKeyState(getKey(bind), pressed);
/*    */   }
/*    */   
/*    */   public static boolean isPressed(class_304 bind) {
/* 42 */     return (isKeyPressed(getKey(bind)) || isButtonPressed(getKey(bind)));
/*    */   }
/*    */   
/*    */   public static boolean isKeyPressed(int key) {
/* 46 */     if (!GuiKeyEvents.canUseKeys) return false;
/*    */     
/* 48 */     if (key == -1) return false; 
/* 49 */     return (key < keys.length && keys[key]);
/*    */   }
/*    */   
/*    */   public static boolean isButtonPressed(int button) {
/* 53 */     if (button == -1) return false; 
/* 54 */     return (button < buttons.length && buttons[button]);
/*    */   }
/*    */   
/*    */   public static void setCursorStyle(CursorStyle style) {
/* 58 */     if (lastCursorStyle != style) {
/* 59 */       GLFW.glfwSetCursor(MeteorClient.mc.method_22683().method_4490(), style.getGlfwCursor());
/* 60 */       lastCursorStyle = style;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static int getModifier(int key) {
/* 65 */     switch (key) { case 340: case 344: case 341: case 345: case 342: case 346: case 343: case 347:  }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 70 */       0;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\input\Input.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */