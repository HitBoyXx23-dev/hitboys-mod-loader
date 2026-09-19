/*    */ package meteordevelopment.meteorclient.systems.modules.movement;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.meteor.KeyEvent;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.Freecam;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.KeyAction;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AirJump
/*    */   extends Module
/*    */ {
/* 21 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 23 */   private final Setting<Boolean> maintainLevel = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 24 */       .name("maintain-level"))
/* 25 */       .description("Maintains your current Y level when holding the jump key."))
/* 26 */       .defaultValue(Boolean.valueOf(false)))
/* 27 */       .build());
/*    */   
/*    */   private int level;
/*    */ 
/*    */   
/*    */   public AirJump() {
/* 33 */     super(Categories.Movement, "air-jump", "Lets you jump in the air.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void onActivate() {
/* 38 */     this.level = this.mc.field_1724.method_24515().method_10264();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onKey(KeyEvent event) {
/* 43 */     if (Modules.get().isActive(Freecam.class) || this.mc.field_1755 != null || this.mc.field_1724.method_24828())
/*    */       return; 
/* 45 */     if (event.action != KeyAction.Press)
/*    */       return; 
/* 47 */     if (this.mc.field_1690.field_1903.method_1417(event.input)) {
/* 48 */       this.level = this.mc.field_1724.method_24515().method_10264();
/* 49 */       this.mc.field_1724.method_6043();
/*    */     }
/* 51 */     else if (this.mc.field_1690.field_1832.method_1417(event.input)) {
/* 52 */       this.level--;
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Pre event) {
/* 58 */     if (Modules.get().isActive(Freecam.class) || this.mc.field_1724.method_24828())
/*    */       return; 
/* 60 */     if (((Boolean)this.maintainLevel.get()).booleanValue() && this.mc.field_1724.method_24515().method_10264() == this.level && this.mc.field_1690.field_1903.method_1434())
/* 61 */       this.mc.field_1724.method_6043(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\AirJump.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */