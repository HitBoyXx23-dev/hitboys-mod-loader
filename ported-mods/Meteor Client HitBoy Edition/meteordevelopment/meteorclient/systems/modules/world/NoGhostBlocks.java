/*    */ package meteordevelopment.meteorclient.systems.modules.world;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.entity.player.BreakBlockEvent;
/*    */ import meteordevelopment.meteorclient.events.entity.player.PlaceBlockEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1937;
/*    */ import net.minecraft.class_2680;
/*    */ 
/*    */ 
/*    */ public class NoGhostBlocks
/*    */   extends Module
/*    */ {
/* 19 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 21 */   private final Setting<Boolean> breaking = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 22 */       .name("breaking"))
/* 23 */       .description("Whether to apply for block breaking actions."))
/* 24 */       .defaultValue(Boolean.valueOf(true)))
/* 25 */       .build());
/*    */ 
/*    */   
/* 28 */   public final Setting<Boolean> placing = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 29 */       .name("placing"))
/* 30 */       .description("Whether to apply for block placement actions."))
/* 31 */       .defaultValue(Boolean.valueOf(true)))
/* 32 */       .build());
/*    */ 
/*    */   
/*    */   public NoGhostBlocks() {
/* 36 */     super(Categories.World, "no-ghost-blocks", "Attempts to prevent ghost blocks arising.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onBreakBlock(BreakBlockEvent event) {
/* 41 */     if (this.mc.method_1542() || !((Boolean)this.breaking.get()).booleanValue())
/*    */       return; 
/* 43 */     event.cancel();
/*    */     
/* 45 */     class_2680 blockState = this.mc.field_1687.method_8320(event.blockPos);
/* 46 */     blockState.method_26204().method_9576((class_1937)this.mc.field_1687, event.blockPos, blockState, (class_1657)this.mc.field_1724);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onPlaceBlock(PlaceBlockEvent event) {
/* 51 */     if (!((Boolean)this.placing.get()).booleanValue())
/*    */       return; 
/* 53 */     event.cancel();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\NoGhostBlocks.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */