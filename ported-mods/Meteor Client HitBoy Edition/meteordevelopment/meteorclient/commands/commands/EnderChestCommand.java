/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import net.minecraft.class_1802;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnderChestCommand
/*    */   extends Command
/*    */ {
/*    */   public EnderChestCommand() {
/* 17 */     super("ender-chest", "Allows you to preview memory of your ender chest.", new String[] { "ec", "echest" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 22 */     builder.executes(context -> {
/*    */           Utils.openContainer(class_1802.field_8466.method_7854(), new net.minecraft.class_1799[27], true);
/*    */           return 1;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\EnderChestCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */