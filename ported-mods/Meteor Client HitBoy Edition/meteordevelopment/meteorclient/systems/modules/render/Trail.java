/*    */ package meteordevelopment.meteorclient.systems.modules.render;
/*    */ 
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.ParticleTypeListSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_2394;
/*    */ import net.minecraft.class_2396;
/*    */ import net.minecraft.class_2398;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Trail
/*    */   extends Module
/*    */ {
/* 23 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 25 */   private final Setting<List<class_2396<?>>> particles = this.sgGeneral.add((Setting)((ParticleTypeListSetting.Builder)((ParticleTypeListSetting.Builder)(new ParticleTypeListSetting.Builder())
/* 26 */       .name("particles"))
/* 27 */       .description("Particles to draw."))
/* 28 */       .defaultValue(new class_2396[] { (class_2396)class_2398.field_22446, (class_2396)class_2398.field_17430
/* 29 */         }).build());
/*    */ 
/*    */   
/* 32 */   private final Setting<Boolean> pause = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 33 */       .name("pause-when-stationary"))
/* 34 */       .description("Whether or not to add particles when you are not moving."))
/* 35 */       .defaultValue(Boolean.valueOf(true)))
/* 36 */       .build());
/*    */ 
/*    */   
/*    */   public Trail() {
/* 40 */     super(Categories.Render, "trail", "Renders a customizable trail behind your player.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 45 */     if (((Boolean)this.pause.get()).booleanValue() && this.mc.field_1724
/* 46 */       .method_23317() == this.mc.field_1724.field_6014 && this.mc.field_1724
/* 47 */       .method_23318() == this.mc.field_1724.field_6036 && this.mc.field_1724
/* 48 */       .method_23321() == this.mc.field_1724.field_5969)
/*    */       return; 
/* 50 */     for (class_2396<?> particleType : (Iterable<class_2396<?>>)this.particles.get())
/* 51 */       this.mc.field_1687.method_8406((class_2394)particleType, this.mc.field_1724.method_23317(), this.mc.field_1724.method_23318(), this.mc.field_1724.method_23321(), 0.0D, 0.0D, 0.0D); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\render\Trail.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */