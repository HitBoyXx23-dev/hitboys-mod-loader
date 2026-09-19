/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.tree.CommandNode;
/*    */ import com.mojang.brigadier.tree.RootCommandNode;
/*    */ import java.util.Map;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.Commands;
/*    */ import meteordevelopment.meteorclient.commands.arguments.CommandArgumentType;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ import net.minecraft.class_124;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_5250;
/*    */ import net.minecraft.class_637;
/*    */ 
/*    */ public class HelpCommand
/*    */   extends Command {
/*    */   public HelpCommand() {
/* 23 */     super("help", "Shows you what a command does.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 28 */     builder.then(argument("command", (ArgumentType)CommandArgumentType.create()).executes(context -> {
/*    */             showHelp(CommandArgumentType.get(context));
/*    */             
/*    */             return 1;
/*    */           }));
/* 33 */     builder.executes(context -> {
/*    */           showHelp(this);
/*    */           return 1;
/*    */         });
/*    */   }
/*    */   
/*    */   private void showHelp(Command cmd) {
/* 40 */     class_5250 msg = class_2561.method_43470("");
/* 41 */     msg.method_10852((class_2561)class_2561.method_43470("Help for ").method_27692(class_124.field_1080).method_10852((class_2561)class_2561.method_43470(cmd.getName()).method_27692(class_124.field_1054)));
/* 42 */     msg.method_10852((class_2561)class_2561.method_43470("\n ")).method_10852((class_2561)class_2561.method_43470("Description: ").method_27692(class_124.field_1080).method_10852((class_2561)class_2561.method_43470(cmd.getDescription()).method_27692(class_124.field_1068)));
/*    */     
/* 44 */     if (!cmd.getAliases().isEmpty()) {
/* 45 */       msg.method_10852((class_2561)class_2561.method_43470("\n ")).method_10852((class_2561)class_2561.method_43470("Aliases: ").method_27692(class_124.field_1080));
/* 46 */       msg.method_10852((class_2561)class_2561.method_43470(String.join(", ", cmd.getAliases())).method_27692(class_124.field_1075));
/*    */     } 
/*    */     
/* 49 */     msg.method_10852((class_2561)getUsageText(cmd));
/* 50 */     ChatUtils.sendMsg((class_2561)msg);
/*    */   }
/*    */   
/*    */   private class_5250 getUsageText(Command cmd) {
/* 54 */     class_637 class_637 = mc.method_1562().method_2875();
/* 55 */     RootCommandNode rootCommandNode = Commands.DISPATCHER.getRoot();
/* 56 */     CommandNode<class_2172> node = rootCommandNode.getChild(cmd.getName());
/*    */     
/* 58 */     class_5250 usagesText = class_2561.method_43470("");
/*    */     
/* 60 */     if (node != null) {
/* 61 */       Map<CommandNode<class_2172>, String> usages = Commands.DISPATCHER.getSmartUsage(node, class_637);
/*    */       
/* 63 */       for (String usage : usages.values()) {
/* 64 */         usagesText.method_10852((class_2561)class_2561.method_43470("\n " + String.valueOf(cmd) + " ").method_27692(class_124.field_1060)).method_10852((class_2561)class_2561.method_43470(usage).method_27692(class_124.field_1060));
/*    */       }
/*    */     } 
/*    */     
/* 68 */     if (usagesText.getString().isEmpty()) {
/* 69 */       usagesText.method_10852((class_2561)class_2561.method_43470("\n " + String.valueOf(cmd)).method_27692(class_124.field_1060));
/*    */     }
/*    */     
/* 72 */     return class_2561.method_43470("\n Usage:").method_27692(class_124.field_1080).method_10852((class_2561)usagesText);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\HelpCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */