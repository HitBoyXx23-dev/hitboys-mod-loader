/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.Commands;
/*    */ import meteordevelopment.meteorclient.systems.config.Config;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ import net.minecraft.class_124;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2558;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2568;
/*    */ import net.minecraft.class_5250;
/*    */ 
/*    */ 
/*    */ public class CommandsCommand
/*    */   extends Command
/*    */ {
/*    */   public CommandsCommand() {
/* 23 */     super("commands", "List of all commands.", new String[] { "help" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 28 */     builder.executes(context -> {
/*    */           ChatUtils.info("--- Commands ((highlight)%d(default)) ---", new Object[] { Integer.valueOf(Commands.COMMANDS.size()) });
/*    */           class_5250 commands = class_2561.method_43470("");
/*    */           Commands.COMMANDS.forEach(());
/*    */           ChatUtils.sendMsg((class_2561)commands);
/*    */           return 1;
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private class_5250 getCommandText(Command command) {
/* 41 */     class_5250 tooltip = class_2561.method_43470("");
/*    */     
/* 43 */     tooltip.method_10852((class_2561)class_2561.method_43470(Utils.nameToTitle(command.getName())).method_27695(new class_124[] { class_124.field_1078, class_124.field_1067 })).method_27693("\n");
/*    */     
/* 45 */     class_5250 aliases = class_2561.method_43470((String)(Config.get()).prefix.get() + (String)(Config.get()).prefix.get());
/* 46 */     if (!command.getAliases().isEmpty()) {
/* 47 */       aliases.method_27693(", ");
/* 48 */       for (String alias : command.getAliases()) {
/* 49 */         if (alias.isEmpty())
/* 50 */           continue;  aliases.method_27693((String)(Config.get()).prefix.get() + (String)(Config.get()).prefix.get());
/* 51 */         if (!alias.equals(command.getAliases().getLast())) aliases.method_27693(", "); 
/*    */       } 
/*    */     } 
/* 54 */     tooltip.method_10852((class_2561)aliases.method_27692(class_124.field_1080)).method_27693("\n\n");
/*    */     
/* 56 */     tooltip.method_10852((class_2561)class_2561.method_43470(command.getDescription()).method_27692(class_124.field_1068));
/*    */ 
/*    */     
/* 59 */     class_5250 text = class_2561.method_43470(Utils.nameToTitle(command.getName()));
/* 60 */     if (command != Commands.COMMANDS.getLast())
/* 61 */       text.method_10852((class_2561)class_2561.method_43470(", ").method_27692(class_124.field_1080)); 
/* 62 */     text.method_10862(text
/* 63 */         .method_10866()
/* 64 */         .method_10949((class_2568)new class_2568.class_10613((class_2561)tooltip))
/* 65 */         .method_10958((class_2558)new class_2558.class_10610((String)(Config.get()).prefix.get() + (String)(Config.get()).prefix.get())));
/*    */ 
/*    */     
/* 68 */     return text;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\CommandsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */