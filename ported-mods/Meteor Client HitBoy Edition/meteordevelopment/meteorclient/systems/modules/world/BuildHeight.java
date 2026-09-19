/*    */ package meteordevelopment.meteorclient.systems.modules.world;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*    */ import meteordevelopment.meteorclient.mixin.BlockHitResultAccessor;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2885;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuildHeight
/*    */   extends Module
/*    */ {
/*    */   public BuildHeight() {
/* 18 */     super(Categories.World, "build-height", "Allows you to interact with objects at the build limit.");
/*    */   }
/*    */   @EventHandler
/*    */   private void onSendPacket(PacketEvent.Send event) {
/*    */     class_2885 p;
/* 23 */     class_2596 class_2596 = event.packet; if (class_2596 instanceof class_2885) { p = (class_2885)class_2596; } else { return; }
/* 24 */      if (this.mc.field_1687 == null)
/* 25 */       return;  if ((p.method_12543().method_17784()).field_1351 >= this.mc.field_1687.method_31605() && p.method_12543().method_17780() == class_2350.field_11036)
/* 26 */       ((BlockHitResultAccessor)p.method_12543()).meteor$setSide(class_2350.field_11033); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\BuildHeight.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */