/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Fullbright;
/*    */ import net.minecraft.class_1944;
/*    */ import net.minecraft.class_761;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.ModifyVariable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_761.class_10948.class})
/*    */ public interface BrightnessGetterMixin
/*    */ {
/*    */   @ModifyVariable(method = {"method_68890"}, at = @At("STORE"), ordinal = 0)
/*    */   private static int getLightmapCoordinatesModifySkyLight(int sky) {
/* 21 */     return Math.max(((Fullbright)Modules.get().get(Fullbright.class)).getLuminance(class_1944.field_9284), sky);
/*    */   }
/*    */   
/*    */   @ModifyVariable(method = {"method_68890"}, at = @At("STORE"), ordinal = 1)
/*    */   private static int getLightmapCoordinatesModifyBlockLight(int sky) {
/* 26 */     return Math.max(((Fullbright)Modules.get().get(Fullbright.class)).getLuminance(class_1944.field_9282), sky);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\BrightnessGetterMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */