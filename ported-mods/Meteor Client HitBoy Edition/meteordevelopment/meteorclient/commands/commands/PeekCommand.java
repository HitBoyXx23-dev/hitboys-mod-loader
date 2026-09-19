/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.utils.Utils;
/*    */ import net.minecraft.class_1533;
/*    */ import net.minecraft.class_1799;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2561;
/*    */ 
/*    */ public class PeekCommand
/*    */   extends Command
/*    */ {
/* 18 */   private static final class_1799[] ITEMS = new class_1799[27];
/* 19 */   private static final SimpleCommandExceptionType CANT_PEEK = new SimpleCommandExceptionType((Message)class_2561.method_43470("You must be holding a storage block or looking at an item frame."));
/*    */   
/*    */   public PeekCommand() {
/* 22 */     super("peek", "Lets you see what's inside storage block items.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 27 */     builder.executes(context -> {
/*    */           if (Utils.openContainer(mc.field_1724.method_6047(), ITEMS, true))
/*    */             return 1; 
/*    */           if (Utils.openContainer(mc.field_1724.method_6079(), ITEMS, true))
/*    */             return 1; 
/*    */           if (mc.field_1692 instanceof class_1533 && Utils.openContainer(((class_1533)mc.field_1692).method_6940(), ITEMS, true))
/*    */             return 1; 
/*    */           throw CANT_PEEK.create();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\PeekCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */