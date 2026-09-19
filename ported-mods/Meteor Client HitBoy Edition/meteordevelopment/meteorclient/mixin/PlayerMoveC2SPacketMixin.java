/*    */ package meteordevelopment.meteorclient.mixin;
/*    */ 
/*    */ import meteordevelopment.meteorclient.mixininterface.IPlayerMoveC2SPacket;
/*    */ import net.minecraft.class_2828;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.Unique;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Mixin({class_2828.class})
/*    */ public abstract class PlayerMoveC2SPacketMixin
/*    */   implements IPlayerMoveC2SPacket
/*    */ {
/*    */   @Unique
/*    */   private int tag;
/*    */   
/*    */   public void meteor$setTag(int tag) {
/* 18 */     this.tag = tag;
/*    */   }
/*    */   public int meteor$getTag() {
/* 21 */     return this.tag;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\mixin\PlayerMoveC2SPacketMixin.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */