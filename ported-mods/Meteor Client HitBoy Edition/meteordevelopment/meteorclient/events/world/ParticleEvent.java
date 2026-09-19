/*    */ package meteordevelopment.meteorclient.events.world;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_2394;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleEvent
/*    */   extends Cancellable
/*    */ {
/* 12 */   private static final ParticleEvent INSTANCE = new ParticleEvent();
/*    */   
/*    */   public class_2394 particle;
/*    */   
/*    */   public static ParticleEvent get(class_2394 particle) {
/* 17 */     INSTANCE.setCancelled(false);
/* 18 */     INSTANCE.particle = particle;
/* 19 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\world\ParticleEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */