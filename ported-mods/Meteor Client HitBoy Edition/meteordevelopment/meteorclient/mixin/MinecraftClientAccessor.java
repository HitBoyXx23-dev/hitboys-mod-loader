/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.authlib.minecraft.UserApiService;
/*    */ import com.mojang.authlib.yggdrasil.ProfileResult;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.class_1071;
/*    */ import net.minecraft.class_310;
/*    */ import net.minecraft.class_320;
/*    */ import net.minecraft.class_5520;
/*    */ import net.minecraft.class_6360;
/*    */ import net.minecraft.class_7497;
/*    */ import net.minecraft.class_7574;
/*    */ import net.minecraft.class_7853;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Mutable;
/*    */ import org.spongepowered.asm.mixin.gen.Accessor;
/*    */ import org.spongepowered.asm.mixin.gen.Invoker;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_310.class})
/*    */ public interface MinecraftClientAccessor
/*    */ {
/*    */   @Accessor("field_1738")
/*    */   static int meteor$getFps() {
/* 29 */     return 0;
/*    */   }
/*    */   
/*    */   @Mutable
/*    */   @Accessor("field_1726")
/*    */   void meteor$setSession(class_320 paramclass_320);
/*    */   
/*    */   @Accessor("field_33697")
/*    */   class_6360 meteor$getResourceReloadLogger();
/*    */   
/*    */   @Accessor("field_1771")
/*    */   int meteor$getAttackCooldown();
/*    */   
/*    */   @Accessor("field_1771")
/*    */   void meteor$setAttackCooldown(int paramInt);
/*    */   
/*    */   @Invoker("method_1536")
/*    */   boolean meteor$leftClick();
/*    */   
/*    */   @Mutable
/*    */   @Accessor("field_39068")
/*    */   void meteor$setProfileKeys(class_7853 paramclass_7853);
/*    */   
/*    */   @Mutable
/*    */   @Accessor("field_26902")
/*    */   void meteor$setUserApiService(UserApiService paramUserApiService);
/*    */   
/*    */   @Mutable
/*    */   @Accessor("field_1707")
/*    */   void meteor$setSkinProvider(class_1071 paramclass_1071);
/*    */   
/*    */   @Mutable
/*    */   @Accessor("field_26842")
/*    */   void meteor$setSocialInteractionsManager(class_5520 paramclass_5520);
/*    */   
/*    */   @Mutable
/*    */   @Accessor("field_39492")
/*    */   void meteor$setAbuseReportContext(class_7574 paramclass_7574);
/*    */   
/*    */   @Mutable
/*    */   @Accessor("field_45899")
/*    */   void meteor$setGameProfileFuture(CompletableFuture<ProfileResult> paramCompletableFuture);
/*    */   
/*    */   @Mutable
/*    */   @Accessor("field_62106")
/*    */   void meteor$setApiServices(class_7497 paramclass_7497);
/*    */   
/*    */   @Invoker("method_1508")
/*    */   void meteor$handleInputEvents();
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\MinecraftClientAccessor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */