/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.ModuleArgumentType;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ public class BindCommand
/*    */   extends Command
/*    */ {
/*    */   public BindCommand() {
/* 17 */     super("bind", "Binds a specified module to the next pressed key.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 22 */     builder.then(argument("module", (ArgumentType)ModuleArgumentType.create()).executes(context -> {
/*    */             Module module = (Module)context.getArgument("module", Module.class);
/*    */             Modules.get().setModuleToBind(module);
/*    */             Modules.get().awaitKeyRelease();
/*    */             module.info("Press a key to bind the module to.", new Object[0]);
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\BindCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */