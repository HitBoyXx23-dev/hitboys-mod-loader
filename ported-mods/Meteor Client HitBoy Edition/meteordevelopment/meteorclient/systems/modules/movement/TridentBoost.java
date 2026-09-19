/*    */ package meteordevelopment.meteorclient.systems.modules.movement;
/*    */ 
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TridentBoost
/*    */   extends Module
/*    */ {
/* 16 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 18 */   private final Setting<Double> multiplier = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 19 */       .name("boost"))
/* 20 */       .description("How much your velocity is multiplied by when using riptide."))
/* 21 */       .defaultValue(2.0D)
/* 22 */       .min(0.1D)
/* 23 */       .sliderMin(1.0D)
/* 24 */       .build());
/*    */ 
/*    */   
/* 27 */   private final Setting<Boolean> allowOutOfWater = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 28 */       .name("out-of-water"))
/* 29 */       .description("Whether riptide should work out of water"))
/* 30 */       .defaultValue(Boolean.valueOf(true)))
/* 31 */       .build());
/*    */ 
/*    */   
/*    */   public TridentBoost() {
/* 35 */     super(Categories.Movement, "trident-boost", "Boosts you when using riptide with a trident.");
/*    */   }
/*    */   
/*    */   public double getMultiplier() {
/* 39 */     return isActive() ? ((Double)this.multiplier.get()).doubleValue() : 1.0D;
/*    */   }
/*    */   
/*    */   public boolean allowOutOfWater() {
/* 43 */     return isActive() ? ((Boolean)this.allowOutOfWater.get()).booleanValue() : false;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\TridentBoost.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */