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
/*     */ import java.util.function.ToIntFunction;
/*     */ import meteordevelopment.meteorclient.commands.Command;
/*     */ import meteordevelopment.meteorclient.commands.arguments.RegistryEntryReferenceArgumentType;
/*     */ import meteordevelopment.meteorclient.utils.Utils;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_1887;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_2378;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_437;
/*     */ import net.minecraft.class_490;
/*     */ import net.minecraft.class_6880;
/*     */ import net.minecraft.class_7924;
/*     */ 
/*     */ public class EnchantCommand extends Command {
/*  27 */   private static final SimpleCommandExceptionType NOT_IN_CREATIVE = new SimpleCommandExceptionType((Message)class_2561.method_43470("You must be in creative mode to use this."));
/*  28 */   private static final SimpleCommandExceptionType NOT_HOLDING_ITEM = new SimpleCommandExceptionType((Message)class_2561.method_43470("You need to hold some item to enchant."));
/*     */   
/*     */   public EnchantCommand() {
/*  31 */     super("enchant", "Enchants the item in your hand. REQUIRES Creative mode.", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/*  36 */     builder.then(literal("one").then(((RequiredArgumentBuilder)argument("enchantment", (ArgumentType)RegistryEntryReferenceArgumentType.enchantment())
/*  37 */           .then(literal("level").then(argument("level", (ArgumentType)IntegerArgumentType.integer()).executes(context -> {
/*     */                   one(context, ());
/*     */                   
/*     */                   return 1;
/*  41 */                 })))).then(literal("max").executes(context -> {
/*     */                 one(context, class_1887::method_8183);
/*     */                 
/*     */                 return 1;
/*     */               }))));
/*     */     
/*  47 */     builder.then(((LiteralArgumentBuilder)literal("all_possible")
/*  48 */         .then(literal("level").then(argument("level", (ArgumentType)IntegerArgumentType.integer()).executes(context -> {
/*     */                 all(true, ());
/*     */                 
/*     */                 return 1;
/*  52 */               })))).then(literal("max").executes(context -> {
/*     */               all(true, class_1887::method_8183);
/*     */               
/*     */               return 1;
/*     */             })));
/*     */     
/*  58 */     builder.then(((LiteralArgumentBuilder)literal("all")
/*  59 */         .then(literal("level").then(argument("level", (ArgumentType)IntegerArgumentType.integer()).executes(context -> {
/*     */                 all(false, ());
/*     */                 
/*     */                 return 1;
/*  63 */               })))).then(literal("max").executes(context -> {
/*     */               all(false, class_1887::method_8183);
/*     */               
/*     */               return 1;
/*     */             })));
/*     */     
/*  69 */     builder.then(literal("clear").executes(context -> {
/*     */             class_1799 itemStack = tryGetItemStack();
/*     */             
/*     */             Utils.clearEnchantments(itemStack);
/*     */             
/*     */             syncItem();
/*     */             return 1;
/*     */           }));
/*  77 */     builder.then(literal("remove").then(argument("enchantment", (ArgumentType)RegistryEntryReferenceArgumentType.enchantment()).executes(context -> {
/*     */               class_1799 itemStack = tryGetItemStack();
/*     */               class_6880.class_6883<class_1887> enchantment = RegistryEntryReferenceArgumentType.getEnchantment(context, "enchantment");
/*     */               Utils.removeEnchantment(itemStack, (class_1887)enchantment.comp_349());
/*     */               syncItem();
/*     */               return 1;
/*     */             })));
/*     */   }
/*     */ 
/*     */   
/*     */   private void one(CommandContext<class_2172> context, ToIntFunction<class_1887> level) throws CommandSyntaxException {
/*  88 */     class_1799 itemStack = tryGetItemStack();
/*     */     
/*  90 */     class_6880.class_6883<class_1887> enchantment = RegistryEntryReferenceArgumentType.getEnchantment(context, "enchantment");
/*  91 */     Utils.addEnchantment(itemStack, (class_6880)enchantment, level.applyAsInt((class_1887)enchantment.comp_349()));
/*     */     
/*  93 */     syncItem();
/*     */   }
/*     */   
/*     */   private void all(boolean onlyPossible, ToIntFunction<class_1887> level) throws CommandSyntaxException {
/*  97 */     class_1799 itemStack = tryGetItemStack();
/*     */     
/*  99 */     mc.method_1562().method_29091().method_46759(class_7924.field_41265).ifPresent(registry -> registry.method_42017().forEach(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     syncItem();
/*     */   }
/*     */   
/*     */   private void syncItem() {
/* 111 */     mc.method_1507((class_437)new class_490((class_1657)mc.field_1724));
/* 112 */     mc.method_1507(null);
/*     */   }
/*     */   
/*     */   private class_1799 tryGetItemStack() throws CommandSyntaxException {
/* 116 */     if (!mc.field_1724.method_68878()) throw NOT_IN_CREATIVE.create();
/*     */     
/* 118 */     class_1799 itemStack = getItemStack();
/* 119 */     if (itemStack == null) throw NOT_HOLDING_ITEM.create();
/*     */     
/* 121 */     return itemStack;
/*     */   }
/*     */   
/*     */   private class_1799 getItemStack() {
/* 125 */     class_1799 itemStack = mc.field_1724.method_6047();
/* 126 */     if (itemStack == null) itemStack = mc.field_1724.method_6079(); 
/* 127 */     return itemStack.method_7960() ? null : itemStack;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\EnchantCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */