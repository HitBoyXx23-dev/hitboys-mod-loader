/*    */ package meteordevelopment.meteorclient.systems.modules.misc;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.entity.EntityAddedEvent;
/*    */ import meteordevelopment.meteorclient.settings.BoolSetting;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.settings.StringSetting;
/*    */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1657;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageAura
/*    */   extends Module
/*    */ {
/* 21 */   private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
/*    */   
/* 23 */   private final Setting<String> message = this.sgGeneral.add((Setting)((StringSetting.Builder)((StringSetting.Builder)((StringSetting.Builder)(new StringSetting.Builder())
/* 24 */       .name("message"))
/* 25 */       .description("The specified message sent to the player."))
/* 26 */       .defaultValue("Meteor on Crack!"))
/* 27 */       .build());
/*    */ 
/*    */   
/* 30 */   private final Setting<Boolean> ignoreFriends = this.sgGeneral.add((Setting)((BoolSetting.Builder)((BoolSetting.Builder)((BoolSetting.Builder)(new BoolSetting.Builder())
/* 31 */       .name("ignore-friends"))
/* 32 */       .description("Will not send any messages to people friended."))
/* 33 */       .defaultValue(Boolean.valueOf(false)))
/* 34 */       .build());
/*    */ 
/*    */   
/*    */   public MessageAura() {
/* 38 */     super(Categories.Misc, "message-aura", "Sends a specified message to any player that enters render distance.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onEntityAdded(EntityAddedEvent event) {
/* 43 */     if (!(event.entity instanceof class_1657) || event.entity.method_5667().equals(this.mc.field_1724.method_5667()))
/*    */       return; 
/* 45 */     if (!((Boolean)this.ignoreFriends.get()).booleanValue() || (((Boolean)this.ignoreFriends.get()).booleanValue() && !Friends.get().isFriend((class_1657)event.entity)))
/* 46 */       ChatUtils.sendPlayerMsg("/msg " + event.entity.method_5477().getString() + " " + (String)this.message.get()); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\misc\MessageAura.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */