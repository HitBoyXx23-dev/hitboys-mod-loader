/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import net.minecraft.class_10185;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2851;
/*    */ 
/*    */ public class DismountCommand
/*    */   extends Command
/*    */ {
/*    */   public DismountCommand() {
/* 16 */     super("dismount", "Dismounts you from entity you are riding.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 21 */     builder.executes(context -> {
/*    */           class_10185 sneak = new class_10185(false, false, false, false, false, true, false);
/*    */           mc.method_1562().method_52787((class_2596)new class_2851(sneak));
/*    */           return 1;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\DismountCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */