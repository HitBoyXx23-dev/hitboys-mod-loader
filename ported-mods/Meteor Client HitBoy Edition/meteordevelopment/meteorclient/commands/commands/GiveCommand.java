/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.utils.player.FindItemResult;
/*    */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2287;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2873;
/*    */ 
/*    */ public class GiveCommand extends Command {
/* 22 */   private static final SimpleCommandExceptionType NOT_IN_CREATIVE = new SimpleCommandExceptionType((Message)class_2561.method_43470("You must be in creative mode to use this."));
/* 23 */   private static final SimpleCommandExceptionType NO_SPACE = new SimpleCommandExceptionType((Message)class_2561.method_43470("No space in hotbar."));
/*    */   
/*    */   public GiveCommand() {
/* 26 */     super("give", "Gives you any item.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 31 */     builder.then(((RequiredArgumentBuilder)argument("item", (ArgumentType)class_2287.method_9776(REGISTRY_ACCESS)).executes(context -> {
/*    */             if (!(mc.field_1724.method_31549()).field_7477) {
/*    */               throw NOT_IN_CREATIVE.create();
/*    */             }
/*    */             class_1799 item = class_2287.method_9777(context, "item").method_9781(1, false);
/*    */             giveItem(item);
/*    */             return 1;
/* 38 */           })).then(argument("number", (ArgumentType)IntegerArgumentType.integer(1, 99)).executes(context -> {
/*    */               if (!(mc.field_1724.method_31549()).field_7477) {
/*    */                 throw NOT_IN_CREATIVE.create();
/*    */               }
/*    */               class_1799 item = class_2287.method_9777(context, "item").method_9781(IntegerArgumentType.getInteger(context, "number"), true);
/*    */               giveItem(item);
/*    */               return 1;
/*    */             })));
/*    */   }
/*    */   
/*    */   private void giveItem(class_1799 item) throws CommandSyntaxException {
/* 49 */     FindItemResult fir = InvUtils.find(class_1799::method_7960, 0, 8);
/* 50 */     if (!fir.found()) throw NO_SPACE.create();
/*    */     
/* 52 */     mc.method_1562().method_52787((class_2596)new class_2873(36 + fir.slot(), item));
/* 53 */     mc.field_1724.field_7498.method_7611(36 + fir.slot()).method_53512(item);
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\GiveCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */