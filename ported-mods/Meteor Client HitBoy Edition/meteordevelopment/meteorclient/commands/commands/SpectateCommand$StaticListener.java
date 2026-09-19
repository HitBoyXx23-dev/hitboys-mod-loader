/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.events.meteor.KeyEvent;
/*    */ import meteordevelopment.meteorclient.events.meteor.MouseClickEvent;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.Input;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1297;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class StaticListener
/*    */ {
/*    */   @EventHandler
/*    */   private void onKey(KeyEvent event) {
/* 45 */     if (Input.isPressed((SpectateCommand.access$000()).field_1690.field_1832)) {
/* 46 */       SpectateCommand.access$200().method_1504((class_1297)(SpectateCommand.access$100()).field_1724);
/* 47 */       event.cancel();
/* 48 */       MeteorClient.EVENT_BUS.unsubscribe(this);
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   private void onMouse(MouseClickEvent event) {
/* 54 */     if (Input.isPressed((SpectateCommand.access$300()).field_1690.field_1832)) {
/* 55 */       SpectateCommand.access$500().method_1504((class_1297)(SpectateCommand.access$400()).field_1724);
/* 56 */       event.cancel();
/* 57 */       MeteorClient.EVENT_BUS.unsubscribe(this);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\SpectateCommand$StaticListener.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */