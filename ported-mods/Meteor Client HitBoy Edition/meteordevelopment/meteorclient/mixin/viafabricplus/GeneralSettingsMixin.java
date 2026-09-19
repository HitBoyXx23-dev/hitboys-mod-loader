/*    */ package meteordevelopment.meteorclient.mixin.viafabricplus;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import com.viaversion.viafabricplus.settings.impl.GeneralSettings;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({GeneralSettings.class})
/*    */ public abstract class GeneralSettingsMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"<init>"}, at = {@At(value = "CONSTANT", args = {"intValue=2"}, ordinal = 1)}, remap = false)
/*    */   private int modifyDefaultPosition(int original) {
/* 19 */     return 4;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\viafabricplus\GeneralSettingsMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */