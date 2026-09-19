/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*    */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*    */ import meteordevelopment.meteorclient.utils.player.Rotations;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_1792;
/*    */ import net.minecraft.class_1802;
/*    */ 
/*    */ 
/*    */ public class EXPThrower
/*    */   extends Module
/*    */ {
/*    */   public EXPThrower() {
/* 19 */     super(Categories.Player, "exp-thrower", "Automatically throws XP bottles from your hotbar.");
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onTick(TickEvent.Pre event) {
/* 24 */     FindItemResult exp = InvUtils.findInHotbar(new class_1792[] { class_1802.field_8287 });
/* 25 */     if (!exp.found())
/*    */       return; 
/* 27 */     Rotations.rotate(this.mc.field_1724.method_36454(), 90.0D, () -> {
/*    */           if (exp.getHand() != null) {
/*    */             this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, exp.getHand());
/*    */           } else {
/*    */             InvUtils.swap(exp.slot(), true);
/*    */             this.mc.field_1761.method_2919((class_1657)this.mc.field_1724, exp.getHand());
/*    */             InvUtils.swapBack();
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\EXPThrower.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */