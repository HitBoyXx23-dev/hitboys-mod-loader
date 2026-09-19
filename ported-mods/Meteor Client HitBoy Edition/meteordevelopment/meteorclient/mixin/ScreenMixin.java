/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.commands.Commands;
/*    */ import meteordevelopment.meteorclient.systems.config.Config;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.GUIMove;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.NoRender;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import meteordevelopment.meteorclient.utils.misc.text.MeteorClickEvent;
/*    */ import meteordevelopment.meteorclient.utils.misc.text.RunnableClickEvent;
/*    */ import net.minecraft.class_11908;
/*    */ import net.minecraft.class_2558;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_437;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin(value = {class_437.class}, priority = 500)
/*    */ public abstract class ScreenMixin
/*    */ {
/*    */   @Inject(method = {"method_52752"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onRenderInGameBackground(CallbackInfo info) {
/* 37 */     if (Utils.canUpdate() && ((NoRender)Modules.get().get(NoRender.class)).noGuiBackground())
/* 38 */       info.cancel(); 
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_71847"}, at = {@At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V", remap = false)}, cancellable = true)
/*    */   private static void onHandleBasicClickEvent(class_2558 clickEvent, class_310 client, class_437 screen, CallbackInfo ci) {
/* 43 */     if (clickEvent instanceof RunnableClickEvent) { RunnableClickEvent runnableClickEvent = (RunnableClickEvent)clickEvent;
/* 44 */       runnableClickEvent.runnable.run();
/* 45 */       ci.cancel(); }
/*    */     
/* 47 */     else if (clickEvent instanceof MeteorClickEvent) { MeteorClickEvent meteorClickEvent = (MeteorClickEvent)clickEvent; if (meteorClickEvent.value.startsWith((String)(Config.get()).prefix.get()))
/*    */         try {
/* 49 */           Commands.dispatch(meteorClickEvent.value.substring(((String)(Config.get()).prefix.get()).length()));
/* 50 */         } catch (CommandSyntaxException e) {
/* 51 */           MeteorClient.LOG.error("Failed to run command", (Throwable)e);
/*    */         } finally {
/* 53 */           ci.cancel();
/*    */         }   }
/*    */   
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_25404"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void onKeyPressed(class_11908 input, CallbackInfoReturnable<Boolean> cir) {
/* 60 */     if (this instanceof net.minecraft.class_408)
/* 61 */       return;  GUIMove guiMove = (GUIMove)Modules.get().get(GUIMove.class);
/* 62 */     List<Integer> arrows = List.of(Integer.valueOf(262), Integer.valueOf(263), Integer.valueOf(264), Integer.valueOf(265));
/* 63 */     if ((guiMove.disableArrows() && arrows.contains(Integer.valueOf(input.comp_4795()))) || (guiMove.disableSpace() && input.comp_4795() == 32))
/* 64 */       cir.setReturnValue(Boolean.valueOf(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */