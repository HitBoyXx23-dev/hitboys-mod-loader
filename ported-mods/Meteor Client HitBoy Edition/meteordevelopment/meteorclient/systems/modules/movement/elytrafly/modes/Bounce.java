/*     */ package meteordevelopment.meteorclient.systems.modules.movement.elytrafly.modes;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.elytrafly.ElytraFlightMode;
/*     */ import meteordevelopment.meteorclient.systems.modules.movement.elytrafly.ElytraFlightModes;
/*     */ import meteordevelopment.meteorclient.systems.modules.player.Rotation;
/*     */ import net.minecraft.class_1294;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1304;
/*     */ import net.minecraft.class_1309;
/*     */ import net.minecraft.class_2596;
/*     */ import net.minecraft.class_2680;
/*     */ import net.minecraft.class_2848;
/*     */ import net.minecraft.class_3481;
/*     */ import net.minecraft.class_746;
/*     */ 
/*     */ 
/*     */ public class Bounce
/*     */   extends ElytraFlightMode
/*     */ {
/*     */   boolean rubberbanded;
/*     */   int tickDelay;
/*     */   double prevFov;
/*     */   
/*     */   public Bounce() {
/*  28 */     super(ElytraFlightModes.Bounce);
/*     */ 
/*     */     
/*  31 */     this.rubberbanded = false;
/*     */     
/*  33 */     this.tickDelay = ((Integer)this.elytraFly.restartDelay.get()).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onTick() {
/*  38 */     super.onTick();
/*     */     
/*  40 */     if (this.mc.field_1690.field_1903.method_1434() && !this.mc.field_1724.method_6128() && !((Boolean)this.elytraFly.manualTakeoff.get()).booleanValue()) this.mc.method_1562().method_52787((class_2596)new class_2848((class_1297)this.mc.field_1724, class_2848.class_2849.field_12982));
/*     */ 
/*     */     
/*  43 */     if (checkConditions(this.mc.field_1724)) {
/*  44 */       if (!this.rubberbanded) {
/*  45 */         if (this.prevFov != 0.0D && !((Boolean)this.elytraFly.sprint.get()).booleanValue()) this.mc.field_1690.method_42454().method_41748(Double.valueOf(0.0D)); 
/*  46 */         if (((Boolean)this.elytraFly.autoJump.get()).booleanValue()) this.mc.field_1690.field_1903.method_23481(true); 
/*  47 */         this.mc.field_1690.field_1894.method_23481(true);
/*  48 */         this.mc.field_1724.method_36456(getYawDirection());
/*  49 */         if (((Boolean)this.elytraFly.lockPitch.get()).booleanValue()) this.mc.field_1724.method_36457(((Double)this.elytraFly.pitch.get()).floatValue());
/*     */       
/*     */       } 
/*  52 */       if (!((Boolean)this.elytraFly.sprint.get()).booleanValue())
/*     */       {
/*  54 */         if (this.mc.field_1724.method_6128()) { this.mc.field_1724.method_5728(this.mc.field_1724.method_24828()); }
/*  55 */         else { this.mc.field_1724.method_5728(true); }
/*     */       
/*     */       }
/*     */       
/*  59 */       if (this.rubberbanded && ((Boolean)this.elytraFly.restart.get()).booleanValue()) {
/*  60 */         if (this.tickDelay > 0) {
/*  61 */           this.tickDelay--;
/*     */         } else {
/*  63 */           this.mc.method_1562().method_52787((class_2596)new class_2848((class_1297)this.mc.field_1724, class_2848.class_2849.field_12982));
/*  64 */           this.rubberbanded = false;
/*  65 */           this.tickDelay = ((Integer)this.elytraFly.restartDelay.get()).intValue();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPreTick() {
/*  73 */     super.onPreTick();
/*     */     
/*  75 */     if (checkConditions(this.mc.field_1724) && ((Boolean)this.elytraFly.sprint.get()).booleanValue()) this.mc.field_1724.method_5728(true); 
/*     */   }
/*     */   
/*     */   private void unpress() {
/*  79 */     this.mc.field_1690.field_1894.method_23481(false);
/*  80 */     if (((Boolean)this.elytraFly.autoJump.get()).booleanValue()) this.mc.field_1690.field_1903.method_23481(false);
/*     */   
/*     */   }
/*     */   
/*     */   public void onPacketReceive(PacketEvent.Receive event) {
/*  85 */     if (event.packet instanceof net.minecraft.class_2708) {
/*  86 */       this.rubberbanded = true;
/*  87 */       this.mc.field_1724.method_66281();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketSend(PacketEvent.Send event) {
/*  93 */     if (event.packet instanceof class_2848 && ((class_2848)event.packet).method_12365().equals(class_2848.class_2849.field_12982) && !((Boolean)this.elytraFly.sprint.get()).booleanValue()) {
/*  94 */       this.mc.field_1724.method_5728(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean recastElytra(class_746 player) {
/*  99 */     if (checkConditions(player) && startGliding(player)) {
/* 100 */       player.field_3944.method_52787((class_2596)new class_2848((class_1297)player, class_2848.class_2849.field_12982));
/* 101 */       return true;
/* 102 */     }  return false;
/*     */   }
/*     */   
/*     */   public static boolean checkConditions(class_746 player) {
/* 106 */     class_2680 blockState = player.method_55667();
/* 107 */     boolean isClimbing = (blockState.method_26164(class_3481.field_22414) && !blockState.method_26164(class_3481.field_63247));
/* 108 */     return (!(player.method_31549()).field_7479 && !player.method_5765() && !isClimbing && !player.method_5799() && !player.method_6059(class_1294.field_5902));
/*     */   }
/*     */   
/*     */   private static boolean startGliding(class_746 player) {
/* 112 */     for (class_1304 equipmentSlot : class_1304.field_54086) {
/* 113 */       if (class_1309.method_63624(player.method_6118(equipmentSlot), equipmentSlot)) {
/* 114 */         Objects.requireNonNull(player); MeteorClient.mc.method_40000(player::method_23669);
/* 115 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   private float getYawDirection() {
/* 123 */     switch ((Rotation.LockMode)this.elytraFly.yawLockMode.get()) { default: throw new MatchException(null, null);case None: case Smart: case Simple: break; }  return (
/*     */ 
/*     */       
/* 126 */       (Double)this.elytraFly.yaw.get()).floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onActivate() {
/* 133 */     this.prevFov = ((Double)this.mc.field_1690.method_42454().method_41753()).doubleValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeactivate() {
/* 138 */     unpress();
/* 139 */     this.rubberbanded = false;
/* 140 */     if (this.prevFov != 0.0D && !((Boolean)this.elytraFly.sprint.get()).booleanValue()) this.mc.field_1690.method_42454().method_41748(Double.valueOf(this.prevFov)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\elytrafly\modes\Bounce.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */