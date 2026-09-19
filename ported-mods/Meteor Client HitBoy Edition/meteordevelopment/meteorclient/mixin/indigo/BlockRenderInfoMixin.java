/*    */ package meteordevelopment.meteorclient.mixin.indigo;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Xray;
/*    */ import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo;
/*    */ import net.minecraft.class_1920;
/*    */ import net.minecraft.class_1922;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2680;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({BlockRenderInfo.class})
/*    */ public abstract class BlockRenderInfoMixin
/*    */ {
/*    */   @Shadow
/*    */   public class_2680 blockState;
/*    */   @Shadow
/*    */   public class_1920 blockView;
/*    */   @Shadow
/*    */   public class_2338 blockPos;
/*    */   
/*    */   @ModifyReturnValue(method = {"shouldDrawSide"}, at = {@At("RETURN")})
/*    */   private boolean modifyShouldDrawSide(boolean original, class_2350 side) {
/* 33 */     Xray xray = (Xray)Modules.get().get(Xray.class);
/*    */     
/* 35 */     if (xray.isActive()) {
/* 36 */       return xray.modifyDrawSide(this.blockState, (class_1922)this.blockView, this.blockPos, side, original);
/*    */     }
/*    */     
/* 39 */     return original;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\indigo\BlockRenderInfoMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */