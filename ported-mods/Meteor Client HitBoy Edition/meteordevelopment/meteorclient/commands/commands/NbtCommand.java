/*     */ package meteordevelopment.meteorclient.commands.commands;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import meteordevelopment.meteorclient.commands.arguments.ComponentMapArgumentType;
/*     */ import meteordevelopment.meteorclient.utils.misc.text.MeteorClickEvent;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1799;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_2203;
/*     */ import net.minecraft.class_2512;
/*     */ import net.minecraft.class_2520;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2568;
/*     */ import net.minecraft.class_3169;
/*     */ import net.minecraft.class_3902;
/*     */ import net.minecraft.class_5250;
/*     */ import net.minecraft.class_5321;
/*     */ import net.minecraft.class_6880;
/*     */ import net.minecraft.class_7923;
/*     */ import net.minecraft.class_9323;
/*     */ import net.minecraft.class_9326;
/*     */ import net.minecraft.class_9331;
/*     */ import net.minecraft.class_9335;
/*     */ import net.minecraft.class_9336;
/*     */ 
/*     */ public class NbtCommand extends Command {
/*     */   static {
/*  41 */     MALFORMED_ITEM_EXCEPTION = new DynamicCommandExceptionType(error -> class_2561.method_54159("arguments.item.malformed", new Object[] { error }));
/*     */   }
/*     */   private static final DynamicCommandExceptionType MALFORMED_ITEM_EXCEPTION;
/*  44 */   private final class_2561 copyButton = (class_2561)class_2561.method_43470("NBT").method_10862(class_2583.field_24360
/*  45 */       .method_27706(class_124.field_1073)
/*  46 */       .method_10958((class_2558)new MeteorClickEvent(
/*  47 */           toString(new String[] { "copy"
/*     */             
/*  49 */             }))).method_10949((class_2568)new class_2568.class_10613(
/*  50 */           (class_2561)class_2561.method_43470("Copy the NBT data to your clipboard."))));
/*     */ 
/*     */   
/*     */   public NbtCommand() {
/*  54 */     super("nbt", "Modifies NBT data for an item, example: .nbt add {display:{Name:'{\"text\":\"$cRed Name\"}'}}", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/*  59 */     builder.then(literal("add").then(argument("component", (ArgumentType)ComponentMapArgumentType.componentMap(REGISTRY_ACCESS)).executes(ctx -> {
/*     */               class_1799 stack = mc.field_1724.method_31548().method_7391();
/*     */               
/*     */               if (validBasic(stack)) {
/*     */                 class_9323 itemComponents = stack.method_57353();
/*     */                 
/*     */                 class_9323 newComponents = ComponentMapArgumentType.getComponentMap(ctx, "component");
/*     */                 
/*     */                 class_9323 testComponents = class_9323.method_59771(itemComponents, newComponents);
/*     */                 
/*     */                 DataResult<class_3902> dataResult = class_1799.method_59691(testComponents);
/*     */                 
/*     */                 Objects.requireNonNull(MALFORMED_ITEM_EXCEPTION);
/*     */                 dataResult.getOrThrow(MALFORMED_ITEM_EXCEPTION::create);
/*     */                 stack.method_57365(testComponents);
/*     */                 setStack(stack);
/*     */               } 
/*     */               return 1;
/*     */             })));
/*  78 */     builder.then(literal("set").then(argument("component", (ArgumentType)ComponentMapArgumentType.componentMap(REGISTRY_ACCESS)).executes(ctx -> {
/*     */               class_1799 stack = mc.field_1724.method_31548().method_7391();
/*     */               
/*     */               if (validBasic(stack)) {
/*     */                 class_9323 components = ComponentMapArgumentType.getComponentMap(ctx, "component");
/*     */                 
/*     */                 class_9335 stackComponents = (class_9335)stack.method_57353();
/*     */                 
/*     */                 DataResult<class_3902> dataResult = class_1799.method_59691(components);
/*     */                 
/*     */                 Objects.requireNonNull(MALFORMED_ITEM_EXCEPTION);
/*     */                 
/*     */                 dataResult.getOrThrow(MALFORMED_ITEM_EXCEPTION::create);
/*     */                 
/*     */                 class_9326.class_9327 changesBuilder = class_9326.method_57841();
/*     */                 
/*     */                 Set<class_9331<?>> types = stackComponents.method_57831();
/*     */                 
/*     */                 for (class_9336<?> entry : (Iterable<class_9336<?>>)components) {
/*     */                   changesBuilder.method_57855(entry);
/*     */                   
/*     */                   types.remove(entry.comp_2443());
/*     */                 } 
/*     */                 
/*     */                 for (class_9331<?> type : types) {
/*     */                   changesBuilder.method_57853(type);
/*     */                 }
/*     */                 stackComponents.method_57936(changesBuilder.method_57852());
/*     */                 setStack(stack);
/*     */               } 
/*     */               return 1;
/*     */             })));
/* 110 */     builder.then(literal("remove").then((ArgumentBuilder)((RequiredArgumentBuilder)argument("component", (ArgumentType)class_7079.method_41224(class_7924.field_49659)).executes(ctx -> {
/*     */               class_1799 stack = mc.field_1724.method_31548().method_7391();
/*     */               
/*     */               if (validBasic(stack)) {
/*     */                 class_5321<class_9331<?>> componentTypeKey = (class_5321<class_9331<?>>)ctx.getArgument("component", class_5321.class);
/*     */                 
/*     */                 class_9331<?> componentType = (class_9331)class_7923.field_49658.method_29107(componentTypeKey);
/*     */                 
/*     */                 class_9335 components = (class_9335)stack.method_57353();
/*     */                 
/*     */                 components.method_57936(class_9326.method_57841().method_57853(componentType).method_57852());
/*     */                 
/*     */                 setStack(stack);
/*     */               } 
/*     */               
/*     */               return 1;
/* 126 */             })).suggests((ctx, suggestionsBuilder) -> {
/*     */               class_1799 stack = mc.field_1724.method_31548().method_7391();
/*     */ 
/*     */               
/*     */               if (stack != class_1799.field_8037) {
/*     */                 class_9323 components = stack.method_57353();
/*     */ 
/*     */                 
/*     */                 String remaining = suggestionsBuilder.getRemaining().toLowerCase(Locale.ROOT);
/*     */ 
/*     */                 
/*     */                 Objects.requireNonNull(class_7923.field_49658);
/*     */ 
/*     */                 
/*     */                 class_2172.method_9268(components.method_57831().stream().map(class_7923.field_49658::method_47983).toList(), remaining, (), ());
/*     */               } 
/*     */ 
/*     */               
/*     */               return suggestionsBuilder.buildFuture();
/*     */             })));
/*     */ 
/*     */     
/* 148 */     builder.then(literal("get").executes(context -> {
/*     */             class_3169 class_3169 = new class_3169((class_1297)mc.field_1724);
/*     */             
/*     */             class_2203.class_2209 handPath = class_2203.class_2209.method_58472("SelectedItem");
/*     */             
/*     */             class_5250 text = class_2561.method_43473().method_10852(this.copyButton);
/*     */             try {
/*     */               List<class_2520> nbtElement = handPath.method_9366((class_2520)class_3169.method_13881());
/*     */               if (!nbtElement.isEmpty()) {
/*     */                 text.method_27693(" ").method_10852(class_2512.method_32270(nbtElement.getFirst()));
/*     */               }
/* 159 */             } catch (CommandSyntaxException e) {
/*     */               text.method_27693("{}");
/*     */             } 
/*     */             
/*     */             info((class_2561)text);
/*     */             
/*     */             return 1;
/*     */           }));
/*     */     
/* 168 */     builder.then(literal("copy").executes(context -> {
/*     */             class_3169 class_3169 = new class_3169((class_1297)mc.field_1724);
/*     */             
/*     */             class_2203.class_2209 handPath = class_2203.class_2209.method_58472("SelectedItem");
/*     */             
/*     */             class_5250 text = class_2561.method_43473().method_10852(this.copyButton);
/*     */             String nbt = "{}";
/*     */             try {
/*     */               List<class_2520> nbtElement = handPath.method_9366((class_2520)class_3169.method_13881());
/*     */               if (!nbtElement.isEmpty()) {
/*     */                 text.method_27693(" ").method_10852(class_2512.method_32270(nbtElement.getFirst()));
/*     */                 nbt = ((class_2520)nbtElement.getFirst()).toString();
/*     */               } 
/* 181 */             } catch (CommandSyntaxException e) {
/*     */               text.method_27693("{}");
/*     */             } 
/*     */             
/*     */             mc.field_1774.method_1455(nbt);
/*     */             
/*     */             text.method_27693(" data copied!");
/*     */             
/*     */             info((class_2561)text);
/*     */             
/*     */             return 1;
/*     */           }));
/* 193 */     builder.then(literal("count").then(argument("count", (ArgumentType)IntegerArgumentType.integer(-127, 127)).executes(context -> {
/*     */               class_1799 stack = mc.field_1724.method_31548().method_7391();
/*     */               if (validBasic(stack)) {
/*     */                 int count = IntegerArgumentType.getInteger(context, "count");
/*     */                 stack.method_7939(count);
/*     */                 setStack(stack);
/*     */                 info("Set mainhand stack count to %s.", new Object[] { Integer.valueOf(count) });
/*     */               } 
/*     */               return 1;
/*     */             })));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setStack(class_1799 stack) {
/* 208 */     mc.field_1724.field_3944.method_52787((class_2596)new class_2873(36 + mc.field_1724.method_31548().method_67532(), stack));
/*     */   }
/*     */   
/*     */   private boolean validBasic(class_1799 stack) {
/* 212 */     if (!(mc.field_1724.method_31549()).field_7477) {
/* 213 */       error("Creative mode only.", new Object[0]);
/* 214 */       return false;
/*     */     } 
/*     */     
/* 217 */     if (stack == class_1799.field_8037) {
/* 218 */       error("You must hold an item in your main hand.", new Object[0]);
/* 219 */       return false;
/*     */     } 
/* 221 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\NbtCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */