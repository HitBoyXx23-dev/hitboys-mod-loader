/*    */ package meteordevelopment.meteorclient.pathing;
/*    */ 
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.Settings;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NopSettings
/*    */   implements IPathManager.ISettings
/*    */ {
/* 67 */   private final Settings settings = new Settings();
/* 68 */   private final Setting<Boolean> setting = (Setting<Boolean>)(new BoolSetting.Builder()).build();
/*    */ 
/*    */   
/*    */   public Settings get() {
/* 72 */     return this.settings;
/*    */   }
/*    */ 
/*    */   
/*    */   public Setting<Boolean> getWalkOnWater() {
/* 77 */     this.setting.reset();
/* 78 */     return this.setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public Setting<Boolean> getWalkOnLava() {
/* 83 */     this.setting.reset();
/* 84 */     return this.setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public Setting<Boolean> getStep() {
/* 89 */     this.setting.reset();
/* 90 */     return this.setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public Setting<Boolean> getNoFall() {
/* 95 */     this.setting.reset();
/* 96 */     return this.setting;
/*    */   }
/*    */   
/*    */   public void save() {}
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\pathing\NopPathManager$NopSettings.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */