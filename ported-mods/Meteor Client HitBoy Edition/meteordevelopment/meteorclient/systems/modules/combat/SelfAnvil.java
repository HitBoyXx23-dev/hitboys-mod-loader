/*    */ package meteordevelopment.meteorclient.systems.modules.combat;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*    */ import meteordevelopment.meteorclient.utils.world.BlockUtils;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_2248;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelfAnvil
/*    */   extends Module
/*    */ {
/*    */   public SelfAnvil() {
/* 21 */     super(Categories.Combat, "self-anvil", "Automatically places an anvil on you to prevent other players from going into your hole.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onOpenScreen(OpenScreenEvent event) {
/* 26 */     if (event.screen instanceof net.minecraft.class_471) event.cancel(); 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Pre event) {
/* 31 */     if (BlockUtils.place(this.mc.field_1724.method_24515().method_10069(0, 2, 0), InvUtils.findInHotbar(itemStack -> class_2248.method_9503(itemStack.method_7909()) instanceof net.minecraft.class_2199), 0))
/* 32 */       toggle(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\combat\SelfAnvil.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */