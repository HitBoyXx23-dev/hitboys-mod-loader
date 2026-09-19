/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import java.io.File;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.game.ChangePerspectiveEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Freecam;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.KeyBinds;
/*    */ import meteordevelopment.orbit.ICancellable;
/*    */ import net.minecraft.class_304;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_315;
/*    */ import net.minecraft.class_5498;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Mutable;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_315.class})
/*    */ public abstract class GameOptionsMixin
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   @Mutable
/*    */   public class_304[] field_1839;
/*    */   
/*    */   @Inject(method = {"<init>"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/class_315;field_1839:[Lnet/minecraft/class_304;", opcode = 181, shift = At.Shift.AFTER)})
/*    */   private void onInitAfterKeysAll(class_310 client, File optionsFile, CallbackInfo info) {
/* 34 */     this.field_1839 = KeyBinds.apply(this.field_1839);
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_31043"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void setPerspective(class_5498 perspective, CallbackInfo info) {
/* 39 */     if (Modules.get() == null)
/*    */       return; 
/* 41 */     ChangePerspectiveEvent event = (ChangePerspectiveEvent)MeteorClient.EVENT_BUS.post((ICancellable)ChangePerspectiveEvent.get(perspective));
/*    */     
/* 43 */     if (event.isCancelled()) info.cancel();
/*    */     
/* 45 */     if (Modules.get().isActive(Freecam.class)) info.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\GameOptionsMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */