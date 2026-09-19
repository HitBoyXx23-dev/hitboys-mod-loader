/*    */ package meteordevelopment.meteorclient.systems.modules.movement.speed.modes;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
/*    */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.Anchor;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedMode;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedModes;
/*    */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*    */ import net.minecraft.class_1294;
/*    */ import net.minecraft.class_243;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Vanilla
/*    */   extends SpeedMode
/*    */ {
/*    */   public Vanilla() {
/* 20 */     super(SpeedModes.Vanilla);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onMove(PlayerMoveEvent event) {
/* 25 */     class_243 vel = PlayerUtils.getHorizontalVelocity(((Double)this.settings.vanillaSpeed.get()).doubleValue());
/* 26 */     double velX = vel.method_10216();
/* 27 */     double velZ = vel.method_10215();
/*    */     
/* 29 */     if (this.mc.field_1724.method_6059(class_1294.field_5904)) {
/* 30 */       double value = (this.mc.field_1724.method_6112(class_1294.field_5904).method_5578() + 1) * 0.205D;
/* 31 */       velX += velX * value;
/* 32 */       velZ += velZ * value;
/*    */     } 
/*    */     
/* 35 */     Anchor anchor = (Anchor)Modules.get().get(Anchor.class);
/* 36 */     if (anchor.isActive() && anchor.controlMovement) {
/* 37 */       velX = anchor.deltaX;
/* 38 */       velZ = anchor.deltaZ;
/*    */     } 
/*    */     
/* 41 */     ((IVec3d)event.movement).meteor$set(velX, event.movement.field_1351, velZ);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\speed\modes\Vanilla.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */