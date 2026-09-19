/*    */ package meteordevelopment.meteorclient.systems.modules.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
/*    */ import meteordevelopment.meteorclient.systems.modules.Categories;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.render.WaypointsModule;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutoRespawn
/*    */   extends Module
/*    */ {
/*    */   public AutoRespawn() {
/* 19 */     super(Categories.Player, "auto-respawn", "Automatically respawns after death.");
/*    */   }
/*    */   
/*    */   @EventHandler(priority = 100)
/*    */   private void onOpenScreenEvent(OpenScreenEvent event) {
/* 24 */     if (!(event.screen instanceof net.minecraft.class_418))
/*    */       return; 
/* 26 */     ((WaypointsModule)Modules.get().get(WaypointsModule.class)).addDeath(this.mc.field_1724.method_73189());
/* 27 */     this.mc.field_1724.method_7331();
/* 28 */     event.cancel();
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\systems\modules\player\AutoRespawn.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */