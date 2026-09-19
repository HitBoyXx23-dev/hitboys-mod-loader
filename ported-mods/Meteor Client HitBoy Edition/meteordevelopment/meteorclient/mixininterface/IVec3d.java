/*    */ package meteordevelopment.meteorclient.mixininterface;
/*    */ 
/*    */ import net.minecraft.class_2382;
/*    */ import net.minecraft.class_243;
/*    */ import org.joml.Vector3d;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IVec3d
/*    */ {
/*    */   class_243 meteor$set(double paramDouble1, double paramDouble2, double paramDouble3);
/*    */   
/*    */   default class_243 meteor$set(class_2382 vec) {
/* 17 */     return meteor$set(vec.method_10263(), vec.method_10264(), vec.method_10260());
/*    */   }
/*    */   
/*    */   default class_243 meteor$set(Vector3d vec) {
/* 21 */     return meteor$set(vec.x, vec.y, vec.z);
/*    */   }
/*    */   
/*    */   default class_243 meteor$set(class_243 pos) {
/* 25 */     return meteor$set(pos.field_1352, pos.field_1351, pos.field_1350);
/*    */   }
/*    */   
/*    */   class_243 meteor$setXZ(double paramDouble1, double paramDouble2);
/*    */   
/*    */   class_243 meteor$setY(double paramDouble);
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixininterface\IVec3d.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */