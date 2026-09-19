/*     */ package meteordevelopment.meteorclient.systems.modules.movement.elytrafly;
/*     */ 
/*     */ import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*     */ import net.minecraft.class_1268;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1304;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1792;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_243;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2848;
/*     */ import net.minecraft.class_310;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ElytraFlightMode
/*     */ {
/*     */   protected final class_310 mc;
/*     */   protected final ElytraFly elytraFly;
/*     */   private final ElytraFlightModes type;
/*     */   protected boolean lastJumpPressed;
/*     */   protected boolean incrementJumpTimer;
/*     */   protected boolean lastForwardPressed;
/*     */   protected int jumpTimer;
/*     */   
/*     */   public ElytraFlightMode(ElytraFlightModes type) {
/*  37 */     this.elytraFly = (ElytraFly)Modules.get().get(ElytraFly.class);
/*  38 */     this.mc = class_310.method_1551();
/*  39 */     this.type = type;
/*     */   }
/*     */   protected double velX; protected double velY; protected double velZ; protected double ticksLeft; protected class_243 forward; protected class_243 right; protected double acceleration;
/*     */   public void onTick() {
/*  43 */     if (((Boolean)this.elytraFly.autoReplenish.get()).booleanValue()) {
/*  44 */       FindItemResult fireworks = InvUtils.find(new class_1792[] { class_1802.field_8639 });
/*     */       
/*  46 */       if (fireworks.found() && !fireworks.isHotbar()) {
/*  47 */         InvUtils.move().from(fireworks.slot()).toHotbar(((Integer)this.elytraFly.replenishSlot.get()).intValue() - 1);
/*     */       }
/*     */     } 
/*     */     
/*  51 */     if (((Boolean)this.elytraFly.replace.get()).booleanValue()) {
/*  52 */       class_1799 chestStack = this.mc.field_1724.method_6118(class_1304.field_6174);
/*     */       
/*  54 */       if (chestStack.method_7909() == class_1802.field_8833 && 
/*  55 */         chestStack.method_7936() - chestStack.method_7919() <= ((Integer)this.elytraFly.replaceDurability.get()).intValue()) {
/*  56 */         FindItemResult elytra = InvUtils.find(stack -> (stack.method_7936() - stack.method_7919() > ((Integer)this.elytraFly.replaceDurability.get()).intValue() && stack.method_7909() == class_1802.field_8833));
/*     */         
/*  58 */         InvUtils.move().from(elytra.slot()).toArmor(2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPreTick() {}
/*     */ 
/*     */   
/*     */   public void onPacketSend(PacketEvent.Send event) {}
/*     */ 
/*     */   
/*     */   public void onPacketReceive(PacketEvent.Receive event) {}
/*     */ 
/*     */   
/*     */   public void onPlayerMove() {}
/*     */ 
/*     */   
/*     */   public void onActivate() {
/*  77 */     this.lastJumpPressed = false;
/*  78 */     this.jumpTimer = 0;
/*  79 */     this.ticksLeft = 0.0D;
/*  80 */     this.acceleration = 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {}
/*     */   
/*     */   public void autoTakeoff() {
/*  87 */     if (this.incrementJumpTimer) this.jumpTimer++;
/*     */     
/*  89 */     boolean jumpPressed = this.mc.field_1690.field_1903.method_1434();
/*     */     
/*  91 */     if ((((Boolean)this.elytraFly.autoTakeOff.get()).booleanValue() && this.elytraFly.flightMode.get() != ElytraFlightModes.Pitch40 && this.elytraFly.flightMode.get() != ElytraFlightModes.Bounce) || (
/*  92 */       !((Boolean)this.elytraFly.manualTakeoff.get()).booleanValue() && this.elytraFly.flightMode.get() == ElytraFlightModes.Bounce && jumpPressed)) {
/*  93 */       if (!this.lastJumpPressed && !this.mc.field_1724.method_6128()) {
/*  94 */         this.jumpTimer = 0;
/*  95 */         this.incrementJumpTimer = true;
/*     */       } 
/*     */       
/*  98 */       if (this.jumpTimer >= 8) {
/*  99 */         this.jumpTimer = 0;
/* 100 */         this.incrementJumpTimer = false;
/* 101 */         this.mc.field_1724.method_6100(false);
/* 102 */         this.mc.field_1724.method_5728(true);
/* 103 */         this.mc.field_1724.method_6043();
/* 104 */         this.mc.method_1562().method_52787((class_2596)new class_2848((class_1297)this.mc.field_1724, class_2848.class_2849.field_12982));
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     this.lastJumpPressed = jumpPressed;
/*     */   }
/*     */   
/*     */   public void handleAutopilot() {
/* 112 */     if (!this.mc.field_1724.method_6128())
/*     */       return; 
/* 114 */     if (((Boolean)this.elytraFly.autoPilot.get()).booleanValue() && this.mc.field_1724.method_23318() > ((Double)this.elytraFly.autoPilotMinimumHeight.get()).doubleValue() && this.elytraFly.flightMode.get() != ElytraFlightModes.Bounce) {
/* 115 */       this.mc.field_1690.field_1894.method_23481(true);
/* 116 */       this.lastForwardPressed = true;
/*     */     } 
/*     */     
/* 119 */     if (((Boolean)this.elytraFly.useFireworks.get()).booleanValue()) {
/* 120 */       if (this.ticksLeft <= 0.0D) {
/* 121 */         this.ticksLeft = ((Double)this.elytraFly.autoPilotFireworkDelay.get()).doubleValue() * 20.0D;
/*     */         
/* 123 */         FindItemResult itemResult = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8639 });
/* 124 */         if (!itemResult.found())
/*     */           return; 
/* 126 */         if (itemResult.isOffhand()) {
/* 127 */           this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, class_1268.field_5810);
/* 128 */           this.mc.field_1724.method_6104(class_1268.field_5810);
/*     */         } else {
/* 130 */           InvUtils.swap(itemResult.slot(), true);
/*     */           
/* 132 */           this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, class_1268.field_5808);
/* 133 */           this.mc.field_1724.method_6104(class_1268.field_5808);
/*     */           
/* 135 */           InvUtils.swapBack();
/*     */         } 
/*     */       } 
/* 138 */       this.ticksLeft--;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleHorizontalSpeed(PlayerMoveEvent event) {
/* 143 */     boolean a = false;
/* 144 */     boolean b = false;
/*     */     
/* 146 */     if (this.mc.field_1690.field_1894.method_1434()) {
/* 147 */       this.velX += this.forward.field_1352 * getSpeed() * 10.0D;
/* 148 */       this.velZ += this.forward.field_1350 * getSpeed() * 10.0D;
/* 149 */       a = true;
/* 150 */     } else if (this.mc.field_1690.field_1881.method_1434()) {
/* 151 */       this.velX -= this.forward.field_1352 * getSpeed() * 10.0D;
/* 152 */       this.velZ -= this.forward.field_1350 * getSpeed() * 10.0D;
/* 153 */       a = true;
/*     */     } 
/*     */     
/* 156 */     if (this.mc.field_1690.field_1849.method_1434()) {
/* 157 */       this.velX += this.right.field_1352 * getSpeed() * 10.0D;
/* 158 */       this.velZ += this.right.field_1350 * getSpeed() * 10.0D;
/* 159 */       b = true;
/* 160 */     } else if (this.mc.field_1690.field_1913.method_1434()) {
/* 161 */       this.velX -= this.right.field_1352 * getSpeed() * 10.0D;
/* 162 */       this.velZ -= this.right.field_1350 * getSpeed() * 10.0D;
/* 163 */       b = true;
/*     */     } 
/*     */     
/* 166 */     if (a && b) {
/* 167 */       double diagonal = 1.0D / Math.sqrt(2.0D);
/* 168 */       this.velX *= diagonal;
/* 169 */       this.velZ *= diagonal;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleVerticalSpeed(PlayerMoveEvent event) {
/* 174 */     if (this.mc.field_1690.field_1903.method_1434()) { this.velY += 0.5D * ((Double)this.elytraFly.verticalSpeed.get()).doubleValue(); }
/* 175 */     else if (this.mc.field_1690.field_1832.method_1434()) { this.velY -= 0.5D * ((Double)this.elytraFly.verticalSpeed.get()).doubleValue(); }
/*     */   
/*     */   }
/*     */   public void handleFallMultiplier() {
/* 179 */     if (this.velY < 0.0D) { this.velY *= ((Double)this.elytraFly.fallMultiplier.get()).doubleValue(); }
/* 180 */     else if (this.velY > 0.0D) { this.velY = 0.0D; }
/*     */   
/*     */   }
/*     */   public void handleAcceleration() {
/* 184 */     if (((Boolean)this.elytraFly.acceleration.get()).booleanValue()) {
/* 185 */       if (!PlayerUtils.isMoving()) this.acceleration = 0.0D; 
/* 186 */       this.acceleration = Math.min(this.acceleration + ((Double)this.elytraFly.accelerationMin
/* 187 */           .get()).doubleValue() + ((Double)this.elytraFly.accelerationStep.get()).doubleValue() * 0.1D, ((Double)this.elytraFly.horizontalSpeed
/* 188 */           .get()).doubleValue());
/*     */     } else {
/*     */       
/* 191 */       this.acceleration = 0.0D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void zeroAcceleration() {
/* 196 */     this.acceleration = 0.0D;
/*     */   }
/*     */   
/*     */   protected double getSpeed() {
/* 200 */     return ((Boolean)this.elytraFly.acceleration.get()).booleanValue() ? this.acceleration : ((Double)this.elytraFly.horizontalSpeed.get()).doubleValue();
/*     */   }
/*     */   
/*     */   public String getHudString() {
/* 204 */     return this.type.name();
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\elytrafly\ElytraFlightMode.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */