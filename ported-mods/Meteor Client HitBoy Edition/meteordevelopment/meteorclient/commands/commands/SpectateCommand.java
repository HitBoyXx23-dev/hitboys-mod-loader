/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.MeteorClient;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.PlayerArgumentType;
/*    */ import meteordevelopment.meteorclient.events.meteor.KeyEvent;
/*    */ import meteordevelopment.meteorclient.events.meteor.MouseClickEvent;
/*    */ import meteordevelopment.meteorclient.utils.misc.input.Input;
/*    */ import meteordevelopment.orbit.EventHandler;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_310;
/*    */ 
/*    */ public class SpectateCommand
/*    */   extends Command {
/* 21 */   private final StaticListener shiftListener = new StaticListener();
/*    */   
/*    */   public SpectateCommand() {
/* 24 */     super("spectate", "Allows you to spectate nearby players", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 29 */     builder.then(literal("reset").executes(context -> {
/*    */             mc.method_1504((class_1297)mc.field_1724);
/*    */             
/*    */             return 1;
/*    */           }));
/* 34 */     builder.then(argument("player", (ArgumentType)PlayerArgumentType.create()).executes(context -> {
/*    */             mc.method_1504((class_1297)PlayerArgumentType.get(context));
/*    */             mc.field_1724.method_7353((class_2561)class_2561.method_43470("Sneak to un-spectate."), true);
/*    */             MeteorClient.EVENT_BUS.subscribe(this.shiftListener);
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */   
/*    */   private static class StaticListener {
/*    */     @EventHandler
/*    */     private void onKey(KeyEvent event) {
/* 45 */       if (Input.isPressed(SpectateCommand.mc.field_1690.field_1832)) {
/* 46 */         SpectateCommand.mc.method_1504((class_1297)SpectateCommand.mc.field_1724);
/* 47 */         event.cancel();
/* 48 */         MeteorClient.EVENT_BUS.unsubscribe(this);
/*    */       } 
/*    */     }
/*    */     
/*    */     @EventHandler
/*    */     private void onMouse(MouseClickEvent event) {
/* 54 */       if (Input.isPressed(SpectateCommand.mc.field_1690.field_1832)) {
/* 55 */         SpectateCommand.mc.method_1504((class_1297)SpectateCommand.mc.field_1724);
/* 56 */         event.cancel();
/* 57 */         MeteorClient.EVENT_BUS.unsubscribe(this);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\SpectateCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */