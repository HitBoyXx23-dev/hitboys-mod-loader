/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.ModuleArgumentType;
/*    */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*    */ import meteordevelopment.meteorclient.settings.Setting;
/*    */ import meteordevelopment.meteorclient.settings.SettingGroup;
/*    */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ public class ResetCommand
/*    */   extends Command
/*    */ {
/*    */   public ResetCommand() {
/* 22 */     super("reset", "Resets specified settings.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 27 */     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder.then(((LiteralArgumentBuilder)literal("settings")
/* 28 */         .then(argument("module", (ArgumentType)ModuleArgumentType.create()).executes(context -> {
/*    */               Module module = (Module)context.getArgument("module", Module.class);
/*    */               
/*    */               module.settings.forEach(());
/*    */               module.info("Reset all settings.", new Object[0]);
/*    */               return 1;
/* 34 */             }))).then(literal("all").executes(context -> {
/*    */               Modules.get().getAll().forEach(());
/*    */               
/*    */               ChatUtils.infoPrefix("Modules", "Reset all module settings", new Object[0]);
/*    */               return 1;
/* 39 */             })))).then(literal("gui").executes(context -> {
/*    */             GuiThemes.get().clearWindowConfigs();
/*    */             (GuiThemes.get()).settings.reset();
/*    */             ChatUtils.info("Reset all GUI settings.", new Object[0]);
/*    */             return 1;
/* 44 */           }))).then(((LiteralArgumentBuilder)literal("bind")
/* 45 */         .then(argument("module", (ArgumentType)ModuleArgumentType.create()).executes(context -> {
/*    */               Module module = (Module)context.getArgument("module", Module.class);
/*    */               
/*    */               module.keybind.reset();
/*    */               
/*    */               module.info("Reset bind.", new Object[0]);
/*    */               
/*    */               return 1;
/* 53 */             }))).then(literal("all").executes(context -> {
/*    */               Modules.get().getAll().forEach(());
/*    */               
/*    */               ChatUtils.infoPrefix("Modules", "Reset all binds.", new Object[0]);
/*    */               return 1;
/* 58 */             })))).then(literal("hud").executes(context -> {
/*    */             Hud.get().resetToDefaultElements();
/*    */             ChatUtils.infoPrefix("HUD", "Reset all elements.", new Object[0]);
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\ResetCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */