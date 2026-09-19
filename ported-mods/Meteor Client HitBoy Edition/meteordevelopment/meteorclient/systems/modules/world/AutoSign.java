/*    */ package meteordevelopment.meteorclient.systems.modules.world;
/*    */ 
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Queue;
/*    */ import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
/*    */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.mixin.AbstractSignEditScreenAccessor;
/*    */ import meteordevelopment.meteorclient.settings.IntSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2625;
/*    */ import net.minecraft.class_2877;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutoSign
/*    */   extends Module
/*    */ {
/* 26 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 28 */   private final Setting<Integer> delay = this.sgGeneral.add((Setting)((IntSetting.Builder)((IntSetting.Builder)((IntSetting.Builder)(new IntSetting.Builder())
/* 29 */       .name("delay"))
/* 30 */       .description("The tick delay between sign update packets."))
/* 31 */       .defaultValue(Integer.valueOf(10)))
/* 32 */       .range(0, 100)
/* 33 */       .sliderRange(0, 100)
/* 34 */       .build());
/*    */ 
/*    */ 
/*    */   
/*    */   private String[] text;
/*    */ 
/*    */   
/* 41 */   private final Queue<class_2877> queue = new ArrayDeque<>();
/* 42 */   private int timer = 0;
/*    */   
/*    */   public AutoSign() {
/* 45 */     super(Categories.World, "auto-sign", "Automatically writes signs. The first sign's text will be used.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDeactivate() {
/* 50 */     this.text = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Post event) {
/* 58 */     if (this.mc.field_1724 == null || this.queue.peek() == null) {
/* 59 */       this.timer = 0;
/*    */       
/*    */       return;
/*    */     } 
/* 63 */     if (this.timer < ((Integer)this.delay.get()).intValue()) {
/* 64 */       this.timer++;
/*    */       
/*    */       return;
/*    */     } 
/* 68 */     this.mc.field_1724.field_3944.method_52787((class_2596)this.queue.poll());
/*    */     
/* 70 */     this.timer = 0;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onSendPacket(PacketEvent.Send event) {
/* 75 */     if (!(event.packet instanceof class_2877))
/*    */       return; 
/* 77 */     this.text = ((class_2877)event.packet).method_12508();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onOpenScreen(OpenScreenEvent event) {
/* 82 */     if (!(event.screen instanceof net.minecraft.class_7743) || this.text == null)
/*    */       return; 
/* 84 */     class_2625 sign = ((AbstractSignEditScreenAccessor)event.screen).meteor$getSign();
/*    */     
/* 86 */     this.queue.add(new class_2877(sign.method_11016(), true, this.text[0], this.text[1], this.text[2], this.text[3]));
/*    */     
/* 88 */     event.cancel();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\world\AutoSign.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */