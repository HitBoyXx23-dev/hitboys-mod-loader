/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.DirectionArgumentType;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_2350;
/*    */ import net.minecraft.class_3532;
/*    */ 
/*    */ public class RotationCommand
/*    */   extends Command {
/*    */   public RotationCommand() {
/* 18 */     super("rotation", "Modifies your rotation.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 23 */     ((LiteralArgumentBuilder)builder
/* 24 */       .then(((LiteralArgumentBuilder)literal("set")
/* 25 */         .then(argument("direction", (ArgumentType)DirectionArgumentType.create())
/* 26 */           .executes(context -> {
/*    */               mc.field_1724.method_36457((((class_2350)context.getArgument("direction", class_2350.class)).method_62675().method_10264() * -90));
/*    */               
/*    */               mc.field_1724.method_36456(((class_2350)context.getArgument("direction", class_2350.class)).method_10144());
/*    */               
/*    */               return 1;
/* 32 */             }))).then(((RequiredArgumentBuilder)argument("pitch", (ArgumentType)FloatArgumentType.floatArg(-90.0F, 90.0F))
/* 33 */           .executes(context -> {
/*    */               mc.field_1724.method_36457(((Float)context.getArgument("pitch", Float.class)).floatValue());
/*    */ 
/*    */               
/*    */               return 1;
/* 38 */             })).then(argument("yaw", (ArgumentType)FloatArgumentType.floatArg(-180.0F, 180.0F))
/* 39 */             .executes(context -> {
/*    */                 mc.field_1724.method_36457(((Float)context.getArgument("pitch", Float.class)).floatValue());
/*    */ 
/*    */ 
/*    */                 
/*    */                 mc.field_1724.method_36456(((Float)context.getArgument("yaw", Float.class)).floatValue());
/*    */ 
/*    */                 
/*    */                 return 1;
/* 48 */               }))))).then(literal("add")
/* 49 */         .then(((RequiredArgumentBuilder)argument("pitch", (ArgumentType)FloatArgumentType.floatArg(-90.0F, 90.0F))
/* 50 */           .executes(context -> {
/*    */               float pitch = mc.field_1724.method_36455() + ((Float)context.getArgument("pitch", Float.class)).floatValue();
/*    */               
/*    */               mc.field_1724.method_36457((pitch >= 0.0F) ? Math.min(pitch, 90.0F) : Math.max(pitch, -90.0F));
/*    */               
/*    */               return 1;
/* 56 */             })).then(argument("yaw", (ArgumentType)FloatArgumentType.floatArg(-180.0F, 180.0F))
/* 57 */             .executes(context -> {
/*    */                 float pitch = mc.field_1724.method_36455() + ((Float)context.getArgument("pitch", Float.class)).floatValue();
/*    */                 mc.field_1724.method_36457((pitch >= 0.0F) ? Math.min(pitch, 90.0F) : Math.max(pitch, -90.0F));
/*    */                 float yaw = mc.field_1724.method_36454() + ((Float)context.getArgument("yaw", Float.class)).floatValue();
/*    */                 mc.field_1724.method_36456(class_3532.method_15393(yaw));
/*    */                 return 1;
/*    */               }))));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\RotationCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */