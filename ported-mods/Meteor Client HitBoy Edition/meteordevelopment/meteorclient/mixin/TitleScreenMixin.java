/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.config.Config;
/*    */ import meteordevelopment.meteorclient.utils.player.TitleScreenCredits;
/*    */ import net.minecraft.class_11909;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_332;
/*    */ import net.minecraft.class_437;
/*    */ import net.minecraft.class_442;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_442.class})
/*    */ public abstract class TitleScreenMixin
/*    */   extends class_437
/*    */ {
/*    */   public TitleScreenMixin(class_2561 title) {
/* 25 */     super(title);
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_25394"}, at = {@At("TAIL")})
/*    */   private void onRender(class_332 context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
/* 30 */     if (((Boolean)(Config.get()).titleScreenCredits.get()).booleanValue()) TitleScreenCredits.render(context); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_25402"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onMouseClicked(class_11909 click, boolean doubled, CallbackInfoReturnable<Boolean> cir) {
/* 35 */     if (((Boolean)(Config.get()).titleScreenCredits.get()).booleanValue() && click.method_74245() == 0 && 
/* 36 */       TitleScreenCredits.onClicked(click.comp_4798(), click.comp_4799())) cir.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\TitleScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */