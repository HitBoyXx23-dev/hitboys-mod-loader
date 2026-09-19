/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.meteor.CharTypedEvent;
/*    */ import meteordevelopment.meteorclient.events.meteor.KeyEvent;
/*    */ import meteordevelopment.meteorclient.gui.GuiKeyEvents;
/*    */ import meteordevelopment.meteorclient.gui.WidgetScreen;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.Input;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
/*    */ import meteordevelopment.orbit.ICancellable;
/*    */ import net.minecraft.class_11905;
/*    */ import net.minecraft.class_11908;
/*    */ import net.minecraft.class_309;
/*    */ import net.minecraft.class_310;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_309.class})
/*    */ public abstract class KeyboardMixin
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_310 field_1678;
/*    */   
/*    */   @Inject(method = {"method_1466"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void onKey(long window, int action, class_11908 input, CallbackInfo ci) {
/* 34 */     int modifiers = input.comp_4797();
/* 35 */     if (input.comp_4795() != -1) {
/*    */ 
/*    */       
/* 38 */       if (action == 1) {
/* 39 */         modifiers |= Input.getModifier(input.comp_4795());
/* 40 */       } else if (action == 0) {
/* 41 */         modifiers &= Input.getModifier(input.comp_4795()) ^ 0xFFFFFFFF;
/*    */       } 
/*    */       
/* 44 */       if (this.field_1678.field_1755 instanceof WidgetScreen && action == 2) {
/* 45 */         ((WidgetScreen)this.field_1678.field_1755).keyRepeated(new class_11908(input.comp_4795(), input.comp_4796(), modifiers));
/*    */       }
/*    */       
/* 48 */       if (GuiKeyEvents.canUseKeys) {
/* 49 */         Input.setKeyState(input.comp_4795(), (action != 0));
/* 50 */         if (((KeyEvent)MeteorClient.EVENT_BUS.post((ICancellable)KeyEvent.get(new class_11908(input.comp_4795(), input.comp_4796(), modifiers), KeyAction.get(action)))).isCancelled()) ci.cancel(); 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_1457"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onChar(long window, class_11905 input, CallbackInfo ci) {
/* 57 */     if (Utils.canUpdate() && !this.field_1678.method_1493() && (this.field_1678.field_1755 == null || this.field_1678.field_1755 instanceof WidgetScreen) && (
/* 58 */       (CharTypedEvent)MeteorClient.EVENT_BUS.post((ICancellable)CharTypedEvent.get((char)input.comp_4793()))).isCancelled()) ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\KeyboardMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */