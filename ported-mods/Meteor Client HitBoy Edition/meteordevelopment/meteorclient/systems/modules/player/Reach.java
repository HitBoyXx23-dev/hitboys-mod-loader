/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.gui.GuiTheme;
/*    */ import meteordevelopment.meteorclient.gui.widgets.WWidget;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Reach
/*    */   extends Module
/*    */ {
/* 18 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 20 */   private final Setting<Double> blockReach = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 21 */       .name("extra-block-reach"))
/* 22 */       .description("The distance to add to your block reach."))
/* 23 */       .sliderMax(1.0D)
/* 24 */       .build());
/*    */ 
/*    */   
/* 27 */   private final Setting<Double> entityReach = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 28 */       .name("extra-entity-reach"))
/* 29 */       .description("The distance to add to your entity reach."))
/* 30 */       .sliderMax(1.0D)
/* 31 */       .build());
/*    */ 
/*    */   
/*    */   public Reach() {
/* 35 */     super(Categories.Player, "reach", "Gives you super long arms.");
/*    */   }
/*    */ 
/*    */   
/*    */   public WWidget getWidget(GuiTheme theme) {
/* 40 */     return (WWidget)theme.label("Note: on vanilla servers you may give yourself up to 4 blocks of additional reach for specific actions - interacting with block entities (chests, furnaces, etc.) or with vehicles. This does not work on paper servers.", 
/* 41 */         Utils.getWindowWidth() / 3.0D);
/*    */   }
/*    */   
/*    */   public double blockReach() {
/* 45 */     return isActive() ? ((Double)this.blockReach.get()).doubleValue() : 0.0D;
/*    */   }
/*    */   
/*    */   public double entityReach() {
/* 49 */     return isActive() ? ((Double)this.entityReach.get()).doubleValue() : 0.0D;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\Reach.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */