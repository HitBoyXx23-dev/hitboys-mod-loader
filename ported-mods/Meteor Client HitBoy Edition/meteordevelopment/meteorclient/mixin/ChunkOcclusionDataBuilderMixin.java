/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.world.ChunkOcclusionEvent;
/*    */ import meteordevelopment.orbit.ICancellable;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_852;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_852.class})
/*    */ public abstract class ChunkOcclusionDataBuilderMixin
/*    */ {
/*    */   @Inject(method = {"method_3682"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onMarkClosed(class_2338 pos, CallbackInfo info) {
/* 21 */     ChunkOcclusionEvent event = (ChunkOcclusionEvent)MeteorClient.EVENT_BUS.post((ICancellable)ChunkOcclusionEvent.get());
/* 22 */     if (event.isCancelled()) info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ChunkOcclusionDataBuilderMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */