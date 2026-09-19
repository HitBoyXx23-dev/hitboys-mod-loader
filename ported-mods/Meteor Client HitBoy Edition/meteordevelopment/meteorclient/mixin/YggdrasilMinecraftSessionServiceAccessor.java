/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.authlib.Environment;
/*    */ import com.mojang.authlib.yggdrasil.ServicesKeySet;
/*    */ import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
/*    */ import java.net.Proxy;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.gen.Invoker;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({YggdrasilMinecraftSessionService.class})
/*    */ public interface YggdrasilMinecraftSessionServiceAccessor
/*    */ {
/*    */   @Invoker("<init>")
/*    */   static YggdrasilMinecraftSessionService meteor$createYggdrasilMinecraftSessionService(ServicesKeySet servicesKeySet, Proxy proxy, Environment env) {
/* 20 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\YggdrasilMinecraftSessionServiceAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */