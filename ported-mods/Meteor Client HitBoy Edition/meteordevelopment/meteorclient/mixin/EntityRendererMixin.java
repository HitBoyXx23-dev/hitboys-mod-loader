/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import com.llamalad7.mixinextras.injector.ModifyReturnValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.ESP;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Fullbright;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Nametags;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_10017;
/*    */ import net.minecraft.class_10042;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1944;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_4604;
/*    */ import net.minecraft.class_5617;
/*    */ import net.minecraft.class_897;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_897.class})
/*    */ public abstract class EntityRendererMixin<T extends class_1297, S extends class_10017>
/*    */ {
/*    */   @Unique
/*    */   private ESP esp;
/*    */   @Unique
/*    */   private NoRender noRender;
/*    */   
/*    */   @Inject(method = {"<init>"}, at = {@At("TAIL")})
/*    */   private void onInit(class_5617.class_5618 context, CallbackInfo ci) {
/* 45 */     this.esp = (ESP)Modules.get().get(ESP.class);
/* 46 */     this.noRender = (NoRender)Modules.get().get(NoRender.class);
/*    */   }
/*    */   @Inject(method = {"method_62426"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onRenderLabel(T entity, CallbackInfoReturnable<class_2561> cir) {
/*    */     class_1657 player;
/* 51 */     if (this.noRender.noNametags()) cir.setReturnValue(null); 
/* 52 */     if (entity instanceof class_1657) { player = (class_1657)entity; } else { return; }
/* 53 */      if (((Nametags)Modules.get().get(Nametags.class)).playerNametags() && (EntityUtils.getGameMode(player) != null || !((Nametags)Modules.get().get(Nametags.class)).excludeBots()))
/* 54 */       cir.setReturnValue(null); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_3933"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void shouldRender(T entity, class_4604 frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
/* 59 */     if (this.noRender.noEntity((class_1297)entity)) cir.setReturnValue(Boolean.valueOf(false)); 
/* 60 */     if (this.noRender.noFallingBlocks() && entity instanceof net.minecraft.class_1540) cir.setReturnValue(Boolean.valueOf(false)); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_62406"}, at = {@At("HEAD")}, cancellable = true)
/*    */   void canBeCulled(T entity, CallbackInfoReturnable<Boolean> cir) {
/* 65 */     if (this.esp.forceRender()) cir.setReturnValue(Boolean.valueOf(false)); 
/*    */   }
/*    */   
/*    */   @ModifyReturnValue(method = {"method_27950"}, at = {@At("RETURN")})
/*    */   private int onGetSkyLight(int original) {
/* 70 */     return Math.max(((Fullbright)Modules.get().get(Fullbright.class)).getLuminance(class_1944.field_9284), original);
/*    */   }
/*    */   
/*    */   @ModifyReturnValue(method = {"method_24087"}, at = {@At("RETURN")})
/*    */   private int onGetBlockLight(int original) {
/* 75 */     return Math.max(((Fullbright)Modules.get().get(Fullbright.class)).getLuminance(class_1944.field_9282), original);
/*    */   }
/*    */   
/*    */   @ModifyExpressionValue(method = {"method_62354"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1937;method_8314(Lnet/minecraft/class_1944;Lnet/minecraft/class_2338;)I")})
/*    */   private int onGetLightLevel(int original) {
/* 80 */     return Math.max(((Fullbright)Modules.get().get(Fullbright.class)).getLuminance(class_1944.field_9282), original);
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_62354"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/class_10017;field_61821:I", shift = At.Shift.AFTER, opcode = 181)})
/*    */   private void onGetOutlineColor(T entity, S state, float tickProgress, CallbackInfo ci) {
/* 85 */     if (this.esp.isGlow() && !this.esp.shouldSkip((class_1297)entity)) {
/* 86 */       Color color = this.esp.getColor((class_1297)entity);
/*    */       
/* 88 */       if (color == null)
/* 89 */         return;  ((class_10017)state).field_61821 = color.getPacked();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_73154(Lnet/minecraft/class_1297;Lnet/minecraft/class_10017;)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void updateShadow(class_1297 entity, class_10017 renderState, CallbackInfo ci) {
/* 95 */     if (this.noRender.noDeadEntities() && entity instanceof net.minecraft.class_1309 && renderState instanceof class_10042) {
/*    */       
/* 97 */       class_10042 livingEntityRenderState = (class_10042)renderState; if (livingEntityRenderState.field_53449 > 0.0F)
/*    */       {
/* 99 */         ci.cancel();
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\EntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */