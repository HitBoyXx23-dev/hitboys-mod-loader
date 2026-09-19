/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.combat.Hitboxes;
/*    */ import net.minecraft.class_12155;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_238;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_12155.class})
/*    */ public class EntityHitboxDebugRendererMixin
/*    */ {
/*    */   @ModifyExpressionValue(method = {"method_75432"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_1297;method_5829()Lnet/minecraft/class_238;", ordinal = 0)})
/*    */   private class_238 meteor$createHitbox(class_238 original, class_1297 entity, float tickProgress, boolean inLocalServer) {
/* 21 */     double v = ((Hitboxes)Modules.get().get(Hitboxes.class)).getEntityValue(entity);
/* 22 */     if (v == 0.0D) return original;
/*    */     
/* 24 */     return original.method_1014(v);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\EntityHitboxDebugRendererMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */