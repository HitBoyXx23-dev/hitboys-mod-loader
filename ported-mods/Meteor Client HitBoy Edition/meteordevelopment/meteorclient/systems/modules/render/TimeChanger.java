/*    */ package meteordevelopment.meteorclient.systems.modules.render;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_2761;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TimeChanger
/*    */   extends Module
/*    */ {
/* 19 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 21 */   private final Setting<Double> time = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 22 */       .name("time"))
/* 23 */       .description("The specified time to be set."))
/* 24 */       .defaultValue(0.0D)
/* 25 */       .sliderRange(-20000.0D, 20000.0D)
/* 26 */       .build());
/*    */   
/*    */   long oldTime;
/*    */ 
/*    */   
/*    */   public TimeChanger() {
/* 32 */     super(Categories.Render, "time-changer", "Makes you able to set a custom time.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void onActivate() {
/* 37 */     this.oldTime = this.mc.field_1687.method_75260();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDeactivate() {
/* 42 */     this.mc.field_1687.method_28104().method_165(this.oldTime);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onPacketReceive(PacketEvent.Receive event) {
/* 47 */     if (event.packet instanceof class_2761) {
/* 48 */       this.oldTime = ((class_2761)event.packet).comp_3220();
/* 49 */       event.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 55 */     this.mc.field_1687.method_28104().method_165(((Double)this.time.get()).longValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\TimeChanger.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */