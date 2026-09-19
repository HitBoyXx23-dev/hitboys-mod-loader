/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyReceiver;
/*    */ import com.llamalad7.mixinextras.sugar.Local;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Consumer;
/*    */ import meteordevelopment.meteorclient.utils.tooltip.MeteorTooltipData;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2960;
/*    */ import net.minecraft.class_327;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_5632;
/*    */ import net.minecraft.class_5684;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ @Mixin({class_332.class})
/*    */ public abstract class DrawContextMixin
/*    */ {
/*    */   @Inject(method = {"method_51437(Lnet/minecraft/class_327;Ljava/util/List;Ljava/util/Optional;IILnet/minecraft/class_2960;)V"}, at = {@At(value = "INVOKE", target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V", shift = At.Shift.BEFORE)})
/*    */   private void onDrawTooltip(class_327 textRenderer, List<class_2561> text, Optional<class_5632> data, int x, int y, @Nullable class_2960 texture, CallbackInfo info, @Local(ordinal = 1) List<class_5684> list) {
/* 31 */     if (data.isPresent()) { MeteorTooltipData meteorTooltipData = (MeteorTooltipData)data.get(); if (meteorTooltipData instanceof MeteorTooltipData) { MeteorTooltipData meteorTooltipData1 = meteorTooltipData;
/* 32 */         list.add(meteorTooltipData1.getComponent()); }
/*    */        }
/*    */   
/*    */   } @ModifyReceiver(method = {"method_51437(Lnet/minecraft/class_327;Ljava/util/List;Ljava/util/Optional;IILnet/minecraft/class_2960;)V"}, at = {@At(value = "INVOKE", target = "Ljava/util/Optional;ifPresent(Ljava/util/function/Consumer;)V")})
/*    */   private Optional<class_5632> onDrawTooltip_modifyIfPresentReceiver(Optional<class_5632> data, Consumer<class_5632> consumer) {
/* 37 */     if (data.isPresent() && data.get() instanceof MeteorTooltipData) return Optional.empty(); 
/* 38 */     return data;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\DrawContextMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */