/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.Anchor;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.Scaffold;
/*    */ import net.minecraft.class_1299;
/*    */ import net.minecraft.class_1309;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_3222;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_3222.class})
/*    */ public abstract class ServerPlayerEntityMixin
/*    */   extends class_1309
/*    */ {
/*    */   protected ServerPlayerEntityMixin(class_1299<? extends class_1309> entityType, class_1937 world) {
/* 23 */     super(entityType, world);
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_6043"}, at = {@At("HEAD")}, cancellable = true)
/*    */   public void dontJump(CallbackInfo ci) {
/* 28 */     if (!method_73183().method_8608())
/*    */       return; 
/* 30 */     Anchor module = (Anchor)Modules.get().get(Anchor.class);
/* 31 */     if (module.isActive() && module.cancelJump) { ci.cancel(); }
/* 32 */     else if (((Scaffold)Modules.get().get(Scaffold.class)).towering()) { ci.cancel(); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ServerPlayerEntityMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */