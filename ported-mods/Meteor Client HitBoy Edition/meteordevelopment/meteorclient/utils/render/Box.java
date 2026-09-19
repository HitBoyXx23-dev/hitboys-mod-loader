/*    */ package meteordevelopment.meteorclient.utils.render;
/*    */ 
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Box
/*    */ {
/*    */   public double x;
/*    */   public double y;
/*    */   public double width;
/*    */   public double height;
/*    */   
/*    */   public Box(double x, double y, double width, double height) {
/* 15 */     this.x = x;
/* 16 */     this.y = y;
/* 17 */     this.width = width;
/* 18 */     this.height = height;
/*    */   }
/*    */   
/*    */   public Box() {
/* 22 */     this(0.0D, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 27 */     if (this == o) return true; 
/* 28 */     if (o == null || getClass() != o.getClass()) return false; 
/* 29 */     Box box = (Box)o;
/* 30 */     return (Double.compare(box.x, this.x) == 0 && 
/* 31 */       Double.compare(box.y, this.y) == 0 && 
/* 32 */       Double.compare(box.width, this.width) == 0 && 
/* 33 */       Double.compare(box.height, this.height) == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 38 */     return Objects.hash(new Object[] { Double.valueOf(this.x), Double.valueOf(this.y), Double.valueOf(this.width), Double.valueOf(this.height) });
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\render\Box.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */