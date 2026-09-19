/*    */ package meteordevelopment.meteorclient.systems.modules.world;
/*    */ 
/*    */ import java.util.Set;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.EntityTypeListSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.entity.EntityUtils;
/*    */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*    */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1268;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_1299;
/*    */ import net.minecraft.class_1308;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_3966;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutoMount
/*    */   extends Module
/*    */ {
/* 36 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 38 */   private final Setting<Boolean> checkSaddle = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 39 */       .name("check-saddle"))
/* 40 */       .description("Checks if the entity contains a saddle before mounting."))
/* 41 */       .defaultValue(Boolean.valueOf(false)))
/* 42 */       .build());
/*    */ 
/*    */   
/* 45 */   private final Setting<Boolean> rotate = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 46 */       .name("rotate"))
/* 47 */       .description("Faces the entity you mount."))
/* 48 */       .defaultValue(Boolean.valueOf(true)))
/* 49 */       .build());
/*    */ 
/*    */   
/* 52 */   private final Setting<Set<class_1299<?>>> entities = this.sgGeneral.add((Setting)((EntityTypeListSetting.Builder)((EntityTypeListSetting.Builder)(new EntityTypeListSetting.Builder())
/* 53 */       .name("entities"))
/* 54 */       .description("Rideable entities."))
/* 55 */       .filter(EntityUtils::isRideable)
/* 56 */       .build());
/*    */ 
/*    */   
/*    */   public AutoMount() {
/* 60 */     super(Categories.World, "auto-mount", "Automatically mounts entities.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Pre event) {
/* 65 */     if (this.mc.field_1724.method_5765())
/* 66 */       return;  if (this.mc.field_1724.method_5715())
/* 67 */       return;  if (this.mc.field_1724.method_6047().method_7909() instanceof net.minecraft.class_1826)
/*    */       return; 
/* 69 */     for (class_1297 entity : this.mc.field_1687.method_18112()) {
/* 70 */       if (!((Set)this.entities.get()).contains(entity.method_5864()) || 
/* 71 */         !PlayerUtils.isWithin(entity, 4.0D) || ((
/* 72 */         entity instanceof net.minecraft.class_1452 || entity instanceof net.minecraft.class_1506 || entity instanceof net.minecraft.class_4985 || entity instanceof net.minecraft.class_1507) && !((class_1308)entity).method_66672()))
/* 73 */         continue;  if (!(entity instanceof net.minecraft.class_1501) && entity instanceof class_1308) { class_1308 mobEntity = (class_1308)entity; if (((Boolean)this.checkSaddle.get()).booleanValue() && !mobEntity.method_66672())
/* 74 */           continue;  }  interact(entity, ((Boolean)this.rotate.get()).booleanValue());
/*    */       return;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void interact(class_1297 entity, boolean rotate) {
/* 80 */     if (rotate) {
/* 81 */       Rotations.rotate(Rotations.getYaw(entity), Rotations.getPitch(entity), -100, () -> interact(entity));
/*    */     } else {
/* 83 */       interact(entity);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void interact(class_1297 entity) {
/* 88 */     class_3966 location = new class_3966(entity, entity.method_5829().method_1005());
/* 89 */     this.mc.field_1761.method_2917((class_1657)this.mc.field_1724, entity, location, class_1268.field_5808);
/* 90 */     this.mc.field_1761.method_2905((class_1657)this.mc.field_1724, entity, class_1268.field_5808);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\AutoMount.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */