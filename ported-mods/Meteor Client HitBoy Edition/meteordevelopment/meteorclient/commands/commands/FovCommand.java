/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.mixininterface.ISimpleOption;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ public class FovCommand
/*    */   extends Command
/*    */ {
/*    */   public FovCommand() {
/* 16 */     super("fov", "Changes your fov.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 21 */     builder.then(argument("fov", (ArgumentType)IntegerArgumentType.integer(1, 180)).executes(context -> {
/*    */             ((ISimpleOption)mc.field_1690.method_41808()).meteor$set(context.getArgument("fov", Integer.class));
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\FovCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */