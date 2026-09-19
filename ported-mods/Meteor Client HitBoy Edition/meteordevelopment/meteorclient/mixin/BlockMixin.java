/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.NoSlow;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.Slippy;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Xray;
/*    */ import net.minecraft.class_1935;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_2248;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_4970;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin({class_2248.class})
/*    */ public abstract class BlockMixin
/*    */   extends class_4970
/*    */   implements class_1935
/*    */ {
/*    */   public BlockMixin(class_4970.class_2251 settings) {
/* 27 */     super(settings);
/*    */   }
/*    */ 
/*    */   
/*    */   @ModifyReturnValue(method = {"method_9499"}, at = {@At("RETURN")})
/*    */   public float getSlipperiness(float original) {
/* 33 */     if (Modules.get() == null) return original;
/*    */     
/* 35 */     Slippy slippy = (Slippy)Modules.get().get(Slippy.class);
/* 36 */     class_2248 block = (class_2248)this;
/*    */     
/* 38 */     if (slippy.isActive() && ((slippy.listMode.get() == Slippy.ListMode.Whitelist) ? ((List)slippy.allowedBlocks.get()).contains(block) : !((List)slippy.ignoredBlocks.get()).contains(block))) {
/* 39 */       return ((Double)slippy.friction.get()).floatValue();
/*    */     }
/*    */     
/* 42 */     if (block == class_2246.field_10030 && ((NoSlow)Modules.get().get(NoSlow.class)).slimeBlock()) return 0.6F; 
/* 43 */     return original;
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"method_9607"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void meteor$forceXrayFace(class_2680 state, class_2680 sideState, class_2350 side, CallbackInfoReturnable<Boolean> cir) {
/* 49 */     Modules modules = Modules.get();
/* 50 */     if (modules == null)
/*    */       return; 
/* 52 */     Xray xray = (Xray)modules.get(Xray.class);
/* 53 */     if (xray.isActive() && !xray.isBlocked(state.method_26204(), null))
/* 54 */       cir.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BlockMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */