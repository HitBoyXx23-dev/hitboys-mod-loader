/*     */ package meteordevelopment.meteorclient.mixin;
/*     */ 
/*     */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*     */ import com.llamalad7.mixinextras.sugar.Local;
/*     */ import com.llamalad7.mixinextras.sugar.ref.LocalRef;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.game.ReceiveMessageEvent;
/*     */ import meteordevelopment.meteorclient.mixininterface.IChatHud;
/*     */ import meteordevelopment.meteorclient.mixininterface.IChatHudLine;
/*     */ import meteordevelopment.meteorclient.mixininterface.IChatHudLineVisible;
/*     */ import meteordevelopment.meteorclient.mixininterface.IMessageHandler;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.misc.BetterChat;
/*     */ import meteordevelopment.orbit.ICancellable;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_303;
/*     */ import net.minecraft.class_310;
/*     */ import net.minecraft.class_338;
/*     */ import net.minecraft.class_5481;
/*     */ import net.minecraft.class_7469;
/*     */ import net.minecraft.class_7591;
/*     */ import org.spongepowered.asm.mixin.Final;
/*     */ import org.spongepowered.asm.mixin.Mixin;
/*     */ import org.spongepowered.asm.mixin.Shadow;
/*     */ import org.spongepowered.asm.mixin.Unique;
/*     */ import org.spongepowered.asm.mixin.injection.At;
/*     */ import org.spongepowered.asm.mixin.injection.Inject;
/*     */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Mixin({class_338.class})
/*     */ public abstract class ChatHudMixin
/*     */   implements IChatHud
/*     */ {
/*     */   @Shadow
/*     */   @Final
/*     */   class_310 field_2062;
/*     */   @Shadow
/*     */   @Final
/*     */   private List<class_303.class_7590> field_2064;
/*     */   @Shadow
/*     */   @Final
/*     */   private List<class_303> field_2061;
/*     */   @Unique
/*     */   private BetterChat betterChat;
/*     */   @Unique
/*     */   private int nextId;
/*     */   
/*     */   public void meteor$add(class_2561 message, int id) {
/*  58 */     this.nextId = id;
/*  59 */     method_1812(message);
/*  60 */     this.nextId = 0;
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_1815"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;addFirst(Ljava/lang/Object;)V", shift = At.Shift.AFTER)})
/*     */   private void onAddMessageAfterNewChatHudLineVisible(class_303 message, CallbackInfo ci) {
/*  65 */     ((IChatHudLine)this.field_2064.getFirst()).meteor$setId(this.nextId);
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_58744(Lnet/minecraft/class_303;)V"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;addFirst(Ljava/lang/Object;)V", shift = At.Shift.AFTER)})
/*     */   private void onAddMessageAfterNewChatHudLine(class_303 message, CallbackInfo ci) {
/*  70 */     ((IChatHudLine)this.field_2061.getFirst()).meteor$setId(this.nextId);
/*     */   }
/*     */ 
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_1815"}, at = {@At(value = "NEW", target = "(ILnet/minecraft/class_5481;Lnet/minecraft/class_7591;Z)Lnet/minecraft/class_303$class_7590;")})
/*     */   private class_303.class_7590 onAddMessage_modifyChatHudLineVisible(class_303.class_7590 line, @Local(ordinal = 1) int j) {
/*  76 */     IMessageHandler handler = (IMessageHandler)this.field_2062.method_44714();
/*  77 */     if (handler == null) return line;
/*     */     
/*  79 */     IChatHudLineVisible meteorLine = (IChatHudLineVisible)line;
/*     */     
/*  81 */     meteorLine.meteor$setSender(handler.meteor$getSender());
/*  82 */     meteorLine.meteor$setStartOfEntry((j == 0));
/*     */     
/*  84 */     return line;
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_44811(Lnet/minecraft/class_2561;Lnet/minecraft/class_7469;Lnet/minecraft/class_7591;)V"}, at = {@At(value = "NEW", target = "(ILnet/minecraft/class_2561;Lnet/minecraft/class_7469;Lnet/minecraft/class_7591;)Lnet/minecraft/class_303;")})
/*     */   private class_303 onAddMessage_modifyChatHudLine(class_303 line) {
/*  89 */     IMessageHandler handler = (IMessageHandler)this.field_2062.method_44714();
/*  90 */     if (handler == null) return line;
/*     */     
/*  92 */     ((IChatHudLine)line).meteor$setSender(handler.meteor$getSender());
/*  93 */     return line;
/*     */   }
/*     */   
/*     */   @Inject(at = {@At("HEAD")}, method = {"method_44811(Lnet/minecraft/class_2561;Lnet/minecraft/class_7469;Lnet/minecraft/class_7591;)V"}, cancellable = true)
/*     */   private void onAddMessage(class_2561 message, class_7469 signatureData, class_7591 indicator, CallbackInfo ci, @Local(argsOnly = true) LocalRef<class_2561> messageRef, @Local(argsOnly = true) LocalRef<class_7591> indicatorRef) {
/*  98 */     ReceiveMessageEvent event = (ReceiveMessageEvent)MeteorClient.EVENT_BUS.post((ICancellable)ReceiveMessageEvent.get(message, indicator, this.nextId));
/*     */     
/* 100 */     if (event.isCancelled()) { ci.cancel(); }
/*     */     else
/* 102 */     { this.field_2064.removeIf(msg -> (((IChatHudLine)msg).meteor$getId() == this.nextId && this.nextId != 0));
/*     */       
/* 104 */       for (int i = this.field_2061.size() - 1; i > -1; i--) {
/* 105 */         if (((IChatHudLine)this.field_2061.get(i)).meteor$getId() == this.nextId && this.nextId != 0) {
/* 106 */           this.field_2061.remove(i);
/* 107 */           getBetterChat().removeLine(i);
/*     */         } 
/*     */       } 
/*     */       
/* 111 */       if (event.isModified()) {
/* 112 */         messageRef.set(event.getMessage());
/* 113 */         indicatorRef.set(event.getIndicator());
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_58744(Lnet/minecraft/class_303;)V"}, at = {@At(value = "CONSTANT", args = {"intValue=100"})})
/*     */   private int maxLength(int size) {
/* 121 */     if (Modules.get() == null || !getBetterChat().isLongerChat()) return size;
/*     */     
/* 123 */     return size + this.betterChat.getExtraChatLines();
/*     */   }
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_1815"}, at = {@At(value = "CONSTANT", args = {"intValue=100"})})
/*     */   private int maxLengthVisible(int size) {
/* 128 */     if (Modules.get() == null || !getBetterChat().isLongerChat()) return size;
/*     */     
/* 130 */     return size + this.betterChat.getExtraChatLines();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @ModifyExpressionValue(method = {"method_1805(Lnet/minecraft/class_338$class_12233;IIZ)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_3532;method_15386(F)I")})
/*     */   private int onRender_modifyWidth(int width) {
/* 137 */     return getBetterChat().modifyChatWidth(width);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Inject(method = {"method_1815"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_338;method_1819()Z")})
/*     */   private void onBreakChatMessageLines(class_303 message, CallbackInfo ci, @Local List<class_5481> list) {
/* 144 */     if (Modules.get() == null)
/*     */       return; 
/* 146 */     (getBetterChat()).lines.addFirst(Integer.valueOf(list.size()));
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_58744(Lnet/minecraft/class_303;)V"}, at = {@At(value = "INVOKE", target = "Ljava/util/List;removeLast()Ljava/lang/Object;")})
/*     */   private void onRemoveMessage(class_303 message, CallbackInfo ci) {
/* 151 */     if (Modules.get() == null)
/*     */       return; 
/* 153 */     int extra = getBetterChat().isLongerChat() ? getBetterChat().getExtraChatLines() : 0;
/* 154 */     int size = this.betterChat.lines.size();
/*     */     
/* 156 */     while (size > 100 + extra) {
/* 157 */       this.betterChat.lines.removeLast();
/* 158 */       size--;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_1808"}, at = {@At("HEAD")})
/*     */   private void onClear(boolean clearHistory, CallbackInfo ci) {
/* 164 */     (getBetterChat()).lines.clear();
/*     */   }
/*     */   
/*     */   @Inject(method = {"method_44813"}, at = {@At("HEAD")})
/*     */   private void onRefresh(CallbackInfo ci) {
/* 169 */     (getBetterChat()).lines.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   @Unique
/*     */   private BetterChat getBetterChat() {
/* 175 */     if (this.betterChat == null) {
/* 176 */       this.betterChat = (BetterChat)Modules.get().get(BetterChat.class);
/*     */     }
/*     */     
/* 179 */     return this.betterChat;
/*     */   }
/*     */   
/*     */   @Shadow
/*     */   public abstract void method_1812(class_2561 paramclass_2561);
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ChatHudMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */