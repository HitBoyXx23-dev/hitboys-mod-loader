/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.sugar.Local;
/*    */ import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.BetterTab;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_327;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_3532;
/*    */ import net.minecraft.class_355;
/*    */ import net.minecraft.class_640;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Constant;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyConstant;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ @Mixin({class_355.class})
/*    */ public abstract class PlayerListHudMixin
/*    */ {
/*    */   @Shadow
/*    */   protected abstract List<class_640> method_48213();
/*    */   
/*    */   @ModifyConstant(constant = {@Constant(longValue = 80L)}, method = {"method_48213"})
/*    */   private long modifyCount(long count) {
/* 34 */     BetterTab module = (BetterTab)Modules.get().get(BetterTab.class);
/*    */     
/* 36 */     return module.isActive() ? ((Integer)module.tabSize.get()).intValue() : count;
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_1918"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void getPlayerName(class_640 playerListEntry, CallbackInfoReturnable<class_2561> info) {
/* 41 */     BetterTab betterTab = (BetterTab)Modules.get().get(BetterTab.class);
/*    */     
/* 43 */     if (betterTab.isActive()) info.setReturnValue(betterTab.getPlayerName(playerListEntry)); 
/*    */   }
/*    */   
/*    */   @ModifyArg(method = {"method_1919"}, at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"), index = 0)
/*    */   private int modifyWidth(int width) {
/* 48 */     BetterTab module = (BetterTab)Modules.get().get(BetterTab.class);
/*    */     
/* 50 */     return (module.isActive() && ((Boolean)module.accurateLatency.get()).booleanValue()) ? (width + 30) : width;
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_1919"}, at = {@At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I", shift = At.Shift.BEFORE)})
/*    */   private void modifyHeight(CallbackInfo ci, @Local(ordinal = 5) LocalIntRef o, @Local(ordinal = 6) LocalIntRef p) {
/* 55 */     BetterTab module = (BetterTab)Modules.get().get(BetterTab.class);
/* 56 */     if (!module.isActive()) {
/*    */       return;
/*    */     }
/* 59 */     int newP = 1;
/* 60 */     int newO = method_48213().size(), totalPlayers = newO;
/* 61 */     while (newO > ((Integer)module.tabHeight.get()).intValue()) {
/* 62 */       newO = (totalPlayers + ++newP - 1) / newP;
/*    */     }
/*    */     
/* 65 */     o.set(newO);
/* 66 */     p.set(newP);
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_1923"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onRenderLatencyIcon(class_332 context, int width, int x, int y, class_640 entry, CallbackInfo ci) {
/* 71 */     BetterTab betterTab = (BetterTab)Modules.get().get(BetterTab.class);
/*    */     
/* 73 */     if (betterTab.isActive() && ((Boolean)betterTab.accurateLatency.get()).booleanValue()) {
/* 74 */       class_310 mc = class_310.method_1551();
/* 75 */       class_327 textRenderer = mc.field_1772;
/*    */       
/* 77 */       int latency = class_3532.method_15340(entry.method_2959(), 0, 9999);
/*    */       
/* 79 */       int color = (latency < 150) ? -16717456 : ((latency < 300) ? -1585120 : -2670024);
/* 80 */       String text = "" + latency + "ms";
/* 81 */       context.method_25303(textRenderer, text, x + width - textRenderer.method_1727(text), y, color);
/* 82 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\PlayerListHudMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */