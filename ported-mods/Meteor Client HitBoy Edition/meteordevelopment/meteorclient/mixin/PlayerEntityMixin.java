/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*     */ import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.entity.DropItemsEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.ClipAtLedgeEvent;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Flight;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.NoSlow;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.Sprint;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.Reach;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.SpeedMine;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.orbit.ICancellable;
/*     */ import net.minecraft.class_1299;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1542;
/*     */ import net.minecraft.class_1656;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1937;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_239;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_3965;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_1657.class})
/*     */ public abstract class PlayerEntityMixin
/*     */   extends class_1309
/*     */ {
/*     */   @Shadow
/*     */   public abstract class_1656 method_31549();
/*     */   
/*     */   protected PlayerEntityMixin(class_1299<? extends class_1309> entityType, class_1937 world) {
/*  45 */     super(entityType, world);
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_21825"}, at = {@At("HEAD")}, cancellable = true)
/*     */   protected void clipAtLedge(CallbackInfoReturnable<Boolean> info) {
/*  50 */     if (!method_73183().method_8608())
/*     */       return; 
/*  52 */     ClipAtLedgeEvent event = (ClipAtLedgeEvent)MeteorClient.EVENT_BUS.post(ClipAtLedgeEvent.get());
/*  53 */     if (event.isSet()) info.setReturnValue(Boolean.valueOf(event.isClip())); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_7328"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onDropItem(class_1799 stack, boolean retainOwnership, CallbackInfoReturnable<class_1542> cir) {
/*  58 */     if (method_73183().method_8608() && !stack.method_7960() && (
/*  59 */       (DropItemsEvent)MeteorClient.EVENT_BUS.post((ICancellable)DropItemsEvent.get(stack))).isCancelled()) cir.setReturnValue(null);
/*     */   
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_7325"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onIsSpectator(CallbackInfoReturnable<Boolean> info) {
/*  65 */     if (MeteorClient.mc.method_1562() == null) info.setReturnValue(Boolean.valueOf(false)); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_68878"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onIsCreative(CallbackInfoReturnable<Boolean> info) {
/*  70 */     if (MeteorClient.mc.method_1562() == null) info.setReturnValue(Boolean.valueOf(false)); 
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_7351"}, at = {@At("RETURN")})
/*     */   public float onGetBlockBreakingSpeed(float breakSpeed, class_2680 block) {
/*  75 */     if (!method_73183().method_8608()) return breakSpeed;
/*     */     
/*  77 */     SpeedMine speedMine = (SpeedMine)Modules.get().get(SpeedMine.class);
/*  78 */     if (!speedMine.isActive() || speedMine.mode.get() != SpeedMine.Mode.Normal || !speedMine.filter(block.method_26204())) return breakSpeed;
/*     */     
/*  80 */     float breakSpeedMod = (float)(breakSpeed * ((Double)speedMine.modifier.get()).doubleValue());
/*     */     
/*  82 */     class_239 class_239 = MeteorClient.mc.field_1765; if (class_239 instanceof class_3965) { class_3965 bhr = (class_3965)class_239;
/*  83 */       class_2338 pos = bhr.method_17777();
/*  84 */       if (((Double)speedMine.modifier.get()).doubleValue() < 1.0D || BlockUtils.canInstaBreak(pos, breakSpeed) == BlockUtils.canInstaBreak(pos, breakSpeedMod)) {
/*  85 */         return breakSpeedMod;
/*     */       }
/*  87 */       return 0.9F / BlockUtils.calcBlockBreakingDelta2(pos, 1.0F); }
/*     */ 
/*     */ 
/*     */     
/*  91 */     return breakSpeed;
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_6029"}, at = {@At("RETURN")})
/*     */   private float onGetMovementSpeed(float original) {
/*  96 */     if (!method_73183().method_8608()) return original; 
/*  97 */     if (!((NoSlow)Modules.get().get(NoSlow.class)).slowness()) return original;
/*     */     
/*  99 */     float walkSpeed = method_31549().method_7253();
/*     */     
/* 101 */     if (original < walkSpeed) {
/* 102 */       if (method_5624()) return (float)(walkSpeed * 1.300000011920929D); 
/* 103 */       return walkSpeed;
/*     */     } 
/*     */     
/* 106 */     return original;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_49484"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onGetOffGroundSpeed(CallbackInfoReturnable<Float> info) {
/* 111 */     if (!method_73183().method_8608())
/*     */       return; 
/* 113 */     float speed = ((Flight)Modules.get().get(Flight.class)).getOffGroundSpeed();
/* 114 */     if (speed != -1.0F) info.setReturnValue(Float.valueOf(speed)); 
/*     */   }
/*     */   
/*     */   @WrapWithCondition(method = {"method_75122"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1657;method_18799(Lnet/minecraft/class_243;)V")})
/*     */   private boolean keepSprint$setVelocity(class_1657 instance, class_243 vec3d) {
/* 119 */     return ((Sprint)Modules.get().get(Sprint.class)).stopSprinting();
/*     */   }
/*     */   
/*     */   @WrapWithCondition(method = {"method_75122"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1657;method_5728(Z)V")})
/*     */   private boolean keepSprint$setSprinting(class_1657 instance, boolean b) {
/* 124 */     return ((Sprint)Modules.get().get(Sprint.class)).stopSprinting();
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_55754"}, at = {@At("RETURN")})
/*     */   private double modifyBlockInteractionRange(double original) {
/* 129 */     return Math.max(0.0D, original + ((Reach)Modules.get().get(Reach.class)).blockReach());
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_55755"}, at = {@At("RETURN")})
/*     */   private double modifyEntityInteractionRange(double original) {
/* 134 */     return Math.max(0.0D, original + ((Reach)Modules.get().get(Reach.class)).entityReach());
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\PlayerEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */