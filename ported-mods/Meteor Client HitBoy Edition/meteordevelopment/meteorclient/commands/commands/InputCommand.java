/*     */ package meteordevelopment.meteorclient.commands.commands;
/*     */ 
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.commands.Command;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.mixin.KeyBindingAccessor;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_1074;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_304;
/*     */ 
/*     */ public class InputCommand
/*     */   extends Command
/*     */ {
/*  24 */   private static final List<KeypressHandler> activeHandlers = new ArrayList<>();
/*     */   
/*  26 */   private static final List<Pair<class_304, String>> holdKeys = List.of(new Pair(mc.field_1690.field_1894, "forwards"), new Pair(mc.field_1690.field_1881, "backwards"), new Pair(mc.field_1690.field_1913, "left"), new Pair(mc.field_1690.field_1849, "right"), new Pair(mc.field_1690.field_1903, "jump"), new Pair(mc.field_1690.field_1832, "sneak"), new Pair(mc.field_1690.field_1867, "sprint"), new Pair(mc.field_1690.field_1904, "use"), new Pair(mc.field_1690.field_1886, "attack"));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static final List<Pair<class_304, String>> pressKeys = List.of(new Pair(mc.field_1690.field_1831, "swap"), new Pair(mc.field_1690.field_1869, "drop"));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputCommand() {
/*  44 */     super("input", "Keyboard input simulation.", new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/*  49 */     for (Pair<class_304, String> keyBinding : holdKeys) {
/*  50 */       builder.then(((LiteralArgumentBuilder)literal((String)keyBinding.getSecond())
/*  51 */           .executes(context -> {
/*     */               activeHandlers.add(new KeypressHandler((class_304)keyBinding.getFirst(), 1));
/*     */               
/*     */               return 1;
/*  55 */             })).then(argument("ticks", (ArgumentType)IntegerArgumentType.integer(1))
/*  56 */             .executes(context -> {
/*     */                 activeHandlers.add(new KeypressHandler((class_304)keyBinding.getFirst(), ((Integer)context.getArgument("ticks", Integer.class)).intValue()));
/*     */ 
/*     */                 
/*     */                 return 1;
/*     */               })));
/*     */     } 
/*     */     
/*  64 */     for (Pair<class_304, String> keyBinding : pressKeys) {
/*  65 */       builder.then(literal((String)keyBinding.getSecond())
/*  66 */           .executes(context -> {
/*     */               press((class_304)keyBinding.getFirst());
/*     */               
/*     */               return 1;
/*     */             }));
/*     */     } 
/*     */     
/*  73 */     for (class_304 keyBinding : mc.field_1690.field_1852) {
/*  74 */       builder.then(literal(keyBinding.method_1431().substring(4))
/*  75 */           .executes(context -> {
/*     */               press(keyBinding);
/*     */               
/*     */               return 1;
/*     */             }));
/*     */     } 
/*     */     
/*  82 */     builder.then(literal("clear").executes(ctx -> {
/*     */             if (activeHandlers.isEmpty()) {
/*     */               warning("No active keypress handlers.", new Object[0]);
/*     */             } else {
/*     */               info("Cleared all keypress handlers.", new Object[0]); Objects.requireNonNull(MeteorClient.EVENT_BUS);
/*     */               activeHandlers.forEach(MeteorClient.EVENT_BUS::unsubscribe);
/*     */               activeHandlers.clear();
/*     */             } 
/*     */             return 1;
/*     */           }));
/*  92 */     builder.then(literal("list").executes(ctx -> {
/*     */             if (activeHandlers.isEmpty()) {
/*     */               warning("No active keypress handlers.", new Object[0]);
/*     */             } else {
/*     */               info("Active keypress handlers: ", new Object[0]);
/*     */               for (int i = 0; i < activeHandlers.size(); i++) {
/*     */                 KeypressHandler handler = activeHandlers.get(i);
/*     */                 info("(highlight)%d(default) - (highlight)%s %d(default) ticks left out of (highlight)%d(default).", new Object[] { Integer.valueOf(i), class_1074.method_4662(handler.key.method_1431(), new Object[0]), Integer.valueOf(handler.ticks), Integer.valueOf(handler.totalTicks) });
/*     */               } 
/*     */             } 
/*     */             return 1;
/*     */           }));
/* 104 */     builder.then(literal("remove").then(argument("index", (ArgumentType)IntegerArgumentType.integer(0)).executes(ctx -> {
/*     */               int index = IntegerArgumentType.getInteger(ctx, "index");
/*     */               if (index >= activeHandlers.size()) {
/*     */                 warning("Index out of range.", new Object[0]);
/*     */               } else {
/*     */                 info("Removed keypress handler.", new Object[0]);
/*     */                 MeteorClient.EVENT_BUS.unsubscribe(activeHandlers.get(index));
/*     */                 activeHandlers.remove(index);
/*     */               } 
/*     */               return 1;
/*     */             })));
/*     */   }
/*     */   private static void press(class_304 keyBinding) {
/* 117 */     KeyBindingAccessor accessor = (KeyBindingAccessor)keyBinding;
/* 118 */     accessor.meteor$setTimesPressed(accessor.meteor$getTimesPressed() + 1);
/*     */   }
/*     */   
/*     */   private static class KeypressHandler {
/*     */     private final class_304 key;
/*     */     private final int totalTicks;
/*     */     private int ticks;
/*     */     
/*     */     public KeypressHandler(class_304 key, int ticks) {
/* 127 */       this.key = key;
/* 128 */       this.totalTicks = ticks;
/* 129 */       this.ticks = ticks;
/*     */       
/* 131 */       MeteorClient.EVENT_BUS.subscribe(this);
/*     */     }
/*     */     
/*     */     @EventHandler
/*     */     private void onTick(TickEvent.Post event) {
/* 136 */       if (this.ticks == this.totalTicks) InputCommand.press(this.key);
/*     */       
/* 138 */       if (this.ticks-- > 0) {
/* 139 */         this.key.method_23481(true);
/*     */       } else {
/* 141 */         this.key.method_23481(false);
/* 142 */         MeteorClient.EVENT_BUS.unsubscribe(this);
/* 143 */         InputCommand.activeHandlers.remove(this);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\InputCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */