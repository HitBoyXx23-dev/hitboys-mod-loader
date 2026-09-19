/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import meteordevelopment.meteorclient.systems.config.Config;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_4008;
/*    */ import net.minecraft.class_8519;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_4008.class})
/*    */ public abstract class SplashTextResourceSupplierMixin
/*    */ {
/*    */   @Unique
/*    */   private boolean override = true;
/*    */   @Unique
/* 26 */   private static final Random random = new Random();
/*    */   @Unique
/* 28 */   private final List<String> meteorSplashes = getMeteorSplashes();
/*    */   
/*    */   @Inject(method = {"method_18174"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onApply(CallbackInfoReturnable<class_8519> cir) {
/* 32 */     if (Config.get() == null || !((Boolean)(Config.get()).titleScreenSplashes.get()).booleanValue())
/*    */       return; 
/* 34 */     if (this.override) cir.setReturnValue(new class_8519((class_2561)class_2561.method_43470(this.meteorSplashes.get(random.nextInt(this.meteorSplashes.size()))))); 
/* 35 */     this.override = !this.override;
/*    */   }
/*    */   
/*    */   @Unique
/*    */   private static List<String> getMeteorSplashes() {
/* 40 */     return List.of("Meteor on Crack!", "Star Meteor Client on GitHub!", "Based utility mod.", "§6MineGame159 §fbased god", "§4meteorclient.com", "§4Meteor on Crack!", "§6Meteor on Crack!");
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\SplashTextResourceSupplierMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */