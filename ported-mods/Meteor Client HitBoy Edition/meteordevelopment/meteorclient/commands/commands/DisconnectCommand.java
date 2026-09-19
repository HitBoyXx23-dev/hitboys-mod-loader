/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import net.minecraft.class_124;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2661;
/*    */ 
/*    */ public class DisconnectCommand
/*    */   extends Command
/*    */ {
/*    */   public DisconnectCommand() {
/* 18 */     super("disconnect", "Disconnect from the server", new String[] { "dc" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 23 */     builder.executes(context -> {
/*    */           mc.field_1724.field_3944.method_52781(new class_2661((class_2561)class_2561.method_43470("%s[%sDisconnectCommand%s] Disconnected by user.".formatted(new Object[] { class_124.field_1080, class_124.field_1078, class_124.field_1080 }))));
/*    */           
/*    */           return 1;
/*    */         });
/* 28 */     builder.then(argument("reason", (ArgumentType)StringArgumentType.greedyString()).executes(context -> {
/*    */             mc.field_1724.field_3944.method_52781(new class_2661((class_2561)class_2561.method_43470(StringArgumentType.getString(context, "reason"))));
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\DisconnectCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */