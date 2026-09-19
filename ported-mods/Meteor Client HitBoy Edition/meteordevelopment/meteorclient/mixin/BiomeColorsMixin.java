/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.Ambience;
/*    */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*    */ import net.minecraft.class_1163;
/*    */ import net.minecraft.class_1920;
/*    */ import net.minecraft.class_2338;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1163.class})
/*    */ public abstract class BiomeColorsMixin
/*    */ {
/*    */   @Inject(method = {"method_4961"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onGetWaterColor(class_1920 world, class_2338 pos, CallbackInfoReturnable<Integer> info) {
/* 25 */     Ambience ambience = (Ambience)Modules.get().get(Ambience.class);
/*    */     
/* 27 */     if (ambience.isActive() && ((Boolean)ambience.customWaterColor.get()).booleanValue()) {
/* 28 */       info.setReturnValue(Integer.valueOf(((SettingColor)ambience.waterColor.get()).getPacked()));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"method_4966"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onGetFoliageColor(class_1920 world, class_2338 pos, CallbackInfoReturnable<Integer> info) {
/* 37 */     Ambience ambience = (Ambience)Modules.get().get(Ambience.class);
/*    */     
/* 39 */     if (ambience.isActive() && ((Boolean)ambience.customFoliageColor.get()).booleanValue()) {
/* 40 */       info.setReturnValue(Integer.valueOf(((SettingColor)ambience.foliageColor.get()).getPacked()));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Inject(method = {"method_4962"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private static void onGetGrassColor(class_1920 world, class_2338 pos, CallbackInfoReturnable<Integer> info) {
/* 49 */     Ambience ambience = (Ambience)Modules.get().get(Ambience.class);
/*    */     
/* 51 */     if (ambience.isActive() && ((Boolean)ambience.customGrassColor.get()).booleanValue())
/* 52 */       info.setReturnValue(Integer.valueOf(((SettingColor)ambience.grassColor.get()).getPacked())); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BiomeColorsMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */