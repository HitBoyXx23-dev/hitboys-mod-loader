/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.NoSlow;
/*    */ import net.minecraft.class_1702;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_1702.class})
/*    */ public class HungerManagerMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"method_75882()Z"}, at = {@At(value = "CONSTANT", args = {"floatValue=6.0f"})})
/*    */   private float onHunger(float constant) {
/* 19 */     if (((NoSlow)Modules.get().get(NoSlow.class)).hunger()) return -1.0F; 
/* 20 */     return constant;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\HungerManagerMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */