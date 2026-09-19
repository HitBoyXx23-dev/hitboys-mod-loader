/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import java.util.Set;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.EntityTypeListSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1299;
/*    */ import net.minecraft.class_3489;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoMiningTrace
/*    */   extends Module
/*    */ {
/* 21 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 23 */   private final Setting<Set<class_1299<?>>> entities = this.sgGeneral.add((Setting)((EntityTypeListSetting.Builder)((EntityTypeListSetting.Builder)(new EntityTypeListSetting.Builder())
/* 24 */       .name("blacklisted-entities"))
/* 25 */       .description("Entities you will interact with as normal."))
/* 26 */       .defaultValue(new class_1299[0])
/* 27 */       .build());
/*    */ 
/*    */   
/* 30 */   private final Setting<Boolean> onlyWhenHoldingPickaxe = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 31 */       .name("only-when-holding-a-pickaxe"))
/* 32 */       .description("Whether or not to work only when holding a pickaxe."))
/* 33 */       .defaultValue(Boolean.valueOf(true)))
/* 34 */       .build());
/*    */ 
/*    */   
/*    */   public NoMiningTrace() {
/* 38 */     super(Categories.Player, "no-mining-trace", "Allows you to mine blocks through entities.");
/*    */   }
/*    */   
/*    */   public boolean canWork(class_1297 entity) {
/* 42 */     if (!isActive()) return false;
/*    */     
/* 44 */     return ((!((Boolean)this.onlyWhenHoldingPickaxe.get()).booleanValue() || this.mc.field_1724.method_6047().method_31573(class_3489.field_42614) || this.mc.field_1724.method_6079().method_31573(class_3489.field_42614)) && (entity == null || 
/* 45 */       !((Set)this.entities.get()).contains(entity.method_5864())));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\NoMiningTrace.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */