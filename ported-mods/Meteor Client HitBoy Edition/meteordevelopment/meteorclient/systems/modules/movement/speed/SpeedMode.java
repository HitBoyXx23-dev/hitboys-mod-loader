/*    */ package meteordevelopment.meteorclient.systems.modules.movement.speed;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import net.minecraft.class_1293;
/*    */ import net.minecraft.class_1294;
/*    */ import net.minecraft.class_310;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SpeedMode
/*    */ {
/*    */   protected final class_310 mc;
/*    */   protected final Speed settings;
/*    */   private final SpeedModes type;
/*    */   protected int stage;
/*    */   protected double distance;
/*    */   protected double speed;
/*    */   
/*    */   public void onTick() {}
/*    */   
/*    */   public SpeedMode(SpeedModes type) {
/* 23 */     this.settings = (Speed)Modules.get().get(Speed.class);
/* 24 */     this.mc = class_310.method_1551();
/* 25 */     this.type = type;
/* 26 */     reset();
/*    */   }
/*    */   
/*    */   public void onMove(PlayerMoveEvent event) {}
/*    */   
/*    */   public void onRubberband() {
/* 32 */     reset();
/*    */   }
/*    */   
/*    */   public void onActivate() {}
/*    */   
/*    */   protected double getDefaultSpeed() {
/* 38 */     double defaultSpeed = 0.2873D;
/* 39 */     if (this.mc.field_1724.method_6059(class_1294.field_5904)) {
/* 40 */       int amplifier = this.mc.field_1724.method_6112(class_1294.field_5904).method_5578();
/* 41 */       defaultSpeed *= 1.0D + 0.2D * (amplifier + 1);
/*    */     } 
/* 43 */     if (this.mc.field_1724.method_6059(class_1294.field_5909)) {
/* 44 */       int amplifier = this.mc.field_1724.method_6112(class_1294.field_5909).method_5578();
/* 45 */       defaultSpeed /= 1.0D + 0.2D * (amplifier + 1);
/*    */     } 
/* 47 */     return defaultSpeed;
/*    */   }
/*    */   public void onDeactivate() {}
/*    */   protected void reset() {
/* 51 */     this.stage = 0;
/* 52 */     this.distance = 0.0D;
/* 53 */     this.speed = 0.2873D;
/*    */   }
/*    */   
/*    */   protected double getHop(double height) {
/* 57 */     class_1293 jumpBoost = this.mc.field_1724.method_6059(class_1294.field_5913) ? this.mc.field_1724.method_6112(class_1294.field_5913) : null;
/* 58 */     if (jumpBoost != null) height += ((jumpBoost.method_5578() + 1) * 0.1F); 
/* 59 */     return height;
/*    */   }
/*    */   
/*    */   public String getHudString() {
/* 63 */     return this.type.name();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\speed\SpeedMode.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */