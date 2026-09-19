/*    */ package meteordevelopment.meteorclient.systems.modules.combat;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*    */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_2248;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AntiAnchor
/*    */   extends Module
/*    */ {
/* 22 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 24 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 25 */       .name("rotate"))
/* 26 */       .description("Makes you rotate when placing."))
/* 27 */       .defaultValue(Boolean.valueOf(true)))
/* 28 */       .build());
/*    */ 
/*    */   
/* 31 */   private final Setting<Boolean> swing = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 32 */       .name("swing"))
/* 33 */       .description("Swings your hand when placing."))
/* 34 */       .defaultValue(Boolean.valueOf(true)))
/* 35 */       .build());
/*    */ 
/*    */   
/*    */   public AntiAnchor() {
/* 39 */     super(Categories.Combat, "anti-anchor", "Automatically prevents Anchor Aura by placing a slab on your head.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Pre event) {
/* 44 */     if (this.mc.field_1687.method_8320(this.mc.field_1724.method_24515().method_10086(2)).method_26204() == class_2246.field_23152 && this.mc.field_1687
/* 45 */       .method_8320(this.mc.field_1724.method_24515().method_10084()).method_26204() == class_2246.field_10124)
/*    */     {
/* 47 */       BlockUtils.place(this.mc.field_1724
/* 48 */           .method_24515().method_10069(0, 1, 0), 
/* 49 */           InvUtils.findInHotbar(itemStack -> class_2248.method_9503(itemStack.method_7909()) instanceof net.minecraft.class_2482), ((Boolean)this.rotate
/* 50 */           .get()).booleanValue(), 15, ((Boolean)this.swing
/*    */           
/* 52 */           .get()).booleanValue(), false, true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\AntiAnchor.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */