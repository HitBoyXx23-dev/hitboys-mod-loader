/*    */ package meteordevelopment.meteorclient.pathing;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.Settings;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_2248;
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NopPathManager
/*    */   implements IPathManager
/*    */ {
/* 18 */   private final NopSettings settings = new NopSettings();
/*    */ 
/*    */   
/*    */   public String getName() {
/* 22 */     return "none";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPathing() {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void pause() {}
/*    */ 
/*    */   
/*    */   public void resume() {}
/*    */ 
/*    */   
/*    */   public void stop() {}
/*    */ 
/*    */   
/*    */   public void moveTo(class_2338 pos, boolean ignoreY) {}
/*    */ 
/*    */   
/*    */   public void moveInDirection(float yaw) {}
/*    */ 
/*    */   
/*    */   public void mine(class_2248... blocks) {}
/*    */ 
/*    */   
/*    */   public void follow(Predicate<class_1297> entity) {}
/*    */ 
/*    */   
/*    */   public float getTargetYaw() {
/* 53 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getTargetPitch() {
/* 58 */     return 0.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public IPathManager.ISettings getSettings() {
/* 63 */     return this.settings;
/*    */   }
/*    */   
/*    */   private static class NopSettings implements IPathManager.ISettings {
/* 67 */     private final Settings settings = new Settings();
/* 68 */     private final Setting<Boolean> setting = (Setting<Boolean>)(new BoolSetting.Builder()).build();
/*    */ 
/*    */     
/*    */     public Settings get() {
/* 72 */       return this.settings;
/*    */     }
/*    */ 
/*    */     
/*    */     public Setting<Boolean> getWalkOnWater() {
/* 77 */       this.setting.reset();
/* 78 */       return this.setting;
/*    */     }
/*    */ 
/*    */     
/*    */     public Setting<Boolean> getWalkOnLava() {
/* 83 */       this.setting.reset();
/* 84 */       return this.setting;
/*    */     }
/*    */ 
/*    */     
/*    */     public Setting<Boolean> getStep() {
/* 89 */       this.setting.reset();
/* 90 */       return this.setting;
/*    */     }
/*    */ 
/*    */     
/*    */     public Setting<Boolean> getNoFall() {
/* 95 */       this.setting.reset();
/* 96 */       return this.setting;
/*    */     }
/*    */     
/*    */     public void save() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\pathing\NopPathManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */