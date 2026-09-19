/*    */ package meteordevelopment.meteorclient.utils.entity.simulator;
/*    */ 
/*    */ import net.minecraft.class_1299;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class MotionData
/*    */   extends Record
/*    */ {
/*    */   private final float power;
/*    */   private final float roll;
/*    */   private final double gravity;
/*    */   private final float airDrag;
/*    */   private final float waterDrag;
/*    */   private final class_1299<?> entity;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object o) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #56	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lmeteordevelopment/meteorclient/utils/entity/simulator/ProjectileEntitySimulator$MotionData;
/*    */     //   0	8	1	o	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public MotionData(float power, float roll, double gravity, float airDrag, float waterDrag, class_1299<?> entity) {
/* 56 */     this.power = power; this.roll = roll; this.gravity = gravity; this.airDrag = airDrag; this.waterDrag = waterDrag; this.entity = entity; } public float power() { return this.power; } public float roll() { return this.roll; } public double gravity() { return this.gravity; } public float airDrag() { return this.airDrag; } public float waterDrag() { return this.waterDrag; } public class_1299<?> entity() { return this.entity; }
/*    */    public MotionData withPower(float power) {
/* 58 */     return new MotionData(power, roll(), gravity(), airDrag(), waterDrag(), entity());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\entity\simulator\ProjectileEntitySimulator$MotionData.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */