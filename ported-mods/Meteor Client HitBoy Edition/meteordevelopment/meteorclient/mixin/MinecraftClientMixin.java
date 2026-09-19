/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*     */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*     */ import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
/*     */ import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
/*     */ import com.llamalad7.mixinextras.sugar.Local;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.entity.player.DoAttackEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.DoItemUseEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.ItemUseCrosshairTargetEvent;
/*     */ import meteordevelopment.meteorclient.events.game.GameLeftEvent;
/*     */ import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
/*     */ import meteordevelopment.meteorclient.events.game.ResolutionChangedEvent;
/*     */ import meteordevelopment.meteorclient.events.game.ResourcePacksReloadedEvent;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.mixininterface.IMinecraftClient;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.misc.InventoryTweaks;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.GUIMove;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.FastUse;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.Multitask;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.ESP;
/*     */ import meteordevelopment.meteorclient.systems.modules.world.HighwayBuilder;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.misc.CPSUtils;
/*     */ import meteordevelopment.meteorclient.utils.misc.MeteorStarscript;
/*     */ import meteordevelopment.meteorclient.utils.network.OnlinePlayers;
/*     */ import meteordevelopment.orbit.ICancellable;
/*     */ import net.minecraft.class_10209;
/*     */ import net.minecraft.class_1041;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_276;
/*     */ import net.minecraft.class_304;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_312;
/*     */ import net.minecraft.class_315;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_636;
/*     */ import net.minecraft.class_638;
/*     */ import net.minecraft.class_746;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ import org.meteordev.starscript.Script;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Mutable;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ @Mixin(value = {class_310.class}, priority = 1001)
/*     */ public abstract class MinecraftClientMixin implements IMinecraftClient {
/*     */   @Unique
/*     */   private boolean doItemUseCalled;
/*     */   @Unique
/*     */   private boolean rightClick;
/*     */   @Unique
/*     */   private long lastTime;
/*     */   @Unique
/*     */   private boolean firstFrame;
/*     */   @Shadow
/*     */   public class_638 field_1687;
/*     */   @Shadow
/*     */   @Final
/*     */   public class_312 field_1729;
/*     */   @Shadow
/*     */   @Final
/*     */   private class_1041 field_1704;
/*     */   @Shadow
/*     */   public class_437 field_1755;
/*     */   @Shadow
/*     */   @Final
/*     */   public class_315 field_1690;
/*     */   @Shadow
/*     */   @Nullable
/*     */   public class_636 field_1761;
/*     */   @Shadow
/*     */   private int field_1752;
/*     */   @Shadow
/*     */   @Nullable
/*     */   public class_746 field_1724;
/*     */   @Shadow
/*     */   @Final
/*     */   @Mutable
/*     */   private class_276 field_1689;
/*     */   
/*     */   @Inject(method = {"<init>"}, at = {@At("TAIL")})
/*     */   private void onInit(CallbackInfo info) {
/*  99 */     MeteorClient.INSTANCE.onInitializeClient();
/* 100 */     this.firstFrame = true;
/*     */   }
/*     */   
/*     */   @Inject(at = {@At("HEAD")}, method = {"method_1574"})
/*     */   private void onPreTick(CallbackInfo info) {
/* 105 */     OnlinePlayers.update();
/*     */     
/* 107 */     this.doItemUseCalled = false;
/*     */     
/* 109 */     class_10209.method_64146().method_15396("meteor-client_pre_update");
/* 110 */     MeteorClient.EVENT_BUS.post(TickEvent.Pre.get());
/* 111 */     class_10209.method_64146().method_15407();
/*     */     
/* 113 */     if (this.rightClick && !this.doItemUseCalled && this.field_1761 != null) method_1583(); 
/* 114 */     this.rightClick = false;
/*     */   }
/*     */   
/*     */   @Inject(at = {@At("TAIL")}, method = {"method_1574"})
/*     */   private void onTick(CallbackInfo info) {
/* 119 */     class_10209.method_64146().method_15396("meteor-client_post_update");
/* 120 */     MeteorClient.EVENT_BUS.post(TickEvent.Post.get());
/* 121 */     class_10209.method_64146().method_15407();
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_1536"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onAttack(CallbackInfoReturnable<Boolean> cir) {
/* 126 */     CPSUtils.onAttack();
/* 127 */     if (((DoAttackEvent)MeteorClient.EVENT_BUS.post((ICancellable)DoAttackEvent.get())).isCancelled()) cir.cancel(); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_1583"}, at = {@At("HEAD")})
/*     */   private void onDoItemUse(CallbackInfo info) {
/* 132 */     this.doItemUseCalled = true;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_18096(Lnet/minecraft/class_437;ZZ)V"}, at = {@At("HEAD")})
/*     */   private void onDisconnect(class_437 screen, boolean transferring, boolean stopSound, CallbackInfo info) {
/* 137 */     if (this.field_1687 != null) {
/* 138 */       MeteorClient.EVENT_BUS.post(GameLeftEvent.get());
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_1507"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onSetScreen(class_437 screen, CallbackInfo info) {
/* 144 */     if (screen instanceof meteordevelopment.meteorclient.gui.WidgetScreen) screen.method_16014(this.field_1729.method_1603() * this.field_1704.method_4495(), this.field_1729.method_1604() * this.field_1704.method_4495());
/*     */     
/* 146 */     OpenScreenEvent event = OpenScreenEvent.get(screen);
/* 147 */     MeteorClient.EVENT_BUS.post((ICancellable)event);
/*     */     
/* 149 */     if (event.isCancelled()) info.cancel(); 
/*     */   }
/*     */   
/*     */   @WrapOperation(method = {"method_1507"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_304;method_1437()V")})
/*     */   private void onSetScreenKeyBindingUnpressAll(Operation<Void> op) {
/* 154 */     Modules modules = Modules.get();
/* 155 */     if (modules == null) {
/* 156 */       op.call(new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 160 */     GUIMove guimove = (GUIMove)modules.get(GUIMove.class);
/* 161 */     if (guimove == null || !guimove.isActive() || guimove.skip()) {
/* 162 */       op.call(new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 166 */     class_315 options = MeteorClient.mc.field_1690;
/* 167 */     for (class_304 kb : KeyBindingAccessor.getKeysById().values()) {
/* 168 */       if (kb == options.field_1894 || 
/* 169 */         kb == options.field_1913 || 
/* 170 */         kb == options.field_1849 || 
/* 171 */         kb == options.field_1881 || ((
/* 172 */         (Boolean)guimove.sneak.get()).booleanValue() && kb == options.field_1832) || ((
/* 173 */         (Boolean)guimove.sprint.get()).booleanValue() && kb == options.field_1867) || ((
/* 174 */         (Boolean)guimove.jump.get()).booleanValue() && kb == options.field_1903))
/* 175 */         continue;  ((KeyBindingAccessor)kb).meteor$invokeReset();
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_1583"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1799;method_45435(Lnet/minecraft/class_7699;)Z")})
/*     */   private void onDoItemUseHand(CallbackInfo ci, @Local class_1799 itemStack) {
/* 181 */     FastUse fastUse = (FastUse)Modules.get().get(FastUse.class);
/* 182 */     if (fastUse.isActive()) {
/* 183 */       this.field_1752 = fastUse.getItemUseCooldown(itemStack);
/*     */     }
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_1583"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1268;values()[Lnet/minecraft/class_1268;")}, cancellable = true)
/*     */   private void onDoItemUseBeforeHands(CallbackInfo ci) {
/* 189 */     if (((DoItemUseEvent)MeteorClient.EVENT_BUS.post((ICancellable)DoItemUseEvent.get())).isCancelled()) ci.cancel(); 
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_1583"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/class_310;field_1765:Lnet/minecraft/class_239;", ordinal = 1)})
/*     */   private class_239 doItemUseMinecraftClientCrosshairTargetProxy(class_239 original) {
/* 194 */     return ((ItemUseCrosshairTargetEvent)MeteorClient.EVENT_BUS.post(ItemUseCrosshairTargetEvent.get(original))).target;
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_36561(ZLnet/minecraft/class_310$class_8764;)Ljava/util/concurrent/CompletableFuture;"}, at = {@At("RETURN")})
/*     */   private CompletableFuture<Void> onReloadResourcesNewCompletableFuture(CompletableFuture<Void> original) {
/* 199 */     return original.thenRun(() -> MeteorClient.EVENT_BUS.post(ResourcePacksReloadedEvent.get()));
/*     */   }
/*     */   
/*     */   @ModifyArg(method = {"method_24288"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_1041;method_24286(Ljava/lang/String;)V"))
/*     */   private String setTitle(String original) {
/* 204 */     if (Config.get() == null || !((Boolean)(Config.get()).customWindowTitle.get()).booleanValue()) return original;
/*     */     
/* 206 */     String customTitle = (String)(Config.get()).customWindowTitleText.get();
/* 207 */     Script script = MeteorStarscript.compile(customTitle);
/*     */     
/* 209 */     if (script != null) {
/* 210 */       String title = MeteorStarscript.run(script);
/* 211 */       if (title != null) customTitle = title;
/*     */     
/*     */     } 
/* 214 */     return customTitle;
/*     */   }
/*     */ 
/*     */   
/*     */   @WrapWithCondition(method = {"method_1508"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_636;method_2897(Lnet/minecraft/class_1657;)V")})
/*     */   private boolean wrapStopUsing(class_636 instance, class_1657 player) {
/* 220 */     return HB$stopUsingItem();
/*     */   }
/*     */   
/*     */   @Unique
/*     */   private boolean HB$stopUsingItem() {
/* 225 */     HighwayBuilder b = (HighwayBuilder)Modules.get().get(HighwayBuilder.class);
/* 226 */     return (!b.isActive() || !b.drawingBow);
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_15993"}, at = {@At("TAIL")})
/*     */   private void onResolutionChanged(CallbackInfo info) {
/* 231 */     MeteorClient.EVENT_BUS.post(ResolutionChangedEvent.get());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"method_1523"}, at = {@At("HEAD")})
/*     */   private void onRender(CallbackInfo info) {
/* 238 */     long time = System.currentTimeMillis();
/*     */     
/* 240 */     if (this.firstFrame) {
/* 241 */       this.lastTime = time;
/* 242 */       this.firstFrame = false;
/*     */     } 
/*     */     
/* 245 */     Utils.frameTime = (time - this.lastTime) / 1000.0D;
/* 246 */     this.lastTime = time;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_1583"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_636;method_2923()Z")})
/*     */   private boolean doItemUseModifyIsBreakingBlock(boolean original) {
/* 253 */     return (!Modules.get().isActive(Multitask.class) && original);
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_1590"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_746;method_6115()Z")})
/*     */   private boolean handleBlockBreakingModifyIsUsingItem(boolean original) {
/* 258 */     return (!Modules.get().isActive(Multitask.class) && original);
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_1508"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_746;method_6115()Z", ordinal = 0)})
/*     */   private boolean handleInputEventsModifyIsUsingItem(boolean original) {
/* 263 */     return (!((Multitask)Modules.get().get(Multitask.class)).attackingEntities() && original);
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_1508"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_746;method_6115()Z", ordinal = 0, shift = At.Shift.BEFORE)})
/*     */   private void handleInputEventsInjectStopUsingItem(CallbackInfo info) {
/* 268 */     if (((Multitask)Modules.get().get(Multitask.class)).attackingEntities() && this.field_1724.method_6115()) {
/* 269 */       if (!this.field_1690.field_1904.method_1434() && HB$stopUsingItem()) this.field_1761.method_2897((class_1657)this.field_1724);
/*     */       
/* 271 */       while (this.field_1690.field_1904.method_1436());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyReturnValue(method = {"method_27022"}, at = {@At("RETURN")})
/*     */   private boolean hasOutlineModifyIsOutline(boolean original, class_1297 entity) {
/* 279 */     ESP esp = (ESP)Modules.get().get(ESP.class);
/* 280 */     if (esp == null) return original; 
/* 281 */     if (!esp.isGlow() || esp.shouldSkip(entity)) return original;
/*     */     
/* 283 */     return (esp.getColor(entity) != null || original);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private boolean isBreaking = false;
/*     */ 
/*     */   
/*     */   @WrapWithCondition(method = {"method_1574"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_310;method_1508()V")})
/*     */   private boolean wrapHandleInputEvents(class_310 instance) {
/* 294 */     return !((InventoryTweaks)Modules.get().get(InventoryTweaks.class)).frameInput();
/*     */   }
/*     */   
/*     */   @WrapWithCondition(method = {"method_1508"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_310;method_1590(Z)V")})
/*     */   private boolean wrapHandleBlockBreaking(class_310 instance, boolean breaking) {
/* 299 */     this.isBreaking = breaking;
/* 300 */     return !((InventoryTweaks)Modules.get().get(InventoryTweaks.class)).frameInput();
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_1574"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_310;method_1508()V", shift = At.Shift.AFTER)})
/*     */   private void afterHandleInputEvents(CallbackInfo ci) {
/* 305 */     if (!((InventoryTweaks)Modules.get().get(InventoryTweaks.class)).frameInput())
/*     */       return; 
/* 307 */     method_1590(this.isBreaking);
/* 308 */     this.isBreaking = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void meteor$rightClick() {
/* 315 */     this.rightClick = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void meteor$setFramebuffer(class_276 framebuffer) {
/* 320 */     this.field_1689 = framebuffer;
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   protected abstract void method_1583();
/*     */   
/*     */   @Shadow
/*     */   protected abstract void method_1590(boolean paramBoolean);
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\MinecraftClientMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */