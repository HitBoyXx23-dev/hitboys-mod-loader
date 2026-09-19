/*    */ package meteordevelopment.meteorclient.systems.modules.world;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*    */ import meteordevelopment.meteorclient.mixininterface.IPlayerInteractEntityC2SPacket;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2824;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MountBypass
/*    */   extends Module
/*    */ {
/*    */   private boolean dontCancel;
/*    */   
/*    */   public MountBypass() {
/* 20 */     super(Categories.World, "mount-bypass", "Allows you to bypass the IllegalStacks plugin and put chests on entities.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onSendPacket(PacketEvent.Send event) {
/* 25 */     if (this.dontCancel) {
/* 26 */       this.dontCancel = false;
/*    */       
/*    */       return;
/*    */     } 
/* 30 */     class_2596 class_2596 = event.packet; if (class_2596 instanceof IPlayerInteractEntityC2SPacket) { IPlayerInteractEntityC2SPacket packet = (IPlayerInteractEntityC2SPacket)class_2596;
/* 31 */       if (packet.meteor$getType() == class_2824.class_5907.field_29173 && packet.meteor$getEntity() instanceof net.minecraft.class_1492) event.cancel();  }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\MountBypass.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */