/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
/*    */ import com.llamalad7.mixinextras.sugar.Local;
/*    */ import java.nio.file.Path;
/*    */ import java.util.UUID;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import net.minecraft.class_9028;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_9028.class})
/*    */ public class DownloaderMixin
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   private Path field_47573;
/*    */   
/*    */   @ModifyExpressionValue(method = {"method_55485"}, at = {@At(value = "INVOKE", target = "Ljava/nio/file/Path;resolve(Ljava/lang/String;)Ljava/nio/file/Path;")})
/*    */   private Path hookResolve(Path original, @Local(argsOnly = true) UUID id) {
/* 38 */     UUID accountId = MeteorClient.mc.method_1548().method_44717();
/* 39 */     if (accountId == null) {
/* 40 */       MeteorClient.LOG.warn("Failed to change resource pack download directory because the account id is null.");
/* 41 */       return original;
/*    */     } 
/*    */     
/* 44 */     return this.field_47573.resolve(accountId.toString()).resolve(id.toString());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\DownloaderMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */