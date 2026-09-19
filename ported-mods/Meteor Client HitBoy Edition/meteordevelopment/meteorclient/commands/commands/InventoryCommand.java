/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.PlayerArgumentType;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_437;
/*    */ import net.minecraft.class_490;
/*    */ 
/*    */ public class InventoryCommand
/*    */   extends Command {
/*    */   public InventoryCommand() {
/* 17 */     super("inventory", "Allows you to see parts of another player's inventory.", new String[] { "inv", "invsee" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 22 */     builder.then(argument("player", (ArgumentType)PlayerArgumentType.create()).executes(context -> {
/*    */             Utils.screenToOpen = (class_437)new class_490(PlayerArgumentType.get(context));
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\InventoryCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */