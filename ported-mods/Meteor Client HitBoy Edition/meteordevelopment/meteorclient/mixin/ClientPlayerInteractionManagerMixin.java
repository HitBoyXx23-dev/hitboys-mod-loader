/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.entity.DropItemsEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.AttackEntityEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.BlockBreakingCooldownEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.BreakBlockEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.InteractBlockEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.InteractEntityEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.InteractItemEvent;
/*     */ import meteordevelopment.meteorclient.events.entity.player.StartBreakingBlockEvent;
/*     */ import meteordevelopment.meteorclient.mixininterface.IClientPlayerInteractionManager;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.BreakDelay;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.SpeedMine;
/*     */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*     */ import meteordevelopment.orbit.ICancellable;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1269;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1713;
/*     */ import net.minecraft.class_1735;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1922;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2350;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2846;
/*     */ import net.minecraft.class_3965;
/*     */ import net.minecraft.class_636;
/*     */ import net.minecraft.class_638;
/*     */ import net.minecraft.class_7204;
/*     */ import net.minecraft.class_746;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.Redirect;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_636.class})
/*     */ public abstract class ClientPlayerInteractionManagerMixin
/*     */   implements IClientPlayerInteractionManager
/*     */ {
/*     */   @Shadow
/*     */   private int field_3716;
/*     */   
/*     */   @Inject(method = {"method_2906"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onClickSlot(int syncId, int slotId, int button, class_1713 actionType, class_1657 player, CallbackInfo info) {
/*  57 */     if (actionType == class_1713.field_7795 && slotId >= 0 && slotId < player.field_7512.field_7761.size()) {
/*  58 */       if (((DropItemsEvent)MeteorClient.EVENT_BUS.post((ICancellable)DropItemsEvent.get(((class_1735)player.field_7512.field_7761.get(slotId)).method_7677()))).isCancelled()) info.cancel();
/*     */     
/*  60 */     } else if (slotId == -999) {
/*     */       
/*  62 */       if (((DropItemsEvent)MeteorClient.EVENT_BUS.post((ICancellable)DropItemsEvent.get(player.field_7512.method_34255()))).isCancelled()) info.cancel(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_2910"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onAttackBlock(class_2338 blockPos, class_2350 direction, CallbackInfoReturnable<Boolean> info) {
/*  68 */     if (((StartBreakingBlockEvent)MeteorClient.EVENT_BUS.post((ICancellable)StartBreakingBlockEvent.get(blockPos, direction))).isCancelled()) { info.cancel(); }
/*     */     else
/*  70 */     { SpeedMine sm = (SpeedMine)Modules.get().get(SpeedMine.class);
/*  71 */       class_2680 state = MeteorClient.mc.field_1687.method_8320(blockPos);
/*     */       
/*  73 */       if (!sm.instamine() || !sm.filter(state.method_26204()))
/*     */         return; 
/*  75 */       if (state.method_26165((class_1657)MeteorClient.mc.field_1724, (class_1922)MeteorClient.mc.field_1687, blockPos) > 0.5F) {
/*  76 */         method_2899(blockPos);
/*  77 */         method_41931(MeteorClient.mc.field_1687, sequence -> new class_2846(class_2846.class_2847.field_12968, blockPos, direction, sequence));
/*  78 */         method_41931(MeteorClient.mc.field_1687, sequence -> new class_2846(class_2846.class_2847.field_12973, blockPos, direction, sequence));
/*  79 */         info.setReturnValue(Boolean.valueOf(true));
/*     */       }  }
/*     */   
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_2896"}, at = {@At("HEAD")}, cancellable = true)
/*     */   public void interactBlock(class_746 player, class_1268 hand, class_3965 hitResult, CallbackInfoReturnable<class_1269> cir) {
/*  86 */     if (((InteractBlockEvent)MeteorClient.EVENT_BUS.post((ICancellable)InteractBlockEvent.get(player.method_6047().method_7960() ? class_1268.field_5810 : hand, hitResult))).isCancelled()) cir.setReturnValue(class_1269.field_5814); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_2918"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onAttackEntity(class_1657 player, class_1297 target, CallbackInfo info) {
/*  91 */     if (((AttackEntityEvent)MeteorClient.EVENT_BUS.post((ICancellable)AttackEntityEvent.get(target))).isCancelled()) info.cancel(); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_2905"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onInteractEntity(class_1657 player, class_1297 entity, class_1268 hand, CallbackInfoReturnable<class_1269> info) {
/*  96 */     if (((InteractEntityEvent)MeteorClient.EVENT_BUS.post((ICancellable)InteractEntityEvent.get(entity, hand))).isCancelled()) info.setReturnValue(class_1269.field_5814); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_2915"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onDropCreativeStack(class_1799 stack, CallbackInfo info) {
/* 101 */     if (((DropItemsEvent)MeteorClient.EVENT_BUS.post((ICancellable)DropItemsEvent.get(stack))).isCancelled()) info.cancel(); 
/*     */   }
/*     */   
/*     */   @Redirect(method = {"method_2902"}, at = @At(value = "FIELD", target = "Lnet/minecraft/class_636;field_3716:I", opcode = 181, ordinal = 1))
/*     */   private void creativeBreakDelayChange(class_636 interactionManager, int value) {
/* 106 */     BlockBreakingCooldownEvent event = (BlockBreakingCooldownEvent)MeteorClient.EVENT_BUS.post(BlockBreakingCooldownEvent.get(value));
/* 107 */     this.field_3716 = event.cooldown;
/*     */   }
/*     */   
/*     */   @Redirect(method = {"method_2902"}, at = @At(value = "FIELD", target = "Lnet/minecraft/class_636;field_3716:I", opcode = 181, ordinal = 2))
/*     */   private void survivalBreakDelayChange(class_636 interactionManager, int value) {
/* 112 */     BlockBreakingCooldownEvent event = (BlockBreakingCooldownEvent)MeteorClient.EVENT_BUS.post(BlockBreakingCooldownEvent.get(value));
/* 113 */     this.field_3716 = event.cooldown;
/*     */   }
/*     */   
/*     */   @Redirect(method = {"method_2910"}, at = @At(value = "FIELD", target = "Lnet/minecraft/class_636;field_3716:I", opcode = 181))
/*     */   private void creativeBreakDelayChange2(class_636 interactionManager, int value) {
/* 118 */     BlockBreakingCooldownEvent event = (BlockBreakingCooldownEvent)MeteorClient.EVENT_BUS.post(BlockBreakingCooldownEvent.get(value));
/* 119 */     this.field_3716 = event.cooldown;
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_41930"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_2680;method_26165(Lnet/minecraft/class_1657;Lnet/minecraft/class_1922;Lnet/minecraft/class_2338;)F")})
/*     */   private float modifyBlockBreakingDelta(float original) {
/* 124 */     if (((BreakDelay)Modules.get().get(BreakDelay.class)).preventInstaBreak() && original >= 1.0F) {
/* 125 */       BlockBreakingCooldownEvent event = (BlockBreakingCooldownEvent)MeteorClient.EVENT_BUS.post(BlockBreakingCooldownEvent.get(this.field_3716));
/* 126 */       this.field_3716 = event.cooldown;
/* 127 */       return 0.0F;
/*     */     } 
/* 129 */     return original;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_2899"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onBreakBlock(class_2338 blockPos, CallbackInfoReturnable<Boolean> info) {
/* 134 */     if (((BreakBlockEvent)MeteorClient.EVENT_BUS.post((ICancellable)BreakBlockEvent.get(blockPos))).isCancelled()) info.setReturnValue(Boolean.valueOf(false)); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_2919"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onInteractItem(class_1657 player, class_1268 hand, CallbackInfoReturnable<class_1269> info) {
/* 139 */     InteractItemEvent event = (InteractItemEvent)MeteorClient.EVENT_BUS.post(InteractItemEvent.get(hand));
/* 140 */     if (event.toReturn != null) info.setReturnValue(event.toReturn); 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_2925"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onCancelBlockBreaking(CallbackInfo info) {
/* 145 */     if (BlockUtils.breaking) info.cancel();
/*     */   
/*     */   }
/*     */   
/*     */   public void meteor$syncSelected() {
/* 150 */     method_2911();
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   protected abstract void method_2911();
/*     */   
/*     */   @Shadow
/*     */   public abstract boolean method_2899(class_2338 paramclass_2338);
/*     */   
/*     */   @Shadow
/*     */   public abstract void method_41931(class_638 paramclass_638, class_7204 paramclass_7204);
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ClientPlayerInteractionManagerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */