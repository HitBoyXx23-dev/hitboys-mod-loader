/*     */ package meteordevelopment.meteorclient.systems.hud.elements.keyboard;
/*     */ 
/*     */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*     */ import net.minecraft.class_304;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LayoutContext
/*     */ {
/*     */   final double keyUnit;
/*     */   final double keyGap;
/*     */   final double step;
/*     */   final double functionRowGap;
/*     */   
/*     */   LayoutContext(double keyUnit, double keyGap, double functionRowGap) {
/*  19 */     this.keyUnit = keyUnit;
/*  20 */     this.keyGap = keyGap;
/*  21 */     this.step = keyUnit + keyGap;
/*  22 */     this.functionRowGap = functionRowGap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double ux(double units) {
/*  32 */     return units * this.step;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double y(double rows) {
/*  43 */     return rows * this.step;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double uy(double rows) {
/*  54 */     return rows * this.step + ((rows > 0.0D) ? this.functionRowGap : 0.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double px(KeyDimensions d) {
/*  64 */     return d.toPixels(this.keyUnit, this.keyGap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KeyboardHud.Key key(Keybind kb, double x, double y) {
/*  73 */     return new KeyboardHud.Key(kb, null, x, y, px(KeyDimensions.STANDARD), px(KeyDimensions.STANDARD));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KeyboardHud.Key key(Keybind kb, double x, double y, KeyDimensions w) {
/*  80 */     return new KeyboardHud.Key(kb, null, x, y, px(w), px(KeyDimensions.STANDARD));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KeyboardHud.Key key(Keybind kb, double x, double y, KeyDimensions w, KeyDimensions h) {
/*  87 */     return new KeyboardHud.Key(kb, null, x, y, px(w), px(h));
/*     */   }
/*     */   
/*     */   KeyboardHud.Key keyNamed(Keybind kb, String name, double x, double y, KeyDimensions w) {
/*  91 */     return new KeyboardHud.Key(kb, name, x, y, px(w), px(KeyDimensions.STANDARD));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KeyboardHud.Key key(class_304 kb, double x, double y) {
/* 100 */     return new KeyboardHud.Key(kb, null, x, y, px(KeyDimensions.STANDARD), px(KeyDimensions.STANDARD));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KeyboardHud.Key key(class_304 kb, double x, double y, KeyDimensions w) {
/* 107 */     return new KeyboardHud.Key(kb, null, x, y, px(w), px(KeyDimensions.STANDARD));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   KeyboardHud.Key key(class_304 kb, String name, double x, double y) {
/* 114 */     return new KeyboardHud.Key(kb, name, x, y, px(KeyDimensions.STANDARD), px(KeyDimensions.STANDARD));
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\hud\elements\keyboard\LayoutContext.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */