/*    */ package meteordevelopment.meteorclient.systems.modules.movement.elytrafly.modes;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.elytrafly.ElytraFlightMode;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.elytrafly.ElytraFlightModes;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Pitch40
/*    */   extends ElytraFlightMode
/*    */ {
/*    */   private boolean pitchingDown = true;
/*    */   private float pitch;
/*    */   
/*    */   public Pitch40() {
/* 17 */     super(ElytraFlightModes.Pitch40);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onActivate() {
/* 22 */     if (this.mc.field_1724.method_23318() < ((Double)this.elytraFly.pitch40upperBounds.get()).doubleValue()) {
/* 23 */       this.elytraFly.error("Player must be above upper bounds!", new Object[0]);
/* 24 */       this.elytraFly.toggle();
/* 25 */     } else if (this.mc.field_1724.method_23318() - 40.0D < ((Double)this.elytraFly.pitch40lowerBounds.get()).doubleValue()) {
/* 26 */       this.elytraFly.error("Player must be at least 40 blocks above the lower bounds!", new Object[0]);
/* 27 */       this.elytraFly.toggle();
/*    */     } 
/*    */     
/* 30 */     this.pitch = 37.72F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private float randPitch(float pitch, float bound) {
/* 40 */     return (float)(pitch + bound * (Math.random() - 0.5D));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onTick() {
/* 45 */     super.onTick();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 52 */     if (this.pitchingDown && this.mc.field_1724.method_23318() <= ((Double)this.elytraFly.pitch40lowerBounds.get()).doubleValue()) {
/* 53 */       this.pitchingDown = false;
/*    */     }
/* 55 */     else if (!this.pitchingDown && this.mc.field_1724.method_23318() >= ((Double)this.elytraFly.pitch40upperBounds.get()).doubleValue()) {
/* 56 */       this.pitchingDown = true;
/*    */     } 
/*    */ 
/*    */     
/* 60 */     if (!this.pitchingDown) {
/* 61 */       this.pitch -= randPitch(((Double)this.elytraFly.pitch40rotationSpeedUp.get()).floatValue(), 1.0F);
/*    */       
/* 63 */       if (this.pitch < -54.77F) {
/* 64 */         this.pitch = -54.77F;
/* 65 */         this.pitchingDown = true;
/*    */       }
/*    */     
/* 68 */     } else if (this.pitch < 37.72F) {
/* 69 */       this.pitch += randPitch(((Double)this.elytraFly.pitch40rotationSpeedDown.get()).floatValue(), 0.5F);
/*    */     } 
/*    */     
/* 72 */     this.mc.field_1724.method_36457(this.pitch);
/*    */   }
/*    */ 
/*    */   
/*    */   public void autoTakeoff() {}
/*    */ 
/*    */   
/*    */   public void handleHorizontalSpeed(PlayerMoveEvent event) {
/* 80 */     this.velX = event.movement.field_1352;
/* 81 */     this.velZ = event.movement.field_1350;
/*    */   }
/*    */   
/*    */   public void handleVerticalSpeed(PlayerMoveEvent event) {}
/*    */   
/*    */   public void handleFallMultiplier() {}
/*    */   
/*    */   public void handleAutopilot() {}
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\elytrafly\modes\Pitch40.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */