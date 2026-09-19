/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.List;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ import net.minecraft.class_124;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2568;
/*    */ import net.minecraft.class_5250;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BindsCommand
/*    */   extends Command
/*    */ {
/*    */   public BindsCommand() {
/* 24 */     super("binds", "List of all bound modules.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 29 */     builder.executes(context -> {
/*    */           List<Module> modules = Modules.get().getAll().stream().filter(()).toList();
/*    */           ChatUtils.info("--- Bound Modules ((highlight)%d(default)) ---", new Object[] { Integer.valueOf(modules.size()) });
/*    */           for (Module module : modules) {
/*    */             class_2568.class_10613 class_10613 = new class_2568.class_10613((class_2561)getTooltip(module));
/*    */             class_5250 text = class_2561.method_43470(module.title).method_27692(class_124.field_1068);
/*    */             text.method_10862(text.method_10866().method_10949((class_2568)class_10613));
/*    */             class_5250 sep = class_2561.method_43470(" - ");
/*    */             sep.method_10862(sep.method_10866().method_10949((class_2568)class_10613));
/*    */             text.method_10852((class_2561)sep.method_27692(class_124.field_1080));
/*    */             class_5250 key = class_2561.method_43470(module.keybind.toString());
/*    */             key.method_10862(key.method_10866().method_10949((class_2568)class_10613));
/*    */             text.method_10852((class_2561)key.method_27692(class_124.field_1080));
/*    */             ChatUtils.sendMsg((class_2561)text);
/*    */           } 
/*    */           return 1;
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private class_5250 getTooltip(Module module) {
/* 59 */     class_5250 tooltip = class_2561.method_43470(Utils.nameToTitle(module.title)).method_27695(new class_124[] { class_124.field_1078, class_124.field_1067 }).method_27693("\n\n");
/* 60 */     tooltip.method_10852((class_2561)class_2561.method_43470(module.description).method_27692(class_124.field_1068));
/* 61 */     return tooltip;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\BindsCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */