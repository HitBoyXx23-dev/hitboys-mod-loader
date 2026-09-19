/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import meteordevelopment.meteorclient.mixininterface.IChatHudLineVisible;
/*    */ import net.minecraft.class_2583;
/*    */ import net.minecraft.class_303;
/*    */ import net.minecraft.class_5481;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ 
/*    */ @Mixin({class_303.class_7590.class})
/*    */ public abstract class ChatHudLineVisibleMixin implements IChatHudLineVisible {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_5481 comp_896;
/*    */   @Unique
/*    */   private int id;
/*    */   @Unique
/*    */   private GameProfile sender;
/*    */   @Unique
/*    */   private boolean startOfEntry;
/*    */   
/*    */   public String meteor$getText() {
/* 26 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 28 */     this.comp_896.accept((index, style, codePoint) -> {
/*    */           sb.appendCodePoint(codePoint);
/*    */           
/*    */           return true;
/*    */         });
/* 33 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public int meteor$getId() {
/* 38 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$setId(int id) {
/* 43 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfile meteor$getSender() {
/* 48 */     return this.sender;
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$setSender(GameProfile profile) {
/* 53 */     this.sender = profile;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean meteor$isStartOfEntry() {
/* 58 */     return this.startOfEntry;
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$setStartOfEntry(boolean start) {
/* 63 */     this.startOfEntry = start;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ChatHudLineVisibleMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */