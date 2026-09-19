/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import net.minecraft.class_1934;
/*    */ import net.minecraft.class_2172;
/*    */ 
/*    */ 
/*    */ public class GamemodeCommand
/*    */   extends Command
/*    */ {
/*    */   public GamemodeCommand() {
/* 15 */     super("gamemode", "Changes your gamemode client-side.", new String[] { "gm" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 20 */     for (class_1934 gameMode : class_1934.values()) {
/* 21 */       builder.then(literal(gameMode.method_8381()).executes(context -> {
/*    */               mc.field_1761.method_2907(gameMode);
/*    */               return 1;
/*    */             }));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\GamemodeCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */