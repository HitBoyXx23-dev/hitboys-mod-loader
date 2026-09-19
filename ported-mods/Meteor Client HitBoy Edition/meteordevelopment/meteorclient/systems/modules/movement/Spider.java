/*    */ package meteordevelopment.meteorclient.systems.modules.movement;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_243;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Spider
/*    */   extends Module
/*    */ {
/* 18 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 20 */   private final Setting<Double> speed = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 21 */       .name("climb-speed"))
/* 22 */       .description("The speed you go up blocks."))
/* 23 */       .defaultValue(0.2D)
/* 24 */       .min(0.0D)
/* 25 */       .build());
/*    */ 
/*    */   
/*    */   public Spider() {
/* 29 */     super(Categories.Movement, "spider", "Allows you to climb walls like a spider.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 34 */     if (!this.mc.field_1724.field_5976)
/*    */       return; 
/* 36 */     class_243 velocity = this.mc.field_1724.method_18798();
/* 37 */     if (velocity.field_1351 >= 0.2D)
/*    */       return; 
/* 39 */     this.mc.field_1724.method_18800(velocity.field_1352, ((Double)this.speed.get()).doubleValue(), velocity.field_1350);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\Spider.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */