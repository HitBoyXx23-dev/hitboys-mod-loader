/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.world.Ambience;
/*    */ import meteordevelopment.meteorclient.utils.render.color.SettingColor;
/*    */ import net.minecraft.class_1920;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2680;
/*    */ import net.minecraft.class_322;
/*    */ import net.minecraft.class_324;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyArg;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_324.class})
/*    */ public abstract class BlockColorsMixin
/*    */ {
/*    */   @ModifyArg(method = {"method_1689"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_324;method_1690(Lnet/minecraft/class_322;[Lnet/minecraft/class_2248;)V", ordinal = 3), index = 0)
/*    */   private static class_322 modifySpruceLeavesColor(class_322 provider) {
/* 31 */     return (state, world, pos, tintIndex) -> getModifiedColor(-10380959);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @ModifyArg(method = {"method_1689"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/class_324;method_1690(Lnet/minecraft/class_322;[Lnet/minecraft/class_2248;)V", ordinal = 4), index = 0)
/*    */   private static class_322 modifyBirchLeavesColor(class_322 provider) {
/* 44 */     return (state, world, pos, tintIndex) -> getModifiedColor(-8345771);
/*    */   }
/*    */   
/*    */   @Unique
/*    */   private static int getModifiedColor(int original) {
/* 49 */     if (Modules.get() == null) return original;
/*    */     
/* 51 */     Ambience ambience = (Ambience)Modules.get().get(Ambience.class);
/* 52 */     if (ambience.isActive() && ((Boolean)ambience.customFoliageColor.get()).booleanValue()) {
/* 53 */       return ((SettingColor)ambience.foliageColor.get()).getPacked();
/*    */     }
/*    */     
/* 56 */     return original;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BlockColorsMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */