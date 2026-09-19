/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.systems.modules.Category;
/*    */ import meteordevelopment.meteorclient.systems.modules.Module;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ import net.minecraft.class_124;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2568;
/*    */ import net.minecraft.class_5250;
/*    */ 
/*    */ public class ModulesCommand
/*    */   extends Command
/*    */ {
/*    */   public ModulesCommand() {
/* 21 */     super("modules", "Displays a list of all modules.", new String[] { "features" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 26 */     builder.executes(context -> {
/*    */           ChatUtils.info("--- Modules ((highlight)%d(default)) ---", new Object[] { Integer.valueOf(Modules.get().getCount()) });
/*    */           Modules.loopCategories().forEach(());
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
/*    */   private class_5250 getModuleText(Module module) {
/* 41 */     class_5250 tooltip = class_2561.method_43470("");
/*    */     
/* 43 */     tooltip.method_10852((class_2561)class_2561.method_43470(module.title).method_27695(new class_124[] { class_124.field_1078, class_124.field_1067 })).method_27693("\n");
/* 44 */     tooltip.method_10852((class_2561)class_2561.method_43470(module.name).method_27692(class_124.field_1080)).method_27693("\n\n");
/* 45 */     tooltip.method_10852((class_2561)class_2561.method_43470(module.description).method_27692(class_124.field_1068));
/*    */     
/* 47 */     class_5250 finalModule = class_2561.method_43470(module.title);
/* 48 */     if (!module.isActive()) finalModule.method_27692(class_124.field_1080); 
/* 49 */     if (!module.equals(Modules.get().getGroup(module.category).getLast())) finalModule.method_10852((class_2561)class_2561.method_43470(", ").method_27692(class_124.field_1080)); 
/* 50 */     finalModule.method_10862(finalModule.method_10866().method_10949((class_2568)new class_2568.class_10613((class_2561)tooltip)));
/*    */     
/* 52 */     return finalModule;
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\ModulesCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */