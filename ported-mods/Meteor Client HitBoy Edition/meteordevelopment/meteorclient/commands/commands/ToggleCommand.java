/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.ArrayList;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.ModuleArgumentType;
/*    */ import meteordevelopment.meteorclient.systems.hud.Hud;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ public class ToggleCommand
/*    */   extends Command
/*    */ {
/*    */   public ToggleCommand() {
/* 20 */     super("toggle", "Toggles a module.", new String[] { "t" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 25 */     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder
/* 26 */       .then(((LiteralArgumentBuilder)literal("all")
/* 27 */         .then(literal("on")
/* 28 */           .executes(context -> {
/*    */               (new ArrayList(Modules.get().getAll())).forEach(Module::enable);
/*    */               
/*    */               (Hud.get()).active = true;
/*    */               
/*    */               return 1;
/* 34 */             }))).then(literal("off")
/* 35 */           .executes(context -> {
/*    */               (new ArrayList(Modules.get().getActive())).forEach(Module::toggle);
/*    */ 
/*    */               
/*    */               (Hud.get()).active = false;
/*    */               
/*    */               return 1;
/* 42 */             })))).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)argument("module", (ArgumentType)ModuleArgumentType.create())
/* 43 */         .executes(context -> {
/*    */             Module m = ModuleArgumentType.get(context);
/*    */             
/*    */             m.toggle();
/*    */             m.sendToggledMsg();
/*    */             return 1;
/* 49 */           })).then(literal("on")
/* 50 */           .executes(context -> {
/*    */               Module m = ModuleArgumentType.get(context);
/*    */               
/*    */               m.enable();
/*    */               return 1;
/* 55 */             }))).then(literal("off")
/* 56 */           .executes(context -> {
/*    */               Module m = ModuleArgumentType.get(context);
/*    */ 
/*    */               
/*    */               m.disable();
/*    */               
/*    */               return 1;
/* 63 */             })))).then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)literal("hud")
/* 64 */         .executes(context -> {
/*    */             (Hud.get()).active = !(Hud.get()).active;
/*    */             
/*    */             return 1;
/* 68 */           })).then(literal("on")
/* 69 */           .executes(context -> {
/*    */               (Hud.get()).active = true;
/*    */               
/*    */               return 1;
/* 73 */             }))).then(literal("off")
/* 74 */           .executes(context -> {
/*    */               (Hud.get()).active = false;
/*    */               return 1;
/*    */             })));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\ToggleCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */