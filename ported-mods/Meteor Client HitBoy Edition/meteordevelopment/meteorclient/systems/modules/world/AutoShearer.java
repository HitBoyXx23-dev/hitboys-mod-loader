/*    */ package meteordevelopment.meteorclient.systems.modules.world;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*    */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*    */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*    */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1472;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_1802;
/*    */ import net.minecraft.class_3966;
/*    */ 
/*    */ 
/*    */ public class AutoShearer
/*    */   extends Module
/*    */ {
/* 27 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 29 */   private final Setting<Double> distance = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 30 */       .name("distance"))
/* 31 */       .description("The maximum distance the sheep have to be to be sheared."))
/* 32 */       .min(0.0D)
/* 33 */       .defaultValue(5.0D)
/* 34 */       .build());
/*    */ 
/*    */   
/* 37 */   private final Setting<Boolean> antiBreak = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 38 */       .name("anti-break"))
/* 39 */       .description("Prevents shears from being broken."))
/* 40 */       .defaultValue(Boolean.valueOf(false)))
/* 41 */       .build());
/*    */ 
/*    */   
/* 44 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 45 */       .name("rotate"))
/* 46 */       .description("Automatically faces towards the animal being sheared."))
/* 47 */       .defaultValue(Boolean.valueOf(true)))
/* 48 */       .build());
/*    */   
/*    */   private class_1297 entity;
/*    */   
/*    */   private class_1268 hand;
/*    */   
/*    */   public AutoShearer() {
/* 55 */     super(Categories.World, "auto-shearer", "Automatically shears sheep.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDeactivate() {
/* 60 */     this.entity = null;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Pre event) {
/* 65 */     this.entity = null;
/*    */     
/* 67 */     for (class_1297 entity : this.mc.field_1687.method_18112()) {
/* 68 */       if (!(entity instanceof class_1472) || ((class_1472)entity).method_6629() || ((class_1472)entity).method_6109() || !PlayerUtils.isWithin(entity, ((Double)this.distance.get()).doubleValue()))
/*    */         continue; 
/* 70 */       FindItemResult findShear = InvUtils.findInHotbar(itemStack -> (itemStack.method_7909() == class_1802.field_8868 && (!((Boolean)this.antiBreak.get()).booleanValue() || itemStack.method_7919() < itemStack.method_7936() - 1)));
/* 71 */       if (!InvUtils.swap(findShear.slot(), true))
/*    */         return; 
/* 73 */       this.hand = findShear.getHand();
/* 74 */       this.entity = entity;
/*    */       
/* 76 */       if (((Boolean)this.rotate.get()).booleanValue()) { Rotations.rotate(Rotations.getYaw(entity), Rotations.getPitch(entity), -100, this::interact); }
/* 77 */       else { interact(); }
/*    */       
/*    */       return;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void interact() {
/* 84 */     class_3966 location = new class_3966(this.entity, this.entity.method_5829().method_1005());
/* 85 */     this.mc.field_1761.method_2917((class_1657)this.mc.field_1724, this.entity, location, this.hand);
/* 86 */     this.mc.field_1761.method_2905((class_1657)this.mc.field_1724, this.entity, this.hand);
/* 87 */     InvUtils.swapBack();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\AutoShearer.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */