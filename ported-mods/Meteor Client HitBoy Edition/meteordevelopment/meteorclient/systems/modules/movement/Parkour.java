/*    */ package meteordevelopment.meteorclient.systems.modules.movement;
/*    */ 
/*    */ import com.google.common.collect.Streams;
/*    */ import java.util.stream.Stream;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.settings.DoubleSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_238;
/*    */ import net.minecraft.class_265;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Parkour
/*    */   extends Module
/*    */ {
/* 23 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 25 */   private final Setting<Double> edgeDistance = this.sgGeneral.add((Setting)((DoubleSetting.Builder)((DoubleSetting.Builder)(new DoubleSetting.Builder())
/* 26 */       .name("edge-distance"))
/* 27 */       .description("How far from the edge should you jump."))
/* 28 */       .range(0.001D, 0.1D)
/* 29 */       .defaultValue(0.001D)
/* 30 */       .build());
/*    */ 
/*    */   
/*    */   public Parkour() {
/* 34 */     super(Categories.Movement, "parkour", "Automatically jumps at the edges of blocks.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 39 */     if (!this.mc.field_1724.method_24828() || this.mc.field_1690.field_1903.method_1434())
/*    */       return; 
/* 41 */     if (this.mc.field_1724.method_5715() || this.mc.field_1690.field_1832.method_1434())
/*    */       return; 
/* 43 */     class_238 box = this.mc.field_1724.method_5829();
/* 44 */     class_238 adjustedBox = box.method_989(0.0D, -0.5D, 0.0D).method_1009(-((Double)this.edgeDistance.get()).doubleValue(), 0.0D, -((Double)this.edgeDistance.get()).doubleValue());
/*    */     
/* 46 */     Stream<class_265> blockCollisions = Streams.stream(this.mc.field_1687.method_20812((class_1297)this.mc.field_1724, adjustedBox));
/*    */     
/* 48 */     if (blockCollisions.findAny().isPresent())
/*    */       return; 
/* 50 */     this.mc.field_1724.method_6043();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\movement\Parkour.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */