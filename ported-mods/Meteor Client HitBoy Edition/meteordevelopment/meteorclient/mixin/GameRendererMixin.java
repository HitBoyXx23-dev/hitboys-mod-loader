/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*     */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*     */ import com.llamalad7.mixinextras.sugar.Local;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.MixinPlugin;
/*     */ import meteordevelopment.meteorclient.events.render.GetFovEvent;
/*     */ import meteordevelopment.meteorclient.events.render.Render3DEvent;
/*     */ import meteordevelopment.meteorclient.events.render.RenderAfterWorldEvent;
/*     */ import meteordevelopment.meteorclient.gui.WidgetScreen;
/*     */ import meteordevelopment.meteorclient.mixininterface.IGameRenderer;
/*     */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*     */ import meteordevelopment.meteorclient.renderer.MeteorRenderPipelines;
/*     */ import meteordevelopment.meteorclient.renderer.Renderer3D;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.Freecam;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*     */ import meteordevelopment.meteorclient.systems.modules.render.Zoom;
/*     */ import meteordevelopment.meteorclient.systems.modules.world.HighwayBuilder;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.render.CustomBannerGuiElementRenderer;
/*     */ import meteordevelopment.meteorclient.utils.render.NametagUtils;
/*     */ import meteordevelopment.meteorclient.utils.render.RenderUtils;
/*     */ import net.minecraft.class_10209;
/*     */ import net.minecraft.class_11228;
/*     */ import net.minecraft.class_11239;
/*     */ import net.minecraft.class_11246;
/*     */ import net.minecraft.class_11701;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_332;
/*     */ import net.minecraft.class_4184;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_4587;
/*     */ import net.minecraft.class_4599;
/*     */ import net.minecraft.class_757;
/*     */ import net.minecraft.class_758;
/*     */ import net.minecraft.class_9779;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Matrix4fc;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_757.class})
/*     */ public abstract class GameRendererMixin
/*     */   implements IGameRenderer
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   private class_310 field_4015;
/*     */   @Shadow
/*     */   @Final
/*     */   private class_4184 field_18765;
/*     */   @Unique
/*     */   private Renderer3D renderer;
/*     */   @Unique
/*     */   private Renderer3D depthRenderer;
/*     */   @Unique
/*  81 */   private final class_4587 matrices = new class_4587();
/*     */ 
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   @Final
/*     */   private class_4599 field_20948;
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   @Final
/*     */   private class_11228 field_59965;
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   @Final
/*     */   private class_758 field_60793;
/*     */ 
/*     */   
/*     */   @Shadow
/*     */   @Final
/*     */   class_11246 field_59966;
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyArg(method = {"<init>"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_11228;<init>(Lnet/minecraft/class_11246;Lnet/minecraft/class_4597$class_4598;Lnet/minecraft/class_11659;Lnet/minecraft/class_11684;Ljava/util/List;)V"))
/*     */   private List<class_11239<?>> meteor$addSpecialRenderers(List<class_11239<?>> list) {
/* 108 */     list = new ArrayList<>(list);
/* 109 */     list.add(new CustomBannerGuiElementRenderer(this.field_20948.method_23000(), (class_11701)this.field_4015.method_72703()));
/*     */     
/* 111 */     return List.of((class_11239<?>[])list.<class_11239>toArray(new class_11239[0])); } @Shadow
/*     */   public abstract void method_3190(float paramFloat); @Shadow
/*     */   public abstract void method_3203(); @Shadow
/*     */   protected abstract void method_3186(class_4587 paramclass_4587, float paramFloat); @Shadow
/*     */   protected abstract void method_3198(class_4587 paramclass_4587, float paramFloat); @Inject(method = {"method_3188"}, at = {@At(value = "INVOKE_STRING", target = "Lnet/minecraft/class_3695;method_15405(Ljava/lang/String;)V", args = {"ldc=hand"})})
/* 116 */   private void onRenderWorld(class_9779 tickCounter, CallbackInfo ci, @Local(ordinal = 0) Matrix4f projection, @Local(ordinal = 1) Matrix4f position, @Local(ordinal = 0) float tickDelta, @Local class_4587 matrixStack) { if (!Utils.canUpdate())
/*     */       return; 
/* 118 */     class_10209.method_64146().method_15396("meteor-client_render");
/*     */ 
/*     */ 
/*     */     
/* 122 */     if (this.renderer == null) this.renderer = new Renderer3D(MeteorRenderPipelines.WORLD_COLORED_LINES, MeteorRenderPipelines.WORLD_COLORED); 
/* 123 */     if (this.depthRenderer == null) this.depthRenderer = new Renderer3D(MeteorRenderPipelines.WORLD_COLORED_LINES_DEPTH, MeteorRenderPipelines.WORLD_COLORED_DEPTH); 
/* 124 */     Render3DEvent event = Render3DEvent.get(matrixStack, this.renderer, this.depthRenderer, tickDelta, (this.field_18765.method_71156()).field_1352, (this.field_18765.method_71156()).field_1351, (this.field_18765.method_71156()).field_1350);
/*     */ 
/*     */ 
/*     */     
/* 128 */     RenderSystem.getModelViewStack().pushMatrix().mul((Matrix4fc)position);
/*     */     
/* 130 */     this.matrices.method_22903();
/* 131 */     method_3198(this.matrices, this.field_18765.method_55437());
/* 132 */     if (((Boolean)this.field_4015.field_1690.method_42448().method_41753()).booleanValue()) {
/* 133 */       method_3186(this.matrices, this.field_18765.method_55437());
/*     */     }
/* 135 */     Matrix4f inverseBob = (new Matrix4f((Matrix4fc)this.matrices.method_23760().method_23761())).invert();
/* 136 */     RenderSystem.getModelViewStack().mul((Matrix4fc)inverseBob);
/* 137 */     this.matrices.method_22909();
/*     */ 
/*     */ 
/*     */     
/* 141 */     Matrix4f correctedPosition = (MixinPlugin.isIrisPresent && RenderUtils.isShaderPackInUse()) ? (new Matrix4f((Matrix4fc)position)).mul((Matrix4fc)inverseBob) : position;
/* 142 */     RenderUtils.updateScreenCenter(projection, correctedPosition);
/* 143 */     NametagUtils.onRender(position);
/*     */ 
/*     */ 
/*     */     
/* 147 */     this.renderer.begin();
/* 148 */     this.depthRenderer.begin();
/* 149 */     MeteorClient.EVENT_BUS.post(event);
/* 150 */     this.renderer.render(matrixStack);
/* 151 */     this.depthRenderer.render(matrixStack);
/*     */ 
/*     */ 
/*     */     
/* 155 */     RenderSystem.getModelViewStack().popMatrix();
/*     */     
/* 157 */     class_10209.method_64146().method_15407(); }
/*     */ 
/*     */   
/*     */   @Inject(method = {"method_3188"}, at = {@At("TAIL")})
/*     */   private void onRenderWorldTail(CallbackInfo info) {
/* 162 */     MeteorClient.EVENT_BUS.post(RenderAfterWorldEvent.get());
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_3192"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_11228;method_70890(Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V", shift = At.Shift.AFTER)})
/*     */   private void onRenderGui(class_9779 tickCounter, boolean tick, CallbackInfo info) {
/* 167 */     class_437 class_437 = this.field_4015.field_1755; if (class_437 instanceof WidgetScreen) { WidgetScreen widgetScreen = (WidgetScreen)class_437;
/* 168 */       this.field_59966.method_70926();
/* 169 */       int mouseX = (int)this.field_4015.field_1729.method_68879(this.field_4015.method_22683());
/* 170 */       int mouseY = (int)this.field_4015.field_1729.method_68883(this.field_4015.method_22683());
/*     */       
/* 172 */       class_332 context = new class_332(this.field_4015, this.field_59966, mouseX, mouseY);
/*     */       
/* 174 */       widgetScreen.renderCustom(context, mouseX, mouseY, tickCounter.method_60636());
/*     */       
/* 176 */       RenderSystem.getDevice().createCommandEncoder().clearDepthTexture(this.field_4015.method_1522().method_30278(), 1.0D);
/* 177 */       meteor$flushGuiState(); }
/*     */   
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_3189"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void onShowFloatingItem(class_1799 floatingItem, CallbackInfo info) {
/* 183 */     if (floatingItem.method_7909() == class_1802.field_8288 && ((NoRender)Modules.get().get(NoRender.class)).noTotemAnimation()) {
/* 184 */       info.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_3188"}, at = {@At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F", ordinal = 0)})
/*     */   private float applyCameraTransformationsMathHelperLerpProxy(float original) {
/* 190 */     return ((NoRender)Modules.get().get(NoRender.class)).noNausea() ? 0.0F : original;
/*     */   }
/*     */   
/*     */   @ModifyReturnValue(method = {"method_3196"}, at = {@At("RETURN")})
/*     */   private float modifyFov(float original) {
/* 195 */     return ((GetFovEvent)MeteorClient.EVENT_BUS.post(GetFovEvent.get(original))).fov;
/*     */   }
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private boolean freecamSet = false;
/*     */ 
/*     */   
/*     */   @Inject(method = {"method_3190"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void updateTargetedEntityInvoke(float tickDelta, CallbackInfo info) {
/* 205 */     Freecam freecam = (Freecam)Modules.get().get(Freecam.class);
/* 206 */     boolean highwayBuilder = Modules.get().isActive(HighwayBuilder.class);
/*     */     
/* 208 */     if ((freecam.isActive() || highwayBuilder) && this.field_4015.method_1560() != null && !this.freecamSet) {
/* 209 */       info.cancel();
/* 210 */       class_1297 cameraE = this.field_4015.method_1560();
/*     */       
/* 212 */       double x = cameraE.method_23317();
/* 213 */       double y = cameraE.method_23318();
/* 214 */       double z = cameraE.method_23321();
/* 215 */       double lastX = cameraE.field_6014;
/* 216 */       double lastY = cameraE.field_6036;
/* 217 */       double lastZ = cameraE.field_5969;
/* 218 */       float yaw = cameraE.method_36454();
/* 219 */       float pitch = cameraE.method_36455();
/* 220 */       float lastYaw = cameraE.field_5982;
/* 221 */       float lastPitch = cameraE.field_6004;
/*     */       
/* 223 */       if (highwayBuilder) {
/* 224 */         cameraE.method_36456(this.field_18765.method_19330());
/* 225 */         cameraE.method_36457(this.field_18765.method_19329());
/*     */       } else {
/* 227 */         ((IVec3d)cameraE.method_73189()).meteor$set(freecam.pos.x, freecam.pos.y - cameraE.method_18381(cameraE.method_18376()), freecam.pos.z);
/* 228 */         cameraE.field_6014 = freecam.prevPos.x;
/* 229 */         cameraE.field_6036 = freecam.prevPos.y - cameraE.method_18381(cameraE.method_18376());
/* 230 */         cameraE.field_5969 = freecam.prevPos.z;
/* 231 */         cameraE.method_36456(freecam.yaw);
/* 232 */         cameraE.method_36457(freecam.pitch);
/* 233 */         cameraE.field_5982 = freecam.lastYaw;
/* 234 */         cameraE.field_6004 = freecam.lastPitch;
/*     */       } 
/*     */       
/* 237 */       this.freecamSet = true;
/* 238 */       method_3190(tickDelta);
/* 239 */       this.freecamSet = false;
/*     */       
/* 241 */       ((IVec3d)cameraE.method_73189()).meteor$set(x, y, z);
/* 242 */       cameraE.field_6014 = lastX;
/* 243 */       cameraE.field_6036 = lastY;
/* 244 */       cameraE.field_5969 = lastZ;
/* 245 */       cameraE.method_36456(yaw);
/* 246 */       cameraE.method_36457(pitch);
/* 247 */       cameraE.field_5982 = lastYaw;
/* 248 */       cameraE.field_6004 = lastPitch;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_3172"}, at = {@At("HEAD")}, cancellable = true)
/*     */   private void renderHand(float tickProgress, boolean sleeping, Matrix4f positionMatrix, CallbackInfo ci) {
/* 254 */     if (!((Freecam)Modules.get().get(Freecam.class)).renderHands() || 
/* 255 */       !((Zoom)Modules.get().get(Zoom.class)).renderHands()) {
/* 256 */       ci.cancel();
/*     */     }
/*     */   }
/*     */   
/*     */   public void meteor$flushGuiState() {
/* 261 */     this.field_59965.method_70890(this.field_60793.method_71109(class_758.class_4596.field_60101));
/* 262 */     this.field_59965.method_70879();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\GameRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */