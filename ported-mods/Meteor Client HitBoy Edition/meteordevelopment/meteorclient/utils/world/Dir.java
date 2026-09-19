/*    */ package meteordevelopment.meteorclient.utils.world;
/*    */ 
/*    */ import net.minecraft.class_2350;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Dir
/*    */ {
/*    */   public static final byte UP = 2;
/*    */   public static final byte DOWN = 4;
/*    */   public static final byte NORTH = 8;
/*    */   public static final byte SOUTH = 16;
/*    */   public static final byte WEST = 32;
/*    */   public static final byte EAST = 64;
/*    */   
/*    */   public static byte get(class_2350 dir) {
/* 22 */     switch (dir) { default: throw new MatchException(null, null);case field_11036: case field_11033: case field_11043: case field_11035: case field_11039: case field_11034: break; }  return 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 28 */       64;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean is(int dir, byte idk) {
/* 33 */     return ((dir & idk) == idk);
/*    */   }
/*    */   
/*    */   public static boolean isNot(int dir, byte idk) {
/* 37 */     return ((dir & idk) != idk);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\world\Dir.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */