/*    */ package meteordevelopment.meteorclient.systems.modules.misc;
/*    */ 
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.events.world.PlaySoundEvent;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.settings.SoundEventListSetting;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1113;
/*    */ import net.minecraft.class_3414;
/*    */ import net.minecraft.class_7923;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundBlocker
/*    */   extends Module
/*    */ {
/* 22 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 24 */   private final Setting<List<class_3414>> sounds = this.sgGeneral.add((Setting)((SoundEventListSetting.Builder)((SoundEventListSetting.Builder)(new SoundEventListSetting.Builder())
/* 25 */       .name("sounds"))
/* 26 */       .description("Sounds to block."))
/* 27 */       .build());
/*    */ 
/*    */   
/*    */   public SoundBlocker() {
/* 31 */     super(Categories.Misc, "sound-blocker", "Cancels out selected sounds.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onPlaySound(PlaySoundEvent event) {
/* 36 */     for (class_3414 sound : this.sounds.get()) {
/* 37 */       if (sound.comp_3319().equals(event.sound.method_4775())) {
/* 38 */         event.cancel();
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean shouldBlock(class_1113 soundInstance) {
/* 45 */     return (isActive() && ((List)this.sounds.get()).contains(Setting.parseId(class_7923.field_41172, soundInstance.method_4775().method_12832())));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\misc\SoundBlocker.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */