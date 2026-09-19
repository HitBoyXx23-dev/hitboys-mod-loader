/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.settings.StatusEffectListSetting;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import net.minecraft.class_1291;
/*    */ import net.minecraft.class_1294;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoStatusEffects
/*    */   extends Module
/*    */ {
/* 18 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 20 */   private final Setting<List<class_1291>> blockedEffects = this.sgGeneral.add((Setting)((StatusEffectListSetting.Builder)((StatusEffectListSetting.Builder)(new StatusEffectListSetting.Builder())
/* 21 */       .name("blocked-effects"))
/* 22 */       .description("Effects to block."))
/* 23 */       .defaultValue(new class_1291[] {
/* 24 */           (class_1291)class_1294.field_5902.comp_349(), (class_1291)class_1294.field_5913
/* 25 */           .comp_349(), (class_1291)class_1294.field_5906
/* 26 */           .comp_349(), (class_1291)class_1294.field_5900
/* 27 */           .comp_349()
/*    */         
/* 29 */         }).build());
/*    */ 
/*    */   
/*    */   public NoStatusEffects() {
/* 33 */     super(Categories.Player, "no-status-effects", "Blocks specified status effects.");
/*    */   }
/*    */   
/*    */   public boolean shouldBlock(class_1291 effect) {
/* 37 */     return (isActive() && ((List)this.blockedEffects.get()).contains(effect));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\NoStatusEffects.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */