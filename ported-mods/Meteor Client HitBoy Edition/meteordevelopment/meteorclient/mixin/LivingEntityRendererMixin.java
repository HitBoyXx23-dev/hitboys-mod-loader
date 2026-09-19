/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*     */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*     */ import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.mixininterface.IEntityRenderState;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.Chams;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.Freecam;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*     */ import net.minecraft.class_10042;
/*     */ import net.minecraft.class_1058;
/*     */ import net.minecraft.class_11659;
/*     */ import net.minecraft.class_11683;
/*     */ import net.minecraft.class_12075;
/*     */ import net.minecraft.class_12249;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1921;
/*     */ import net.minecraft.class_268;
/*     */ import net.minecraft.class_3879;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_583;
/*     */ import net.minecraft.class_922;
/*     */ import org.lwjgl.opengl.GL11C;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_922.class})
/*     */ public abstract class LivingEntityRendererMixin<T extends class_1309, S extends class_10042, M extends class_583<? super S>>
/*     */ {
/*     */   @Unique
/*     */   private Chams chams;
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_4055(Lnet/minecraft/class_1309;D)Z"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_310;method_1560()Lnet/minecraft/class_1297;")})
/*     */   private class_1297 hasLabelGetCameraEntityProxy(class_1297 cameraEntity) {
/*  47 */     return Modules.get().isActive(Freecam.class) ? null : cameraEntity;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_4055(Lnet/minecraft/class_1309;D)Z"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_746;method_5781()Lnet/minecraft/class_268;")})
/*     */   private class_268 hasLabelClientPlayerEntityGetScoreboardTeamProxy(class_268 team) {
/*  54 */     return (MeteorClient.mc.field_1724 == null) ? null : team;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"<init>"}, at = {@At("RETURN")})
/*     */   private void init$chams(CallbackInfo info) {
/*  64 */     this.chams = (Chams)Modules.get().get(Chams.class);
/*     */   }
/*     */ 
/*     */   
/*     */   @WrapWithCondition(method = {"method_4054(Lnet/minecraft/class_10042;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;Lnet/minecraft/class_12075;)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_11659;method_73490(Lnet/minecraft/class_3879;Ljava/lang/Object;Lnet/minecraft/class_4587;Lnet/minecraft/class_1921;IIILnet/minecraft/class_1058;ILnet/minecraft/class_11683$class_11792;)V")})
/*     */   private <TState> boolean render$render(class_11659 instance, class_3879<? super TState> model, TState state, class_4587 matrixStack, class_1921 renderLayer, int light, int overlay, int mixColor, class_1058 sprite, int outlineColor, class_11683.class_11792 crumblingOverlayCommand) {
/*     */     class_1657 player;
/*  71 */     if (this.chams.isActive() && ((Boolean)this.chams.players.get()).booleanValue()) { class_1297 class_1297 = ((IEntityRenderState)state).meteor$getEntity(); if (class_1297 instanceof class_1657) { player = (class_1657)class_1297; } else { return true; }  } else { return true; }
/*  72 */      if (((Boolean)this.chams.ignoreSelf.get()).booleanValue() && player == MeteorClient.mc.field_1724) return true;
/*     */     
/*  74 */     instance.method_73490(model, state, matrixStack, renderLayer, light, overlay, PlayerUtils.getPlayerColor(player, (Color)this.chams.playersColor.get()).getPacked(), sprite, outlineColor, null);
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @ModifyReturnValue(method = {"method_24302"}, at = {@At("RETURN")})
/*     */   private class_1921 getRenderPlayer(class_1921 original, S state, boolean showBody, boolean translucent, boolean showOutline) {
/*     */     class_1657 player;
/*  82 */     if (this.chams.isActive()) { class_1297 class_1297 = ((IEntityRenderState)state).meteor$getEntity(); if (class_1297 instanceof class_1657) { player = (class_1657)class_1297; }
/*  83 */       else { return original; }  } else { return original; }
/*     */     
/*  85 */     if (!((Boolean)this.chams.players.get()).booleanValue() || ((Boolean)this.chams.playersTexture.get()).booleanValue())
/*  86 */       return original; 
/*  87 */     if (((Boolean)this.chams.ignoreSelf.get()).booleanValue() && player == MeteorClient.mc.field_1724) {
/*  88 */       return original;
/*     */     }
/*  90 */     return class_12249.method_75998(Chams.BLANK);
/*     */   }
/*     */ 
/*     */   
/*     */   @Inject(method = {"method_4054(Lnet/minecraft/class_10042;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;Lnet/minecraft/class_12075;)V"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void render$Head(S state, class_4587 matrixStack, class_11659 orderedRenderCommandQueue, class_12075 arg, CallbackInfo ci) {
/*     */     class_1309 livingEntity;
/*  97 */     class_1297 entity = ((IEntityRenderState)state).meteor$getEntity();
/*  98 */     if (entity instanceof class_1309) { livingEntity = (class_1309)entity; }
/*     */     else { return; }
/* 100 */      if (((NoRender)Modules.get().get(NoRender.class)).noDeadEntities() && livingEntity.method_29504()) ci.cancel();
/*     */     
/* 102 */     if (this.chams.shouldRender(entity)) {
/* 103 */       GL11C.glEnable(32823);
/* 104 */       GL11C.glPolygonOffset(1.0F, -1100000.0F);
/*     */     } 
/*     */   }
/*     */   @Inject(method = {"method_4054(Lnet/minecraft/class_10042;Lnet/minecraft/class_4587;Lnet/minecraft/class_11659;Lnet/minecraft/class_12075;)V"}, at = {@At("TAIL")})
/*     */   private void render$Tail(S state, class_4587 matrixStack, class_11659 orderedRenderCommandQueue, class_12075 arg, CallbackInfo ci) {
/*     */     class_1309 livingEntity;
/* 110 */     class_1297 entity = ((IEntityRenderState)state).meteor$getEntity();
/* 111 */     if (entity instanceof class_1309) { livingEntity = (class_1309)entity; }
/*     */     else { return; }
/* 113 */      if (this.chams.shouldRender((class_1297)livingEntity)) {
/* 114 */       GL11C.glPolygonOffset(1.0F, 1100000.0F);
/* 115 */       GL11C.glDisable(32823);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\LivingEntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */