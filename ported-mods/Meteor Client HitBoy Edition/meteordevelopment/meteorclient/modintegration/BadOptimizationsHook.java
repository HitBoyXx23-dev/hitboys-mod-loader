/*    */ package meteordevelopment.meteorclient.modintegration;
/*    */ 
/*    */ import java.util.function.BooleanSupplier;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Fullbright;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Xray;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BadOptimizationsHook
/*    */   implements BooleanSupplier
/*    */ {
/*    */   private int lastState;
/*    */   
/*    */   public boolean getAsBoolean() {
/* 23 */     Modules m = Modules.get();
/* 24 */     if (m == null) return false;
/*    */     
/* 26 */     int state = (((Fullbright)m.get(Fullbright.class)).getGamma() ? 1 : 0) | (m.isActive(Xray.class) ? 2 : 0);
/* 27 */     boolean changed = (state != this.lastState);
/* 28 */     this.lastState = state;
/* 29 */     return changed;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\modintegration\BadOptimizationsHook.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */