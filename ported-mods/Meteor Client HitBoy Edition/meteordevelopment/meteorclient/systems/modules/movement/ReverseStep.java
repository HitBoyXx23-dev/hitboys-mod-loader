/*    */ package meteordevelopment.meteorclient.systems.modules.movement;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.mixininterface.IVec3d;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReverseStep
/*    */   extends Module
/*    */ {
/* 22 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 24 */   private final Setting<Double> fallSpeed = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 25 */       .name("fall-speed"))
/* 26 */       .description("How fast to fall in blocks per second."))
/* 27 */       .defaultValue(3.0D)
/* 28 */       .min(0.0D)
/* 29 */       .build());
/*    */ 
/*    */   
/* 32 */   private final Setting<Double> fallDistance = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 33 */       .name("fall-distance"))
/* 34 */       .description("The maximum fall distance this setting will activate at."))
/* 35 */       .defaultValue(3.0D)
/* 36 */       .min(0.0D)
/* 37 */       .build());
/*    */ 
/*    */   
/* 40 */   private final Setting<Boolean> vehicles = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 41 */       .name("vehicles"))
/* 42 */       .description("Whether or not reverse step should affect vehicles."))
/* 43 */       .defaultValue(Boolean.valueOf(false)))
/* 44 */       .build());
/*    */ 
/*    */   
/*    */   public ReverseStep() {
/* 48 */     super(Categories.Movement, "reverse-step", "Allows you to fall down blocks at a greater speed.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 53 */     class_1297 vehicle = this.mc.field_1724.method_5854();
/* 54 */     if (vehicle != null && ((Boolean)this.vehicles.get()).booleanValue()) {
/* 55 */       if (canSnap(vehicle)) {
/* 56 */         ((IVec3d)vehicle.method_18798()).meteor$setY(-((Double)this.fallSpeed.get()).doubleValue());
/*    */       }
/*    */     } else {
/* 59 */       if (this.mc.field_1724.method_21754() || (this.mc.field_1724.field_6250 == 0.0F && this.mc.field_1724.field_6212 == 0.0F))
/* 60 */         return;  if (!isOnBed() && canSnap((class_1297)this.mc.field_1724)) {
/* 61 */         ((IVec3d)this.mc.field_1724.method_18798()).meteor$setY(-((Double)this.fallSpeed.get()).doubleValue());
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean canSnap(class_1297 entity) {
/* 67 */     if (!entity.method_24828() || entity.method_5869() || entity.method_5771() || this.mc.field_1690.field_1903.method_1434() || entity.field_5960)
/* 68 */       return false; 
/* 69 */     return !this.mc.field_1687.method_18026(entity.method_5829().method_989(0.0D, (float)-(((Double)this.fallDistance.get()).doubleValue() + 0.01D), 0.0D));
/*    */   }
/*    */   
/*    */   private boolean isOnBed() {
/* 73 */     class_2338.class_2339 blockPos = this.mc.field_1724.method_24515().method_25503();
/*    */     
/* 75 */     if (check(blockPos, 0, 0)) return true;
/*    */     
/* 77 */     double xa = this.mc.field_1724.method_23317() - blockPos.method_10263();
/* 78 */     double za = this.mc.field_1724.method_23321() - blockPos.method_10260();
/*    */     
/* 80 */     if (xa >= 0.0D && xa <= 0.3D && check(blockPos, -1, 0)) return true; 
/* 81 */     if (xa >= 0.7D && check(blockPos, 1, 0)) return true; 
/* 82 */     if (za >= 0.0D && za <= 0.3D && check(blockPos, 0, -1)) return true; 
/* 83 */     if (za >= 0.7D && check(blockPos, 0, 1)) return true;
/*    */     
/* 85 */     if (xa >= 0.0D && xa <= 0.3D && za >= 0.0D && za <= 0.3D && check(blockPos, -1, -1)) return true; 
/* 86 */     if (xa >= 0.0D && xa <= 0.3D && za >= 0.7D && check(blockPos, -1, 1)) return true; 
/* 87 */     if (xa >= 0.7D && za >= 0.0D && za <= 0.3D && check(blockPos, 1, -1)) return true; 
/* 88 */     return (xa >= 0.7D && za >= 0.7D && check(blockPos, 1, 1));
/*    */   }
/*    */   
/*    */   private boolean check(class_2338.class_2339 blockPos, int x, int z) {
/* 92 */     blockPos.method_10100(x, 0, z);
/* 93 */     boolean is = this.mc.field_1687.method_8320((class_2338)blockPos).method_26204() instanceof net.minecraft.class_2244;
/* 94 */     blockPos.method_10100(-x, 0, -z);
/*    */     
/* 96 */     return is;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\ReverseStep.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */