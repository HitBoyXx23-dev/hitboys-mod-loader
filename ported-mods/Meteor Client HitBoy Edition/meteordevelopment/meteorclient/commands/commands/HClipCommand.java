/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.DoubleArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import net.minecraft.class_1297;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_243;
/*    */ 
/*    */ public class HClipCommand
/*    */   extends Command
/*    */ {
/*    */   public HClipCommand() {
/* 17 */     super("hclip", "Lets you clip through blocks horizontally.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 22 */     builder.then(argument("blocks", (ArgumentType)DoubleArgumentType.doubleArg()).executes(context -> {
/*    */             double blocks = ((Double)context.getArgument("blocks", Double.class)).doubleValue();
/*    */             class_243 forward = class_243.method_1030(0.0F, mc.field_1724.method_36454()).method_1029();
/*    */             if (mc.field_1724.method_5765()) {
/*    */               class_1297 vehicle = mc.field_1724.method_5854();
/*    */               vehicle.method_5814(vehicle.method_23317() + forward.field_1352 * blocks, vehicle.method_23318(), vehicle.method_23321() + forward.field_1350 * blocks);
/*    */             } 
/*    */             mc.field_1724.method_5814(mc.field_1724.method_23317() + forward.field_1352 * blocks, mc.field_1724.method_23318(), mc.field_1724.method_23321() + forward.field_1350 * blocks);
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\HClipCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */