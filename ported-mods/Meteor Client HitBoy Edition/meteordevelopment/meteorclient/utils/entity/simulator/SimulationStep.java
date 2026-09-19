/*    */ package meteordevelopment.meteorclient.utils.entity.simulator;
/*    */ 
/*    */ import net.minecraft.class_239;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SimulationStep
/*    */ {
/* 11 */   public static final SimulationStep MISS = new SimulationStep(true, new class_239[0]);
/*    */   
/*    */   public boolean shouldStop;
/*    */   public class_239[] hitResults;
/*    */   
/*    */   public SimulationStep(boolean stop, class_239... hitResults) {
/* 17 */     this.shouldStop = stop;
/* 18 */     this.hitResults = hitResults;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\entity\simulator\SimulationStep.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */