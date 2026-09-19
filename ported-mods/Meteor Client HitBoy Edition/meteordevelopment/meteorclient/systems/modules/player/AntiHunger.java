/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.entity.player.SendMovementPacketsEvent;
/*    */ import meteordevelopment.meteorclient.events.packets.PacketEvent;
/*    */ import meteordevelopment.meteorclient.mixin.PlayerMoveC2SPacketAccessor;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2828;
/*    */ import net.minecraft.class_2848;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AntiHunger
/*    */   extends Module
/*    */ {
/* 21 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 23 */   private final Setting<Boolean> sprint = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 24 */       .name("sprint"))
/* 25 */       .description("Spoofs sprinting packets."))
/* 26 */       .defaultValue(Boolean.valueOf(true)))
/* 27 */       .build());
/*    */ 
/*    */   
/* 30 */   private final Setting<Boolean> onGround = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 31 */       .name("on-ground"))
/* 32 */       .description("Spoofs the onGround flag."))
/* 33 */       .defaultValue(Boolean.valueOf(true)))
/* 34 */       .build());
/*    */   
/*    */   private boolean lastOnGround;
/*    */   private boolean ignorePacket;
/*    */   
/*    */   public AntiHunger() {
/* 40 */     super(Categories.Player, "anti-hunger", "Reduces (does NOT remove) hunger consumption.");
/*    */   }
/*    */ 
/*    */   
/*    */   public void onActivate() {
/* 45 */     this.lastOnGround = this.mc.field_1724.method_24828();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onSendPacket(PacketEvent.Send event) {
/* 50 */     if (this.ignorePacket && event.packet instanceof class_2828) {
/* 51 */       this.ignorePacket = false;
/*    */       
/*    */       return;
/*    */     } 
/* 55 */     if (this.mc.field_1724.method_5765() || this.mc.field_1724.method_5799() || this.mc.field_1724.method_5869())
/*    */       return; 
/* 57 */     class_2596 class_2596 = event.packet; if (class_2596 instanceof class_2848) { class_2848 packet = (class_2848)class_2596; if (((Boolean)this.sprint.get()).booleanValue() && 
/* 58 */         packet.method_12365() == class_2848.class_2849.field_12981) event.cancel();
/*    */        }
/*    */     
/* 61 */     class_2596 = event.packet; if (class_2596 instanceof class_2828) { class_2828 packet = (class_2828)class_2596; if (((Boolean)this.onGround.get()).booleanValue() && this.mc.field_1724.method_24828() && this.mc.field_1724.field_6017 <= 0.0D && !this.mc.field_1761.method_2923())
/* 62 */         ((PlayerMoveC2SPacketAccessor)packet).meteor$setOnGround(false);  }
/*    */   
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(SendMovementPacketsEvent.Pre event) {
/* 68 */     if (this.mc.field_1724.method_24828() && !this.lastOnGround && ((Boolean)this.onGround.get()).booleanValue()) {
/* 69 */       this.ignorePacket = true;
/*    */     }
/*    */     
/* 72 */     this.lastOnGround = this.mc.field_1724.method_24828();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\AntiHunger.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */