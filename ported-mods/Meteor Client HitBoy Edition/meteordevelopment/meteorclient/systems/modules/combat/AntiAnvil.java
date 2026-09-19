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
/*    */ import net.minecraft.class_1792;
/*    */ import net.minecraft.class_1802;
/*    */ import net.minecraft.class_2246;
/*    */ import net.minecraft.class_2338;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AntiAnvil
/*    */   extends Module
/*    */ {
/* 22 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 24 */   private final Setting<Boolean> swing = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 25 */       .name("swing"))
/* 26 */       .description("Swings your hand client-side when placing."))
/* 27 */       .defaultValue(Boolean.valueOf(true)))
/* 28 */       .build());
/*    */ 
/*    */   
/* 31 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 32 */       .name("rotate"))
/* 33 */       .description("Makes you rotate when placing."))
/* 34 */       .defaultValue(Boolean.valueOf(true)))
/* 35 */       .build());
/*    */ 
/*    */   
/*    */   public AntiAnvil() {
/* 39 */     super(Categories.Combat, "anti-anvil", "Automatically prevents Auto Anvil by placing between you and the anvil.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Pre event) {
/* 44 */     for (int i = 0; i <= this.mc.field_1724.method_55754(); i++) {
/* 45 */       class_2338 pos = this.mc.field_1724.method_24515().method_10069(0, i + 3, 0);
/*    */       
/* 47 */       if (this.mc.field_1687.method_8320(pos).method_26204() == class_2246.field_10535 && this.mc.field_1687.method_8320(pos.method_10074()).method_26215() && 
/* 48 */         BlockUtils.place(pos.method_10074(), InvUtils.findInHotbar(new class_1792[] { class_1802.field_8281 }, ), ((Boolean)this.rotate.get()).booleanValue(), 15, ((Boolean)this.swing.get()).booleanValue(), true))
/*    */         break; 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\AntiAnvil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */