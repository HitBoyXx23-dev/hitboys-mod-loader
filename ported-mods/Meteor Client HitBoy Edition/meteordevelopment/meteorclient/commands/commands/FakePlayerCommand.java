/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.commands.arguments.FakePlayerArgumentType;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.player.FakePlayer;
/*    */ import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerEntity;
/*    */ import meteordevelopment.meteorclient.utils.entity.fakeplayer.FakePlayerManager;
/*    */ import meteordevelopment.meteorclient.utils.player.ChatUtils;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ public class FakePlayerCommand
/*    */   extends Command
/*    */ {
/*    */   public FakePlayerCommand() {
/* 21 */     super("fake-player", "Manages fake players that you can use for testing.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 26 */     builder.then(((LiteralArgumentBuilder)literal("add")
/* 27 */         .executes(context -> {
/*    */             FakePlayer fakePlayer = (FakePlayer)Modules.get().get(FakePlayer.class);
/*    */             
/*    */             FakePlayerManager.add((String)fakePlayer.name.get(), ((Integer)fakePlayer.health.get()).intValue(), ((Boolean)fakePlayer.copyInv.get()).booleanValue());
/*    */             return 1;
/* 32 */           })).then(argument("name", (ArgumentType)StringArgumentType.word())
/* 33 */           .executes(context -> {
/*    */               FakePlayer fakePlayer = (FakePlayer)Modules.get().get(FakePlayer.class);
/*    */               
/*    */               FakePlayerManager.add(StringArgumentType.getString(context, "name"), ((Integer)fakePlayer.health.get()).intValue(), ((Boolean)fakePlayer.copyInv.get()).booleanValue());
/*    */               
/*    */               return 1;
/*    */             })));
/*    */     
/* 41 */     builder.then(literal("remove")
/* 42 */         .then(argument("fp", (ArgumentType)FakePlayerArgumentType.create())
/* 43 */           .executes(context -> {
/*    */               FakePlayerEntity fp = FakePlayerArgumentType.get(context);
/*    */               
/*    */               if (fp == null || !FakePlayerManager.contains(fp)) {
/*    */                 error("Couldn't find a Fake Player with that name.", new Object[0]);
/*    */                 
/*    */                 return 1;
/*    */               } 
/*    */               
/*    */               FakePlayerManager.remove(fp);
/*    */               
/*    */               info("Removed Fake Player %s.".formatted(new Object[] { fp.method_5477().getString() }, ), new Object[0]);
/*    */               
/*    */               return 1;
/*    */             })));
/* 58 */     builder.then(literal("clear")
/* 59 */         .executes(context -> {
/*    */             FakePlayerManager.clear();
/*    */             
/*    */             return 1;
/*    */           }));
/*    */     
/* 65 */     builder.then(literal("list")
/* 66 */         .executes(context -> {
/*    */             info("--- Fake Players ((highlight)%s(default)) ---", new Object[] { Integer.valueOf(FakePlayerManager.count()) });
/*    */             FakePlayerManager.forEach(());
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\FakePlayerCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */