/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import meteordevelopment.meteorclient.mixininterface.IChatHudLine;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_303;
/*    */ import org.spongepowered.asm.mixin.Final;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Shadow;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ 
/*    */ @Mixin({class_303.class})
/*    */ public abstract class ChatHudLineMixin
/*    */   implements IChatHudLine
/*    */ {
/*    */   @Shadow
/*    */   @Final
/*    */   private class_2561 comp_893;
/*    */   @Unique
/*    */   private int id;
/*    */   @Unique
/*    */   private GameProfile sender;
/*    */   
/*    */   public String meteor$getText() {
/* 25 */     return this.comp_893.getString();
/*    */   }
/*    */ 
/*    */   
/*    */   public int meteor$getId() {
/* 30 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$setId(int id) {
/* 35 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfile meteor$getSender() {
/* 40 */     return this.sender;
/*    */   }
/*    */ 
/*    */   
/*    */   public void meteor$setSender(GameProfile profile) {
/* 45 */     this.sender = profile;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\ChatHudLineMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */