/*     */ package meteordevelopment.meteorclient.commands.commands;
/*     */ 
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIntPair;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import meteordevelopment.meteorclient.commands.Command;
/*     */ import meteordevelopment.meteorclient.commands.arguments.ModuleArgumentType;
/*     */ import meteordevelopment.meteorclient.commands.arguments.PlayerArgumentType;
/*     */ import meteordevelopment.meteorclient.pathing.PathManagers;
/*     */ import meteordevelopment.meteorclient.systems.modules.Module;
/*     */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*     */ import meteordevelopment.meteorclient.systems.modules.misc.swarm.Swarm;
/*     */ import meteordevelopment.meteorclient.systems.modules.misc.swarm.SwarmConnection;
/*     */ import meteordevelopment.meteorclient.systems.modules.misc.swarm.SwarmWorker;
/*     */ import meteordevelopment.meteorclient.systems.modules.world.InfinityMiner;
/*     */ import meteordevelopment.meteorclient.utils.misc.text.MeteorClickEvent;
/*     */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*     */ import net.minecraft.class_124;
/*     */ import net.minecraft.class_1297;
/*     */ import net.minecraft.class_1657;
/*     */ import net.minecraft.class_2172;
/*     */ import net.minecraft.class_2247;
/*     */ import net.minecraft.class_2257;
/*     */ import net.minecraft.class_2338;
/*     */ import net.minecraft.class_2558;
/*     */ import net.minecraft.class_2561;
/*     */ import net.minecraft.class_2583;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public class SwarmCommand extends Command {
/*  42 */   private static final SimpleCommandExceptionType SWARM_NOT_ACTIVE = new SimpleCommandExceptionType((Message)class_2561.method_43470("The swarm module must be active to use this command."));
/*     */ 
/*     */   
/*     */   public SwarmCommand() {
/*  46 */     super("swarm", "Sends commands to connected swarm workers.", new String[0]);
/*     */   }
/*     */   @Nullable
/*     */   private ObjectIntPair<String> pendingConnection;
/*     */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/*  51 */     builder.then(literal("disconnect").executes(context -> {
/*     */             Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */             
/*     */             if (swarm.isActive()) {
/*     */               swarm.close();
/*     */             } else {
/*     */               throw SWARM_NOT_ACTIVE.create();
/*     */             } 
/*     */             
/*     */             return 1;
/*     */           }));
/*     */     
/*  63 */     builder.then(((LiteralArgumentBuilder)literal("join")
/*  64 */         .then(argument("ip", (ArgumentType)StringArgumentType.string())
/*  65 */           .then(argument("port", (ArgumentType)IntegerArgumentType.integer(0, 65535))
/*  66 */             .executes(context -> {
/*     */                 String ip = StringArgumentType.getString(context, "ip");
/*     */ 
/*     */                 
/*     */                 int port = IntegerArgumentType.getInteger(context, "port");
/*     */ 
/*     */                 
/*     */                 this.pendingConnection = (ObjectIntPair<String>)new ObjectIntImmutablePair(ip, port);
/*     */ 
/*     */                 
/*     */                 info("Are you sure you want to connect to '%s:%s'?", new Object[] { ip, Integer.valueOf(port) });
/*     */ 
/*     */                 
/*     */                 info((class_2561)class_2561.method_43470("Click here to confirm").method_10862(class_2583.field_24360.method_27705(new class_124[] { class_124.field_1073, class_124.field_1060 }).method_10958((class_2558)new MeteorClickEvent(".swarm join confirm"))));
/*     */                 
/*     */                 return 1;
/*  82 */               })))).then(literal("confirm").executes(ctx -> {
/*     */               if (this.pendingConnection == null) {
/*     */                 error("No pending swarm connections.", new Object[0]);
/*     */                 
/*     */                 return 1;
/*     */               } 
/*     */               
/*     */               Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */               
/*     */               swarm.enable();
/*     */               
/*     */               swarm.close();
/*     */               swarm.mode.set(Swarm.Mode.Worker);
/*     */               swarm.worker = new SwarmWorker((String)this.pendingConnection.left(), this.pendingConnection.rightInt());
/*     */               this.pendingConnection = null;
/*     */               try {
/*     */                 info("Connected to (highlight)%s.", new Object[] { swarm.worker.getConnection() });
/*  99 */               } catch (NullPointerException e) {
/*     */                 error("Error connecting to swarm host.", new Object[0]);
/*     */                 
/*     */                 swarm.close();
/*     */                 
/*     */                 swarm.toggle();
/*     */               } 
/*     */               
/*     */               return 1;
/*     */             })));
/* 109 */     builder.then(literal("connections").executes(context -> {
/*     */             Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */             
/*     */             if (swarm.isActive()) {
/*     */               if (swarm.isHost()) {
/*     */                 if (swarm.host.getConnectionCount() > 0) {
/*     */                   ChatUtils.info("--- Swarm Connections (highlight)(%s/%s)(default) ---", new Object[] { Integer.valueOf(swarm.host.getConnectionCount()), Integer.valueOf((swarm.host.getConnections()).length) });
/*     */                   
/*     */                   for (int i = 0; i < (swarm.host.getConnections()).length; i++) {
/*     */                     SwarmConnection connection = swarm.host.getConnections()[i];
/*     */                     
/*     */                     if (connection != null) {
/*     */                       ChatUtils.info("(highlight)Worker %s(default): %s.", new Object[] { Integer.valueOf(i), connection.getConnection() });
/*     */                     }
/*     */                   } 
/*     */                 } else {
/*     */                   warning("No active connections", new Object[0]);
/*     */                 } 
/*     */               } else if (swarm.isWorker()) {
/*     */                 info("Connected to (highlight)%s", new Object[] { swarm.worker.getConnection() });
/*     */               } 
/*     */             } else {
/*     */               throw SWARM_NOT_ACTIVE.create();
/*     */             } 
/*     */             
/*     */             return 1;
/*     */           }));
/* 136 */     builder.then(((LiteralArgumentBuilder)literal("follow").executes(context -> {
/*     */             Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */ 
/*     */             
/*     */             if (swarm.isActive()) {
/*     */               if (swarm.isHost()) {
/*     */                 swarm.host.sendMessage(context.getInput() + " " + context.getInput());
/*     */               } else if (swarm.isWorker()) {
/*     */                 error("The follow host command must be used by the host.", new Object[0]);
/*     */               } 
/*     */             } else {
/*     */               throw SWARM_NOT_ACTIVE.create();
/*     */             } 
/*     */             
/*     */             return 1;
/* 151 */           })).then(argument("player", (ArgumentType)PlayerArgumentType.create()).executes(context -> {
/*     */               class_1657 playerEntity = PlayerArgumentType.get(context);
/*     */ 
/*     */               
/*     */               Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */               
/*     */               if (swarm.isActive()) {
/*     */                 if (swarm.isHost()) {
/*     */                   swarm.host.sendMessage(context.getInput());
/*     */                 } else if (swarm.isWorker() && playerEntity != null) {
/*     */                   PathManagers.get().follow(());
/*     */                 } 
/*     */               } else {
/*     */                 throw SWARM_NOT_ACTIVE.create();
/*     */               } 
/*     */               
/*     */               return 1;
/*     */             })));
/*     */     
/* 170 */     builder.then(literal("goto")
/* 171 */         .then(argument("x", (ArgumentType)IntegerArgumentType.integer())
/* 172 */           .then(argument("z", (ArgumentType)IntegerArgumentType.integer()).executes(context -> {
/*     */                 Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */ 
/*     */                 
/*     */                 if (swarm.isActive()) {
/*     */                   if (swarm.isHost()) {
/*     */                     swarm.host.sendMessage(context.getInput());
/*     */                   } else if (swarm.isWorker()) {
/*     */                     int x = IntegerArgumentType.getInteger(context, "x");
/*     */                     
/*     */                     int z = IntegerArgumentType.getInteger(context, "z");
/*     */                     
/*     */                     PathManagers.get().moveTo(new class_2338(x, 0, z), true);
/*     */                   } 
/*     */                 } else {
/*     */                   throw SWARM_NOT_ACTIVE.create();
/*     */                 } 
/*     */                 
/*     */                 return 1;
/*     */               }))));
/*     */     
/* 193 */     builder.then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)literal("infinity-miner").executes(context -> {
/*     */             Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */ 
/*     */             
/*     */             if (swarm.isActive()) {
/*     */               if (swarm.isHost()) {
/*     */                 swarm.host.sendMessage(context.getInput());
/*     */               } else if (swarm.isWorker()) {
/*     */                 runInfinityMiner();
/*     */               } 
/*     */             } else {
/*     */               throw SWARM_NOT_ACTIVE.create();
/*     */             } 
/*     */             
/*     */             return 1;
/* 208 */           })).then(((RequiredArgumentBuilder)argument("target", (ArgumentType)class_2257.method_9653(REGISTRY_ACCESS)).executes(context -> {
/*     */               Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */               
/*     */               if (swarm.isActive()) {
/*     */                 if (swarm.isHost()) {
/*     */                   swarm.host.sendMessage(context.getInput());
/*     */                 } else if (swarm.isWorker()) {
/*     */                   ((InfinityMiner)Modules.get().get(InfinityMiner.class)).targetBlocks.set(List.of(((class_2247)context.getArgument("target", class_2247.class)).method_9494().method_26204()));
/*     */                   
/*     */                   runInfinityMiner();
/*     */                 } 
/*     */               } else {
/*     */                 throw SWARM_NOT_ACTIVE.create();
/*     */               } 
/*     */               
/*     */               return 1;
/* 224 */             })).then(argument("repair", (ArgumentType)class_2257.method_9653(REGISTRY_ACCESS)).executes(context -> {
/*     */                 Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */                 
/*     */                 if (swarm.isActive()) {
/*     */                   if (swarm.isHost()) {
/*     */                     swarm.host.sendMessage(context.getInput());
/*     */                   } else if (swarm.isWorker()) {
/*     */                     ((InfinityMiner)Modules.get().get(InfinityMiner.class)).targetBlocks.set(List.of(((class_2247)context.getArgument("target", class_2247.class)).method_9494().method_26204()));
/*     */                     
/*     */                     ((InfinityMiner)Modules.get().get(InfinityMiner.class)).repairBlocks.set(List.of(((class_2247)context.getArgument("repair", class_2247.class)).method_9494().method_26204()));
/*     */                     
/*     */                     runInfinityMiner();
/*     */                   } 
/*     */                 } else {
/*     */                   throw SWARM_NOT_ACTIVE.create();
/*     */                 } 
/*     */                 return 1;
/* 241 */               })))).then(literal("logout").then(argument("logout", (ArgumentType)BoolArgumentType.bool()).executes(context -> {
/*     */                 Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */ 
/*     */                 
/*     */                 if (swarm.isActive()) {
/*     */                   if (swarm.isHost()) {
/*     */                     swarm.host.sendMessage(context.getInput());
/*     */                   } else if (swarm.isWorker()) {
/*     */                     ((InfinityMiner)Modules.get().get(InfinityMiner.class)).logOut.set(Boolean.valueOf(BoolArgumentType.getBool(context, "logout")));
/*     */                   } 
/*     */                 } else {
/*     */                   throw SWARM_NOT_ACTIVE.create();
/*     */                 } 
/*     */                 
/*     */                 return 1;
/* 256 */               })))).then(literal("walkhome").then(argument("walkhome", (ArgumentType)BoolArgumentType.bool()).executes(context -> {
/*     */                 Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */                 
/*     */                 if (swarm.isActive()) {
/*     */                   if (swarm.isHost()) {
/*     */                     swarm.host.sendMessage(context.getInput());
/*     */                   } else if (swarm.isWorker()) {
/*     */                     ((InfinityMiner)Modules.get().get(InfinityMiner.class)).walkHome.set(Boolean.valueOf(BoolArgumentType.getBool(context, "walkhome")));
/*     */                   } 
/*     */                 } else {
/*     */                   throw SWARM_NOT_ACTIVE.create();
/*     */                 } 
/*     */                 return 1;
/*     */               }))));
/* 270 */     builder.then(literal("mine")
/* 271 */         .then(argument("block", (ArgumentType)class_2257.method_9653(REGISTRY_ACCESS)).executes(context -> {
/*     */               Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */               
/*     */               if (swarm.isActive()) {
/*     */                 if (swarm.isHost()) {
/*     */                   swarm.host.sendMessage(context.getInput());
/*     */                 } else if (swarm.isWorker()) {
/*     */                   swarm.worker.target = ((class_2247)context.getArgument("block", class_2247.class)).method_9494().method_26204();
/*     */                 } 
/*     */               } else {
/*     */                 throw SWARM_NOT_ACTIVE.create();
/*     */               } 
/*     */               
/*     */               return 1;
/*     */             })));
/* 286 */     builder.then(literal("toggle")
/* 287 */         .then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)argument("module", (ArgumentType)ModuleArgumentType.create())
/* 288 */           .executes(context -> {
/*     */               Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */               if (swarm.isActive()) {
/*     */                 if (swarm.isHost()) {
/*     */                   swarm.host.sendMessage(context.getInput());
/*     */                 } else if (swarm.isWorker()) {
/*     */                   Module module = ModuleArgumentType.get(context);
/*     */                   module.toggle();
/*     */                 } 
/*     */               } else {
/*     */                 throw SWARM_NOT_ACTIVE.create();
/*     */               } 
/*     */               return 1;
/* 301 */             })).then(literal("on")
/* 302 */             .executes(context -> {
/*     */                 Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */                 if (swarm.isActive()) {
/*     */                   if (swarm.isHost()) {
/*     */                     swarm.host.sendMessage(context.getInput());
/*     */                   } else if (swarm.isWorker()) {
/*     */                     Module m = ModuleArgumentType.get(context);
/*     */                     m.enable();
/*     */                   } 
/*     */                 } else {
/*     */                   throw SWARM_NOT_ACTIVE.create();
/*     */                 } 
/*     */                 return 1;
/* 315 */               }))).then(literal("off")
/* 316 */             .executes(context -> {
/*     */                 Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */                 
/*     */                 if (swarm.isActive()) {
/*     */                   if (swarm.isHost()) {
/*     */                     swarm.host.sendMessage(context.getInput());
/*     */                   } else if (swarm.isWorker()) {
/*     */                     Module m = ModuleArgumentType.get(context);
/*     */                     
/*     */                     m.disable();
/*     */                   } 
/*     */                 } else {
/*     */                   throw SWARM_NOT_ACTIVE.create();
/*     */                 } 
/*     */                 
/*     */                 return 1;
/*     */               }))));
/*     */     
/* 334 */     builder.then(((LiteralArgumentBuilder)literal("scatter").executes(context -> {
/*     */             Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */             if (swarm.isActive()) {
/*     */               if (swarm.isHost()) {
/*     */                 swarm.host.sendMessage(context.getInput());
/*     */               } else if (swarm.isWorker()) {
/*     */                 scatter(100);
/*     */               } 
/*     */             } else {
/*     */               throw SWARM_NOT_ACTIVE.create();
/*     */             } 
/*     */             return 1;
/* 346 */           })).then(argument("radius", (ArgumentType)IntegerArgumentType.integer()).executes(context -> {
/*     */               Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */               
/*     */               if (swarm.isActive()) {
/*     */                 if (swarm.isHost()) {
/*     */                   swarm.host.sendMessage(context.getInput());
/*     */                 } else if (swarm.isWorker()) {
/*     */                   scatter(IntegerArgumentType.getInteger(context, "radius"));
/*     */                 } 
/*     */               } else {
/*     */                 throw SWARM_NOT_ACTIVE.create();
/*     */               } 
/*     */               return 1;
/*     */             })));
/* 360 */     builder.then(literal("stop").executes(context -> {
/*     */             Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */             
/*     */             if (swarm.isActive()) {
/*     */               if (swarm.isHost()) {
/*     */                 swarm.host.sendMessage(context.getInput());
/*     */               } else if (swarm.isWorker()) {
/*     */                 PathManagers.get().stop();
/*     */               } 
/*     */             } else {
/*     */               throw SWARM_NOT_ACTIVE.create();
/*     */             } 
/*     */             return 1;
/*     */           }));
/* 374 */     builder.then(literal("exec").then(argument("command", (ArgumentType)StringArgumentType.greedyString()).executes(context -> {
/*     */               Swarm swarm = (Swarm)Modules.get().get(Swarm.class);
/*     */               if (swarm.isActive()) {
/*     */                 if (swarm.isHost()) {
/*     */                   swarm.host.sendMessage(context.getInput());
/*     */                 } else if (swarm.isWorker()) {
/*     */                   ChatUtils.sendPlayerMsg(StringArgumentType.getString(context, "command"));
/*     */                 } 
/*     */               } else {
/*     */                 throw SWARM_NOT_ACTIVE.create();
/*     */               } 
/*     */               return 1;
/*     */             })));
/*     */   }
/*     */   
/*     */   private void runInfinityMiner() {
/* 390 */     InfinityMiner infinityMiner = (InfinityMiner)Modules.get().get(InfinityMiner.class);
/* 391 */     infinityMiner.disable();
/*     */     
/* 393 */     infinityMiner.enable();
/*     */   }
/*     */   
/*     */   private void scatter(int radius) {
/* 397 */     Random random = new Random();
/*     */     
/* 399 */     double a = random.nextDouble() * 2.0D * Math.PI;
/* 400 */     double r = radius * Math.sqrt(random.nextDouble());
/* 401 */     double x = mc.field_1724.method_23317() + r * Math.cos(a);
/* 402 */     double z = mc.field_1724.method_23321() + r * Math.sin(a);
/*     */     
/* 404 */     PathManagers.get().stop();
/* 405 */     PathManagers.get().moveTo(new class_2338((int)x, 0, (int)z), true);
/*     */   }
/*     */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\SwarmCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */