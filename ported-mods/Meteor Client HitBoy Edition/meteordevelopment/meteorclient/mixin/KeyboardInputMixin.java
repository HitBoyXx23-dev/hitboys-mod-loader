/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.Sneak;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Freecam;
/*    */ import net.minecraft.class_10185;
/*    */ import net.minecraft.class_743;
/*    */ import net.minecraft.class_744;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_743.class})
/*    */ public abstract class KeyboardInputMixin
/*    */   extends class_744
/*    */ {
/*    */   @Inject(method = {"method_3129"}, at = {@At("TAIL")})
/*    */   private void isPressed(CallbackInfo ci) {
/* 23 */     if (((Sneak)Modules.get().get(Sneak.class)).doVanilla() || ((Freecam)Modules.get().get(Freecam.class)).staySneaking()) this
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 30 */         .field_54155 = new class_10185(this.field_54155.comp_3159(), this.field_54155.comp_3160(), this.field_54155.comp_3161(), this.field_54155.comp_3162(), this.field_54155.comp_3163(), true, this.field_54155.comp_3165()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\KeyboardInputMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */