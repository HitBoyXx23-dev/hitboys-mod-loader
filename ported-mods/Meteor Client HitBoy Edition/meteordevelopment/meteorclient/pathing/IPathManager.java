/*    */ package meteordevelopment.meteorclient.pathing;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.Settings;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_2248;
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface IPathManager
/*    */ {
/*    */   String getName();
/*    */   
/*    */   boolean isPathing();
/*    */   
/*    */   void pause();
/*    */   
/*    */   void resume();
/*    */   
/*    */   void stop();
/*    */   
/*    */   default void moveTo(class_2338 pos) {
/* 25 */     moveTo(pos, false);
/*    */   }
/*    */   
/*    */   void moveTo(class_2338 paramclass_2338, boolean paramBoolean);
/*    */   
/*    */   void moveInDirection(float paramFloat);
/*    */   
/*    */   void mine(class_2248... paramVarArgs);
/*    */   
/*    */   void follow(Predicate<class_1297> paramPredicate);
/*    */   
/*    */   float getTargetYaw();
/*    */   
/*    */   float getTargetPitch();
/*    */   
/*    */   ISettings getSettings();
/*    */   
/*    */   public static interface ISettings {
/*    */     Settings get();
/*    */     
/*    */     Setting<Boolean> getWalkOnWater();
/*    */     
/*    */     Setting<Boolean> getWalkOnLava();
/*    */     
/*    */     Setting<Boolean> getStep();
/*    */     
/*    */     Setting<Boolean> getNoFall();
/*    */     
/*    */     void save();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\pathing\IPathManager.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */