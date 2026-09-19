/*    */ package meteordevelopment.meteorclient.events.world;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_265;
/*    */ import net.minecraft.class_2680;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CollisionShapeEvent
/*    */   extends Cancellable
/*    */ {
/* 15 */   private static final CollisionShapeEvent INSTANCE = new CollisionShapeEvent();
/*    */   
/*    */   public class_2680 state;
/*    */   public class_2338 pos;
/*    */   public class_265 shape;
/*    */   
/*    */   public static CollisionShapeEvent get(class_2680 state, class_2338 pos, class_265 shape) {
/* 22 */     CollisionShapeEvent event = INSTANCE;
/*    */     
/* 24 */     if (!RenderSystem.isOnRenderThread()) {
/* 25 */       event = new CollisionShapeEvent();
/*    */     }
/*    */     
/* 28 */     event.setCancelled(false);
/* 29 */     event.state = state;
/* 30 */     event.pos = pos;
/* 31 */     event.shape = shape;
/*    */     
/* 33 */     return event;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\world\CollisionShapeEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */