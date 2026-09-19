/*    */ package meteordevelopment.meteorclient.systems.modules.combat;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*    */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*    */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*    */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_1792;
/*    */ import net.minecraft.class_1802;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2846;
/*    */ import net.minecraft.class_2879;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AntiBed
/*    */   extends Module
/*    */ {
/* 28 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 30 */   private final Setting<Boolean> placeStringTop = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 31 */       .name("place-string-top"))
/* 32 */       .description("Places string above you."))
/* 33 */       .defaultValue(Boolean.valueOf(false)))
/* 34 */       .build());
/*    */ 
/*    */   
/* 37 */   private final Setting<Boolean> placeStringMiddle = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 38 */       .name("place-string-middle"))
/* 39 */       .description("Places string in your upper hitbox."))
/* 40 */       .defaultValue(Boolean.valueOf(true)))
/* 41 */       .build());
/*    */ 
/*    */   
/* 44 */   private final Setting<Boolean> placeStringBottom = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 45 */       .name("place-string-bottom"))
/* 46 */       .description("Places string at your feet."))
/* 47 */       .defaultValue(Boolean.valueOf(false)))
/* 48 */       .build());
/*    */ 
/*    */   
/* 51 */   private final Setting<Boolean> onlyInHole = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 52 */       .name("only-in-hole"))
/* 53 */       .description("Only functions when you are standing in a hole."))
/* 54 */       .defaultValue(Boolean.valueOf(true)))
/* 55 */       .build());
/*    */   
/*    */   private boolean breaking;
/*    */ 
/*    */   
/*    */   public AntiBed() {
/* 61 */     super(Categories.Combat, "anti-bed", "Places string to prevent beds being placed on you.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Pre event) {
/* 66 */     if (((Boolean)this.onlyInHole.get()).booleanValue() && !PlayerUtils.isInHole(true)) {
/*    */       return;
/*    */     }
/* 69 */     class_2338 head = this.mc.field_1724.method_24515().method_10084();
/*    */     
/* 71 */     if (this.mc.field_1687.method_8320(head).method_26204() instanceof net.minecraft.class_2244 && !this.breaking) {
/* 72 */       Rotations.rotate(Rotations.getYaw(head), Rotations.getPitch(head), 50, () -> sendMinePackets(head));
/* 73 */       this.breaking = true;
/* 74 */     } else if (this.breaking) {
/* 75 */       Rotations.rotate(Rotations.getYaw(head), Rotations.getPitch(head), 50, () -> sendStopPackets(head));
/* 76 */       this.breaking = false;
/*    */     } 
/*    */ 
/*    */     
/* 80 */     if (((Boolean)this.placeStringTop.get()).booleanValue()) place(this.mc.field_1724.method_24515().method_10086(2)); 
/* 81 */     if (((Boolean)this.placeStringMiddle.get()).booleanValue()) place(this.mc.field_1724.method_24515().method_10086(1)); 
/* 82 */     if (((Boolean)this.placeStringBottom.get()).booleanValue()) place(this.mc.field_1724.method_24515()); 
/*    */   }
/*    */   
/*    */   private void place(class_2338 blockPos) {
/* 86 */     if (this.mc.field_1687.method_8320(blockPos).method_26204().method_8389() != class_1802.field_8276) {
/* 87 */       BlockUtils.place(blockPos, InvUtils.findInHotbar(new class_1792[] { class_1802.field_8276 }, ), 50, false);
/*    */     }
/*    */   }
/*    */   
/*    */   private void sendMinePackets(class_2338 blockPos) {
/* 92 */     this.mc.field_1761.method_41931(this.mc.field_1687, sequence -> new class_2846(class_2846.class_2847.field_12968, blockPos, class_2350.field_11036, sequence));
/* 93 */     this.mc.field_1761.method_41931(this.mc.field_1687, sequence -> new class_2846(class_2846.class_2847.field_12973, blockPos, class_2350.field_11036, sequence));
/*    */   }
/*    */   
/*    */   private void sendStopPackets(class_2338 blockPos) {
/* 97 */     this.mc.method_1562().method_52787((class_2596)new class_2846(class_2846.class_2847.field_12971, blockPos, class_2350.field_11036));
/* 98 */     this.mc.method_1562().method_52787((class_2596)new class_2879(class_1268.field_5808));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\AntiBed.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */