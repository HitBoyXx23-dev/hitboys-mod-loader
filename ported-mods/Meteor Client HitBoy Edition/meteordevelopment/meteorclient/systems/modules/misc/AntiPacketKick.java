/*    */ package meteordevelopment.meteorclient.systems.modules.misc;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AntiPacketKick
/*    */   extends Module
/*    */ {
/* 15 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 17 */   public final Setting<Boolean> catchExceptions = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 18 */       .name("catch-exceptions"))
/* 19 */       .description("Drops corrupted packets."))
/* 20 */       .defaultValue(Boolean.valueOf(false)))
/* 21 */       .build());
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final Setting<Boolean> logExceptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AntiPacketKick() {
/* 33 */     super(Categories.Misc, "anti-packet-kick", "Attempts to prevent you from being disconnected by large packets.");
/*    */     Objects.requireNonNull(this.catchExceptions);
/*    */     this.logExceptions = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("log-exceptions")).description("Logs caught exceptions.")).defaultValue(Boolean.valueOf(true))).visible(this.catchExceptions::get)).build());
/*    */   } public boolean catchExceptions() {
/* 37 */     return (isActive() && ((Boolean)this.catchExceptions.get()).booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\misc\AntiPacketKick.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */