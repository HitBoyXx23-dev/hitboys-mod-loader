/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.net.URI;
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.UUID;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.PlayerListEntryArgumentType;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import meteordevelopment.meteorclient.utils.network.Http;
/*    */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ import meteordevelopment.meteorclient.utils.player.PlayerUtils;
/*    */ import meteordevelopment.meteorclient.utils.render.color.Color;
/*    */ import net.minecraft.class_124;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2558;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2568;
/*    */ import net.minecraft.class_5250;
/*    */ import net.minecraft.class_5251;
/*    */ import net.minecraft.class_640;
/*    */ 
/*    */ public class NameHistoryCommand extends Command {
/*    */   public NameHistoryCommand() {
/* 30 */     super("name-history", "Provides a list of a players previous names from the laby.net api.", new String[] { "history", "names" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 35 */     builder.then(argument("player", (ArgumentType)PlayerListEntryArgumentType.create()).executes(context -> {
/*    */             MeteorExecutor.execute(());
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */   
/*    */   private static class NameHistory {
/*    */     public NameHistoryCommand.Name[] username_history;
/*    */   }
/*    */   
/*    */   private static class Name {
/*    */     public String name;
/*    */     public Date changed_at;
/*    */     public boolean accurate;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\NameHistoryCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */