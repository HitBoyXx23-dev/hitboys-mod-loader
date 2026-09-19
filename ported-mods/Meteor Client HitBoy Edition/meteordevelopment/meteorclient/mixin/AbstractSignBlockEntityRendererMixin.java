/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import net.minecraft.class_10529;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_10529.class})
/*    */ public abstract class AbstractSignBlockEntityRendererMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"method_65828"}, at = {@At(value = "CONSTANT", args = {"intValue=4", "ordinal=1"})})
/*    */   private int loopTextLengthProxy(int i) {
/* 19 */     if (((NoRender)Modules.get().get(NoRender.class)).noSignText()) return 0; 
/* 20 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\AbstractSignBlockEntityRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */