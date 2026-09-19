/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.meteor.MouseClickEvent;
/*    */ import meteordevelopment.meteorclient.events.meteor.MouseScrollEvent;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.Input;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
/*    */ import meteordevelopment.orbit.ICancellable;
/*    */ import net.minecraft.class_1041;
/*    */ import net.minecraft.class_11909;
/*    */ import net.minecraft.class_11910;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_312;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_312.class})
/*    */ public abstract class MouseMixin
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_310 field_1779;
/*    */   
/*    */   @Shadow
/*    */   public abstract double method_68879(class_1041 paramclass_1041);
/*    */   
/*    */   @Shadow
/*    */   public abstract double method_68883(class_1041 paramclass_1041);
/*    */   
/*    */   @Inject(method = {"method_1601"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onMouseButton(long window, class_11910 mouseInput, int action, CallbackInfo ci) {
/* 41 */     Input.setButtonState(mouseInput.comp_4801(), (action != 0));
/*    */     
/* 43 */     class_11909 click = new class_11909(method_68879(this.field_1779.method_22683()), method_68883(this.field_1779.method_22683()), mouseInput);
/* 44 */     if (((MouseClickEvent)MeteorClient.EVENT_BUS.post((ICancellable)MouseClickEvent.get(click, KeyAction.get(action)))).isCancelled()) ci.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_1598"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo info) {
/* 49 */     if (((MouseScrollEvent)MeteorClient.EVENT_BUS.post((ICancellable)MouseScrollEvent.get(vertical))).isCancelled()) info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\MouseMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */