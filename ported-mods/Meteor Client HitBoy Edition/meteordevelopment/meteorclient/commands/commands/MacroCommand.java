/*     */ package meteordevelopment.meteorclient.commands.commands;
/*     */ 
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import meteordevelopment.meteorclient.MeteorClient;
/*     */ import meteordevelopment.meteorclient.commands.Command;
/*     */ import meteordevelopment.meteorclient.commands.arguments.MacroArgumentType;
/*     */ import meteordevelopment.meteorclient.events.world.TickEvent;
/*     */ import meteordevelopment.meteorclient.systems.macros.Macro;
/*     */ import meteordevelopment.orbit.EventHandler;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_2245;
/*     */ 
/*     */ public class MacroCommand extends Command {
/*     */   List<ScheduledMacro> scheduleQueue;
/*     */   
/*     */   public MacroCommand() {
/*  24 */     super("macro", "Allows you to execute macros.", new String[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  29 */     this.scheduleQueue = new ArrayList<>();
/*  30 */     this.scheduledMacros = new ArrayList<>();
/*     */     MeteorClient.EVENT_BUS.subscribe(this);
/*     */   } List<ScheduledMacro> scheduledMacros;
/*     */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/*  34 */     ((LiteralArgumentBuilder)builder
/*  35 */       .then(((LiteralArgumentBuilder)literal("clear")
/*  36 */         .executes(context -> {
/*     */             if (this.scheduleQueue.isEmpty() && this.scheduledMacros.isEmpty()) {
/*     */               error("No macros are currently scheduled.", new Object[0]);
/*     */               
/*     */               return 1;
/*     */             } 
/*     */             
/*     */             clearAll();
/*     */             
/*     */             info("Cleared all scheduled macros.", new Object[0]);
/*     */             return 1;
/*  47 */           })).then(argument("macro", (ArgumentType)MacroArgumentType.create())
/*  48 */           .executes(context -> {
/*     */               Macro macro = MacroArgumentType.get(context);
/*     */               
/*     */               if (!isScheduled(macro)) {
/*     */                 error("This macro is not currently scheduled.", new Object[0]);
/*     */                 
/*     */                 return 1;
/*     */               } 
/*     */               
/*     */               clear(macro);
/*     */               
/*     */               info("Cleared scheduled macro.", new Object[0]);
/*     */               
/*     */               return 1;
/*  62 */             })))).then(((RequiredArgumentBuilder)argument("macro", (ArgumentType)MacroArgumentType.create())
/*  63 */         .executes(context -> {
/*     */             Macro macro = MacroArgumentType.get(context);
/*     */             
/*     */             this.scheduleQueue.add(new ScheduledMacro(0, macro));
/*     */             
/*     */             return 1;
/*  69 */           })).then(argument("delay", (ArgumentType)class_2245.method_9489())
/*  70 */           .executes(context -> {
/*     */               Macro macro = MacroArgumentType.get(context);
/*     */               this.scheduleQueue.add(new ScheduledMacro(IntegerArgumentType.getInteger(context, "delay"), macro));
/*     */               return 1;
/*     */             })));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAll() {
/*  82 */     this.scheduleQueue.clear();
/*  83 */     this.scheduledMacros.clear();
/*     */   }
/*     */   
/*     */   public boolean isScheduled(Macro macro) {
/*  87 */     return (this.scheduleQueue.stream().anyMatch(element -> (element.macro == macro)) || this.scheduledMacros
/*  88 */       .stream().anyMatch(element -> (element.macro == macro)));
/*     */   }
/*     */   
/*     */   public void clear(Macro macro) {
/*  92 */     this.scheduleQueue.removeIf(scheduledMacro -> (scheduledMacro.macro == macro));
/*  93 */     this.scheduledMacros.removeIf(scheduledMacro -> (scheduledMacro.macro == macro));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onTick(TickEvent.Post event) {
/*  98 */     if (!this.scheduleQueue.isEmpty()) {
/*  99 */       this.scheduledMacros.addAll(this.scheduleQueue);
/* 100 */       this.scheduleQueue.clear();
/*     */     } 
/*     */     
/* 103 */     if (!this.scheduledMacros.isEmpty()) {
/* 104 */       runMacros();
/*     */     }
/*     */     
/* 107 */     this.scheduledMacros.forEach(ScheduledMacro::tick);
/*     */   }
/*     */   
/*     */   private void runMacros() {
/* 111 */     this.scheduledMacros.removeIf(ScheduledMacro::run);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\MacroCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */