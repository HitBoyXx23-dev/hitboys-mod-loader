/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import java.util.function.ToDoubleFunction;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.combat.Hitboxes;
/*    */ import net.minecraft.class_12392;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1309;
/*    */ import net.minecraft.class_243;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_12392.class})
/*    */ public class AttackRangeComponentMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"method_76737(Lnet/minecraft/class_1309;Ljava/util/function/ToDoubleFunction;D)Z"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/class_12392;comp_5262:F", opcode = 180)})
/*    */   private float modifyHitboxMargin(float original, class_1309 entity, ToDoubleFunction<class_243> squaredDistanceFunction, double extraHitboxMargin) {
/* 24 */     float v = (float)((Hitboxes)Modules.get().get(Hitboxes.class)).getEntityValue((class_1297)entity);
/* 25 */     return original + v;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\AttackRangeComponentMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */