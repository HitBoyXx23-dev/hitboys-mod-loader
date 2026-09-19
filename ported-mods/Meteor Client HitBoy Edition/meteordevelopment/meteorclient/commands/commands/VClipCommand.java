/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.DoubleArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2828;
/*    */ import net.minecraft.class_2833;
/*    */ 
/*    */ public class VClipCommand
/*    */   extends Command {
/*    */   public VClipCommand() {
/* 17 */     super("vclip", "Lets you clip through blocks vertically.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 22 */     builder.then(argument("blocks", (ArgumentType)DoubleArgumentType.doubleArg()).executes(context -> {
/*    */             double blocks = ((Double)context.getArgument("blocks", Double.class)).doubleValue();
/*    */             int packetsRequired = (int)Math.ceil(Math.abs(blocks / 10.0D));
/*    */             if (packetsRequired > 20)
/*    */               packetsRequired = 1; 
/*    */             if (mc.field_1724.method_5765()) {
/*    */               for (int packetNumber = 0; packetNumber < packetsRequired - 1; packetNumber++)
/*    */                 mc.field_1724.field_3944.method_52787((class_2596)class_2833.method_65307(mc.field_1724.method_5854())); 
/*    */               mc.field_1724.method_5854().method_5814(mc.field_1724.method_5854().method_23317(), mc.field_1724.method_5854().method_23318() + blocks, mc.field_1724.method_5854().method_23321());
/*    */               mc.field_1724.field_3944.method_52787((class_2596)class_2833.method_65307(mc.field_1724.method_5854()));
/*    */             } else {
/*    */               for (int packetNumber = 0; packetNumber < packetsRequired - 1; packetNumber++)
/*    */                 mc.field_1724.field_3944.method_52787((class_2596)new class_2828.class_5911(true, mc.field_1724.field_5976)); 
/*    */               mc.field_1724.field_3944.method_52787((class_2596)new class_2828.class_2829(mc.field_1724.method_23317(), mc.field_1724.method_23318() + blocks, mc.field_1724.method_23321(), true, mc.field_1724.field_5976));
/*    */               mc.field_1724.method_5814(mc.field_1724.method_23317(), mc.field_1724.method_23318() + blocks, mc.field_1724.method_23321());
/*    */             } 
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\VClipCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */