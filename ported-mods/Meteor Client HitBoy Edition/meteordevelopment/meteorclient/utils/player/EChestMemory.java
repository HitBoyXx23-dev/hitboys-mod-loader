/*    */ package meteordevelopment.meteorclient.utils.player;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.game.GameLeftEvent;
/*    */ import meteordevelopment.meteorclient.events.game.OpenScreenEvent;
/*    */ import meteordevelopment.meteorclient.events.world.BlockActivateEvent;
/*    */ import meteordevelopment.meteorclient.utils.PreInit;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1263;
/*    */ import net.minecraft.class_1707;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_2371;
/*    */ import net.minecraft.class_476;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EChestMemory
/*    */ {
/* 24 */   public static final class_2371<class_1799> ITEMS = class_2371.method_10213(27, class_1799.field_8037);
/*    */   
/*    */   private static int echestOpenedState;
/*    */   
/*    */   private static boolean isKnown = false;
/*    */ 
/*    */   
/*    */   @PreInit
/*    */   public static void init() {
/* 33 */     MeteorClient.EVENT_BUS.subscribe(EChestMemory.class);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private static void onBlockActivate(BlockActivateEvent event) {
/* 38 */     if (event.blockState.method_26204() instanceof net.minecraft.class_2336 && echestOpenedState == 0) echestOpenedState = 1; 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private static void onOpenScreenEvent(OpenScreenEvent event) {
/* 43 */     if (echestOpenedState == 1 && event.screen instanceof class_476) {
/* 44 */       echestOpenedState = 2;
/*    */       return;
/*    */     } 
/* 47 */     if (echestOpenedState == 0)
/*    */       return; 
/* 49 */     if (!(MeteorClient.mc.field_1755 instanceof class_476))
/* 50 */       return;  class_1707 container = (class_1707)((class_476)MeteorClient.mc.field_1755).method_17577();
/* 51 */     if (container == null)
/* 52 */       return;  class_1263 inv = container.method_7629();
/*    */     
/* 54 */     for (int i = 0; i < 27; i++) {
/* 55 */       ITEMS.set(i, inv.method_5438(i));
/*    */     }
/* 57 */     isKnown = true;
/*    */     
/* 59 */     echestOpenedState = 0;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private static void onLeaveEvent(GameLeftEvent event) {
/* 64 */     ITEMS.clear();
/* 65 */     isKnown = false;
/*    */   }
/*    */   
/*    */   public static boolean isKnown() {
/* 69 */     return isKnown;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclien\\utils\player\EChestMemory.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */