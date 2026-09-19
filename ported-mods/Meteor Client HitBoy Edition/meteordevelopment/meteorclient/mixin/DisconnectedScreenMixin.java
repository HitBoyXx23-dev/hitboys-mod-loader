/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.Pair;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.misc.AutoReconnect;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_412;
/*    */ import net.minecraft.class_4185;
/*    */ import net.minecraft.class_419;
/*    */ import net.minecraft.class_437;
/*    */ import net.minecraft.class_442;
/*    */ import net.minecraft.class_639;
/*    */ import net.minecraft.class_642;
/*    */ import net.minecraft.class_8021;
/*    */ import net.minecraft.class_8667;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ @Mixin({class_419.class})
/*    */ public abstract class DisconnectedScreenMixin extends class_437 {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_8667 field_44552;
/*    */   @Unique
/*    */   private class_4185 reconnectBtn;
/*    */   @Unique
/* 33 */   private double time = ((Double)((AutoReconnect)Modules.get().get(AutoReconnect.class)).time.get()).doubleValue() * 20.0D;
/*    */   
/*    */   protected DisconnectedScreenMixin(class_2561 title) {
/* 36 */     super(title);
/*    */   }
/*    */   
/*    */   @Inject(method = {"method_25426"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/class_8667;method_48222()V", shift = At.Shift.BEFORE)})
/*    */   private void addButtons(CallbackInfo ci) {
/* 41 */     AutoReconnect autoReconnect = (AutoReconnect)Modules.get().get(AutoReconnect.class);
/*    */     
/* 43 */     if (autoReconnect.lastServerConnection != null && !((Boolean)autoReconnect.button.get()).booleanValue()) {
/* 44 */       this.reconnectBtn = (new class_4185.class_7840((class_2561)class_2561.method_43470(getText()), button -> tryConnecting())).method_46431();
/* 45 */       this.field_44552.method_52736((class_8021)this.reconnectBtn);
/*    */       
/* 47 */       this.field_44552.method_52736((class_8021)(new class_4185.class_7840(
/* 48 */             (class_2561)class_2561.method_43470("Toggle Auto Reconnect"), button -> {
/*    */               autoReconnect.toggle();
/*    */               this.reconnectBtn.method_25355((class_2561)class_2561.method_43470(getText()));
/*    */               this.time = ((Double)autoReconnect.time.get()).doubleValue() * 20.0D;
/* 52 */             })).method_46431());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void method_25393() {
/* 59 */     AutoReconnect autoReconnect = (AutoReconnect)Modules.get().get(AutoReconnect.class);
/* 60 */     if (!autoReconnect.isActive() || autoReconnect.lastServerConnection == null)
/*    */       return; 
/* 62 */     if (this.time <= 0.0D) {
/* 63 */       tryConnecting();
/*    */     } else {
/* 65 */       this.time--;
/* 66 */       if (this.reconnectBtn != null) this.reconnectBtn.method_25355((class_2561)class_2561.method_43470(getText())); 
/*    */     } 
/*    */   }
/*    */   
/*    */   @Unique
/*    */   private String getText() {
/* 72 */     String reconnectText = "Reconnect";
/* 73 */     if (Modules.get().isActive(AutoReconnect.class)) reconnectText = reconnectText + " " + reconnectText; 
/* 74 */     return reconnectText;
/*    */   }
/*    */   
/*    */   @Unique
/*    */   private void tryConnecting() {
/* 79 */     Pair<class_639, class_642> lastServer = ((AutoReconnect)Modules.get().get(AutoReconnect.class)).lastServerConnection;
/* 80 */     class_412.method_36877((class_437)new class_442(), MeteorClient.mc, (class_639)lastServer.left(), (class_642)lastServer.right(), false, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\DisconnectedScreenMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */