/*     */ package meteordevelopment.meteorclient.commands.commands;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import meteordevelopment.meteorclient.commands.arguments.ModuleArgumentType;
/*     */ import meteordevelopment.meteorclient.commands.arguments.SettingArgumentType;
/*     */ import meteordevelopment.meteorclient.commands.arguments.SettingValueArgumentType;
/*     */ import meteordevelopment.meteorclient.gui.GuiThemes;
/*     */ import meteordevelopment.meteorclient.gui.WidgetScreen;
/*     */ import meteordevelopment.meteorclient.gui.tabs.TabScreen;
/*     */ import meteordevelopment.meteorclient.gui.tabs.Tabs;
/*     */ import meteordevelopment.meteorclient.gui.tabs.builtin.ConfigTab;
/*     */ import meteordevelopment.meteorclient.gui.tabs.builtin.HudTab;
/*     */ import meteordevelopment.meteorclient.settings.Setting;
/*     */ import meteordevelopment.meteorclient.systems.config.Config;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_437;
/*     */ 
/*     */ public class SettingCommand extends Command {
/*     */   public SettingCommand() {
/*  28 */     super("settings", "Allows you to view and change module settings.", new String[] { "s" });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/*  34 */     builder.then(
/*  35 */         literal("hud")
/*  36 */         .executes(context -> {
/*     */             TabScreen screen = Tabs.get(HudTab.class).createScreen(GuiThemes.get());
/*     */             
/*     */             screen.parent = null;
/*     */             
/*     */             Utils.screenToOpen = (class_437)screen;
/*     */             
/*     */             return 1;
/*     */           }));
/*     */     
/*  46 */     builder.then(
/*  47 */         literal("config")
/*  48 */         .executes(context -> {
/*     */             TabScreen screen = Tabs.get(ConfigTab.class).createScreen(GuiThemes.get());
/*     */             
/*     */             screen.parent = null;
/*     */             
/*     */             Utils.screenToOpen = (class_437)screen;
/*     */             
/*     */             return 1;
/*     */           }));
/*     */     
/*  58 */     builder.then(
/*  59 */         literal("config").then((
/*  60 */           (RequiredArgumentBuilder)argument("setting", (ArgumentType)SettingArgumentType.create())
/*  61 */           .executes(context -> {
/*     */               Setting<?> setting = SettingArgumentType.get(context, (Config.get()).settings);
/*     */ 
/*     */               
/*     */               ChatUtils.infoPrefix("Config", "Setting (highlight)%s(default) is (highlight)%s(default).", new Object[] { setting.title, setting.get() });
/*     */               
/*     */               return 1;
/*  68 */             })).suggests((ctx, suggestionsBuilder) -> SettingArgumentType.listSuggestions(suggestionsBuilder, (Config.get()).settings))
/*     */ 
/*     */           
/*  71 */           .then(
/*  72 */             (ArgumentBuilder)((RequiredArgumentBuilder)argument("value", (ArgumentType)SettingValueArgumentType.create())
/*  73 */             .executes(context -> {
/*     */                 Setting<?> setting = SettingArgumentType.get(context, (Config.get()).settings);
/*     */                 
/*     */                 String value = SettingValueArgumentType.get(context);
/*     */                 
/*     */                 if (setting.parse(value)) {
/*     */                   ChatUtils.infoPrefix("Config", "Setting (highlight)%s(default) changed to (highlight)%s(default).", new Object[] { setting.title, value });
/*     */                 }
/*     */                 
/*     */                 return 1;
/*  83 */               })).suggests((context, suggestionsBuilder) -> SettingValueArgumentType.listSuggestions(context, suggestionsBuilder, (Config.get()).settings)))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  91 */     builder.then(
/*  92 */         argument("module", (ArgumentType)ModuleArgumentType.create())
/*  93 */         .executes(context -> {
/*     */             Module module = (Module)context.getArgument("module", Module.class);
/*     */             
/*     */             WidgetScreen screen = GuiThemes.get().moduleScreen(module);
/*     */             
/*     */             screen.parent = null;
/*     */             
/*     */             Utils.screenToOpen = (class_437)screen;
/*     */             
/*     */             return 1;
/*     */           }));
/*     */     
/* 105 */     builder.then(
/* 106 */         argument("module", (ArgumentType)ModuleArgumentType.create())
/* 107 */         .then((
/* 108 */           (RequiredArgumentBuilder)argument("setting", (ArgumentType)SettingArgumentType.create())
/* 109 */           .executes(context -> {
/*     */               Setting<?> setting = SettingArgumentType.get(context);
/*     */ 
/*     */               
/*     */               ModuleArgumentType.get(context).info("Setting (highlight)%s(default) is (highlight)%s(default).", new Object[] { setting.title, setting.get() });
/*     */ 
/*     */               
/*     */               return 1;
/* 117 */             })).then(
/* 118 */             argument("value", (ArgumentType)SettingValueArgumentType.create())
/* 119 */             .executes(context -> {
/*     */                 Setting<?> setting = SettingArgumentType.get(context);
/*     */                 String value = SettingValueArgumentType.get(context);
/*     */                 if (setting.parse(value))
/*     */                   ModuleArgumentType.get(context).info("Setting (highlight)%s(default) changed to (highlight)%s(default).", new Object[] { setting.title, value }); 
/*     */                 return 1;
/*     */               }))));
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\SettingCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */