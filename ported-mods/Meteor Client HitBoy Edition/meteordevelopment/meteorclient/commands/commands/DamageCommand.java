/*    */ package meteordevelopment.meteorclient.commands.commands;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import meteordevelopment.meteorclient.commands.Command;
/*    */ import meteordevelopment.meteorclient.systems.modules.Modules;
/*    */ import meteordevelopment.meteorclient.systems.modules.movement.NoFall;
/*    */ import meteordevelopment.meteorclient.systems.modules.player.AntiHunger;
/*    */ import net.minecraft.class_2172;
/*    */ import net.minecraft.class_243;
/*    */ import net.minecraft.class_2561;
/*    */ import net.minecraft.class_2596;
/*    */ import net.minecraft.class_2828;
/*    */ 
/*    */ public class DamageCommand extends Command {
/* 21 */   private static final SimpleCommandExceptionType INVULNERABLE = new SimpleCommandExceptionType((Message)class_2561.method_43470("You are invulnerable."));
/*    */   
/*    */   public DamageCommand() {
/* 24 */     super("damage", "Damages self", new String[] { "dmg" });
/*    */   }
/*    */ 
/*    */   
/*    */   public void build(LiteralArgumentBuilder<class_2172> builder) {
/* 29 */     builder.then(argument("damage", (ArgumentType)IntegerArgumentType.integer(1, 7)).executes(context -> {
/*    */             int amount = IntegerArgumentType.getInteger(context, "damage");
/*    */             if ((mc.field_1724.method_31549()).field_7480) {
/*    */               throw INVULNERABLE.create();
/*    */             }
/*    */             damagePlayer(amount);
/*    */             return 1;
/*    */           }));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void damagePlayer(int amount) {
/* 43 */     boolean noFall = Modules.get().isActive(NoFall.class);
/* 44 */     if (noFall) ((NoFall)Modules.get().get(NoFall.class)).toggle();
/*    */     
/* 46 */     boolean antiHunger = Modules.get().isActive(AntiHunger.class);
/* 47 */     if (antiHunger) ((AntiHunger)Modules.get().get(AntiHunger.class)).toggle();
/*    */     
/* 49 */     class_243 pos = mc.field_1724.method_73189();
/*    */     
/* 51 */     for (int i = 0; i < 80; i++) {
/* 52 */       sendPositionPacket(pos.field_1352, pos.field_1351 + amount + 2.1D, pos.field_1350, false);
/* 53 */       sendPositionPacket(pos.field_1352, pos.field_1351 + 0.05D, pos.field_1350, false);
/*    */     } 
/*    */     
/* 56 */     sendPositionPacket(pos.field_1352, pos.field_1351, pos.field_1350, true);
/*    */     
/* 58 */     if (noFall) ((NoFall)Modules.get().get(NoFall.class)).toggle(); 
/* 59 */     if (antiHunger) ((AntiHunger)Modules.get().get(AntiHunger.class)).toggle(); 
/*    */   }
/*    */   
/*    */   private void sendPositionPacket(double x, double y, double z, boolean onGround) {
/* 63 */     mc.field_1724.field_3944.method_52787((class_2596)new class_2828.class_2829(x, y, z, onGround, mc.field_1724.field_5976));
/*    */   }
/*    */ }


/* Location:              C:\Users\hitbo\Downloads\jd-gui-windows-1.6.6\jd-gui-windows-1.6.6\meteor-client-1.21.11-86.jar!\meteordevelopment\meteorclient\commands\commands\DamageCommand.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */