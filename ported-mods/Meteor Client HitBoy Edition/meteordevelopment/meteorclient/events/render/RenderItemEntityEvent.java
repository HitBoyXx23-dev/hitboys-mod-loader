/*    */ package meteordevelopment.meteorclient.events.render;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.Cancellable;
/*    */ import meteordevelopment.meteorclient.mixininterface.IEntityRenderState;
/*    */ import net.minecraft.class_10039;
/*    */ import net.minecraft.class_10442;
/*    */ import net.minecraft.class_11659;
/*    */ import net.minecraft.class_1542;
/*    */ import net.minecraft.class_4587;
/*    */ import net.minecraft.class_4597;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenderItemEntityEvent
/*    */   extends Cancellable
/*    */ {
/* 19 */   private static final RenderItemEntityEvent INSTANCE = new RenderItemEntityEvent();
/*    */   
/*    */   public class_1542 itemEntity;
/*    */   
/*    */   public class_10039 renderState;
/*    */   public float tickDelta;
/*    */   public class_4587 matrixStack;
/*    */   public class_4597 vertexConsumerProvider;
/*    */   public int light;
/*    */   public class_10442 itemModelManager;
/*    */   public class_11659 renderCommandQueue;
/*    */   
/*    */   public static RenderItemEntityEvent get(class_10039 renderState, float tickDelta, class_4587 matrixStack, class_4597 vertexConsumerProvider, int light, class_10442 itemModelManager, class_11659 renderCommandQueue) {
/* 32 */     INSTANCE.setCancelled(false);
/* 33 */     INSTANCE.itemEntity = (class_1542)((IEntityRenderState)renderState).meteor$getEntity();
/* 34 */     INSTANCE.renderState = renderState;
/* 35 */     INSTANCE.tickDelta = tickDelta;
/* 36 */     INSTANCE.matrixStack = matrixStack;
/* 37 */     INSTANCE.vertexConsumerProvider = vertexConsumerProvider;
/* 38 */     INSTANCE.light = light;
/* 39 */     INSTANCE.itemModelManager = itemModelManager;
/* 40 */     INSTANCE.renderCommandQueue = renderCommandQueue;
/* 41 */     return INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\events\render\RenderItemEntityEvent.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */