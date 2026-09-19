/*    */ package meteordevelopment.meteorclient.systems.modules.movement.speed.modes;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
/*    */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.Anchor;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedMode;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.speed.SpeedModes;
/*    */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*    */ import org.joml.Vector2d;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Strafe
/*    */   extends SpeedMode
/*    */ {
/*    */   private long timer;
/*    */   
/*    */   public Strafe() {
/* 21 */     super(SpeedModes.Strafe);
/*    */ 
/*    */     
/* 24 */     this.timer = 0L;
/*    */   }
/*    */   
/*    */   public void onMove(PlayerMoveEvent event) {
/* 28 */     switch (this.stage) {
/*    */       case 0:
/* 30 */         if (PlayerUtils.isMoving()) {
/* 31 */           this.stage++;
/* 32 */           this.speed = 1.1799999475479126D * getDefaultSpeed() - 0.01D;
/*    */         } 
/*    */       case 1:
/* 35 */         if (!PlayerUtils.isMoving() || !this.mc.field_1724.method_24828())
/*    */           break; 
/* 37 */         ((IVec3d)event.movement).meteor$setY(getHop(0.40123128D));
/* 38 */         this.speed *= ((Double)this.settings.ncpSpeed.get()).doubleValue();
/* 39 */         this.stage++; break;
/*    */       case 2:
/* 41 */         this.speed = this.distance - 0.76D * (this.distance - getDefaultSpeed()); this.stage++; break;
/*    */       case 3:
/* 43 */         if (!this.mc.field_1687.method_18026(this.mc.field_1724.method_5829().method_989(0.0D, (this.mc.field_1724.method_18798()).field_1351, 0.0D)) || (this.mc.field_1724.field_5992 && this.stage > 0)) {
/* 44 */           this.stage = 0;
/*    */         }
/* 46 */         this.speed = this.distance - this.distance / 159.0D;
/*    */         break;
/*    */     } 
/*    */     
/* 50 */     this.speed = Math.max(this.speed, getDefaultSpeed());
/*    */     
/* 52 */     if (((Boolean)this.settings.ncpSpeedLimit.get()).booleanValue()) {
/* 53 */       if (System.currentTimeMillis() - this.timer > 2500L) {
/* 54 */         this.timer = System.currentTimeMillis();
/*    */       }
/*    */       
/* 57 */       this.speed = Math.min(this.speed, (System.currentTimeMillis() - this.timer > 1250L) ? 0.44D : 0.43D);
/*    */     } 
/*    */     
/* 60 */     Vector2d change = transformStrafe(this.speed);
/*    */     
/* 62 */     Anchor anchor = (Anchor)Modules.get().get(Anchor.class);
/* 63 */     if (anchor.isActive() && anchor.controlMovement) {
/* 64 */       change.set(anchor.deltaX, anchor.deltaZ);
/*    */     }
/*    */     
/* 67 */     ((IVec3d)event.movement).meteor$setXZ(change.x, change.y);
/*    */   }
/*    */   
/*    */   public static Vector2d transformStrafe(double speed) {
/* 71 */     float forward = Math.signum((MeteorClient.mc.field_1724.field_3913.method_3128()).field_1342);
/* 72 */     float side = Math.signum((MeteorClient.mc.field_1724.field_3913.method_3128()).field_1343);
/* 73 */     float yaw = MeteorClient.mc.field_1724.method_61415(MeteorClient.mc.method_61966().method_60637(true));
/*    */     
/* 75 */     if (forward == 0.0F && side == 0.0F) return new Vector2d();
/*    */     
/* 77 */     float strafe = 90.0F * side;
/* 78 */     if (forward != 0.0F) strafe *= forward * 0.5F;
/*    */     
/* 80 */     yaw -= strafe;
/* 81 */     if (forward < 0.0F) yaw -= 180.0F; 
/* 82 */     double yawRadians = Math.toRadians(yaw);
/*    */     
/* 84 */     return new Vector2d(-Math.sin(yawRadians) * speed, Math.cos(yawRadians) * speed);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 89 */     this.distance = Math.sqrt((this.mc.field_1724.method_23317() - this.mc.field_1724.field_6014) * (this.mc.field_1724.method_23317() - this.mc.field_1724.field_6014) + (this.mc.field_1724.method_23321() - this.mc.field_1724.field_5969) * (this.mc.field_1724.method_23321() - this.mc.field_1724.field_5969));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\speed\modes\Strafe.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */