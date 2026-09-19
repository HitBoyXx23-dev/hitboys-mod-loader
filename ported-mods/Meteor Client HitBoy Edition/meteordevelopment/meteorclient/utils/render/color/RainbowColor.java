/*    */ package meteordevelopment.meteorclient.utils.render.color;
/*    */ 
/*    */ import java.awt.Color;
/*    */ 
/*    */ 
/*    */ public class RainbowColor
/*    */   extends Color
/*    */ {
/*    */   private double speed;
/* 10 */   private static final float[] hsb = new float[3];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getSpeed() {
/* 17 */     return this.speed;
/*    */   }
/*    */   
/*    */   public RainbowColor setSpeed(double speed) {
/* 21 */     this.speed = speed;
/* 22 */     return this;
/*    */   }
/*    */   
/*    */   public RainbowColor getNext() {
/* 26 */     return getNext(1.0D);
/*    */   }
/*    */   
/*    */   public RainbowColor getNext(double delta) {
/* 30 */     if (this.speed > 0.0D) {
/* 31 */       Color.RGBtoHSB(this.r, this.g, this.b, hsb);
/* 32 */       int c = Color.HSBtoRGB(hsb[0] + (float)(this.speed * delta), 1.0F, 1.0F);
/*    */       
/* 34 */       this.r = toRGBAR(c);
/* 35 */       this.g = toRGBAG(c);
/* 36 */       this.b = toRGBAB(c);
/*    */     } 
/* 38 */     return this;
/*    */   }
/*    */   
/*    */   public RainbowColor set(RainbowColor color) {
/* 42 */     this.r = color.r;
/* 43 */     this.g = color.g;
/* 44 */     this.b = color.b;
/* 45 */     this.a = color.a;
/* 46 */     this.speed = color.speed;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 52 */     if (this == o) return true; 
/* 53 */     if (o == null || getClass() != o.getClass()) return false; 
/* 54 */     if (!super.equals(o)) return false;
/*    */     
/* 56 */     return (Double.compare(((RainbowColor)o).speed, this.speed) == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 61 */     int result = super.hashCode();
/*    */     
/* 63 */     long temp = Double.doubleToLongBits(this.speed);
/* 64 */     result = 31 * result + (int)(temp ^ temp >>> 32L);
/* 65 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\color\RainbowColor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */