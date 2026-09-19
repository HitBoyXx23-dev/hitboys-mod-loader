/*    */ package meteordevelopment.meteorclient.systems.modules.render;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import meteordevelopment.meteorclient.events.game.ChangePerspectiveEvent;
/*    */ import meteordevelopment.meteorclient.events.meteor.MouseScrollEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.KeybindSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.misc.Keybind;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_5498;
/*    */ 
/*    */ public class CameraTweaks
/*    */   extends Module {
/* 19 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/* 20 */   private final SettingGroup sgScrolling = this.settings.createGroup("Scrolling");
/*    */ 
/*    */ 
/*    */   
/* 24 */   private final Setting<Boolean> clip = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 25 */       .name("clip"))
/* 26 */       .description("Allows the camera to clip through blocks."))
/* 27 */       .defaultValue(Boolean.valueOf(true)))
/* 28 */       .build());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Setting<Double> cameraDistance;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Setting<Boolean> scrollingEnabled;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Setting<Keybind> scrollKeybind;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Setting<Double> scrollSensitivity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double distance;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CameraTweaks() {
/* 69 */     super(Categories.Render, "camera-tweaks", "Allows modification of the third person camera."); this.cameraDistance = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("camera-distance")).description("The distance the third person camera is from the player.")).defaultValue(4.0D).min(0.0D).onChanged(value -> this.distance = value.doubleValue())).build());
/*    */     this.scrollingEnabled = this.sgScrolling.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("scrolling")).description("Allows you to scroll to change camera distance.")).defaultValue(Boolean.valueOf(true))).build());
/*    */     Objects.requireNonNull(this.scrollingEnabled);
/*    */     this.scrollKeybind = this.sgScrolling.add((Setting)((KeybindSetting.Builder)((KeybindSetting.Builder)((KeybindSetting.Builder)((KeybindSetting.Builder)(new KeybindSetting.Builder()).name("bind")).description("Binds camera distance scrolling to a key.")).visible(this.scrollingEnabled::get)).defaultValue(Keybind.fromKey(342))).build());
/*    */     Objects.requireNonNull(this.scrollingEnabled);
/* 74 */     this.scrollSensitivity = this.sgScrolling.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder()).name("sensitivity")).description("Sensitivity of the scroll wheel when changing the cameras distance.")).visible(this.scrollingEnabled::get)).defaultValue(1.0D).min(0.01D).build()); } public void onActivate() { this.distance = ((Double)this.cameraDistance.get()).doubleValue(); }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   private void onPerspectiveChanged(ChangePerspectiveEvent event) {
/* 79 */     this.distance = ((Double)this.cameraDistance.get()).doubleValue();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onMouseScroll(MouseScrollEvent event) {
/* 84 */     if (this.mc.field_1690.method_31044() == class_5498.field_26664 || this.mc.field_1755 != null || !((Boolean)this.scrollingEnabled.get()).booleanValue() || (((Keybind)this.scrollKeybind.get()).isSet() && !((Keybind)this.scrollKeybind.get()).isPressed()))
/*    */       return; 
/* 86 */     if (((Double)this.scrollSensitivity.get()).doubleValue() > 0.0D) {
/* 87 */       this.distance -= event.value * 0.25D * ((Double)this.scrollSensitivity.get()).doubleValue() * this.distance;
/*    */       
/* 89 */       event.cancel();
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean clip() {
/* 94 */     return (isActive() && ((Boolean)this.clip.get()).booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\CameraTweaks.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */