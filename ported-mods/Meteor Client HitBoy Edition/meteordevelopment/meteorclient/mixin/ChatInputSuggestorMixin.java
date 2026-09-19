/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.sugar.Local;
/*    */ import com.mojang.brigadier.ParseResults;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.commands.Commands;
/*    */ import meteordevelopment.meteorclient.systems.config.Config;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_342;
/*    */ import net.minecraft.class_4717;
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
/*    */ @Mixin({class_4717.class})
/*    */ public abstract class ChatInputSuggestorMixin
/*    */ {
/*    */   @Shadow
/*    */   private ParseResults<class_2172> field_21610;
/*    */   @Shadow
/*    */   @Final
/*    */   class_342 field_21599;
/*    */   @Shadow
/*    */   boolean field_21614;
/*    */   @Shadow
/*    */   private CompletableFuture<Suggestions> field_21611;
/*    */   @Shadow
/*    */   private class_4717.class_464 field_21612;
/*    */   
/*    */   @Shadow
/*    */   protected abstract void method_23937();
/*    */   
/*    */   @Inject(method = {"method_23934"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/brigadier/StringReader;canRead()Z", remap = false)}, cancellable = true)
/*    */   public void onRefresh(CallbackInfo ci, @Local StringReader reader) {
/* 44 */     String prefix = (String)(Config.get()).prefix.get();
/* 45 */     int length = prefix.length();
/*    */     
/* 47 */     if (reader.canRead(length) && reader.getString().startsWith(prefix, reader.getCursor())) {
/* 48 */       reader.setCursor(reader.getCursor() + length);
/*    */       
/* 50 */       if (this.field_21610 == null) {
/* 51 */         this.field_21610 = Commands.DISPATCHER.parse(reader, MeteorClient.mc.method_1562().method_2875());
/*    */       }
/*    */       
/* 54 */       int cursor = this.field_21599.method_1881();
/* 55 */       if (cursor >= length && (this.field_21612 == null || !this.field_21614)) {
/* 56 */         this.field_21611 = Commands.DISPATCHER.getCompletionSuggestions(this.field_21610, cursor);
/* 57 */         this.field_21611.thenRun(() -> {
/*    */               if (this.field_21611.isDone()) {
/*    */                 method_23937();
/*    */               }
/*    */             });
/*    */       } 
/*    */       
/* 64 */       ci.cancel();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ChatInputSuggestorMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */