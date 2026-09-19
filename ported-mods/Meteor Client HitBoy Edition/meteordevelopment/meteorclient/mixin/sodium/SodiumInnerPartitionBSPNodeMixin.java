/*    */ package meteordevelopment.meteorclient.mixin.sodium;
/*    */ 
/*    */ import com.llamalad7.mixinextras.expression.Definition;
/*    */ import com.llamalad7.mixinextras.expression.Expression;
/*    */ import com.llamalad7.mixinextras.sugar.Local;
/*    */ import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexEncoder;
/*    */ import org.joml.Vector3fc;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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
/*    */ @Mixin(targets = {"net/caffeinemc/mods/sodium/client/render/chunk/translucent_sorting/bsp_tree/InnerPartitionBSPNode"}, remap = false)
/*    */ public class SodiumInnerPartitionBSPNodeMixin
/*    */ {
/*    */   @Inject(method = {"interpolateAttributes(FLorg/joml/Vector3fc;Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex;Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex;Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex;Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex;Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex;)V"}, at = {@At("MIXINEXTRAS:EXPRESSION")}, cancellable = true)
/*    */   @Definition(id = "splitPlaneEdgeDot", local = {@Local(type = float.class, name = {"splitPlaneEdgeDot"})})
/*    */   @Expression({"splitPlaneEdgeDot == 0.0"})
/*    */   private static void onInterpolateAttributes(float splitDistance, Vector3fc splitPlane, ChunkVertexEncoder.Vertex inside, ChunkVertexEncoder.Vertex outside, ChunkVertexEncoder.Vertex targetA, ChunkVertexEncoder.Vertex targetB, ChunkVertexEncoder.Vertex targetC, CallbackInfo ci, @Local(name = {"splitPlaneEdgeDot"}) float splitPlaneEdgeDot) {
/* 29 */     if (splitPlaneEdgeDot == 0.0F) ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\sodium\SodiumInnerPartitionBSPNodeMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */