/*    */ package meteordevelopment.meteorclient.utils.misc;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2680;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MBlockPos
/*    */ {
/* 15 */   private static final class_2338.class_2339 POS = new class_2338.class_2339();
/*    */   
/*    */   public int x;
/*    */   
/*    */   public int y;
/*    */   
/*    */   public MBlockPos(class_1297 entity) {
/* 22 */     set(entity);
/*    */   } public int z;
/*    */   public MBlockPos() {}
/*    */   public MBlockPos set(int x, int y, int z) {
/* 26 */     this.x = x;
/* 27 */     this.y = y;
/* 28 */     this.z = z;
/* 29 */     return this;
/*    */   }
/*    */   public MBlockPos set(MBlockPos pos) {
/* 32 */     return set(pos.x, pos.y, pos.z);
/*    */   }
/*    */   public MBlockPos set(class_1297 entity) {
/* 35 */     return set(entity.method_31477(), entity.method_31478(), entity.method_31479());
/*    */   }
/*    */   
/*    */   public MBlockPos coerceBlockLevel(class_1297 entity) {
/* 39 */     return set(entity.method_31477(), (int)Math.round(entity.method_23318()), entity.method_31479());
/*    */   }
/*    */   
/*    */   public MBlockPos offset(HorizontalDirection dir, int amount) {
/* 43 */     this.x += dir.offsetX * amount;
/* 44 */     this.z += dir.offsetZ * amount;
/* 45 */     return this;
/*    */   }
/*    */   public MBlockPos offset(HorizontalDirection dir) {
/* 48 */     return offset(dir, 1);
/*    */   }
/*    */   
/*    */   public MBlockPos add(int x, int y, int z) {
/* 52 */     this.x += x;
/* 53 */     this.y += y;
/* 54 */     this.z += z;
/* 55 */     return this;
/*    */   }
/*    */   
/*    */   public class_2338 getBlockPos() {
/* 59 */     return (class_2338)POS.method_10103(this.x, this.y, this.z);
/*    */   }
/*    */   
/*    */   public class_2680 getState() {
/* 63 */     return MeteorClient.mc.field_1687.method_8320(getBlockPos());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 68 */     if (this == o) return true; 
/* 69 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 71 */     MBlockPos mBlockPos = (MBlockPos)o;
/*    */     
/* 73 */     if (this.x != mBlockPos.x) return false; 
/* 74 */     if (this.y != mBlockPos.y) return false; 
/* 75 */     return (this.z == mBlockPos.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 80 */     int result = this.x;
/* 81 */     result = 31 * result + this.y;
/* 82 */     result = 31 * result + this.z;
/* 83 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 88 */     return "" + this.x + ", " + this.x + ", " + this.y;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\misc\MBlockPos.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */