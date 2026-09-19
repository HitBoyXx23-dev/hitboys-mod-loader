/*    */ package meteordevelopment.meteorclient.systems.modules.world;
/*    */ 
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Timer
/*    */   extends Module
/*    */ {
/* 15 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 17 */   private final Setting<Double> multiplier = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 18 */       .name("multiplier"))
/* 19 */       .description("The timer multiplier amount."))
/* 20 */       .defaultValue(1.0D)
/* 21 */       .min(0.1D)
/* 22 */       .sliderMin(0.1D)
/* 23 */       .build());
/*    */   
/*    */   public static final double OFF = 1.0D;
/*    */   
/* 27 */   private double override = 1.0D;
/*    */   
/*    */   public Timer() {
/* 30 */     super(Categories.World, "timer", "Changes the speed of everything in your game.");
/*    */   }
/*    */   
/*    */   public double getMultiplier() {
/* 34 */     return (this.override != 1.0D) ? this.override : (isActive() ? ((Double)this.multiplier.get()).doubleValue() : 1.0D);
/*    */   }
/*    */   
/*    */   public void setOverride(double override) {
/* 38 */     this.override = override;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\Timer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */