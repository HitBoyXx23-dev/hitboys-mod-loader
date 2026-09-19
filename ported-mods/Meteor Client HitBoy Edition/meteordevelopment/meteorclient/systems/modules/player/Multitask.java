/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Multitask
/*    */   extends Module
/*    */ {
/* 15 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 17 */   private final Setting<Boolean> attackingEntities = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 18 */       .name("attacking-entities"))
/* 19 */       .description("Lets you attack entities while using an item."))
/* 20 */       .defaultValue(Boolean.valueOf(true)))
/* 21 */       .build());
/*    */ 
/*    */   
/*    */   public Multitask() {
/* 25 */     super(Categories.Player, "multitask", "Lets you use items and attack at the same time.");
/*    */   }
/*    */   
/*    */   public boolean attackingEntities() {
/* 29 */     return (isActive() && ((Boolean)this.attackingEntities.get()).booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\Multitask.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */