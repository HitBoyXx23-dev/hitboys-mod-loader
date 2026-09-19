/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.PlayerArgumentType;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.AutoWasp;
/*    */ import net.minecraft.class_1657;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2561;
/*    */ 
/*    */ public class WaspCommand
/*    */   extends Command {
/* 19 */   private static final SimpleCommandExceptionType CANT_WASP_SELF = new SimpleCommandExceptionType((Message)class_2561.method_43470("You cannot target yourself!"));
/*    */   
/*    */   public WaspCommand() {
/* 22 */     super("wasp", "Sets the auto wasp target.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 27 */     AutoWasp wasp = (AutoWasp)Modules.get().get(AutoWasp.class);
/*    */     
/* 29 */     builder.then(literal("reset").executes(context -> {
/*    */             wasp.disable();
/*    */             
/*    */             return 1;
/*    */           }));
/* 34 */     builder.then(argument("player", (ArgumentType)PlayerArgumentType.create()).executes(context -> {
/*    */             class_1657 player = PlayerArgumentType.get(context);
/*    */             if (player == mc.field_1724)
/*    */               throw CANT_WASP_SELF.create(); 
/*    */             wasp.target = player;
/*    */             wasp.enable();
/*    */             info(player.method_5477().getString() + " set as target.", new Object[0]);
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\WaspCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */