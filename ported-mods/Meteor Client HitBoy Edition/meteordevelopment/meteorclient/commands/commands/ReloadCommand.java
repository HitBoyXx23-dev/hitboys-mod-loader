/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.renderer.Fonts;
/*    */ import meteordevelopment.meteorclient.systems.Systems;
/*    */ import meteordevelopment.meteorclient.systems.friends.Friend;
/*    */ import meteordevelopment.meteorclient.systems.friends.Friends;
/*    */ import meteordevelopment.meteorclient.utils.network.Capes;
/*    */ import meteordevelopment.meteorclient.utils.network.MeteorExecutor;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ 
/*    */ public class ReloadCommand
/*    */   extends Command
/*    */ {
/*    */   public ReloadCommand() {
/* 20 */     super("reload", "Reloads many systems.", new String[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 25 */     builder.executes(context -> {
/*    */           warning("Reloading systems, this may take a while.", new Object[0]);
/*    */           Systems.load();
/*    */           Capes.init();
/*    */           Fonts.refresh();
/*    */           MeteorExecutor.execute(());
/*    */           return 1;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\ReloadCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */