/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.settings.StatusEffectListSetting;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*    */ import net.minecraft.class_1291;
/*    */ import net.minecraft.class_1294;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PotionSaver
/*    */   extends Module
/*    */ {
/* 22 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 24 */   private final Setting<List<class_1291>> effects = this.sgGeneral.add((Setting)((StatusEffectListSetting.Builder)((StatusEffectListSetting.Builder)(new StatusEffectListSetting.Builder())
/* 25 */       .name("effects"))
/* 26 */       .description("The effects to preserve."))
/* 27 */       .defaultValue(new class_1291[] { 
/* 28 */           (class_1291)class_1294.field_5910.comp_349(), (class_1291)class_1294.field_5898
/* 29 */           .comp_349(), (class_1291)class_1294.field_5907
/* 30 */           .comp_349(), (class_1291)class_1294.field_5918
/* 31 */           .comp_349(), (class_1291)class_1294.field_5904
/* 32 */           .comp_349(), (class_1291)class_1294.field_5917
/* 33 */           .comp_349(), (class_1291)class_1294.field_5924
/* 34 */           .comp_349(), (class_1291)class_1294.field_5923
/* 35 */           .comp_349(), (class_1291)class_1294.field_5922
/* 36 */           .comp_349(), (class_1291)class_1294.field_5926
/* 37 */           .comp_349(), (class_1291)class_1294.field_5906
/* 38 */           .comp_349(), (class_1291)class_1294.field_5900
/* 39 */           .comp_349(), (class_1291)class_1294.field_5927
/* 40 */           .comp_349(), (class_1291)class_1294.field_18980
/* 41 */           .comp_349()
/*    */         
/* 43 */         }).build());
/*    */ 
/*    */   
/* 46 */   public final Setting<Boolean> onlyWhenStationary = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 47 */       .name("only-when-stationary"))
/* 48 */       .description("Only freezes effects when you aren't moving."))
/* 49 */       .defaultValue(Boolean.valueOf(false)))
/* 50 */       .build());
/*    */ 
/*    */   
/*    */   public PotionSaver() {
/* 54 */     super(Categories.Player, "potion-saver", "Stops potion effects ticking when you stand still.");
/*    */   }
/*    */   
/*    */   public boolean shouldFreeze(class_1291 effect) {
/* 58 */     return (isActive() && (!((Boolean)this.onlyWhenStationary.get()).booleanValue() || !PlayerUtils.isMoving()) && !this.mc.field_1724.method_6026().isEmpty() && ((List)this.effects.get()).contains(effect));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\PotionSaver.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */