/*     */ package meteordevelopment.meteorclient.commands.commands;
/*     */ 
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import meteordevelopment.meteorclient.commands.Command;
/*     */ import meteordevelopment.meteorclient.utils.player.InvUtils;
/*     */ import net.minecraft.class_1304;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1802;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_2287;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_746;
/*     */ import net.minecraft.class_9274;
/*     */ 
/*     */ public class DropCommand
/*     */   extends Command
/*     */ {
/*  25 */   private static final SimpleCommandExceptionType NOT_SPECTATOR = new SimpleCommandExceptionType((Message)class_2561.method_43470("Can't drop items while in spectator."));
/*  26 */   private static final SimpleCommandExceptionType NO_SUCH_ITEM = new SimpleCommandExceptionType((Message)class_2561.method_43470("Could not find an item with that name!"));
/*     */   
/*     */   public DropCommand() {
/*  29 */     super("drop", "Automatically drops specified items.", new String[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/*  35 */     builder.then(literal("hand").executes(context -> drop(())));
/*     */ 
/*     */     
/*  38 */     builder.then(literal("offhand").executes(context -> drop(())));
/*     */ 
/*     */     
/*  41 */     builder.then(literal("hotbar").executes(context -> drop(())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  48 */     builder.then(literal("inventory").executes(context -> drop(())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     builder.then(literal("all").executes(context -> drop(())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     builder.then(literal("armor").executes(context -> drop(())));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     builder.then(((RequiredArgumentBuilder)argument("item", (ArgumentType)class_2287.method_9776(REGISTRY_ACCESS))
/*  73 */         .executes(context -> drop(())))
/*     */ 
/*     */         
/*  76 */         .then(argument("amount", (ArgumentType)IntegerArgumentType.integer(1))
/*  77 */           .executes(context -> drop(()))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void dropItem(class_746 player, CommandContext<class_2172> context, int amount) throws CommandSyntaxException {
/*  85 */     class_1799 stack = class_2287.method_9777(context, "item").method_9781(1, false);
/*  86 */     if (stack == null || stack.method_7909() == class_1802.field_8162) throw NO_SUCH_ITEM.create();
/*     */     
/*  88 */     for (int i = 0; i < player.method_31548().method_5439() && amount > 0; i++) {
/*  89 */       class_1799 invStack = player.method_31548().method_5438(i);
/*  90 */       if (!invStack.method_7960() && stack.method_7909() == invStack.method_7909()) {
/*     */         
/*  92 */         int dropCount = Math.min(amount, invStack.method_7947());
/*     */         
/*  94 */         if (dropCount == invStack.method_7947()) {
/*  95 */           InvUtils.drop().slot(i);
/*     */         } else {
/*  97 */           for (int j = 0; j < dropCount; j++) {
/*  98 */             InvUtils.dropOne().slot(i);
/*     */           }
/*     */         } 
/*     */         
/* 102 */         amount -= dropCount;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private int drop(PlayerConsumer consumer) throws CommandSyntaxException {
/* 107 */     if (mc.field_1724.method_7325()) throw NOT_SPECTATOR.create(); 
/* 108 */     consumer.accept(mc.field_1724);
/* 109 */     return 1;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface PlayerConsumer {
/*     */     void accept(class_746 param1class_746) throws CommandSyntaxException;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\DropCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */